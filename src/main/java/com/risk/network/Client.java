package com.risk.network;

import com.risk.gamephases.AttackMove;
import com.risk.gamephases.DistributionPhase;
import com.risk.gamephases.MoveTroopsPhase;
import com.risk.gamephases.PlaceTroopsPhase;
import com.risk.gamephases.TradeCardsPhase;
import com.risk.gui.WaitInLobbyController;
import com.risk.main.User;
import com.risk.network.messages.DiceResultDetermineBeginnerMessage;
import com.risk.network.messages.DisconnectMessage;
import com.risk.network.messages.DistributionMessage;
import com.risk.network.messages.GameOverMessage;
import com.risk.network.messages.GameStateReceivedMessage;
import com.risk.network.messages.MoveTroopsMessage;
import com.risk.network.messages.NextPhaseMessage;
import com.risk.network.messages.PlaceTroopsMessage;
import com.risk.network.messages.PlayerAddedToClientMessage;
import com.risk.network.messages.PlayersReadyUpdateMessage;
import com.risk.network.messages.TradeCardsMessage;
import com.risk.network.messages.UpdatePlayersLobbyMessage;
import com.risk.network.messages.UpdatedGameStateMessage;
import com.risk.network.messages.UserInfoMessage;
import com.risk.objects.Player;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import javafx.application.Platform;

/**
 * This class represents the Client side of the network connection. It is responsible for the
 * connection to the server and the communication with it. It also handles the communication with
 * the ChatServer.
 *
 * @author lgreiner, floribau
 */
public class Client extends Thread {

  private final String gameServerIP;
  private final int gameServerPort;
  private final boolean searching = false;
  private final String userKey;
  private final String userName;
  private Socket clientSocket;
  private boolean running;
  private ObjectInputStream inStreamer;
  private ObjectOutputStream outStreamer;
  private Player player;
  private ArrayList<String> usernamesInLobby = new ArrayList<>();
  private WaitInLobbyController waitInLobbyController;

  private ChatClient chatClient;
  private boolean triedToConnect = false;
  private boolean connected = false;
  private User user = User.getUser();


  /**
   * Constructor for the Client class. It initializes the attributes of the class. It also creates a
   * new ChatClient.
   *
   * @param gameServerIP   IP of the game server
   * @param gameServerPort port of the game server
   * @param userKey        user key
   * @param userName       user name
   * @author lgreiner
   */
  public Client(String gameServerIP, int gameServerPort, String userKey, String userName) {
    this.gameServerIP = gameServerIP;
    this.gameServerPort = gameServerPort;
    this.userKey = userKey;
    this.userName = userName;
    this.running = true;
    this.setPriority(MIN_PRIORITY);
  }

  /**
   * returns Userkey.
   *
   * @author floribau
   */
  public String getUserKey() {
    return this.userKey;
  }

  /**
   * This method returns the controller of the WaitInLobbyScene
   *
   * @return WaitInLobbyController
   * @Author floribau
   */
  public WaitInLobbyController getWaitInLobbyController() {
    return this.waitInLobbyController;
  }

  /**
   * This method sets the Controller of the WaitInLobbyScene.
   *
   * @author floribau
   */
  public void setWaitInLobbyController(WaitInLobbyController waitInLobbyController) {
    this.waitInLobbyController = waitInLobbyController;
  }

  /**
   * This method returns if the player tried to connect to the server.
   *
   * @return boolean triedToConnect
   * @author floribau
   */
  public boolean isTriedToConnect() {
    return this.triedToConnect;
  }

  /**
   * method returns if the player is connected to the server.
   *
   * @return boolean connected
   * @author floribau
   */
  public boolean isConnected() {
    return this.connected;
  }

  /**
   * run Method of the Client Thread.
   *
   * @author lgreiner
   */
  public void run() {
    try {
      clientSocket = new Socket(gameServerIP, gameServerPort);
      triedToConnect = true;
      connected = true;
      inStreamer = new ObjectInputStream(clientSocket.getInputStream());
      outStreamer = new ObjectOutputStream(clientSocket.getOutputStream());

      chatClient = new ChatClient(gameServerIP, gameServerPort + 1, this.userKey, this.userName);
      chatClient.start();

      Message msg;
      synchronized (inStreamer) {
        while ((msg = (Message) inStreamer.readObject()) != null) {
          handleMessage(msg);
        }
      }
    } catch (IOException e) {
      triedToConnect = true;
    } catch (Exception e) {
      // ignore
    }
    while (running) {
      // To-Do Multicast

    }

  }

  /**
   * Getter method for the ChatClient.
   *
   * @return ChatClient
   * @author lgreiner
   */
  public ChatClient getChatClient() {
    return this.chatClient;
  }

  /**
   * Returns IP of Gameserver.
   *
   * @author lgreiner
   */
  public String getGameServerIP() {
    return this.gameServerIP;
  }

  /**
   * Returns Port of Gameserver.
   *
   * @author lgreiner
   */
  public int getGameServerPort() {
    return this.gameServerPort;
  }

  /**
   * Returns the Player Object of the Client.
   *
   * @return Player
   * @author lkuech
   */
  public Player getPlayer() {
    return this.player;
  }

  /**
   * Handles incoming Messages by calling their handle method based on their Type by using switch
   * case.
   *
   * @author lgreiner
   */
  public void handleMessage(Message message) {
    switch (message.getType()) {
      case WELCOME_MESSAGE -> handleWelcomeMessage(message);
      case UPDATE_PLAYERS_LOBBY_MESSAGE -> handleUpdatePlayersLobbyMessage(message);
      case DISTRIBUTION_REQUEST -> handleDistributionRequest(message);
      case PLACE_TROOPS_REQUEST -> handlePlaceTroopsRequest(message);
      case TRADE_CARDS_REQUEST -> handleTradeCardsRequest(message);
      case ATTACK_MOVE_REQUEST -> handleAttackMoveRequest(message);
      case MOVE_TROOPS_REQUEST -> handleMoveTroopsRequest(message);
      case UPDATED_GAME_STATE_MESSAGE -> handleGameStateMessage(message);
      case START_GAME_MESSAGE -> handleStartGameMessage(message);
      case TEST_MESSAGE -> handleTestMessage(message);
      case NEXT_PHASE_REQUEST -> handleNextPhaseRequest();
      case PLAYERS_READY_UPDATE_MESSAGE -> handlePlayersReadyUpdateMessage(message);
      case SERVER_SHUTDOWN_MESSAGE -> handleServerShutdownMessage();
      case DICE_RESULT_DETERMINE_BEGINNER_MESSAGE ->
          handleDiceResultDetermineBeginnerMessage(message);
      case GAME_OVER_MESSAGE -> handleGameOverMessage(message);
    }
  }

  /**
   * This method handles the GameOverMesssage sent by the server.
   *
   * @param message the message received
   * @author lkuech
   */
  private void handleGameOverMessage(Message message) {
    GameOverMessage gameOverMessage = (GameOverMessage) message;
    user.setGameState(gameOverMessage.getGameState());
    user.getGameController().setCountriesToButtons();
    user.getGameController().repaintGui(user.getGameState());
    Platform.runLater(() -> {
      user.getGameController().showEndPane();
    });
  }

  /**
   * This method handles the TestMessage sent by the server.
   *
   * @param message the message recieved
   * @author hneumann
   */
  private void handleTestMessage(Message message) {
  }

  /**
   * This method handles the ServerShutdownMessage sent by the server. It will shutdown the client.
   *
   * @author floribau
   */
  private void handleServerShutdownMessage() {
    this.shutdown();
  }

  /**
   * This method handles the NextPhaseRequest sent by the server. It will update the GUI of the
   * GameController.
   *
   * @author floribau
   */
  private void handleNextPhaseRequest() {
    user.getGameController().nextPhaseRequested();
    sendMessage(new NextPhaseMessage());
  }

  /**
   * Handler method for the PlayersReadyUpdateMessage. This method will update the GUI of the
   * WaitInLobbyScene.
   *
   * @param msg the message recieved from the server
   * @author floribau
   */
  private void handlePlayersReadyUpdateMessage(Message msg) {
    PlayersReadyUpdateMessage playersReadyUpdateMessage = (PlayersReadyUpdateMessage) msg;
    if (waitInLobbyController != null) {
      waitInLobbyController.setStartGameButton(playersReadyUpdateMessage.isGameReady());
    }
  }

  /**
   * handles Message from Type WELCOME_MESSAGE After receiving method will create a new message of
   * Type UserInfoMessage. This Message  provides Information about the userKey and userName and
   * gets sent to the Server.
   *
   * @param message Message of Type WELCOME_MESSAGE
   * @author hneumann, lgreiner
   */
  private void handleWelcomeMessage(Message message) {
    sendMessage(new UserInfoMessage(userKey, userName));
  }

  /**
   * handles Message from Typer StarGame After receiving method will create a new Player and send
   * the PlayerAddedToClientMessage to the Server.
   *
   * @param message Message of Type StartGameMessage
   * @author floribau
   */
  private void handleStartGameMessage(Message message) {
    this.player = new Player(userName, userKey, false);
    sendMessage(new PlayerAddedToClientMessage(this.player));
    waitInLobbyController.startGame();
  }

  /**
   * handles Message from Type UpdatePlayersLobbyMessage After receiving method will update the
   * usernamesInLobby and call the method updateUsernamesInLobby() in the WaitInLobbyController
   * class.
   *
   * @param message Message of Type GameStateMessage
   * @author floribau & lgreiner
   */
  private void handleUpdatePlayersLobbyMessage(Message message) {
    UpdatePlayersLobbyMessage temp;
    temp = (UpdatePlayersLobbyMessage) message;
    usernamesInLobby = temp.getUserNames();
    if (waitInLobbyController != null) {
      waitInLobbyController.updateUsernamesInLobby(usernamesInLobby);
    }
  }

  /**
   * This method handles the DistributionRequest. It creates a new DistributionPhase and sends the
   * DistributionMessage to the server.
   *
   * @param message the message recieved
   * @author floribau & lgreiner
   */
  private void handleDistributionRequest(Message message) {
    StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();

    DistributionPhase distributionPhase = new DistributionPhase(userKey,
        user.getGameState().getUnownedCountries());
    //send message to server
    sendMessage(
        new DistributionMessage(distributionPhase.getPlayer(), distributionPhase.getCountry()));
  }

  private void handleTradeCardsRequest(Message message) {
    TradeCardsPhase tradeCardsPhase = new TradeCardsPhase(user.getGameState());
    while (!tradeCardsPhase.isFinishedPhase()) {
      try {
        Thread.sleep(50);
      } catch (InterruptedException e) {
        //ignore
      }
    }

    sendMessage(new TradeCardsMessage(tradeCardsPhase.getCardsTurnedIn()));
  }

  /**
   * This method handles the PlaceTroopsRequest.
   *
   * @param message - Message of Type PlaceTroopsRequestMessage
   * @author floribau & lgreiner
   */
  private void handlePlaceTroopsRequest(Message message) {
    PlaceTroopsPhase ptp = new PlaceTroopsPhase(player.getUserKey());
    sendMessage(
        new PlaceTroopsMessage(ptp.getPlacements()));
  }

  /**
   * This method handles the AttackMoveRequest.
   *
   * @param message - Message of Type AttackMoveRequestMessage
   * @author floribau, lgreiner
   */
  private void handleAttackMoveRequest(Message message) {
    AttackMove am = new AttackMove(userKey);
    sendMessage(am.createAttackMoveMessage());
  }

  /**
   * This method handles the MoveTroopsRequest.
   *
   * @param message - Message of Type MoveTroopsRequestMessage
   * @author floribau, lgreiner
   */
  private void handleMoveTroopsRequest(Message message) {
    MoveTroopsPhase mtp = new MoveTroopsPhase(userKey);
    sendMessage(new MoveTroopsMessage(mtp.getFromCountry(), mtp.getToCountry(),
        mtp.getTroopCount()));
  }

  /**
   * This method handles the GameStateMessage.
   *
   * @author floribau, lgreiner
   */
  private void handleGameStateMessage(Message message) {
    UpdatedGameStateMessage updatedGameStateMessage = (UpdatedGameStateMessage) message;
    user.setGameState(updatedGameStateMessage.getGameState());
    user.getGameController().setCountriesToButtons();
    user.getGameController().repaintGui(user.getGameState());
    try {
      Thread.sleep(100);
    } catch (InterruptedException e) {
      // ignore
    }
    sendMessage(new GameStateReceivedMessage());
  }

  /**
   * Handles the DiceResultDeterminerBeginnerMessage.
   *
   * @param message - the message to be handled
   * @author floribau
   */
  private void handleDiceResultDetermineBeginnerMessage(Message message) {
    DiceResultDetermineBeginnerMessage diceResultDetermineBeginnerMessage = (DiceResultDetermineBeginnerMessage) message;
    int diceResult = diceResultDetermineBeginnerMessage.getDiceResult();
    int playerNumber = diceResultDetermineBeginnerMessage.getPlayerNumber();
    user.getGameController().setDiceResult(diceResult);
    user.getGameController().setPlayerNumber(playerNumber);
  }

  /**
   * This method sends a message to the server.
   *
   * @param message - the message to be sent to the server
   * @author floribau, lgreiner
   */
  public void sendMessage(Message message) {
    try {
      synchronized (outStreamer) {
        outStreamer.writeObject(message);
        outStreamer.flush();
      }
    } catch (IOException e) {
      // ignore
      shutdown();
    } catch (Exception e) {
      // ignore
    }
  }

  /**
   * Returns a list of the usernames of the players in the lobby.
   *
   * @return list of usernames in the lobby
   * @author floribau
   */
  public ArrayList<String> getUsernamesInLobby() {
    return this.usernamesInLobby;
  }

  /**
   * Sets running to false and closes in and out Stream.
   *
   * @author lgreiner, floribau
   */
  public void shutdown() {
    sendMessage(new DisconnectMessage());
    chatClient.stopRunning();

    if (waitInLobbyController != null) {
      waitInLobbyController.showDisconnectPane();
    }
    if (player != null && user.getGameController() != null) {
      user.getGameController().showDisconnectPane();
    }
    this.running = false;
    try {
      inStreamer.close();
      outStreamer.close();
    } catch (Exception e) {
      // ignore
    }
    user.setClient(null);
  }

  /**
   * Returns if the client is running or not.
   *
   * @author hneumann
   */
  public boolean getIsRunning() {
    return this.running;
  }


}
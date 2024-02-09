package com.risk.network;

import com.risk.ai.AiPlayer;
import com.risk.ai.Node;
import com.risk.ai.Tree;
import com.risk.ai.util.Score;
import com.risk.gamephases.TradeCardsPhase;
import com.risk.main.User;
import com.risk.network.messages.AttackMoveMessage;
import com.risk.network.messages.DistributionMessage;
import com.risk.network.messages.GameStateReceivedMessage;
import com.risk.network.messages.MoveTroopsMessage;
import com.risk.network.messages.NextPhaseMessage;
import com.risk.network.messages.PlaceTroopsMessage;
import com.risk.network.messages.PlayerAddedToClientMessage;
import com.risk.network.messages.TradeCardsMessage;
import com.risk.network.messages.UpdatedGameStateMessage;
import com.risk.network.messages.UserInfoMessage;
import com.risk.objects.AttackResult;
import com.risk.objects.Card;
import com.risk.objects.Country;
import com.risk.objects.Placement;
import com.risk.objects.Player;
import com.risk.objects.dice.AttackRoll;
import com.risk.objects.dice.DefenseRoll;
import com.risk.util.NameList;
import com.risk.util.exceptions.ClientAlreadyContainsPlayerException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

/**
 * This class represents a client that is controlled by an AI.
 *
 * @author hneumann
 */
public class AiPlayerClient extends Thread {

  private final String gameServerIP;
  private final int gameServerPort;
  private final String userKey;
  private final String userName;
  private final ArrayList<String> usernamesInLobby = new ArrayList<>();
  private final double thresholdMedium = 0.675;
  private final int difficulty;
  private AiPlayer aiPlayer;
  private Socket clientSocket;
  private boolean running;
  private ObjectInputStream inStreamer;
  private ObjectOutputStream outStreamer;
  private Player player;
  private ChatClient chatClient;
  private boolean isAiClient;
  private User user = User.getUser();
  private int attackCounter = 0;

  /**
   * Constructor of Clients in wich the IP-Address and Port gets set for the connection to.
   *
   * @param gameServerIP IP-Address of the Server
   * @param gameServerPort Port of the Server
   * @param userKey UserKey of the Client
   * @author hneumann
   */
  public AiPlayerClient(String gameServerIP, int gameServerPort, String userKey, int difficulty) {
    this.gameServerIP = gameServerIP;
    this.gameServerPort = gameServerPort;
    this.userKey = userKey;
    this.aiPlayer = new AiPlayer(userKey, userKey, difficulty);
    this.difficulty = difficulty;
    String name = NameList.getRandomName();
    switch (difficulty) {
      case 1 -> name = name + " (Easy)";
      case 2 -> name = name + " (Medium)";
      case 3 -> name = name + " (Hard)";
    }
    this.userName = name;
    this.running = true;
    this.isAiClient = false;
  }

  /**
   * Mehtod that handles the incomin message from the server and calls the corresponding method
   * based on the message type.
   *
   * @param message Message from the server that needs to be handled by the client
   * @author hneumann
   */
  private void handleMessage(Message message) {
    switch (message.getType()) {
      case WELCOME_MESSAGE -> handleWelcomeMessage(message);
      case SERVER_SHUTDOWN_MESSAGE -> handleServerShutdownMessage();
      case TRADE_CARDS_REQUEST -> handleTradeCardsRequest(message);
      case START_GAME_MESSAGE -> handleStartGameMessage(message);
      case DISTRIBUTION_REQUEST -> handleDistributionRequest(message);
      case PLACE_TROOPS_REQUEST -> handlePlaceTroopsRequest(message);
      case ATTACK_MOVE_REQUEST -> handleAttackMoveRequest(message);
      case MOVE_TROOPS_REQUEST -> handleMoveTroopsRequest(message);
      case NEXT_PHASE_REQUEST -> handleNextPhaseRequest(message);
      case UPDATED_GAME_STATE_MESSAGE -> handleUpdatedGameStateMessage(message);
      default -> {
      }
    }
  }
  /**
   * Method that handles the incoming UpdatedGameStateMessage and updates the gamestate in AIPlayer.
   *
   * @param message UpdatedGameStateMessage
   * @author hneumann
   */
  private void handleTradeCardsRequest(Message message) {
    // TODO
    TradeCardsPhase tradeCardsPhase = new TradeCardsPhase(user.getGameState(), true);
    if (tradeCardsPhase.checkTradePossible()) {
      tradeCardsPhase.tradeAiCards();
      Card[] cards = tradeCardsPhase.getCardsTurnedIn();
      Time now = new Time(System.currentTimeMillis());
      long time = now.getTime();
      while (System.currentTimeMillis() < time + 600) {
        // wait
      }
      sendMessage(new TradeCardsMessage(cards));
      return;
    }
    sendMessage(new TradeCardsMessage(null));
  }

  /**
   * Handles the GameStateMessage and updates the gamestate in AIPlayer.
   *
   * @param message GameStateMessage
   * @author hneumann
   */
  private void handleUpdatedGameStateMessage(Message message) {
    UpdatedGameStateMessage updatedGameStateMessage = (UpdatedGameStateMessage) message;
    // user.setGameState(updatedGameStateMessage.getGameState().cloneGameState()); //TODO if
    // something crashes, maybe here
    try {
      Thread.sleep(50);
    } catch (InterruptedException e) {
      // ignore
    }
    sendMessage(new GameStateReceivedMessage());
  }

  /**
   * Handles the WelcomeMessage.
   *
   * @param message welcomeMessage
   * @author hneumann
   */
  private void handleWelcomeMessage(Message message) {
    // TODO sout entfernen
    sendMessage(new UserInfoMessage(userKey, userName, true));
  }

  /**
   * handles the DistributionRequestMessage and sends the DistributionMessage. Calculates the
   * country to distribute to using the AIMethod.
   *
   * @param message DistributionRequestMessage
   * @author hneumann
   */
  private void handleDistributionRequest(Message message) {
    Time time = new Time(System.currentTimeMillis());
    long now = time.getTime();
    // TODO Zeit anpassen
    while (now + 600 > time.getTime()) {
      time = new Time(System.currentTimeMillis());
    }

    Country country1 = this.aiPlayer.distributeTroops(user.getGameState());
    double rdm = Math.random();
    switch (this.difficulty) {
      case 1:
        country1 = this.aiPlayer.distributeRandomCountry(user.getGameState());
      case 2:
        if (rdm < this.thresholdMedium) {
          country1 = this.aiPlayer.distributeRandomCountry(user.getGameState());
        } else {
          country1 = this.aiPlayer.distributeTroops(user.getGameState());
        }
    }
    sendMessage(new DistributionMessage(this.aiPlayer.getUserKey(), country1));
  }

  /**
   * Method that runs the AiClient Connects to the server and handles the messages.
   *
   * @author hneumann
   */
  public void run() {
    try {
      clientSocket = new Socket(gameServerIP, gameServerPort);
      inStreamer = new ObjectInputStream(clientSocket.getInputStream());
      outStreamer = new ObjectOutputStream(clientSocket.getOutputStream());

      Message msg;
      while ((msg = (Message) inStreamer.readObject()) != null) {
        handleMessage(msg);
      }
    } catch (IOException ignored) {
      // ignore
    } catch (Exception e) {
      //ignore
    }
  }

  /**
   * sendMessage method that sends a message to the server.
   *
   * @param message Message to be sent to the server
   * @author hneumann
   */
  public void sendMessage(Message message) {
    try {
      outStreamer.writeObject(message);
      outStreamer.flush();
    } catch (IOException e) {
      //ignore
    }
  }

  /**
   * handles the PlaceTroopsRequestMessage and sends the PlaceTroopsMessage. Calculates the
   * placements using the AIMethod reinforcement.
   *
   * @param message PlaceTroopsRequestMessage
   * @author hneumann
   */
  private void handlePlaceTroopsRequest(Message message) {

    Time time = new Time(System.currentTimeMillis());
    long now = time.getTime();
    while (now + 600 > time.getTime()) {
      time = new Time(System.currentTimeMillis());
    }

    int troops = this.aiPlayer.calculateTroops();
    troops += user.getGameState().getTroopsFromTradingCards();
    List<Placement> placements = this.aiPlayer.reinforcement(user.getGameState(), troops);
    sendMessage(new PlaceTroopsMessage((ArrayList<Placement>) placements));
  }

  /**
   * handles the AttackMoveRequestMessage and sends the AttackMoveMessage. Calculates the attack
   * using the AIMethod attack.
   *
   * @param message AttackMoveRequestMessage
   * @author hneumann
   */
  private void handleAttackMoveRequest(Message message) {

    Time time = new Time(System.currentTimeMillis());
    long now = time.getTime();
    while (now + 600 > time.getTime()) {
      time = new Time(System.currentTimeMillis());
    }
    double score = Score.calculateScoring(user.getGameState());

    List<Node> attacks = this.aiPlayer.attack(user.getGameState().cloneGameState());
    Tree tree = new Tree(new Node(user.getGameState().cloneGameState(), 0));
    if (tree.getRoot().getBestChild() == null) {
      sendMessage(new NextPhaseMessage());
      return;
    }
    if (tree.getRoot().getBestChild().getScore() < score && this.attackCounter > 1) {
      sendMessage(new NextPhaseMessage());
      return;
    }
    Country start = tree.getRoot().getBestChild().getFrom();
    Country target = tree.getRoot().getBestChild().getTo();
    start = user.getGameState().getCountryByName(attacks.get(0).getBestChild().getFrom().getName());
    target = user.getGameState().getCountryByName(attacks.get(0).getBestChild().getTo().getName());
    switch (this.difficulty) {
      case 1:
        start = this.aiPlayer.attackRandomCountry(user.getGameState()).get(0);
        target = this.aiPlayer.attackRandomCountry(user.getGameState()).get(0);
      case 2:
        double rdm = Math.random();
        if (rdm < this.thresholdMedium) {
          start = this.aiPlayer.attackRandomCountry(user.getGameState()).get(0);
          target = this.aiPlayer.attackRandomCountry(user.getGameState()).get(1);
        } else {
          start = this.aiPlayer.attack(user.getGameState()).get(0).getFrom();
          target = this.aiPlayer.attack(user.getGameState()).get(0).getTo();
        }
    }
    start = attacks.get(0).getBestChild().getFrom();
    target = attacks.get(0).getBestChild().getTo();

    // Simulation of the attack
    int attackingTroops = user.getGameState().getCountryByName(start.getName()).getTroops() - 1;
    int defendingTroops = user.getGameState().getCountryByName(target.getName()).getTroops();

    while (attackingTroops > 2 && defendingTroops > 0) {
      AttackRoll attackRoll = new AttackRoll(attackingTroops);
      DefenseRoll defenseRoll = new DefenseRoll(defendingTroops);
      AttackResult attackResult = new AttackResult(attackRoll, defenseRoll);
      attackingTroops -= attackResult.getAttackingTroopsLost();
      defendingTroops -= attackResult.getDefendingTroopsLost();
    }
    Player winner = user.getGameState().getPlayerByUserKey(target.getOwner());
    AttackMoveMessage attackMoveMessage = null;
    Country attackingCountry = user.getGameState().getCountryByName(start.getName());
    Country defendingCountry = user.getGameState().getCountryByName(target.getName());
    if (defendingTroops <= 0) {
      attackMoveMessage =
          new AttackMoveMessage(
              defendingCountry,
              attackingCountry,
              this.aiPlayer.getUserKey(),
              attackingTroops - 1,
              1);
    } else {
      attackMoveMessage =
          new AttackMoveMessage(
              defendingCountry, attackingCountry, defendingCountry.getOwner(), 1, defendingTroops);
    }

    sendMessage(attackMoveMessage);
  }

  /**
   * handles the StartGameMessage and creates a new AIPlayer.
   *
   * @param message StartGameMessage
   * @author hneumann
   */
  private void handleStartGameMessage(Message message) {
    this.aiPlayer = new AiPlayer(userName, userKey, this.difficulty);
    sendMessage(new PlayerAddedToClientMessage(aiPlayer));
  }

  /**
   * handles the MoveTroopsRequestMessage and sends the MoveTroopsMessage. Calculates the move using
   * the AIMethod moveTroops.
   *
   * @param message MoveTroopsRequestMessage
   * @author hneumann
   */
  private void handleMoveTroopsRequest(Message message) {
    // TODO sout entfernen
    this.attackCounter = 0; // reset count um nächste Runde wieder dreimal angreifen zu können

    Time now = new Time(System.currentTimeMillis());
    long time = now.getTime();
    while (time + 600 > now.getTime()) {
      now = new Time(System.currentTimeMillis());
    }

    List<Country> countries = this.aiPlayer.moveTroops(user.getGameState().cloneGameState());
    if (user.getGameState().getTroopsByPlayer(this.aiPlayer.getUserKey())
            == user.getGameState().getCountriesOwnedByPlayer(this.aiPlayer.getUserKey()).size()
        || countries == null) {
      sendMessage(new NextPhaseMessage());
      return;
    }
    Country from = user.getGameState().getCountryByName(countries.get(0).getName());
    Country to = user.getGameState().getCountryByName(countries.get(1).getName());

    int troops = from.getTroops() - 1;
    sendMessage(new MoveTroopsMessage(from, to, troops));
  }
  /**
   * sends the NextPhaseMessage.
   *
   * @param message NextPhaseRequestMessage
   * @author hneumann
   */
  private void handleNextPhaseRequest(Message message) {
    sendMessage(new NextPhaseMessage());
  }
  /**
   * handles the EndGameMessage and sends the EndGameMessage to the server.
   *
   * @author hneumann
   */
  private void handleServerShutdownMessage() {
    this.shutdown();
  }

  public ChatClient getChatClient() {
    return this.chatClient;
  }
  /**
   * returns the String gameServerIP.
   *
   * @return String gameServerIP @Author hneumann
   */
  public String getGameServerIP() {
    return this.gameServerIP;
  }

  /**
   * returns the Player.
   *
   * @return Player player @Author hneumann
   */
  public Player getPlayer() {
    return this.player;
  }

  /**
   * returns the String userKey.
   *
   * @return String userKey
   * @author hneumann
   */
  public String getUserKey() {
    return this.userKey;
  }
  /**
   * returns the boolean isAiClient.
   *
   * @return boolean true if AiClient, false if not AiClient
   * @author hneumann
   */
  public boolean isAiClient() {
    return this.isAiClient;
  }
  /**
   * sets the boolean isAiClient.
   *
   * @param isAiClient boolean true if AiClient, false if not AiClient
   * @author hneumann
   */
  public void setIsAiClient(boolean isAiClient) {
    this.isAiClient = isAiClient;
  }

  /**
   * boolean to check if the outStreamer is ready to send.
   *
   * @return boolean true if ready to send, false if not ready to send
   * @author hneumann
   */
  public boolean readyToSend() {
    return outStreamer != null;
  }

  /**
   * sends a message to the server.
   *
   * @throws ClientAlreadyContainsPlayerException
   * @author hneumann
   */
  public void addPlayer() throws ClientAlreadyContainsPlayerException {
    if (player == null) {
      player = new Player("", userKey, false);
    } else {
      throw new ClientAlreadyContainsPlayerException();
    }
  }
  /**
   * returns the usernames in the lobby
   *
   * @return ArrayList<String> usernames in the lobby
   * @author hneumann
   */
  public ArrayList<String> getUsernamesInLobby() {
    return this.usernamesInLobby;
  }

  /**
   * shutdown the AiClient
   *
   * @author hneumann
   */
  public void shutdown() {
    this.running = false;
    try {
      inStreamer.close();
      outStreamer.close();
    } catch (Exception e) {
      //ignore
    }
  }
}

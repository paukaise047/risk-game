package com.risk.network;

import com.risk.ai.AiPlayer;
import com.risk.database.DatabaseHandler;
import com.risk.game.GameState;
import com.risk.main.User;
import com.risk.network.messages.AttackMoveMessage;
import com.risk.network.messages.AttackMoveRequest;
import com.risk.network.messages.DiceResultDetermineBeginnerMessage;
import com.risk.network.messages.DistributionMessage;
import com.risk.network.messages.DistributionRequest;
import com.risk.network.messages.GameOverMessage;
import com.risk.network.messages.MoveTroopsMessage;
import com.risk.network.messages.MoveTroopsRequest;
import com.risk.network.messages.NextPhaseRequest;
import com.risk.network.messages.PlaceTroopsMessage;
import com.risk.network.messages.PlaceTroopsRequest;
import com.risk.network.messages.PlayerAddedToClientMessage;
import com.risk.network.messages.PlayersReadyUpdateMessage;
import com.risk.network.messages.ServerShutdownMessage;
import com.risk.network.messages.TestMessage;
import com.risk.network.messages.TradeCardsMessage;
import com.risk.network.messages.TradeCardsRequest;
import com.risk.network.messages.UpdatePlayersLobbyMessage;
import com.risk.network.messages.UpdatedGameStateMessage;
import com.risk.network.messages.UserInfoMessage;
import com.risk.network.messages.WelcomeMessage;
import com.risk.objects.Card;
import com.risk.objects.Country;
import com.risk.objects.Player;
import com.risk.objects.dice.Dice;
import com.risk.objects.enums.GamePhase;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server implements Runnable {

  private final int port;
  private ArrayList<ConnectionHandler> connections;
  private int countConnections = 0;
  private ServerSocket server;
  private boolean running;
  private ExecutorService pool;
  private GameState gameState;
  private ChatServer chatServer;
  private MultiCastSender multiCastSender;
  private boolean shutdown = false;

  /**
   * This method is the constructor of the Server class. It creates a new Server with a random port
   * between 49152 and 65535.
   *
   * @author floribau
   */
  public Server() {
    connections = new ArrayList<>();

    int minPort = 49152;
    int maxPort = 65535;
    this.port = (int) (Math.random() * ((maxPort - minPort) + 1)) + minPort;
  }

  /**
   * Main method, only for testing purposes.
   *
   * @param args - run arguments, should be left empty here
   * @author floribau
   */
  public static void main(String[] args) {
    Server server = new Server();
    server.run();
  }

  /**
   * This method runs the server. It creates a new ServerSocket and listens for incoming
   * connections. If a connection is established, a new ConnectionHandler is created and added to
   * the connections ArrayList. The ConnectionHandler is then started in a new Thread.
   *
   * @author floribau, lkuech
   */
  @Override
  public void run() {
    try {
      running = true;
      server = new ServerSocket(port);
      pool = Executors.newCachedThreadPool();

      DatabaseHandler db = new DatabaseHandler();
      ServerCredentials myCredentials = new ServerCredentials(getIP(), port, db.getUserElo());
      this.multiCastSender = new MultiCastSender(myCredentials);
      Thread multiThreadedServer = new Thread(multiCastSender);
      multiThreadedServer.start();

      // ChatServer Setup
      chatServer = new ChatServer(port);
      chatServer.start();

      while (running) {
        if (countConnections < 6) {
          Socket client = server.accept();
          ConnectionHandler handler = new ConnectionHandler(client);
          connections.add(handler);
          pool.execute(handler);
          countConnections++;
        } else {
          try {
            Thread.sleep(100);
          } catch (InterruptedException e) {
            // ignore
          }
        }
      }
    } catch (IOException e) {
      // ignore
      shutdown = true;
      shutdown();
    }
  }

  /**
   * This method gets the ip of the server.
   *
   * @author floribau
   */
  public String getIP() {
    try {
      return InetAddress.getLocalHost().getHostAddress();
    } catch (UnknownHostException e) {
      // ignore
    }
    return null;
  }

  /**
   * This method gets the port of the server.
   *
   * @author floribau
   */
  public int getPort() {
    return this.port;
  }

  /**
   * This method gets the gameState of the server.
   *
   * @author floribau
   */
  public GameState getGameState() {
    return this.gameState;
  }

  /**
   * This method gets the ConnectionHandler of a specific user by the userKey.
   *
   * @author floribau
   */
  public ConnectionHandler getConnectionHandlerByUserKey(String userKey) {
    for (ConnectionHandler ch : connections) {
      if (ch.userKey.equals(userKey)) {
        return ch;
      }
    }
    return null;
  }

  public ArrayList<ConnectionHandler> getConnectionHandlers() {
    return this.connections;
  }

  /**
   * This method stops the multicast sender so the server cant be found anymore.
   *
   * @author lkuech
   */
  public void shutdownMulticast() {
    this.multiCastSender.setRunning(false);
  }

  /**
   * This method remove a connection from the connections ArrayList.
   *
   * @author floribau
   */
  public void removeLastAiConnection() {
    for (int i = connections.size() - 1; i >= 0; i--) {
      ConnectionHandler ch = connections.get(i);
      if (ch.isAi) {
        ch.shutdown();
        AiPlayer.decreaseCount();
        break;
      }
    }
  }

  /**
   * This method broadcasts a message to all clients.
   *
   * @author lkuech
   * @msg - Message that is sent to the server
   */
  public void broadcast(Message msg) {
    for (ConnectionHandler ch : this.connections) {
      ch.sendMessage(msg);
    }
  }

  /**
   * This method sets isGameReady to true for all connections.
   *
   * @author floribau
   */
  public boolean isGameReady() {
    boolean ready = true;
    connections.get(0).userReady = true;
    for (ConnectionHandler ch : connections) {
      if (!ch.userReady) {
        ready = false;
        break;
      }
    }
    return ready;
  }

  /**
   * This method checks all game controllers if they are ready and added.
   *
   * @author floribau
   */
  public boolean checkAllGameControllersAdded() {
    boolean ready = true;
    for (ConnectionHandler ch : connections) {
      if (!ch.addedGameController) {
        ready = false;
        break;
      }
    }
    return ready;
  }

  /**
   * This method checks if all clients have pressed continue after the determineBeginner() method.
   *
   * @author floribau
   */
  public boolean checkAllPressedContinueAfterDetermineBeginner() {
    boolean ready = true;
    for (ConnectionHandler ch : connections) {
      if (!ch.pressedContinueAfterDetermineBeginner) {
        ready = false;
        break;
      }
    }
    return ready;
  }

  /**
   * This method checks if all clients have received the GameState.
   *
   * @author floribau
   */
  public boolean checkAllReceivedGameState() {
    boolean ready = true;
    for (ConnectionHandler ch : connections) {
      if (!ch.gameStateReceived) {
        ready = false;
        break;
      }
    }
    return ready;
  }

  /**
   * determines the player to begin via Dice.roll() and then calls rearrangeClients().
   *
   * @author floribau
   */
  public void determineBeginner() {
    int max = -1;
    for (ConnectionHandler ch : this.connections) {
      int diceResult = com.risk.objects.dice.Dice.roll();
      ch.diceResult = diceResult;
      if (diceResult > max) {
        max = diceResult;
      }
    }
    ArrayList<ConnectionHandler> equalMax = new ArrayList<>();
    for (ConnectionHandler ch : this.connections) {
      if (ch.diceResult == max) {
        equalMax.add(ch);
      }
    }
    while (equalMax.size() > 1) {
      ArrayList<ConnectionHandler> equalMaxNew = new ArrayList<>();
      max = -1;
      for (ConnectionHandler ch : this.connections) {
        int diceResult = Dice.roll();
        ch.diceResult = diceResult;
        if (diceResult > max) {
          max = diceResult;
        }
      }
      for (ConnectionHandler ch : this.connections) {
        if (ch.diceResult == max) {
          equalMaxNew.add(ch);
        }
      }
      equalMax = equalMaxNew;
    }
    ConnectionHandler starter = equalMax.get(0);
    rearrangeClients(starter);
    for (ConnectionHandler ch : connections) {
      ch.sendMessage(new DiceResultDetermineBeginnerMessage(ch.diceResult, ch.playerNumber));
    }
  }

  /**
   * rearranges the ConnectionHandler list according to the following logic: take the starter
   * parameter and put this to index 0, then go through connections and adds the rest of the
   * ConnectionHandlers according to their order coming after starter.
   *
   * @param starter - the first ConnectionHandler to add to the list
   * @author floribau
   */
  public void rearrangeClients(ConnectionHandler starter) {
    ArrayList<ConnectionHandler> connectionsNewOrder = new ArrayList<>();
    int positionStarter = 0;
    for (int i = 0; i < connections.size(); i++) {
      if (connections.get(i).equals(starter)) {
        positionStarter = i;
        break;
      }
    }
    for (int i = 0; i < connections.size(); i++) {
      int pos = i + positionStarter;
      ConnectionHandler ch = connections.get(pos % connections.size());
      ch.playerNumber = i + 1;
      ch.player.setPlayerNumber(ch.playerNumber);
      connectionsNewOrder.add(ch);
      String colourCode = "";
      switch (ch.playerNumber) {
        case 1 -> colourCode = "ORANGE";
        case 2 -> colourCode = "GREEN";
        case 3 -> colourCode = "RED";
        case 4 -> colourCode = "BLUE";
        case 5 -> colourCode = "PURPLE";
        case 6 -> colourCode = "#1FC4FF";
        default -> { //ignore
        }
      }

      ch.getPlayer().setPlayerColour(colourCode);

    }
    this.connections = connectionsNewOrder;
  }

  /**
   * This class shuts down the server and all its connections.
   *
   * @author floribau
   */
  public void shutdown() {
    try {
      broadcast(new ServerShutdownMessage());
      if (chatServer != null) {
        chatServer.shutdown();
      }
      for (ConnectionHandler ch : connections) {
        ch.shutdown();
      }
      running = false;
      if (server != null && !server.isClosed()) {
        server.close();
      }
    } catch (IOException e) {
      // ignore
    }
  }

  class ConnectionHandler implements Runnable {

    private Socket client;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private String username;
    private String userKey;
    private Player player;
    private boolean userReady = false;
    private boolean addedGameController = false;
    private boolean pressedContinueAfterDetermineBeginner = false;
    private boolean gameStateReceived = false;
    private boolean isAi = false;
    private int diceResult;
    private int playerNumber;

    /**
     * This constructor creates a new ConnectionHandler for a Client.
     *
     * @author floribau
     */
    public ConnectionHandler(Socket client) {
      this.client = client;
    }

    /**
     * overrides the run method of interface Runnable: opens output- and input-streams to connect to
     * a Client and reads Messages coming from this Client.
     *
     * @author floribau
     */
    @Override
    public void run() {
      try {
        out = new ObjectOutputStream(client.getOutputStream());
        in = new ObjectInputStream(client.getInputStream());
        ArrayList<String> tester = new ArrayList<>();
        sendMessage(new TestMessage(tester));
        sendMessage(new WelcomeMessage());
        Message msg;
        try {
          synchronized (in) {
            while ((msg = (Message) in.readObject()) != null) {
              handleMessage(msg);
            }
          }
        } catch (SocketException e) {
          if ((User.getUser().getGameController() != null)) {
            broadcast(new ServerShutdownMessage());
          }
          shutdown();
        } catch (Exception e) {
          //ignore
        }
      } catch (IOException e) {
        shutdown();
      } catch (Exception e) {
        //ignore
      }
    }

    /**
     * returns the username of the Client connected to this ConnectionHandler.
     *
     * @return username
     * @author floribau
     */
    public String getUsername() {
      return this.username;
    }

    /**
     * returns the userKey of the Client connected to this ConnectionHandler.
     *
     * @return userKey
     * @author floribau
     */
    public String getUserKey() {
      return this.userKey;
    }

    /**
     * returns the Player of the Client connected to this ConnectionHandler.
     *
     * @return player
     * @author floribau
     */
    public Player getPlayer() {
      return this.player;
    }


    /**
     * called when the ConnectionHandler receives a new message, calls the correct handler method
     * according to the messageType.
     *
     * @param msg - the message to handle
     * @author floribau
     */
    private void handleMessage(Message msg) {

      switch (msg.getType()) {
        case USER_INFO_MESSAGE -> handleUserInfoMessage(msg);
        case DISTRIBUTION_MESSAGE -> handleDistributionMessage(msg);
        case TRADE_CARDS_MESSAGE -> handleTradeCardsMessage(msg);
        case PLACE_TROOPS_MESSAGE -> handlePlaceTroopsMessage(msg);
        case ATTACK_MOVE_MESSAGE -> handleAttackMoveMessage(msg);
        case MOVE_TROOPS_MESSAGE -> handleMoveTroopsMessage(msg);
        case WELCOME_MESSAGE -> handleWelcomeMessage();
        case PLAYER_ADDED_TO_CLIENT_MESSAGE -> handlePlayerAddedToClientMessage(msg);
        case PLAYER_READY_MESSAGE -> handlePlayerReadyMessage();
        case PRESSED_CONTINUE_AFTER_DETERMINE_BEGINNER_MESSAGE ->
            handlePressedContinueAfterDetermineBeginnerMessage();
        case GAME_CONTROLLER_ADDED_TO_PLAYER_MESSAGE -> handleGameControllerAddedToPlayer();
        case GAME_STATE_RECEIVED_MESSAGE -> handleGameStateReceivedMessage();
        case NEXT_PHASE_MESSAGE -> handleNextPhaseMessage();
        case SET_AI_PLAYER_ON_READY_MESSAGE -> handleSetAiPlayerOnReadyMessage();
        case DISCONNECT_MESSAGE -> handleDisconnectMessage();
        default ->{
          // ignore
        }
      }
    }

    /**
     * only for testing purposes.
     *
     * @author floribau
     */
    private void handleWelcomeMessage() {
      // System.out.println("\nNew welcome message received");
    }

    /**
     * Handles disconnect messages. It removes the connection from the connections list and
     * broadcasts a server shutdown message if there are no more connections.
     *
     * @author lkuech
     */
    public void handleDisconnectMessage() {
      for (int i = connections.size() - 1; i >= 0; i--) {
        this.shutdown();
        break;
      }
    }

    /**
     * This method handles move troops messages. It updates the troops of the countries and resets
     * the last attack move countries. It also sets the next player and resets the eliminated player
     * to null.
     *
     * @author floribau
     */
    private void handleMoveTroopsMessage(Message msg) {
      MoveTroopsMessage moveTroopsMessage = (MoveTroopsMessage) msg;
      int troopCount = moveTroopsMessage.getTroopCount();
      Country fromCountry = moveTroopsMessage.getFromCountry();
      Country toCountry = moveTroopsMessage.getToCountry();
      if (troopCount != 0) {
        gameState.updateTroops(fromCountry, fromCountry.getTroops() - troopCount);
        gameState.updateTroops(toCountry, toCountry.getTroops() + troopCount);
      }

      gameState.resetLastAttackMoveCountries();
      gameState.nextPlayer();
      gameState.resetEliminatedPlayer();
      gameState.setGamePhase(GamePhase.TRADE_CARDS_PHASE);
      broadcast(new UpdatedGameStateMessage(gameState.cloneGameState()));
    }

    /**
     * This method handles the AttackMoveMessage. It updates the gameState and sends the updated
     * gameState to all clients.
     *
     * @author , floribau
     */
    private void handleAttackMoveMessage(Message msg) {
      AttackMoveMessage attackMoveMessage = (AttackMoveMessage) msg;
      if (attackMoveMessage.getDefendingCountryTroops() < 0) {
        gameState.resetLastAttackMoveCountries();
        gameState.setGamePhase(GamePhase.MOVE_TROOPS_PHASE);
        UpdatedGameStateMessage newGameState = new UpdatedGameStateMessage(
            gameState.cloneGameState());
        broadcast(newGameState);
      } else {
        String attackingPlayerKey = attackMoveMessage.getAttackingCountry().getOwner();
        gameState.updateGameStateAttackMove(attackMoveMessage.getDefendingCountry(),
            attackMoveMessage.getAttackingCountry(), attackMoveMessage.getDefendingCountryOwner(),
            attackMoveMessage.getDefendingCountryTroops(),
            attackMoveMessage.getAttackingCountryTroops());
        gameState.setContinentOwner();
        gameState.setGamePhase(GamePhase.ATTACK_PHASE);
        String attackedPlayerKey = attackMoveMessage.getDefendingCountry().getOwner();
        if (gameState.checkPlayerEliminated(attackedPlayerKey)) {
          gameState.eliminatePlayer(attackingPlayerKey, attackedPlayerKey);
        }
        if (gameState.checkGameOver()) {
          DatabaseHandler dbHandler = new DatabaseHandler();
          dbHandler.updateUsers(gameState);
          dbHandler.updateThisUser(gameState);
          broadcast(new GameOverMessage(gameState.cloneGameState()));
        } else {
          UpdatedGameStateMessage newGameState = new UpdatedGameStateMessage(
              gameState.cloneGameState());

          broadcast(newGameState);
        }
      }
    }

    /**
     * Handles DistributionMessages. It updates the gameState and sends a NextPhaseRequest to the
     * client.
     *
     * @param msg - The message received from the client.
     * @author floribau
     */
    private void handleDistributionMessage(Message msg) {
      DistributionMessage distributionMessage = (DistributionMessage) msg;
      gameState.updateGameStateDistributionPhase(distributionMessage.getPlayer(),
          distributionMessage.getCountry());
      gameState.setContinentOwner();
      gameState.nextPlayer();
      if (gameState.getTroopsToDistribute() == 0) {
        gameState.setGamePhase(GamePhase.TRADE_CARDS_PHASE);
      }

      UpdatedGameStateMessage newGameState = new UpdatedGameStateMessage(
          gameState.cloneGameState());
      broadcast(newGameState);
    }

    /**
     * This method handles PlaceTroopsMessages. It updates the gameState and sends a
     * NextPhaseRequest to the client.
     *
     * @param msg - The PlaceTroopsMessage received from the client.
     * @author floribau
     */
    private void handlePlaceTroopsMessage(Message msg) {
      PlaceTroopsMessage placeTroopsMessage = (PlaceTroopsMessage) msg;
      gameState.updateGameStatePlaceTroopPhase(placeTroopsMessage.getPlacements());

      getConnectionHandlerByUserKey(gameState.getCurrentPlayerKey()).sendMessage(
          new NextPhaseRequest());
    }

    /**
     * This method handles TradeCardsMessages. It updates the gameState and sends a NextPhaseRequest
     * to the client.
     *
     * @param msg - The TradeCardsMessage received from the client.
     * @author floribau
     */
    private void handleTradeCardsMessage(Message msg) {
      TradeCardsMessage tradeCardsMessage = (TradeCardsMessage) msg;
      Integer[] cardsTradedIds = tradeCardsMessage.getTradedCards();
      if (cardsTradedIds != null) {
        Card[] cardsTraded = new Card[3];
        cardsTraded[0] = gameState.getCardById(cardsTradedIds[0]);
        cardsTraded[1] = gameState.getCardById(cardsTradedIds[1]);
        cardsTraded[2] = gameState.getCardById(cardsTradedIds[2]);
        gameState.updateGameStateTradeCardsPhase(gameState.getCurrentPlayerKey(), cardsTradedIds);
      }
      gameState.setGamePhase(GamePhase.PLACE_TROOP_PHASE);
      broadcast(new UpdatedGameStateMessage(gameState.cloneGameState()));
    }

    /**
     * sets username and userKey for the ConnectionHandler and broadcasts the updated users in
     * lobby-list to all clients.
     *
     * @param msg - the message to handle
     * @author floribau
     */
    private void handleUserInfoMessage(Message msg) {
      UserInfoMessage userInfoMessage = (UserInfoMessage) msg;
      username = userInfoMessage.getUsername();
      userKey = userInfoMessage.getUserKey();
      isAi = userInfoMessage.getIsAi();
      ArrayList<String> userNames = new ArrayList<>();
      for (ConnectionHandler ch : connections) {
        userNames.add(ch.username);
      }
      try {
        Thread.sleep(150);
      } catch (InterruptedException e) {
        // ignore
      }
      broadcast(new UpdatePlayersLobbyMessage(userNames));
      broadcast(new PlayersReadyUpdateMessage(isGameReady()));
    }

    /**
     * adds a player to this ConnectionHandler.
     *
     * @param msg - the message to handle
     * @author floribau
     */
    private void handlePlayerAddedToClientMessage(Message msg) {
      PlayerAddedToClientMessage playerAddedToClientMessage = (PlayerAddedToClientMessage) msg;
      this.player = playerAddedToClientMessage.getPlayer();
    }

    /**
     * This method handles adding a game controller to a player. It sets the addedGameController
     * boolean to true and checks if all game controllers have been added. If so, it determines the
     * beginner.
     *
     * @author floribau
     */
    private void handleGameControllerAddedToPlayer() {
      addedGameController = true;
      if (checkAllGameControllersAdded()) {
        determineBeginner();
      }
    }

    /**
     * This method handles the next phase message. It changes the game phase to the next phase and
     * broadcasts the updated game state to all clients.
     *
     * @author floribau
     */
    private void handleNextPhaseMessage() {
      switch (gameState.getGamePhase()) {
        case ATTACK_PHASE -> gameState.setGamePhase(GamePhase.MOVE_TROOPS_PHASE);
        case MOVE_TROOPS_PHASE -> {
          gameState.setGamePhase(GamePhase.TRADE_CARDS_PHASE);
          gameState.nextPlayer();
        }
        case PLACE_TROOP_PHASE -> {
          gameState.setGamePhase(GamePhase.ATTACK_PHASE);
          gameState.setFirstAttack();
        }
      }
      UpdatedGameStateMessage newGameState = new UpdatedGameStateMessage(
          gameState.cloneGameState());
      broadcast(newGameState);
    }

    /**
     * sets userReady to true for this ConnectionHandler, then checks if all users pressed ready and
     * broadcasts the result to all clients.
     *
     * @author floribau
     */
    private void handlePlayerReadyMessage() {
      this.userReady = true;
      broadcast(new PlayersReadyUpdateMessage(isGameReady()));
    }

    /**
     * sets userReady to false for this ConnectionHandler, then checks if all users pressed ready
     * and broadcasts the result to all clients.
     *
     * @author floribau
     */
    private void handleSetAiPlayerOnReadyMessage() {
      handlePlayerReadyMessage();
      handleGameControllerAddedToPlayer();
      handlePressedContinueAfterDetermineBeginnerMessage();
    }

    /**
     * This class is used to store the credentials of a server. It is used to store the IP address,
     * port and elo of a server. It also has a static security code that is used to verify that the
     * server is a valid server.
     *
     * @author lkuech
     */
    private void handlePressedContinueAfterDetermineBeginnerMessage() {
      this.pressedContinueAfterDetermineBeginner = true;
      if (checkAllPressedContinueAfterDetermineBeginner()) {
        ArrayList<Player> players = new ArrayList<>();
        for (ConnectionHandler ch : connections) {
          players.add(ch.player);
        }
        Player currentPlayer = players.get(0);
        gameState = new GameState(currentPlayer.getUserKey(), players);
        broadcast(new UpdatedGameStateMessage(gameState.cloneGameState()));
      }
    }

    /**
     * This method handles the message that is sent by the client when the user presses the continue
     * button after the distribution phase. It sets the boolean pressedContinueAfterDistribution to
     * true and checks if all players have pressed the continue button. If so, it sets the gamePhase
     * to ATTACK_PHASE and sends an UpdatedGameStateMessage to all clients.
     *
     * @author floribau, lkuech
     */
    private void handleGameStateReceivedMessage() {
      if (isAi) {
        try {
          Thread.sleep(100);
        } catch (InterruptedException e) {
          // ignore
        }
      }
      gameStateReceived = true;
      if (checkAllReceivedGameState()) {
        ConnectionHandler currentConnectionHandler = getConnectionHandlerByUserKey(
            gameState.getCurrentPlayerKey());
        for (ConnectionHandler ch : connections) {
          ch.gameStateReceived = false;
        }
        switch (gameState.getGamePhase()) {
          case DISTRIBUTION_PHASE -> {
            currentConnectionHandler.sendMessage(new DistributionRequest());
          }
          case TRADE_CARDS_PHASE -> {
            currentConnectionHandler.sendMessage(new TradeCardsRequest());
          }
          case PLACE_TROOP_PHASE -> {
            currentConnectionHandler.sendMessage(new PlaceTroopsRequest());
          }
          case ATTACK_PHASE -> {
            currentConnectionHandler.sendMessage(new AttackMoveRequest());
          }
          case MOVE_TROOPS_PHASE -> {
            currentConnectionHandler.sendMessage(new MoveTroopsRequest());
          }
          default -> {
            //ignore
          }
        }
      }
    }

    /**
     * sends a message to the Client connected to this ConnectionHandler.
     *
     * @param msg - the message to send
     * @author floribau
     */
    public void sendMessage(Message msg) {
      try {
        synchronized (out) {
          out.writeObject(msg);
          out.flush();
        }
      } catch (IOException e) {
        // ignore
        this.shutdown();
      } catch (Exception e) {
        //ignore
      }
    }

    /**
     * shuts down this ConnectionHandler: closes all streams and connections.
     *
     * @author floribau
     */
    public void shutdown() {

      try {
        ArrayList<String> usernames = new ArrayList<>();
        connections.remove(this);
        countConnections--;
        for (ConnectionHandler ch : connections) {
          usernames.add(ch.username);
        }

        try {
          broadcast(new UpdatePlayersLobbyMessage(usernames));
          broadcast(new PlayersReadyUpdateMessage(isGameReady()));
          if (gameState != null) {
            broadcast(new ServerShutdownMessage());
            gameState = null;
          }
          if (shutdown) {
            broadcast(new ServerShutdownMessage());
          }
        } catch (Exception e) {
          //ignore
        }
        in.close();
        out.close();

        if (client != null && !client.isClosed()) {
          client.close();
          client = null;
        }
      } catch (IOException e) {
        //ignore
      }
    }
  }
}

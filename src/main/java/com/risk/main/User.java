package com.risk.main;

import com.risk.game.GameState;
import com.risk.gui.ChatController;
import com.risk.gui.GameController;
import com.risk.network.Client;
import com.risk.network.Server;
import com.risk.network.messages.GameControllerAddedToPlayerMessage;
import com.risk.objects.Player;
import com.risk.util.exceptions.UserAlreadyContainsClientException;

/**
 * This class represents a user. It contains the username, the userkey, the client, the server, the
 * elo and the chatController. Most importantly it contains the static user object that is used to
 * access the user from anywhere in the program. This is mostly used to access the user from the
 * GameController class.
 *
 * <p>The user object is a singleton. This means that there can only be one instance of the user
 * object at a time. This is done to prevent multiple users from being created.
 *
 * <p>The user object is created when the program starts. It is created in the main method of the
 * program. The user object is created with the default constructor.
 *
 * @author lkuech
 */
public class User {

  private static final User user = new User();
  private final String userKey = generateKey();
  private String username;
  private Client client;
  private GameState gameState;
  private GameController guiController;
  private boolean loggedIn = false;
  private int elo;
  private Server server = null;
  private ChatController chatController;

  /**
   * This method returns a userKey. The userkey is a random String that is used to identify the user
   * since the username can be duplicated.
   *
   * @return String The userKey of the user as a String.
   * @author floribau
   */
  public static String generateKey() {
    String key = "";
    for (int i = 0; i < 8; i++) {
      char c = (char) ((int) (Math.random() * (122 - 97 + 1)) + 97);
      key += c;
    }
    return key;
  }

  /**
   * This method returns the static user object. It is use as a data storage for information about
   * the user later needed in the game.
   *
   * @return the static user object.
   * @author lkuech
   */
  public static User getUser() {
    return user;
  }

  /**
   * Returns the userKey of the user instance.
   *
   * @return userKey of the user
   * @author floribau
   */
  public String getUserKey() {
    return this.userKey;
  }

  /**
   * This method returns the username of the user.
   *
   * @return the username of the user as a String.
   * @author floribau
   */
  public String getUsername() {
    return this.username;
  }

  /**
   * This method sets the username of the user.
   *
   * @param username - The username of the user as a String.
   * @author floribau
   */
  public void setUsername(String username) {
    this.username = username;
    if (!(this.client == null)) {
      // TODO implement method for client
      // client.setUsername(username);
    }
  }

  /**
   * This method is used to add a client to the user. It calles the constructor of the client class
   * and passes the IP address and port of a server and a userKey and username to it.
   *
   * @param ipAddress The IP address of the server.
   * @param port      The port of the server.
   * @throws UserAlreadyContainsClientException thrown if the user already has a client.
   * @author floribau
   */
  public void addClient(String ipAddress, int port) throws UserAlreadyContainsClientException {
    if (this.client == null) {
      this.client = new Client(ipAddress, port, userKey, username);
    } else {
      this.client = null;
      throw new UserAlreadyContainsClientException();
    }
  }

  /**
   * This method is used to get the client of the user.
   *
   * @return The client of the user as a Client object.
   * @author floribau
   */
  public Client getClient() {
    return this.client;
  }

  /**
   * This method is used to set the client of the user.
   *
   * @param client The client of the user as a Client object.
   * @author floribau
   */
  public void setClient(Client client) {
    this.client = client;
  }

  /**
   * This method is used to get the player of the user.
   *
   * @return The player of the user as a Player object. If the user has no client, it returns null.
   * @author floribau
   */
  public Player getPlayer() {
    if (this.client == null) {
      return null;
    } else {
      return this.client.getPlayer();
    }
  }

  /**
   * This method is used to add a chat controller to the user.
   *
   * @param chatController The chat controller of the user as a ChatController object.
   * @author lkuech
   */
  public void addChatController(ChatController chatController) {
    this.chatController = chatController;
  }

  /**
   * This method is used to get the chat controller of the user.
   *
   * @return The chat controller of the user as a ChatController object. If the user has no client,
   * it returns null.
   * @author lkuech
   */
  public ChatController getChatController() {
    return chatController;
  }

  /**
   * This method is used to remove the client of the user.
   *
   * @author floribau
   */
  public void removeClient() {
    this.client = null;
  }

  /**
   * This method returns a boolean that indicates if the user is logged in or not.
   *
   * @return a boolean that indicates if the user is logged in or not.
   * @author lkuech
   */
  public boolean isLoggedIn() {
    return loggedIn;
  }

  /**
   * This method is used to get the user key of the user.
   *
   * @param loggedIn a boolean that indicates if the user is logged in or not.
   * @author lkuech
   */
  public void setLoggedIn(boolean loggedIn) {
    this.loggedIn = loggedIn;
  }

  /**
   * This method returns the elo of the users account.
   *
   * @return the elo of the users account.
   * @author lkuech
   */
  public int getElo() {
    return elo;
  }

  /**
   * This method sets the elo of the users account.
   *
   * @param elo the elo of the users account.
   * @author lkuech
   */
  public void setElo(int elo) {
    this.elo = elo;
  }

  /**
   * This method returns the server of the user. If the user is not hosting a server, it returns
   * null.
   *
   * @return the server of the user.
   * @author lkuech
   */
  public Server getServer() {
    return server;
  }

  /**
   * This method sets the server of the user. It is used to store the server the user is hosting.
   *
   * @param server the server of the user.
   * @author lkuech
   */
  public void setServer(Server server) {
    this.server = server;
  }

  public GameState getGameState() {
    return gameState;
  }

  public void setGameState(GameState gameState) {
    this.gameState = gameState;
  }

  public GameController getGameController() {
    return guiController;
  }

  public void addGameController(GameController gameController) {
    this.guiController = gameController;
    client.sendMessage(new GameControllerAddedToPlayerMessage());
  }
}

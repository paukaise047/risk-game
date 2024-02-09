package com.risk.network;

import com.risk.main.User;
import com.risk.network.messages.ChatMessage;
import com.risk.network.messages.HeartBeatMessage;
import com.risk.objects.Player;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

/**
 * A class that represents the chat client in a network connection. It handles sending and receiving
 * chat messages to and from the chat server.
 *
 * @author lgreiner + floribau
 */
public class ChatClient extends Thread {

  private final String chatServerIP;
  private final int chatServerPort;
  private final String userKey;
  private final String username;
  private final User user = User.getUser();
  private final ArrayList<String[]> chatMessageHistory = new ArrayList<>();
  private final String[] chatNachricht =
      new String[3]; // [0] = message, [1] = username, [2] = userkey
  private Socket chatClientSocket;
  private boolean running;
  private ObjectOutputStream objectOutputStream;
  private ObjectInputStream objectInputStream;
  private Player player;
  private Message message;

  /**
   * Constructs a new ChatClient.
   *
   * @param chatServerIP the IP address of the chat server
   * @param chatServerPort the port number of the chat server
   * @param userKey the user key
   * @param username the username
   * @author lgreiner
   */
  public ChatClient(String chatServerIP, int chatServerPort, String userKey, String username) {
    this.chatServerIP = chatServerIP;
    this.chatServerPort = chatServerPort;
    this.userKey = userKey;
    this.username = username;
  }

  /**
   * The run method for the ChatClient thread. It sets up the socket connection and streams, and
   * continually reads incoming messages from the server.
   *
   * @author lgreiner
   */
  @Override
  public void run() {
    try {
      chatClientSocket = new Socket(chatServerIP, chatServerPort);
      objectInputStream = new ObjectInputStream(chatClientSocket.getInputStream());
      objectOutputStream = new ObjectOutputStream(chatClientSocket.getOutputStream());
      this.running = true;

      while (running) {
        if ((message = (Message) objectInputStream.readObject()) != null) {
          handleMessage(message);
        }
        Thread.sleep(100);
      }
    } catch (Exception e) {
      if (user.getClient() != null) {
        user.getClient().getWaitInLobbyController().showDisconnectPane();
      }
      // e.printStackTrace();
      shutdown();
    }
  }

  /**
   * Sends a message to the server.
   *
   * @param message
   * @author lgreiner
   */
  public void sendMessage(Message message) {
    try {
      objectOutputStream.writeObject(message);
      objectOutputStream.flush();
    } catch (Exception e) {
      e.printStackTrace();
      shutdown();
    }
  }

  /**
   * Adds a chat message to the chat history.
   *
   * @param message the message
   * @param userName the name of the user who sent the message
   * @author lgreiner
   */
  public void addChatMessageToHistory(String message, String userName) {
    String[] temp = new String[2];
    temp[0] = message;
    temp[1] = userName;
    chatMessageHistory.add(temp);
    if (user.getChatController() != null) {
      user.getChatController().refreshChat();
    }
  }

  /**
   * Handles incoming messages from the server with switch case and calls specific handle-Method.
   *
   * @param message
   * @author lgreiner
   */
  private void handleMessage(Message message) {
    switch (message.getType()) {
      case CHAT_MESSAGE -> handle_Chat_Message(message);
      case HEART_BEAT_MESSAGE -> handleHeartBeatMessage(message);
      case SERVER_SHUTDOWN_MESSAGE -> handleServerShutdownMessage();
      default -> {
        // ignore
      }
    }
  }

  /**
   * Adds the incoming Chat Message to the chatMessageHistory by calling addChatMessageToHistory
   * Method.
   *
   * @param message chat message
   * @author lgreiner
   */
  private void handle_Chat_Message(Message message) {
    ChatMessage chatmsg = (ChatMessage) message;
    String[] temp = new String[3];
    temp[0] = chatmsg.getMessageContent();
    temp[1] = chatmsg.getAuthor();
    temp[2] = String.valueOf(chatmsg.getTimestamp());
    addChatMessageToHistory(temp[0], temp[1]);
  }
  /**
   * Shuts down the ChatClient.
   *
   * @author floribau
   */
  private void handleServerShutdownMessage() {
    this.shutdown();
  }

  /**
   * Sends a HeartBeatMessage to the server.
   *
   * @param message
   * @author floribau
   */
  private void handleHeartBeatMessage(Message message) {
    sendMessage(new HeartBeatMessage());
  }

  /**
   * Returns the chat history.
   *
   * @return chatMessageHistory
   * @author lgreiner
   */
  public ArrayList<String[]> getChatMessageHistory() {
    //
    return this.chatMessageHistory;
  }

  /**
   * Returns the username of Person who runs the ChatClient.
   *
   * @return username
   * @author lgreiner
   */
  public String getUsername() {
    return username;
  }

  /**
   * Returns the userkey of Person who runs the ChatClient.
   *
   * @return username
   * @author lgreiner
   */
  public String getUserKey() {
    return userKey;
  }

  /**
   * Shuts down the client by closing the socket and streams.
   *
   * @author lgreiner
   */
  public void shutdown() {
    if (user.getClient() != null) {
      user.getClient().shutdown();
    }
    running = false;
    try {
      if (!this.chatClientSocket.isClosed()) {
        this.chatClientSocket.close();
      }
      if (this.objectInputStream != null) {
        this.objectInputStream.close();
        this.objectInputStream = null;
      }
      if (this.objectOutputStream != null) {
        this.objectOutputStream.close();
        this.objectOutputStream = null;
      }
    } catch (Exception e) {
      //ignore
    }
    user.setClient(null);
    user.setServer(null);
  }

  /**
   * Returns the running state of the client.
   *
   * @return running
   * @author lkuech
   */
  public void stopRunning() {
    this.running = false;
  }
}

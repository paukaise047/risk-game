package com.risk.network;

import com.risk.main.User;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * A class that represents the chat server in a network connection. It handles accepting new client
 * connections and broadcasting chat messages.
 *
 * @author lgreiner, floribau
 */

public class ChatServer extends Thread {

  private final int port;
  private final ArrayList<ChatConnectionHandler> chatConnections;
  private ServerSocket serverSocket;
  private boolean running;
  private ExecutorService pool;

  /**
   * Constructs a new ChatServer.
   *
   * @param gameServerPort - the port of the game server
   * @author lgreiner
   */
  public ChatServer(int gameServerPort) {
    chatConnections = new ArrayList<>();
    this.port = gameServerPort + 1;
    this.running = true;
  }

  /**
   * The run method for the ChatServer thread. It sets up the server socket and listens for incoming
   * client connections.
   *
   * @author lgreiner
   */
  @Override
  public void run() {
    try {
      serverSocket = new ServerSocket(port);
      pool = Executors.newCachedThreadPool();
      while (running) {
        Socket clientSocket = serverSocket.accept();
        ChatConnectionHandler chatConnectionHandler =
            new ChatConnectionHandler(clientSocket);
        chatConnections.add(chatConnectionHandler);

        pool.execute(chatConnectionHandler);
      }

    } catch (Exception e) {
      shutdown();
    }
  }

  /**
   * Shuts down the server by closing all connection handlers and the server socket.
   *
   * @author lgreiner
   */
  public void shutdown() {
    try {
      this.running = false;
      for (ChatConnectionHandler chatConnectionHandler : chatConnections) {
        chatConnectionHandler.shutdown();
      }
      if (serverSocket != null && !serverSocket.isClosed()) {
        serverSocket.close();
      }
    } catch (Exception e) {
      // ignore
    }
  }

  /**
   * Broadcasts a message to all connected clients.
   *
   * @param message - the message to be broadcast
   * @author floribau
   */
  public void broadcast(Message message) {
    try {
      for (ChatConnectionHandler chatConnectionHandler : chatConnections) {
        chatConnectionHandler.sendMessage(message);
      }
    } catch (Exception e) {
      shutdown();
    }
  }

  /**
   * Inner class that handles the chat connection with a client For each incoming Request one
   * instance of this class gets created.
   *
   * @author lgreiner
   */
  class ChatConnectionHandler implements Runnable {

    private final Socket clientSocket;
    private ObjectInputStream objectInputStream;
    private ObjectOutputStream objectOutputStream;
    private Message message;

    /**
     * Constructs a new ChatConnectionHandler.
     *
     * @param clientSocket - the socket of the client that is connected to the server
     * @author lgreiner
     */
    public ChatConnectionHandler(Socket clientSocket) {
      this.clientSocket = clientSocket;
    }

    /**
     * The run method for the ChatConnectionHandler. It sets up the input and output streams and
     * continually reads incoming messages from the client.
     *
     * @author lgreiner
     */
    @Override
    public void run() {
      try {
        objectOutputStream = new ObjectOutputStream(clientSocket.getOutputStream());
        objectInputStream = new ObjectInputStream(clientSocket.getInputStream());
        while (running) {
          if ((message = (Message) objectInputStream.readObject()) != null) {
            handleMessage(message);
          }
          Thread.sleep(100);
        }
      } catch (Exception e) {
        shutdown();
      }

    }

    /**
     * Shuts down the connection handler by closing the streams and socket.
     *
     * @author lgreiner
     */
    public void shutdown() {
      try {
        objectInputStream.close();
        objectOutputStream.close();
        if (clientSocket != null && !clientSocket.isClosed()) {
          clientSocket.close();
        }
        User.getUser().setClient(null);
        User.getUser().setServer(null);
      } catch (Exception e) {
        // ignore
      }
    }

    /**
     * Handles incoming messages from the client and calls the matching handler method.
     *
     * @param message - the incoming message from the client
     * @author lgreiner
     */
    public void handleMessage(Message message) {
      if (Objects.requireNonNull(message.getType()) ==
          MessageType.CHAT_MESSAGE) {
        handleChatMessage(message);
      }
    }

    /**
     * Triggers broadcast Method when new Chat Message arrives.
     *
     * @param message - the incoming message from the client
     * @author lgreiner
     */
    public void handleChatMessage(Message message) {
      broadcast(message);
    }


    /**
     * Sends a message to the client.
     *
     * @param message - the message to be sent to the client
     * @author lgreiner
     */
    public void sendMessage(Message message) {
      try {
        objectOutputStream.writeObject(message);
        objectOutputStream.flush();
      } catch (Exception e) {
        shutdown();
      }
    }
  }

}
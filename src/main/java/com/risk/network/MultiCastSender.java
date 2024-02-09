package com.risk.network;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

/**
 * Represents a multicast sender that sends server credentials to a multicast group Implements
 * Runnable interface to be executed in a separate thread.
 *
 * @author lkuech
 */
public class MultiCastSender implements Runnable {

  private final ServerCredentials serverCredentials;
  private final String group = "224.0.0.10";
  protected MulticastSocket multicastSocket;
  private boolean running;

  /**
   * Initializes a new instance of the MultiCastSender class.
   *
   * @param serverCredentials the server credentials to be sent
   * @throws IOException if an I/O error occurs
   * @author lkuech
   */
  public MultiCastSender(ServerCredentials serverCredentials) throws IOException {
    this.multicastSocket = new MulticastSocket();
    this.serverCredentials = serverCredentials;
    this.running = true;
  }

  /**
   * main method
   *
   * @param args command-line arguments (not used).
   * @throws IOException if an I/O error occurs
   * @author lkuech
   */
  public static void main(String[] args) throws IOException {
    Thread thread = new Thread(new MultiCastSender(new ServerCredentials("224.0.0.10", 4446, 3)));
    thread.start();
  }

  /**
   * Sends a datagram packet containing the server credentials to the multicast group.
   *
   * @throws IOException if an I/O error occurs
   * @author lkuech
   */
  public void sendMessage() throws IOException {
    String message = serverCredentials.toString();
    DatagramPacket dp = new DatagramPacket(message.getBytes(), message.length(),
        InetAddress.getByName(group), 4446);
    multicastSocket.send(dp);

  }

  /**
   * Continuously sends the server credentials while the sender is running.
   *
   * @author lkuech
   */
  @Override
  public void run() {
    while (running) {
      try {
        sendMessage();
        Thread.sleep(10);
      } catch (IOException | InterruptedException e) {
        throw new RuntimeException(e);
      }
    }
  }

  /**
   * Returns the running status of the sender.
   *
   * @author lkuech
   */
  public boolean isRunning() {
    return running;
  }

  /**
   * Sets the running status of the sender.
   *
   * @param running the new running status
   * @author lkuech
   */
  public void setRunning(boolean running) {
    this.running = running;
  }

}

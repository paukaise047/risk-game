package com.risk.network;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.MulticastSocket;
import java.net.NetworkInterface;
import java.util.ArrayList;


/**
 * Represents a multicast receiver that listens to a multicast group for server credentials
 * Implements Runnable interface to be executed in a separate thread.
 *
 * @author lkuech
 */
public class MultiCastReceiver implements Runnable {

  private final byte[] buf;
  private final ArrayList<ServerCredentials> allLobbies = new ArrayList<>();
  protected MulticastSocket multicastSocket;
  protected boolean running;

  /**
   * Initializes a new instance of the MultiCastReceiver class.
   *
   * @throws IOException if an I/O error occurs
   * @author lkuech
   */
  public MultiCastReceiver() throws IOException {

    this.buf = new byte[1024];
    this.multicastSocket = new MulticastSocket(4446);

    NetworkInterface netIf = NetworkInterface.getByName("bge0");
    InetSocketAddress inetSocketAddress = new InetSocketAddress(InetAddress.getByName("224.0.0.10"),
        4446);

    multicastSocket.joinGroup(inetSocketAddress, netIf);

    this.running = true;

  }

  /**
   * main method.
   *
   * @param args command-line arguments (not used)
   * @throws IOException          if an I/O error occurs
   * @throws InterruptedException if any thread has interrupted the current thread
   * @author lkuech
   */
  public static void main(String[] args) throws IOException, InterruptedException {
    MultiCastReceiver mr = new MultiCastReceiver();
    Thread thread = new Thread(mr);
    thread.start();
    Thread.sleep(10000);
    mr.setRunningFalse();
    thread.interrupt();
  }

  /**
   * Receives a datagram packet and returns the data as a string.
   *
   * @return string containing the received data
   * @throws IOException if an I/O error occurs
   * @author lkuech
   */
  public String receive() throws IOException {
    DatagramPacket dp = new DatagramPacket(buf, 1024);
    this.multicastSocket.receive(dp);
    return new String(dp.getData(), 0, dp.getLength());
  }

  /**
   * Continuously receives and processes server credentials while the receiver is running.
   *
   * @author lkuech
   */
  @Override
  public void run() {
    while (running) {
      try {

        String lobby = receive();
        String[] credentials = lobby.split(":");
        if (credentials[0].equals(
            ServerCredentials.getSecurityCode()) && !checkIfKnown(credentials[1], credentials[2],
            credentials[3])) {
          this.allLobbies.add(
              new ServerCredentials(credentials[1], Integer.valueOf(credentials[2]),
                  Integer.valueOf(credentials[3])));
        }

        Thread.sleep(10);
      } catch (IOException e) {
        throw new RuntimeException(e);
      } catch (InterruptedException e) {
        // ignore
      }
    }
  }

  /**
   * Checks if a server with the given IP and port is already known.
   *
   * @param ip   the IP of the server
   * @param port the port of the server
   * @param elo  the ELO rating of the server
   * @return true if the server is already known, false otherwise
   * @author lkuech
   */
  public boolean checkIfKnown(String ip, String port, String elo) {
    boolean known = false;
    for (ServerCredentials sc : this.allLobbies) {
      boolean ipKnown = sc.getIp().equals(ip);
      if (ipKnown && String.valueOf(sc.getPort()).equals(port)) {
        known = true;
        updateElo(sc, Integer.valueOf(elo));
        break;
      }
    }
    return known;
  }

  /**
   * Stops the receiver from running.
   *
   * @author lkuech
   */
  public void setRunningFalse() {
    this.running = false;
  }

  /**
   * Gets the list of all known lobbies.
   *
   * @return the list of all known lobbies
   * @author lkuech
   */
  public ArrayList<ServerCredentials> getAllLobbies() {
    return allLobbies;
  }

  /**
   * Updates the ELO rating of a server.
   *
   * @param sc  the server credentials
   * @param elo the new ELO rating
   * @author lkuech
   */
  public void updateElo(ServerCredentials sc, int elo) {
    sc.setElo(elo);
  }
}

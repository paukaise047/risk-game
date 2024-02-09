package com.risk.network;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * This class is used to store the credentials of a server. It is used to store the IP address, port
 * and elo of a server. It also has a static security code that is used to verify that the server is
 * a valid server.
 *
 * @author lkuech
 */
public class ServerCredentials {

  private static final String securityCode = "15052023";
  private final InetAddress ip;
  private final int port;
  private int elo;

  /**
   * This constructor is used to create a ServerCredentials object.
   *
   * @param ip   The IP address of the server.
   * @param port The port of the server.
   * @param elo  The elo of the server.
   * @throws UnknownHostException This exception is thrown if the IP address is invalid.
   * @author lkuech
   */
  public ServerCredentials(String ip, int port, int elo) throws UnknownHostException {
    this.ip = InetAddress.getByName(ip);
    this.port = port;
    this.elo = elo;
  }

  /**
   * This method is used to get the security code of the server.
   *
   * @return The security code of the server as a String.
   * @author lkuech
   */
  public static String getSecurityCode() {
    return securityCode;
  }

  /**
   * This method is used to create a ServerCredentials object from a String. This is used to convert
   * the String received from the server buttons to a ServerCredentials object.
   *
   * @param credentialsString The String to be converted to a ServerCredentials object.
   * @return The ServerCredentials object created from the String.
   * @author lkuech
   */
  public static ServerCredentials credentialsFromString(String credentialsString) {
    String[] credentials = credentialsString.split(":");
    try {
      return new ServerCredentials(credentials[0].trim(), Integer.parseInt(credentials[1].trim()),
          Integer.parseInt(credentials[2].trim()));
    } catch (UnknownHostException e) {
      //ignore
    }
    return null;
  }

  /**
   * This method is used to get the IP address of the server.
   *
   * @return The IP address of the server as a String.
   * @author lkuech
   */
  public String getIp() {
    return ip.toString().substring(1);
  }

  /**
   * This method is used to get the port of the server.
   *
   * @return The port of the server as an int.
   * @author lkuech
   */
  public int getPort() {
    return port;
  }

  /**
   * This method converts the ServerCredentials object to a String. This is used to send the
   * ServerCredentials object over the network.
   *
   * @return The ServerCredentials object as a String. The format is: securityCode:ip:port:elo
   * @author lkuech
   */
  @Override
  public String toString() {
    return securityCode + ":" + ip.toString().substring(1) + ":" + port + ":" + elo;
  }

  /**
   * This method is used to get the elo of the server.
   *
   * @return The elo of the server as an int. The elo is the skill level of the server. The higher
   * the elo, the better the server is at the game. The elo is used to match players with servers of
   * similar skill level. The elo is calculated by the server and is based on the average elo of the
   * players elo on the server.
   * @author lkuech
   */
  public int getElo() {
    return elo;
  }

  /**
   * This method is used to set the elo of the server.
   *
   * @param elo The elo of the server as an int.
   * @author lkuech
   */
  public void setElo(int elo) {
    this.elo = elo;
  }

}

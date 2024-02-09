package com.risk.network;

import static org.junit.jupiter.api.Assertions.fail;

import com.risk.network.messages.WelcomeMessage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests the server client communication.
 *
 * @author floribau
 */
class ServerClientTest {

  private Server testServer;
  private int testServerPort = 9999;
  private String testServerIp = "localhost";
  private String testUsername = "testUsername";
  private String testUserKey = "testUserKey";

  /**
   * Sets up a new server.
   *
   * @author floribau
   */
  @BeforeEach
  public void setUp() {
    testServer = new Server();
    Thread serverThread = new Thread(testServer);
    serverThread.start();
  }

  /**
   * Calls the server's shutdown method.
   *
   * @author floribau
   */
  @AfterEach
  public void tearDown(){
    testServer.shutdown();
  }

  /**
   * Connects a new Client and send messages between Server and Client.
   *
   * @author floribau
   */
  @Test
  public void testClientServer() {
    try {
      Client testClient = new Client(testServerIp, testServerPort, testUserKey, testUsername);
      testClient.start();
      try {
        Thread.sleep(2000);
      } catch (InterruptedException e) {
        // ignore
      }
      WelcomeMessage message = new WelcomeMessage();
      testClient.sendMessage(message);
      try {
        Thread.sleep(1000);
      } catch (InterruptedException e) {
        // ignore
      }
      testServer.broadcast(new WelcomeMessage());
    } catch (Exception e) {
      fail();
    }
  }

}
package com.risk.network;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;


import com.risk.network.messages.ChatMessage;
import java.time.LocalTime;
import java.util.ArrayList;
import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.Test;

/**
 * Tests the communication between ChatServer and ChatClient.
 *
 * @author lgreiner
 */
class ChatServerClientTest {

  private final int testServerPort = 9999;
  private ChatServer testChatServer;
  private ChatClient testChatClient;
  private String testServerIp = "localhost";
  private String testUsername = "testUsername";
  private String testUserKey = "testUserKey";
  private String testMessageContent = "TestMessageContent";

  /**
   * Sets up a ChatServer.
   *
   * @author lgreiner
   */
  @Before
  public void setUp() {
    testChatServer = new ChatServer(testServerPort - 1);
    testChatServer.start();
  }

  /**
   * Calls the ChatServer's shutdown method.
   *
   * @author lgreiner
   */
  @After
  public void tearDown() {
    testChatServer.shutdown();
  }

  /**
   * Connects a new ChatClient and sends a ChatMessage from the Client to the Server which is then
   * broadcast to all clients.
   *
   * @author floribau
   */
  @Test
  public void testChatServerClient() {
    try {
      setUp();
      testChatClient = new ChatClient(testServerIp, testServerPort, testUserKey, testUsername);
      testChatClient.start();
      try {
        Thread.sleep(1000);
      } catch (InterruptedException e) {
        // ignore
      }

      ChatMessage testMessage = new ChatMessage(testMessageContent, testUsername, LocalTime.now());
      testChatClient.sendMessage(testMessage);
      try {
        Thread.sleep(1000);
      } catch (InterruptedException e) {
        // ignore
      }
      ArrayList<String[]> temp = this.testChatClient.getChatMessageHistory();
      String[] nachricht = new String[2];
      if (temp != null) {
        nachricht = temp.get(0);
      }
      assertEquals(nachricht[0], testMessageContent);
      testChatClient.shutdown();
      testChatServer.shutdown();
    } catch (Exception e) {
      fail();
    }
  }
}

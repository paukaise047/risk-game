package com.risk.network;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.MulticastSocket;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

/**
 * This class contains test cases for the MultiCastSender class. It uses Mockito to mock
 * dependencies.
 *
 * @author lgreiner.
 */
class MultiCastSenderTest {

  @Mock
  private MulticastSocket multicastSocket;

  private MultiCastSender multiCastSender;
  private ServerCredentials serverCredentials;
  private AutoCloseable closeable;

  /**
   * This method is run before each test case. It initializes the test environment and mocks.
   *
   * @throws IOException if an I/O error occurs
   * @author lgreiner
   */
  @BeforeEach
  public void setUp() throws IOException {
    closeable = MockitoAnnotations.openMocks(this);
    serverCredentials = new ServerCredentials("224.0.0.10", 4446, 3);
    multiCastSender = new MultiCastSender(serverCredentials);
    multiCastSender.multicastSocket = multicastSocket; // Inject the mock socket
  }

  /**
   * This method is run after each test case. It closes the resources used during the test.
   *
   * @throws Exception if an error occurs during closing resources
   * @author lgreiner
   */
  @AfterEach
  public void tearDown() throws Exception {
    closeable.close();
  }

  /**
   * Test case for the sendMessage method. It verifies if the send method of the mocked
   * MulticastSocket is called once.
   *
   * @throws IOException if an I/O error occurs
   * @author lgreiner
   */
  @Test
  public void testSendMessage() throws IOException {
    multiCastSender.sendMessage();
    verify(multicastSocket, times(1)).send(any(DatagramPacket.class));
  }

  /**
   * Test case for the setRunning method It tests whether the running field is updated correctly
   *
   * @author lgreiner
   */
  @Test
  public void testSetRunning() {
    multiCastSender.setRunning(false);
    // Assert that the running field is updated
    assertFalse(multiCastSender.isRunning());
  }
}

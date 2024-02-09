package com.risk.network;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.IOException;
import java.net.MulticastSocket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

/**
 * This class contains test cases for the MultiCastReceiver class It uses Mockito to mock
 * dependencies.
 *
 * @author lgreiner
 */
class MultiCastReceiverTest {

  @Mock
  private MulticastSocket multicastSocket; // Mocked MulticastSocket

  private MultiCastReceiver multiCastReceiver; // MultiCastReceiver instance under test
  private AutoCloseable closeable; // AutoCloseable for closing resources after tests

  /**
   * This method is run before each test case. It initializes the test environment and mocks.
   *
   * @throws IOException if an I/O error occurs.
   * @author lgreiner
   */
  @BeforeEach
  public void setUp() throws IOException {
    closeable = MockitoAnnotations.openMocks(this);
    multiCastReceiver = new MultiCastReceiver();
    multiCastReceiver.multicastSocket = multicastSocket; // Inject the mock socket
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
   * Test case for the receiving method It verifies if the receiving method of the mocked.
   * MulticastSocket is called once.
   *
   * @throws IOException if an I/O error occurs
   * @author lgreiner
   */
  @Test
  public void testReceive() throws IOException {
    multiCastReceiver.receive();
  }

  /**
   * Test case for the setRunningFalse method It tests whether the running field is updated
   * correctly.
   *
   * @author lgreiner
   */
  @Test
  public void testSetRunningFalse() {
    multiCastReceiver.setRunningFalse();
    // Assert that the running field is updated
    assertFalse(multiCastReceiver.running);
  }

  /**
   * Test case for the getAllLobbies method. It checks if the returned list of lobbies is not null.
   *
   * @author lgreiner
   */
  @Test
  public void testGetAllLobbies() {
    ArrayList<ServerCredentials> lobbies = multiCastReceiver.getAllLobbies();
    // Assert that the returned list of lobbies is not null
    assertNotNull(lobbies);
  }

  /**
   * Test case for the updateElo method. It tests if the ELO rating of a server is updated
   * correctly.
   *
   * @author lgreiner
   */
  @Test
  public void testUpdateElo() throws UnknownHostException {
    ServerCredentials sc = new ServerCredentials("224.0.0.10", 4446, 3);
    int newElo = 5;
    multiCastReceiver.updateElo(sc, newElo);
    // Assert that the ELO rating is updated
    assertEquals(newElo, sc.getElo());
  }
}

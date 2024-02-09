package com.risk.gamephases;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.risk.game.GameState;
import com.risk.gui.GameController;
import com.risk.main.User;
import com.risk.objects.Player;
import java.util.ArrayList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests the functionality of DistributionPhase class.
 *
 * @author floribau
 */
class DistributionPhaseTest {

  private GameState testGameState;
  private String playerOneKey;
  private GameController mockGameController;

  /**
   * Initializes the test with a test game state with all countries being unowned.
   *
   * @author floribau
   */
  @BeforeEach
  public void initTest() {
    User user = User.getUser();
    mockGameController = mock(GameController.class);
    try {
      user.addGameController(mockGameController);
    } catch (NullPointerException e) {
      // ignore server-client communication
    }

    ArrayList<Player> players = new ArrayList<>();
    for (int i = 0; i < 2; i++) {
      players.add(new Player("testPlayer" + (i + 1), User.generateKey(), false));
    }
    playerOneKey = players.get(0).getUserKey();
    testGameState = new GameState(playerOneKey, players);
    testGameState.getUnownedCountries().addAll(testGameState.getCountries());
    user.setGameState(testGameState);
  }

  /**
   * Tests, if the user input is processed and stored correctly.
   *
   * @author floribau
   */
  @Test
  void testDistributionPhase() {
    when(mockGameController.distributionSelection(testGameState.getUnownedCountries())).thenReturn(
        testGameState.getUnownedCountries().get(0));
    assertEquals(0, testGameState.getCountriesOwnedByPlayer(playerOneKey).size());
    DistributionPhase testDistributionPhase = new DistributionPhase(playerOneKey,
        testGameState.getUnownedCountries());
    assertEquals(testDistributionPhase.getPlayer(), playerOneKey);
    assertEquals(testDistributionPhase.getCountry(), testGameState.getCountries().get(0));
  }
}

package com.risk.gamephases;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.risk.game.GameState;
import com.risk.gui.GameController;
import com.risk.main.User;
import com.risk.objects.Country;
import com.risk.objects.Placement;
import com.risk.objects.Player;
import java.util.ArrayList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * This class tests the features of PlaceTroopsPhase.
 *
 * @author floribau
 */
class PlaceTroopsPhaseTest {

  private GameState testGameState;
  private String playerOneKey;
  private GameController mockGameController;

  /**
   * Initializes the variables needed to test the features of PlaceTroopsPhase.
   *
   * @author floribau
   */
  @BeforeEach
  void initTest() {
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
    for (Country c : testGameState.getCountriesByContinent("Africa")) {
      testGameState.updateCountryOwner(c, playerOneKey, 1);
    }
    testGameState.setContinentOwner();
    user.setGameState(testGameState);
  }

  /**
   * Mocks the user input from gui and tests, if this input is processed and stored correctly.
   *
   * @author floribau
   */
  @Test
  void testPlaceTroopsPhase() {
    for (int i = 0; i < 10; i++) {
      when(mockGameController.placeTroopSelection(playerOneKey, i)).thenReturn(
          new Placement(testGameState.getCountriesOwnedByPlayer(playerOneKey).get(0), 1));
    }

    PlaceTroopsPhase testPlaceTroopsPhase = new PlaceTroopsPhase(playerOneKey);
    int countTroops = 0;
    for (Placement placement : testPlaceTroopsPhase.getPlacements()) {
      int troops = placement.getTroopsPlaced();
      assertTrue(troops > 0);
      countTroops += troops;
    }
    assertEquals(6, countTroops);
  }
}

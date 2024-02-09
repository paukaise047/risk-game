package com.risk.gamephases;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.risk.game.GameState;
import com.risk.gui.GameController;
import com.risk.main.User;
import com.risk.objects.Move;
import com.risk.objects.Player;
import java.util.ArrayList;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests the functionality of MoveTroopsPhase class.
 *
 * @author floribau
 */
class MoveTroopsPhaseTest {

  private GameState testGameState;
  private String playerOneKey;
  private GameController mockGameController;

  /**
   * Sets up the variables needed for each test.
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
    testGameState.updateCountryOwner(testGameState.getCountries().get(0), playerOneKey, 5);
    testGameState.updateCountryOwner(testGameState.getCountries().get(0), playerOneKey, 2);

    user.setGameState(testGameState);
  }

  /**
   * Mocks the user input in the gui and tests, if this input is processed and stored correctly.
   *
   * @author floribau
   */
  @Test
  void testMoveTroopsPhase() {
    Move mockMove = new Move();
    mockMove.setFromCountry(testGameState.getCountries().get(0));
    mockMove.setToCountry(testGameState.getCountries().get(1));
    mockMove.setTroops(4);
    mockMove.setMoveValid(true);

    when(mockGameController.movingTroopsSelection(playerOneKey)).thenReturn(mockMove);
    MoveTroopsPhase testMoveTroopsPhase = new MoveTroopsPhase(playerOneKey);
    Assertions.assertEquals(testGameState.getCountries().get(0),
        testMoveTroopsPhase.getFromCountry());
    Assertions.assertEquals(testGameState.getCountries().get(1),
        testMoveTroopsPhase.getToCountry());
    Assertions.assertEquals(4, testMoveTroopsPhase.getTroopCount());
  }

}
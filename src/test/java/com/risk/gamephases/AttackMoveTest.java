package com.risk.gamephases;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.risk.game.GameState;
import com.risk.gui.GameController;
import com.risk.main.User;
import com.risk.objects.Attack;
import com.risk.objects.Player;
import java.util.ArrayList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests the features of AttackMove class.
 *
 * @author floribau
 */
class AttackMoveTest {

  private GameState testGameState;
  private String playerOneKey;
  private String playerTwoKey;
  private GameController mockGameController;

  /**
   * Sets up the variables necessary for each test.
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
    playerTwoKey = players.get(1).getUserKey();
    testGameState = new GameState(playerOneKey, players);
    testGameState.updateCountryOwner(testGameState.getCountries().get(0), playerOneKey, 5);
    testGameState.updateCountryOwner(testGameState.getCountries().get(1), playerTwoKey, 2);

    user.setGameState(testGameState);
  }

  /**
   * tests the functionality of the attackMove() method and all called methods.
   *
   * @author floribau
   */
  @Test
  void testAttackMove() {
    Attack mockAttack = new Attack();
    mockAttack.setAttackingCountry(testGameState.getCountries().get(0));
    mockAttack.setDefendingCountry(testGameState.getCountries().get(1));
    mockAttack.setTroopsUsed(mockAttack.getAttackingCountry().getTroops() - 1);
    mockAttack.setAttackValid(true);
    when(mockGameController.attackSelection(playerOneKey)).thenReturn(mockAttack);

    AttackMove testAttackMove = new AttackMove(playerOneKey);

    assertEquals(testGameState.getCountries().get(0), testAttackMove.getAttackingCountry());
    assertEquals(testGameState.getCountries().get(1), testAttackMove.getDefendingCountry());
    assertEquals(playerOneKey, testAttackMove.getAttackingPlayer());
    assertEquals(playerTwoKey, testAttackMove.getDefendingPlayer());
  }
}
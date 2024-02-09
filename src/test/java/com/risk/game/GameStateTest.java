package com.risk.game;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;

import com.risk.main.User;
import com.risk.objects.Country;
import com.risk.objects.Player;
import com.risk.objects.enums.GamePhase;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * This class tests the functionality of GameState util methods.
 *
 * @author floribau
 */
class GameStateTest {

  private static Player player1;
  private static Player player2;
  private static GameState gameState;

  /**
   * This method is used to initialize the test.
   *
   * @author hneumann
   */
  @BeforeEach
  void initTest() {
    List<Player> players = new ArrayList<>();
    player1 = new Player("test1", User.generateKey(), false);
    player2 = new Player("test1", User.generateKey(), false);

    players.add(player1);
    players.add(player2);
    gameState = new GameState(player1.getUserKey(), players);
    gameState.getUnownedCountries().addAll(gameState.getCountries());
  }

  /**
   * Tests updateCountryOwner() of GameState.
   *
   * @author floribau
   */
  @Test
  void testUpdateCountryOwner() {
    assertEquals(gameState.getUnownedCountries().size(), gameState.getCountries().size());
    for (int i = 0; i < 5; i++) {
      gameState.updateCountryOwner(gameState.getCountries().get(i), player1.getUserKey(), 5);
    }
    assertEquals(5, gameState.getCountriesOwnedByPlayer(player1.getUserKey()).size());
    assertEquals((42 - 5), gameState.getUnownedCountries().size());
  }

  /**
   * Tests updateCardOwner() of GameState.
   *
   * @author floribau
   */
  @Test
  void testUpdateCardOwner() {
    assertEquals(0, gameState.getCardsByPlayerKey(player1.getUserKey()).size());
    for (int i = 0; i < 5; i++) {
      gameState.updateCardOwner(i, player1.getUserKey());
    }
    assertEquals(5, gameState.getCardsByPlayerKey(player1.getUserKey()).size());
  }

  /**
   * Tests checkPlayerEliminated(), checkGameOver() and getWinner() of GameState.
   *
   * @author floribau
   */
  @Test
  void testChecksEliminationAndGameOver() {
    for (Country c : gameState.getCountries()) {
      gameState.updateCountryOwner(c, player1.getUserKey(), 1);
    }
    Assertions.assertTrue(gameState.checkPlayerEliminated(player2.getUserKey()));
    Assertions.assertTrue(gameState.checkGameOver());
    assertEquals(player1.getUserKey(), gameState.getWinner().getUserKey());
  }

  /**
   * Tests the nextPlayer() method of GameState.
   *
   * @author floribau
   */
  @Test
  void testNextPlayer() {
    gameState.nextPlayer();
    assertEquals(player2.getUserKey(), gameState.getCurrentPlayerKey());
    for (Country c : gameState.getCountries()) {
      gameState.updateCountryOwner(c, player1.getUserKey(), 1);
    }
    gameState.eliminatePlayer(player1.getUserKey(), player2.getUserKey());
    gameState.nextPlayer();
    assertEquals(player1.getUserKey(), gameState.getCurrentPlayerKey());
    gameState.nextPlayer();
    assertEquals(player1.getUserKey(), gameState.getCurrentPlayerKey());
  }

  /**
   * Tests updateGameStateTradeCardsPhase().
   *
   * @author floribau
   */
  @Test
  void testDrawCardForPlayer() {
    for (int i = 0; i < 5; i++) {
      gameState.drawCardForPlayer(player1.getUserKey());
    }
    assertEquals(5, gameState.getCardsByPlayerKey(player1.getUserKey()).size());
  }

  /**
   * Tests eliminatePLayer().
   *
   * @author floribau
   */
  @Test
  void eliminatePlayer() {
    String attackingPlayer = player1.getUserKey();
    String eliminatedPlayer = player2.getUserKey();
    assertEquals(0, gameState.getEliminatedPlayers().size());

    gameState.eliminatePlayer(attackingPlayer, eliminatedPlayer);
    Assertions.assertTrue(gameState.getEliminatedPlayers().contains(eliminatedPlayer));
    assertEquals(1, gameState.getEliminatedPlayers().size());
  }

  /**
   * This method tests if the cloneGameState method works correctly and provides deep copies for all
   * values. It also tests if the clone is not equal to the original.
   *
   * @author floribau
   */
  @Test
  void testCloneGameState() {
    gameState.setGamePhase(GamePhase.DISTRIBUTION_PHASE);
    gameState.setTroopsToDistribute(20);
    gameState.resetLastAttackMoveCountries();
    for (int i = 0; i < 10; i++) {
      gameState.updateCountryOwner(gameState.getCountries().get(i), player1.getUserKey(), 1);
      gameState.updateCountryOwner(gameState.getCountries().get(20 + i), player2.getUserKey(), 1);
    }
    for (int j = 0; j < 3; j++) {
      gameState.drawCardForPlayer(player1.getUserKey());
      gameState.drawCardForPlayer(player2.getUserKey());
    }

    GameState clonedGameState = gameState.cloneGameState();
    assertNotSame(gameState, clonedGameState);
    assertEquals(gameState.getGamePhase(), clonedGameState.getGamePhase());
    assertEquals(gameState.getTroopsToDistribute(), clonedGameState.getTroopsToDistribute());
    assertEquals(gameState.getTroopsFromTradingCards(),
        clonedGameState.getTroopsFromTradingCards());
    Assertions.assertNull(clonedGameState.getLastAttackingCountry());
    Assertions.assertNull(clonedGameState.getLastDefendingCountry());

    assertEquals(gameState.getCountries().size(), clonedGameState.getCountries().size());
    assertEquals(gameState.getUnownedCountries().size(),
        clonedGameState.getUnownedCountries().size());
    assertEquals(gameState.getPlayers().size(), clonedGameState.getPlayers().size());
    assertEquals(gameState.getCurrentPlayerKey(), clonedGameState.getCurrentPlayerKey());
    assertNotSame(gameState.getCountriesOwnedByPlayer(player1.getUserKey()),
        clonedGameState.getCountriesOwnedByPlayer(
            player1.getUserKey()));
    assertNotSame(gameState.getCountriesOwnedByPlayer(player2.getUserKey()),
        clonedGameState.getCountriesOwnedByPlayer(
            player2.getUserKey()));
    assertNotSame(gameState.getCardsByPlayerKey(player1.getUserKey()),
        clonedGameState.getCardsByPlayerKey(player1.getUserKey()));
    assertNotSame(gameState.getCardsByPlayerKey(player2.getUserKey()),
        clonedGameState.getCardsByPlayerKey(player2.getUserKey()));
  }
}

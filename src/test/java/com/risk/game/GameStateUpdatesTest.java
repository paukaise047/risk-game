package com.risk.game;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

import com.risk.main.User;
import com.risk.objects.Country;
import com.risk.objects.Placement;
import com.risk.objects.Player;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GameStateUpdatesTest {

  private static List<Player> players;
  private static Player player1;
  private static Player player2;
  private static GameState gameState;

  /**
   * This method is used to initialize the test.
   *
   * @author floribau
   */
  @BeforeEach
  void initTest() {
    players = new ArrayList<>();
    player1 = new Player("test1", User.generateKey(), false);
    player2 = new Player("test1", User.generateKey(), false);

    players.add(player1);
    players.add(player2);
    gameState = new GameState(player1.getUserKey(), players);
    gameState.getUnownedCountries().addAll(gameState.getCountries());
  }

  /**
   * Tests the updateGameStateDistributionPhase() method.
   *
   * @author floribau
   */
  @Test
  void testUpdateGameStateDistributionPhase() {
    Country c = gameState.getCountries().get(0);
    assertEquals(42, gameState.getUnownedCountries().size());
    assertTrue(gameState.getUnownedCountries().contains(c));
    gameState.updateGameStateDistributionPhase(player1.getUserKey(), c);
    assertEquals(1, c.getTroops());
    assertEquals(41, gameState.getUnownedCountries().size());
    assertFalse(gameState.getUnownedCountries().contains(c));
    gameState.updateGameStateDistributionPhase(player1.getUserKey(), c);
    assertEquals(2, c.getTroops());
    assertEquals(41, gameState.getUnownedCountries().size());
  }

  /**
   * Tests updateGameStateTradeCardsPhase().
   *
   * @author floribau
   */
  @Test
  void testUpdateGameStateTradeCardsPhase() {
    gameState.updateCardOwner(0, player1.getUserKey());
    gameState.updateCardOwner(1, player1.getUserKey());
    gameState.updateCardOwner(43, player1.getUserKey());
    Integer[] cardsTradedIds = {0, 1, 43};

    Country c = gameState.getCountries().get(0);
    gameState.updateCountryOwner(c, player1.getUserKey(), 1);
    gameState.updateCountryOwner(gameState.getCountries().get(1), player2.getUserKey(), 1);
    gameState.updateGameStateTradeCardsPhase(player1.getUserKey(), cardsTradedIds);

    assertEquals(3, c.getTroops());
    assertEquals(1, gameState.getCountries().get(1).getTroops());
    assertEquals(0, gameState.getCardsByPlayerKey(player1.getUserKey()).size());
  }

  /**
   * tests the moveTroops() method of GameState.
   *
   * @author floribau
   */
  @Test
  void testMoveTroops() {
    gameState.updateCountryOwner(gameState.getCountries().get(0), player1.getUserKey(), 5);
    gameState.updateCountryOwner(gameState.getCountries().get(1), player1.getUserKey(), 5);
    gameState.moveTroops(gameState.getCountries().get(0), gameState.getCountries().get(1));
    assertEquals(1, gameState.getCountries().get(0).getTroops());
    assertEquals(9, gameState.getCountries().get(1).getTroops());
  }

  /**
   * Tests the updateTroops() method of GameState.
   *
   * @author floribau
   */
  @Test
  void testUpdateTroops() {
    gameState.updateCountryOwner(gameState.getCountries().get(0), player1.getUserKey(), 1);
    gameState.updateTroops(gameState.getCountries().get(0), 5);
    assertEquals(5, gameState.getCountries().get(0).getTroops());
  }

  /**
   * Tests updateGameStatePlaceTroopPhase().
   */
  @Test
  void testUpdateGameStatePlaceTroopPhase(){
    ArrayList<Placement> placements = new ArrayList<>();
    for(int i=0; i<3; i++) {
      Country country = gameState.getCountries().get(i);
      gameState.updateCountryOwner(country, player1.getUserKey(), 1);
      placements.add(new Placement(country, 3));
    }
    gameState.updateGameStatePlaceTroopPhase(placements);
    for(Placement placement : placements){
      Country country = placement.getCountry();
      assertEquals(4, gameState.getCountryByName(country.getName()).getTroops());
    }
  }

  /**
   * Tests updateGameStateAttackMove() for the case attack won.
   *
   * @author floribau
   */
  @Test
  void testUpdateGameStateAttackMoveWon(){
    Country attackingCountry = gameState.getCountries().get(0);
    Country defendingCountry = gameState.getCountries().get(1);
    gameState.updateCountryOwner(attackingCountry, player1.getUserKey(), 5);
    gameState.updateCountryOwner(defendingCountry, player2.getUserKey(), 3);
    String defendingCountryOwner = attackingCountry.getOwner();
    int defendingCountryTroops = 2;
    int attackingCountryTroops = 1;

    gameState.updateGameStateAttackMove(defendingCountry, attackingCountry, defendingCountryOwner, defendingCountryTroops, attackingCountryTroops);
    assertEquals(attackingCountry.getName(), gameState.getLastAttackingCountry());
    assertEquals(defendingCountry.getName(), gameState.getLastDefendingCountry());
    assertEquals(attackingCountry.getOwner(), defendingCountry.getOwner());
    assertEquals(defendingCountryOwner, defendingCountry.getOwner());
    assertEquals(attackingCountryTroops, attackingCountry.getTroops());
    assertEquals(defendingCountryTroops, defendingCountry.getTroops());
  }

  /**
   * Tests updateGameStateAttackMove() for the case attack lost.
   *
   * @author floribau
   */
  @Test
  void testUpdateGameStateAttackMoveLost(){
    Country attackingCountry = gameState.getCountries().get(0);
    Country defendingCountry = gameState.getCountries().get(1);
    gameState.updateCountryOwner(attackingCountry, player1.getUserKey(), 5);
    gameState.updateCountryOwner(defendingCountry, player2.getUserKey(), 5);
    String defendingCountryOwner = defendingCountry.getOwner();
    int defendingCountryTroops = 3;
    int attackingCountryTroops = 1;

    gameState.updateGameStateAttackMove(defendingCountry, attackingCountry, defendingCountryOwner, defendingCountryTroops, attackingCountryTroops);
    assertEquals(attackingCountry.getName(), gameState.getLastAttackingCountry());
    assertEquals(defendingCountry.getName(), gameState.getLastDefendingCountry());
    assertNotEquals(attackingCountry.getOwner(), defendingCountry.getOwner());
    assertEquals(defendingCountryOwner, defendingCountry.getOwner());
    assertEquals(attackingCountryTroops, attackingCountry.getTroops());
    assertEquals(defendingCountryTroops, defendingCountry.getTroops());
  }

}
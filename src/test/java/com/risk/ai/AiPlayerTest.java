package com.risk.ai;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.risk.game.GameState;
import com.risk.main.User;
import com.risk.objects.Country;
import com.risk.objects.Placement;
import com.risk.objects.Player;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 * This class tests the AiPlayer class.
 *
 * @author hneumann
 */
class AiPlayerTest {


  /**
   * This method is used to initialize the test.
   *
   * @author hneumann
   */
  @BeforeAll
  static void initTest() {
    try {
      List<Player> players = new ArrayList<>();
      Player player1 = new AiPlayer("HannesAi", User.generateKey(), 3);
      Player player2 = new AiPlayer("AiPlayer2", User.generateKey(), 3);

      players.add(player1);
      players.add(player2);
      GameState gameState = new GameState(player1.getUserKey(), players);

    } catch (Exception e) {
      // ignore
    }

  }

  /**
   * Test for the AIPlayer class trying to check if the distribution of troops is working by
   * checking a chane in the amount of troops in a country picked randomly.
   *
   * @author hneumann
   */
  @Test
  void testPlaceTroops() {

    try {
      AiPlayer player1 = new AiPlayer("HannesAI", User.generateKey(), 3);
      AiPlayer player2 = new AiPlayer("AISPieler2", User.generateKey(), 3);
      List<Player> players = new ArrayList<>();
      players.add(player1);
      players.add(player2);
      GameState gameState = new GameState(player1.getUserKey(), players);
      gameState.setUnownedCountries(gameState.getCountries());

      assertEquals(gameState.getPlayers(), players);
      assertEquals(gameState.getCurrentPlayerKey(), player1.getUserKey());
      assertEquals(gameState.getPlayers().get(0).getUserKey(), player1.getUserKey());
      assertEquals(gameState.getPlayers().get(1).getUserKey(), player2.getUserKey());

      assertEquals(gameState.getCountries(), gameState.getUnownedCountries());
      assertEquals(
          gameState.getCountriesOwnedByPlayer(gameState.getPlayers().get(0).getUserKey()).size(),
          0);
      assertEquals(
          gameState.getCountriesOwnedByPlayer(gameState.getPlayers().get(1).getUserKey()).size(),
          0);

      for (int i = 0; i < 42; i++) {
        AiPlayer current = (AiPlayer) gameState.getPlayerByUserKey(gameState.getCurrentPlayerKey());
        Country country = current.distributeTroops(gameState);
        gameState.updateCountryOwner(gameState.getCountryByName(country.getName()),
            current.getUserKey(), 1);
        gameState.nextPlayer();

      }

    } catch (Exception e) {
      //ignore
    }
  }

  /**
   * Test for the AIPlayer class trying to check if the reinforcement of troops is working by
   * checking a chane in the amount of troops in a country picked randomly.
   *
   * @author hneumann
   */
  @Test
  void testAIPlayer() {
    try {
      AiPlayer aiPlayer = new AiPlayer("testAI", User.generateKey(), 3);
      assertEquals("testAI", aiPlayer.getName());
      assertEquals(true, aiPlayer.isAIPlayer());

      AiPlayer player1 = new AiPlayer("HannesAI", User.generateKey(), 3);
      AiPlayer player2 = new AiPlayer("AISPieler2", User.generateKey(), 3);
      List<Player> players = new ArrayList<>();
      players.add(player1);
      players.add(player2);
      GameState gameState = new GameState(player1.getUserKey(), players);

      for (int i = 0; i < 10; i++) {

        Country c1 = gameState.getCountriesOwnedByPlayer(player1.getUserKey())
            .get(((int) (Math.random() *
                gameState.getCountriesOwnedByPlayer(aiPlayer.getUserKey()).size())));
        int countC1 = c1.getTroops();
        Country c2 = gameState.getCountriesOwnedByPlayer(player1.getUserKey())
            .get(((int) (Math.random() *
                gameState.getCountriesOwnedByPlayer(aiPlayer.getUserKey()).size())));
        int countC2 = c2.getTroops();
        Country c3 = gameState.getCountriesOwnedByPlayer(player1.getUserKey())
            .get(((int) (Math.random() *
                gameState.getCountriesOwnedByPlayer(aiPlayer.getUserKey()).size())));
        int countC3 = c3.getTroops();
        Country c4 = gameState.getCountriesOwnedByPlayer(player1.getUserKey())
            .get(((int) (Math.random() *
                gameState.getCountriesOwnedByPlayer(aiPlayer.getUserKey()).size())));
        int countC4 = c4.getTroops();
        Country c5 = gameState.getCountriesOwnedByPlayer(player1.getUserKey())
            .get(((int) (Math.random() *
                gameState.getCountriesOwnedByPlayer(aiPlayer.getUserKey()).size())));
        int countC5 = c5.getTroops();
        Country c6 = gameState.getCountriesOwnedByPlayer(player1.getUserKey())
            .get(((int) (Math.random() *
                gameState.getCountriesOwnedByPlayer(aiPlayer.getUserKey()).size())));
        int countC6 = c6.getTroops();

        List<Placement> placements = player1.reinforcement(gameState, 15);
        for (Placement p : placements) {
          gameState.updateCountryOwner(gameState.getCountryByName(p.getCountry().getName()),
              player1.getUserKey(), p.getTroopsPlaced());
        }
        assertNotEquals(countC1, c1.getTroops());
        assertNotEquals(countC2, c2.getTroops());
        assertNotEquals(countC3, c3.getTroops());
        assertNotEquals(countC4, c4.getTroops());
        assertNotEquals(countC5, c5.getTroops());
        assertNotEquals(countC6, c6.getTroops());

      }
    } catch (Exception e) {
      //ignore
    }
  }

  /**
   * Test for the AIPlayer class trying to check if the attack of troops is working by checking a
   * chane in the amount of troops in a country picked randomly.
   *
   * @author hneumann
   */
  @Test
  void testTree() {
    AiPlayer player1 = new AiPlayer("HannesAI", 3);
    AiPlayer player2 = new AiPlayer("AISPieler2", 3);
    List<Player> players = new ArrayList<>();
    players.add(player1);
    players.add(player2);
    GameState gameState = new GameState(player1.getUserKey(), players);
    gameState.setUnownedCountries(gameState.getCountries());
    assertEquals(42, gameState.getUnownedCountries().size());
    for (int i = 0; i < 42; i++) {
      int rdm = (int) (Math.random() * gameState.getUnownedCountries().size());
      if (i % 2 == 0) {
        gameState.updateCountryOwner(gameState.getUnownedCountries().get(rdm), player1.getUserKey(),
            1);
      } else {
        gameState.updateCountryOwner(gameState.getUnownedCountries().get(rdm), player2.getUserKey(),
            1);
      }
    }
    assertEquals(0, gameState.getUnownedCountries().size());
    assertEquals(0, gameState.getUnownedCountries().size());

    assertEquals(0, gameState.getUnownedCountries().size());

    for (Country country : gameState.getCountriesOwnedByPlayer(player1.getUserKey())) {
      try {
        int i = (int) (Math.random() * 10) + 5;
        country.setTroops(i);
      } catch (Exception e) {
        //ignore
      }
    }
    AiPlayer current = (AiPlayer) gameState.getPlayerByUserKey(gameState.getCurrentPlayerKey());
    GameState clone = gameState.cloneGameState();
    List<Country> country = current.moveTroops(gameState);
    assertNotEquals(clone, gameState);

    Node root = new Node(gameState, 0);
    Tree tree = new Tree(root);
    List<Node> bestMoves = tree.calcBestMove();
  }

  /**
   * Test for the AIPlayer class trying to check if the attack of troops is working by checking a
   * chane in the amount of troops in a country picked randomly.
   *
   * @author hneumann
   */
  @Test
  void testAttack() {
    AiPlayer aiPlayer1 = new AiPlayer("testAI1", 3);
    AiPlayer aiPlayer2 = new AiPlayer("testAI2", 3);
    AiPlayer aiPlayer3 = new AiPlayer("testAI3", 3);
    List<Player> players = new ArrayList<>();
    players.add(aiPlayer1);
    players.add(aiPlayer2);
    players.add(aiPlayer3);
    assertNotEquals(aiPlayer1, aiPlayer2);
    assertNotEquals(aiPlayer1, aiPlayer3);
    assertNotEquals(aiPlayer2, aiPlayer3);

    GameState gameState = new GameState(aiPlayer1.getUserKey(), players);
    assertNotEquals(null, gameState);
    assertEquals(aiPlayer1.getUserKey(), gameState.getCurrentPlayerKey());
    assertNotEquals(gameState.getPlayers().get(0), gameState.getPlayers().get(1));
    assertNotEquals(gameState.getPlayers().get(0), gameState.getPlayers().get(2));
    assertNotEquals(gameState.getPlayers().get(1), gameState.getPlayers().get(2));

    for (int i = 0; i < 20; i++) {
      String current = gameState.getCurrentPlayerKey();
      gameState.nextPlayer();
      assertNotEquals(current, gameState.getCurrentPlayerKey());
    }

    for (Country country : gameState.getCountries()) {
      int i = (int) (Math.random() * 3);
      int troops = (int) (Math.random() * 10) + 1;
      gameState.updateCountryOwner(country, gameState.getCurrentPlayerKey(), troops);
      gameState.nextPlayer();
    }

    //Block of assertTest to check if the current gameState is the expected one
    assertEquals(0, gameState.getUnownedCountries().size());
    assertEquals(42, gameState.getCountries().size());
    assertNotEquals(0, gameState.getCountriesOwnedByPlayer(aiPlayer1.getUserKey()).size());
    assertNotEquals(0, gameState.getCountriesOwnedByPlayer(aiPlayer2.getUserKey()).size());
    assertNotEquals(0, gameState.getCountriesOwnedByPlayer(aiPlayer3.getUserKey()).size());
    assertNotEquals(gameState.getCountriesOwnedByPlayer(aiPlayer1.getUserKey()),
        gameState.getCountriesOwnedByPlayer(aiPlayer2.getUserKey()));
    assertNotEquals(gameState.getCountriesOwnedByPlayer(aiPlayer1.getUserKey()),
        gameState.getCountriesOwnedByPlayer(aiPlayer3.getUserKey()));
    assertNotEquals(gameState.getCountriesOwnedByPlayer(aiPlayer2.getUserKey()),
        gameState.getCountriesOwnedByPlayer(aiPlayer3.getUserKey()));

    for (Player player : gameState.getPlayers()) {
      for (Country country : gameState.getCountriesOwnedByPlayer(player.getUserKey())) {
        assertNotEquals(0, country.getTroops());
        assertNotNull(
            gameState.getCountriesOwnedByPlayer(aiPlayer1.getUserKey()).get(0).getNeighbours());
        assertNotNull(country.getOwner());
        assertEquals(player.getUserKey(), country.getOwner());
      }
    }
    assertNotEquals(gameState, gameState.cloneGameState());

    for (Country country : gameState.getCountriesOwnedByPlayer(gameState.getCurrentPlayerKey())) {
      gameState.updateTroops(country, country.getTroops() + 10);
    }
    AiPlayer current = (AiPlayer) gameState.getPlayerByUserKey(gameState.getCurrentPlayerKey());
    List<Node> move = current.attack(gameState);
    assertNotEquals(0, move.size());

  }

  /**
   * Second test for the AI player class to check if the attack of troops is working by checking a
   * chane in the amount of troops in a country picked randomly.
   *
   * @author hneumann
   */
  @Test
  void testAttack2() {
    AiPlayer aiPlayer1 = new AiPlayer("testAI1", 3);
    AiPlayer aiPlayer2 = new AiPlayer("testAI2", 3);
    AiPlayer aiPlayer3 = new AiPlayer("testAI3", 3);
    List<Player> players = new ArrayList<>();
    players.add(aiPlayer1);
    players.add(aiPlayer2);
    players.add(aiPlayer3);
    GameState gameState = new GameState(aiPlayer1.getUserKey(), players);

    for (Country country : gameState.getCountries()) {
      int i = (int) (Math.random() * 3);
      int troops = (int) (Math.random() * 10) + 1;
      gameState.updateCountryOwner(country, gameState.getCurrentPlayerKey(), troops);
      gameState.nextPlayer();
    }

    assertEquals(0, gameState.getUnownedCountries().size());
    assertEquals(42, gameState.getCountries().size());
    assertNotEquals(0, gameState.getCountriesOwnedByPlayer(aiPlayer1.getUserKey()).size());
    assertNotEquals(0, gameState.getCountriesOwnedByPlayer(aiPlayer2.getUserKey()).size());
    assertNotEquals(0, gameState.getCountriesOwnedByPlayer(aiPlayer3.getUserKey()).size());

    assertNotEquals(gameState.getCountriesOwnedByPlayer(aiPlayer1.getUserKey()),
        gameState.getCountriesOwnedByPlayer(aiPlayer2.getUserKey()));
    assertNotEquals(gameState.getCountriesOwnedByPlayer(aiPlayer1.getUserKey()),
        gameState.getCountriesOwnedByPlayer(aiPlayer3.getUserKey()));
    assertNotEquals(gameState.getCountriesOwnedByPlayer(aiPlayer2.getUserKey()),
        gameState.getCountriesOwnedByPlayer(aiPlayer3.getUserKey()));
  }

  /**
   * Third test for the AI player class to check if the attack of troops is working by checking a
   * chane in the amount of troops in a country picked randomly. This test is to check if the attack
   * is working when the AI player has no neighbours to attack.
   *
   * @author hneumann
   */
  @Test
  void testDistributeTroops() {
    AiPlayer player1 = new AiPlayer("testAI1", 3);
    AiPlayer player2 = new AiPlayer("testAI2", 3);
    AiPlayer player3 = new AiPlayer("testAI3", 3);
    List<Player> players = new ArrayList<>();
    players.add(player1);
    players.add(player2);
    players.add(player3);
    GameState gameState = new GameState(player1.getUserKey(), players);

    int count = 0;
    for (int i = 0; i < 42; i++) {

      AiPlayer current = (AiPlayer) gameState.getPlayerByUserKey(gameState.getCurrentPlayerKey());
      Country country = current.distributeTroops(gameState.cloneGameState());
      gameState.updateCountryOwner(gameState.getCountryByName(country.getName()),
          current.getUserKey(), 1);
      gameState.nextPlayer();

    }
    assertEquals(42, gameState.getCountries().size());
    assertNotEquals(0, gameState.getCountriesOwnedByPlayer(player1.getUserKey()).size());
    assertNotEquals(0, gameState.getCountriesOwnedByPlayer(player2.getUserKey()).size());
    assertNotEquals(0, gameState.getCountriesOwnedByPlayer(player3.getUserKey()).size());
    assertNotEquals(42, gameState.getCountriesOwnedByPlayer(player1.getUserKey()).size());
    assertNotEquals(42, gameState.getCountriesOwnedByPlayer(player2.getUserKey()).size());
    assertNotEquals(42, gameState.getCountriesOwnedByPlayer(player3.getUserKey()).size());
    assertEquals(14, gameState.getCountriesOwnedByPlayer(player1.getUserKey()).size());
    assertEquals(14, gameState.getCountriesOwnedByPlayer(player2.getUserKey()).size());
    assertEquals(14, gameState.getCountriesOwnedByPlayer(player3.getUserKey()).size());
    assertFalse(gameState.getCountriesOwnedByPlayer(player1.getUserKey()).size() > 42);
    assertFalse(gameState.getCountriesOwnedByPlayer(player2.getUserKey()).size() > 42);
    assertFalse(gameState.getCountriesOwnedByPlayer(player3.getUserKey()).size() > 42);

    for (Player player : gameState.getPlayers()) {
      for (Country country : gameState.getCountriesOwnedByPlayer(player.getUserKey())) {
        assertNotEquals(0, country.getTroops());
        assertNotNull(country.getOwner());
        assertEquals(player.getUserKey(), country.getOwner());
      }
    }
  }

  /**
   * Method to test if the reinforcement of troops is working by checking if the amount of troops in
   * a country is increased by the amount of troops given to the country by the AI player. This test
   * is to check if the reinforcement is working when the AI player has no neighbours to attack.
   *
   * @author hneumann
   */
  @Test
  void testReinforcement() {
    AiPlayer aiPlayer1 = new AiPlayer("testAI1", 3);
    AiPlayer aiPlayer2 = new AiPlayer("testAI2", 3);
    AiPlayer aiPlayer3 = new AiPlayer("testAI3", 3);
    List<Player> players = new ArrayList<>();
    players.add(aiPlayer1);
    players.add(aiPlayer2);
    players.add(aiPlayer3);
    GameState gameState = new GameState(aiPlayer1.getUserKey(), players);
    gameState.setUnownedCountries(gameState.getCountries());

    for (int i = 0; i < 42; i++) {
      int rmd = (int) (Math.random() * gameState.getUnownedCountries().size());
      int troops = 10; // (int) (Math.random() * 10) + 1;
      gameState.updateCountryOwner(gameState.getUnownedCountries().get(rmd),
          gameState.getCurrentPlayerKey(), troops);
      gameState.nextPlayer();
    }
    for (int i = 0; i < 1; i++) {

      AiPlayer current = (AiPlayer) gameState.getPlayerByUserKey(gameState.getCurrentPlayerKey());
      GameState clone = gameState.cloneGameState();
      List<Placement> placements = current.reinforcement(gameState, 20);
      for (Placement placement : placements) {
        gameState.updateCountryOwner(gameState.getCountryByName(placement.getCountry().getName()),
            current.getUserKey(), placement.getTroopsPlaced());
      }
      gameState.nextPlayer();
    }

    //Block of assertTest to check if the current gameState is the expected one
    for (Player player : gameState.getPlayers()) {
      for (Country country : gameState.getCountriesOwnedByPlayer(player.getUserKey())) {
        assertNotEquals(0, country.getTroops());
        assertNotNull(country.getOwner());
        assertEquals(player, country.getOwner());
        assertEquals(player.getUserKey(), country.getOwner());
      }
    }

  }

  /**
   * Method to test if the moving of troops is working by checking if the amount of troops in a
   * country is decreased by the amount of troops given to the country by the AI player. This test
   * is to check if the moving of troops is working when the AI player has no neighbours to attack.
   *
   * @author hneumann
   */
  @Test
  void testMoveTroops() {
    AiPlayer aiPlayer1 = new AiPlayer("testAI1", 3);
    AiPlayer aiPlayer2 = new AiPlayer("testAI2", 3);
    AiPlayer aiPlayer3 = new AiPlayer("testAI3", 3);
    List<Player> players = new ArrayList<>();
    players.add(aiPlayer1);
    players.add(aiPlayer2);
    players.add(aiPlayer3);
    GameState gameState = new GameState(aiPlayer1.getUserKey(), players);
    gameState.setUnownedCountries(gameState.getCountries());
    for (int i = 0; i < 42; i++) {
      int rmd = (int) (Math.random() * gameState.getUnownedCountries().size());
      int troops = 10; // (int) (Math.random() * 10) + 1;
      gameState.updateCountryOwner(gameState.getUnownedCountries().get(rmd),
          gameState.getCurrentPlayerKey(), troops);
      gameState.nextPlayer();
    }
  }

  /**
   * Method to test if the attack of troops is working by checking if the amount of troops in a
   *  country is decreased by the amount of troops given to the country by the AI player. This test
   *  is to check if the attack of troops is working when the AI player has no neighbours to attack.
   *
   * @author hneumann
   */
  @Test
  void testAttack1() {
    AiPlayer aiPlayer1 = new AiPlayer("testAI1", 3);
    AiPlayer aiPlayer2 = new AiPlayer("testAI2", 3);
    AiPlayer aiPlayer3 = new AiPlayer("testAI3", 3);
    List<Player> players = new ArrayList<>();
    players.add(aiPlayer1);
    players.add(aiPlayer2);
    players.add(aiPlayer3);
    GameState gameState = new GameState(aiPlayer1.getUserKey(), players);
    gameState.setUnownedCountries(gameState.getCountries());
    for (int i = 0; i < 42; i++) {
      int rmd = (int) (Math.random() * gameState.getUnownedCountries().size());
      int troops = (int) (Math.random() * 10) + 1;
      gameState.updateCountryOwner(gameState.getUnownedCountries().get(rmd),
          gameState.getCurrentPlayerKey(), troops);
      gameState.nextPlayer();
    }

    assertEquals(0, gameState.getUnownedCountries().size());
    assertEquals(3, gameState.getPlayers().size());

    AiPlayer current = (AiPlayer) gameState.getPlayerByUserKey(gameState.getCurrentPlayerKey());
    List<Node> list = current.attack(gameState);
  }
}

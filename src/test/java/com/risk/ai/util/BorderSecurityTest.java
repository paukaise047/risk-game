package com.risk.ai.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.risk.ai.AiPlayer;
import com.risk.game.GameState;
import com.risk.objects.Country;
import com.risk.objects.Player;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 * This class tests the functionality of BorderSecurity class.
 *
 * @author hneumann
 */
class BorderSecurityTest {

  private static AiPlayer player1;
  private static GameState gameState;

  /**
   * This method is used to initialize the test.
   *
   * @author hneumann
   */
  @BeforeAll
  static void initTest() {
    try {

      List<Player> players = new ArrayList<>();
      player1 = new AiPlayer("test1", 1);
      Player player2 = new Player("test2", "ab2", true);
      Player player3 = new Player("test3", "ab3", true);
      Player player4 = new Player("test4", "ab4", true);
      players.add(player1);
      players.add(player2);
      players.add(player3);
      players.add(player4);
      gameState = new GameState(player1.getUserKey(), players);

      List<Country> countries = gameState.getCountries();
      for (Country c : countries) {
        int i = (int) (Math.random() * 4);
        switch (i) {
          case 1:
            gameState.updateCountryOwner(c, player1.getUserKey(), c.getTroops());
          case 2:
            gameState.updateCountryOwner(c, player2.getUserKey(), c.getTroops());
          case 3:
            gameState.updateCountryOwner(c, player3.getUserKey(), c.getTroops());
          case 4:
            gameState.updateCountryOwner(c, player4.getUserKey(), c.getTroops());
        }
      }
      for (int i = 0; i < 10; i++) {
        gameState.updateCountryOwner(
            countries.get(i), player1.getUserKey(), countries.get(i).getTroops());
        gameState.updateCountryOwner(
            countries.get(i + 10), player2.getUserKey(), countries.get(i + 10).getTroops());
        gameState.updateCountryOwner(
            countries.get(i + 20), player3.getUserKey(), countries.get(i + 20).getTroops());
        gameState.updateCountryOwner(
            countries.get(i + 30), player4.getUserKey(), countries.get(i + 30).getTroops());
      }
      gameState.updateCountryOwner(
          countries.get(40), player1.getUserKey(), countries.get(40).getTroops());
      gameState.updateCountryOwner(
          countries.get(41), player2.getUserKey(), countries.get(41).getTroops());

    } catch (Exception e) {
      //ignore
    }
  }

  /**
   * Test method to test the BorderSecurityRatio() and the BorderSecurityThread() methods.
   *
   * @author hneumann
   */
  @Test
  void TestBorderSecurityThread() {
    try {
      for (int i = 0; i < 100; i++) {
        int numTroops = 0;
        Country rdm =
            gameState
                .getCountriesOwnedByPlayer(player1.getUserKey())
                .get(
                    (int)
                        (Math.random()
                            * gameState
                            .getCountriesOwnedByPlayer(gameState.getCurrentPlayerKey())
                            .size()));
        for (Country n : rdm.getNeighbours()) {
          numTroops += n.getTroops();
        }

        assertEquals(numTroops, BorderSecurity.borderSecurityThread(rdm));
        double ratio = (double) numTroops / rdm.getTroops();
        assertEquals(ratio, BorderSecurity.borderSecurityRatio(rdm));
      }
    } catch (Exception e) {
      //ignore
    }
  }

  /**
   * Tests the calculation of the normalized border security ratio.
   *
   * @author hneumann
   */
  @Test
  void TestNormalizedBorderSecurityRatio() {
    try {
      for (int i = 0; i < 100; i++) {
        double SumBSR = 0.0;
        Country rdm =
            gameState
                .getCountriesOwnedByPlayer(gameState.getCurrentPlayerKey())
                .get(
                    (int)
                        (Math.random()
                            * gameState
                            .getCountriesOwnedByPlayer(gameState.getCurrentPlayerKey())
                            .size()));
        for (Country c : gameState.getCountriesOwnedByPlayer(player1.getUserKey())) {
          SumBSR += BorderSecurity.borderSecurityRatio(c);
        }
        double bsr = BorderSecurity.borderSecurityRatio(rdm);
        double nbsr = bsr / SumBSR;
        assertEquals(nbsr, BorderSecurity.normalizedBorderSecurityRatio(rdm, gameState));
      }
    } catch (Exception e) {
      //ignore
    }
  }
}

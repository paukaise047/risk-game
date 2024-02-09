package com.risk.ai.util;

import com.risk.game.GameState;
import com.risk.objects.Country;

/**
 * This class is used to calculate the heuristic values for the nodes. These heurisitcs help us to
 * evaluate a node resulting from a move.
 *
 * @author hneumann
 */
public class AttackHeuristics {

  /**
   * Calculates the ratio of the number of countries a player owns to all countries on the map.
   *
   * @param gameState resulting gameState of the move
   * @return ratio countries the player has to countries on the board
   * @author hneumann
   */
  public static double calcHeuristic1(GameState gameState) {
    return (double) gameState.getCountriesOwnedByPlayer(gameState.getCurrentPlayerKey())
        .size() / 42;
  }

  /**
   * Calculates the ratio of the number of all the units a player owns to all units that are in the
   * map.
   *
   * @param gameState resulting gameState of the move
   * @return ratio troops the player has to troops on the board
   * @author hneumann
   */
  public static double calcHeuristic2(GameState gameState) {

    assert gameState != null;
    return (double) gameState.getTroopsByPlayer(gameState.getCurrentPlayerKey())
        / (double) gameState
        .getTroopsOnBoard();
  }

  /**
   * Method to calculate the third heuristic to evaluate the defensive strength of each country. We
   * calculate for each country x the AIPlayer owns for every adjacent country y we calculate z = x
   * / (x + y). In the next step we calculate the average of all z's. The closer this average gets
   * to 1, the better is our defensive strength.
   *
   * @return Average z to evaluate defensive strength on the board
   * @author hneumann
   */
  public static double calcHeuristic3(GameState gameState) {
    double sum = 0.0;
    double count = 0.0;
    try {
      for (Country country : gameState.getCountriesOwnedByPlayer(
          gameState.getCurrentPlayerKey())) {
        for (Country neighbour : country.getNeighbours()) {
          sum += ((double) gameState.getCountryByName(country.getName()).getTroops()
              / (gameState.getCountryByName(country.getName()).getTroops()
              + gameState.getCountryByName(neighbour.getName()).getTroops()));
          count++;
        }

        assert !Float.isNaN((float) sum);
        assert count >= 0;
      }
    } catch (Exception e) {
      //ignore
    }
    return sum / count;
  }

  /**
   * Heurisitc 4 evaluates how connected the countries the ai player owns are. For each country we
   * calculate the ratio of neighbour countries owned to existing neighbour countries. Then we take
   * the average of these ratios, the closer to 1 it gets, the more countries owned by the AIPlayer
   * are neighbours. Since in the game of risk it is important to own connected areas, because you
   * get more troops each round if you own a continent, and connected areas are easier to defend.
   *
   * @param gameState gameState after the move
   * @return Indicator to evaluate the result of an attack.
   */
  public static double calcHeuristic4(GameState gameState) {
    double sum = 0.0;
    int count = 0;
    for (Country country : gameState.getCountriesOwnedByPlayer(
        gameState.getCurrentPlayerKey())) {
      for (Country neighbour : country.getNeighbours()) {
        if (neighbour.getOwner() == null || neighbour.getOwner()
            .equals(gameState.getCurrentPlayerKey())) {
          count++;
        }
      }
      sum += (double) count / country.getNeighbours().length;
    }
    return sum / (double) gameState.getCountriesOwnedByPlayer(
        gameState.getCurrentPlayerKey()).size();
  }

  /**
   * Adapted the heuristic 4 to the distribution of the countries on the board. The more countries a
   * player owns, the more  important it is to own connected areas.
   *
   * @param gameState gameState after the move
   * @return Indicator to evaluate the result of an attack.
   */
  public static double calcDistributionIndikator(GameState gameState) {

    double sum = 0.0;
    int count = 0;
    double indicator = 0.0;
    try {
      for (Country country : gameState.getCountriesOwnedByPlayer(
          gameState.getCurrentPlayerKey())) {
        if (country.getOwner() == null) {
          continue;
        }
        for (Country neighbour : country.getNeighbours()) {
          if (neighbour.getOwner() == null) {
            continue;
          }
          if (neighbour.getOwner().equals(gameState.getCurrentPlayerKey())) {
            count++;
          }
        }
        sum += (double) count / country.getNeighbours().length;
      }
      indicator =
          sum / gameState.getCountriesOwnedByPlayer(gameState.getCurrentPlayerKey())
              .size();
    } catch (Exception e) {
      //ignore
    }
    return indicator;
  }
}

package com.risk.ai.util;

import com.risk.game.GameState;

/**
 * Class that calculates the score of a given GameState The score is calculated by multiplying the
 * heuristic values with their weights.
 *
 * @author hneumann
 */

public class Score {

  private static final double weightHeuristic1 = 11.0;
  private static final double weightHeuristic2 = 27.0;
  private static final double weightHeuristic3 = 24.0;
  private static final double weightHeuristic4 = 29.0;

  /**
   * Calculates the score of a given GameState.
   *
   * @param gameState the GameState to calculate the score for
   * @return the score of the given GameState
   * @author hneumann
   */
  public static double calculateScoring(GameState gameState) {
    double wh1 = weightHeuristic1 * AttackHeuristics.calcHeuristic1(gameState);
    double wh2 = weightHeuristic2 * AttackHeuristics.calcHeuristic2(gameState);
    double wh3 = weightHeuristic3 * AttackHeuristics.calcHeuristic3(gameState);
    double wh4 = weightHeuristic4 * AttackHeuristics.calcHeuristic4(gameState);
    double sum = 0;
    sum += wh1 + wh2 + wh3 + wh4;
    return sum;
  }
}

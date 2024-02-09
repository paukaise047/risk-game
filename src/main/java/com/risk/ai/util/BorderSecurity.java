package com.risk.ai.util;

import com.risk.game.GameState;
import com.risk.objects.Country;

/**
 * Class that contains methods to calculate the border security thread and the border security to
 * evaluate the thread to a country. Based on these values the AI can decide which country to
 * reinforce.
 *
 * @author hneumann
 */
public class BorderSecurity {

  /**
   * Calculate the border security thread of a country. The border security thread is the sum of all
   * troops of the neighbours of the country that are not owned by the same player.
   *
   * @param country the country to calculate the border security thread
   * @return borderSecurityThread the border security thread of the country
   * @author lkuech
   */
  public static int borderSecurityThread(Country country) {
    Country[] neighbours = country.getNeighbours();
    int borderSecurityThread = 0;
    for (Country neighbour : neighbours) {
      if (!country.getOwner().equals(neighbour.getOwner())) {
        borderSecurityThread += neighbour.getTroops();
      }
    }
    return borderSecurityThread;
  }

  /**
   * Calculate the border security ratio of a country. The border security ratio is the border
   * security thread of a country divided by the number of troops of the country.
   *
   * @param country the country to calculate the border security ratio
   * @return borderSecurityRatio the border security ratio of the country
   * @author hneumann
   */
  public static double borderSecurityRatio(Country country) {

    return (double) borderSecurityThread(country) / (double) country.getTroops();
  }

  /**
   * Calculate the normalized border security ratio of a country. The normalized border security
   * ratio is the border security ratio of a country divided by the sum of all border security
   * ratios of the countries owned by the same player.
   *
   * @param country the country to calculate the normalized border security ratio
   * @return normalizedBorderSecurityRatio the normalized border security ratio of the country
   * @author hneumann
   */
  public static double normalizedBorderSecurityRatio(Country country, GameState gamestate) {
    double sumBsr = 0.0;
    for (Country c : gamestate.getCountriesOwnedByPlayer(country.getOwner())) {
      sumBsr += borderSecurityRatio(c);
    }
    double bsr = borderSecurityRatio(country);
    return bsr / sumBsr;
  }
}

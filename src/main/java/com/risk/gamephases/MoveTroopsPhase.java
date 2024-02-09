package com.risk.gamephases;

import com.risk.gui.GameController;
import com.risk.main.User;
import com.risk.objects.Country;
import com.risk.objects.Move;
import com.risk.objects.enums.GamePhase;

/**
 * Class that represents the MoveTroopsPhase and interacts with the gui.
 *
 * @author floribau
 */
public class MoveTroopsPhase extends Phase {

  private final String player;
  private Country fromCountry;
  private Country toCountry;
  private int troopCount;
  private boolean moved;

  /**
   * The constructor of the MoveTroopsPhase
   *
   * @param p - the player whose turn it is in this MoveTroopsPhase
   * @author floribau
   */
  public MoveTroopsPhase(String p) {
    this.player = p;
    try {

      this.moveTroops();
    } catch (Exception e) {
      //ignore
    }
  }

  /**
   * Waits for user input from the Gui and then handles the game logic / creates a Move instance
   * from this input.
   *
   * @author lkuech
   */
  public void moveTroops() {
    // Todo check for troops wanting to move
    GameController gui = User.getUser().getGameController();
    gui.setGamePhase(GamePhase.MOVE_TROOPS_PHASE);

    gui.setSelectedCountry2(null);
    gui.setSelectedCountry1(null);
    gui.setMoveTroopsCountriesActive(player);

    Move move = gui.movingTroopsSelection(player);
    while (!move.isMoveValid()) {
      gui.setMoveTroopsCountriesActive(player);
      move = gui.movingTroopsSelection(player);
    }


    if (move.isNextPhase()) {

      this.fromCountry = null;
      this.toCountry = null;
      this.troopCount = 0;
    } else {

      this.fromCountry = move.getFromCountry();

      this.toCountry = move.getToCountry();

      this.troopCount = move.getTroops();

    }
  }

  /**
   * Returns the country from which troops are moved.
   *
   * @return the country from which troops are moved
   * @author floribau
   */
  public Country getFromCountry() {
    return this.fromCountry;
  }

  /**
   * Sets the country from which to move troops.
   *
   * @param fromCountry - the country from which troops should be moved
   * @author floribau
   */
  public void setFromCountry(Country fromCountry) {
    this.fromCountry = fromCountry;
  }

  /**
   * Returns the country to which troops are moved.
   *
   * @return the country to which troops are moved
   * @author floribau
   */
  public Country getToCountry() {
    return this.toCountry;
  }

  /**
   * Sets the country to move troops to.
   *
   * @param toCountry - the country to which troops should be moved
   * @author floribau
   */
  public void setToCountry(Country toCountry) {
    this.toCountry = toCountry;
  }

  /**
   * returns the amount of troops that are moved.
   *
   * @return the amount of troops that are moved
   * @author floribau
   */
  public int getTroopCount() {
    return this.troopCount;
  }

  public void setTroopCount(int troopCount) {
    this.troopCount = troopCount;
  }
}

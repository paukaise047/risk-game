package com.risk.objects;

/**
 * This class represents a move made by a player. It contains the country the player is moving
 * troops from, the country the player is moving troops to and the number of troops being moved.
 *
 * @author lkuech
 */
public class Move {

  private Country fromCountry;
  private Country toCountry;
  private int troops;
  private boolean moveValid;
  private boolean nextPhase;

  /**
   * This method returns the country the player is moving troops from.
   *
   * @return fromCountry
   * @author lkuech
   */
  public Country getFromCountry() {
    return fromCountry;
  }

  /**
   * This method sets the country the player is moving troops from.
   *
   * @param fromCountry Country the player is moving troops from
   * @author lkuech
   */
  public void setFromCountry(Country fromCountry) {
    this.fromCountry = fromCountry;
  }

  /**
   * This method returns the country the player is moving troops to.
   *
   * @return toCountry
   * @author lkuech
   */
  public Country getToCountry() {
    return toCountry;
  }

  /**
   * This method sets the country the player is moving troops to.
   *
   * @param toCountry Country the player is moving troops to
   * @author lkuech
   */
  public void setToCountry(Country toCountry) {
    this.toCountry = toCountry;
  }

  /**
   * This method returns the number of troops the player is moving.
   *
   * @return int troops
   * @author lkuech
   */
  public int getTroops() {
    return troops;
  }

  /**
   * This method sets the number of troops the player is moving.
   *
   * @param troops int number of troops the player is moving from one country to another
   * @author lkuech
   */
  public void setTroops(int troops) {
    this.troops = troops;
  }

  /**
   * This method returns whether the move is valid.
   *
   * @return boolean moveValid
   * @author lkuech
   */
  public boolean isMoveValid() {
    return moveValid;
  }

  /**
   * This method sets whether the move is valid.
   *
   * @param moveValid boolean whether the move is valid or not
   * @author lkuech
   */
  public void setMoveValid(boolean moveValid) {
    this.moveValid = moveValid;
  }

  /**
   * This method returns whether the next phase should be executed.
   *
   * @return boolean nextPhase
   * @author lkuech
   */
  public boolean isNextPhase() {
    return nextPhase;
  }

  /**
   * This method sets whether the next phase should be executed.
   *
   * @param nextPhase boolean whether the next phase should be executed or not
   * @author lkuech
   */
  public void setNextPhase(boolean nextPhase) {
    this.nextPhase = nextPhase;
  }
}

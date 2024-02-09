package com.risk.objects;
/**
 * This class represents an Attack. An Attack has an attacking country, a defending country, the
 * amount of troops used, and a boolean that represents if the attack is valid.
 *
 * @author lkuech.
 */
public class Attack {

  private Country attackingCountry;
  private Country defendingCountry;
  private int troopsUsed;
  private boolean attackValid;

  /**
   * returns the defending country.
   *
   * @return the defending country.
   * @author lkuech.
   */
  public Country getDefendingCountry() {
    return defendingCountry;
  }
  /**
   * sets the country that is defending.
   *
   * @param defendingCountry the country that is defending.
   * @author lkuech.
   */
  public void setDefendingCountry(Country defendingCountry) {
    this.defendingCountry = defendingCountry;
  }
  /**
   * returns the country that is attacking.
   *
   * @return the country that is attacking.
   * @author lkuech.
   */
  public Country getAttackingCountry() {
    return attackingCountry;
  }
  /**
   * sets the country that is attacking.
   *
   * @param attackingCountry the country that is attacking.
   * @author lkuech.
   */
  public void setAttackingCountry(Country attackingCountry) {
    this.attackingCountry = attackingCountry;
  }
  /**
   * returns the amount of troops used.
   *
   * @return the amount of troops used.
   * @author lkuech.
   */
  public int getTroopsUsed() {
    return troopsUsed;
  }
  /**
   * sets the amount of troops used.
   *
   * @param troopsUsed the amount of troops used in the attack.
   * @author lkuech.
   */
  public void setTroopsUsed(int troopsUsed) {
    this.troopsUsed = troopsUsed;
  }
  /**
   * returns if the attack is valid.
   *
   * @return if the attack is valid.
   * @author lkuech.
   */
  public boolean isAttackValid() {
    return attackValid;
  }
  /**
   * sets if the attack is valid.
   *
   * @param attackValid if the attack is valid.
   * @author lkuech.
   */
  public void setAttackValid(boolean attackValid) {
    this.attackValid = attackValid;
  }
}

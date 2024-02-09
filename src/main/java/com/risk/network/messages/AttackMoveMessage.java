package com.risk.network.messages;

import static com.risk.network.MessageType.ATTACK_MOVE_MESSAGE;

import com.risk.network.Message;
import com.risk.objects.Country;

/**
 * Message class of the AttackMoveMessage
 *
 * @author lkuech
 */
public class AttackMoveMessage extends Message {

  private final Country attackingCountry;
  private final Country defendingCountry;
  private final String defendingCountryOwner;
  private final int defendingCountryTroops;
  private final int attackingCountryTroops;
  private boolean firstAttack;

  /**
   * Constructor for the attackMoveMessage
   * @param defendingCountry defending country
   * @param attackingCountry attacking country
   * @param defendingCountryOwner owner of the defending country
   * @param defendingCountryTroops amount of troops in the defending country
   * @param attackingCountryTroops amount of attacking troops
   */
  public AttackMoveMessage(Country defendingCountry, Country attackingCountry,
      String defendingCountryOwner, int defendingCountryTroops, int attackingCountryTroops) {

    super(ATTACK_MOVE_MESSAGE);
    this.attackingCountry = attackingCountry;
    this.defendingCountry = defendingCountry;
    this.defendingCountryOwner = defendingCountryOwner;
    this.attackingCountryTroops = attackingCountryTroops;
    this.defendingCountryTroops = defendingCountryTroops;
  }

  /**
   * getter method for the defending country
   * @return defending country
   * @author lkuech
   */
  public String getDefendingCountryOwner() {
    return defendingCountryOwner;
  }

  /**
   * getter method for the attackingCountry
   * @return the attackingCountry
   * @author lkuech
   */
  public Country getAttackingCountry() {
    return attackingCountry;
  }

  /**
   * getter method for the defending country
   *
   * @return the defendingCountry
   * @author lkuech
   */
  public Country getDefendingCountry() {
    return defendingCountry;
  }

  /**
   * getter method for the attacking troops
   *
   * @return attackingCountryTroops
   * @author lkuech
   */
  public int getAttackingCountryTroops() {
    return attackingCountryTroops;
  }

  /**
   * getter method for the defending countryTroops.
   *
   * @return number of contryTroops
   * @author lkuech
   */
  public int getDefendingCountryTroops() {
    return defendingCountryTroops;
  }
}

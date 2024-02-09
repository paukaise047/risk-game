package com.risk.gamephases;

import com.risk.gui.GameController;
import com.risk.main.User;
import com.risk.network.messages.AttackMoveMessage;
import com.risk.objects.Attack;
import com.risk.objects.AttackResult;
import com.risk.objects.Country;
import com.risk.objects.dice.AttackRoll;
import com.risk.objects.dice.DefenseRoll;
import com.risk.objects.enums.GamePhase;

/**
 * This class represents the attack move phase of the game. It contains the attacking player, the
 * defending player, the attacking country, the defending country, the number of troops used in the
 * attack, the number of troops survived, the number of troops on the defending country.
 * It gets the Information it needs by enabling the attack countries on the GUI and then waiting for
 * the player to choose the attacking and defending country. After that it calculates the result of
 * the attack.
 * It also provides a method to create an AttackMoveMessage that is sent to the server.
 */

public class AttackMove extends Phase {

  private final String attackingPlayer;
  private String defendingPlayer;
  private Country attackingCountry;
  private Country defendingCountry;
  private int attackingTroops;
  private int troopSurvived;
  private int defendingTroops;
  private boolean attackWon;
  private String defendingCountryOwner;
  private boolean attackEnded = false;

  /**
   * Constructor of the attack move.
   *
   * @param player the player that is attacking
   * @author lkuech
   */
  public AttackMove(String player) {
    this.attackingPlayer = player;
    this.attackWon = false;
    this.attackMove();

  }

  /**
   * Method that handles the entire attack move. Waits for the chosen Country from the Gui and then
   * calculates the result of the attack. After that it sets the new owner of the defending country
   * and the new amount of troops on the defending country. Finally, it updates the GUI. This Method
   * includes the gameLogic of the attack move.
   *
   * @author lkuech
   */
  public void attackMove() {
    GameController gui = User.getUser().getGameController();
    gui.setGamePhase(GamePhase.ATTACK_PHASE);

    gui.setSelectedCountry1(null);
    gui.setSelectedCountry2(null);

    gui.setAttackCountriesActive(attackingPlayer);

    Attack attack = gui.attackSelection(attackingPlayer);
    if (attack != null) {
      while (!attack.isAttackValid()) {
        gui.setAttackCountriesActive(attackingPlayer);
        attack = gui.attackSelection(attackingPlayer);
        if (attack == null) {
          break;
        }
        try {
          Thread.sleep(10);
        } catch (InterruptedException e) {
          //ignore
        }
      }
    }

    if (attack != null) {
      this.attackingCountry = attack.getAttackingCountry();
      this.defendingCountry = attack.getDefendingCountry();

      this.defendingPlayer = defendingCountry.getOwner();

      this.attackingTroops = attack.getTroopsUsed();
      this.troopSurvived = this.attackingTroops;
      this.defendingTroops = this.defendingCountry.getTroops();

      while (troopSurvived > 0 && defendingTroops > 0) {
        AttackRoll attackRoll = new AttackRoll(troopSurvived);
        DefenseRoll defenseRoll = new DefenseRoll(defendingTroops);
        AttackResult as = new AttackResult(attackRoll, defenseRoll);

        this.troopSurvived -= as.getAttackingTroopsLost();
        this.defendingTroops -= as.getDefendingTroopsLost();
      }

      if (troopSurvived > 0) {
        setAttackWon(true);
        setDefendingCountryOwner(attackingCountry.getOwner());
        setDefendingTroops(troopSurvived);
      } else {
        setAttackWon(false);
        setDefendingCountryOwner(defendingCountry.getOwner());
      }
    } else {
      attackEnded = true;
    }
  }

  /**
   * Method that creates the AttackMoveMessage that can be sent to the server.
   *
   * @return AttackMoveMessage based on the class variables of the AttackMove class
   * @author floribau
   */
  public AttackMoveMessage createAttackMoveMessage() {
    if (!attackEnded) {
      int defendingCountryTroops =
          attackWon ? this.troopSurvived : this.defendingTroops; //TODO integrate attack pop up
      String defendingCountryOwner = attackWon ? this.attackingPlayer : this.defendingPlayer;
      return new AttackMoveMessage(defendingCountry, attackingCountry, defendingCountryOwner,
          defendingTroops, attackingCountry.getTroops() - attackingTroops);
    } else {
      return new AttackMoveMessage(null, null, null, -1, -1);
    }
  }

  /**
   * Returns the number of troops of the attacker.
   * @return the number of troops used in the attack
   * @author lkuech
   */
  public String getAttackingPlayer() {
    return attackingPlayer;
  }

  /**
   * Returns the number of troops used by the defendant.
   * @return the number of troops used in the attack
   */
  public String getDefendingPlayer() {
    return defendingPlayer;
  }

  /**
   * Returns the defending country that is being attacked.
   *
   * @return the defending country
   * @author lkuech
   */
  public Country getDefendingCountry() {
    return defendingCountry;
  }

  /**
   * Sets the defending country.
   *
   * @param defendingCountry - the new defending country
   * @author lkuech
   */
  public void setDefendingCountry(Country defendingCountry) {
    this.defendingCountry = defendingCountry;
  }

  /**
   * Returns the attacking country.
   *
   * @return the attacking country
   * @author lkuech
   */
  public Country getAttackingCountry() {
    return attackingCountry;
  }

  /**
   * Sets the attacking country.
   *
   * @param attackingCountry - the new attacking country
   * @author lkuech
   */
  public void setAttackingCountry(Country attackingCountry) {
    this.attackingCountry = attackingCountry;
  }

  /**
   * Returns the owner of the defending country.
   *
   * @return the owner of the defending country
   * @author lkuech
   */
  public String getDefendingCountryOwner() {
    return defendingCountryOwner;
  }

  /**
   * Sets the (new) owner of the attacked country after the attack.
   *
   * @param defendingCountryOwner - the (new) owner of the defending country
   * @author lkuech
   */
  public void setDefendingCountryOwner(String defendingCountryOwner) {
    this.defendingCountryOwner = defendingCountryOwner;
  }
  /**
   * Sets the troop count of the defending country after the attack.
   *
   * @param defendingTroops - the troop count of the defending country after the attack
   * @author lkuech
   */
  public void setDefendingTroops(int defendingTroops) {
    this.defendingTroops = defendingTroops;
  }

  /**
   * Sets attackWon: true if the country was conquered, false if not.
   *
   * @param attackWon - the boolean indicating if the attack was won
   * @author lkuech
   */
  public void setAttackWon(boolean attackWon) {
    this.attackWon = attackWon;
  }

}

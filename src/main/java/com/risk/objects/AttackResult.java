package com.risk.objects;

import com.risk.objects.dice.AttackRoll;
import com.risk.objects.dice.DefenseRoll;

/**
 * This class represents the outcome of one attack. Therefore it calculates the lost troops on both
 * sides.
 *
 * @author lkuech.
 */
public class AttackResult {

  private int attackingTroopsLost;
  private int defendingTroopsLost;

  /**
   * This method returns the number of troops the attacker lost.
   *
   * @param attackRoll This constructor creates a new instance of AttackResult, therefore comparing.
   * @param defenseRoll This constructor creates a new instance of AttackResult, therefore comparing
   *     outcomes of attackRoll and defenseRoll. Then it determines the lost troops for each side by
   *     comparing first and if possible second highest rolls on both sides.
   * @author lkuech
   */
  public AttackResult(AttackRoll attackRoll, DefenseRoll defenseRoll) {
    if (attackRoll.getSecondValue() == -1) {
      this.attackingTroopsLost = attackRoll.getFirstValue() > defenseRoll.getFirstValue() ? 0 : 1;
      this.defendingTroopsLost = 1 - attackingTroopsLost;
    } else if (defenseRoll.getSecondValue() == -1) {
      this.attackingTroopsLost = attackRoll.getFirstValue() > defenseRoll.getFirstValue() ? 0 : 1;
      this.defendingTroopsLost = 1 - attackingTroopsLost;
    } else {
      int attackWin1 = attackRoll.getFirstValue() > defenseRoll.getFirstValue() ? 0 : 1;
      int attackWin2 = attackRoll.getSecondValue() > defenseRoll.getFirstValue() ? 0 : 1;
      this.attackingTroopsLost = attackWin1 + attackWin2;
      this.defendingTroopsLost = 2 - attackingTroopsLost;
    }
  }

  /**
   * returns amount of attacking troops lost due to roll.
   *
   * @return amount of attacking troops lost due to roll
   * @author lkuech
   */
  public int getAttackingTroopsLost() {
    return attackingTroopsLost;
  }

  /**
   * sets amount of attacking troops lost mainly for test purpose.
   *
   * @param attackingTroopsLost.
   * @author lkuech.
   */
  public void setAttackingTroopsLost(int attackingTroopsLost) {
    this.attackingTroopsLost = attackingTroopsLost;
  }

  /**
   * returns amount of defending troops lost due to roll.
   *
   * @return amount of defending troops lost due to roll.
   * @author lkuech.
   */
  public int getDefendingTroopsLost() {
    return defendingTroopsLost;
  }

  /**
   * sets amount of defending troops lost mainly for test purpose.
   *
   * @param defendingTroopsLost amount of defending troops lost due to roll.
   * @author lkuech.
   */
  public void setDefendingTroopsLost(int defendingTroopsLost) {
    this.defendingTroopsLost = defendingTroopsLost;
  }
}

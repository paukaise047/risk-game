package com.risk.objects.dice;

import java.util.ArrayList;

/**
 * This class represents the dice rolled for an attack. It saves the highest and second-highest
 * rolled value. If only one dice was rolled, the second value is -1.
 *
 * @author lkuech.
 */
public class AttackRoll {

  private final int firstValue;
  private final int secondValue;

  /**
   * Creating a new instance of AttackRoll by rolling the amount of dice necassary for the amount of
   * troops and saving the highest one or two values in the class variables.
   *
   * @param troopAmount - the amount of troops attacking.
   * @author lkuech.
   */
  public AttackRoll(int troopAmount) throws IllegalArgumentException {

    ArrayList<Integer> valueList = new ArrayList<>();

    if (troopAmount == 1) {
      valueList.add(Dice.roll());
    } else if (troopAmount == 2) {
      valueList.add(Dice.roll());
      valueList.add(Dice.roll());
    } else if (troopAmount >= 3) {
      valueList.add(Dice.roll());
      valueList.add(Dice.roll());
      valueList.add(Dice.roll());
    } else {
      throw new IllegalArgumentException(
          "The amount of Attacking units must be between greater then one but is " + troopAmount);
    }

    Integer[] values = valueList.toArray(new Integer[0]);

    for (int i = 0; i < values.length - 1; i++) {
      for (int j = i + 1; j < values.length; j++) {
        if (values[i] < values[j]) {
          int temp = values[j];
          values[j] = values[i];
          values[i] = temp;
        }
      }
    }

    if (values.length == 1) {
      firstValue = values[0];
      secondValue = -1;
    } else {
      firstValue = values[0];
      secondValue = values[1];
    }
  }

  /**
   * Returns the highest rolled value as an integer.
   *
   * @return the highest rolled value as an integer.
   * @author lkuech.
   */
  public int getFirstValue() {
    return firstValue;
  }

  /**
   * Returns the second highest rolled value as an integer. If only one dice was rolled, it returns
   * -1.
   *
   * @return the second highest rolled value as an integer.
   * @author lkuech.
   */
  public int getSecondValue() {
    return secondValue;
  }
}

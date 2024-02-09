package com.risk.objects.dice;

import java.util.ArrayList;

/**
 * This class represents the dice rolls of the defending troops. It can be one or two dice rolls.
 *
 * @author hneumann.
 */
public class DefenseRoll {

  private final int firstValue;
  private final int secondValue;

  /**
   * Creates a new entity of DefenseRole by simulting the dice rolls for the existing troops and
   * saving the highest or highest two values in the class variables.
   *
   * @param troopAmount the amount of troops that are defending the country.
   * @throws IllegalArgumentException if the amount of troops is less than one or greater than two.
   * @author lkuech
   */
  public DefenseRoll(int troopAmount) throws IllegalArgumentException {

    ArrayList<Integer> valueList = new ArrayList<>();

    if (troopAmount == 1) {
      valueList.add(Dice.roll());
    } else if (troopAmount >= 2) {
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
   * This method returns the highest rolled value as an integer.
   *
   * @return the highest rolled value as an integer.
   * @author lkuech.
   */
  public int getFirstValue() {
    return firstValue;
  }

  /**
   * This method returns the second highest rolled value as an integer.
   *
   * @return the second highest rolled value as an integer.
   * @author lkuech.
   */
  public int getSecondValue() {
    return secondValue;
  }
}

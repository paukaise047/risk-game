package com.risk.objects.dice;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class DefenseRollTest {

  @Test
  void testOneValue() {
    int loopCount = 10000;
    double count = 0;
    for (int i = 0; i < loopCount; i++) {
      DefenseRoll defenseRoll = new DefenseRoll(1);
      assertEquals(-1, defenseRoll.getSecondValue());
      count += defenseRoll.getFirstValue();
    }
    double epsilon = 0.07;
    double average = count / loopCount;
    assertTrue(Math.abs(average - 3.5) < epsilon);
  }

  @Test
  void testMoreValues(){
    for (int i = 0; i < 1000; i++) {
      DefenseRoll defenseRoll = new DefenseRoll(2);
      assertTrue(defenseRoll.getFirstValue() >= defenseRoll.getSecondValue());
    }
  }

}
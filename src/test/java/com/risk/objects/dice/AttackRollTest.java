package com.risk.objects.dice;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class AttackRollTest {

  @Test
  void testOneValue() {
    int loopCount = 10000;
    double count = 0;
    for (int i = 0; i < loopCount; i++) {
      AttackRoll attackRoll = new AttackRoll(1);
      assertEquals(-1, attackRoll.getSecondValue());
      count += attackRoll.getFirstValue();
    }
    double epsilon = 0.07;
    double average = count / loopCount;
    assertTrue(Math.abs(average - 3.5) < epsilon);
  }

  @Test
  void testMoreValues(){
    for (int i = 0; i < 1000; i++) {
      AttackRoll attackRoll = new AttackRoll(2);
      assertTrue(attackRoll.getFirstValue() >= attackRoll.getSecondValue());
    }
    for (int i = 0; i < 1000; i++) {
      AttackRoll attackRoll = new AttackRoll(3);
      assertTrue(attackRoll.getFirstValue() >= attackRoll.getSecondValue());
    }
  }

}
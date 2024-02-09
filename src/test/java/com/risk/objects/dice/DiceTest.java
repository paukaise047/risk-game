package com.risk.objects.dice;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class DiceTest {

  @Test
  void roll() {
    int loopCount = 10000;
    double count = 0;
    for (int i = 0; i < loopCount; i++) {
      count += Dice.roll();
    }
    double epsilon = 0.07;
    double average = count / loopCount;
    assertTrue(Math.abs(average - 3.5) < epsilon);
  }
}
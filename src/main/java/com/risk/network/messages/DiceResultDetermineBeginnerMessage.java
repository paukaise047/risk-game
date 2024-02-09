package com.risk.network.messages;

import com.risk.network.Message;
import com.risk.network.MessageType;

/**
 * Message class of the DiceResultDetermineBeginnerMessage.
 * @author floribau
 */
public class DiceResultDetermineBeginnerMessage extends Message {

  private final int diceResult;
  private final int playerNumber;

  /**
   * Constructor for the DiceResultDetermineBeginnerMessage.
   * @param diceResult dice result
   * @param playerNumber player number
   * @author floribau
   */
  public DiceResultDetermineBeginnerMessage(int diceResult, int playerNumber) {
    super(MessageType.DICE_RESULT_DETERMINE_BEGINNER_MESSAGE);
    this.diceResult = diceResult;
    this.playerNumber = playerNumber;
  }

  /**
   * getter method for the dice result.
   * @return the dice result
   * @author floribau
   */
  public int getDiceResult() {
    return this.diceResult;
  }

  /**
   * getter method for the player number.
   * @return the player number of the player that rolled the dice
   * @author floribau
   */
  public int getPlayerNumber() {
    return this.playerNumber;
  }
}

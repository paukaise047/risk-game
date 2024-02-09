package com.risk.network.messages;

import com.risk.network.Message;
import com.risk.network.MessageType;

/**
 * This class is the GameStateReceivedMessage Type.
 *
 * @author floribau
 */
public class GameStateReceivedMessage extends Message {

  /**
   * This is the Constructor of the class.
   *
   * @author floribau
   */
  public GameStateReceivedMessage() {
    super(MessageType.GAME_STATE_RECEIVED_MESSAGE);
  }
}

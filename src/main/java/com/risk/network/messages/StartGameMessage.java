package com.risk.network.messages;

import com.risk.network.Message;
import com.risk.network.MessageType;

/**
 * This class represents a message that indicates that the game has started.
 *
 * @author floribau
 */
public class StartGameMessage extends Message {

  /**
   * Constructor for the StartGameMessage.
   *
   * @author floribau
   */
  public StartGameMessage() {
    super(MessageType.START_GAME_MESSAGE);
  }
}

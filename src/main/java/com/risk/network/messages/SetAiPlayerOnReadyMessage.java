package com.risk.network.messages;

import com.risk.network.Message;
import com.risk.network.MessageType;

/**
 * This class represents a message indicating that the aiPlayer sends to tell the Server it is
 * ready.
 *
 * @author floribau
 */
public class SetAiPlayerOnReadyMessage extends Message {

  /**
   * Default constructor of this class.
   *
   * @author floribau
   */
  public SetAiPlayerOnReadyMessage() {
    super(MessageType.SET_AI_PLAYER_ON_READY_MESSAGE);
  }
}

package com.risk.network.messages;

import com.risk.network.Message;
import com.risk.network.MessageType;
/**
 * This class represents a MoveTroopsRequest. It is used to request a move of troops from one
 * country to another.
 *
 * @author lkuech
 */
public class MoveTroopsRequest extends Message {

  /**
   * Constructs a new MoveTroopsRequest.
   *
   * @author lkuech
   */
  public MoveTroopsRequest() {
    super(MessageType.MOVE_TROOPS_REQUEST);
  }
}

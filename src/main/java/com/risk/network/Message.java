package com.risk.network;

import java.io.Serializable;

/**
 * Sets the type of message that is sent.
 *
 * @author floribau
 */
public class Message implements Serializable {

  private static final long serialVersionUID = 1L;
  private final MessageType type;

  /**
   * Initializes a new instance of the Message class.
   *
   * @param type the type of message
   * @author lkuech
   */

  public Message(MessageType type) {
    this.type = type;
  }

  /**
   * Returns the type of message.
   *
   * @author lkuech
   */
  public MessageType getType() {
    return type;
  }
}

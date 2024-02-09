package com.risk.network.messages;

import com.risk.network.Message;
import com.risk.network.MessageType;

/**
 * Message class for the DisconnectMessage.
 *
 * @author lkuech
 */
public class DisconnectMessage extends Message {

  /**
   * Constructor for the DisconnectMessage class. It calls the constructor of the Message class.
   *
   * @author lkuech
   */
  public DisconnectMessage() {
    super(MessageType.DISCONNECT_MESSAGE);
  }
}

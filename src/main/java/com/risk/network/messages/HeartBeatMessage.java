package com.risk.network.messages;

import com.risk.network.Message;
import com.risk.network.MessageType;

/**
 * Message class for the HeartBeatMessage.
 *
 * @author lkuech
 */
public class HeartBeatMessage extends Message {

  /**
   * Constructor for the HeartBeatMessage class.
   *
   * @author lkuech
   */
  public HeartBeatMessage() {
    super(MessageType.HEART_BEAT_MESSAGE);
  }
}

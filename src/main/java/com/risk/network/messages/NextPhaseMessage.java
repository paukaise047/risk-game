package com.risk.network.messages;

import com.risk.network.Message;
import com.risk.network.MessageType;
/**
 * This class represents a NextPhaseMessage. It is used to request the next phase.
 *
 * @author lkuech
 */
public class NextPhaseMessage extends Message {
  /** Constructs a new NextPhaseMessage. */
  public NextPhaseMessage() {
    super(MessageType.NEXT_PHASE_MESSAGE);
  }
}

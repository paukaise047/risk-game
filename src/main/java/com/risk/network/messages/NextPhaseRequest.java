package com.risk.network.messages;

import com.risk.network.Message;
import com.risk.network.MessageType;

/**
 * This class represents a NextPhaseRequest. It is used to request the next phase.
 *
 * @author lkuech
 */
public class NextPhaseRequest extends Message {

  /**
   * Constructs a new NextPhaseRequest.
   *
   * @author lkuech
   */
  public NextPhaseRequest() {
    super(MessageType.NEXT_PHASE_REQUEST);
  }
}

package com.risk.network.messages;

import com.risk.network.Message;
import com.risk.network.MessageType;

/**
 * This class represents a message idicating that the player is now able to trade cards.
 *
 * @author floribau
 */
public class TradeCardsRequest extends Message {

  /**
   * Constructor for the TradeCardsRequest.
   *
   * @author floribau
   */
  public TradeCardsRequest() {
    super(MessageType.TRADE_CARDS_REQUEST);
  }
}

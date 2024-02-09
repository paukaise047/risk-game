package com.risk.network.messages;

import com.risk.network.Message;
import com.risk.network.MessageType;
import com.risk.objects.Card;

/**
 * This class represents a message that contains the cards that the player wants to trade.
 *
 * @author floribau
 */
public class TradeCardsMessage extends Message {

  private Integer[] tradedCardsIds;

  /**
   * Constructor for the TradeCardsMessage.
   *
   * @param tradedCards cards that the player wants to trade
   * @author floribau
   */
  public TradeCardsMessage(Card[] tradedCards) {
    super(MessageType.TRADE_CARDS_MESSAGE);
    if (tradedCards != null) {
      tradedCardsIds = new Integer[3];
      tradedCardsIds[0] = tradedCards[0].getId();
      tradedCardsIds[1] = tradedCards[1].getId();
      tradedCardsIds[2] = tradedCards[2].getId();
    } else {
      tradedCardsIds = null;
    }
  }

  /**
   * Getter for the tradedCards.
   *
   * @return Integer[] cards that the player wants to trade (ids of the cards) or null if the player
   * does not want to trade cards
   * @author lkuech
   */
  public Integer[] getTradedCards() {
    return this.tradedCardsIds;
  }
}

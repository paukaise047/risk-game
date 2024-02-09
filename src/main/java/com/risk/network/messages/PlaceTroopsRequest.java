package com.risk.network.messages;

import com.risk.network.Message;
import com.risk.network.MessageType;

/**
 * Message class for the PlaceTroopsRequest. This class is used to indicate the client to place
 * troops. It contains a list of placements. A placement is a country and the number of troops to
 * place on that country. The client will place the troops on the countries in the list.
 *
 * @author lkuech
 */
public class PlaceTroopsRequest extends Message {

  /**
   * Constructor for the PlaceTroopsRequest class. It calls the constructor of the Message class
   * with the enum MessageType.PLACE_TROOPS_REQUEST.
   *
   * @author lkuech
   */
  public PlaceTroopsRequest() {
    super(MessageType.PLACE_TROOPS_REQUEST);
  }

}

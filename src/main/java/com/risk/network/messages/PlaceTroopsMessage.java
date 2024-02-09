package com.risk.network.messages;

import com.risk.network.Message;
import com.risk.network.MessageType;
import com.risk.objects.Placement;
import java.util.ArrayList;

/**
 * Message class for the PlaceTroopsMessage.
 * This message is used to indicate the client to place troops.
 * It contains a list of placements.
 * A placement is a country and the number of troops to place on that country.
 * The client will place the troops on the countries in the list.
 *
 * @author lkuech
 */
public class PlaceTroopsMessage extends Message {

  ArrayList<Placement> placements;

  /**
   * Constructor for the PlaceTroopsMessage class. It calls the constructor of the Message class with
   * the enum MessageType.PLACE_TROOPS_MESSAGE.
   *
   * @param placements list of placements
   * @author lkuech
   */
  public PlaceTroopsMessage(ArrayList<Placement> placements) {
    super(MessageType.PLACE_TROOPS_MESSAGE);
    this.placements = placements;
  }

  public ArrayList<Placement> getPlacements() {
    return placements;
  }
}

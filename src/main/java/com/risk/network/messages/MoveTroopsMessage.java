package com.risk.network.messages;

import com.risk.network.Message;
import com.risk.network.MessageType;
import com.risk.objects.Country;

/**
 * Message class for the MoveTroopsMessage.
 *
 * @author floribau
 */
public class MoveTroopsMessage extends Message {

  private final Country fromCountry;
  private final Country toCountry;
  private final int troopCount;

  /**
   * Constructor for the MoveTroopsMessage.
   *
   * @param fromCountry - This is the country where the troops move from
   * @param toCountry   - This is the country where the troops move to
   * @param troopCount  - Integer how many troops move
   * @author lkuech
   */
  public MoveTroopsMessage(Country fromCountry, Country toCountry, int troopCount) {
    super(MessageType.MOVE_TROOPS_MESSAGE);
    this.fromCountry = fromCountry;
    this.toCountry = toCountry;
    this.troopCount = troopCount;
  }

  /**
   * This returns the country where the troops are moving from.
   *
   * @author floribau
   */
  public Country getFromCountry() {
    return this.fromCountry;
  }

  /**
   * This returns the country where the troops are moving to.
   *
   * @author floribau
   */
  public Country getToCountry() {
    return this.toCountry;
  }

  /**
   * This returns the troop count.
   *
   * @author floribau
   */
  public int getTroopCount() {
    return this.troopCount;
  }
}

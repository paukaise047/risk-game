package com.risk.network.messages;

import com.risk.network.Message;
import com.risk.network.MessageType;
import com.risk.objects.Country;

/**
 * Message class for the DistributionMessage.
 * @author lkuech
 */
public class DistributionMessage extends Message {

  private final String player;
  private final Country country;

  /**
   * Constructor for the DistributionMessage class. It calls the constructor of the Message class with
   * the enum MessageType.DISTRIBUTION_MESSAGE.
   * @param player the player
   * @param country the country
   * @author floribau
   */
  public DistributionMessage(String player, Country country) {
    super(MessageType.DISTRIBUTION_MESSAGE);
    this.player = player;
    this.country = country;
  }

  /**
   * getter method for the player.
   * @return the player that received the country in the distribution phase
   * @author floribau
   */
  public String getPlayer() {
    return this.player;
  }

  /**
   * getter method for the country.
   * @return the country that was distributed to the player in the distribution phase
   * @author floribau
   */
  public Country getCountry() {
    return this.country;
  }
}

package com.risk.objects;

import java.io.Serializable;

/**
 * This class represents a Placement. A Placement has a country and an amount of troops that are
 * being placed on the country.
 * @author lkuech.
 */
public class Placement implements Serializable {

  private Country country;
  private int troopsPlaced;

  /**
   * Constructor for Placement class.
   *
   * @param country the country that is being placed on.
   * @param troopsPlaced the amount of troops that are being placed on the country.
   * @author lkuech.
   */
  public Placement(Country country, int troopsPlaced) {
    this.troopsPlaced = troopsPlaced;
    this.country = country;
  }

  /**
   * gets the country that is being placed on.
   *
   * @return the country that is being placed on.
   * @author lkuech.
   */
  public Country getCountry() {
    return country;
  }

  /**
   * sets the country that is being placed on.
   *
   * @param country the country that is being placed on.
   * @author hneumann.
   */
  public void setCountry(Country country) {
    this.country = country;
  }

  /**
   * gets the amount of troops that are being placed on the country.
   *
   * @return the amount of troops that are being placed on the country.
   * @author lkuech.
   */
  public int getTroopsPlaced() {
    return troopsPlaced;
  }

  /**
   * sets the amount of troops that are being placed on the country.
   *
   * @param troopsPlaced the amount of troops that are being placed on the country.
   * @author lkuech.
   */
  public void setTroopsPlaced(int troopsPlaced) {
    this.troopsPlaced = troopsPlaced;
  }
}

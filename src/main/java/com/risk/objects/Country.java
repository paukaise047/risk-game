package com.risk.objects;

import com.risk.util.exceptions.IntegerNotPositiveException;
import com.risk.util.exceptions.TroopsOverLimitException;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * This class represents a Country. A Country has a name, a continent, an owner, an amount of troops
 * and a list of neighbours.
 *
 * @author lkuech.
 */
public class Country implements Serializable {

  private final String name;
  private final String continent;
  private String ownerKey;
  private int troops;
  private Country[] neighbours;
  private int buttonIndex;

  /**
   * Constructor of Country class.
   *
   * @param name of the Country
   * @param continent of the Country
   * @author floribau
   */
  public Country(String name, String continent) {
    this.ownerKey = null;
    this.name = name;
    this.continent = continent;
    this.troops = 0;
    this.neighbours = null;
  }

  /**
   * returns the owner of the country.
   *
   * @return owning player of the country, null if not owned by anyone.
   * @author floribau.
   */
  public String getOwner() {
    return this.ownerKey;
  }

  /**
   * set a new owner to the country.
   *
   * @param ownerKey to be set to.
   * @author floribau.
   */
  public void setOwner(String ownerKey) {
    this.ownerKey = ownerKey;
  }

  /**
   * returns the name of the Country.
   *
   * @return name of the Country.
   * @author floribau.
   */
  public String getName() {
    return name;
  }

  /**
   * returns the continent the Country belongs to.
   *
   * @return continent the Country belongs to.
   * @author floribau.
   */
  public String getContinent() {
    return continent;
  }

  /**
   * returns the index of the button that represents the country on the map.
   *
   * @return amount of troops stationed in the country.
   * @author floribau.
   */
  public int getTroops() {
    return troops;
  }

  /**
   * Method called when a whole new player comes to the country.
   *
   * @param troops the count should be set to, should only be positive.
   * @author floribau.
   */
  public void setTroops(int troops) throws IntegerNotPositiveException {
    if (troops >= 0) {
      this.troops = troops;
    } else if (troops < 0) {
      throw new IntegerNotPositiveException();
    }
  }

  /**
   * Method called when updating troop count of a country.
   *
   * @param troops to be added or substracted, can be positive or negative.
   * @throws TroopsOverLimitException if troops goes below 0 after updating.
   * @author floribau.
   */
  public void updateTroops(int troops) throws TroopsOverLimitException {
    int newTroops = this.troops + troops;
    if (newTroops < 0) {
      throw new TroopsOverLimitException();
    }
    this.troops = this.troops + troops;
  }

  /**
   * returns the neighbours of the country.
   *
   * @return array of neighbour countries of the country.
   * @author floribau.
   */
  public Country[] getNeighbours() {
    return neighbours;
  }

  /**
   * sets the neighbours of the country.
   *
   * @param neighbours to set country's neighbours to.
   * @author floribau.
   */
  public void setNeighbours(Country[] neighbours) {
    this.neighbours = neighbours;
  }

  /**
   * checks if given country is neighbour of this country.
   *
   * @param country to check.
   * @return true if the countries are neigbours.
   * @author floribau.
   */
  public boolean isNeighbour(Country country) {
    boolean isNeighbour = false;
    for (Country c : this.neighbours) {
      if (country.equals(c)) {
        isNeighbour = true;
        break;
      }
    }
    return isNeighbour;
  }

  /**
   * returns all neighbouring countries that don't have the same owner.
   *
   * @return list of all neighbouring countries with a different owner.
   * @author floribau.
   */
  public ArrayList<Country> getNeighboursWithDifferentOwner() {
    ArrayList<Country> neighboursWithDifferentOwner = new ArrayList<>();
    for (Country c : this.neighbours) {
      if (c.ownerKey == null) {
        throw new IllegalArgumentException("Country has no owner");
      }
      if (!c.ownerKey.equals(this.ownerKey)) {
        neighboursWithDifferentOwner.add(c);
      }
    }
    return neighboursWithDifferentOwner;
  }

  /**
   * returns all neighbouring countries that have the same owner.
   *
   * @return list of all neighbouring countries with the same owner.
   * @author floribau.
   */
  public ArrayList<Country> getNeighboursWithSameOwner() {
    ArrayList<Country> neighboursWithSameOwner = new ArrayList<>();
    for (Country c : this.neighbours) {
      if (c.ownerKey.equals(this.ownerKey)) {
        neighboursWithSameOwner.add(c);
      }
    }
    return neighboursWithSameOwner;
  }

  /**
   * toString method for Country class.
   *
   * @return String representation of Country class.
   * @author hneumann.
   */
  @Override
  public String toString() {
    return "Country{"
        + "owner="
        + (ownerKey != null ? ownerKey : "NaN")
        + ", name='"
        + name
        + '\''
        + ", continent="
        + continent
        + ", troops="
        + troops
        + '}';
  }

  /**
   * equals method for Country class.
   *
   * @param country to compare to.
   * @return true if the countries have the same name, false otherwise.
   * @author floribau.
   */
  public boolean equals(Country country) {
    return this.name.equals(country.name);
  }

  /**
   * gets the button index of the country.
   *
   * @return the index of the button in the array of buttons in the game view.
   * @author lkuech.
   */
  public int getButtonIndex() {
    return buttonIndex;
  }

  /**
   * sets the button index of the country.
   *
   * @param buttonIndex the index of the button in the array of buttons in the game view.
   * @author lkuech.
   */
  public void setButtonIndex(int buttonIndex) {
    this.buttonIndex = buttonIndex;
  }
}

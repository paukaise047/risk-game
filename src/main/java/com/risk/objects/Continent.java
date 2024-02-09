package com.risk.objects;

import java.io.Serializable;

/**
 * This class represents a Continent. A Continent has a name, a boolean that represents if it is
 * owned, and a String that represents the owner.
 *
 * @author lkuech.
 */
public class Continent implements Serializable {

  private String name;
  private boolean isOwned;
  private String owner;
  /**
   * Constructor for Continent class.
   *
   * @param name of the Continent.
   * @param isOwned boolean that represents if it is owned.
   * @param owner String that represents the owner.
   * @author lkuech.
   */
  public Continent(String name, boolean isOwned, String owner) {
    this.name = name;
    this.isOwned = isOwned;
    this.owner = owner;
  }

  /**
   * gets if the Continent is owned.
   *
   * @return boolean that represents if it is owned.
   * @author lkuech.
   */
  public boolean isOwned() {
    return isOwned;
  }
  /**
   * sets if the Continent is owned.
   *
   * @param owned boolean that represents if it is owned.
   * @author lkuech.
   */
  public void setOwned(boolean owned) {
    isOwned = owned;
  }
  /**
   * gets the owner of the Continent.
   *
   * @return String that represents the owner of the Continent, null if not owned by anyone.
   * @author lkuech.
   */
  public String getOwner() {
    return owner;
  }

  /**
   * sets the owner of the Continent.
   *
   * @param userKey
   * @author lkuech.
   */
  public void setOwner(String userKey) {
    this.owner = userKey;
  }
  /**
   * gets the name of the Continent.
   *
   * @return name of the Continent.
   * @author lkuech.
   */
  public String getName() {
    return name;
  }
  /**
   * sets the name of the Continent.
   *
   * @param name of the Continent to be set to.
   * @author lkuech.
   */
  public void setName(String name) {
    this.name = name;
  }
}

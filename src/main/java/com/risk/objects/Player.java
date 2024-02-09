package com.risk.objects;

import java.io.Serializable;

/**
 * This class represents a Player. A Player has a name, a list of countries, a list of continents, a
 * list of cards and a boolean indicating weather this player is an ai (true) or human (false).
 *
 * @author floribau, hneumann, lkuech.
 */
public class Player implements Serializable {

  private final String userKey;
  private final boolean isAiPlayer;
  private String name;
  private int playerNumber;
  private String playerColour;
  /**
   * Constructor of Player class.
   *
   * @param name - name of the Player.
   * @param userKey - key identifying User, Client and Player.
   * @param isAiPlayer - boolean indicates weather this player is an ai (true) or human (false).
   * @author floribau.
   */
  public Player(String name, String userKey, boolean isAiPlayer) {
    this.name = name;
    this.isAiPlayer = isAiPlayer;
    this.userKey = userKey;
  }

  /**
   * getter Method for the name of the Player
   *
   * @return name of the Player
   * @author hneumann
   */
  public String getName() {
    return name;
  }

  /**
   * setter method fpr the name of the Player
   *
   * @param name - name of the Player to be set to
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * getter method for the Players user key
   *
   * @return user key of the Player
   * @author hneumann
   */
  public String getUserKey() {
    return this.userKey;
  }
  /**
   * getter method for the Players player number.
   *
   * @return player number of the Player as an int, e.g. 1 for the first player in the game.
   * @author hneumann.
   */
  public int getPlayerNumber() {
    return this.playerNumber;
  }
  /**
   * setter method for the Players player number.
   *
   * @param playerNumber - player number of the Player to be set to.
   * @author hneumann.
   */
  public void setPlayerNumber(int playerNumber) {
    this.playerNumber = playerNumber;
  }
  /**
   * getter method for the Players colour.
   *
   * @return colour of the Player as a String (hexadecimal value), e.g. "#ff0000" for red.
   * @author hneumann.
   */
  public String getPlayerColour() {
    return this.playerColour;
  }

  /**
   * setter method for the Players colour.
   *
   * @param colour - colour of the Player to be set to.
   * @author hneumann.
   */
  public void setPlayerColour(String colour) {
    this.playerColour = colour;
  }

  /**
   * return status of player (Ai or human).
   *
   * @return true if Ai player, false if human.
   * @author floribau.
   */
  public boolean isAiplayer() {
    return this.isAiPlayer;
  }

  /**
   * Method to check if a player equals another player by comparing their userKeys.
   *
   * @param c Player to compare to.
   * @return true if the players are equal, false if not.
   * @author hneumann.
   */
  public boolean equals(Player c) {
    // TODO check if this is enough or the right approach
    boolean equals = this.userKey.equals(c.getUserKey()) && this.name.equals(c.getName());
    return equals;
  }

  /**
   * Method prints out information about the player.
   *
   * @return String with information about the player.
   * @author hneumann.
   */
  @Override
  public String toString() {
    return "Player{name: "
        + name
        + ", userKey: "
        + userKey
        + ", playerNumber: "
        + playerNumber
        + ", isAiPlayer: "
        + isAiPlayer
        + "}";
  }
}

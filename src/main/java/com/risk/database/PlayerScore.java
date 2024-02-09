package com.risk.database;

/**
 * This class represents a column from the database table Player.
 *
 * @author lkuech
 */
public class PlayerScore {

  private final String username;
  private final int games;
  private final int score;
  private final int elo;

  /**
   * Constructor for the PlayerScore class.
   *
   * @param username username of the player
   * @param games    number of games played
   * @param score    score of the player
   * @param elo      elo of the player
   * @author lkuech
   */
  public PlayerScore(String username, int games, int score, int elo) {
    this.username = username;
    this.games = games;
    this.score = score;
    this.elo = elo;
  }

  /**
   * Returns the elo of the player.
   *
   * @return elo of the player
   * @author lkuech
   */
  public int getElo() {
    return elo;
  }

  /**
   * Returns the number of games played.
   *
   * @return number of games played
   * @author lkuech
   */
  public int getGames() {
    return games;
  }

  /**
   * Returns the score of the player.
   *
   * @return score of the player
   * @author lkuech
   */
  public int getScore() {
    return score;
  }

  /**
   * Returns the username of the player.
   *
   * @return username of the player
   * @author lkuech
   */
  public String getUsername() {
    return username;
  }
}

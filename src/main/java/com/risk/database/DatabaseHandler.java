package com.risk.database;

import com.risk.game.GameState;
import com.risk.main.User;
import com.risk.objects.Player;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * This class is used to handle the database. It is used to write and read from the database.
 *
 * @author lkuech
 */
public class DatabaseHandler extends Database {

  private PreparedStatement pstm;

  /**
   * Constructor for DatabaseHandler class.
   *
   * @author floribau
   */
  public DatabaseHandler() {
    super();
  }

  /**
   * method to disconnect from the database.
   *
   * @author floribau
   */
  public void disconnect() {
    try {
      if ((pstm != null) && (!pstm.isClosed())) {
        pstm.close();
      }
    } catch (SQLException e) {
      //ignore
    }
    super.disconnect();
  }

  /**
   * This method writes a new column with username and password into the database. It returns true
   * if the column is successfully created and false otherwise.
   *
   * @param username - username of the user
   * @param password - password of the user
   * @return true if created db entry successfully, false if not
   * @author floribau
   */
  public boolean writeUserToDb(String username, String password) {
    if (checkUsernameExistence(username)) {
      return false;
    }
    String prep = "INSERT INTO User ('username', 'password', 'elo') VALUES (?, ?, 25)";
    try (PreparedStatement pstm = connection.prepareStatement(prep)) {
      pstm.setString(1, username);
      pstm.setString(2, password);
      pstm.executeUpdate();
      return true;
    } catch (SQLException e) {
      //ignore
    }
    return false;
  }

  /**
   * Returns the password for the username if the username exists in the database.
   *
   * @param username - the username of which you want to get the corresponding password
   * @return password of the user
   * @author floribau
   */
  public String getPasswordByUsername(String username) {
    try (Statement stm = connection.createStatement()) {
      String sql = "SELECT * FROM User WHERE username = \"" + username + "\"";

      ResultSet rs = stm.executeQuery(sql);
      String password = rs.getString("password");
      stm.close();

      return password;
    } catch (SQLException e) {
      //ignore
    }
    return null;
  }

  /**
   * deletes the specified user from the database.
   *
   * @param username - specifies the user which should be deleted
   * @return true if deleted successfully, false if not
   * @author floribau
   */
  public boolean deleteUser(String username) {
    String prep = "DELETE FROM User WHERE username = \"" + username + "\"";
    try (PreparedStatement pstm = connection.prepareStatement(prep)) {
      pstm.executeUpdate();
      return true;
    } catch (SQLException e) {
      //ignore
    }
    return false;
  }

  /**
   * This method returns the value of the column elo in the row where username has value username.
   *
   * @param username - username of the user
   * @return elo of the user
   * @author lkuech
   */
  public int getEloByUsername(String username) {
    try (Statement stmt = connection.createStatement()) {

      String sql = "SELECT * FROM User WHERE username = \"" + username + "\"";

      ResultSet rs = stmt.executeQuery(sql);
      int elo = rs.getInt("elo");
      stmt.close();
      return elo;

    } catch (SQLException e) {
      //ignore
    }
    return 0;
  }

  /**
   * This method changes the entry value of the column password in the row where username has value
   * username.
   *
   * @param username    - username to change
   * @param newPassword - new password to set in db
   * @author lkuech
   */
  public void changePasswordInDb(String username, String newPassword) {
    try (Statement stmt = connection.createStatement()) {

      String sql =
          "UPDATE User SET password = \""
              + newPassword
              + "\" WHERE username = \""
              + username
              + "\"";

      stmt.executeUpdate(sql);
      stmt.close();

    } catch (SQLException e) {
      //ignore
    }
  }

  /**
   * This method changes the entry value of the column username to newUsername in the row where
   * username hase value username.
   *
   * @param username    - username to change
   * @param newUsername - new username to set in db
   * @author lkuech
   */
  public void changeUsernamedInDb(String username, String newUsername) {
    try (Statement stmt = connection.createStatement()) {

      String sql =
          "UPDATE User SET username = \""
              + newUsername
              + "\" WHERE username = \""
              + username
              + "\"";

      stmt.executeUpdate(sql);
      stmt.close();

    } catch (SQLException e) {
      //ignore
    }
  }

  /**
   * This method returns true if the username is already existing in db. Otherwise, it returns
   * false.
   *
   * @param username - username to check
   * @return true if username is already existing in db, false if not
   * @author lkuech
   */
  public boolean checkUsernameExistence(String username) {
    try (Statement stmt = connection.createStatement()) {
      String sql = "SELECT * FROM User WHERE username = \"" + username + "\"";
      ResultSet rs = stmt.executeQuery(sql);
      if (username.equals(rs.getString("username"))) {
        return true;
      }
    } catch (SQLException e) {
      //ignore
    }
    return false;
  }

  /**
   * This method returns all entry in the table Players ordered by their score.
   *
   * @return PlayerScore[] - array of PlayerScore objects ordered by score (highest score first)
   * @author lkuech
   */
  public PlayerScore[] getPlayersOrderByScore() {
    try (Statement stmt = connection.createStatement()) {
      String sql = "Select * FROM Player ORDER BY score DESC ";
      ResultSet rs = stmt.executeQuery(sql);
      ArrayList<PlayerScore> playersByScore = new ArrayList<PlayerScore>();
      while (rs.next()) {
        String username = rs.getString("username");
        int games = rs.getInt("games");
        int score = rs.getInt("score");
        int elo = rs.getInt("elo");
        playersByScore.add(new PlayerScore(username, games, score, elo));
      }
      rs.close();
      stmt.close();
      PlayerScore[] playerScores = (playersByScore.toArray(new PlayerScore[0]));

      return playerScores;

    } catch (SQLException e) {
      //ignore
    }

    return null;
  }

  /**
   * Method that returns the three players with the highest scores in descending order.
   *
   * @return PlayerScore[] with the three players with the highest scores in descending order.
   * @author lkuech
   */
  public PlayerScore[] getTopThree() {
    try (Statement stmt = connection.createStatement()) {
      String sql = "Select * FROM Player ORDER BY score DESC ";
      ResultSet rs = stmt.executeQuery(sql);
      ArrayList<PlayerScore> playersByScore = new ArrayList<PlayerScore>();
      int j = 0;
      while (rs.next() && j < 3) {
        String username = rs.getString("username");
        int games = rs.getInt("games");
        int score = rs.getInt("score");
        int elo = rs.getInt("elo");
        playersByScore.add(new PlayerScore(username, games, score, elo));
        j++;
      }
      rs.close();
      stmt.close();
      PlayerScore[] playerScores = (playersByScore.toArray(new PlayerScore[0]));

      if (playerScores.length < 3) {
        PlayerScore[] playerScoresFixed = new PlayerScore[3];

        int i = 0;

        while (i < playerScores.length) {
          playerScoresFixed[i] = playerScores[0];
          i++;
        }

        while (i < 3) {
          playerScoresFixed[i] = new PlayerScore(" ", 0, 0, 0);
        }

        return playerScoresFixed;
      }

      return playerScores;

    } catch (SQLException e) {
      //ignore
    }

    return null;
  }

  /**
   * This method returns the names of the three players with the highest scores in descending
   * order.
   *
   * @return ArrayList of the names of the three players with the highest scores in descending
   * order.
   * @author lkuech
   */
  public ArrayList<String> getNamesOfTopThree() {
    try (Statement stmt = connection.createStatement()) {
      String sql = "Select username FROM Player ORDER BY score DESC ";
      ResultSet rs = stmt.executeQuery(sql);
      ArrayList<String> playersByScore = new ArrayList<String>();
      int j = 0;
      while (rs.next() && j < 3) {
        String username = rs.getString("username");
        playersByScore.add(username);
        j++;
      }
      rs.close();
      stmt.close();
      if (playersByScore.size() < 3) {
        ArrayList<String> playerScoresFixed = new ArrayList<>();
        int i = 0;

        while (i < playersByScore.size()) {
          playerScoresFixed.add(i, playersByScore.get(0));
          i++;
        }

        while (i < 3) {
          playerScoresFixed.add(i, " ");
          i++;
        }

        return playerScoresFixed;
      }

      return playersByScore;
    } catch (SQLException e) {
      //ignore
    }
    return null;
  }

  /**
   * This method updates the database with the new scores and elos of the players.
   *
   * @param gameState the result gamestate
   * @author lkuech
   */
  public void updateUsers(GameState gameState) {
    List<Player> players = gameState.getPlayers();
    for (Player player : players) {
      if (!player.isAiplayer()) {
        try {
          String sql = "SELECT * FROM Player WHERE username = \"" + player.getName() + "\"";
          Statement statement = connection.createStatement();
          ResultSet resultSet = statement.executeQuery(sql);
          if (resultSet.next()) {
            ArrayList<String> deadPlayers = gameState.getEliminatedPlayers();
            boolean won = true;
            int place = 5;
            for (int i = deadPlayers.size(); i > 0; i--) {
              if (!deadPlayers.get(i - 1).equals(player.getUserKey())) {
                place--;
              } else {
                won = false;
              }
            }
            if (won) {
              place = 5;
            }

            double factor;
            switch (players.size()) {
              case 2:
                factor = 0.0;
                break;
              case 3:
                factor = 0.25;
                break;
              case 4:
                factor = 0.5;
                break;
              case 5:
                factor = 0.75;
                break;
              case 6:
                factor = 1.0;
                break;
              default:
                factor = 0;
                break;
            }

            double scoreFactor = 0.5 * factor + 0.5;

            int games = resultSet.getInt("games");
            int score = resultSet.getInt("score");
            int elo = resultSet.getInt("elo");

            int newGames = ++games;
            // todo calculate the new score
            int newScore = (int) (score + ((place * 10) * scoreFactor));
            // todo calculate the new elo
            int newElo = newScore / newGames;
            String sql2 =
                "UPDATE Player SET games = "
                    + newGames
                    + " WHERE username = "
                    + "\"" + player.getName() + "\";";
            Statement updateGamesStatement = connection.createStatement();
            statement.executeUpdate(sql2);
            String sql3 =
                "UPDATE Player SET score = " + newScore
                    + " WHERE username = "
                    + "\"" + player.getName() + "\";";
            Statement updateScoreStatement = connection.createStatement();
            statement.executeUpdate(sql3);
            String sql4 =
                "UPDATE Player SET score = " + newElo + " WHERE username = "
                    + "\"" + player.getName() + "\";";
            Statement updateEloStatement = connection.createStatement();
            statement.executeUpdate(sql4);
            statement.close();
            resultSet.close();
            updateGamesStatement.close();
            updateScoreStatement.close();
            updateEloStatement.close();
          } else {
            ArrayList<String> deadPlayers = gameState.getEliminatedPlayers();
            boolean won = true;
            int place = 5;
            for (int i = deadPlayers.size(); i > 0; i--) {
              if (!deadPlayers.get(i - 1).equals(player.getUserKey())) {
                place--;
              } else {
                won = false;
              }
            }
            if (won) {
              place = 5;
            }

            double factor = switch (players.size()) {
              case 2 -> 0.0;
              case 3 -> 0.25;
              case 4 -> 0.5;
              case 5 -> 0.75;
              case 6 -> 1.0;
              default -> 0;
            };

            double scoreFactor = 0.5 * factor + 0.5;

            int newGames = 1;
            // todo calculate the new score
            int newScore = (int) (place * 10 * scoreFactor);
            // todo calculate the new elo
            int newElo = newScore;

            String sql2 =
                "INSERT INTO Player (username, games, score, elo) VALUES ("
                    + "\""
                    + player.getName()
                    + "\", "
                    + newGames
                    + ", "
                    + newScore
                    + ", "
                    + newElo
                    + ");";

            statement.executeUpdate(sql2);

            statement.close();
            resultSet.close();
          }
        } catch (SQLException e) {
          //ignore
        }
      }
    }
  }

  /**
   * This method updates the user's elo after a game.
   *
   * @param gameState the result gameState
   * @author lkuech
   */
  public void updateThisUser(GameState gameState) {
    Player thisUser = User.getUser().getPlayer();

    String sql1 = "SELECT * FROM Player WHERE username = \"" + thisUser.getName() + "\"";

    try (Statement statement1 = connection.createStatement()) {

      ResultSet rs1 = statement1.executeQuery(sql1);

      int elo = rs1.getInt("elo");

      String sql2 =
          "UPDATE User SET elo = " + elo + " WHERE username = \"" + thisUser.getName() + "\";";

      Statement statement2 = connection.createStatement();
      statement2.executeUpdate(sql2);

      rs1.close();

      statement1.close();
      statement2.close();

    } catch (SQLException e) {
      //ignore
    }
  }

  /**
   * This method returns the user's elo, from the database.
   *
   * @return the user's elo
   * @author lkuech
   */
  public int getUserElo() {
    String sql = "SELECT * FROM User WHERE username = \"" + User.getUser().getUsername() + "\"";

    try (Statement statement = connection.createStatement()) {
      ResultSet rs = statement.executeQuery(sql);
      int elo = rs.getInt("elo");
      rs.close();
      statement.close();

      return elo;
    } catch (SQLException e) {
      ////ignore
    }
    return 0;
  }
}

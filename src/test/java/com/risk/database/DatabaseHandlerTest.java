package com.risk.database;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


/**
 * This Test Class tests all methods of DatabaseHandler.java By doing so it's also testing the
 * following Operation Contracts - createAccount(username: String, password: String) -
 * updateAccount() - deleteAccount()
 *
 * @author lgreiner
 * @version 1.0
 */
class DatabaseHandlerTest {

  private static final String username = "testUser";
  private static final String newUsername = "newTestUser";
  private static final String password = "testPassword";
  private static final String newPassword = "newTestPassword";
  DatabaseHandler db;

  /**
   * This method sets up the environment before each test.
   *
   * @author lgreiner
   */
  @BeforeEach
  public void setUp() {
    db = new DatabaseHandler();
  }

  /**
   * This method cleans up the environment after each test.
   *
   * @author lgreiner
   */
  @AfterEach
  public void tearDown() {
    db.disconnect();
  }

  /**
   * This test verifies if the method can successfully write a user to the database.
   *
   * @author lgreiner
   */
  @Test
  public void writeUserToDB() {
    setUp();
    boolean result = db.writeUserToDb(username, password);
    assertTrue(result);
    assertTrue(db.checkUsernameExistence(username));
    db.deleteUser(username);
  }

  /**
   * This test verifies if the method can successfully retrieve the password of a user.
   *
   * @author lgreiner
   */
  @Test
  public void getPasswordByUsername() {
    setUp();
    db.writeUserToDb(username, password);
    String feedback = db.getPasswordByUsername(username);
    assertEquals(password, feedback);
    db.deleteUser(username);
  }

  /**
   * This test verifies if the method can successfully delete a user from the database.
   *
   * @author lgreiner
   */
  @Test
  public void deleteUser() {
    setUp();
    db.writeUserToDb(username, password);
    boolean result = db.deleteUser(username);
    assertTrue(result);
    assertFalse(db.checkUsernameExistence(username));
  }

  /**
   * This test verifies if the method can successfully change the password of a user.
   *
   * @author lgreiner
   */
  @Test
  public void testChangePasswordInDB() {
    setUp();
    db.writeUserToDb(username, password);
    db.changePasswordInDb(username, newPassword);
    String actualPassword = db.getPasswordByUsername(username);
    assertEquals(newPassword, actualPassword);
    db.deleteUser(username);
  }

  /**
   * This test verifies if the method can successfully change the username of a user.
   *
   * @author lgreiner
   */
  @Test
  void changeUsernameInDBTest() {
    setUp();
    db.writeUserToDb(username, password);
    db.changeUsernameInDb(username, newUsername);
    assertTrue(db.checkUsernameExistence(newUsername));
    db.deleteUser(newUsername);
  }

  /**
   * This test verifies if the method can correctly check the existence of a username.
   *
   * @author lgreiner
   */
  @Test
  void checkUsernameExistenceTest() {
    setUp();
    db.writeUserToDb(username, password);
    assertTrue(db.checkUsernameExistence(username));
    db.deleteUser(username);
  }

  /**
   * The test verifies if the method can correctly order players by score This gets verified by
   * checking that each player's score is greater than or equal to the next player's score.
   *
   * @author lgreiner
   */
  @Test
  void getPlayersOrderByScoreTest() {
    setUp();
    // Players are already available
    PlayerScore[] players = db.getPlayersOrderByScore();
    for (int i = 0; i < players.length - 1; i++) {
      assertTrue(players[i].getScore() >= players[i + 1].getScore());
    }
  }
}

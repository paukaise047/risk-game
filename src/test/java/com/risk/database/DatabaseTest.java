package com.risk.database;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.sql.Connection;
import java.sql.SQLException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * This class is testing all methods from the Database.java Class
 *
 * @author lgreiner
 * @version 1.0
 */
class DatabaseTest {

  private Database database;

  /**
   * Set up the test environment by creating a new Database instance.
   *
   * @author lgreiner
   */
  @BeforeEach
  public void setUp() {
    database = new Database();
  }

  /**
   * Clean up the test environment by disconnecting the Database instance.
   *
   * @author lgreiner
   */
  @AfterEach
  public void tearDown() {
    database.disconnect();
  }

  /**
   * Test if the connection is not null after creating a new Database instance.
   *
   * @author lgreiner
   */
  @Test
  public void testConnectionNotNull() {
    Connection connection = database.connection;
    assertNotNull(connection, "Connection should not be null");
  }

  /**
   * Test if the connection is established and points to the specified test database file.
   *
   * @author lgreiner
   */
  @Test
  public void testConnect() {
    String testDbFile = "Test.db";
    database.connect(testDbFile);
    Connection connection = database.connection;
    assertNotNull(connection, "Connection should not be null");
  }

  /**
   * Test if the connection is closed after calling the disconnect method.
   *
   * @author lgreiner
   */
  @Test
  public void testDisconnect() {
    database.disconnect();
    assertThrows(
        SQLException.class,
        () -> database.connection.createStatement(),
        "Connection should be closed");
  }

  /**
   * Test if foreign keys are enabled in the Database instance.
   *
   * @author lgreiner
   */
  @Test
  public void testForeignKeysEnabled() {
    String foreignKeysPragma = database.properties.getProperty("PRAGMA foreign_keys");
    assertEquals("ON", foreignKeysPragma, "Foreign keys should be enabled");
  }
}

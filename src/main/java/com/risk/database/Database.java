package com.risk.database;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Class to create and connect the database.
 *
 * @author floribau
 */
public class Database {

  protected Connection connection;
  protected Properties properties;

  /**
   * Constructor for Database class.
   *
   * @author floribau
   */
  public Database() {

    String userHome = System.getProperty("user.home");
    String databaseDirectory = userHome + File.separator + ".risk9" + File.separator;
    new File(databaseDirectory).mkdirs();
    this.connect(databaseDirectory + "Risk9.db");
    TableCreator tableCreator = new TableCreator(this.connection);

    properties = new Properties();
    properties.setProperty("PRAGMA foreign_keys", "ON");
  }

  /**
   * Method to connect to the database.
   *
   * @param file the file to connect to
   * @author floribau
   */
  public void connect(String file) {
    try {
      Class.forName("org.sqlite.JDBC");
      this.connection = DriverManager.getConnection("jdbc:sqlite:" + file);
    } catch (ClassNotFoundException | SQLException e) {
      //ignore
    }
  }

  /**
   * method to disconnect from the database.
   *
   * @author floribau
   */
  protected void disconnect() {
    try {
      this.connection.close();
    } catch (SQLException e) {
      //ignore
    }
  }
}

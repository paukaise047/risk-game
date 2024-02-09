package com.risk.database;

import java.sql.Connection;
import java.sql.Statement;

/**
 * Class to create the table in the database if it does not exist in case there was no database
 * before.
 *
 * @author lkuech
 */
public class TableCreator {

  /**
   * Constructor for TableCreator class.
   *
   * @param connection the connection to the database
   * @author lkuech
   */
  public TableCreator(Connection connection) {
    createTable(connection);
  }

  /**
   * Method to create the table in the database if it does not exist. It creates a statement with
   * all the columns and executes it.
   *
   * @param connection the connection to the database
   * @author lkuech
   */
  public void createTable(Connection connection) {
    try {
      String sql =
          "CREATE TABLE IF NOT EXISTS User "
              + "(ID INTEGER NOT NULL UNIQUE, "
              + "username TEXT NOT NULL UNIQUE, "
              + "password TEXT NOT NULL, "
              + "elo INTEGER, PRIMARY KEY(ID AUTOINCREMENT))";

      Statement stmt = connection.createStatement();
      stmt.execute(sql);

      String sql2 =
          "CREATE TABLE IF NOT EXISTS Player "
              + "( ID INTEGER NOT NULL UNIQUE, "
              + "username TEXT NOT NULL UNIQUE, "
              + "games INTEGER DEFAULT 0, "
              + "score INTEGER DEFAULT 0, "
              + "elo INTEGER DEFAULT 3,"
              + " PRIMARY KEY(ID AUTOINCREMENT) )";

      Statement stmt2 = connection.createStatement();
      stmt.execute(sql2);

    } catch (Exception e) {
      //ignore
    }
  }
}

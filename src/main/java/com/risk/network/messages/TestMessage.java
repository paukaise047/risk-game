package com.risk.network.messages;

import com.risk.network.Message;
import com.risk.network.MessageType;
import java.util.ArrayList;

/**
 * This class represents a message using to test a connection.
 *
 * @author floribau
 */
public class TestMessage extends Message {

  private final ArrayList<String> tester;

  /**
   * Constructor for the TestMessage.
   *
   * @param tester list of strings to test the connection
   * @author floribau
   */
  public TestMessage(ArrayList<String> tester) {
    super(MessageType.TEST_MESSAGE);
    this.tester = tester;
  }

  /**
   * Getter for the tester.
   *
   * @return ArrayList<String> list of strings to test the connection
   * @auther floribau
   */
  public ArrayList<String> getTester() {
    return tester;
  }
}

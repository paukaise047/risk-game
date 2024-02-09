package com.risk.network.messages;

import com.risk.network.Message;
import com.risk.network.MessageType;

/**
 * This class represents a message that contains the user information.
 *
 * @author floribau
 */
public class UserInfoMessage extends Message {

  private final String userKey;
  private final String username;
  private final boolean isAi;

  /**
   * Constructor for the UserInfoMessage.
   *
   * @param userKey userKey of the user
   * @param username username of the user
   * @author floribau
   */
  public UserInfoMessage(String userKey, String username) {
    super(MessageType.USER_INFO_MESSAGE);
    this.userKey = userKey;
    this.username = username;
    this.isAi = false;
  }

  /**
   * Constructor for the UserInfoMessage.
   *
   * @param userKey userKey of the user
   * @param username username of the user
   * @param isAi boolean if the user is an ai or not
   * @author floribau
   */
  public UserInfoMessage(String userKey, String username, boolean isAi) {
    super(MessageType.USER_INFO_MESSAGE);
    this.userKey = userKey;
    this.username = username;
    this.isAi = isAi;
  }

  /**
   * Getter for the userKey.
   *
   * @return String userKey of the user
   * @author floribau
   */
  public String getUserKey() {
    return this.userKey;
  }

  /**
   * Getter for the username.
   *
   * @return String username of the user
   * @author floribau
   */
  public String getUsername() {
    return this.username;
  }

  /**
   * Getter for the isAi.
   *
   * @return boolean if the user is an ai or not
   * @author floribau
   */
  public boolean getIsAi() {
    return this.isAi;
  }
}

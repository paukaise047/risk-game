package com.risk.network.messages;

import com.risk.network.Message;
import com.risk.network.MessageType;

/**
 * Message class for the GameControllerAddedToPlayerMessage.
 * @author floribau
 */
public class GameControllerAddedToPlayerMessage extends Message {

  /**
   * Constructor for the GameControllerAddedToPlayerMessage.
   * @author floribau
   */
  public GameControllerAddedToPlayerMessage() {
    super(MessageType.GAME_CONTROLLER_ADDED_TO_PLAYER_MESSAGE);
  }
}

package com.risk.network.messages;

import com.risk.network.Message;
import com.risk.network.MessageType;

/**
 * This class is the message send to the clients when a new player is ready in lobby.
 *
 * @author floribau
 */
public class PlayersReadyUpdateMessage extends Message {

  private final boolean gameReady;

  /**
   * The constructor of this class.
   *
   * @param gameReady boolean indicating if the game can be started.
   * @author floribau
   */
  public PlayersReadyUpdateMessage(boolean gameReady) {
    super(MessageType.PLAYERS_READY_UPDATE_MESSAGE);
    this.gameReady = gameReady;
  }

  /**
   * Getter for the is game ready.
   *
   * @return boolean indicating if the game can start.
   * @author floribau
   */
  public boolean isGameReady() {
    return this.gameReady;
  }
}

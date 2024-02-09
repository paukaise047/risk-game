package com.risk.network.messages;

import com.risk.network.Message;
import com.risk.network.MessageType;
import java.util.ArrayList;

/**
 * This class represents a message that contains information about the players in the lobby.
 *
 * @author floribau
 */
public class UpdatePlayersLobbyMessage extends Message {

  private final ArrayList<String> userNames = new ArrayList<>();

  /**
   * Constructor for the UpdatePlayersLobbyMessage.
   *
   * @param userNames list of usernames of the players in the lobby
   * @author floribau
   */
  public UpdatePlayersLobbyMessage(ArrayList<String> userNames) {
    super(MessageType.UPDATE_PLAYERS_LOBBY_MESSAGE);
    this.userNames.addAll(userNames);
  }

  /**
   * Getter for the usernames.
   *
   * @return ArrayList<String> usernames of the players in the lobby
   * @author floribau
   */
  public ArrayList<String> getUserNames() {
    return this.userNames;
  }
}

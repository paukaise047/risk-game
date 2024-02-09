package com.risk.network.messages;

import com.risk.game.GameState;
import com.risk.network.Message;
import com.risk.network.MessageType;

/**
 * This class represents a message that contains the updated game state.
 *
 * @author floribau
 */
public class UpdatedGameStateMessage extends Message {

  private final GameState gs;

  /**
   * Constructor for the UpdatedGameStateMessage.
   *
   * @param gs updated GameState of the game
   * @author floribau
   */
  public UpdatedGameStateMessage(GameState gs) {
    super(MessageType.UPDATED_GAME_STATE_MESSAGE);
    this.gs = gs;
  }

  /**
   * Getter for the current ameState.
   *
   * @return GameState updated GameState of the game
   * @author floribau
   */
  public GameState getGameState() {
    return gs;
  }
}
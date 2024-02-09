package com.risk.network.messages;

import com.risk.game.GameState;
import com.risk.network.Message;
import com.risk.network.MessageType;

/**
 * This class is used to indicate the client that the game is over and sends the final game state.
 *
 * @author Lkuech
 */
public class GameOverMessage extends Message {

  private GameState gameState;

  /**
   * This is the constructor for the GameOverMessage class. It calls the constructor of the Message
   * class with the enum MessageType.GAME_OVER_MESSAGE.
   *
   * @author lkuech
   */
  public GameOverMessage(GameState gameState) {
    super(MessageType.GAME_OVER_MESSAGE);
    this.gameState = gameState;
  }

  /**
   * This method returns the game state.
   *
   * @return the game state
   * @Author lkuech
   */
  public GameState getGameState() {
    return gameState;
  }

  /**
   * This method sets the game state.
   *
   * @param gameState the game state
   * @author Lkuech
   */
  public void setGameState(GameState gameState) {
    this.gameState = gameState;
  }
}

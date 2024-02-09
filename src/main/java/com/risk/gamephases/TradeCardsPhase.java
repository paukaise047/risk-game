package com.risk.gamephases;

import com.risk.game.GameState;
import com.risk.gui.GameController;
import com.risk.main.User;
import com.risk.objects.Card;
import com.risk.objects.Player;
import com.risk.objects.enums.CardSymbol;
import java.util.List;

public class TradeCardsPhase extends Phase {

  private final User user = User.getUser();
  private final Player player;
  private final List<Card> cardsOwnedByPlayer;
  private GameController gameController;
  private Card[] cardsTurnedIn = new Card[3];
  private boolean finishedPhase = false;
  private GameState gameState;

  public TradeCardsPhase(GameState gameState) {
    this.player = gameState.getPlayerByUserKey(gameState.getCurrentPlayerKey());
    this.cardsOwnedByPlayer = gameState.getCardsByPlayerKey(
        player.getUserKey());
    this.gameController = user.getGameController();
    tradeCards();
  }

  public TradeCardsPhase(GameState gameState, boolean isAi) {
    this.player = gameState.getPlayerByUserKey(gameState.getCurrentPlayerKey());
    this.cardsOwnedByPlayer = gameState.getCardsByPlayerKey(player.getUserKey());
    this.gameController = null;
    this.gameState = gameState;
    tradeAiCards();
  }

  public Card[] getCardsTurnedIn() {
    return this.cardsTurnedIn;
  }

  /**
   * Method that checks if a trade is possible.
   * @return boolean if trade is possible
   */
  public boolean checkTradePossible() {
    if (cardsOwnedByPlayer.size() < 3) {
      return false;
    }
    int countInfantryCards = 0;
    int countCavalryCards = 0;
    int countCannonCards = 0;
    int countWildCards = 0;

    for (Card c : cardsOwnedByPlayer) {
      switch (c.getSymbol()) {
        case Infantry -> countInfantryCards++;
        case Cavalry -> countCavalryCards++;
        case Cannon -> countCannonCards++;
        case Wildcard -> countWildCards++;
      }
    }

    boolean threeSame =
        countInfantryCards >= 3
            || countCavalryCards >= 3
            || countCannonCards >= 3
            || countWildCards >= 3;
    boolean twoSamePlusWildcard =
        (countInfantryCards >= 2 && countWildCards >= 1)
            || (countCavalryCards >= 2 && countWildCards >= 1)
            || (countCannonCards >= 2 && countWildCards >= 1)
            || (countWildCards >= 2);
    boolean allThreeDifferent =
        (countInfantryCards >= 1 || countWildCards >= 1)
            && (countCavalryCards >= 1 || countWildCards >= 1)
            && (countCannonCards >= 1 || countWildCards >= 1);
    return threeSame || twoSamePlusWildcard || allThreeDifferent;
  }

  /**
   * method that trades the cards and interacts with the gui.
   * @author floribau
   */
  private void tradeCards() {
    if (checkTradePossible()) {

      selectCardsToTradeFromGui();
    } else {

      cardsTurnedIn = null;
    }
    finishedPhase = true;

  }

  /**
   * method that sets the cards selected from the gui.
   * @author floribau
   */
  public void selectCardsToTradeFromGui() {
    cardsTurnedIn = gameController.tradeCardsSelection(player.getUserKey(),
        cardsOwnedByPlayer.size() < 5);
  }

  /**
   * Method that sets the cardsTrunedIn.
   * @author hneumann
   */
  public void tradeAiCards() {
    int countInfantryCards = 0;
    int countCavalryCards = 0;
    int countCannonCards = 0;
    int countWildCards = 0;
    for (Card c : cardsOwnedByPlayer) {
      switch (c.getSymbol()) {
        case Infantry -> countInfantryCards++;
        case Cavalry -> countCavalryCards++;
        case Cannon -> countCannonCards++;
        case Wildcard -> countWildCards++;
      }
    }
    if (checkTradePossible()) {
      if (countInfantryCards >= 3) {
        this.cardsTurnedIn = gameState.getCardsByPlayerBySymbol(player.getUserKey(),
            CardSymbol.Infantry).toArray(this.cardsTurnedIn);

      } else if (countCannonCards >= 3) {
        this.cardsTurnedIn = gameState.getCardsByPlayerBySymbol(player.getUserKey(),
            CardSymbol.Cannon).toArray(this.cardsTurnedIn);

      } else if (countCavalryCards >= 3) {
        this.cardsTurnedIn = gameState.getCardsByPlayerBySymbol(player.getUserKey(),
            CardSymbol.Cavalry).toArray(this.cardsTurnedIn);

      } else if (countCannonCards >= 2 && countWildCards >= 1) {
        this.cardsTurnedIn[0] = gameState.getCardsByPlayerBySymbol(player.getUserKey(),
            CardSymbol.Cannon).get(0);
        this.cardsTurnedIn[1] = gameState.getCardsByPlayerBySymbol(player.getUserKey(),
            CardSymbol.Cannon).get(1);
        this.cardsTurnedIn[2] = gameState.getCardsByPlayerBySymbol(player.getUserKey(),
            CardSymbol.Wildcard).get(0);

      } else if (countCavalryCards >= 2 && countWildCards >= 1) {
        this.cardsTurnedIn[0] = gameState.getCardsByPlayerBySymbol(player.getUserKey(),
            CardSymbol.Cavalry).get(0);
        this.cardsTurnedIn[1] = gameState.getCardsByPlayerBySymbol(player.getUserKey(),
            CardSymbol.Cavalry).get(1);
        this.cardsTurnedIn[2] = gameState.getCardsByPlayerBySymbol(player.getUserKey(),
            CardSymbol.Wildcard).get(0);

      } else if (countInfantryCards >= 2 && countWildCards >= 1) {
        this.cardsTurnedIn[0] = gameState.getCardsByPlayerBySymbol(player.getUserKey(),
            CardSymbol.Infantry).get(0);
        this.cardsTurnedIn[1] = gameState.getCardsByPlayerBySymbol(player.getUserKey(),
            CardSymbol.Infantry).get(1);
        this.cardsTurnedIn[2] = gameState.getCardsByPlayerBySymbol(player.getUserKey(),
            CardSymbol.Wildcard).get(0);

      } else if (countWildCards >= 2) {
        this.cardsTurnedIn[0] = gameState.getCardsByPlayerBySymbol(player.getUserKey(),
            CardSymbol.Wildcard).get(0);
        this.cardsTurnedIn[1] = gameState.getCardsByPlayerBySymbol(player.getUserKey(),
            CardSymbol.Wildcard).get(1);
        this.cardsTurnedIn[2] =
            (countInfantryCards >= 1) ? gameState.getCardsByPlayerBySymbol(player.getUserKey(),
                CardSymbol.Infantry).get(0)
                : (countCavalryCards >= 1) ? gameState.getCardsByPlayerBySymbol(player.getUserKey(),
                    CardSymbol.Cavalry).get(0)
                    : gameState.getCardsByPlayerBySymbol(player.getUserKey(),
                        CardSymbol.Cannon).get(0);

      } else if (countInfantryCards >= 1 && countCavalryCards >= 1 && countCannonCards >= 1) {
        this.cardsTurnedIn[0] = gameState.getCardsByPlayerBySymbol(player.getUserKey(),
            CardSymbol.Infantry).get(0);
        this.cardsTurnedIn[1] = gameState.getCardsByPlayerBySymbol(player.getUserKey(),
            CardSymbol.Cavalry).get(0);
        this.cardsTurnedIn[2] = gameState.getCardsByPlayerBySymbol(player.getUserKey(),
            CardSymbol.Cannon).get(0);

      } else if (countWildCards >= 1 && countCannonCards >= 1 && countCavalryCards >= 1) {
        this.cardsTurnedIn[0] = gameState.getCardsByPlayerBySymbol(player.getUserKey(),
            CardSymbol.Wildcard).get(0);
        this.cardsTurnedIn[1] = gameState.getCardsByPlayerBySymbol(player.getUserKey(),
            CardSymbol.Cavalry).get(0);
        this.cardsTurnedIn[2] = gameState.getCardsByPlayerBySymbol(player.getUserKey(),
            CardSymbol.Cannon).get(0);
      } else if (countWildCards >= 1 && countCannonCards >= 1 && countInfantryCards >= 1) {
        this.cardsTurnedIn[0] = gameState.getCardsByPlayerBySymbol(player.getUserKey(),
            CardSymbol.Wildcard).get(0);
        this.cardsTurnedIn[1] = gameState.getCardsByPlayerBySymbol(player.getUserKey(),
            CardSymbol.Infantry).get(0);
        this.cardsTurnedIn[2] = gameState.getCardsByPlayerBySymbol(player.getUserKey(),
            CardSymbol.Cannon).get(0);
      } else if (countWildCards >= 1 && countInfantryCards >= 1 && countCavalryCards >= 1) {
        this.cardsTurnedIn[0] = gameState.getCardsByPlayerBySymbol(player.getUserKey(),
            CardSymbol.Wildcard).get(0);
        this.cardsTurnedIn[1] = gameState.getCardsByPlayerBySymbol(player.getUserKey(),
            CardSymbol.Infantry).get(0);
        this.cardsTurnedIn[2] = gameState.getCardsByPlayerBySymbol(player.getUserKey(),
            CardSymbol.Cavalry).get(0);
      }
    }
  }

  /**
   * Boolean that returns if the phase is finished.
   * @return finishedPhase boolean
   * @author floribau
   */
  public boolean isFinishedPhase() {
    return this.finishedPhase;
  }
}

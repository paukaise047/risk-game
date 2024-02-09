package com.risk.gamephases;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.risk.game.GameState;
import com.risk.gui.GameController;
import com.risk.main.User;
import com.risk.objects.Card;
import com.risk.objects.Player;
import java.util.ArrayList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TradeCardsPhaseTest {

  private GameState testGameState;
  private String playerOneKey;
  private GameController mockGameController;

  @BeforeEach
  void setup() {
    User user = User.getUser();
    mockGameController = mock(GameController.class);
    try {
      user.addGameController(mockGameController);
    } catch (NullPointerException e) {
      // ignore server-client communication
    }

    ArrayList<Player> players = new ArrayList<>();
    for (int i = 0; i < 2; i++) {
      players.add(new Player("testPlayer" + (i + 1), User.generateKey(), true));
    }
    playerOneKey = players.get(0).getUserKey();
    testGameState = new GameState(playerOneKey, players);

    testGameState.updateCountryOwner(testGameState.getCountries().get(0), playerOneKey, 1);
    testGameState.updateCardOwner(0, playerOneKey);
    testGameState.updateCardOwner(1, playerOneKey);
    testGameState.updateCardOwner(43, playerOneKey);

    user.setGameState(testGameState);
  }

  @Test
  void testTradeCardsPhase() {
    Card[] tradedCardsMock = new Card[3];
    tradedCardsMock[0] = testGameState.getCardById(0);
    tradedCardsMock[1] = testGameState.getCardById(1);
    tradedCardsMock[2] = testGameState.getCardById(43);

    when(mockGameController.tradeCardsSelection(playerOneKey, true)).thenReturn(tradedCardsMock);

    TradeCardsPhase testPhase = new TradeCardsPhase(testGameState);
    assertNotNull(testPhase.getCardsTurnedIn());
    assertEquals(testPhase.getCardsTurnedIn()[0], testGameState.getCardById(0));
    assertEquals(testPhase.getCardsTurnedIn()[1], testGameState.getCardById(1));
    assertEquals(testPhase.getCardsTurnedIn()[2], testGameState.getCardById(43));
    assertTrue(testPhase.checkTradePossible());

    when(mockGameController.tradeCardsSelection(playerOneKey, true)).thenReturn(tradedCardsMock);

    TradeCardsPhase testPhase2 = new TradeCardsPhase(testGameState);
    assertNotNull(testPhase2.getCardsTurnedIn());
  }

  @Test
  void checkTradeCardsPhaseAi() {
    TradeCardsPhase testPhaseAi = new TradeCardsPhase(testGameState, true);
    testPhaseAi.tradeAiCards();
    if (!testPhaseAi.checkTradePossible()) {
      fail("Trade impossible");
    }
    if (testPhaseAi.getCardsTurnedIn() == null) {
      fail("Cards not turned in");
    }
    for (Card card : testPhaseAi.getCardsTurnedIn()) {
      if (card == null) {
        fail("Card is null");
      }
    }
    assertNotNull(testPhaseAi.getCardsTurnedIn());
    assertTrue(testPhaseAi.checkTradePossible());
    assertEquals(testPhaseAi.getCardsTurnedIn()[0], testGameState.getCardById(43));
    assertEquals(testPhaseAi.getCardsTurnedIn()[1], testGameState.getCardById(0));
    assertEquals(testPhaseAi.getCardsTurnedIn()[2], testGameState.getCardById(1));
  }
}
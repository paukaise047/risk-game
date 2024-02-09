package com.risk.game;

import com.risk.gamephases.Phase;
import com.risk.objects.Card;
import com.risk.objects.Continent;
import com.risk.objects.Country;
import com.risk.objects.Placement;
import com.risk.objects.Player;
import com.risk.objects.enums.CardSymbol;
import com.risk.objects.enums.GamePhase;
import com.risk.util.CardList;
import com.risk.util.ContinentList;
import com.risk.util.CountryList;
import com.risk.util.exceptions.IntegerNotPositiveException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * This class represents the state of the game. It contains all the information that is needed to
 * play the game and to save and load the game.
 *
 * @author hneumann
 */
public class GameState implements Serializable {

  private final List<Player> players;
  private final ArrayList<String> eliminatedPlayers;
  private final CountryList countryList = new CountryList();
  private final List<Continent> continentList;
  private final List<Card> cards = new CardList(countryList).getAllCards();
  private Player currentPlayer;
  private String eliminatedPlayer;
  private ArrayList<Country> countries;
  private ArrayList<Country> unownedCountries;
  private List<Card> untakenCards = new ArrayList<>();
  private int countCardsTurnedIn;
  private int troopsToDistribute;
  private int troopsFromTradingCards = 0;
  private GamePhase gamePhase;
  private Phase phase;
  private int currentPlayerIndex = 0;
  private boolean firstAttack;
  private String lastAttackingCountry = null;
  private String lastDefendingCountry = null;

  /**
   * Empty GameState constructor.
   *
   * @author hneumann
   */
  public GameState() {
    this.currentPlayer = null;
    this.players = new ArrayList<>();
    this.eliminatedPlayers = new ArrayList<>();
    this.eliminatedPlayer = null;
    this.countries = null;
    this.continentList = new ContinentList().getAllContinents();
    this.unownedCountries = new ArrayList<>();
    this.troopsToDistribute = -1;
    this.gamePhase = null;
    this.untakenCards.addAll(cards);
  }

  /**
   * GameState constructor that takes the current player and the list of all players as parameters.
   *
   * @param currentPlayerKey - the userKey representing player that will be current player in the
   *                         upcoming phase
   * @param players          - list of all players in the correct order: Player 1 is on index 0 and
   *                         so on
   * @author hneumann
   */
  public GameState(String currentPlayerKey, List<Player> players) {

    this.players = players;
    this.eliminatedPlayers = new ArrayList<>();
    this.eliminatedPlayer = null;
    this.unownedCountries = new ArrayList<>();
    this.countries = countryList.getAllCountries();
    switch (players.size()) {
      case 2 -> this.troopsToDistribute = 40;
      case 3 -> this.troopsToDistribute = 35;
      case 4 -> this.troopsToDistribute = 30;
      case 5 -> this.troopsToDistribute = 25;
      case 6 -> this.troopsToDistribute = 20;
      default -> this.troopsToDistribute = -1;
    }
    this.gamePhase = GamePhase.DISTRIBUTION_PHASE;
    this.countCardsTurnedIn = 0;
    this.continentList = new ContinentList().getAllContinents();
    this.currentPlayer = getPlayerByUserKey(currentPlayerKey);
    this.untakenCards.addAll(cards);
  }

  /**
   * This method creates a new GameState for the tutorial. It sets the gamePhase to
   * DistributionPhase, while most countries are owned by the AI or the player.
   *
   * @return GameState the GameState for the tutorial
   * @author lkuech
   */
  public static GameState createTutorialGameState() {
    GameState tutorialGameState = new GameState();

    tutorialGameState.setGamePhase(GamePhase.DISTRIBUTION_PHASE);

    CountryList countryList1 = new CountryList();
    Player tutorialBoss = new Player("Coco", "Coco", false);
    Player tutorialPlayer = new Player("Bob", "Bob", false);

    tutorialGameState.getPlayers().add(tutorialPlayer);
    tutorialGameState.getPlayers().add(tutorialBoss);

    tutorialGameState.drawCardForPlayer(tutorialPlayer.getUserKey());
    tutorialGameState.drawCardForPlayer(tutorialPlayer.getUserKey());
    tutorialGameState.drawCardForPlayer(tutorialPlayer.getUserKey());
    tutorialGameState.drawCardForPlayer(tutorialPlayer.getUserKey());
    tutorialGameState.drawCardForPlayer(tutorialPlayer.getUserKey());

    try {
      countryList1.getCountryByName("Alaska").setOwner(tutorialBoss.getUserKey());
      countryList1.getCountryByName("Alaska").setTroops(1);
      countryList1.getCountryByName("Alberta").setOwner(tutorialBoss.getUserKey());
      countryList1.getCountryByName("Alberta").setTroops(1);
      countryList1.getCountryByName("CentralAmerica").setOwner(tutorialBoss.getUserKey());
      countryList1.getCountryByName("CentralAmerica").setTroops(1);
      countryList1.getCountryByName("Ontario").setOwner(tutorialBoss.getUserKey());
      countryList1.getCountryByName("Ontario").setTroops(1);
      countryList1.getCountryByName("EasternUnitedStates").setOwner(tutorialBoss.getUserKey());
      countryList1.getCountryByName("EasternUnitedStates").setTroops(1);
      countryList1.getCountryByName("WesternUnitedStates").setOwner(tutorialBoss.getUserKey());
      countryList1.getCountryByName("WesternUnitedStates").setTroops(1);
      countryList1.getCountryByName("Venezuela").setOwner(tutorialBoss.getUserKey());
      countryList1.getCountryByName("Venezuela").setTroops(1);
      countryList1.getCountryByName("Peru").setOwner(tutorialBoss.getUserKey());
      countryList1.getCountryByName("Peru").setTroops(1);
      countryList1.getCountryByName("Ukraine").setOwner(tutorialBoss.getUserKey());
      countryList1.getCountryByName("Ukraine").setTroops(1);
      countryList1.getCountryByName("NorthernEurope").setOwner(tutorialBoss.getUserKey());
      countryList1.getCountryByName("NorthernEurope").setTroops(1);
      countryList1.getCountryByName("Scandinavia").setOwner(tutorialBoss.getUserKey());
      countryList1.getCountryByName("Scandinavia").setTroops(1);
      countryList1.getCountryByName("Kamchatka").setOwner(tutorialBoss.getUserKey());
      countryList1.getCountryByName("Kamchatka").setTroops(1);
      countryList1.getCountryByName("Yakutsk").setOwner(tutorialBoss.getUserKey());
      countryList1.getCountryByName("Yakutsk").setTroops(1);
      countryList1.getCountryByName("Siberia").setOwner(tutorialBoss.getUserKey());
      countryList1.getCountryByName("Siberia").setTroops(1);
      countryList1.getCountryByName("Ural").setOwner(tutorialBoss.getUserKey());
      countryList1.getCountryByName("Ural").setTroops(1);
      countryList1.getCountryByName("Afghanistan").setOwner(tutorialBoss.getUserKey());
      countryList1.getCountryByName("Afghanistan").setTroops(1);
      countryList1.getCountryByName("Irkutsk").setOwner(tutorialBoss.getUserKey());
      countryList1.getCountryByName("Irkutsk").setTroops(1);
      countryList1.getCountryByName("EasternAustralia").setOwner(tutorialBoss.getUserKey());
      countryList1.getCountryByName("EasternAustralia").setTroops(1);
      countryList1.getCountryByName("WesternAustralia").setOwner(tutorialBoss.getUserKey());
      countryList1.getCountryByName("WesternAustralia").setTroops(1);

      countryList1.getCountryByName("NorthWestTerritory").setOwner(tutorialPlayer.getUserKey());
      countryList1.getCountryByName("NorthWestTerritory").setTroops(1);
      countryList1.getCountryByName("Greenland").setOwner(tutorialPlayer.getUserKey());
      countryList1.getCountryByName("Greenland").setTroops(1);
      countryList1.getCountryByName("Quebec").setOwner(tutorialPlayer.getUserKey());
      countryList1.getCountryByName("Quebec").setTroops(1);
      countryList1.getCountryByName("Brazil").setOwner(tutorialPlayer.getUserKey());
      countryList1.getCountryByName("Brazil").setTroops(1);
      countryList1.getCountryByName("Argentina").setOwner(tutorialPlayer.getUserKey());
      countryList1.getCountryByName("Argentina").setTroops(1);
      countryList1.getCountryByName("Iceland").setOwner(tutorialPlayer.getUserKey());
      countryList1.getCountryByName("Iceland").setTroops(1);
      countryList1.getCountryByName("GreatBritain").setOwner(tutorialPlayer.getUserKey());
      countryList1.getCountryByName("GreatBritain").setTroops(1);
      countryList1.getCountryByName("WesternEurope").setOwner(tutorialPlayer.getUserKey());
      countryList1.getCountryByName("WesternEurope").setTroops(1);
      countryList1.getCountryByName("NorthAfrica").setOwner(tutorialPlayer.getUserKey());
      countryList1.getCountryByName("NorthAfrica").setTroops(1);
      countryList1.getCountryByName("Congo").setOwner(tutorialPlayer.getUserKey());
      countryList1.getCountryByName("Congo").setTroops(1);
      countryList1.getCountryByName("SouthAfrica").setOwner(tutorialPlayer.getUserKey());
      countryList1.getCountryByName("SouthAfrica").setTroops(1);
      countryList1.getCountryByName("Madagascar").setOwner(tutorialPlayer.getUserKey());
      countryList1.getCountryByName("Madagascar").setTroops(1);
      countryList1.getCountryByName("Mongolia").setOwner(tutorialPlayer.getUserKey());
      countryList1.getCountryByName("Mongolia").setTroops(1);
      countryList1.getCountryByName("Japan").setOwner(tutorialPlayer.getUserKey());
      countryList1.getCountryByName("Japan").setTroops(1);
      countryList1.getCountryByName("China").setOwner(tutorialPlayer.getUserKey());
      countryList1.getCountryByName("China").setTroops(1);
      countryList1.getCountryByName("Siam").setOwner(tutorialPlayer.getUserKey());
      countryList1.getCountryByName("Siam").setTroops(1);
      countryList1.getCountryByName("India").setOwner(tutorialPlayer.getUserKey());
      countryList1.getCountryByName("India").setTroops(1);
      countryList1.getCountryByName("Indonesia").setOwner(tutorialPlayer.getUserKey());
      countryList1.getCountryByName("Indonesia").setTroops(1);
      countryList1.getCountryByName("NewGuinea").setOwner(tutorialPlayer.getUserKey());
      countryList1.getCountryByName("NewGuinea").setTroops(1);

    } catch (IntegerNotPositiveException e) {
      // ignore
    }
    tutorialGameState.setCountries(countryList1.getAllCountries());

    return tutorialGameState;
  }

  /**
   * This method updates the owner of a country and the troops on it. If the country was previously
   * unowned it is removed from the unowned list. It checks if now a whole continent is owned by a
   * player and updates the players continentList.
   *
   * @param country  - the country to be updated
   * @param ownerKey - the key representing the new owner of the country
   * @param troops   - the new amount of troops on the country
   * @author hneumann
   */
  public void updateCountryOwner(Country country, String ownerKey, int troops) {
    try {
      String oldOwner = country.getOwner();
      Player owner = getPlayerByUserKey(ownerKey);
      if (country.getOwner() != null && !country.getOwner()
          .equals(ownerKey)) {
        country.setOwner(ownerKey);
        country.setTroops(troops);
        // this.getCountriesOwnedByPlayer(oldOwner.getUserKey()).remove(country);
      } else {
        country.setOwner(owner.getUserKey());
        this.unownedCountries.remove(country);
        country.setTroops(troops);
      }
    } catch (Exception e) {
      // ignore
    }
  }

  /**
   * getter method for a card by id
   *
   * @param id the id of the card
   * @return the card with the given id or null if no card with this id exists in the game
   * @author floribau
   */
  public Card getCardById(int id) {
    for (Card c : this.cards) {
      if (c.getId() == id) {
        return c;
      }
    }
    return null;
  }

  /**
   * getter method for the list of eliminated players
   *
   * @return the list of eliminated players
   * @author floribau
   */
  public ArrayList<String> getEliminatedPlayers() {
    return this.eliminatedPlayers;
  }

  /**
   * This method updates the owner of a card.
   *
   * @param cardId  the id of the card to be updated
   * @param userKey the key representing the new owner of the card
   * @author floribau
   */
  public void updateCardOwner(int cardId, String userKey) {

    Card card = getCardById(cardId);
    String oldOwner = card.getOwner();
    card.setOwner(userKey);
    if (oldOwner == null) {
      untakenCards.remove(card);
    }
    if (oldOwner != null && userKey == null) {
      putBackCard(cardId);
    }
  }

  /**
   * method that puts back a card from a player.
   *
   * @param cardId the id of the card to be put back
   * @author floribau
   */
  public void putBackCard(int cardId) {
    Card card = getCardById(cardId);
    card.setOwner(null);
    untakenCards.add(card);
  }

  /**
   * getter method for the cards of a player.
   *
   * @param userKey the userKey of the player
   * @return the cards of the player with the given userKey
   * @author floribau
   */
  public ArrayList<Card> getCardsByPlayerKey(String userKey) {
    ArrayList<Card> playerCards = new ArrayList<>();
    for (Card c : cards) {
      if (c.getOwner() != null && c.getOwner().equals(userKey)) {
        playerCards.add(c);
      }
    }
    return playerCards;
  }

  /**
   * Method that returns the current phase object of the game.
   *
   * @return the current player
   * @author floribau
   */
  public Phase getPhase() {
    return this.phase;
  }

  /**
   * getter method for the troops resulting from trading cards.
   *
   * @return the troops resulting from trading cards
   * @author floribau
   */
  public int getTroopsFromTradingCards() {
    return this.troopsFromTradingCards;
  }

  /**
   * returns the current GamePhase enum.
   *
   * @return the current GamePhase enum
   * @author floribau
   */
  public GamePhase getGamePhase() {
    return this.gamePhase;
  }

  /**
   * setter method for the current phase.
   *
   * @param gamePhase current phase of the game to be set to
   * @author floribau
   */
  public void setGamePhase(GamePhase gamePhase) {
    this.gamePhase = gamePhase;
  }

  /**
   * sets the firstAttack boolean to true.
   *
   * @author floribau
   */
  public void setFirstAttack() {
    this.firstAttack = true;
  }

  /**
   * resets the firstAttack boolean to false.
   *
   * @author floribau
   */
  public void resetFirstAttack() {
    this.firstAttack = false;
  }

  /**
   * returns true if the current attack is the first attack of the game.
   *
   * @return true if the current attack is the first attack of the game, false otherwise
   * @author floribau
   */
  public boolean isFirstAttack() {
    return this.firstAttack;
  }

  /**
   * decreases the troopsToDistribute counter for DistributionPhase by 1.
   *
   * @author floribau
   */
  public void decTroopsToDistribute() {
    this.troopsToDistribute--;
  }

  /**
   * returns the current value of the troopsToDistribute counter for Distribution phase.
   *
   * @return troopsToDistribute counter for Distribution phase as int value
   * @author floribau
   */
  public int getTroopsToDistribute() {
    return this.troopsToDistribute;
  }

  /**
   * Method that checks if a player has been eliminated from the game.
   *
   * @param playerKey the player to be checked
   * @return true if the player has been eliminated, false if not
   * @author floribau
   */
  public boolean checkPlayerEliminated(String playerKey) {
    return getCountriesOwnedByPlayer(playerKey).size() == 0;
  }

  /**
   * checks if any player owns all 42 countries.
   *
   * @return true if one player won, false if not
   * @author floribau
   */
  public boolean checkGameOver() {
    for (Player player : players) {
      if (getCountriesOwnedByPlayer(player.getUserKey()).size() == 42) {
        return true;
      }
    }
    return false;
  }

  /**
   * returns the winner of the game if the game is over.
   *
   * @return player that won the game, null if game is not over
   * @author floribau
   */
  public Player getWinner() {
    for (Player player : players) {
      if (getCountriesOwnedByPlayer(player.getUserKey()).size() == 42) {
        return player;
      }
    }
    return null;
  }

  /**
   * returns all countries owned by the player, specified by its userKey.
   *
   * @param userKey - userKey of the player
   * @return ArrayList containing all countries owned by the player
   * @author floribau
   */
  public ArrayList<Country> getCountriesOwnedByPlayer(String userKey) {
    ArrayList<Country> ownedCountries = new ArrayList<>();
    for (Country country : this.countries) {
      if (country.getOwner() != null && country.getOwner().equals(userKey)) {
        ownedCountries.add(country);
      }
    }
    return ownedCountries;
  }

  /**
   * getter method for the current player.
   *
   * @return currentPlayer
   * @author lkuech
   */
  public String getCurrentPlayerKey() {
    return this.currentPlayer.getUserKey();
  }

  /**
   * getter Method for the CircularList of Players.
   *
   * @return players CircularList of Players
   * @author lkuech
   */
  public List<Player> getPlayers() {
    return players;
  }

  /**
   * getter method for a player bei its userKey.
   *
   * @param userKey userKey of the player
   * @return player with the userKey, null if no player with this userKey exists
   * @author floribau
   */
  public Player getPlayerByUserKey(String userKey) {
    for (Player p : players) {
      if (p.getUserKey().equals(userKey)) {
        return p;
      }
    }
    return null;
  }

  /**
   * sets the currentPlayer and currentPlayerIndex to the next player in the list, if this new
   * currentPlayer is already eliminated, the method recalls itself.
   *
   * @author floribau
   */
  public void nextPlayer() {
    int index = ++currentPlayerIndex % players.size();
    if (index == 0 && gamePhase.equals(GamePhase.DISTRIBUTION_PHASE)) {
      decTroopsToDistribute();
    }
    this.currentPlayer = this.players.get(index);
    if (this.eliminatedPlayers.contains(getCurrentPlayerKey())) {
      nextPlayer();
    }
  }

  /**
   * returns total amount of troops on the board.
   *
   * @return total amount of troops on the board
   * @author lkuech
   */
  public int getTroopsOnBoard() {
    int count = 0;
    for (Player player : players) {
      count += this.getTroopsByPlayer(player.getUserKey());
    }
    return count;
  }

  /**
   * method to get the amount of troops owned by a player.
   *
   * @param userKey
   * @return
   */
  public int getTroopsByPlayer(String userKey) {
    int count = 0;
    for (Country country : this.getCountriesOwnedByPlayer(userKey)) {
      count += country.getTroops();
    }
    return count;
  }

  /**
   * Method that returns the list of unowned countries.
   *
   * @return list of unowned countries
   * @author lkuech
   */
  public ArrayList<Country> getUnownedCountries() {
    return unownedCountries;
  }

  /**
   * Method that sets the list of unowned countries.
   *
   * @param unownedCountries list of unowned countries to be set to the GameState
   * @author lkuech
   */
  public void setUnownedCountries(ArrayList<Country> unownedCountries) {
    this.unownedCountries = unownedCountries;
  }

  /**
   * Method that returns the list of countries.
   *
   * @return list of countries
   * @author hneumann
   */
  public ArrayList<Country> getCountries() {
    return countries;
  }

  /**
   * Method that moves the troops from one country to another country and updates the amount of
   * troops in both countries.
   *
   * @param fromCountry country to move troops from
   * @param toCountry country to move troops to
   * @author hneumann
   */
  public void moveTroops(Country fromCountry, Country toCountry) {
    try {
      toCountry.setTroops(toCountry.getTroops() + fromCountry.getTroops() - 1);
      fromCountry.setTroops(1);
    } catch (Exception e) {
      // ignore
    }
  }

  /**
   * Method that updates the amount of troops in a country.
   *
   * @param country country to be updated
   * @param troops  amount of troops to be set
   * @author hneumann
   */
  public void updateTroops(Country country, int troops) {
    try {
      getCountryByName(country.getName()).setTroops(troops);
    } catch (Exception e) {
      // ignore
    }
  }

  /**
   *
   /**
   * Method that returns the amount of troops that are available to be distributed in the next
   * DistributionPhase.
   *
   * @param i amount of troops to be set to the GameState
   * @author hneumann
   */
  public void setTroopsToDistribute(int i) {
    this.troopsToDistribute = i;
  }

  /**
   * Method that evaluates how much of a continent is already captured by other players.
   *
   * @param continent continent to be evaluated
   * @return  double between 0 and 1, 0 if no country of the continent is captured, 1 if all
   * countries are captured
   * @author hneumann
   */
  public double getContinentCaptureRate(String continent) throws NullPointerException {
    // Method that calculates how much of a continent is already captured and hwo many countries do
    // not have an owner yet
    double captured = 0;
    double total = 0;
    for (Country country : this.countries) {
      if (country.getContinent().equals(continent)) {
        total++;
        if (country.getOwner() != null
            && !country.getOwner().equals(this.currentPlayer.getUserKey())) {
          captured++;
        }
      }
    }
    return captured / total;
  }

  /**
   * Method that returns a country by its name.
   *
   * @param name name of the country
   * @return Country with the given name, null if no country with the given name exists
   * @author hneumann
   */
  public Country getCountryByName(String name) {
    for (Country country : this.countries) {
      if (country.getName().equalsIgnoreCase(name)) {
        return country;
      }
    }
    return null;
  }

  /**
   * Method that returns a list of all countries that are in a continent.
   *
   * @param continent continent to be evaluated
   * @return list of countries that are in the continent, empty list if no country of the continent
   * is captured yet
   * @author hneumann
   */
  public List<Country> getCountriesByContinent(String continent) {
    List<Country> countries = new ArrayList<>();
    for (Country country : this.countries) {
      if (country.getContinent().equals(continent)) {
        countries.add(country);
      }
    }
    return countries;
  }

  /**
   * updates the GameState after the server received a DistributionMessage.
   *
   * @param playerKey - the playerKey that distributed a troop
   * @param country   - the country a troop was distributed to
   * @author lkuech
   */
  public void updateGameStateDistributionPhase(String playerKey, Country country) {
    for (Country c : this.countries) {
      if (c.getName().equals(country.getName())) {
        updateCountryOwner(c, playerKey, c.getTroops() + 1);
        unownedCountries.remove(c);
      }
    }
  }

  public void updateGameStateTradeCardsPhase(String playerKey, Integer[] cardsTradedIds) {
    Card[] cardsTraded = new Card[3];
    cardsTraded[0] = getCardById(cardsTradedIds[0]);
    cardsTraded[1] = getCardById(cardsTradedIds[1]);
    cardsTraded[2] = getCardById(cardsTradedIds[2]);
    switch (countCardsTurnedIn) {
      case 0 -> troopsFromTradingCards += 4;
      case 1 -> troopsFromTradingCards += 6;
      case 2 -> troopsFromTradingCards += 8;
      case 3 -> troopsFromTradingCards += 10;
      case 4 -> troopsFromTradingCards += 12;
      case 5 -> troopsFromTradingCards += 15;
      default -> troopsFromTradingCards += (countCardsTurnedIn - 2) * 5;
    }
    countCardsTurnedIn++;
    for (Card c : cardsTraded) {
      if (getCountriesOwnedByPlayer(playerKey).contains(c.getCountry())) {
        updateCountryOwner(c.getCountry(), playerKey, c.getCountry().getTroops() + 2);
      }
      putBackCard(c.getId());
    }
  }

  public void drawCardForPlayer(String playerKey) {
    int cardIndex = (int) (Math.random() * untakenCards.size());
    Card c = untakenCards.get(cardIndex);
    updateCardOwner(c.getId(), playerKey);
  }

  /**
   * updates the GameState after the server received a PlaceTroopsMessage.
   *
   * @param placements - a list of countries + amount of troops the current player distributed their
   *                   troops to
   * @author lkuech
   */
  public void updateGameStatePlaceTroopPhase(ArrayList<Placement> placements) {
    resetTroopsFromTradingCards();
    for (Placement p : placements) {
      Country country = this.getCountryByName(p.getCountry().getName());
      try {
        country.setTroops(country.getTroops() + p.getTroopsPlaced());
      } catch (Exception e) {
        // ignore
      }
    }
  }

  /**
   * updates the GameState after the server received a AttackMoveMessage.
   *
   * @param defendingCountry       - the country that is being attacked
   * @param attackingCountry       - the country that is attacking
   * @param defendingCountryOwner  - the owner of the defending country
   * @param defendingCountryTroops - the amount of troops the defending country has
   * @param attackingCountryTroops - the amount of troops the attacking country has
   * @author hneumann
   */
  public void updateGameStateAttackMove(
      Country defendingCountry,
      Country attackingCountry,
      String defendingCountryOwner,
      int defendingCountryTroops,
      int attackingCountryTroops) {
    this.eliminatedPlayer = null;
    updateCountryOwner(
        getCountryByName(defendingCountry.getName()),
        defendingCountryOwner,
        defendingCountryTroops);
    updateTroops(attackingCountry, attackingCountryTroops);
    if (attackingCountry.getOwner().equals(defendingCountryOwner) && isFirstAttack()) {
      drawCardForPlayer(getCurrentPlayerKey());
      resetFirstAttack();
    }
    lastAttackingCountry = attackingCountry.getName();
    lastDefendingCountry = defendingCountry.getName();
  }

  /**
   * Method that eliminates the player that was attacked, if he lost his last country and updates
   * the GameState accordingly.
   *
   * @param attackingPlayer  the player that attacked
   * @param eliminatedPlayer the player that was eliminated
   */
  public void eliminatePlayer(String attackingPlayer, String eliminatedPlayer) {
    this.eliminatedPlayer = eliminatedPlayer;
    this.eliminatedPlayers.add(eliminatedPlayer);
    for (Card c : getCardsByPlayerKey(eliminatedPlayer)) {
      updateCardOwner(c.getId(), attackingPlayer);
    }
  }

  /**
   * Method that resets the eliminated player to null.
   *
   * @author floribau
   */
  public void resetEliminatedPlayer() {
    this.eliminatedPlayer = null;
  }

  /**
   * Method that resets the troops from trading cards to 0.
   *
   * @author floribau
   */
  public void resetTroopsFromTradingCards() {
    this.troopsFromTradingCards = 0;
  }

  /**
   * Method that resets the attacking and defending countries of the last attack move.
   *
   * @author floribau
   */
  public void resetLastAttackMoveCountries() {
    this.lastDefendingCountry = null;
    this.lastAttackingCountry = null;
  }

  /**
   * Method that returns the last attacking country.
   *
   * @return last attacking country
   * @author floribau
   */
  public String getLastAttackingCountry() {
    return this.lastAttackingCountry;
  }

  /**
   * Method that returns the last defending country.
   *
   * @return last defending country
   * @author floribau
   */
  public String getLastDefendingCountry() {
    return this.lastDefendingCountry;
  }

  /**
   * Method that returns a deep clone of the current GameState.
   *
   * @return clone of the current GameState
   * @author floribau
   */
  public GameState cloneGameState() {
    GameState gs = new GameState();
    try {
      gs.gamePhase = this.gamePhase;
      gs.firstAttack = this.firstAttack;
      gs.troopsToDistribute = this.troopsToDistribute;
      gs.troopsFromTradingCards = this.troopsFromTradingCards;
      gs.countCardsTurnedIn = this.countCardsTurnedIn;
      gs.lastAttackingCountry = this.lastAttackingCountry;
      gs.lastDefendingCountry = this.lastDefendingCountry;

      CountryList countryList = new CountryList();
      gs.countries = countryList.getAllCountries();
      for (Country c : this.countries) {
        Country countryByName = gs.getCountryByName(c.getName());
        countryByName.setTroops(c.getTroops());
        countryByName.setOwner(c.getOwner());
        if (countryByName.getOwner() == null) {
          gs.unownedCountries.add(countryByName);
        }
      }

      gs.untakenCards = new ArrayList<>();
      for (Card oldCard : this.cards) {
        Card newCard = gs.getCardById(oldCard.getId());
        if (oldCard.getOwner() != null) {
          newCard.setOwner(oldCard.getOwner());
        } else {
          gs.untakenCards.add(newCard);
        }
      }

      for (Continent oldContinent : this.continentList) {
        for (Continent newContinent : gs.continentList) {
          if (oldContinent.isOwned() && oldContinent.getName().equals(newContinent.getName())) {
            newContinent.setOwner(oldContinent.getOwner());
          }
        }
      }

      for (Player player : this.players) {
        Player newPlayer = new Player(player.getName(), player.getUserKey(), player.isAiplayer());
        newPlayer.setPlayerNumber(player.getPlayerNumber());
        newPlayer.setPlayerColour(player.getPlayerColour());
        gs.players.add(newPlayer);
      }
      gs.setCurrentPlayer(this.getCurrentPlayerKey());
      gs.eliminatedPlayers.addAll(this.eliminatedPlayers);
      gs.eliminatedPlayer = eliminatedPlayer;
    } catch (Exception e) {
      // ignore
    }
    return gs;
  }

  /**
   * Method that resets the ownerships of each continent.
   *
   * @author hneumann
   */
  private void setCountries(ArrayList<Country> list) {
    this.countries = list;
  }

  /**
   * Method that resets the ownerships of each continent
   *
   * @author hneumann
   */
  public void setContinentOwner() {
    for (Continent continent : this.continentList) {
      boolean isOwned = true;
      String owner = null;
      for (Country country : getCountriesByContinent(continent.getName())) {
        if (owner == null) {
          owner = country.getOwner();
        } else if (!owner.equals(country.getOwner())) {
          isOwned = false;
          break;
        }
      }
      if (isOwned && !(owner == null)) {
        continent.setOwner(owner);
        continent.setOwned(true);
      } else {
        continent.setOwned(false);
        continent.setOwner(null);
      }
    }
  }


  /**
   * Method that sets the current phase
   * @param phase
   */
  public void setPhase(Phase phase) {
    this.phase = phase;
  }

  /**
   * toString method for GameState
   *
   * @return String representation of GameState
   * @author hneumann
   */
  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("GameState: \n");
    for (Player player : this.getPlayers()) {
      sb.append(player.getName()).append(", ");
    }
    sb.append("\nCountries per Continent:\n");
    for (Continent continent : continentList) {
      sb.append(continent).append(" => Countries: ");
      for (Country country : this.getCountriesByContinent(continent.getName())) {
        sb.append(country).append(", ");
      }
      sb.append("\n");
      sb.append(continent).append(" Continent Owner: ").append(continent.getOwner()).append("\n");
    }
    sb.append("Cards:\n");
    for (Player player : this.getPlayers()) {
      sb.append("Player ").append(player.getName()).append(" owns Cards (count: ")
          .append(getCardsByPlayerKey(player.getUserKey()).size()).append("): ");
      for (Card c : getCardsByPlayerKey(player.getUserKey())) {
        sb.append(c).append(", ");
      }
      sb.append("\n");
    }
    sb.append("Untaken Cards: ").append(untakenCards.size()).append("\n");
    sb.append("All Cards: ").append(cards.size());
    sb.append("\n");
    return sb.toString();
  }

  /**
   * Method that returns a List of continents owned by a player.
   *
   * @param userKey userKey of the player
   * @return List of continents owned by the player with the given userKey
   * @author hneumann
   */
  public ArrayList<Continent> getContinentsOwnedByPlayer(String userKey) {
    ArrayList<Continent> continentsOwnedByPlayer = new ArrayList<>();
    for (Continent continent : this.continentList) {
      if (continent.getOwner() != null) {
        if (continent.getOwner().equals(userKey)) {
          continentsOwnedByPlayer.add(continent);
        }
      }
    }
    return continentsOwnedByPlayer;
  }

  /**
   * This method sets the current player to the given player.
   *
   * @param currentPlayerKey the player to be set as the current player
   * @author hneumann
   */
  public void setCurrentPlayer(String currentPlayerKey) {
    this.currentPlayer = getPlayerByUserKey(currentPlayerKey);
    this.currentPlayerIndex = this.players.indexOf(currentPlayer);
  }

  public List<Continent> getContinents() {
    return this.continentList;
  }

  /**
   * Getter method for the cards a player owns filtered by the CardSymbol.
   *
   * @param userKey    userKey of the player
   * @param cardSymbol CardSymbol of the cards
   * @return List of cards owned by the player with the given userKey and the given CardSymbol
   * @author hneumann
   */
  public List<Card> getCardsByPlayerBySymbol(String userKey, CardSymbol cardSymbol) {
    List<Card> cardArrayList = new ArrayList<>();
    for (Card card : this.cards) {
      if (card.getOwner() == null) {
        continue;
      }
      if (card.getOwner().equals(userKey) && card.getSymbol().equals(cardSymbol)) {
        cardArrayList.add(card);
      }
    }
    return cardArrayList;
  }
}

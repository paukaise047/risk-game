package com.risk.ai;

import com.risk.ai.util.AttackHeuristics;
import com.risk.ai.util.BorderSecurity;
import com.risk.game.GameState;
import com.risk.main.User;
import com.risk.objects.Continent;
import com.risk.objects.Country;
import com.risk.objects.Placement;
import com.risk.objects.Player;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

/**
 * Class that represents an AIPlayer. It extends the Player class and adds the functionality of an
 * AIPlayer. It has a difficulty level that can be set and a strategy that is used to calculate the
 * next move.
 *
 * @author hneumann
 */
public class AiPlayer extends Player {

  private static int count = 1;
  private final int difficulty;
  public List<String> nameList = new ArrayList<>();


  /**
   * Constructor of Player class. It sets the name of the Player and generates a userKey.
   *
   * @param name       - name of the Player
   * @param difficulty - difficulty of the AIPlayer
   * @author hneumann
   */
  public AiPlayer(String name, int difficulty) {
    super(name, User.generateKey(), true);
    this.difficulty = difficulty;
  }

  /**
   * Constructor of Player class. It sets the name of the Player and generates a userKey.
   *
   * @param name       - name of the Player
   * @param userKey    - userKey of the Player
   * @param difficulty - difficulty of the AIPlayer
   * @author hneumann
   */
  public AiPlayer(String name, String userKey, int difficulty) {
    super(name, userKey, true);
    this.difficulty = difficulty;
    this.nameList = List.of(
        "Jack",
        "Coco",
        "Sammy",
        "Momo",
        "Peanut",
        "Bobo",
        "Bella",
        "Charlie",
        "Lola");
  }

  public static void decreaseCount() {
    count--;
  }


  /**
   * Method that updates the gameState and gives it back. It calculates which country to conquer
   * based on a given strategy and the current gameState. If all countries are conquered it will
   * calculate where to place the remaining troops using the distributeTroops method.
   *
   * @param gameState - current gameState
   * @return resulting gameState after the AIPlayer has made its move
   * @author hneumann
   */
  public Country distributeTroops(GameState gameState) {

    try {
      if (gameState.getUnownedCountries().size() > 0) {
        double distributionThreshold = 0.7;
        if (gameState.getContinentCaptureRate("Australia") < distributionThreshold) {
          if (gameState.getCountryByName("Indonesia").getOwner() == null) {
            //TODO souts entfernen
            return gameState.getCountryByName("Indonesia");
          } else {
            for (Country country :
                gameState.getCountriesByContinent("Australia")) {
              if (country.getOwner() == null) {
                return country;
              }
            }
          }
        }
        if (gameState.getContinentCaptureRate("SouthAmerica")
            < distributionThreshold) {
          if (gameState.getCountryByName("Venezuela").getOwner() == null) {
            return gameState.getCountryByName("Venezuela");
          } else if (gameState.getCountryByName("Brazil").getOwner() == null) {
            return gameState.getCountryByName("Brazil");
          } else {
            for (Country country :
                gameState.getCountriesByContinent("SouthAmerica")) {
              if (country.getOwner() == null) {
                return country;
              }
            }
          }
        }
        if (gameState.getContinentCaptureRate("Africa") < distributionThreshold) {
          for (Country country :
              gameState.getCountriesByContinent("Africa")) {
            if (country.getOwner() == null) {
              return gameState.getCountryByName(country.getName());
            }
          }
        }

        Country toConquer = calcCountryToConquer(gameState.cloneGameState());
        /*
        if (gameState.getContinentCaptureRate("Australia") > distributionThreshold
            && gameState.getCountryByName("Indonesia").getOwner().equals(this)
            && gameState.getCountryByName("Siam").getOwner() == null) {
          toConquer = gameState.getCountryByName("Siam");
        } else if (gameState.getCountryByName("Venezuela").getOwner().equals(this)
            && gameState.getContinentCaptureRate("SouthAmerica") > distributionThreshold
            && gameState.getCountryByName("CentralAmerica").getOwner() == null) {
          toConquer = gameState.getCountryByName("CentralAmerica");
        } else if (gameState.getCountryByName("Egypt").getOwner().equals(this)
            && gameState.getContinentCaptureRate("Africa") > distributionThreshold
            && gameState.getCountryByName("SouthernEurope").getOwner() == null) {
          toConquer = gameState.getCountryByName("SouthernEurope");
        } else if ((gameState.getCountryByName("Egypt").getOwner().equals(this)
            || gameState.getCountryByName("EastAfrica").getOwner().equals(this))
            && gameState.getContinentCaptureRate("Africa") > distributionThreshold
            && gameState.getCountryByName("MiddleEast").getOwner() == null) {
          toConquer = gameState.getCountryByName("MiddleEast");
        } else if (gameState.getCountryByName("NorthAfrica").getOwner().equals(this)
            && gameState.getContinentCaptureRate("Australia") > distributionThreshold
            && gameState.getCountryByName("WesternEurope").getOwner() == null) {
          toConquer = gameState.getCountryByName("WesternEurope");
        }

        */
        if (toConquer != null
            && gameState.getCountryByName(toConquer.getName()).getOwner() == null) {

          return gameState.getCountryByName(toConquer.getName());
        }
      } else {
        return this.reinforcement(gameState, 1).get(0).getCountry();

      }
    } catch (Exception e) {
      //ignore
    }
    return null;
  }

  /**
   * Method that randomly chooses a country to conquer. It is used if the AIPlayer has no strategy
   *
   * @param gameState - current gameState
   * @return Country to conquer
   * @author hneumann
   */
  public Country distributeRandomCountry(GameState gameState) {
    List<Country> countries = gameState.getUnownedCountries();
    if (countries.size() == 0 && countries != null) {
      int rdm = (int) (Math.random() * gameState.getCountriesOwnedByPlayer(this.getUserKey())
          .size());
      return gameState.getCountriesOwnedByPlayer(this.getUserKey()).get(rdm);
    }
    int random = (int) (Math.random() * countries.size());
    return countries.get(random);
  }

  /**
   * Method that returns a random country to attack from the AI player.
   *
   * @param gameState - current gameState
   * @return List of countries to attack from and to attack to
   * @author hneumann
   */
  public List<Country> attackRandomCountry(GameState gameState) {
    List<Country>[] attacks = new List[2];
    attacks[0] = new ArrayList<>();
    attacks[1] = new ArrayList<>();
    for (Country country : gameState.getCountriesOwnedByPlayer(this.getUserKey())) {
      for (Country neighbour : country.getNeighbours()) {
        if (Tree.checkMove(country, neighbour)) {
          attacks[0].add(country);
          attacks[1].add(neighbour);
        }
      }
    }
    List<Country> attack = new ArrayList<>();
    int rdm = (int) (Math.random() * attacks[0].size());
    attack.add(attacks[0].get(rdm));
    attack.add(attacks[1].get(rdm));
    return attack;
  }

  /**
   * Method to calculate the country to conquer based on the heuristic of the AttackHeuristics. Each
   * available country is cloned and the heuristic is calculated. The country with the highest
   * heuristic is returned.
   *
   * @param gameState - current gameState
   * @return Country to conquer
   * @author hneumann
   */
  private Country calcCountryToConquer(GameState gameState) {
    //method that calculates which country to conquer based on the Heuristics
    Country result = null;
    double max = -1;
    try {

      GameState gs = gameState.cloneGameState();
      for (Country country : gameState.getUnownedCountries()) {
        if (country.getOwner() == null) {
          gs.updateCountryOwner(gs.getCountryByName(country.getName()), this.getUserKey(), 1);
          double tmp = AttackHeuristics.calcHeuristic4(gs);
          if (tmp > max) {
            max = tmp;
            result = gameState.getCountryByName(country.getName());
          }
        }
      }
    } catch (Exception e) {
      //ignore
    }
    return result;
  }

  /**
   * Method that handles the calculation od the tree and returns the best move.
   *
   * @param gameState - current gameState
   * @return List of countries to attack from and to attack to
   * @author hneumann
   */

  public List<Node> attack(GameState gameState) {
    Tree tree = new Tree(new Node(gameState, 0));

    return tree.calcBestMove();

  }

  /**
   * Method to distribute troops to countries. The amount of troops is calculated by the
   * BorderSecurity of the country.
   *
   * @param amount - amount of troops to distribute
   * @author hneumann
   */
  public List<Placement> reinforcement(GameState gameState, int amount) {
    List<Placement> placements = new ArrayList<>();
    TreeMap<Country, Double> countryProbs = new TreeMap<>((Country o1, Country o2) -> {
      if (BorderSecurity.normalizedBorderSecurityRatio(o1, User.getUser().getGameState())
          > BorderSecurity.normalizedBorderSecurityRatio(o2, User.getUser().getGameState())) {
        return -1;
      } else {
        return 1;
      }
    });
    try {
      GameState gs = gameState.cloneGameState();
      //berechnen von nbsr für jedes Land
      for (Country country : gameState.getCountriesOwnedByPlayer(this.getUserKey())) {
        double nbsr = BorderSecurity.normalizedBorderSecurityRatio(country, gs);
        countryProbs.put(country, nbsr);  //countryProbs ist eine Map mit Ländern und deren nbsr
      }
      for (Country country :
          countryProbs.keySet()) {
        double troop = ((0.4175 * amount) + (1 - ((0.4175 * amount) % 1)));
        amount -= troop;
        Placement placement = new Placement(gameState.getCountryByName(country.getName()),
            (int) troop);
        placements.add(placement);
        if (amount <= 0) {
          break;
        }
      }
    } catch (Exception e) {
      //ignore
    }
    return placements;
  }

  /**
   * Method to calculate how many troops to move from one country to another. Calculating a tree
   * where each possible troop move is rated and the best one is chosen.
   *
   * @param gameState - current gameState
   * @return List of countries to move from and to move to
   * @author hneumann
   */
  public List<Country> moveTroops(GameState gameState) {
    Tree tree;
    if (gameState.getUnownedCountries() == null) {
      throw new IllegalArgumentException("GameState is null");
    }
    GameState
        result = gameState.cloneGameState();
    tree = new Tree(new Node(result, 0));

    for (Country c :
        gameState.getCountriesOwnedByPlayer(this.getUserKey())) {
      for (Country n :
          c.getNeighboursWithSameOwner()) {
        if (n.getOwner().equals(this) && c.getTroops() > 1) {
          result = gameState.cloneGameState();
          result.moveTroops(c, n);
          tree.getRoot().addChildren(new Node(result, tree.getRoot(), 1, c, n));
        }
      }
    }
    List<Country> countries = new ArrayList<>();
    Node node = tree.getRoot().getBestChild();
    if (node == null) {
      return null;
    }
    countries.add(gameState.getCountryByName(node.getFrom().getName()));
    countries.add(gameState.getCountryByName(node.getTo().getName()));
    return countries;
  }

  /**
   * Method to calculate the amount of troops to distribute.
   *
   * @return newTroops - the count of new troops to distribute
   * @author floribau
   */
  public int calculateTroops() {
    int newTroops =
        User.getUser().getGameState().getCountriesOwnedByPlayer(this.getUserKey()).size() / 3;
    if (newTroops < 3) {
      newTroops = 3;
    }
    for (Continent continent : User.getUser().getGameState()
        .getContinentsOwnedByPlayer(getUserKey())) {
      switch (continent.getName()) {
        case "Africa" -> newTroops += 3;
        case "Asia" -> newTroops += 7;
        case "SouthAmerica", "Australia" -> newTroops += 2;
        case "Europe", "NorthAmerica" -> newTroops += 5;
        default -> { //ignore
        }
      }
    }
    return newTroops;
  }
}

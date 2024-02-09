package com.risk.gamephases;

import com.risk.gui.GameController;
import com.risk.main.User;
import com.risk.objects.Country;
import com.risk.objects.enums.GamePhase;
import java.util.ArrayList;

/**
 * Class that represents the Distribution Phase. It gets the necessary input from the gui.
 * @author floribau
 */
public class DistributionPhase extends Phase {

  private final String player;
  private final Country country;
  private final ArrayList<Country> unownedCountries = new ArrayList<>();
  private final ArrayList<Country> selectableCountries = new ArrayList<>();

  /**
   * Constructor for DistributionPhase.
   *
   * @param player           - the player whose turn is it in this DistributionPhase
   * @param unownedCountries - list of the countries that are unowned
   * @author floribau
   */
  public DistributionPhase(String player, ArrayList<Country> unownedCountries) {
    // Todo delete sout

    this.player = player;
    this.unownedCountries.addAll(unownedCountries);
    computeSelectableCountries();
    this.country = selectCountry();
  }

  /**
   * Computes the countries which are selectable by the player. If the player owns no countries, all
   * unowned countries are selectable.
   *
   * @author floribau
   */
  private void computeSelectableCountries() {
    if (this.unownedCountries.size() != 0) {
      selectableCountries.addAll(unownedCountries);
    } else {
      selectableCountries.addAll(User.getUser().getGameState().getCountriesOwnedByPlayer(player));
    }
  }

  /**
   * Lets the human player select a country to distribute their troops to in the Gui.
   *
   * @return Country that is selected by the player
   * @author lkuech
   */
  private Country selectCountry() {

    GameController gui = User.getUser().getGameController();
    gui.setGamePhase(GamePhase.DISTRIBUTION_PHASE);

    gui.setSelectedCountry1(null);
    return gui.distributionSelection(selectableCountries);
  }

  /**
   * Returns the country that was selected by the player.
   *
   * @return the country that was selected by the player
   * @author floribau
   */
  public Country getCountry() {
    return this.country;
  }

  /**
   * Returns the player who is in the distribution phase.
   *
   * @return Player who is in the distribution phase
   * @author floribau
   */
  public String getPlayer() {
    return this.player;
  }
}

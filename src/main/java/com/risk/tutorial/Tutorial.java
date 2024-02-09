package com.risk.tutorial;

import com.risk.game.GameState;
import com.risk.gui.TutorialSceneController;
import com.risk.objects.Country;
import com.risk.objects.Player;
import com.risk.util.exceptions.IntegerNotPositiveException;
import java.io.IOException;
import java.util.ArrayList;

/**
 * This class is used to create a tutorial game with its own gameState.
 *
 * @author vstoll.
 */
public class Tutorial {

  private GameState tutorialGameState;
  private TutorialSceneController gui;

  private Player tutorialPlayerEasy;

  private Player tutorialPlayerHard;

  /**
   * This method closes the tradeInCardsPane.
   *
   * @param event - The event that is triggered when the user clicks on the "exit" button.
   * @throws IOException - Throws an IOException if the FXML file can't be found.
   * @author vstoll.
   */

  /**
   * This method creates a tutorial game with its own gameState and controller.
   *
   * @param gui the controller of the tutorial game.
   * @author lgreiner.
   */
  public Tutorial(TutorialSceneController gui) {
    this.gui = gui;
    this.tutorialGameState = GameState.createTutorialGameState();
    for (Player player : tutorialGameState.getPlayers()) {
      if (player.getName().equals("Coco")) {
        tutorialPlayerHard = player;
      } else {
        tutorialPlayerEasy = player;
      }
    }

    ArrayList<Country> countries = tutorialGameState.getCountries();
    for (int i = 0; i < 42; i++) {
      countries.get(i).setButtonIndex(i);
    }
    startTutorial();
  }

  /**
   * This method starts the tutorial.
   *
   * @author lgreiner.
   */
  private void startTutorial() {
    gui.paintGUI(tutorialGameState);
  }

  /**
   * This method sets the owner of a country to the tutorialPlayerHard.
   *
   * @param countryName the name of the country that should be set to the tutorialPlayerHard.
   * @throws IntegerNotPositiveException if the number of troops is not positive.
   * @author lgreiner.
   */
  public void setCountryOwnerHard(String countryName) {
    Country country = tutorialGameState.getCountryByName(countryName);
    country.setOwner(tutorialPlayerHard.getName());
    try {
      country.setTroops(1);
    } catch (IntegerNotPositiveException e) {
    }
    gui.paintGUI(tutorialGameState);
  }

  /**
   * This method sets the owner of a country to the tutorialPlayerEasy.
   *
   * @param countryName the name of the country that should be set to the tutorialPlayerEasy.
   * @throws IntegerNotPositiveException if the number of troops is not positive.
   * @author lgreiner.
   */
  public void setCountryOwnerEasy(String countryName) {
    Country country = tutorialGameState.getCountryByName(countryName);
    country.setOwner(tutorialPlayerEasy.getName());
    try {
      country.setTroops(1);
    } catch (IntegerNotPositiveException e) {
    }
    gui.paintGUI(tutorialGameState);
  }

  /**
   * This method sets the number of troops of a country.
   *
   * @param countryName the name of the country in which the number of troops should be set.
   * @param troops the number of troops that should be set.
   * @throws IntegerNotPositiveException if the number of troops is not positive.
   * @author lgreiner.
   */
  public void setTroops(String countryName, int troops) {
    Country country = tutorialGameState.getCountryByName(countryName);
    try {
      country.setTroops(troops);
    } catch (IntegerNotPositiveException e) {
    }
    gui.paintGUI(tutorialGameState);
  }
}

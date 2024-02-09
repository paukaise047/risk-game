package com.risk.gui;

import com.risk.game.GameState;
import com.risk.objects.Card;
import com.risk.objects.Country;
import com.risk.objects.enums.CardSymbol;
import com.risk.tutorial.Tutorial;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.SVGPath;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class TutorialSceneController implements Initializable {

  ArrayList<Card> cardsDisplayed = new ArrayList<>(Arrays.asList(
      new Card(0, null, CardSymbol.Infantry, "/pictures/tradeCards/AlaskaCard.jpg"),
      new Card(4, null, CardSymbol.Cavalry, "/pictures/tradeCards/Central-AmericaCard.jpg"),
      new Card(9, null, CardSymbol.Cannon, "/pictures/tradeCards/VenezuelaCard.jpg")
  ));
  Card[] cardsInSlot = new Card[3];
  @FXML
  ImageView ivSlotOne;
  @FXML
  ImageView ivSlotTwo;
  @FXML
  ImageView ivSlotThree;
  @FXML
  javafx.scene.layout.FlowPane cardDisplayFlowPane;
  @FXML
  String text2 = (
      "Risk is a game of global domination, where players compete to conquer territories "
          + "and eliminate their opponents. The game is played on a map of the world, divided into "
          + "territories that players will need to claim and defend. Each turn, players can deploy "
          + "armies, attack opponents, and fortify their own territories. The goal is to be the last "
          + "player standing, with total control over the world!");
  @FXML
  String text3 = ("But beware, fellow Soldiers, as risk is not just about attacking and defending. "
      + "There's a lot of strategy involved, from deciding where to deploy your troops to choosing"
      + " which territories to attack. It takes careful planning and cunning to come out on top. "
      + "Now, if you're ready to start learning the ins and outs of this exciting game, then let's "
      + "get started with the tutorial!");
  @FXML
  String text4 = ("At the beginning of the game you have 30 troops to conquer countries. In this "
      + "scenario only 4 countries are available to conquer. Press on East Africa to conquer it.");
  @FXML
  String text5 = ("Press on East Africa to " +
      '\n' +
      "claim it, Soldier!!!");
  @FXML
  String text6 = (
      """
          AMAZING BANANA!
          You claimed your first country.
          Well done, Soldier!""");
  @FXML
  String text7 = ("Now it's the " +
      '\n' + "opponent's claim turn.");
  @FXML
  String text8 = ("""
      Now it's your turn again!
      Go for Egypt so that you
      claimed the whole
      african continent.""");
  @FXML
  String text9 = ("""
      Now it's the
      opponent's claim
      turn again.""");
  @FXML
  String text10 = ("You claimed the whole" +
      '\n' +
      "continent of Africa!!");
  @FXML
  String text11 = ("""
      Owning whole continents is very
      important since it leads to
      extra troops at the beginning
      of every round for every continent
      you own. Here is more detailed
       Information!""");
  @FXML
  String text12 = ("""
      As you now know the claim troops
      phase only occurs once at the
      beginning of each game. Now let me
      introduce the other three phases that
      will occur in each subsequent
      round of the game.""");
  @FXML
  String text13 = ("""
      The three phases that we
      will take a closer look now are:

      1. Place troops phase
      2. Attack phase
      3. Move troops phase""");
  @FXML
  String text14 = ("We will start with the " +
      '\n' +
      "place troops phase!"
  );
  @FXML
  String text15 = ("""
      At the beginning of each round
      you'll receive following troops:

      » 1 troop per 3 countries you own
      (but min 3 troops)
      » troops for continent you own
      (amount from continents was
       mentioned before)""");
  @FXML
  String text16 = ("""
      therefore your troop claim
      is composed as follows:

      » 7 troops for 21 countries you own
      » 3 troops for owning africa""");
  @FXML
  String text17 = ("""
      So in total you would receive
      10 troops but since this is
      a tutorial, you'll get only 1
      troop for place troops. Please
      distribute them in the next step
      in the shown country""");
  @FXML
  String text18 = ("""
      Well done! Now you're
      ready for attacking phase.

      Continue to attack!""");
  @FXML
  String text19 = ("""
      Click on the targeted country
      of the enemy to open the
       attack dialog!""");
  @FXML
  String text20 = ("""
      Well done!
      You successfully attacked the
      enemy country. The amount of
      troops in the new country
      depends from the effort of
      troops you put into the attack""");
  @FXML
  String textOho = ("""
      Attacks are made via
      rolling dices. In Risky Monkeys
      rolling the dices will not
      be shown to make the
      experience smoother.
      But trust us! We are fair!""");
  @FXML
  String text21 = ("""
      Now we move on to our
      last phase of the round.
      After you have done some
      attacks you can move
      the leftover troops.
      Click to see an example!""");
  @FXML
  String text22 = ("""
      In this example we want to
      move the troops from east
      africa to egypt.
      Please click on east africa in
      the next step to move troops!""");
  @FXML
  String text23 = ("""
      You can move troops to
      the countries which are
      colored orange.
      Please click on egypt to
      move the troops there""");
  @FXML
  String text24 = ("""
      Well done!
      Last but not least we'll
      have a look at the card
      system of the game.""");
  @FXML
  String text25 = ("""
      Start the attack by clicking
      on the country you want to
      start the attack from, in our
      case this would be egypt.""");
  @FXML
  String text26 = ("""
      You'll receive one card per
      round if you manged to attack
      one or more countries
      successful in that round.""");
  @FXML
  String text27 = ("""
      You can trade in the received
      cards at the beginning of
      each round.""");
  @FXML
  String text28 = ("""
      You'll receive troops for the
      following card combinations:

      » 3 similar cards
      » 3 different cards
      » one of the combinations above
      with usage of white cards
      » 3 white cards""");
  @FXML
  String text29 = ("""
      To get a better
      understanding we'll take
      a closer look at the
      trading cards dialog""");
  @FXML
  String text30 = ("""
      Holy Bananas!
      You are a natural Risky
      Monkeys player. Now
      you're ready for
      your first game!""");
  @FXML
  String text31 = ("""
      Press next to
      join a lobby where you can
      add AI Players to play
      your first real game.""");
  Tutorial tutorial;
  String wantedMove;
  String clickedCountry;
  private Stage stage;
  private Scene scene;
  private Parent root;
  @FXML
  private AnchorPane scene2AnchorPane;
  @FXML
  private Button playerStatisticsButtonInTutorial;
  @FXML
  private Button chatButtonInTutorial;
  private SVGPath[] countryButtons; // initialized in method initialize
  //Troops in Countries
  //North America
  @FXML
  private Text troopsAlaska;
  @FXML
  private Text troopsNorthWestTerritory;
  @FXML
  private Text troopsGreenland;
  @FXML
  private Text troopsAlberta;
  @FXML
  private Text troopsOntario;
  @FXML
  private Text troopsQuebec;
  @FXML
  private Text troopsWesternUnitedStates;
  @FXML
  private Text troopsEasternUnitedStates;
  @FXML
  private Text troopsCentralAmerica;
  //South America
  @FXML
  private Text troopsVenezuela;
  @FXML
  private Text troopsBrazil;
  @FXML
  private Text troopsPeru;
  @FXML
  private Text troopsArgentina;
  // Europe
  @FXML
  private Text troopsIceland;
  @FXML
  private Text troopsScandinavia;
  @FXML
  private Text troopsGreatBritain;
  @FXML
  private Text troopsNorthernEurope;
  @FXML
  private Text troopsUkraine;
  @FXML
  private Text troopsWesternEurope;
  @FXML
  private Text troopsSouthernEurope;
  // Africa
  @FXML
  private Text troopsNorthAfrica;
  @FXML
  private AnchorPane endAnchorPane;

  @FXML
  private Button endTutorialButton;
  @FXML
  private Text troopsEgypt;
  @FXML
  private Text troopsEastAfrica;
  @FXML
  private Text troopsCongo;
  @FXML
  private Text troopsSouthAfrica;
  @FXML
  private Text troopsMadagascar;
  // Asia
  @FXML
  private Text troopsUral;
  @FXML
  private Text troopsSiberia;
  @FXML
  private Text troopsYakutsk;
  @FXML
  private Text troopsKamchatka;
  @FXML
  private Text troopsIrkutsk;
  @FXML
  private Text troopsMongolia;
  @FXML
  private Text troopsAfghanistan;
  @FXML
  private Text troopsChina;
  @FXML
  private Text troopsJapan;
  @FXML
  private Text troopsMiddleEast;
  @FXML
  private Text troopsSiam;
  @FXML
  private Text troopsIndia;
  //AttackScene
  // Oceania
  @FXML
  private Text troopsIndonesia;
  @FXML
  private Text troopsNewGuinea;
  @FXML
  private Text troopsWesternAustralia;
  @FXML
  private Text troopsEasternAustralia;
  //Array
  private Text[] troopsInCountriesText; // initialized in method initialize
  @FXML
  private SVGPath alaska;
  @FXML
  private SVGPath northWestTerritory;
  @FXML
  private SVGPath greenland;
  @FXML
  private SVGPath alberta;
  @FXML
  private SVGPath ontario;
  @FXML
  private SVGPath quebec;
  @FXML
  private SVGPath westernUnitedStates;
  @FXML
  private SVGPath easternUnitedStates;
  @FXML
  private SVGPath centralAmerica;
  // South America
  @FXML
  private SVGPath venezuela;
  @FXML
  private SVGPath brazil;
  @FXML
  private SVGPath peru;
  @FXML
  private SVGPath argentina;
  // Europe
  @FXML
  private SVGPath iceland;
  @FXML
  private SVGPath scandinavia;
  @FXML
  private SVGPath greatBritain;
  @FXML
  private SVGPath northernEurope;
  @FXML
  private SVGPath ukraine;
  @FXML
  private SVGPath westernEurope;
  @FXML
  private SVGPath southernEurope;
  // Africa
  @FXML
  private SVGPath northAfrica;
  @FXML
  private SVGPath egypt;
  @FXML
  private SVGPath eastAfrica;
  @FXML
  private SVGPath congo;
  @FXML
  private SVGPath southAfrica;
  @FXML
  private SVGPath madagascar;
  // Asia
  @FXML
  private SVGPath ural;
  @FXML
  private SVGPath siberia;
  @FXML
  private SVGPath yakutsk;
  @FXML
  private SVGPath kamchatka;
  @FXML
  private SVGPath irkutsk;
  @FXML
  private SVGPath mongolia;
  @FXML
  private SVGPath afghanistan;
  @FXML
  private SVGPath china;
  @FXML
  private SVGPath japan;
  @FXML
  private SVGPath middleEast;
  @FXML
  private SVGPath india;
  @FXML
  private SVGPath siam;
  // Oceania
  @FXML
  private SVGPath indonesia;
  @FXML
  private SVGPath newGuinea;
  @FXML
  private SVGPath westernAustralia;
  @FXML
  private SVGPath easternAustralia;
  @FXML
  private AnchorPane troopsInCountryPane;
  @FXML
  private Button startTutorialButton;
  @FXML
  private Button nextStepButton;
  @FXML
  private AnchorPane startAnchorPane;
  @FXML
  private Pane profPane;
  @FXML
  private Text tutText1;
  @FXML
  private ImageView speechBubbleImage;
  @FXML
  private ImageView redArrowGif;
  @FXML
  private ImageView tutorialManShadow;
  @FXML
  private Circle northAmericaTroopBonusCircle;
  @FXML
  private Circle southAmericaTroopBonusCircle;
  @FXML
  private Circle europeTroopBonusCircle;
  @FXML
  private Circle africaTroopBonusCircle;
  @FXML
  private Circle asiaTroopBonusCircle;
  @FXML
  private Circle australiaTroopBonusCircle;
  @FXML
  private Text northAmericaTroopBonusCircleText;
  @FXML
  private Text southAmericaTroopBonusCircleText;
  @FXML
  private Text europeTroopBonusCircleText;
  @FXML
  private Text africaTroopBonusCircleText;
  @FXML
  private Text asiaTroopBonusCircleText;
  @FXML
  private Text australiaTroopBonusCircleText;
  private int clickCount = 1;
  @FXML
  private Pane moveTroopsPaneBackground;
  @FXML
  private Pane moveTroopsPane;
  @FXML
  private AnchorPane tradeInCardsPane;
  @FXML
  private AnchorPane tradeInCardsLowerPane;
  @FXML
  private AnchorPane tradeInCardsLowerLowerPane;

  @FXML
  private Button tradeCardsButton;
  @FXML
  private ImageView ivTradeCardScene;
  @FXML
  private Button moveTroopsConfirm;
  @FXML
  private Label moveTroopsNumber;

  /**
   * Method which switches to the MenuScene.
   *
   * @param event - ActionEvent
   * @author paukaise
   */
  @FXML
  protected void switchToMenuScene(ActionEvent event) throws IOException {
    Parent root = FXMLLoader.load(getClass().getResource("/MenuScene.fxml"));
    stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    scene = new Scene(root);
    stage.setScene(scene);
    scene.getStylesheets().clear();
    scene.getStylesheets().add(getClass().getResource("/applicationStyle.css").toExternalForm());
    stage.show();
  }

  /**
   * Method which is called when the user clicks on the "Next Step" button.
   *
   * @param event - ActionEvent which is triggered when the user clicks on the "Start first game"
   * @author paukaise
   */
  @FXML
  protected void switchToLobbyScene(ActionEvent event) throws IOException {
    Parent root = FXMLLoader.load(getClass().getResource("/LobbyScene.fxml"));
    stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    scene = new Scene(root);
    stage.setScene(scene);
    scene.getStylesheets().clear();
    scene.getStylesheets().add(getClass().getResource("/applicationStyle.css").toExternalForm());
    stage.show();
  }

  /**
   * Method which is called when the user clicks on the "Start Game" button.
   *
   * @author paukaise, lkuech, lgreiner
   */
  @FXML
  protected void startTutorialButton() {
    startAnchorPane.setDisable(true);
    scene2AnchorPane.setVisible(true);
    troopsInCountryPane.setVisible(true);
    playerStatisticsButtonInTutorial.setDisable(true);
    chatButtonInTutorial.setDisable(true);
    startTutorialButton.setVisible(false);
    profPane.setVisible(true);
    startAnchorPane.setVisible(false);

    countryButtons = new SVGPath[]{alaska, northWestTerritory, alberta,
        westernUnitedStates,
        centralAmerica, greenland, ontario, quebec, easternUnitedStates, venezuela, peru,
        brazil,
        argentina, iceland, scandinavia, ukraine, greatBritain, northernEurope,
        westernEurope, southernEurope, northAfrica, egypt, congo, eastAfrica, southAfrica,
        madagascar, siberia, ural, china, afghanistan, middleEast, india, siam, yakutsk,
        irkutsk,
        mongolia, japan, kamchatka, indonesia, newGuinea, westernAustralia, easternAustralia};
    troopsInCountriesText = new Text[]{troopsAlaska, troopsNorthWestTerritory, troopsAlberta,
        troopsWesternUnitedStates,
        troopsCentralAmerica, troopsGreenland, troopsOntario, troopsQuebec,
        troopsEasternUnitedStates, troopsVenezuela, troopsPeru, troopsBrazil,
        troopsArgentina, troopsIceland, troopsScandinavia, troopsUkraine, troopsGreatBritain,
        troopsNorthernEurope,
        troopsWesternEurope, troopsSouthernEurope, troopsNorthAfrica, troopsEgypt, troopsCongo,
        troopsEastAfrica, troopsSouthAfrica,
        troopsMadagascar, troopsSiberia, troopsUral, troopsChina, troopsAfghanistan,
        troopsMiddleEast, troopsIndia, troopsSiam, troopsYakutsk, troopsIrkutsk,
        troopsMongolia, troopsJapan, troopsKamchatka, troopsIndonesia, troopsNewGuinea,
        troopsWesternAustralia, troopsEasternAustralia};

    this.tutorial = new Tutorial(this);

  }

  /**
   * This method is called when the user clicks the "Next Step" button. It will call the next step
   * in the tutorial.
   *
   * @param url            The location used to resolve relative paths for the root object, or
   *                       {@code null} if the location is not known.
   * @param resourceBundle The resources used to localize the root object, or {@code null} if the
   *                       root object was not localized.
   * @author lgreiner, lkuech, paukaise.
   */
  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    startTutorialButton.setMouseTransparent(false);
    australiaTroopBonusCircle.setVisible(false);
    asiaTroopBonusCircle.setVisible(false);
    europeTroopBonusCircle.setVisible(false);
    africaTroopBonusCircle.setVisible(false);
    southAmericaTroopBonusCircle.setVisible(false);
    northAmericaTroopBonusCircle.setVisible(false);

    australiaTroopBonusCircleText.setVisible(false);
    asiaTroopBonusCircleText.setVisible(false);
    europeTroopBonusCircleText.setVisible(false);
    africaTroopBonusCircleText.setVisible(false);
    southAmericaTroopBonusCircleText.setVisible(false);
    northAmericaTroopBonusCircleText.setVisible(false);

    displayAllCards();
  }

  /**
   * Method which paints the GUI according to the current gameState.
   *
   * @param gameState - current gameState
   * @author lgreiner, lkuech
   */
  public void paintGUI(GameState gameState) {
    for (Country country : gameState.getCountries()) {
      SVGPath countryButton = getButtonByCountry(country);
      if (country.getOwner() != null) {
        if (country.getOwner().equals("Coco")) {
          changeBackgroundColor(countryButton, "green");
        } else {
          changeBackgroundColor(countryButton, "orange");
        }
        getTextByCountry(country).setText(country.getTroops() + "");
        getTextByCountry(country).setVisible(true);
      } else {
        countryButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
          @Override
          public void handle(MouseEvent event) {
            switch (wantedMove) {
              case "claim":
                tutorial.setCountryOwnerEasy(clickedCountry);
                nextStepButton.fire();
                nextStepButton.setVisible(true);
                redArrowGif.setVisible(false);
                switch (clickedCountry) {
                  case "EastAfrica" -> eastAfrica.setDisable(true);
                  case "Egypt" -> egypt.setDisable(true);
                  default -> {
                  }
                }
                break;
              case "distribute":
                redArrowGif.setVisible(false);
                moveTroopsPaneBackground.setVisible(true);
                moveTroopsPane.setVisible(true);
                moveTroopsConfirm.setDisable(true);
                nextStepButton.fire();
                break;
              case "confirm-button":
                moveTroopsConfirm.setDisable(true);
                moveTroopsPaneBackground.setVisible(false);
                break;
              default:
                break;
            }
          }
        });

        countryButton.setDisable(true);
      }
    }
  }

  /**
   * Method which returns the SVGPath of a countryButton.
   *
   * @param country - country of which the SVGPath should be returned
   * @author lkuech
   */
  public SVGPath getButtonByCountry(Country country) {
    return countryButtons[country.getButtonIndex()];
  }

  /**
   * Method which returns the Text of a countryButton.
   *
   * @param country - country of which the Text should be returned
   * @author lkuech
   */
  public Text getTextByCountry(Country country) {
    return troopsInCountriesText[country.getButtonIndex()];
  }

  /**
   * Method which changes the background color of a countryButton to the given color.
   *
   * @param countryButton - SVGPath of the countryButton
   * @param color         - color which should be set
   * @author lkuech
   */
  public void changeBackgroundColor(SVGPath countryButton, String color) {
    if (!countryButton.getStyle().contains("-fx-fill")) { //todo INFO: SVGPath changes
      countryButton.setStyle(
          countryButton.getStyle() + " -fx-fill: + color + ;");
    } else {
      String style = countryButton.getStyle();
      int startIndex = style.indexOf("-fx-fill: ") + 10; //todo INFO: SVGPath changes
      int endIndex = style.indexOf(";", startIndex);
      String oldStyle = style.substring(startIndex, endIndex);

      String newStyle = style.replaceFirst(oldStyle + ";",
          color + ";");
      if (!newStyle.equals(style)) {
        countryButton.setStyle(newStyle);
      }
    }
  }

  /**
   * Method which sets the countries to grey which are not allowed to be moved.
   *
   * @author lgreiner
   */
  public void setMakeItGreyMove() {
    SVGPath[] makeGreyCountries = {alaska, alberta, westernUnitedStates, centralAmerica,
        easternUnitedStates,
        venezuela, peru, ontario, scandinavia, ukraine, northernEurope, southernEurope, siberia,
        ural, afghanistan, middleEast, yakutsk, irkutsk, kamchatka, westernAustralia,
        easternAustralia, northWestTerritory, greenland, quebec, brazil, argentina, iceland,
        greatBritain, westernEurope, eastAfrica,
        china, india, siam, mongolia, japan, indonesia, newGuinea};

    for (SVGPath c : makeGreyCountries) {
      c.setFill(Color.GRAY);
    }

    eastAfrica.setFill(Color.YELLOW);
  }

  /**
   * Method which sets the countries to grey which are not allowed to be attacked.
   *
   * @author lgreiner
   */
  public void setMakeItGreyAttack() {
    SVGPath[] makeGreyCountries = {alaska, northWestTerritory, alberta,
        westernUnitedStates,
        centralAmerica, greenland, ontario, quebec, easternUnitedStates, venezuela, peru,
        brazil,
        argentina, iceland, scandinavia, ukraine, greatBritain, northernEurope,
        westernEurope, northAfrica, congo, eastAfrica, southAfrica,
        madagascar, siberia, ural, china, afghanistan, india, siam, yakutsk,
        irkutsk,
        mongolia, japan, kamchatka, indonesia, newGuinea, westernAustralia, easternAustralia};

    for (SVGPath c : makeGreyCountries) {
      c.setFill(Color.GRAY);
    }
  }

  /**
   * Method to set the explanation of the continent bonus troops.
   *
   * @author lgreiner
   */
  public void setContinentBonusTroopExplanation() {
    //Africa
    northAfrica.setFill(Color.BLUE);
    egypt.setFill(Color.BLUE);
    eastAfrica.setFill(Color.BLUE);
    congo.setFill(Color.BLUE);
    southAfrica.setFill(Color.BLUE);
    madagascar.setFill(Color.BLUE);
    //NA
    alaska.setFill(Color.RED);
    northWestTerritory.setFill(Color.RED);
    greenland.setFill(Color.RED);
    alberta.setFill(Color.RED);
    ontario.setFill(Color.RED);
    quebec.setFill(Color.RED);
    westernUnitedStates.setFill(Color.RED);
    easternUnitedStates.setFill(Color.RED);
    centralAmerica.setFill(Color.RED);
    //SA
    venezuela.setFill(Color.GREEN);
    peru.setFill(Color.GREEN);
    brazil.setFill(Color.GREEN);
    argentina.setFill(Color.GREEN);
    //Europe
    iceland.setFill(Color.YELLOW);
    greatBritain.setFill(Color.YELLOW);
    westernEurope.setFill(Color.YELLOW);
    southernEurope.setFill(Color.YELLOW);
    northernEurope.setFill(Color.YELLOW);
    scandinavia.setFill(Color.YELLOW);
    ukraine.setFill(Color.YELLOW);
    //Asia
    ural.setFill(Color.PURPLE);
    siberia.setFill(Color.PURPLE);
    yakutsk.setFill(Color.PURPLE);
    irkutsk.setFill(Color.PURPLE);
    kamchatka.setFill(Color.PURPLE);
    afghanistan.setFill(Color.PURPLE);
    middleEast.setFill(Color.PURPLE);
    india.setFill(Color.PURPLE);
    siam.setFill(Color.PURPLE);
    china.setFill(Color.PURPLE);
    mongolia.setFill(Color.PURPLE);
    japan.setFill(Color.PURPLE);
    //AUS
    indonesia.setFill(Color.ORANGE);
    newGuinea.setFill(Color.ORANGE);
    westernAustralia.setFill(Color.ORANGE);
    easternAustralia.setFill(Color.ORANGE);
  }

  /**
   * Method which is called when the nextStepButton is clicked. It changes the text of the tutorial
   * and the function of the button. The button is used to navigate through the tutorial.
   *
   * @param event - ActionEvent, which is triggered by clicking on the tradeCardsButton.
   * @author lgreiner, paukaise, lkuech
   */
  @FXML
  private void changeButtonFunction(ActionEvent event) {
    nextStepButton.setUserData(0);
    //int clickCount = Integer.parseInt(nextStepButton.getUserData().toString());

    switch (clickCount) {
      case 1:
        // Change button function for the 1st click
        tutText1.setText(text2);
        break;
      case 2:
        // Change button function for the 2nd click
        tutText1.setText(text3);
        break;
      case 3:
        // Change button function for the 3rd click
        tutText1.setText(text4);
        break;
      case 4:
        // Change button function for the 3rd click
        redArrowGif.setVisible(true);
        redArrowGif.setX(700);
        redArrowGif.setY(425);
        redArrowGif.setFitHeight(100);
        speechBubbleImage.setFitHeight(300);
        speechBubbleImage.setY(215);
        speechBubbleImage.setX(10);
        tutText1.setY(235);
        tutText1.setX(-170);
        tutText1.setText(text5);
        //scene2AnchorPane.setDisable(false);
        startAnchorPane.setVisible(false);
        eastAfrica.setDisable(false);
        eastAfrica.toFront();
        nextStepButton.setVisible(false);
        wantedMove = "claim";
        clickedCountry = "EastAfrica";
        break;
      // Add more cases for additional clicks as needed
      case 5:
        speechBubbleImage.setFitHeight(380);
        tutText1.setY(250);
        tutText1.setX(-120);
        tutText1.setText(text6);
        break;
      case 6:
        speechBubbleImage.setFitHeight(300);
        speechBubbleImage.setY(215);
        speechBubbleImage.setX(10);
        tutText1.setY(235);
        tutText1.setX(-170);
        tutText1.setText(text7);
        break;
      case 7:
        tutorial.setCountryOwnerHard("SouthernEurope");
        tutText1.setText(text8);
        speechBubbleImage.setFitHeight(300);
        speechBubbleImage.setY(245);
        speechBubbleImage.setX(10);
        tutText1.setY(235);
        tutText1.setX(-170);
        nextStepButton.setVisible(false);
        redArrowGif.setVisible(true);
        redArrowGif.setX(650);
        redArrowGif.setY(335);
        egypt.setDisable(false);
        wantedMove = "claim";
        clickedCountry = "Egypt";
        break;
      case 8:
        //Placeholder
        break;
      case 9:
        tutText1.setText(text10);
        break;
      case 10:
        tutText1.setText(text9);
        break;
      case 11:
        tutorial.setCountryOwnerHard("MiddleEast");
        speechBubbleImage.setY(175);
        speechBubbleImage.setFitHeight(400);
        tutText1.setY(150);
        tutText1.setX(-110);
        tutText1.setText(text11);
        break;
      case 12:
        speechBubbleImage.setVisible(false);
        tutText1.setVisible(false);
        setContinentBonusTroopExplanation();
        australiaTroopBonusCircle.setVisible(true);
        asiaTroopBonusCircle.setVisible(true);
        africaTroopBonusCircle.setVisible(true);
        southAmericaTroopBonusCircle.setVisible(true);
        northAmericaTroopBonusCircle.setVisible(true);
        europeTroopBonusCircle.setVisible(true);
        australiaTroopBonusCircleText.setVisible(true);
        asiaTroopBonusCircleText.setVisible(true);
        africaTroopBonusCircleText.setVisible(true);
        southAmericaTroopBonusCircleText.setVisible(true);
        northAmericaTroopBonusCircleText.setVisible(true);
        europeTroopBonusCircleText.setVisible(true);
        troopsInCountryPane.setVisible(false);
        break;
      case 13:
        troopsInCountryPane.setVisible(true);
        australiaTroopBonusCircle.setVisible(false);
        asiaTroopBonusCircle.setVisible(false);
        africaTroopBonusCircle.setVisible(false);
        southAmericaTroopBonusCircle.setVisible(false);
        northAmericaTroopBonusCircle.setVisible(false);
        europeTroopBonusCircle.setVisible(false);
        australiaTroopBonusCircleText.setVisible(false);
        asiaTroopBonusCircleText.setVisible(false);
        africaTroopBonusCircleText.setVisible(false);
        southAmericaTroopBonusCircleText.setVisible(false);
        northAmericaTroopBonusCircleText.setVisible(false);
        europeTroopBonusCircleText.setVisible(false);
        speechBubbleImage.setVisible(true);
        tutText1.setY(150);
        tutText1.setVisible(true);
        tutText1.setText(text12);
        resetMapForNewScenarios();
        break;
      case 14:
        tutText1.setText(text13);
        break;
      case 15:
        tutText1.setText(text14);
        break;
      case 16:
        speechBubbleImage.setFitHeight(450);
        tutText1.setX(-80);
        tutText1.setText(text15);
        break;
      case 17:
        tutText1.setText(text16);
        tutText1.setX(-80);
        break;
      case 18:
        tutText1.setText(text17);
        break;
      case 19:
        tutText1.setVisible(false);
        speechBubbleImage.setVisible(false);
        redArrowGif.setVisible(true);
        egypt.setDisable(false);
        wantedMove = "distribute";
        clickedCountry = "Egypt";
        moveTroopsPaneBackground.setLayoutX(670);
        moveTroopsPaneBackground.setLayoutY(280);
        nextStepButton.setVisible(false);
        break;
      case 20:
        moveTroopsPane.setVisible(true);
        moveTroopsPaneBackground.setVisible(true);
        break;
      case 21:
        tutText1.setX(-165);
        tutorial.setTroops("Egypt", 2);
        speechBubbleImage.setVisible(true);
        speechBubbleImage.setFitHeight(300);
        tutText1.setVisible(true);
        tutText1.setText(text18);
        moveTroopsPane.setVisible(false);
        moveTroopsPaneBackground.setVisible(false);
        nextStepButton.setVisible(true);
        break;
      case 22:
        tutText1.setX(-155);
        egypt.setDisable(true);
        tutText1.setVisible(true);
        speechBubbleImage.setX(-15);
        speechBubbleImage.setFitHeight(360);
        speechBubbleImage.setVisible(true);
        redArrowGif.setVisible(false);
        clickCount++;
      case 23:
        redArrowGif.setVisible(true);
        redArrowGif.setX(640);
        redArrowGif.setY(335);
        tutText1.setText(text25);
        egypt.setDisable(false);
        break;
      case 24:
        egypt.setDisable(true);
        setMakeItGreyAttack();
        moveTroopsPaneBackground.setVisible(false);
        moveTroopsPane.setVisible(false);
        speechBubbleImage.setVisible(true);
        nextStepButton.setVisible(true);
        tutText1.setText(text19);
        middleEast.setDisable(false);
        redArrowGif.setVisible(true);
        redArrowGif.setX(750);
        redArrowGif.setY(300);
        nextStepButton.setVisible(false);
        break;
      case 25:
        middleEast.setDisable(true);
        redArrowGif.setVisible(false);
        moveTroopsPaneBackground.setVisible(true);
        moveTroopsPane.setVisible(true);
        break;
      case 26:
        tutText1.setText(text20);
        moveTroopsPaneBackground.setVisible(false);
        moveTroopsPane.setVisible(false);
        resetMapForNewScenarios();
        middleEast.setFill(Color.ORANGE);
        tutorial.setTroops("MiddleEast", 1);
        tutorial.setTroops("Egypt", 1);
        middleEast.setFill(Color.ORANGE);
        nextStepButton.setVisible(true);
        break;
      case 27:
        tutText1.setText(textOho);
        break;
      case 28:
        //
      case 29:
        tutText1.setText(text21);
        clickCount++;
        break;
      case 30:
        tutText1.setText(text22);
        tutorial.setTroops("EastAfrica", 2);
        resetMapForNewScenarios();
        middleEast.setFill(Color.ORANGE);
        redArrowGif.setVisible(true);
        redArrowGif.setY(400);
        redArrowGif.setX(675);
        nextStepButton.setVisible(false);
        eastAfrica.setDisable(false);
        break;
      case 31:
        setMakeItGreyMove();
        redArrowGif.setVisible(true);
        redArrowGif.setX(625);
        redArrowGif.setY(350);
        eastAfrica.setDisable(true);
        egypt.setDisable(false);
        moveTroopsPaneBackground.setVisible(false);
        moveTroopsPane.setVisible(false);
        speechBubbleImage.setVisible(true);
        tutText1.setVisible(true);
        tutText1.setText(text23);
        break;
      case 32:
        egypt.setDisable(true);
        moveTroopsPaneBackground.setVisible(true);
        moveTroopsPane.setVisible(true);
        break;
      case 33:
        resetMapForNewScenarios();
        middleEast.setFill(Color.ORANGE);
        tutorial.setTroops("Egypt", 2);
        tutorial.setTroops("EastAfrica", 1);
        tutText1.setText(text24);
        moveTroopsPane.setVisible(false);
        moveTroopsPaneBackground.setVisible(false);
        nextStepButton.setVisible(true);
        break;
      case 34:
        tutText1.setText(text26);
        break;
      case 35:
        tutText1.setText(text27);
        break;
      case 36:
        speechBubbleImage.setFitHeight(470);
        speechBubbleImage.setY(80);
        tutText1.setX(-100);
        tutText1.setY(55);
        tutText1.setText(text28);
        break;
      case 37:
        speechBubbleImage.setFitHeight(300);
        speechBubbleImage.setY(215);
        speechBubbleImage.setX(10);
        tutText1.setY(185);
        tutText1.setX(-170);
        tutText1.setText(text29);
        break;
      case 38:
        tradeInCardsPane.setVisible(true);
        checkCardCombination();
        speechBubbleImage.setVisible(false);
        tutText1.setVisible(false);
        break;
      case 39:
        tutText1.setVisible(true);
        tutText1.setText(text30);
        speechBubbleImage.setVisible(true);
        speechBubbleImage.setFitHeight(300);
        speechBubbleImage.setY(215);
        speechBubbleImage.setX(10);
        tutText1.setY(185);
        tutText1.setX(-170);
        break;
      case 40:
        tutText1.setText(text31);
        break;
      case 41:
        tutText1.setVisible(false);
        speechBubbleImage.setVisible(false);
        endAnchorPane.setVisible(true);
      default:
        break;
    }
    clickCount++;
    nextStepButton.setUserData(clickCount);
  }

  /**
   * Method to set the color of the countries to grey, so that the player can't click on them. It
   * resets the map to a new scenario.
   *
   * @author lgreiner
   */
  public void resetMapForNewScenarios() {
    SVGPath[] cocoC = {alaska, alberta, westernUnitedStates, centralAmerica, easternUnitedStates,
        venezuela, peru, ontario, scandinavia, ukraine, northernEurope, southernEurope, siberia,
        ural, afghanistan, middleEast, yakutsk, irkutsk, kamchatka, westernAustralia,
        easternAustralia};

    SVGPath[] noobC = {northWestTerritory, greenland, quebec, brazil, argentina, iceland,
        greatBritain, westernEurope, northAfrica, egypt, congo, eastAfrica, southAfrica,
        madagascar, china, india, siam, mongolia, japan, indonesia, newGuinea};

    for (SVGPath c : noobC) {
      c.setFill(Color.ORANGE);
    }
    for (SVGPath c : cocoC) {
      c.setFill(Color.GREEN);
    }
  }

  /**
   * Confirm button for the trade in cards pane.
   *
   * @author lkuech
   */
  @FXML
  protected void confirmButtonPressed() {
    nextStepButton.fire();
  }

  /**
   * Method that checks if the cards in the card slots are a valid combination.
   *
   * @author vstoll
   */
  public boolean checkCardCombination() {
    if (cardsInSlot[0] != null && cardsInSlot[1] != null && cardsInSlot[2] != null) {
      int countInfantryCards = 0;
      int countCavalryCards = 0;
      int countCannonCards = 0;
      int countWildCards = 0;

      for (Card c : cardsInSlot) {
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
    } else {
      return false;
    }
  }

  /**
   * @author vstoll.
   * <p>This method displays all cards, which are in the cardsDisplayed list (all the Players
   * cards at the beginning).
   */
  public void displayAllCards() {
    Platform.runLater(
        () -> {
          cardDisplayFlowPane.getChildren().clear();
          for (Card c : cardsDisplayed) {
            displayCard(c);
          }
        });
  }

  /**
   * This method shows the given card in the cardDisplayFlowPane.
   *
   * @param c Card, which should be shown on the cardDisplayFlowPane.
   * @author vstoll.
   */
  public void displayCard(Card c) {
    ImageView iv = new ImageView(new Image(c.getCardImage()));
    iv.setFitHeight(190.0);
    iv.setFitWidth(109.0);
    iv.setPickOnBounds(true);
    iv.setPreserveRatio(true);

    iv.setOnMouseClicked(
        event -> {
          try {
            switchCardIntoSlot(event, c);
          } catch (IOException e) {
            throw new RuntimeException(e);
          }
        });
    cardDisplayFlowPane.getChildren().add(iv);
  }

  /**
   * This method shows the given card in one of the slots. Therefore, it checks, if there is already
   * a card in the slot and if not, it shows the card in the first. It uses the cardsInSlot array to
   * save the cards in the slots.
   *
   * @param c Card, which should be shown in one of the slots.
   * @author vstoll.
   */
  @FXML
  public void switchCardIntoSlot(MouseEvent event, Card c) throws IOException {
    if (ivSlotOne.getImage() == null) {
      ivSlotOne.setImage(new Image(c.getCardImage()));
      cardsInSlot[0] = c;
      removeCardFromDisplay(c);
    } else if (ivSlotTwo.getImage() == null) {
      ivSlotTwo.setImage(new Image(c.getCardImage()));
      cardsInSlot[1] = c;
      removeCardFromDisplay(c);
    } else if (ivSlotThree.getImage() == null) {
      ivSlotThree.setImage(new Image(c.getCardImage()));
      cardsInSlot[2] = c;
      removeCardFromDisplay(c);
    }
    if (checkCardCombination()) {
      tradeCardsButton.setDisable(false);
      tradeCardsButton.setText("Trade in Cards");
    }
  }

  /**
   * This method removes the card from the slot, which was clicked. Therefore, it checks, which slot
   * was clicked, adds it again to the cardsDisplayed list and removes the card from the cardsInSlot
   * array.
   *
   * @param event ActionEvent, which is triggered by clicking on the re of the slot.
   * @author vstoll.
   */
  @FXML
  public void removeCardFromSlot(MouseEvent event) {
    if (event.getSource().equals(ivSlotOne)) {
      ivSlotOne.setImage(null);
      cardsDisplayed.add(cardsInSlot[0]);
      displayCard(cardsInSlot[0]);
      cardsInSlot[0] = null;
    } else if (event.getSource().equals(ivSlotTwo)) {
      ivSlotTwo.setImage(null);
      cardsDisplayed.add(cardsInSlot[1]);
      displayCard(cardsInSlot[1]);
      cardsInSlot[1] = null;
    } else if (event.getSource().equals(ivSlotThree)) {
      ivSlotThree.setImage(null);
      cardsDisplayed.add(cardsInSlot[2]);
      displayCard(cardsInSlot[2]);
      cardsInSlot[2] = null;
    }
  }

  /**
   * This method removes the given card from the cardsDisplayed list and displays all cards again.
   *
   * @param c Card, which should be removed from the cardsDisplayed list.
   * @author vstoll.
   */
  public void removeCardFromDisplay(Card c) {
    cardsDisplayed.remove(c);
    displayAllCards();
  }

  /**
   * Method which is called after clicking on the tradeCardsButton.
   *
   * @param event - ActionEvent, which is triggered by clicking on the tradeCardsButton.
   * @author paukaise
   */
  public void tradedInCards(ActionEvent event) {
    boolean tradeCardsPressed = true;
    ivSlotOne.setImage(null);
    ivSlotTwo.setImage(null);
    ivSlotThree.setImage(null);
    tradeInCardsPane.setVisible(false);
  }

  /**
   * Method to increase the number of troops to move.
   *
   * @param event - ActionEvent
   * @author paukaise
   */
  @FXML
  protected void increaseNumberOfMovingTroops(ActionEvent event) {
    // ToDo Don't allow to count higher than the number of available troops
    if (Integer.parseInt(moveTroopsNumber.getText()) + 1 <= 1) {
      int newValue = Integer.parseInt(moveTroopsNumber.getText()) + 1;
      moveTroopsConfirm.setDisable(false);
      moveTroopsNumber.setText(String.valueOf(newValue));
    }
  }

  /**
   * Method to decrease the number of troops to move.
   *
   * @param event - ActionEvent
   * @author paukaise
   */
  @FXML
  protected void decreaseNumberOfMovingTroops(ActionEvent event) {
    int oldValue = Integer.parseInt(moveTroopsNumber.getText());
    if (oldValue >= 1) {
      int newValue = oldValue - 1;
      moveTroopsConfirm.setDisable(true);
      moveTroopsNumber.setText(String.valueOf(newValue));
    }
  }


  /**
   * Method to set the number of troops to move to 0.
   *
   * @author paukaise
   */
  @FXML
  public void setNull() {
    moveTroopsNumber.setText("0");
  }
}
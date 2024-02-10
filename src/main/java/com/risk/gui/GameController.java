package com.risk.gui;

import com.risk.game.GameState;
import com.risk.gamephases.AttackMove;
import com.risk.main.User;
import com.risk.network.messages.PressedContinueAfterDetermineBeginnerMessage;
import com.risk.objects.Attack;
import com.risk.objects.Card;
import com.risk.objects.Country;
import com.risk.objects.Move;
import com.risk.objects.Placement;
import com.risk.objects.Player;
import com.risk.objects.enums.GamePhase;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Set;
import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.ImageCursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.SVGPath;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;


/**
 * @author all This class is the controller for the GameScene. It handles all the user input and
 * updates the gui accordingly.
 */
public class GameController implements Initializable {

  private final Integer[] tradeInCardsChoices = {1, 2, 3, 4, 5};
  private final User user = User.getUser();
  private final Card[] cardsInSlot = new Card[3];
  public FXMLLoader loader;
  public AnchorPane tradeInCardsLowerPane;
  public AnchorPane tradeInCardsLowerLowerPane;

  @FXML
  Button nextPhaseButton;
  @FXML
  AnchorPane playerNumberPane;
  String[] playerIconsPics = {
      "pictures/player1pb.png",
      "pictures/player2pb.png",
      "pictures/player3pb.png",
      "pictures/player4pb.png",
      "pictures/player5pb.png",
      "pictures/player6pb.png"
  };
  String[] playerColors = {
      "-fx-background-color: orange",
      "-fx-background-color: green",
      "-fx-background-color: red",
      "-fx-background-color: blue",
      "-fx-background-color: purple",
      "-fx-background-color: #1fc4ff"
  };
  int[] playerCountriesOwned = new int[6];
  int[] playerTroopsCount = new int[6];
  Image cursorImage = new Image("pictures/cursor.png"); //TODO add cursor image
  Cursor cursor = new ImageCursor(cursorImage);
  @FXML
  Button exitJustShowCardsPane;
  @FXML
  Button openJustShowCardsPaneButton;
  @FXML
  private Text avTroopsText;
  @FXML
  private Button alertTest;
  @FXML
  private Button closeAlertSceneButton;
  @FXML
  private ImageView ivSlotOne;
  @FXML
  private ImageView ivSlotTwo;
  @FXML
  private ImageView ivSlotThree;
  @FXML
  private ImageView ivSlotFour;
  @FXML
  private ImageView ivSlotFive;
  @FXML
  private Button tradeCardsButton;
  @FXML
  private ChoiceBox<Integer> tradeInCardsChoiceBox;
  @FXML
  private Text currentPhaseText;
  @FXML
  private Rectangle stageOneRect;
  @FXML
  private Rectangle stageTwoRect;
  @FXML
  private Rectangle stageThreeRect;
  private Stage stage;
  private Scene scene;
  private Parent root;
  private int diceResult;
  private int playerNumber;
  private Country selectedCountry1 = null;
  private Country selectedCountry2 = null;
  private GamePhase gamePhase;
  // Bottom Menu Bar
  private ArrayList<Card> cardsDisplayed;
  private ArrayList<Card> cardsShown;
  @FXML
  private Label numberOfTroops;
  @FXML
  private Button plus;
  @FXML
  private Button minus;
  // North America
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
  private Button playerOneIcon;
  @FXML
  private Rectangle playerOneIconRect;
  @FXML
  private Button playerTwoIcon;
  @FXML
  private Rectangle playerTwoIconRect;
  @FXML
  private Button playerThreeIcon;
  @FXML
  private Rectangle playerThreeIconRect;
  @FXML
  private Button playerFourIcon;
  @FXML
  private Rectangle playerFourIconRect;
  @FXML
  private Button playerFiveIcon;
  @FXML
  private Rectangle playerFiveIconRect;
  @FXML
  private Button playerSixIcon;
  @FXML
  private Rectangle playerSixIconRect;
  // Array
  private SVGPath[] countryButtons; // initialized in method initialize
  // Troops in Countries
  // North America
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
  // South America
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
  //Oceania
  @FXML
  private Text troopsIndonesia;
  @FXML
  private Text troopsNewGuinea;
  @FXML
  private Text troopsWesternAustralia;
  @FXML
  private Text troopsEasternAustralia;
  @FXML
  private Label playerNumberTextField;
  // Array
  private Text[] troopsInCountriesText; // initialized in method initialize

  @FXML
  private Button dice;
  @FXML
  private Button opposingTroops;
  @FXML
  private Button yourTroops;
  @FXML
  private AnchorPane resultPane;
  @FXML
  private Label defeatedLabel;
  @FXML
  private Label lostLabel;
  // exit
  @FXML
  private Button exit;
  // dice Window
  @FXML
  private Button continueButton;
  @FXML
  private ImageView diceImage;
  @FXML
  private AnchorPane dicePane;
  @FXML
  private Button diceButton;
  @FXML
  private Label diceResultLabel;
  @FXML
  private Text attackingTroopsCount;
  @FXML
  private Text defendingTroopsCount;
  @FXML
  private Button attackButton;
  @FXML
  private Button chatButton;
  @FXML
  private Button tradeButton;
  @FXML
  private Label youLabel;
  @FXML
  private Label opponentLabel;
  @FXML
  private Button moveTroopsConfirm;
  @FXML
  private Button moveTroopsPlus;
  @FXML
  private Button moveTroopsMinus;
  @FXML
  private Button moveTroopsMaxTroops;
  @FXML
  private Label moveTroopsNumber;
  @FXML
  private AnchorPane moveTroopsPane;
  @FXML
  private Button maxAttackingTroops;
  @FXML
  private AnchorPane greyBackground;
  @FXML
  private Button playerStatisticsButton;
  @FXML
  private Pane nextPhasePane;
  @FXML
  private FlowPane cardDisplayFlowPane;
  @FXML
  private AnchorPane tradeInCardsPane;
  @FXML
  private Button exitTradeCardScene;
  @FXML
  private StackPane playerOneTag;
  @FXML
  private StackPane playerTwoTag;
  @FXML
  private StackPane playerThreeTag;
  @FXML
  private StackPane playerFourTag;
  @FXML
  private StackPane playerFiveTag;
  @FXML
  private StackPane playerSixTag;
  @FXML
  private AnchorPane alertPane;
  @FXML
  private AnchorPane endPane;
  @FXML
  private Button leaveButton;
  @FXML
  private Label eloLabel;
  @FXML
  private Button endCirclePlayer;
  @FXML
  private Label winnerLabel;
  @FXML
  private Button showCurrentPlayerCircle;
  @FXML
  private ImageView showCurrentPlayerImage;
  @FXML
  private ImageView winnerImageView;
  @FXML
  private Button exitStats;
  @FXML
  private Button winnerButton;
  @FXML
  private Button attackingPlayerButton;
  @FXML
  private ImageView attackingPlayerImageView;
  @FXML
  private ImageView defendingPlayerImageView;
  private int maxTroopsToDistribute;
  private int troopsPlaced;
  private boolean nextPhase;
  @FXML
  private AnchorPane moveTroopsPaneBackground;
  @FXML
  private Button moveTroopsBackgroundButton;
  @FXML
  private Label troopsToDistributeLabel;
  @FXML
  private AnchorPane disconnectPane;
  @FXML
  private Button leaveButton2;
  @FXML
  private AnchorPane troopsInCountryPane;
  @FXML
  private Pane player1StatsPane;
  @FXML
  private Pane player2StatsPane;
  @FXML
  private Pane player3StatsPane;
  @FXML
  private Pane player4StatsPane;
  @FXML
  private Pane player5StatsPane;
  @FXML
  private Pane player6StatsPane;
  @FXML
  private Text player1TroopCount;
  @FXML
  private Text player2TroopCount;
  @FXML
  private Text player3TroopCount;
  @FXML
  private Text player4TroopCount;
  @FXML
  private Text player5TroopCount;
  @FXML
  private Text player6TroopCount;
  @FXML
  private Text player1CountryCount;
  @FXML
  private Text player2CountryCount;
  @FXML
  private Text player3CountryCount;
  @FXML
  private Text player4CountryCount;
  @FXML
  private Text player5CountryCount;
  @FXML
  private Text player6CountryCount;
  @FXML
  private Button playerStatisticsButtonInGame;
  @FXML
  private AnchorPane inGameStatsPane;
  @FXML
  private Button mostCountriesCircle;
  @FXML
  private Label mostCountriesNameTag;
  @FXML
  private ImageView mostCountriesImageView;
  @FXML
  private Button mostTroopsCircle;
  @FXML
  private Label mostTroopsNameTag;
  @FXML
  private ImageView mostTroopsImageView;
  @FXML
  private Label invalidCardCombinationLabel;
  @FXML
  private AnchorPane loadingScreen;
  @FXML
  private Button playerOneIconCover;
  @FXML
  private Button playerTwoIconCover;
  @FXML
  private Button playerThreeIconCover;
  @FXML
  private Button playerFourIconCover;
  @FXML
  private Button playerFiveIconCover;
  @FXML
  private Button playerSixIconCover;
  @FXML
  private FlowPane justShowCardsPaneFlowPane;
  @FXML
  private AnchorPane justShowCardsPane;
  @FXML
  private ImageView diceImageView;
  private boolean moveValid;
  private boolean confirmationComplete;
  private int troopsMoved;
  private boolean skipTradeCards = false;
  private boolean tradeCardsPressed = false;

  /**
   * @param url            The location used to resolve relative paths for the root object, or
   *                       {@code null} if the location is not known.
   * @param resourceBundle The resources used to localize the root object, or {@code null} if the
   *                       root object was not localized.
   * @author vstoll, paukaise. This method is called by the FXMLLoader when initialization is
   * complete. All countryButtons are deactivated. The controller is added to the own Player class.
   * The dicePane is set invisible and the result text on the dicePane is set to empty.
   */
  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    Timeline timeline =
        new Timeline(
            new KeyFrame(
                Duration.seconds(2.5),
                event -> {
                  loadingScreen.setVisible(false);
                }));
    timeline.play();
    user.addGameController(this);
    countryButtons =
        new SVGPath[]{
            alaska,
            northWestTerritory,
            alberta,
            westernUnitedStates,
            centralAmerica,
            greenland,
            ontario,
            quebec,
            easternUnitedStates,
            venezuela,
            peru,
            brazil,
            argentina,
            iceland,
            scandinavia,
            ukraine,
            greatBritain,
            northernEurope,
            westernEurope,
            southernEurope,
            northAfrica,
            egypt,
            congo,
            eastAfrica,
            southAfrica,
            madagascar,
            siberia,
            ural,
            china,
            afghanistan,
            middleEast,
            india,
            siam,
            yakutsk,
            irkutsk,
            mongolia,
            japan,
            kamchatka,
            indonesia,
            newGuinea,
            westernAustralia,
            easternAustralia
        };
    troopsInCountriesText =
        new Text[]{
            troopsAlaska,
            troopsNorthWestTerritory,
            troopsAlberta,
            troopsWesternUnitedStates,
            troopsCentralAmerica,
            troopsGreenland,
            troopsOntario,
            troopsQuebec,
            troopsEasternUnitedStates,
            troopsVenezuela,
            troopsPeru,
            troopsBrazil,
            troopsArgentina,
            troopsIceland,
            troopsScandinavia,
            troopsUkraine,
            troopsGreatBritain,
            troopsNorthernEurope,
            troopsWesternEurope,
            troopsSouthernEurope,
            troopsNorthAfrica,
            troopsEgypt,
            troopsCongo,
            troopsEastAfrica,
            troopsSouthAfrica,
            troopsMadagascar,
            troopsSiberia,
            troopsUral,
            troopsChina,
            troopsAfghanistan,
            troopsMiddleEast,
            troopsIndia,
            troopsSiam,
            troopsYakutsk,
            troopsIrkutsk,
            troopsMongolia,
            troopsJapan,
            troopsKamchatka,
            troopsIndonesia,
            troopsNewGuinea,
            troopsWesternAustralia,
            troopsEasternAustralia
        };

    deactivateAllCountryButtons();
    setPlayerIconsHoverEvents(playerOneIcon);
    setPlayerIconsHoverEvents(playerTwoIcon);
    setPlayerIconsHoverEvents(playerThreeIcon);
    setPlayerIconsHoverEvents(playerFourIcon);
    setPlayerIconsHoverEvents(playerFiveIcon);
    setPlayerIconsHoverEvents(playerSixIcon);
    showAmountOfPlayerIcons();
  }

  /**
   * This method shows the player stats in game.
   *
   * @author paukaise
   */
  @FXML
  public void playerStatsInGame() {
    inGameStatsPane.setVisible(!inGameStatsPane.isVisible());
    mostTroopsAndMostCountries();
  }

  /**
   * This method is called when the user hovers over a player icon. It shows the amount of countries
   * and troops the player owns.
   *
   * @param button - Button that is hovered over
   * @author paukaise
   */
  @FXML
  public void setPlayerIconsHoverEvents(Button button) {

    button.setOnMouseEntered(
        event -> {
          if (button == playerOneIcon) {
            player1StatsPane.setVisible(true);
            // get the sum of the countries owned by player 1
            int playerCountries =
                user.getGameState()
                    .getCountriesOwnedByPlayer(user.getGameState().getPlayers().get(0).getUserKey())
                    .size();
            player1CountryCount.setText(String.valueOf(playerCountries));

            int playerTroops =
                user.getGameState()
                    .getTroopsByPlayer(user.getGameState().getPlayers().get(0).getUserKey());
            player1TroopCount.setText(String.valueOf(playerTroops));
            playerCountriesOwned[0] = playerCountries;
            playerTroopsCount[0] = playerTroops;
          } else if (button == playerTwoIcon) {
            player2StatsPane.setVisible(true);
            int playerCountries =
                user.getGameState()
                    .getCountriesOwnedByPlayer(user.getGameState().getPlayers().get(1).getUserKey())
                    .size();
            player2CountryCount.setText(String.valueOf(playerCountries));

            int playerTroops =
                user.getGameState()
                    .getTroopsByPlayer(user.getGameState().getPlayers().get(1).getUserKey());
            player2TroopCount.setText(String.valueOf(playerTroops));
            playerCountriesOwned[1] = playerCountries;
            playerTroopsCount[1] = playerTroops;
          } else if (button == playerThreeIcon) {
            // Do something when button3 is hovered
            player3StatsPane.setVisible(true);
            int playerCountries =
                user.getGameState()
                    .getCountriesOwnedByPlayer(user.getGameState().getPlayers().get(2).getUserKey())
                    .size();
            player3CountryCount.setText(String.valueOf(playerCountries));

            int playerTroops =
                user.getGameState()
                    .getTroopsByPlayer(user.getGameState().getPlayers().get(2).getUserKey());
            player3TroopCount.setText(String.valueOf(playerTroops));
            playerCountriesOwned[2] = playerCountries;
            playerTroopsCount[2] = playerTroops;
          } else if (button == playerFourIcon) {
            player4StatsPane.setVisible(true);
            int playerCountries =
                user.getGameState()
                    .getCountriesOwnedByPlayer(user.getGameState().getPlayers().get(3).getUserKey())
                    .size();
            player4CountryCount.setText(String.valueOf(playerCountries));

            int playerTroops =
                user.getGameState()
                    .getTroopsByPlayer(user.getGameState().getPlayers().get(3).getUserKey());
            player4TroopCount.setText(String.valueOf(playerTroops));
            playerCountriesOwned[3] = playerCountries;
            playerTroopsCount[3] = playerTroops;
          } else if (button == playerFiveIcon) {
            player5StatsPane.setVisible(true);
            int playerCountries =
                user.getGameState()
                    .getCountriesOwnedByPlayer(user.getGameState().getPlayers().get(4).getUserKey())
                    .size();
            player5CountryCount.setText(String.valueOf(playerCountries));

            int playerTroops =
                user.getGameState()
                    .getTroopsByPlayer(user.getGameState().getPlayers().get(4).getUserKey());
            player5TroopCount.setText(String.valueOf(playerTroops));
            playerCountriesOwned[4] = playerCountries;
            playerTroopsCount[4] = playerTroops;
          } else if (button == playerSixIcon) {
            player6StatsPane.setVisible(true);
            int playerCountries =
                user.getGameState()
                    .getCountriesOwnedByPlayer(user.getGameState().getPlayers().get(5).getUserKey())
                    .size();
            player6CountryCount.setText(String.valueOf(playerCountries));

            int playerTroops =
                user.getGameState()
                    .getTroopsByPlayer(user.getGameState().getPlayers().get(5).getUserKey());
            player6TroopCount.setText(String.valueOf(playerTroops));
            playerCountriesOwned[5] = playerCountries;
            playerTroopsCount[5] = playerTroops;
          }
        });
    button.setOnMouseExited(
        event -> {
          if (button == playerOneIcon) {
            player1StatsPane.setVisible(false);
          } else if (button == playerTwoIcon) {
            player2StatsPane.setVisible(false);
          } else if (button == playerThreeIcon) {
            player3StatsPane.setVisible(false);
          } else if (button == playerFourIcon) {
            player4StatsPane.setVisible(false);
          } else if (button == playerFiveIcon) {
            player5StatsPane.setVisible(false);
          } else if (button == playerSixIcon) {
            player6StatsPane.setVisible(false);
          }
        });
  }


  /**
   * This method is called when the user clicks on the "Most Troops and Most Countries" button.
   *
   * @author vstoll, paukaise
   */
  @FXML
  public void mostTroopsAndMostCountries() {
    if (user.getGameState() == null) {
      return;
    }
    // Determine player with the most countries
    int maxCountries =
        user.getGameState()
            .getCountriesOwnedByPlayer(user.getGameState().getPlayers().get(0).getUserKey())
            .size();
    int mostCountriesLocation = 0;
    for (int i = 1; i < user.getGameState().getPlayers().size(); i++) {
      if (user.getGameState()
          .getCountriesOwnedByPlayer(user.getGameState().getPlayers().get(i).getUserKey())
          .size()
          > maxCountries) {
        maxCountries =
            user.getGameState()
                .getCountriesOwnedByPlayer(user.getGameState().getPlayers().get(i).getUserKey())
                .size();
        mostCountriesLocation = i;
      }
    }
    // Display the icon of the player with the most countries
    mostCountriesImageView.setImage(
        new Image("/pictures/player" + (mostCountriesLocation + 1) + "pb.png"));

    mostCountriesCircle.setStyle(playerColors[mostCountriesLocation]);
    String str = user.getGameState().getPlayers().get(mostCountriesLocation).getName();
    if (str.contains("(Easy)")) {
      str = str.replace("(Easy)", "");
    } else if (str.contains("(Medium)")) {

      str = str.replace("(Medium)", "");
    } else if (str.contains("(Hard)")) {

      str = str.replace("(Hard)", "");
    }
    mostCountriesNameTag.setText(str);

    int maxTroops =
        user.getGameState()
            .getTroopsByPlayer(user.getGameState().getPlayers().get(0).getUserKey());
    int mostTroopsLocation = 0;
    for (int j = 1; j < user.getGameState().getPlayers().size(); j++) {
      if (user.getGameState()
          .getTroopsByPlayer(user.getGameState().getPlayers().get(j).getUserKey()) > maxTroops) {
        maxTroops = playerTroopsCount[j];
        mostTroopsLocation = j;
      }
    }
    // Display the icon of the player with the most troops
    mostTroopsImageView.setImage(
        new Image("/pictures/player" + (mostTroopsLocation + 1) + "pb.png"));
    mostTroopsCircle.setStyle(playerColors[mostTroopsLocation]);
    String str2 = user.getGameState().getPlayers().get(mostTroopsLocation).getName();
    if (str2.contains("(Easy)")) {
      str2 = str2.replace("(Easy)", "");
    } else if (str2.contains("(Medium)")) {

      str2 = str2.replace("(Medium)", "");
    } else if (str2.contains("(Hard)")) {

      str2 = str2.replace("(Hard)", "");
    }
    mostTroopsNameTag.setText(str2);
  }

  /**
   * This method sets the countries to the buttons.
   *
   * @author lkuech
   */
  public void setCountriesToButtons() {
    ArrayList<Country> countries = user.getGameState().getCountries();
    for (int i = 0; i < 42; i++) {
      countries.get(i).setButtonIndex(i);
    }
  }

  /**
   * This method is called when the mouse enters the country. It sets the cursor to hand.
   *
   * @param event - MouseEvent that is triggered when the mouse enters the country
   * @author paukaise
   */
  @FXML
  void onCountryEntered(MouseEvent event) {
    for (SVGPath tb : countryButtons) {
      tb.setCursor(cursor);
    }
  }

  /**
   * This method is called when the mouse exits the country. It sets the cursor back to default.
   *
   * @param event - MouseEvent that is triggered when the mouse exits the country
   * @author paukaise
   */
  @FXML
  void onCountryExited(MouseEvent event) {
    for (SVGPath tb : countryButtons) {
      tb.setCursor(Cursor.DEFAULT);
    }
  }

  /**
   * This method shows the amount of players in game as playerIconsPics on the right side of the
   * board.
   *
   * @author floribau
   */
  private void showAmountOfPlayerIcons() {
    int playerCount = user.getClient().getUsernamesInLobby().size();
    switch (playerCount) {
      case 6:
        playerSixIcon.setVisible(true);
        playerSixIconRect.setVisible(true);
      case 5:
        playerFiveIcon.setVisible(true);
        playerFiveIconRect.setVisible(true);
      case 4:
        playerFourIcon.setVisible(true);
        playerFourIconRect.setVisible(true);
      case 3:
        playerThreeIcon.setVisible(true);
        playerThreeIconRect.setVisible(true);
      case 2:
        playerTwoIcon.setVisible(true);
        playerTwoIconRect.setVisible(true);
        playerOneIcon.setVisible(true);
        playerOneIconRect.setVisible(true);
      default: // do nothing
    }
  }

  /**
   * This method is called when exiting the game. A confirmation alert is shown.
   *
   * @param event - The event that is triggered when the user clicks on the "exit" button.
   * @throws IOException - If the fxml file can't be found.
   * @author vstoll, paukaise.
   */
  @FXML
  protected void switchToMenuSceneFromGame(ActionEvent event) throws IOException {
    Alert alert = new Alert(Alert.AlertType.INFORMATION);
    alert.setTitle("Exit game");
    alert.setHeaderText("You're about to exit this game.");
    alert.setContentText(
        "Are you sure you want to exit? This game will be ended and counted as a loss.");
    DialogPane dialogPane = alert.getDialogPane();
    dialogPane
        .getStylesheets()
        .add(getClass().getResource("/applicationStyle.css").toExternalForm());
    dialogPane.getStyleClass().add("popup-alert");
    dialogPane.setStyle("-fx-background-color: #ffd763;");
    Optional<ButtonType> result = alert.showAndWait();
    if (result.get() == ButtonType.OK) {
      user.getClient().shutdown();
    }
    if (result.isPresent() && result.get() == ButtonType.OK) {
      Parent root = FXMLLoader.load(getClass().getResource("/MenuScene.fxml"));
      stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
      scene = new Scene(root);
      stage.setScene(scene);
      scene.getStylesheets().clear();
      scene.getStylesheets().add(getClass().getResource("/applicationStyle.css").toExternalForm());
      stage.show();
    }
  }

  /**
   * Beginning of a player round informing the player about the number of troops he can place.
   * This method opens a PopUp Window for the amount of available troops to place.
   *
   * @param troops - The amount of troops the player can place.
   * @author paukaise.
   */
  public void showAvailableTroopsMessage(int troops) {
    Platform.runLater(
        () -> {
          troopsToDistributeLabel.setText(String.valueOf(troops));
          avTroopsText.setText(String.valueOf(troops));
          alertPane.setVisible(true);
          PauseTransition visiblePause =
              new PauseTransition(Duration.seconds(3)); // Set the duration to 3 seconds
          visiblePause.setOnFinished(event -> alertPane.setVisible(false));
          visiblePause.play();
        });
  }

  /**
   * This method is called when exiting the AttackScene or TradeInCardsScene. Switches back to the
   * GameScene.
   *
   * @param event - The event that is triggered when the user clicks on the "exit" button on
   *              AttackScene or TradeInCardsScene.
   * @throws IOException - Throws an IOException if the FXML file can't be found.
   * @author vstoll
   */
  @FXML
  protected void exitWindow(ActionEvent event) throws IOException {
    stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    stage.close();
  }

  /**
   * This method is called when opening the ChatScene.
   *
   * @param event - The event that is triggered when the user clicks on the "Chat" button.
   * @throws IOException - Throws an IOException if the FXML file can't be found.
   * @author vstoll, paukaise.
   */
  @FXML
  protected void chatPopUpWindow(ActionEvent event) throws IOException {
    if (event.getSource() == chatButton) {
      Parent root = FXMLLoader.load(getClass().getResource("/ChatScene.fxml"));
      Scene chatScene = new Scene(root);
      Stage chatStage = new Stage();
      chatStage.initStyle(StageStyle.UNDECORATED);
      chatStage.setTitle("Chat");
      chatStage.setScene(chatScene);
      chatStage.initModality(Modality.APPLICATION_MODAL);
      chatStage.centerOnScreen();
      chatStage.show();
    }
  }

  /**
   * This method closes the tradeInCardsPane.
   *
   * @param event - The event that is triggered when the user clicks on the "exit" button.
   * @throws IOException - Throws an IOException if the FXML file can't be found.
   * @author vstoll.
   */
  @FXML
  protected void tradeInCardsPaneClose(ActionEvent event) throws IOException {
    if (event.getSource() == exitTradeCardScene) {
      skipTradeCards = true;
      tradeInCardsPane.setVisible(false);
    }
  }

  /**
   * This method closes the inGameStatsPane.
   *
   * @param event - The event that is triggered when the user clicks on the "exit" button.
   * @throws IOException - Throws an IOException if the FXML file can't be found.
   * @author paukaise.
   */
  @FXML
  protected void inGameStatsClose(ActionEvent event) throws IOException {
    if (event.getSource() == exitStats) {
      inGameStatsPane.setVisible(false);
    }
  }

  /**
   * This method calls the showDice method and sets the diceButton invisible.
   *
   * @param event - The event that is triggered when the user clicks on the "dice" button.
   * @author vstoll.
   */
  @FXML
  protected void dice(ActionEvent event) {
    if (event.getSource().equals(diceButton)) {
      showDice(diceResult);
      diceButton.setVisible(false);
    }
  }

  /**
   * This method is called when opening the TradeInCardsScene.
   *
   * @author paukaise, floribau.
   */
  @FXML
  protected void showTradeInCardsPane() {
    cardsDisplayed =
        user.getGameState()
            .getCardsByPlayerKey(user.getPlayer().getUserKey());
    ivSlotOne.setImage(null);
    ivSlotTwo.setImage(null);
    ivSlotThree.setImage(null);
    resetTradCardsVariables();
    displayAllCards();
    tradeInCardsPane.setVisible(true);
    tradeInCardsLowerPane.setVisible(true);
    tradeInCardsLowerLowerPane.setVisible(true);
  }

  /**
   * This method sets the DiceResult.
   *
   * @param diceResult - The diceResult of the player who is the beginner of the game.
   * @author floribau
   */
  public void setDiceResult(int diceResult) {
    this.diceResult = diceResult;
  }

  /**
   * This method sets the PlayerNumber.
   *
   * @param playerNumber - The playerNumber of the player who is the beginner of the game.
   * @author floribau
   */
  public void setPlayerNumber(int playerNumber) {
    this.playerNumber = playerNumber;
  }

  /**
   * This method is sets the dicePane invisible and continues to game.
   *
   * @param event - The event that is triggered when the user clicks on the "Continue" button.
   * @author paukaise, vstoll.
   */
  @FXML
  protected void continueToGame(ActionEvent event) {
    if (event.getSource().equals(continueButton)) {
      dicePane.setVisible(false);
      user.getClient().sendMessage(new PressedContinueAfterDetermineBeginnerMessage());
      setTextDiceResultMessage();
      troopsInCountryPane.setDisable(true);
      troopsInCountryPane.setVisible(true);
    }
  }

  /**
   * This method shows the Player in the diceResultLabel which number he is.
   *
   * @author vstoll, paukaise
   */
  public void setTextDiceResultMessage() {
    playerNumberTextField.setText("You are Player " + playerNumber);
    playerNumberPane.setVisible(true);

    PauseTransition visiblePause =
        new PauseTransition(Duration.seconds(2)); // Set the duration to 2 seconds
    visiblePause.setOnFinished(event -> playerNumberPane.setVisible(false));
    visiblePause.play();
    StackPane[] playerTags = {
        playerOneTag, playerTwoTag, playerThreeTag, playerFourTag, playerFiveTag, playerSixTag
    };
    playerTags[playerNumber - 1].setVisible(true);
  }

  /**
   * This method increases the number of selected Troops for the Attack.
   *
   * @param event - The event that is triggered when the user clicks on the "plus" button.
   * @author vstoll.
   */
  @FXML
  protected void increaseNumberOfTroops(ActionEvent event) {
    int newValue = Integer.parseInt(numberOfTroops.getText()) + 1;
    numberOfTroops.setText(String.valueOf(newValue));
  }

  /**
   * This method decreases the number of selected Troops for the Attack.
   *
   * @param event -  The event that is triggered when the user clicks on the "minus" button.
   * @author lkuech.
   */
  @FXML
  protected void decreaseNumberOfTroops(ActionEvent event) {
    int oldValue = Integer.parseInt(numberOfTroops.getText());
    if (oldValue > 0) {
      int newValue = oldValue - 1;
      numberOfTroops.setText(String.valueOf(newValue));
    }
  }

  /**
   * This method deactivates all countryButtons.
   *
   * @author vstoll.
   */
  public void deactivateAllCountryButtons() {
    selectedCountry1 = null;
    selectedCountry2 = null;
    if (countryButtons != null) {
      for (SVGPath c : countryButtons) {
        if (c != null) {
          c.setDisable(true);
          changeOpacityCountries(c);
        }
      }
    }
  }

  /**
   * This method deselects all CountryButtons.
   *
   * @author lkuech
   */
  public void deselectAllCountryButtons() {
    if (countryButtons != null) {
      for (SVGPath c : countryButtons) {
        if (c != null) {
        }
      }
    }
  }

  /**
   * This method activates the CountryButton.
   *
   * @param country - The country that should be activated.
   * @author vstoll
   */
  public void activateCountryButton(Country country) {
    if (countryButtons != null) {
      for (SVGPath c : countryButtons) {
        if (c != null) {
          if (c.getId().equalsIgnoreCase(country.getName())) {
            c.setDisable(false);
            changeOpacityCountries(c);
          }
        }
      }
    }
  }

  /**
   * This method deactivates the CountryButton.
   *
   * @param country - The country that should be deactivated.
   * @author paukaise, vstoll
   */
  public void deactivateCountryButton(Country country) {
    countryButtons[country.getButtonIndex()].setDisable(true);
    if (countryButtons != null) {
      for (SVGPath c : countryButtons) {
        if (c != null) {
          if (c.getId().equalsIgnoreCase(country.getName())) {
            c.setDisable(true);
            changeOpacityCountries(c);
          }
        }
      }
    }
  }

  /**
   * This method returns the CountryButton by comparing the ID of the Button with the Name of the
   * Country.
   *
   * @param country - The country which button should be returned.
   * @return The CountryButton.
   * @author lkuech.
   */
  public SVGPath getButtonByCountry(Country country) {
    return countryButtons[country.getButtonIndex()];
  }

  /**
   * This method returns the Country of the Button.
   *
   * @param cb - SVGPath of the Button.
   * @return The Country of the Button.
   * @author lkuech.
   */
  protected Country getCountryByButton(SVGPath cb) {
    return user.getGameState().getCountryByName(cb.getId().toLowerCase());
  }

  /**
   * This method returns the Troops_in_Country_Button of the Country.
   *
   * @param country - The country which button should be returned.
   * @author vstoll.
   */
  protected Text getTroopsInCountryTextByCountry(Country country) {
    return troopsInCountriesText[country.getButtonIndex()];
  }

  /**
   * This method activates all owned countries.
   *
   * @param attackingPlayer - the player who is attacking.
   * @author lkuech.
   */
  public void setOwnedCountriesActive(String attackingPlayer) {
    ToggleGroup attackingGroup = new ToggleGroup();
    for (Country country : user.getGameState().getCountriesOwnedByPlayer(attackingPlayer)) {
      activateCountryButton(country);
    }
  }

  /**
   * This method activates all countries that can attack.
   *
   * @param attackingPlayer - the player who is attacking.
   * @author lkuech.
   */
  public void setAttackCountriesActive(String attackingPlayer) {
    ToggleGroup attackingGroup = new ToggleGroup();
    for (Country country : user.getGameState().getCountriesOwnedByPlayer(attackingPlayer)) {
      boolean hasEnemyNeighbours = false;
      for (Country neighbour : country.getNeighbours()) {
        if (!user.getGameState()
            .getCountryByName(neighbour.getName())
            .getOwner()
            .equals(attackingPlayer)) {
          hasEnemyNeighbours = true;
          break;
        }
      }
      if (country.getTroops() > 1 && hasEnemyNeighbours) {
        activateCountryButton(country);
      }
    }
  }

  /**
   * This method sets countries active for the move troops phase.
   *
   * @param movingPlayer - the player who is moving troops.
   * @author floribau.
   */
  public void setMoveTroopsCountriesActive(String movingPlayer) {
    deactivateAllCountryButtons();
    for (Country country : user.getGameState().getCountriesOwnedByPlayer(movingPlayer)) {
      boolean hasFriendlyNeighbours = false;
      for (Country neighbour : country.getNeighbours()) {
        if (user.getGameState()
            .getCountryByName(neighbour.getName())
            .getOwner()
            .equals(movingPlayer)) {
          hasFriendlyNeighbours = true;
          break;
        }
      }
      if (hasFriendlyNeighbours && country.getTroops() > 1) {
        activateCountryButton(country);
      }
    }
  }

  public void setMoveToCountriesActive(Country country) {
    for (Country neighbour : country.getNeighbours()) {
      if (neighbour.getOwner().equals(country.getOwner())) {
        activateCountryButton(neighbour);
      }
    }
  }

  /**
   * This method sets all neighbouring country buttons active.
   *
   * @param country - The country from which the neighbouring countries should be activated.
   * @author lkuech.
   */
  public void setNeighboursActive(Country country) {
    for (Country neighbour : country.getNeighbours()) {
      if (!neighbour.getOwner().equals(country.getOwner())) {
        activateCountryButton(neighbour);
      }
    }
  }

  /**
   * This method deactivates all neighbouring country buttons of the given country.
   *
   * @param country - Country from which the neighbouring countries should be deactivated.
   * @author lkuech.
   */
  public void deactivateNeighbours(Country country) {
    for (Country neighbour : country.getNeighbours()) {
      if (!neighbour.getOwner().equals(user.getPlayer())) {
        deactivateCountryButton(neighbour);
      }
    }
  }

  /**
   * This method is called when the user clicks on the next phase button. It requests the next
   * phase.
   *
   * @author lkuech.
   */
  public void nextPhaseRequested() {
    nextPhaseButton.setDisable(false);
    while (!nextPhase) {
      try {
        Thread.sleep(10);
      } catch (InterruptedException e) {
        //ignore
      }
    }
    nextPhaseButton.setDisable(true);
    this.nextPhase = false;
  }

  /**
   * This method is called when the user clicks on the next phase button. It selects the attack
   * country and the country to attack.
   *
   * @param attackingPlayer - String of the player who is attacking.
   * @author lkuech.
   */
  public Attack attackSelection(String attackingPlayer) {
    nextPhaseButton.setDisable(false);
    while (this.selectedCountry1 == null) {
      try {
        Thread.sleep(10);
      } catch (InterruptedException e) {
        //ignore
      }
      if (nextPhase) {
        nextPhaseButton.setDisable(true);
        nextPhase = false;
        return null;
      }
    }

    Country currentAttackCountry = this.selectedCountry1;
    setNeighboursActive(currentAttackCountry);
    while (selectedCountry2 == null) {
      if (nextPhase) {
        nextPhaseButton.setDisable(true);
        nextPhase = false;
        return null;
      }

      try {
        Thread.sleep(50);
      } catch (InterruptedException e) {
        //ignore
      }

      if (!(this.selectedCountry1.equals(currentAttackCountry))) {
        deactivateNeighbours(currentAttackCountry);
        setAttackCountriesActive(attackingPlayer);
        currentAttackCountry = this.selectedCountry1;
        setNeighboursActive(currentAttackCountry);
      }
    }
    while (this.troopsPlaced < 0) {
      try {
        Thread.sleep(50);
      } catch (InterruptedException e) {
        //ignore
      }
    }

    Attack attack = new Attack();

    if (this.troopsPlaced == 0) {
      attack.setAttackValid(false);
      attack.setAttackingCountry(selectedCountry1);
      attack.setDefendingCountry(selectedCountry2);
      attack.setTroopsUsed(troopsPlaced);
    } else {
      attack.setAttackValid(true);
      attack.setAttackingCountry(selectedCountry1);
      attack.setDefendingCountry(selectedCountry2);
      attack.setTroopsUsed(troopsPlaced);
    }

    if (selectedCountry1 != null) {
      removeCountryBorder(getButtonByCountry(selectedCountry1));
    }
    if (selectedCountry2 != null) {
      removeCountryBorder(getButtonByCountry(selectedCountry2));
    }
    deactivateAllCountryButtons();
    return attack;
  }

  /**
   * This method is called when the user wants to trade cards for troops.
   *
   * @param playerKey    - String of the player who wants to trade cards.
   * @param skipPossible - boolean if the player can skip the trade.
   * @author floribau.
   */
  public Card[] tradeCardsSelection(String playerKey, boolean skipPossible) {
    Platform.runLater(
        () -> {
          cardsDisplayed = user.getGameState().getCardsByPlayerKey(playerKey);
          showTradeInCardsPane();
          exitTradeCardScene.setVisible(skipPossible);
        });

    cardsDisplayed = user.getGameState().getCardsByPlayerKey(playerKey);
    resetTradCardsVariables();
    displayAllCards();
    while (!tradeCardsPressed) {
      try {
        Thread.sleep(10);
      } catch (InterruptedException e) {
        // ignore
      }
      Platform.runLater(
          () -> {
            if (checkCardCombination()) {
              tradeCardsButton.setDisable(false);
              tradeCardsButton.setText("Trade Cards");
            } else {
              tradeCardsButton.setDisable(true);
              tradeCardsButton.setText("No matching cards");
            }
          });
      if (skipTradeCards) {
        return null;
      }
    }
    return cardsInSlot;
  }

  /**
   * This method checks if the cards in the card slot are a valid combination.
   *
   * @author floribau.
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
   * This method is called when a player wants to place troops on a country.
   *
   * @param selectableCountries - ArrayList of countries that can be selected
   * @author lkuech.
   */
  public Country distributionSelection(ArrayList<Country> selectableCountries) {
    for (Country c : selectableCountries) {
      activateCountryButton(c);
    }
    while (selectedCountry1 == null) {
      try {
        Thread.sleep(10);
      } catch (InterruptedException e) {
        // ignore
      }
    }
    Country selectedCountry = selectedCountry1;
    if (selectedCountry1 != null) {
      removeCountryBorder(getButtonByCountry(selectedCountry1));
    }
    if (selectedCountry2 != null) {
      removeCountryBorder(getButtonByCountry(selectedCountry2));
    }
    this.selectedCountry1 = null;
    deactivateAllCountryButtons();
    deselectAllCountryButtons();

    return selectedCountry;
  }

  /**
   * This method is called when a player wants to move troops from one country to another. It
   * returns a Move object with the selected countries and the number of troops to be moved.
   *
   * @param player - the player who is moving troops
   * @author lkuech.
   */
  public Move movingTroopsSelection(String player) {
    selectedCountry1 = null;
    selectedCountry2 = null;
    nextPhase = false;
    Move move = new Move();

    moveValid = false;
    confirmationComplete = false;
    troopsMoved = 0;

    while (selectedCountry1 == null) {
      try {
        Thread.sleep(10);
      } catch (InterruptedException e) {

      }
      if (nextPhase) {
        nextPhaseButton.setDisable(true);
        nextPhase = false;
        deactivateAllCountryButtons();
        Move nexPhase = new Move();
        nexPhase.setNextPhase(true);
        nexPhase.setMoveValid(true);
        return nexPhase;
      }
    }

    move.setFromCountry(this.selectedCountry1);
    SVGPath fromCountrySVGPath = getButtonByCountry(this.selectedCountry1);
    fromCountrySVGPath.setDisable(true);

    while (selectedCountry2 == null) {
      try {
        Thread.sleep(10);
      } catch (InterruptedException e) {

      }
      if (nextPhase) {
        deactivateAllCountryButtons();
        nextPhaseButton.setDisable(true);
        nextPhase = false;
        Move nexPhase = new Move();
        nexPhase.setNextPhase(true);
        nexPhase.setMoveValid(true);
        return nexPhase;
      }
    }
    while (!this.confirmationComplete) {
      try {
        Thread.sleep(20);
      } catch (InterruptedException e) {

      }
    }

    move.setToCountry(this.selectedCountry2);
    move.setTroops(this.troopsMoved);
    move.setMoveValid(this.moveValid);

    if (selectedCountry1 != null) {
      removeCountryBorder(getButtonByCountry(selectedCountry1));
    }
    if (selectedCountry2 != null) {
      removeCountryBorder(getButtonByCountry(selectedCountry2));
    }
    this.selectedCountry1 = null;
    this.selectedCountry2 = null;
    deactivateAllCountryButtons();
    deselectAllCountryButtons();

    return move;
  }

  /**
   * This method places troops on the map.
   *
   * @param player             - the player who is placing troops
   * @param troopsToDistribute - the number of troops to distribute
   * @author lkuech.
   */
  public Placement placeTroopSelection(String player, int troopsToDistribute) {
    this.troopsPlaced = -1;
    setOwnedCountriesActive(player);
    this.maxTroopsToDistribute = troopsToDistribute;

    while (this.selectedCountry1 == null) {
      try {
        Thread.sleep(10);
      } catch (InterruptedException e) {
        //ignore
      }
    }

    while (this.troopsPlaced < 0) {
      try {
        Thread.sleep(10);
      } catch (InterruptedException e) {
        //ignore
      }
    }
    int troopsPlaced = this.troopsPlaced;
    this.troopsPlaced = -1;

    Country selectedCountry = this.selectedCountry1;

    if (selectedCountry1 != null) {
      removeCountryBorder(getButtonByCountry(selectedCountry1));
    }
    if (selectedCountry2 != null) {
      removeCountryBorder(getButtonByCountry(selectedCountry2));
    }
    this.selectedCountry1 = null;
    this.selectedCountry2 = null;
    deactivateAllCountryButtons();
    deselectAllCountryButtons();
    return new Placement(selectedCountry, troopsPlaced);
  }

  /**
   * This method shows the animation of the dice and the end result in the gui. It uses a Timeline
   * with a KeyFrame-Animation. Functionality: Create a Timeline with a KeyFrame animation that runs
   * every 100ms. Set the number of repetitions to 20 to achieve a duration of 2 seconds. Start the
   * Timeline. Create a new Timeline to set the final image after the animation. Start the
   * end-Timeline after the end of the animation.
   *
   * @param i - the number of the dice.
   * @author vstoll.
   */
  private void showDice(int i) {
    Image[] images = {
        new Image("pictures/dice_1.png"),
        new Image("pictures/dice_2.png"),
        new Image("pictures/dice_3.png"),
        new Image("pictures/dice_4.png"),
        new Image("pictures/dice_5.png"),
        new Image("pictures/dice_6.png")
    };
    ImageView imageView = diceImage;
    Timeline timeline =
        new Timeline(
            new KeyFrame(
                Duration.millis(100),
                event -> {
                  imageView.setImage(images[(int) (Math.random() * 6)]);
                }));
    timeline.setCycleCount(20);
    timeline.play();
    Timeline endTimeline =
        new Timeline(
            new KeyFrame(
                Duration.seconds(0.1),
                event -> {
                  diceImage.setImage(images[i - 1]);
                }));
    timeline.setOnFinished(event -> endTimeline.play());
  }

  /**
   * This method is called when the user clicks on the "Next Phase" button. It changes the current
   * phase and the color of the phase rectangles.
   *
   * @author paukaise.
   */
  @FXML
  private void nextStageButton() {
    this.nextPhase = true;
    if (stageOneRect.getFill().equals(Paint.valueOf("#1e6b9e"))) {
      stageOneRect.setFill(Paint.valueOf("#59625d"));
      stageTwoRect.setFill(Paint.valueOf("#1e6b9e"));
      currentPhaseText.setText("ATTACK");
    } else if (stageTwoRect.getFill().equals(Paint.valueOf("#1e6b9e"))) {
      stageTwoRect.setFill(Paint.valueOf("#59625d"));
      stageThreeRect.setFill(Paint.valueOf("#1e6b9e"));
      currentPhaseText.setText("FORTIFY");
      nextPhaseButton.setText("End Turn");
    } else if (stageThreeRect.getFill().equals(Paint.valueOf("#1e6b9e"))) {
      stageThreeRect.setFill(Paint.valueOf("#59625d"));
      stageOneRect.setFill(Paint.valueOf("#1e6b9e"));
      nextPhaseButton.setText("Next Phase");
      currentPhaseText.setText("DRAFT");
    }
  }

  /**
   * This method is called when the user clicks on a country. It sets the selected Country and
   * deactivates all country buttons afterwords.
   *
   * @param event Event, which is triggered when the user clicks on a country button.
   * @throws IOException - Exception, which is thrown when the fxml file is not found.
   * @author lkuech.
   */
  @FXML
  protected void selectCountry(MouseEvent event) throws IOException {
    SVGPath currentCountryButton = (SVGPath) event.getSource();
    switch (this.gamePhase) {
      case ATTACK_PHASE -> {
        Country selectedCountry = getCountryByButton(currentCountryButton);
        if (selectedCountry1 == null) {
          selectedCountry1 = selectedCountry;
          paintCountryBorder(currentCountryButton);
        } else if (selectedCountry.getOwner().equals(selectedCountry1.getOwner())) {
          removeCountryBorder(getButtonByCountry(selectedCountry1));
          selectedCountry1 = selectedCountry;
          paintCountryBorder(currentCountryButton);
        } else {
          selectedCountry2 = selectedCountry;
          paintCountryBorder(currentCountryButton);
          moveTroopsConfirm.setText("Roll the Dice!");
          diceImageView.setVisible(true);
          showMoveTroopsPane(selectedCountry2, selectedCountry1.getTroops() - 1);
        }
      }
      case DISTRIBUTION_PHASE -> {
        diceImageView.setVisible(false);
        selectedCountry1 = getCountryByButton(currentCountryButton);
      }
      case PLACE_TROOP_PHASE -> {
        diceImageView.setVisible(false);
        selectedCountry1 = getCountryByButton(currentCountryButton);
        paintCountryBorder(currentCountryButton);
        showMoveTroopsPane(selectedCountry1, maxTroopsToDistribute);
      }
      case MOVE_TROOPS_PHASE -> {
        diceImageView.setVisible(false);
        if (selectedCountry1 == null) {
          deactivateAllCountryButtons();
          setMoveToCountriesActive(getCountryByButton(currentCountryButton));
          selectedCountry1 = getCountryByButton(currentCountryButton);
        } else {
          selectedCountry2 = getCountryByButton(currentCountryButton);
          showMoveTroopsPane(selectedCountry2, selectedCountry1.getTroops() - 1);
        }
        paintCountryBorder(currentCountryButton);
      }
      default -> {
      }
    }
  }

  /**
   * This method returns the selectedCountry.
   *
   * @return selectedCountry
   * @author lkuech
   */
  public Country getSelectedCountry1() {
    return selectedCountry1;
  }

  /**
   * This method sets the selectedCountry.
   *
   * @author lkuech
   */
  public void setSelectedCountry1(Country selectedCountry1) {
    this.selectedCountry1 = selectedCountry1;
  }


  /**
   * This method returns the selectedCountry2.
   *
   * @param selectedCountry2 - Country, which is selected.
   * @author lkuech.
   */
  public void setSelectedCountry2(Country selectedCountry2) {
    this.selectedCountry2 = selectedCountry2;
  }

  /**
   * This method gets the user.
   *
   * @author lkuech.
   */
  public User getUser() {
    return user;
  }

  /**
   * This method increases the number of troops, which are used to attack by counting up the
   * moveTroopsNumber label.
   *
   * @param event Event, which is triggered when the user clicks on the plus button.
   * @author vstoll.
   */
  @FXML
  protected void increaseNumberOfMovingTroops(ActionEvent event) {
    // ToDo Don't allow to count higher than the number of available troops
    if (Integer.parseInt(moveTroopsNumber.getText()) + 1 <= maxTroopsToDistribute) {
      int newValue = Integer.parseInt(moveTroopsNumber.getText()) + 1;
      moveTroopsNumber.setText(String.valueOf(newValue));
    }
  }

  /**
   * This method decreases the number of troops, which are used to attack by counting down the
   * moveTroopsNumber label.
   *
   * @param event Event, which is triggered when the user clicks on the minus button.
   * @author vstoll.
   */
  @FXML
  protected void decreaseNumberOfMovingTroops(ActionEvent event) {
    int oldValue = Integer.parseInt(moveTroopsNumber.getText());
    if (oldValue > 1) {
      int newValue = oldValue - 1;
      moveTroopsNumber.setText(String.valueOf(newValue));
    }
  }

  /**
   * This method gets the gamePhase.
   *
   * @author lkuech.
   */
  public GamePhase getGamePhase() {
    return this.gamePhase;
  }

  /**
   * This method sets the gamePhase to the given GamePhase.
   *
   * @param gamePhase - GamePhase to set
   * @author lkuech.
   */
  public void setGamePhase(GamePhase gamePhase) {
    this.gamePhase = gamePhase;
  }

  /**
   * This method sets the number of troops, which are used to attack, to the maximum number of
   * troops, which are available in the selected country.
   *
   * @param event Event, which is triggered when the user clicks on the max button.
   * @author vstoll.
   */
  @FXML
  protected void setMaxAttackingTroops(ActionEvent event) {
    AttackMove am = (AttackMove) user.getGameState().getPhase();
    if (am.getAttackingCountry() != null) {
      int newValue = am.getAttackingCountry().getTroops() - 1;
      moveTroopsNumber.setText(String.valueOf(newValue));
    }
  }

  /**
   * This method shows the moveTroopsPane at the given country. The method should be called in the
   * MoveTroopsPhase after selecting the second country.
   *
   * @param toCountry Country, at which the new Pane should be shown at.
   * @param maxTroops Maximum number of troops, which can be moved.
   * @author vstoll, lkuech.
   */
  public void showMoveTroopsPane(Country toCountry, int maxTroops) {
    this.troopsPlaced = -1;
    moveTroopsPlus.setDisable(false);
    moveTroopsMinus.setDisable(false);
    moveTroopsPaneBackground.setVisible(true);
    moveTroopsPaneBackground.setDisable(false);
    moveTroopsNumber.setText("0");
    moveTroopsBackgroundButton.setVisible(true);

    this.maxTroopsToDistribute = maxTroops;

    Text textNode = getTroopsInCountryTextByCountry(toCountry);
    double otherButtonSceneX = textNode.localToScene(textNode.getBoundsInLocal()).getMinX();
    double otherButtonSceneY = textNode.localToScene(textNode.getBoundsInLocal()).getMinY();
    Parent commonParent = getCommonParent(moveTroopsPane, textNode);
    double moveTroopsPaneParentX =
        commonParent.sceneToLocal(otherButtonSceneX, otherButtonSceneY).getX();
    double moveTroopsPaneParentY =
        commonParent.sceneToLocal(otherButtonSceneX, otherButtonSceneY).getY();
    moveTroopsPane.setLayoutX(moveTroopsPaneParentX + 25);
    moveTroopsPane.setLayoutY(moveTroopsPaneParentY + 25);

    moveTroopsPane.setVisible(true);
    moveTroopsPane.setDisable(false);
    moveTroopsPlus.setDisable(false);
    moveTroopsMinus.setDisable(false);
    moveTroopsMaxTroops.setDisable(false);
    moveTroopsPane.toFront();
  }

  /**
   * This method moves the move troops pane to the background.
   *
   * @param event Event, which is triggered when the user clicks on the confirm button.
   * @author lkuech, vstoll
   */
  @FXML
  public void moveTroopsBackgroundButtonClicked(ActionEvent event) {
    moveTroopsPane.setVisible(false);
    moveTroopsBackgroundButton.setVisible(false);
    moveTroopsPaneBackground.setVisible(false);
    moveTroopsPaneBackground.setDisable(true);
    this.troopsPlaced = 0;
    if (gamePhase != null && gamePhase.equals(GamePhase.MOVE_TROOPS_PHASE)) {
      this.confirmationComplete = true;
      this.moveValid = false;
      this.troopsMoved = 0;
    }
  }

  /**
   * This method sets the number of troops, which should be moved, to the maximum number of troops.
   *
   * @param event - ActionEvent which is triggered when the user clicks on the max button.
   * @throws IOException Exception, which is thrown when the fxml file can't be loaded.
   * @author lkuech
   */
  @FXML
  protected void setMaxMovingTroops(ActionEvent event) {
    moveTroopsNumber.setText(String.valueOf(maxTroopsToDistribute));
  }

  /**
   * This method confirms the number of troops, which should be moved. The method should be called
   * in the MoveTroopsPhase after selecting the second country.
   *
   * @param event Event, which is triggered when the user clicks on the confirm button.
   * @throws IOException Exception, which is thrown when the fxml file can't be loaded.
   * @author vstoll
   */
  @FXML
  public void confirmMovingTroops(ActionEvent event) throws IOException {
    moveTroopsPaneBackground.setDisable(true);
    moveTroopsPane.setVisible(false);
    moveTroopsConfirm.setText("Confirm");
    diceImageView.setVisible(false);
    moveTroopsBackgroundButton.setVisible(false);
    moveTroopsBackgroundButton.setVisible(false);
    if (gamePhase.equals(GamePhase.MOVE_TROOPS_PHASE)) {
      this.confirmationComplete = true;
      this.troopsMoved = Integer.parseInt(moveTroopsNumber.getText());
      this.moveValid = true;
    } else {
      this.troopsPlaced = Integer.parseInt(moveTroopsNumber.getText());
      maxTroopsToDistribute -= troopsPlaced;
      troopsToDistributeLabel.setText(String.valueOf(maxTroopsToDistribute));
    }
  }

  /**
   * This method returns the Text, which is shown on the country button of the given country.
   *
   * @param country Country, which text should be gotten.
   * @author lkuech
   */
  public Text getTextByCountry(Country country) {
    return troopsInCountriesText[country.getButtonIndex()];
  }

  /**
   * This method repaints the country button of the given country. The method should be called after
   * a country was conquered.
   *
   * @param country Country, which should be repainted.
   * @author lkuech
   */
  public void changeBackgroundColor(Country country) {
    SVGPath countryButton = getButtonByCountry(country);
    if (!countryButton.getStyle().contains("-fx-fill")) { // todo INFO: SVGPath changes
      countryButton.setStyle(
          countryButton.getStyle()
              + " -fx-fill: "
              + user.getGameState().getPlayerByUserKey(country.getOwner()).getPlayerColour()
              + ";");
    } else {
      String style = countryButton.getStyle();
      int startIndex = style.indexOf("-fx-fill: ") + 10; // todo INFO: SVGPath changes
      int endIndex = style.indexOf(";", startIndex);
      String oldStyle = style.substring(startIndex, endIndex);

      String newStyle =
          style.replaceFirst(
              oldStyle + ";",
              user.getGameState().getPlayerByUserKey(country.getOwner()).getPlayerColour() + ";");
      if (!newStyle.equals(style)) {
        countryButton.setStyle(newStyle);
      }
    }
  }

  /**
   * This method repaints the GUI. It should be called after every change in the gameState. It
   * repaints the countries and the player icons. It also highlights the current player.
   *
   * @param countryButton - SVGPath of the country, which should be repainted.
   * @author paukaise, vstoll, floribau, lkuech.
   */
  public void changeOpacityCountries(SVGPath countryButton) {

    if (countryButton.isDisable()) {
      String style = countryButton.getStyle();
      int startIndex = style.indexOf("-fx-opacity: ") + 13;
      int endIndex = style.indexOf(";", startIndex);
      String oldStyle = style.substring(startIndex, endIndex);

      String newStyle = style.replaceFirst(oldStyle + ";", "0.55;");
      countryButton.setStyle(newStyle);
    } else {
      String style2 = countryButton.getStyle();
      int startIndex = style2.indexOf("-fx-opacity: ") + 13;
      int endIndex = style2.indexOf(";", startIndex);
      String oldStyle = style2.substring(startIndex, endIndex);

      String newStyle = style2.replaceFirst(oldStyle + ";", "1.0;");
      countryButton.setStyle(newStyle);
    }
  }

  /**
   * This method repaints the GUI. It should be called after every change in the gameState. It
   * repaints the countries and the player icons. It also highlights the current player.
   *
   * @param gameState - GameState, which should be used to repaint the GUI.
   * @author paukaise, vstoll, floribau, lkuech.
   */
  public void repaintGui(GameState gameState) {
    Platform.runLater(
        () -> {
          for (Country country : gameState.getCountries()) {
            if (country.getOwner() != null) {
              getButtonByCountry(country).getFill();
              changeBackgroundColor(country);
              removeCountryBorder(getButtonByCountry(country));
              Text troopText = getTextByCountry(country);
              String newCountryText = "" + country.getTroops();
              boolean attCountry = false;
              boolean defCountry = false;
              if (gameState.getLastAttackingCountry() != null) {
                if (troopText.equals(
                    getTroopsInCountryTextByCountry(
                        gameState.getCountryByName(gameState.getLastAttackingCountry())))) {
                  attCountry = true;
                }
              }
              if (gameState.getLastDefendingCountry() != null) {
                if (troopText.equals(
                    getTroopsInCountryTextByCountry(
                        gameState.getCountryByName(gameState.getLastDefendingCountry())))) {
                  defCountry = true;
                }
              }

              if (!troopText.getText().equals(newCountryText) || attCountry || defCountry) {
                if (!gameState.getCurrentPlayerKey().equals(user.getPlayer())) { // &&
                  troopText.setText(newCountryText);
                  troopText.setFill(javafx.scene.paint.Color.BLACK);
                  troopText.setFont(Font.font(Font.getDefault().getFamily(), FontWeight.NORMAL, 12));
                  new Thread(
                      () -> {
                        try {
                          Platform.runLater(
                              () -> {
                                troopText.setFill(javafx.scene.paint.Color.BLACK);
                                troopText.setFont(
                                    Font.font("Berlin Sans FB", FontWeight.BOLD, 20));
                              });
                          Thread.sleep(1000);
                          Platform.runLater(
                              () -> {
                                troopText.setText(String.valueOf(newCountryText));
                                troopText.setFill(javafx.scene.paint.Color.BLACK);
                                troopText.setFont(
                                    Font.font("Berlin Sans FB", FontWeight.NORMAL, 12));
                              });
                        } catch (InterruptedException e) {
                          //ignore
                        }
                      })
                      .start();
                } else {
                  troopText.setText(newCountryText);
                }
              }
            }
          }
          // create an array to hold the player icon objects
          Button[] playerIcons = {
              playerOneIcon,
              playerTwoIcon,
              playerThreeIcon,
              playerFourIcon,
              playerFiveIcon,
              playerSixIcon
          };

          // remove onTurn style class from all player icons
          for (Button playerIcon : playerIcons) {
            playerIcon.getStyleClass().remove("gs_button_playerIcon_onTurn");
            playerIcon.getStyleClass().add("gs_button_playerIcon");
          }

          // add onTurn style class to current player's icon
          int currentPlayerNumber =
              gameState.getPlayerByUserKey(gameState.getCurrentPlayerKey()).getPlayerNumber();
          if (currentPlayerNumber >= 1 && currentPlayerNumber <= playerIcons.length) {
            Button currentPlayerIcon = playerIcons[currentPlayerNumber - 1];
            currentPlayerIcon.getStyleClass().add("gs_button_playerIcon_onTurn");
          }
          switch (gameState.getGamePhase()) {
            case DISTRIBUTION_PHASE:
              // todo check if it works
              nextPhasePane.setVisible(true);
              nextPhaseButton.setVisible(false);
              showCurrentPlayerImage.setImage(
                  new Image(
                      getClass()
                          .getResourceAsStream(
                              "/pictures/player" + currentPlayerNumber + "pb.png")));
              showCurrentPlayerCircle.setStyle(
                  playerColors[currentPlayerNumber - 1]
                      + "; "
                      + "-fx-border-color: #000; "
                      + "-fx-background-radius: 100; "
                      + "-fx-border-radius: 100;");
              // troopsToDistributeLabel.setText(String.valueOf(gameState.getTroopsToDistribute()));
              break;
            case PLACE_TROOP_PHASE:
              // todo check if it works
              nextPhasePane.setVisible(true);
              nextPhaseButton.setDisable(true);
              showCurrentPlayerImage.setImage(
                  new Image(
                      getClass()
                          .getResourceAsStream(
                              "/pictures/player" + currentPlayerNumber + "pb.png")));
              showCurrentPlayerCircle.setStyle(
                  playerColors[currentPlayerNumber - 1]
                      + "; "
                      + "-fx-border-color: #000; "
                      + "-fx-background-radius: 100; "
                      + "-fx-border-radius: 100;");
              troopsToDistributeLabel.setText(String.valueOf(gameState.getTroopsToDistribute()));
              break;
            case ATTACK_PHASE:
            case MOVE_TROOPS_PHASE:
              // hi
              nextPhasePane.setVisible(true);
              nextPhaseButton.setDisable(false);
              // set current player image in nextPhasePane
              showCurrentPlayerImage.setImage(
                  new Image(
                      getClass()
                          .getResourceAsStream(
                              "/pictures/player" + currentPlayerNumber + "pb.png")));
              showCurrentPlayerCircle.setStyle(
                  playerColors[currentPlayerNumber - 1]
                      + "; "
                      + "-fx-border-color: #000; "
                      + "-fx-background-radius: 100; "
                      + "-fx-border-radius: 100;");
              troopsToDistributeLabel.setText(String.valueOf(gameState.getTroopsToDistribute()));
              break;
            default:
          }

          // setting the infos displayed in nextPhasePane
          switch (gameState.getGamePhase()) {
            case DISTRIBUTION_PHASE -> {
              stageOneRect.setFill(Paint.valueOf("#59625d"));
              stageTwoRect.setFill(Paint.valueOf("#59625d"));
              stageThreeRect.setFill(Paint.valueOf("#59625d"));
              currentPhaseText.setText("CLAIM COUNTRIES");
              troopsToDistributeLabel.setText(String.valueOf(gameState.getTroopsToDistribute()));
            }
            case PLACE_TROOP_PHASE -> {
              stageThreeRect.setFill(Paint.valueOf("#59625d"));
              stageTwoRect.setFill(Paint.valueOf("#59625d"));
              stageOneRect.setFill(Paint.valueOf("#1e6b9e"));
              nextPhaseButton.setText("Next Phase");
              currentPhaseText.setText("PLACE TROOPS");
            }
            case ATTACK_PHASE -> {
              stageOneRect.setFill(Paint.valueOf("#59625d"));
              stageTwoRect.setFill(Paint.valueOf("#1e6b9e"));
              stageThreeRect.setFill(Paint.valueOf("#59625d"));
              currentPhaseText.setText("ATTACK");
              nextPhaseButton.setText("Next Phase");
            }
            case MOVE_TROOPS_PHASE -> {
              stageOneRect.setFill(Paint.valueOf("#59625d"));
              stageTwoRect.setFill(Paint.valueOf("#59625d"));
              stageThreeRect.setFill(Paint.valueOf("#1e6b9e"));
              currentPhaseText.setText("MOVE TROOPS");
              nextPhaseButton.setText("End Turn");
            }
            default -> {
            }
          }
          nextPhaseButton.setVisible(
              gameState.getCurrentPlayerKey().equals(user.getPlayer().getUserKey())
                  && !gameState.getGamePhase().equals(GamePhase.DISTRIBUTION_PHASE));
        });
    // greying out all eliminated players
    for (String eliminatedKey : gameState.getEliminatedPlayers()) {
      greyOutPlayerIcons(gameState.getPlayerByUserKey(eliminatedKey).getPlayerNumber());
    }
  }

  /**
   * This method displays all cards, which are in the cardsDisplayed list (all the Players cards at
   * the beginning).
   *
   * @author vstoll.
   */
  public void displayAllCards() {
    Platform.runLater(
        () -> {
          cardDisplayFlowPane.getChildren().clear();
          Iterator<Card> iterator = cardsDisplayed.iterator();
          while (iterator.hasNext()) {
            Card c = iterator.next();
            displayCard(c);
          }
        });
  }

  /**
   * This method resets the tradeCards variables to their default values.
   *
   * @author floribau.
   */
  public void resetTradCardsVariables() {
    ivSlotOne.setImage(null);
    cardsInSlot[0] = null;
    ivSlotTwo.setImage(null);
    cardsInSlot[1] = null;
    ivSlotThree.setImage(null);
    cardsInSlot[2] = null;
    tradeCardsPressed = false;
    skipTradeCards = false;
  }

  /**
   * This method shows the given card in the cardDisplayFlowPane.
   *
   * @param c - Card, which should be shown on the cardDisplayFlowPane.
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
   * Method that is called when the user clicks on the "Trade Cards" button.
   *
   * @param event - ActionEvent that is triggered when the button is clicked
   * @author vstoll
   */
  public void tradedInCards(ActionEvent event) {
    tradeCardsPressed = true;
    ivSlotOne.setImage(null);
    ivSlotTwo.setImage(null);
    ivSlotThree.setImage(null);
    tradeCardsButton.setText("No matching cards");
    tradeCardsButton.setDisable(true);
    tradeInCardsPane.setVisible(false);
  }

  /**
   * Method that paints the border of a country white if it is not already white, otherwise it
   * paints it back to the original color.
   *
   * @param countryButton - SVGPath of the country that should be painted
   * @author lkuech
   */
  public void paintCountryBorder(SVGPath countryButton) {
    Country country = getCountryByButton(countryButton);
    if (!countryButton.getStyle().contains("-fx-fill:")) { // todo INFO: SVGPath changes
      countryButton.setStyle(
          countryButton.getStyle() + " -fx-fill: white;"); // todo INFO: SVGPath changes
    } else {
      String style = countryButton.getStyle();
      int startIndex = style.indexOf("-fx-stroke: ") + 12; // todo INFO: SVGPath changes
      int endIndex = style.indexOf(";", startIndex) + 1;
      String oldStyle = style.substring(startIndex, endIndex);

      String newStyle = style.replaceFirst(oldStyle, " white;");
      if (!newStyle.equals(style)) {
        countryButton.setStyle(newStyle);
      }
    }

    if (!countryButton.getStyle().contains("-fx-stroke-width: ")) { // todo INFO: SVGPath changes
      countryButton.setStyle(
          countryButton.getStyle() + " -fx-stroke-width: 2;"); // todo INFO: SVGPath changes
    } else {
      String style = countryButton.getStyle();
      int startIndex = style.indexOf("-fx-stroke-width: ") + 18; // todo INFO: SVGPath changes
      int endIndex = style.indexOf(";", startIndex) + 1;
      String oldStyle = style.substring(startIndex, endIndex);

      String newStyle = style.replaceFirst(oldStyle, "2;");
      if (!newStyle.equals(style)) {
        countryButton.setStyle(newStyle);
      }
    }
  }

  /**
   * Method to remove the border of a country.
   *
   * @param countryButton - SVGPath of the country that should be painted.
   * @author lkuech
   */
  public void removeCountryBorder(SVGPath countryButton) {
    Country country = getCountryByButton(countryButton);
    if (!countryButton.getStyle().contains("-fx-stroke:")) { // todo INFO: SVGPath changes
      countryButton.setStyle(
          countryButton.getStyle() + " -fx-stroke: black;"); // todo INFO: SVGPath changes
    } else {
      String style = countryButton.getStyle();
      int startIndex = style.indexOf(" -fx-stroke: ") + 12;
      int endIndex = style.indexOf(";", startIndex) + 1;
      String oldStyle = style.substring(startIndex, endIndex);

      String newStyle = style.replaceFirst(oldStyle, " black;");
      if (!newStyle.equals(style)) {
        countryButton.setStyle(newStyle);
      }
    }

    if (!countryButton.getStyle().contains("-fx-stroke-width: ")) { // todo INFO: SVGPath changes
      countryButton.setStyle(
          countryButton.getStyle() + " -fx-stroke-width: 1;"); // todo INFO: SVGPath changes
    } else {
      String style = countryButton.getStyle();
      int startIndex = style.indexOf("-fx-stroke-width: ") + 18; // todo INFO: SVGPath changes
      int endIndex = style.indexOf(";", startIndex) + 1;
      String oldStyle = style.substring(startIndex, endIndex);

      String newStyle = style.replaceFirst(oldStyle, "1;");
      if (!newStyle.equals(style)) {
        countryButton.setStyle(newStyle);
      }
    }
  }

  /**
   * Method to show the endPane, which shows the winner and the position of the player.
   *
   * @author lkuech, floribau
   */
  public void showEndPane() {
    Player winner = user.getGameState().getWinner();
    endPane.setVisible(true);
    endPane.toFront();
    String playerKey = user.getPlayer().getUserKey();

    int position = 5;
    boolean won = true;
    for (int i = user.getGameState().getEliminatedPlayers().size(); i > 0; i--) {
      if (!playerKey.equals(user.getGameState().getEliminatedPlayers().get(i - 1))) {
        position--;
      } else {
        won = false;
      }
    }
    if (won) {
      position = 5;
    }

    double factor;
    switch (user.getGameState().getEliminatedPlayers().size() + 1) {
      case 2:
        factor = 0.0;
        break;
      case 3:
        factor = 0.25;
        break;
      case 4:
        factor = 0.5;
        break;
      case 5:
        factor = 0.75;
        break;
      case 6:
        factor = 1.0;
        break;
      default:
        factor = 0;
        break;
    }

    double scoreFactor = 0.5 * factor + 0.5;

    int gainedPoints = (int) (position * 10 * scoreFactor);

    eloLabel.setText("You gained\n" + String.valueOf(gainedPoints) + " points.");
    String str = winner.getName();
    if (str.contains("(Easy)")) {
      str = str.replace("(Easy)", "");
    } else if (str.contains("(Medium)")) {

      str = str.replace("(Medium)", "");
    } else if (str.contains("(Hard)")) {

      str = str.replace("(Hard)", "");
    }
    winnerLabel.setText(str);
    int winnerNumber = winner.getPlayerNumber();
    winnerImageView.setImage(new Image(playerIconsPics[winnerNumber - 1]));
    endCirclePlayer.setStyle(playerColors[winnerNumber - 1]);
  }

  /**
   * Method that is called when the leave button is clicked. It changes the scene to the menu
   * scene.
   *
   * @param event - ActionEvent that is triggered when the button is clicked
   * @author vstoll
   */
  @FXML
  private void leaveEndMessage(ActionEvent event) throws IOException {
    Parent root = FXMLLoader.load(getClass().getResource("/MenuScene.fxml"));
    stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    scene = new Scene(root);
    stage.setScene(scene);
    scene.getStylesheets().clear();
    scene.getStylesheets().add(getClass().getResource("/applicationStyle.css").toExternalForm());
    stage.show();
  }

  /**
   * Method that gets common parent of two nodes.
   *
   * @param node1 - first node
   * @param node2 - second node
   * @author vstoll
   */
  private Parent getCommonParent(Node node1, Node node2) {
    Set<Node> parents = new HashSet<>();
    Node parent1 = node1;
    while (parent1 != null) {
      parents.add(parent1);
      parent1 = parent1.getParent();
    }
    Node parent2 = node2;
    while (parent2 != null) {
      if (parents.contains(parent2)) {
        return (Parent) parent2;
      }
      parent2 = parent2.getParent();
    }
    return null; // if no common parent node was found
  }

  /**
   * Method that increases the troop count of a country.
   *
   * @param country - country to increase the troop count
   * @param i       - number of troops to increase
   * @author floribau, vstoll
   */
  public void increaseTroopText(Country country, int i) {
    int newCount = Integer.valueOf(troopsInCountriesText[country.getButtonIndex()].getText()) + i;
    troopsInCountriesText[country.getButtonIndex()].setText(String.valueOf(newCount));
  }

  /**
   * Method to show the disconnect pane.
   *
   * @author vstoll
   */
  public void showDisconnectPane() {
    Platform.runLater(() -> {
      disconnectPane.setVisible(true);
      disconnectPane.toFront();
    });
  }

  /**
   * Method to grey out the player icons of the players that have been eliminated.
   *
   * @param playerNumber - the number of the player
   * @author vstoll
   */
  public void greyOutPlayerIcons(int playerNumber) {
    switch (playerNumber) {
      case 6 -> {
        playerSixIcon.setStyle("-fx-background-color: rgba(128, 128, 128, 0.5);");
        playerSixIconRect.setFill(Color.rgb(128, 128, 128, 0.5));
        playerSixIconCover.setVisible(true);
      }
      case 5 -> {
        playerFiveIcon.setStyle("-fx-background-color: rgba(128, 128, 128, 0.5);");
        playerFiveIconRect.setFill(Color.rgb(128, 128, 128, 0.5));
        playerFiveIconCover.setVisible(true);
      }
      case 4 -> {
        playerFourIcon.setStyle("-fx-background-color: rgba(128, 128, 128, 0.5);");
        playerFourIconRect.setFill(Color.rgb(128, 128, 128, 0.5));
        playerFourIconCover.setVisible(true);
      }
      case 3 -> {
        playerThreeIcon.setStyle("-fx-background-color: rgba(128, 128, 128, 0.5);");
        playerThreeIconRect.setFill(Color.rgb(128, 128, 128, 0.5));
        playerThreeIconCover.setVisible(true);
      }
      case 2 -> {
        playerTwoIcon.setStyle("-fx-background-color: rgba(128, 128, 128, 0.5);");
        playerTwoIconRect.setFill(Color.rgb(128, 128, 128, 0.5));
        playerTwoIconCover.setVisible(true);
      }
      case 1 -> {
        playerOneIcon.setStyle("-fx-background-color: rgba(128, 128, 128, 0.5);");
        playerOneIconRect.setFill(Color.rgb(128, 128, 128, 0.5));
        playerOneIconCover.setVisible(true);
      }
      default -> {
        //ignore
      }
    }
  }

  /**
   * Opens the justShowCardsPane and shows all cards of the player.
   *
   * @param event - ActionEvent of the button that was clicked
   * @author vstoll
   */
  @FXML
  public void openJustShowCardsPane(ActionEvent event) {
    justShowCardsPane.setVisible(true);
    justShowCardsPane.toFront();
    cardsShown = user.getGameState().getCardsByPlayerKey(user.getClient().getUserKey());
    justShowAllCards();
  }

  /**
   * Shows all cards in the justShowCardsPane.
   *
   * @author vstoll
   */
  public void justShowAllCards() {
    Platform.runLater(
        () -> {
          justShowCardsPaneFlowPane.getChildren().clear();
          Iterator<Card> iterator = cardsShown.iterator();
          while (iterator.hasNext()) {
            Card c = iterator.next();
            justShowCard(c);
          }
        });
  }

  /**
   * Shows a card in the justShowCardsPane.
   *
   * @param c - Card to be shown
   * @author vstoll
   */
  public void justShowCard(Card c) {

    ImageView iv = new ImageView(new Image(c.getCardImage()));
    iv.setFitHeight(190.0);
    iv.setFitWidth(109.0);
    iv.setPickOnBounds(true);
    iv.setPreserveRatio(true);
    justShowCardsPaneFlowPane.getChildren().add(iv);
  }

  /**
   * Closes the justShowCardsPane.
   *
   * @param event - ActionEvent of the button
   * @author vstoll
   */
  @FXML
  public void exitJustShowCardsPane(ActionEvent event) {
    justShowCardsPane.setVisible(false);
  }
}

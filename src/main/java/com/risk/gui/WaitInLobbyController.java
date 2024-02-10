package com.risk.gui;

import com.risk.main.User;
import com.risk.network.AiPlayerClient;
import com.risk.network.messages.PlayerReadyMessage;
import com.risk.network.messages.SetAiPlayerOnReadyMessage;
import com.risk.network.messages.StartGameMessage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * Controller Class for WaitInLobby. This class is responsible for the WaitInLobby Scene. It handles
 * the user input and sends the appropriate messages to the server while the user is waiting in the
 * lobby.
 *
 * @author vstoll
 */
public class WaitInLobbyController implements Initializable {

  private final User user = User.getUser();
  private final Integer[] numberPlayer = {2, 3, 4, 5, 6};
  private final String[] choosePlayerType = {"Cheater ai", "Normal ai", "Harmless ai"};
  public ToggleGroup menuButtons;
  @FXML
  public Button chatbutton;
  @FXML
  public GridPane playerPane;
  @FXML
  public Button startGameButton;
  @FXML
  public Button exitLobby;
  @FXML
  public Button backToLobbyButton;
  javafx.scene.media.MediaPlayer mediaPlayerLobby;
  javafx.scene.media.Media media;
  private Stage stage;
  private Scene scene;
  private Parent root;
  private ArrayList<String> usernamesInLobby;
  private boolean gameReady = false;
  @FXML
  private Button ReadyButton;
  @FXML
  private Text ipAdressText;
  @FXML
  private Text portText;
  @FXML
  private AnchorPane disconnectPane;
  @FXML
  private Button leaveButton;
  @FXML
  private Button addEasyAi;
  @FXML
  private Button addNormalAi;
  @FXML
  private Button addDifficultAi;
  @FXML
  private Button removeAiPlayer;


  /**
   * Method that switches to the GameScene.
   *
   * @param event ActionEvent that triggers the method
   * @throws IOException if the FXML file is not found
   * @author paukaise, floribau
   */
  public void switchToGameScene(ActionEvent event) throws IOException {
    stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    user.getServer().shutdownMulticast();
    user.getServer().broadcast(new StartGameMessage());
  }

  /**
   * method called for all users when host user presses startGame button: loads the GameScene and
   * calls Server.determineBeginner() for the host user.
   *
   * @author floribau
   */
  public void startGame() {
    Platform.runLater(
        () -> {
          Parent root = null;
          try {
            root = FXMLLoader.load(getClass().getResource("/GameScene.fxml"));
          } catch (IOException e) {
            //ignore
          }
          scene = new Scene(root);
          stage.setScene(scene);
          scene.getStylesheets().clear();
          scene
              .getStylesheets()
              .add(getClass().getResource("/applicationStyle.css").toExternalForm());
          stage.show();
        });
  }

  /**
   * Method that switches to the LobbyScene.
   *
   * @param event ActionEvent that triggers the method
   * @throws IOException if the FXML file is not found
   * @author floribau
   */
  @FXML
  protected void switchToLobbyScene(ActionEvent event) throws IOException {
    if (user.getClient() != null) {
      user.getClient().shutdown();
    }
    if (user.getServer() != null) {
      user.getServer().shutdown();
    }
    user.setServer(null);
    user.setClient(null);
    if (this.user.getServer() != null) {
      this.user.getServer().shutdown();
      this.user.setServer(null);
    }
    if (this.user.getClient() != null) {
      this.user.getClient().shutdown();
      this.user.setClient(null);
    }
    Parent root = FXMLLoader.load(getClass().getResource("/LobbyScene.fxml"));
    stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    scene = new Scene(root);
    stage.setScene(scene);
    scene.getStylesheets().clear();
    scene.getStylesheets().add(getClass().getResource(
        "/applicationStyle.css").toExternalForm());
    stage.show();

    stage.setOnCloseRequest(
        e -> {
          // todo @Florian @Lukas
        });
  }

  /**
   * Method that opens a pop-up window with the chat.
   *
   * @param event ActionEvent that triggers the method
   * @throws IOException if the FXML file is not found
   * @author vstoll
   */
  @FXML
  protected void chatPopUpWindow(ActionEvent event) throws IOException {
    if (event.getSource() == chatbutton) {

      Parent root = FXMLLoader.load(getClass().getResource("/ChatScene.fxml"));
      Scene chatScene = new Scene(root);
      Stage chatStage = new Stage();
      chatStage.initStyle(StageStyle.UNDECORATED);
      chatStage.setTitle("Chat");
      chatStage.setScene(chatScene);
      chatStage.initModality(Modality.APPLICATION_MODAL);
      // todo add position
      // chatStage.setX(640);
      // chatStage.setY(270);
      chatStage.show();
    }
  }

  /**
   * Method that updates the usernames in the lobby.
   *
   * @param usernamesInLobby ArrayList of Strings that contains the usernames of the players in the
   *                         lobby
   * @throws IOException if the FXML file is not found
   * @author vstoll, floribau
   */
  public void updateUsernamesInLobby(ArrayList<String> usernamesInLobby) {
    Platform.runLater(
        () -> {
          playerPane.getChildren().clear();
          playerPane.setHgap(10);
          playerPane.setVgap(10);
          playerPane.setAlignment(Pos.CENTER);

          int row = 0;
          int col = 0;
          for (String str : usernamesInLobby) {
            String style;
            if (str.contains("(Easy)")) {
              style =
                  "-fx-font-family: Berlin Sans FB; -fx-font-size: 28px; -fx-padding: 10px; "
                      + "-fx-background-color:  #FFFACD; -fx-background-radius: 12px;";
              str = str.replace("(Easy)", "");
            } else if (str.contains("(Medium)")) {
              style =
                  "-fx-font-family: Berlin Sans FB; -fx-font-size: 28px; -fx-padding: 10px; "
                      + "-fx-background-color: #ffd763;  -fx-background-radius: 12px;";
              str = str.replace("(Medium)", "");
            } else if (str.contains("(Hard)")) {
              style =
                  "-fx-font-family: Berlin Sans FB; -fx-font-size: 28px; -fx-padding: 10px; "
                      + "-fx-background-color:  #d6aa4c; -fx-background-radius: 12px;";
              str = str.replace("(Hard)", "");
            } else {
              style =
                  "-fx-font-family: Berlin Sans FB; -fx-font-size: 28px; -fx-padding: 10px; "
                      + "-fx-background-color: #f2f2f2; -fx-background-radius: 12px;";
            }
            Label label = new Label(str);
            label.setStyle(style);
            label.setAlignment(Pos.CENTER);
            label.setMaxWidth(Double.MAX_VALUE);
            GridPane.setConstraints(label, col, row);
            playerPane.getChildren().add(label);

            col++;
            if (col > 1) {
              col = 0;
              row++;
            }
          }
          enoughPlayersToStart();
        });
  }

  /**
   * Method that initializes the scene.
   *
   * @param url The location used to resolve relative paths for the root object, or
   * {@code null} if the location is not known.
   * @param resourceBundle The resources used to localize the root object, or {@code null} if the
   * root object was not localized.
   * @author vstoll, paukaise, floribau
   */
  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    ipAdressText.setText("IP: " + user.getClient().getGameServerIP());
    portText.setText("Port: " + user.getClient().getGameServerPort());
    startGameButton.setVisible(false);
    ArrayList<String> nameList = new ArrayList<>();
    nameList.add(user.getUsername());
    updateUsernamesInLobby(nameList);
    user.getClient().setWaitInLobbyController(this);
    enoughPlayersToStart();
    if (user.getServer() != null) {
      addEasyAi.setVisible(true);
      addNormalAi.setVisible(true);
      addDifficultAi.setVisible(true);
      removeAiPlayer.setVisible(true);
      ReadyButton.setVisible(false);
    } else {
      addEasyAi.setVisible(false);
      addNormalAi.setVisible(false);
      addDifficultAi.setVisible(false);
      removeAiPlayer.setVisible(false);
      ReadyButton.setVisible(true);
    }
  }

  /**
   * Method that creates a new AI player with the given difficulty and adds it to the lobby.
   *
   * @param event ActionEvent that triggers the method
   * @throws IOException if the FXML file is not found
   * @author vstoll
   */
  @FXML
  public void createAiPlayer(ActionEvent event) {

    if (event.getSource().equals(addEasyAi)) {
      addAiPlayer(
          new AiPlayerClient(
              user.getClient().getGameServerIP(),
              user.getClient().getGameServerPort(),
              User.generateKey(),
              1));
    } else if (event.getSource().equals(addNormalAi)) {
      addAiPlayer(
          new AiPlayerClient(
              user.getClient().getGameServerIP(),
              user.getClient().getGameServerPort(),
              User.generateKey(),
              2));
    } else if (event.getSource().equals(addDifficultAi)) {
      addAiPlayer(
          new AiPlayerClient(
              user.getClient().getGameServerIP(),
              user.getClient().getGameServerPort(),
              User.generateKey(),
              3));
    }
  }

  /**
   * Method that sends a message to the server that there are enough players to start the game.
   *
   * @param aiPlayerClient AiPlayerClient that is added to the lobby
   * @throws IOException if the FXML file is not found
   * @author floribau
   */
  public void addAiPlayer(AiPlayerClient aiPlayerClient) {
    aiPlayerClient.start();
    while (!aiPlayerClient.readyToSend()) {
      try {
        Thread.sleep(100);
      } catch (InterruptedException e) {
        //ignore
      }
    }
    // aiPlayerClient.sendMessage(new UserInfoMessage(aiPlayerClient.getUserKey(),
    // aiPlayerClient.getName()));
    aiPlayerClient.sendMessage(new SetAiPlayerOnReadyMessage());
    enoughPlayersToStart();
  }

  /**
   * Method that set the gameReady boolean to true.
   *
   * @param event ActionEvent that triggers the method
   * @throws IOException if the FXML file is not found
   * @author vstoll, floribau
   */
  public void readyClicked(ActionEvent event) {
    if (event.getSource().equals(ReadyButton)) {
      // todo @Florian
      stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
      user.getClient().sendMessage(new PlayerReadyMessage());
      ReadyButton.setDisable(true);
    }
  }

  /**
   * Method that checks if there are enough players to start the game and sets the visibility of the
   * start game button.
   *
   * @author vstoll, floribau
   */
  public void enoughPlayersToStart() {
    if (user.getServer() != null) {
      startGameButton.setVisible(
          user.getServer().getConnectionHandlers().size() >= 2 && this.gameReady);
      if (user.getServer().getConnectionHandlers().size() >= 6) {
        addEasyAi.setVisible(false);
        addNormalAi.setVisible(false);
        addDifficultAi.setVisible(false);
      } else {
        addEasyAi.setVisible(true);
        addNormalAi.setVisible(true);
        addDifficultAi.setVisible(true);
      }
    }
  }

  /**
   * Method that sets the gameReady boolean to the given boolean.
   *
   * @param gameReady boolean that indicates if the game is ready to start
   * @author floribau
   */
  public void setStartGameButton(boolean gameReady) {
    Platform.runLater(
        () -> {
          if (user.getServer() != null) {
            this.gameReady = gameReady;
            enoughPlayersToStart();
          }
        });
  }

  /**
   * Method that shows the disconnect pane.
   *
   * @author vstoll, floribau
   */
  public void showDisconnectPane() {
    disconnectPane.setVisible(true);
    disconnectPane.toFront();
  }

  /**
   * This method is called when the user clicks the "Leave" button.
   *
   * @param event ActionEvent that triggers the method
   * @throws IOException if the FXML file is not found
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
   * Method that removes the last AI player from the lobby.
   *
   * @param event ActionEvent that triggers the method
   */
  @FXML
  private void removeAiPlayer(ActionEvent event) {
    if (user.getServer() != null) {
      user.getServer().removeLastAiConnection();
    }
  }
}

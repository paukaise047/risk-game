package com.risk.gui;

import com.risk.main.User;
import com.risk.network.MultiCastReceiver;
import com.risk.network.Server;
import com.risk.network.ServerCredentials;
import com.risk.util.exceptions.UserAlreadyContainsClientException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * Class that represents the LobbyController. It gets the necessary input from the gui.
 * It is responsible for the lobby scene.
 * @author lkuech
 */
public class LobbyController implements Initializable {

  private final User user = User.getUser();
  private final Integer[] numberPlayer = {2, 3, 4, 5, 6};
  public ToggleGroup menuButtons;
  // Playing mode selection
  @FXML
  public ToggleButton playRankedButton;
  @FXML
  public ToggleButton joinLobbyButton;
  @FXML
  public ToggleButton searchLobbyButton;
  @FXML
  public ToggleButton createLobbyButton;
  // Search lobby
  @FXML
  public Text adressText;
  @FXML
  public TextField adressTextField;
  @FXML
  public Text portText;
  @FXML
  public TextField portTextField;
  @FXML
  public Button joinSearchedLobbyButton;
  // Select lobby
  @FXML
  public ScrollPane lobbiesScrollPane;
  @FXML
  public Button refreshAllLobbiesButton;
  @FXML
  public Button joinSelectedLobbyButton;
  // Queue ranked
  @FXML
  public Button joinRankedQueueButton;
  // create new Lobby
  @FXML
  public Button createNewLobbyButton;
  @FXML
  public ComboBox<String> addAiPlayerComboBox;
  ToggleGroup lobbyToggleGroup = new ToggleGroup();
  ServerCredentials selectedLobby;
  private Stage stage;
  private Scene scene;
  private Parent root;
  @FXML
  private ComboBox lobbySelection;

  /**
   * Method that switches to the GameScene.
   *
   * @param event ActionEvent that triggers the method
   * @throws IOException if the FXML file is not found
   * @author paukaise
   */
  public void switchToGameScene(ActionEvent event) throws IOException {
    Parent root = FXMLLoader.load(getClass().getResource("/GameScene.fxml"));
    stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    scene = new Scene(root);
    stage.setScene(scene);
    scene.getStylesheets().clear();
    scene.getStylesheets().add(getClass().getResource("/applicationStyle.css").toExternalForm());
    stage.show();

    stage.setOnCloseRequest(
        e -> {
          // todo @Florian @Lukas
        });
  }

  /**
   * Method that switches to the WaitInLobbyScene.
   *
   * @param event ActionEvent that triggers the method
   * @throws IOException if the FXML file is not found
   * @author vstoll
   */
  public void switchToWaitInLobbyScene(ActionEvent event) throws IOException {
    Parent root = FXMLLoader.load(getClass().getResource("/WaitInLobbyScene.fxml"));
    stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    scene = new Scene(root);
    stage.setScene(scene);
    scene.getStylesheets().clear();
    scene.getStylesheets().add(getClass().getResource("/applicationStyle.css").toExternalForm());
    stage.show();

    stage.setOnCloseRequest(
        e -> {
          // todo @Florian @Lukas
          if (user.getClient() != null) {
            user.getClient().shutdown();
            user.setClient(null);
          }
          if (user.getServer() != null) {
            user.getServer().shutdown();
            user.setServer(null);
          }
        });
  }

  /**
   * Method that switches to the MenuScene.
   *
   * @param event ActionEvent that triggers the method
   * @throws IOException if the FXML file is not found
   * @author lkuech
   */
  @FXML
  protected void switchToMenuScene(ActionEvent event) throws IOException {
    user.setServer(null);
    user.setClient(null);
    Parent root = FXMLLoader.load(getClass().getResource("/MenuScene.fxml"));
    stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    scene = new Scene(root);
    stage.setScene(scene);
    scene.getStylesheets().clear();
    scene.getStylesheets().add(getClass().getResource("/applicationStyle.css").toExternalForm());
    stage.show();

    if (user.getClient() != null) {
      this.user.getClient().shutdown();
      this.user.setClient(null);
    }
  }

  /**
   * initialize method for the controller.
   * @param url
   * The location used to resolve relative paths for the root object, or
   * {@code null} if the location is not known.
   *
   * @param resourceBundle
   * The resources used to localize the root object, or {@code null} if
   * the root object was not localized.
   * @author lkuech
   */
  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {

    if (user.getServer() != null) {
      user.getServer().shutdown();
      user.setServer(null);
    }
    if (user.getClient() != null) {
      user.getClient().shutdown();
      user.setClient(null);
    }
    String[] choosePlayerType = {"Kevin (Hard)", "Bob (Medium)", "Stuart (Easy)", "Random ai"};
    addAiPlayerComboBox = new ComboBox<String>();
    addAiPlayerComboBox.getItems().addAll(choosePlayerType);
  }

  /**
   * Method that switches to the view where you can join a lobby by entering the address and port.
   *
   * @param event ActionEvent that triggers the method
   * @author lkuech
   */
  public void joiningLobbySelected(ActionEvent event) {
    joinSearchedLobbyButton.setVisible(false);
    if (event.getSource().equals(joinLobbyButton)) {
      if (user.getServer() != null) {
        user.getServer().shutdown();
        user.setServer(null);
      }

      adressText.setVisible(false);
      adressTextField.setVisible(false);
      portText.setVisible(false);
      portTextField.setVisible(false);

      joinRankedQueueButton.setVisible(false);

      createNewLobbyButton.setVisible(false);
      // lobbyScrollPane.setVisible(false);
      // startGameButton.setVisible(false);
      // addAIButton.setVisible(false);

      lobbiesScrollPane.setVisible(true);
      refreshAllLobbiesButton.setVisible(true);
      joinSelectedLobbyButton.setVisible(true);
    }
  }

  /**
   * Method that handles the scene when the user selects to play ranked.
   * @param event ActionEvent that triggers the method
   * @author lkuech
   */
  public void playingRankedSelected(ActionEvent event) {

    if (event.getSource().equals(playRankedButton)) {
      if (user.getServer() != null) {
        user.getServer().shutdown();
        user.setServer(null);
      }

      adressText.setVisible(false);
      adressTextField.setVisible(false);
      portText.setVisible(false);
      portTextField.setVisible(false);
      joinSearchedLobbyButton.setVisible(false);
      joinSearchedLobbyButton.setDisable(false);

      joinRankedQueueButton.setVisible(true);
      joinRankedQueueButton.setDisable(false);

      createNewLobbyButton.setVisible(false);
      // lobbyScrollPane.setVisible(false);
      // startGameButton.setVisible(false);
      // addAIButton.setVisible(false);

      lobbiesScrollPane.setVisible(false);
      refreshAllLobbiesButton.setVisible(false);
      joinSelectedLobbyButton.setVisible(false);
    }
  }

  /**
   * Method that checks if searching lobby is empty and disables the join button if it is.
   * @author paukaise
   */
  public void checkingIfSearchingLobbyIsEmpty() {
    String addressText = adressTextField.getText();
    String portText = portTextField.getText();
    boolean enableButton = addressText.length() >= 7 && portText.length() == 5;
    joinSearchedLobbyButton.setDisable(
        !enableButton || addressText.isEmpty() || portText.isEmpty());
  }

  /**
   * Method that switches to the view where you can join a lobby by entering the address and port.
   * @param event ActionEvent that triggers the method
   * @author lkuech
   */
  public void searchingLobbySelected(ActionEvent event) {

    if (event.getSource().equals(searchLobbyButton)) {
      if (user.getServer() != null) {
        user.getServer().shutdown();
        user.setServer(null);
      }
      checkingIfSearchingLobbyIsEmpty();
      adressText.setVisible(true);
      adressTextField.setVisible(true);
      portText.setVisible(true);
      portTextField.setVisible(true);
      joinSearchedLobbyButton.setVisible(true);

      adressTextField
          .textProperty()
          .addListener(
              (observable, oldValue, newValue) -> {
                checkingIfSearchingLobbyIsEmpty();
              });

      portTextField
          .textProperty()
          .addListener(
              (observable, oldValue, newValue) -> {
                checkingIfSearchingLobbyIsEmpty();
              });

      joinRankedQueueButton.setVisible(false);

      createNewLobbyButton.setVisible(false);
      // lobbyScrollPane.setVisible(false);
      // startGameButton.setVisible(false);
      // addAIButton.setVisible(false);

      lobbiesScrollPane.setVisible(false);
      refreshAllLobbiesButton.setVisible(false);
      joinSelectedLobbyButton.setVisible(false);
    }
  }

  /**
   * Method that switches to the view where you can create a new lobby.
   * @param event ActionEvent that triggers the method
   * @author lkuech
   */
  public void creatingLobbySelected(ActionEvent event) {
    if (event.getSource().equals(createLobbyButton)) {
      if (user.getServer() != null) {
        user.getServer().shutdown();
        user.setServer(null);
      }

      adressText.setVisible(false);
      adressTextField.setVisible(false);
      portText.setVisible(false);
      portTextField.setVisible(false);
      joinSearchedLobbyButton.setVisible(false);

      joinRankedQueueButton.setVisible(false);

      createNewLobbyButton.setVisible(true);
      // lobbyScrollPane.setVisible(true);
      // startGameButton.setVisible(true);
      // addAIButton.setVisible(true);

      lobbiesScrollPane.setVisible(false);
      refreshAllLobbiesButton.setVisible(false);
      joinSelectedLobbyButton.setVisible(false);
    }
  }

  /**
   * Method that searches all lobby's that are currently available.
   * @param event ActionEvent that triggers the method
   * @author lkuech
   */
  public void searchAllLobbies(ActionEvent event) {
    try {

      MultiCastReceiver mr = new MultiCastReceiver();
      Thread thread = new Thread(mr);
      thread.start();
      Thread.sleep(1000);
      mr.setRunningFalse();
      thread.interrupt();

      VBox lobbyBox = new VBox();
      lobbyBox.setAlignment(Pos.CENTER);


      for (ServerCredentials sc : mr.getAllLobbies()) {
        String credentials = sc.getIp() + " : " + sc.getPort() + " : " + sc.getElo();
        ToggleButton tb = new ToggleButton(credentials);

        tb.setPrefWidth(180.0);
        tb.setPrefHeight(20.0);
        tb.setToggleGroup(lobbyToggleGroup);
        tb.setOnAction(
            new EventHandler<ActionEvent>() {
              @Override
              public void handle(ActionEvent event) {
                selectedLobby = ServerCredentials.credentialsFromString(tb.getText());
              }
            });
        lobbyBox.getChildren().add(tb);
      }

      lobbiesScrollPane.setContent(lobbyBox);

    } catch (IOException e) {
      //ignore
    } catch (InterruptedException e) {
      //ignore

    }
  }

  /**
   * Method that joins the selected lobby.
   * @param event ActionEvent
   * @throws IOException IOException
   * @author lkuech
   */
  public void joinLobby(ActionEvent event) throws IOException {
    if (selectedLobby != null) {
      if (event.getSource().equals(joinSelectedLobbyButton)) {
        try {
          user.addClient(selectedLobby.getIp(), selectedLobby.getPort());
          user.getClient().start();
          while (!user.getClient().isTriedToConnect()) {
            try {
              Thread.sleep(20);
            } catch (InterruptedException e) {
              // ignore
            }
          }
          if (user.getClient().isConnected()) {
            switchToWaitInLobbyScene(event);
          } else {
            user.removeClient();
            // TODO add pop up
          }
        } catch (Exception e) {
          //ignore
        }
      }
    }
  }

  /**
   * joins the player to the ranked queue and searches for a lobby with a similar elo.
   * @param event the event that triggered the method
   * @throws IOException if the fxml file can't be found
   * @author lkuech
   */
  public void joinRankedQueue(ActionEvent event) throws IOException {
    try {
      if (event.getSource().equals(joinRankedQueueButton)) {
        MultiCastReceiver mr = new MultiCastReceiver();
        Thread thread = new Thread(mr);
        thread.start();
        Thread.sleep(2000);
        mr.setRunningFalse();
        thread.interrupt();

        // int myElo = user.getElo();
        int myElo = 4;
        ServerCredentials bestLobby = null;
        for (ServerCredentials sc : mr.getAllLobbies()) {
          if (bestLobby != null) {
            if (Math.abs(bestLobby.getElo() - myElo) > Math.abs(sc.getElo()) - myElo) {
              bestLobby = sc;
            }
          } else {
            bestLobby = sc;
          }
        }

        user.addClient(bestLobby.getIp(), bestLobby.getPort());
        user.getClient().start();
        while (!user.getClient().isTriedToConnect()) {
          try {
            Thread.sleep(20);
          } catch (InterruptedException e) {
            // ignore
          }
        }
        if (user.getClient().isConnected()) {
          switchToWaitInLobbyScene(event);
        } else {
          user.removeClient();
          // TODO add pop up
        }
      }
    } catch (InterruptedException e) {
      // ignore
    } catch (IOException e) {
      // ignore
    } catch (UserAlreadyContainsClientException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * Method that creates a new lobby.
   * @param event the event that triggered the method
   * @throws IOException if the server could not be created
   * @authir floribau
   */
  public void createLobby(ActionEvent event) throws IOException {
    if (event.getSource().equals(createNewLobbyButton)) {
      user.setServer(new Server());
      Thread serverThread = new Thread(user.getServer());
      serverThread.setPriority(Thread.MIN_PRIORITY);
      serverThread.start();
      try {
        String serverip = user.getServer().getIP();
        int serverPort = user.getServer().getPort();
        user.addClient(serverip, serverPort);
        user.getClient().start();
      } catch (UserAlreadyContainsClientException e) {
        //ignore
      }
      switchToWaitInLobbyScene(event);
    }
  }

  /**
   * Method that joins the lobby that was searched for.
   * @param event the event that triggered the method
   * @author floribau
   */
  public void joinSearchedLobby(ActionEvent event) {
    if (event.getSource().equals(joinSearchedLobbyButton)) {
      try {
        user.addClient(adressTextField.getText(), Integer.valueOf(portTextField.getText()));
        user.getClient().start();
        while (!user.getClient().isTriedToConnect()) {
          try {
            Thread.sleep(20);
          } catch (InterruptedException e) {
            // ignore
          }
        }
        if (user.getClient().isConnected()) {
          switchToWaitInLobbyScene(event);
        } else {
          user.removeClient();
          // TODO add pop up
        }
      } catch (Exception e) {
        //ignore
      }
    }
  }

  private void popUpWindowAlert() {
    Alert alert = new Alert(Alert.AlertType.INFORMATION);
    alert.setTitle("Joining lobby failed.");
    alert.setHeaderText("Wrong IP-Address or Port.");
    alert.setContentText("Please try again.");
    DialogPane dialogPane = alert.getDialogPane();
    // Add custom style class
    dialogPane
        .getStylesheets()
        .add(getClass().getResource("/applicationStyle.css").toExternalForm());
    dialogPane.getStyleClass().add("popup-alert");
    // Set background color
    dialogPane.setStyle("-fx-background-color: #ffd763;");
    // Show the alert and wait for it to be closed
    alert
        .showAndWait()
        .ifPresent(
            response -> {
              // Set font family after dialog is shown
              Font font =
                  Font.loadFont(
                      getClass().getResource("/fonts/BerlinSansFB.ttf").toExternalForm(), 14);
              dialogPane
                  .lookupAll(".content.label")
                  .forEach(
                      node -> {
                        if (node instanceof Label) {
                          ((Label) node).setFont(font);
                        }
                      });
            });
  }
}

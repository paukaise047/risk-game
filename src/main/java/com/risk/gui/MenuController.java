package com.risk.gui;

import com.risk.main.User;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.stage.Stage;

/**
 * Class that represents the MenuController. It handles the menu of the game.
 * @author lkuech
 */
public class MenuController implements Initializable {

  private static boolean methodCalled = false;
  private final User user = User.getUser();
  private Stage stage;
  private Scene scene;
  private Parent root;
  private javafx.scene.media.MediaPlayer mediaPlayer;

  /**
   * Method that switches to the MenuScene.
   *
   * @param event ActionEvent that triggers the method
   * @throws IOException if the FXML file is not found
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
   * Method that switches to the OptionsScene.
   *
   * @param event ActionEvent that triggers the method
   * @throws IOException if the FXML file is not found
   * @author paukaise
   */
  @FXML
  protected void switchToOptionsScene(ActionEvent event) throws IOException {
    Parent root = FXMLLoader.load(getClass().getResource("/SettingsScene.fxml"));
    stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    scene = new Scene(root);
    stage.setScene(scene);
    scene.getStylesheets().clear();
    scene.getStylesheets().add(getClass().getResource("/applicationStyle.css").toExternalForm());
    stage.show();
  }

  /**
   * Method that switches to the PlayerStatisticsScene.
   *
   * @param event ActionEvent that triggers the method
   * @throws IOException if the FXML file is not found
   * @author paukaise
   */
  @FXML
  protected void switchToPlayerStatisticsScene(ActionEvent event) throws IOException {
    Parent root = FXMLLoader.load(getClass().getResource("/PlayerStatisticsScene.fxml"));
    stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    scene = new Scene(root);
    stage.setScene(scene);
    scene.getStylesheets().clear();
    scene.getStylesheets().add(getClass().getResource("/applicationStyle.css").toExternalForm());
    stage.show();
  }

  /**
   * Method that switches to the LoginScene.
   *
   * @param event ActionEvent that triggers the method
   * @throws IOException if the FXML file is not found
   * @author paukaise
   */
  @FXML
  protected void switchToLoginScene(ActionEvent event) throws IOException {
    this.user.setLoggedIn(false);
    Parent root = FXMLLoader.load(getClass().getResource("/LoginScene.fxml"));
    stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    scene = new Scene(root);
    stage.setScene(scene);
    scene.getStylesheets().clear();
    scene.getStylesheets().add(getClass().getResource("/applicationStyle.css").toExternalForm());
    stage.show();
  }

  /**
   * Method that switches to the TutorialScene.
   *
   * @param event ActionEvent that triggers the method
   * @throws IOException if the FXML file is not found
   * @author paukaise
   */
  @FXML
  protected void switchToTutorialScene(ActionEvent event) throws IOException {
    Parent root = FXMLLoader.load(getClass().getResource("/TutorialScene.fxml"));
    stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    scene = new Scene(root);
    stage.setScene(scene);
    scene.getStylesheets().clear();
    scene.getStylesheets().add(getClass().getResource("/applicationStyle.css").toExternalForm());
    stage.show();
  }

  /**
   * Method that switches to the LobbyScene.
   *
   * @param event ActionEvent that triggers the method
   * @throws IOException if the FXML file is not found
   * @author paukaise
   */
  @FXML
  protected void switchToLobbyScene(ActionEvent event) throws IOException {
    stopBackgroundMusic();
    Parent root = FXMLLoader.load(getClass().getResource("/LobbyScene.fxml"));
    stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    scene = new Scene(root);
    stage.setScene(scene);
    scene.getStylesheets().clear();
    scene.getStylesheets().add(getClass().getResource("/applicationStyle.css").toExternalForm());
    stage.show();
  }

  /**
   * Method that plays background music.
   *
   * @author paukaise
   */
  @FXML
  public void playBackgroundMusic() {
    if (mediaPlayer == null) {
      File file = new File("src/main/ressources/mp3/menuBackgroundMusic.mp3");
      javafx.scene.media.Media media = new javafx.scene.media.Media(file.toURI().toString());
      mediaPlayer = new javafx.scene.media.MediaPlayer(media);
      mediaPlayer.play();
    }
  }

  /**
   * Method that stops background music.
   *
   * @author paukaise
   */
  @FXML
  protected void stopBackgroundMusic() {
    if (mediaPlayer != null) {
      mediaPlayer.stop();
    }
  }

  /**
   * Method that exits the game and asks for confirmation.
   *
   * @param event ActionEvent that triggers the method
   * @author lkuech
   */
  @FXML
  public void exitGame(ActionEvent event) {
    Alert alert = new Alert(Alert.AlertType.INFORMATION);
    alert.setTitle("Quit Game");
    alert.setHeaderText("Are you sure you want to quit the game?");
    alert.setContentText("See you soon!");
    DialogPane dialogPane = alert.getDialogPane();
    // Add custom style class
    dialogPane
        .getStylesheets()
        .add(getClass().getResource("/applicationStyle.css").toExternalForm());
    dialogPane.getStyleClass().add("popup-alert");
    // Set background color
    dialogPane.setStyle("-fx-background-color: #ffd763;");

    Optional<ButtonType> result = alert.showAndWait();
    if (result.get() == ButtonType.OK) {
      System.exit(0);
    } else {
      // ... user chose CANCEL or closed the dialog
    }
  }

  /**
   * Method that makes sure that the background music is only played once.
   *
   * @author paukaise
   */
  public void playMusicOnlyOnce() {
    if (!methodCalled) {
      playBackgroundMusic();
      methodCalled = true;
    }
  }

  /**
   * Method that initializes the scene.
   *
   * @param url            The location used to resolve relative paths for the root object, or
   *                       {@code null} if the location is not known.
   * @param resourceBundle The resources used to localize the root object, or {@code null} if the
   *                       root object was not localized.
   * @author paukaise.
   */
  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
  }
}

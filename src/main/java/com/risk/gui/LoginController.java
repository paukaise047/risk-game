package com.risk.gui;

import com.risk.database.DatabaseHandler;
import com.risk.main.User;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * Class that represents the LoginController. It handles the login of the user.
 */
public class LoginController {

  User user = User.getUser();
  private Stage stage;
  private Scene scene;
  @FXML
  private TextField tfUserName;

  @FXML
  private TextField tfPasswordField;

  /**
   * Method that switches to the MenuScene.
   *
   * @param event ActionEvent that triggers the method
   * @throws IOException if the FXML file is not found
   * @author paukaise, lkuech
   */
  @FXML
  protected void switchToMenuScene(ActionEvent event) throws IOException {
    if (!logIn(tfUserName.getText(), tfPasswordField.getText())) {
      popUpWindowAlert();
      /*todo remove
      Alert alert = new Alert(AlertType.ERROR);
      alert.setTitle("Error");
      alert.setContentText(
          "Account doesn't exist or Username and Password don't match!");
      alert.showAndWait();
       */
    } else {
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
   * Method that logs in the user if the username and password are correct and exist in the database
   * and sets the user's ELO.
   *
   * @param username username of the user
   * @param password password of the user
   * @author floribau, lkuech
   */
  @FXML
  protected boolean logIn(String username, String password) {
    // TODO get user from database
    DatabaseHandler db = new DatabaseHandler();
    String dbPassword = db.getPasswordByUsername(username);
    if (dbPassword == null) {
      return false;
    } else if (dbPassword.equals(password)) {
      user.setLoggedIn(true);
      user.setUsername(username);
      user.setElo(db.getEloByUsername(username));
      return true;
    } else {
      return false;
    }
  }

  /**
   * Method that switches to the RegisterScene.
   *
   * @param event ActionEvent that triggers the method
   * @throws IOException if the FXML file is not found
   * @author vstoll
   */
  @FXML
  protected void switchToRegisterScene(ActionEvent event) throws IOException {
    Parent root = FXMLLoader.load(getClass().getResource("/RegisterScene.fxml"));
    stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    scene = new Scene(root);
    stage.setScene(scene);
    scene.getStylesheets().clear();
    scene.getStylesheets().add(getClass().getResource("/applicationStyle.css").toExternalForm());
    stage.show();
  }

  /**
   * Method that alerts the user that the login failed and that he should try again.
   *
   * @author vstoll
   */
  private void popUpWindowAlert() {
    Alert alert = new Alert(Alert.AlertType.INFORMATION);
    alert.setTitle("Login failed.");
    alert.setHeaderText("The username or password you entered is incorrect.");
    alert.setContentText("Please try again or register a new account.");
    DialogPane dialogPane = alert.getDialogPane();
    // Add custom style class
    dialogPane
        .getStylesheets()
        .add(getClass().getResource("/applicationStyle.css").toExternalForm());
    dialogPane.getStyleClass().add("popup-alert");
    // Set background color
    dialogPane.setStyle("-fx-background-color: #ffd763;");
    // Show the alert and wait for it to be closed
    alert.showAndWait();
  }
}

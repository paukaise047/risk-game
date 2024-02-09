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
 * Controller for the register screen.
 * @author vstoll, lkuech, hneumann
 */
public class RegisterController {

  private final User user = User.getUser();
  private Stage stage;
  private Scene scene;
  @FXML
  private TextField tfUserName;

  @FXML
  private TextField tfPasswordField;

  /**
   * This method changes the Scene to the LoginScene.
   *
   * @param event ActionEvent that triggers the method
   * @throws IOException if the FXML file is not found
   * @author vstoll
   */
  @FXML
  protected void switchtoLoginScene(ActionEvent event) throws IOException {
    Parent root = FXMLLoader.load(getClass().getResource("/LoginScene.fxml"));
    stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    scene = new Scene(root);
    stage.setScene(scene);
    scene.getStylesheets().clear();
    scene.getStylesheets().add(getClass().getResource("/applicationStyle.css").toExternalForm());
    stage.show();
  }

  /**
   * This method tries to create a user from the values enter into the text boxes. If not possible
   * it gives an explainotory error message.
   *
   * @param event ActionEvent that triggers the method
   * @throws IOException if the FXML file is not found
   * @author lkuech
   */
  @FXML
  protected void registerAndSwitchToLoginScene(ActionEvent event) throws IOException {

    String username = tfUserName.getText();
    String password = tfPasswordField.getText();

    if (!(username.equals("") || password.equals(""))) {
      DatabaseHandler db = new DatabaseHandler();
      if (db.writeUserToDb(username, password)) {

        popUpWindowAlertSuccessful(username);

        Parent root = FXMLLoader.load(getClass().getResource("/LoginScene.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        scene.getStylesheets().clear();
        scene
            .getStylesheets()
            .add(getClass().getResource("/applicationStyle.css").toExternalForm());
        stage.show();
      } else {
        popUpWindowAlertError(true); // username taken true
      }
    } else {
      popUpWindowAlertError(false); // other error
    }
  }

  /**
   * Pops up a window if the registration was succesful
   * @param username the username registered with
   * @author vstoll
   */
  private void popUpWindowAlertSuccessful(String username) {
    Alert alert = new Alert(Alert.AlertType.INFORMATION);
    alert.setTitle("User created");
    alert.setHeaderText(
        "Congratulations, your account " + username + " has been successfully created.");
    alert.setContentText("Thank you.");
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

  /**
   * Pops up a window with an alert error if the username is taken.
   *
   * @param usernameTaken boolean ig the name is already taken
   * @author vstoll
   */
  private void popUpWindowAlertError(boolean usernameTaken) {
    Alert alert = new Alert(Alert.AlertType.INFORMATION);
    alert.setTitle("Creating your account failed");
    if (usernameTaken) {
      alert.setHeaderText("The username you entered is already taken.");
    } else {
      alert.setHeaderText("Please enter valid credentials!");
    }
    alert.setContentText("We are sorry. Please try again.");
    DialogPane dialogPane = alert.getDialogPane();
    // Add custom style class
    dialogPane
        .getStylesheets()
        .add(getClass().getResource("/applicationStyle.css").toExternalForm());
    dialogPane.getStyleClass().add("popup-alert");
    // Set background color
    dialogPane.setStyle("-fx-background-color: #ffd763;");
    alert.showAndWait();
  }
}

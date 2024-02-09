package com.risk.gui;

import com.risk.database.DatabaseHandler;
import com.risk.main.User;
import java.io.IOException;
import java.util.Optional;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TextInputDialog;
import javafx.stage.Stage;

/**
 * Class that represents the OptionsController. It handles the options of the game.
 * @author lkuech
 */
public class OptionsController {

  private final User user = User.getUser();
  private Stage stage;
  private Scene scene;
  private Parent root;

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
   * This method opens a dialog window asking for the new password.
   *
   * @param e ActionEvent that triggers the method
   * @throws IOException if the FXML file is not found
   * @author lkuech
   */
  @FXML
  protected void changePasswordWindow(ActionEvent e) throws IOException {
    TextInputDialog textInputDialog = new TextInputDialog();
    textInputDialog.setTitle("Password");
    textInputDialog.setHeaderText("Change your password.");
    textInputDialog.setContentText("New password: ");

    DialogPane dialogPane = textInputDialog.getDialogPane();
    dialogPane
        .getStylesheets()
        .add(getClass().getResource("/applicationStyle.css").toExternalForm());
    dialogPane.getStyleClass().add("popup-alert");
    dialogPane.setStyle("-fx-background-color: #ffd763;");

    Optional<String> result = textInputDialog.showAndWait();
    if (result.isPresent() && !result.get().equals("")) {
      DatabaseHandler db = new DatabaseHandler();
      db.changePasswordInDb(user.getUsername(), result.get());
    } else {
      Alert alert =
          popUpWindowAlert("Error", "Please enter a valid password.", "Please try again.");
    }
  }

  /**
   * This method opens a dialog window asking for the new username.
   *
   * @param e ActionEvent that triggers the method
   * @throws IOException if the FXML file is not found
   * @author lkuech
   */
  @FXML
  protected void changeUsernameWindow(ActionEvent e) throws IOException {
    TextInputDialog textInputDialog = new TextInputDialog();
    textInputDialog.setTitle("Username");
    textInputDialog.setHeaderText("Change your username.");
    textInputDialog.setContentText("New username: ");

    DialogPane dialogPane = textInputDialog.getDialogPane();
    dialogPane
        .getStylesheets()
        .add(getClass().getResource("/applicationStyle.css").toExternalForm());
    dialogPane.getStyleClass().add("popup-alert");
    dialogPane.setStyle("-fx-background-color: #ffd763;");

    Optional<String> result = textInputDialog.showAndWait();
    if (result.isPresent() && !result.get().equals("")) {
      String newUsername = result.get();
      DatabaseHandler db = new DatabaseHandler();
      db.changeUsernamedInDb(user.getUsername(), newUsername);
      user.setUsername(newUsername);
    } else {
      Alert alert =
          popUpWindowAlert("Error", "Please enter a valid username.", "Please try again.");
    }
  }

  /**
   * This method deletes the account of the user.
   *
   * @param e ActionEvent that triggers the method
   * @throws IOException if the FXML file is not found
   * @author lkuech
   */
  @FXML
  protected void deleteAccount(ActionEvent e) throws IOException {
    Alert alert =
        popUpWindowAlert(
            "Delete Account",
            "Are you sure you don't want to delete this Account and all of your progress?",
            "If you delete your account, all of your progress will be lost.");

    Optional<ButtonType> result = alert.showAndWait();
    if (result.get() == ButtonType.OK) {
      DatabaseHandler db = new DatabaseHandler();
      db.deleteUser(user.getUsername());
      user.setLoggedIn(false);

      Parent root = FXMLLoader.load(getClass().getResource("/LoginScene.fxml"));
      stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
      scene = new Scene(root);
      stage.setScene(scene);
      scene.getStylesheets().clear();
      scene.getStylesheets().add(getClass().getResource("/applicationStyle.css").toExternalForm());
      stage.show();
    } else {
      // ... user chose CANCEL or closed the dialog
    }
  }

  /**
   * Method that opens a pop up window with the given title, header and content.
   * @param title   The title of the pop up window
   * @param header The header of the pop up window
   * @param content The content of the pop up window
   * @return The alert object
   * @author lkuech
   */
  private Alert popUpWindowAlert(String title, String header, String content) {
    Alert alert = new Alert(Alert.AlertType.INFORMATION);
    alert.setTitle(title);
    alert.setHeaderText(header);
    alert.setContentText(content);
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
    return alert;
  }
}

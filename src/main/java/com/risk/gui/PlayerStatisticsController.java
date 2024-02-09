package com.risk.gui;

import com.risk.database.DatabaseHandler;
import com.risk.database.PlayerScore;
import com.risk.main.User;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * Controller Class for Player Statics.
 *
 * @author lkuech
 */
public class PlayerStatisticsController implements Initializable {

  private final User user = User.getUser();
  @FXML
  public ScrollPane statisticScrollPane;
  private Stage stage;
  private Scene scene;
  @FXML
  private Text firstPlaceText;
  @FXML
  private Text secondPlaceText;
  @FXML
  private Text thirdPlaceText;
  @FXML
  private TableView<PlayerScore> tableView;

  @FXML
  private TableColumn<Integer, Integer> rankColumn;

  @FXML
  private TableColumn<PlayerScore, String> usernameColumn;

  @FXML
  private TableColumn<PlayerScore, Integer> eloColumn;

  @FXML
  private TableColumn<PlayerScore, Integer> gamesColumn;

  @FXML
  private TableColumn<PlayerScore, Double> scoreColumn;

  /**
   * Method that switches to the MenuScene.
   *
   * @param event ActionEvent that triggers the method
   * @throws IOException if the FXML file is not found
   * @author paukaise
   */
  @FXML
  public void switchToMenuScene(ActionEvent event) throws IOException {
    Parent root = FXMLLoader.load(getClass().getResource("/MenuScene.fxml"));
    stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    scene = new Scene(root);
    stage.setScene(scene);
    scene.getStylesheets().clear();
    scene.getStylesheets().add(getClass().getResource("/applicationStyle.css")
        .toExternalForm());
    stage.show();
  }

  /**
   * Method that displays the top three players in the database and shows them in the GUI.
   *
   * @author paukaise
   */
  @FXML
  public void displayTopThree() {
    DatabaseHandler db = new DatabaseHandler();
    String firstPlace = db.getNamesOfTopThree().get(0);
    String secondPlace = db.getNamesOfTopThree().get(1);
    String thirdPlace = db.getNamesOfTopThree().get(2);
    firstPlaceText.setText(firstPlace);
    secondPlaceText.setText(secondPlace);
    thirdPlaceText.setText(thirdPlace);
  }

  /**
   * Overrides the initialize method.
   * @param url            The location used to resolve relative paths for the root object, or null
   *                       if the location is not known.
   * @param resourceBundle The resources used to localize the root object, or null if the root
   *                       object was not localized.
   * @author paukaise. Initializes the controller class.
   */
  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    displayTopThree();
    fillTable();
  }

  /**
   * Fills the table with the player statistics incrementally and styles the table.
   *
   * @author vstoll
   */
  public void fillTable() {
    // set up the column factories
    rankColumn.setCellValueFactory(
        cellData ->
            new ReadOnlyObjectWrapper<>(tableView.getItems().indexOf(cellData.getValue()) + 1));
    usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
    eloColumn.setCellValueFactory(new PropertyValueFactory<>("elo"));
    gamesColumn.setCellValueFactory(new PropertyValueFactory<>("score"));
    scoreColumn.setCellValueFactory(new PropertyValueFactory<>("games"));

    // set the table styles
    rankColumn.setStyle("-fx-alignment: CENTER;");
    usernameColumn.setStyle("-fx-alignment: CENTER;");
    eloColumn.setStyle("-fx-alignment: CENTER;");
    gamesColumn.setStyle("-fx-alignment: CENTER;");
    scoreColumn.setStyle("-fx-alignment: CENTER;");
    tableView.setStyle(
        "-fx-background-color: transparent; -fx-font-family: 'Berlin Sans FB';"
            + "-fx-font-size: 18px;");
    tableView.getStyleClass().add("first-row");

    tableView.setRowFactory(
        tv ->
            new TableRow<>() {
              @Override
              protected void updateItem(PlayerScore item, boolean empty) {
                super.updateItem(item, empty);
                if (getIndex() % 2 == 0) {
                  setStyle("-fx-background-color: #FFFFFF;");
                } else {
                  setStyle("-fx-background-color: #FFE08C;");
                }
              }
            });

    // fill the table incrementally
    ObservableList<PlayerScore> players = FXCollections.observableArrayList();
    DatabaseHandler db = new DatabaseHandler();
    PlayerScore[] allPlayerScores = db.getPlayersOrderByScore();
    int batchSize = 50; // number of players to add in each batch
    for (int i = 0; i < allPlayerScores.length; i += batchSize) {
      int endIndex = Math.min(i + batchSize, allPlayerScores.length);
      for (int j = i; j < endIndex; j++) {
        PlayerScore ps = allPlayerScores[j];
        players.add(new PlayerScore(ps.getUsername(), ps.getElo(), ps.getGames(), ps.getScore()));
      }
      tableView.setItems(players);
      try {
        Thread.sleep(100); // pause to keep UI responsive
      } catch (InterruptedException e) {
        //ignore
      }
    }
  }
}

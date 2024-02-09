package com.risk.gui;

import com.risk.main.User;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * This class represents the application. It is the entry point of the application.
 * It loads the LoginScene.fxml file and sets the scene.
 * It also sets the title of the application and the icon.
 * It also loads the applicationStyle.css file.
 * @author lkuech
 */
public class Application extends javafx.application.Application {

  private final User user = User.getUser();

  public static void main(String[] args) {
    launch(args);
  }

  /**
   *
   * @param stage
   * @throws IOException
   */
  @Override
  public void start(Stage stage) throws IOException {
    try {
      Parent root = FXMLLoader.load(getClass().getResource("/LoginScene.fxml"));

      Scene scene = new Scene(root);
      scene.getStylesheets().clear();
      scene.getStylesheets().add(getClass().getResource("/applicationStyle.css").toExternalForm());

      // Image icon = new Image("icon.png");
      // stage.getIcons().add(icon);
      stage.setTitle("RiskyMonkeys");
      stage
          .getIcons()
          .add(new Image(Application.class.getResourceAsStream("/pictures/banana_monkey.png")));

      stage.setScene(scene);
      stage.setResizable(false);
      stage.show();

    } catch (IOException e) {
      //ignore
    }
  }
}

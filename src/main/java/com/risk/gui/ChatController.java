package com.risk.gui;

import com.risk.main.User;
import com.risk.network.ChatClient;
import com.risk.network.Client;
import com.risk.network.messages.ChatMessage;
import java.io.IOException;
import java.net.URL;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

public class ChatController implements Initializable {

  private final User user = User.getUser();
  private final Client client = user.getClient();
  private final ChatClient chatClient = client.getChatClient();
  private final ArrayList<Object> messages = new ArrayList<>();
  private Stage stage;
  private Scene scene;
  @FXML
  private Button refreshChatButton;
  @FXML
  private Button sendMessageButton;
  @FXML
  private TextField tfMessage;
  @FXML
  private VBox vboxMessages;
  @FXML
  private ScrollPane spMain;

  /**
   * Method adds a label to the chat.
   * @param messageFromServer The message that is added to the chat.
   * @param imAuthor Whether the user is the author of the message.
   * @param vbox The vbox that the message is added to.
   * @author lkuech
   */
  // todo delete
  public static void addLabel(String messageFromServer, boolean imAuthor, VBox vbox) {
    HBox hbox = new HBox();

    if (imAuthor) {
      hbox.setAlignment(Pos.CENTER_RIGHT);
      hbox.setPadding(new Insets(5, 5, 5, 10));

      Text text = new Text(messageFromServer);
      TextFlow textFlow = new TextFlow(text);
      textFlow.setStyle(
          "-fx-color: rgb(239, 242, 255)"
              + ";-fx-background-color: rgb(0,66, 169)"
              + ";-fx-background-radius: 20px");
      textFlow.setPadding(new Insets(5, 5, 5, 10));
      hbox.getChildren().add(textFlow);
    } else {
      hbox.setAlignment(Pos.CENTER_LEFT);
      hbox.setPadding(new Insets(5, 5, 5, 10));

      Text text = new Text(messageFromServer);
      TextFlow textFlow = new TextFlow(text);
      textFlow.setStyle(
          "-fx-color: rgb(239, 242, 255)"
              + ";-fx-background-color: rgb(255, 215, 99)"
              + ";-fx-background-radius: 20px");
      textFlow.setPadding(new Insets(5, 5, 5, 10));
      hbox.getChildren().add(textFlow);
    }

    Platform.runLater(
        new Runnable() {

          @Override
          public void run() {
            vbox.getChildren().add(hbox);
          }
        });
  }

  /**
   * This method is called when exiting the ChatScene. Switches back to the WaitInLobbyScene
   * or GameScene.
   * @param event The event that is triggered when the user clicks on the "Exit" button on
   *              ChatScene
   * @throws IOException If the FXML file for the WaitInLobbyScene or GameScene is not found.
   * @author paukaise
   */
  @FXML
  protected void exitWindow(ActionEvent event) throws IOException {
    stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    stage.close();
  }

  /**
   * This method is called when the ChatScene is loaded. It initializes the ChatScene. It
   * loads the chat history and displays it in the ChatScene. It also sets the listener for the chat
   * messages. When a new message is received, it is displayed in the ChatScene.
   *
   * @param url            The location used to resolve relative paths for the root object, or
   *                       {@code null} if the location is not known.
   * @param resourceBundle The resources used to localize the root object, or {@code null} if the
   *                       root object was not localized.
   * @author lgreiner, paukaise.
   */
  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    user.addChatController(this);
    for (String[] message : chatClient.getChatMessageHistory()) {
      if (message[1].equals(user.getUsername())) {
        HBox hbox = new HBox();
        hbox.setAlignment(Pos.CENTER_RIGHT);

        hbox.setPadding(new Insets(5, 5, 5, 10));

        Text text = new Text(message[1] + ": " + message[0]);
        TextFlow textFlow = new TextFlow(text);

        textFlow.setStyle(
            "-fx-color: rgb(239, 242, 255)"
                + ";-fx-background-color: rgb(0,66, 169)"
                + ";-fx-background-radius: 20px");

        textFlow.setPadding(new Insets(5, 10, 5, 10));
        text.setFill(Color.color(0.934, 0.945, 0.996));
        hbox.getChildren().add(textFlow);
        vboxMessages.getChildren().add(hbox);
      } else {
        HBox hbox = new HBox();
        hbox.setAlignment(Pos.CENTER_LEFT);

        hbox.setPadding(new Insets(5, 5, 5, 10));

        Text text = new Text(message[1] + ": " + message[0]);
        TextFlow textFlow = new TextFlow(text);

        textFlow.setStyle(
            "-fx-color: rgb(239, 242, 255)"
                + ";-fx-background-color: rgb(255, 215, 99)"
                + ";-fx-background-radius: 20px");

        textFlow.setPadding(new Insets(5, 10, 5, 10));
        text.setFill(Color.color(0.934, 0.945, 0.996));
        hbox.getChildren().add(textFlow);
        vboxMessages.getChildren().add(hbox);
      }
    }
    vboxMessages
        .heightProperty()
        .addListener(
            new ChangeListener<Number>() {
              @Override
              public void changed(
                  ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                spMain.setVvalue((Double) newValue);
              }
            });

    // TODO chatClient.receivedMessageNewVariant(vboxMessages);

    sendMessageButton.setOnAction(
        new EventHandler<ActionEvent>() {
          @Override
          public void handle(ActionEvent event) {

            String messageToSend = tfMessage.getText();
            if (!messageToSend.isEmpty()) {
              // chatClient.sendMessage(user.getUsername() + ": " + messageToSend);

              HBox hbox = new HBox();
              hbox.setAlignment(Pos.CENTER_RIGHT);

              hbox.setPadding(new Insets(5, 5, 5, 10));

              Text text = new Text(user.getUsername() + ": " + messageToSend);
              TextFlow textFlow = new TextFlow(text);

              textFlow.setStyle(
                  "-fx-color: rgb(239, 242, 255)"
                      + ";-fx-background-color: rgb(0,66, 169)"
                      + ";-fx-background-radius: 20px");

              textFlow.setPadding(new Insets(5, 10, 5, 10));
              text.setFill(Color.color(0.934, 0.945, 0.996));

              hbox.getChildren().add(textFlow);

              vboxMessages.getChildren().add(hbox);
              chatClient.sendMessage(
                  new ChatMessage(messageToSend, user.getUsername(), LocalTime.now()));
              tfMessage.clear();
            }
          }
        });
  }

  /**
   * This method is called when the ChatScene is loaded. It initializes the ChatScene. It loads the
   * chat history and displays it in the ChatScene. It also sets the listener for the chat messages.
   * When a new message is received, it is displayed in the ChatScene.
   *
   * @author lgreiner, lkuech
   */
  @FXML
  public void refreshChat() {
    Platform.runLater(
        () -> {
          vboxMessages.getChildren().clear();
          for (String[] message : chatClient.getChatMessageHistory()) {

            if (message[1].equals(user.getUsername())) {

              HBox hbox = new HBox();
              hbox.setAlignment(Pos.CENTER_RIGHT);

              hbox.setPadding(new Insets(5, 5, 5, 10));

              Text text = new Text(message[1] + ": " + message[0]);
              TextFlow textFlow = new TextFlow(text);

              textFlow.setStyle(
                  "-fx-color: rgb(255, 255, 255)"
                      + ";-fx-background-color: rgb(0,66, 169)"
                      + ";-fx-background-radius: 20px");

              textFlow.setPadding(new Insets(5, 10, 5, 10));
              text.setFill(Color.color(0.934, 0.945, 0.996));
              hbox.getChildren().add(textFlow);
              vboxMessages.getChildren().add(hbox);
            } else {

              HBox hbox = new HBox();
              hbox.setAlignment(Pos.CENTER_LEFT);

              hbox.setPadding(new Insets(5, 5, 5, 10));

              Text text = new Text(message[1] + ": " + message[0]);
              TextFlow textFlow = new TextFlow(text);

              textFlow.setStyle(
                  "-fx-color: rgb(0, 0, 0)"
                      + ";-fx-background-color: rgb(255, 215, 99)"
                      + ";-fx-background-radius: 20px");

              textFlow.setPadding(new Insets(5, 10, 5, 10));
              text.setFill(Color.color(0.934, 0.945, 0.996));

              hbox.getChildren().add(textFlow);
              vboxMessages.getChildren().add(hbox);
            }
          }

          vboxMessages
              .heightProperty()
              .addListener(
                  new ChangeListener<Number>() {
                    @Override
                    public void changed(
                        ObservableValue<? extends Number> observable,
                        Number oldValue,
                        Number newValue) {
                      spMain.setVvalue((Double) newValue);
                    }
                  });
          sendMessageButton.setOnAction(
              new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {

                  String messageToSend = tfMessage.getText();
                  if (!messageToSend.isEmpty()) {
                    HBox hbox = new HBox();
                    hbox.setAlignment(Pos.CENTER_RIGHT);

                    hbox.setPadding(new Insets(5, 5, 5, 10));

                    Text text = new Text(user.getUsername() + ": " + messageToSend);
                    TextFlow textFlow = new TextFlow(text);

                    textFlow.setStyle(
                        "-fx-color: rgb(239, 242, 255)"
                            + ";-fx-background-color: rgb(0,66, 169)"
                            + ";-fx-background-radius: 20px");

                    textFlow.setPadding(new Insets(5, 10, 5, 10));
                    text.setFill(Color.color(0.934, 0.945, 0.996));

                    hbox.getChildren().add(textFlow);

                    vboxMessages.getChildren().add(hbox);

                    // Message is being sent
                    chatClient.sendMessage(
                        new ChatMessage(messageToSend, user.getUsername(), LocalTime.now()));
                    // chatClient.sendMessage(user.getUsername() + ": " + messageToSend);
                    tfMessage.clear();
                  }
                }
              });
        });
  }
}

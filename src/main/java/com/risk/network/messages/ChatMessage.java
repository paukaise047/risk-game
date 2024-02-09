package com.risk.network.messages;

import com.risk.network.Message;
import com.risk.network.MessageType;
import java.time.LocalTime;

/**
 * Message class of the ChatMessage. This class is used to send chat messages from the client to the
 * server and from the server to the clients. It contains the message content, the author and the
 * timestamp.
 *
 * @author floribau
 */
public class ChatMessage extends Message {

  private final String messageContent;
  private final String author;
  private final LocalTime timestamp;

  /**
   * Constructor for the ChatMessage. It sets the message type to CHAT_MESSAGE. It also sets the
   * message content, the author and the timestamp.
   *
   * @param messageContent the message content
   * @param author         the author of the message
   * @param timestamp      the timestamp of the message
   * @author floribau
   */
  public ChatMessage(String messageContent, String author, LocalTime timestamp) {
    super(MessageType.CHAT_MESSAGE);
    this.messageContent = messageContent;
    this.author = author;
    this.timestamp = timestamp;
  }

  /**
   * method that returns the message content. It is used to display the message in the chat window.
   *
   * @return the message content
   * @author floribau
   */
  public String getMessageContent() {
    return this.messageContent;
  }

  /**
   * method that returns the author of the message.
   *
   * @return the author of the message
   * @author floribau
   */
  public String getAuthor() {
    return this.author;
  }

  /**
   * method that returns the timestamp of the message.
   *
   * @return the timestamp
   * @author floribau
   */
  public LocalTime getTimestamp() {
    return this.timestamp;
  }
}

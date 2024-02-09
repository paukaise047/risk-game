package com.risk.objects;

import com.risk.objects.enums.CardSymbol;
import java.io.Serializable;

/**
 * This class represents a card. A card has a country and a symbol.
 *
 * @author floribau.
 */
public class Card implements Serializable {

  private final Country country;
  private final CardSymbol symbol;
  private final String cardImage;
  private final int id;
  private String owner;

  /**
   * Constructor of the Card class without image url.
   *
   * @param country to be added to the card.
   * @param symbol  to be added to the card.
   * @author floribau.
   */
  public Card(int id, Country country, CardSymbol symbol) {
    this.id = id;
    this.country = country;
    this.symbol = symbol;
    this.cardImage = "";
  }

  /**
   * Constructor of the Card class with image url.
   *
   * @param country   country of the card.
   * @param symbol    symbol of the card.
   * @param cardImage image of the card.
   * @author vstoll.
   */
  public Card(int id, Country country, CardSymbol symbol, String cardImage) {
    this.id = id;
    this.country = country;
    this.symbol = symbol;
    this.cardImage = cardImage;
  }

  /**
   * Returns the card's country.
   *
   * @return the country of the card.
   * @author floribau.
   */
  public Country getCountry() {
    return country;
  }

  /**
   * Returns the card's symbol.
   *
   * @return the symbol of the card.
   * @author floribau.
   */
  public CardSymbol getSymbol() {
    return symbol;
  }

  /**
   * Returns the image representing this card.
   *
   * @return the image instance related to this card.
   * @author vstoll.
   */
  public String getCardImage() {
    return cardImage;
  }

  /**
   * Overrides the toString method inherited from class Object.
   *
   * @return the String representing this card.
   * @author floribau.
   */
  @Override
  public String toString() {
    if (symbol.equals(CardSymbol.Wildcard)) {
      return "Card{ID: " + id + ", Wildcard, Owner=" + this.owner + "}";
    }
    return "Card{ID: " + id + ",Country=" + this.country.getName() + ", Symbol=" + this.symbol
        + ", Owner=" + this.owner + "}";
  }

  public String getOwner() {
    return this.owner;
  }
  /**
   * set a new owner to the card.
   *
   * @param owner to be set to.
   * @author floribau.
   */
  public void setOwner(String owner) {
    this.owner = owner;
  }
  /**
   * Returns the id of the card.
   *
   * @return the id of the card
   * @author floribau.
   */
  public int getId() {
    return this.id;
  }
  /**
   * Overrides the equals method inherited from class Object.
   *
   * @param c the card to be compared to.
   * @return true if the cards are equal, false otherwise.
   * @author floribau.
   */
  public boolean equals(Card c) {
    if (c == null) {
      return false;
    }
    if (country == null || c.country == null) {
      return country == null && c.country == null;
    }
    return country.equals(c.getCountry());
  }

}

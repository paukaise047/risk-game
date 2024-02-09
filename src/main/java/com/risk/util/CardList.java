package com.risk.util;

import com.risk.objects.Card;
import com.risk.objects.Country;
import com.risk.objects.enums.CardSymbol;
import java.util.ArrayList;

public class CardList {

  private final ArrayList<Card> cards;

  public CardList(CountryList cl) {
    Country[] countries = cl.getAllCountriesAsArray();
    cards = new ArrayList<>();

    cards.add(
        new Card(0, countries[0], CardSymbol.Infantry, "/pictures/tradeCards/AlaskaCard.jpg"));
    cards.add(
        new Card(
            1,
            countries[1],
            CardSymbol.Cannon,
            "/pictures/tradeCards/North-West-TerritoryCard.jpg"));
    cards.add(
        new Card(2, countries[2], CardSymbol.Infantry, "/pictures/tradeCards/AlbertaCard.jpg"));
    cards.add(
        new Card(
            3,
            countries[3],
            CardSymbol.Infantry,
            "/pictures/tradeCards/Western-United-StatesCard.jpg"));
    cards.add(
        new Card(
            4, countries[4], CardSymbol.Cavalry, "/pictures/tradeCards/Central-AmericaCard.jpg"));
    cards.add(
        new Card(5, countries[5], CardSymbol.Cavalry, "/pictures/tradeCards/GreenlandCard.jpg"));
    cards.add(
        new Card(6, countries[6], CardSymbol.Cavalry, "/pictures/tradeCards/OntarioCard.jpg"));
    cards.add(new Card(7, countries[7], CardSymbol.Cannon, "/pictures/tradeCards/QuebecCard.jpg"));
    cards.add(
        new Card(
            8,
            countries[8],
            CardSymbol.Cannon,
            "/pictures/tradeCards/Eastern-United-StatesCard.jpg"));
    cards.add(
        new Card(9, countries[9], CardSymbol.Cannon, "/pictures/tradeCards/VenezuelaCard.jpg"));
    cards.add(new Card(10, countries[10], CardSymbol.Cavalry, "/pictures/tradeCards/PeruCard.jpg"));
    cards.add(
        new Card(11, countries[11], CardSymbol.Cannon, "/pictures/tradeCards/BrazilCard.jpg"));
    cards.add(
        new Card(12, countries[12], CardSymbol.Infantry, "/pictures/tradeCards/ArgentinaCard.jpg"));
    cards.add(
        new Card(13, countries[13], CardSymbol.Infantry, "/pictures/tradeCards/IcelandCard.jpg"));
    cards.add(
        new Card(14, countries[14], CardSymbol.Cannon, "/pictures/tradeCards/ScandinaviaCard.jpg"));
    cards.add(
        new Card(15, countries[15], CardSymbol.Cannon, "/pictures/tradeCards/UkraineCard.jpg"));
    cards.add(
        new Card(
            16, countries[16], CardSymbol.Cavalry, "/pictures/tradeCards/Great-BritainCard.jpg"));
    cards.add(
        new Card(
            17, countries[17], CardSymbol.Cavalry, "/pictures/tradeCards/Northern-EuropeCard.jpg"));
    cards.add(
        new Card(
            18, countries[18], CardSymbol.Infantry, "/pictures/tradeCards/Western-EuropeCard.jpg"));
    cards.add(
        new Card(
            19, countries[19], CardSymbol.Cavalry, "/pictures/tradeCards/Southern-EuropeCard.jpg"));
    cards.add(
        new Card(
            20, countries[20], CardSymbol.Infantry, "/pictures/tradeCards/North-AfricaCard.jpg"));
    cards.add(
        new Card(21, countries[21], CardSymbol.Infantry, "/pictures/tradeCards/EgyptCard.jpg"));
    cards.add(
        new Card(22, countries[22], CardSymbol.Cavalry, "/pictures/tradeCards/CongoCard.jpg"));
    cards.add(
        new Card(23, countries[23], CardSymbol.Cannon, "/pictures/tradeCards/East-AfricaCard.jpg"));
    cards.add(
        new Card(
            24, countries[24], CardSymbol.Cannon, "/pictures/tradeCards/South-AfricaCard.jpg"));
    cards.add(
        new Card(
            25, countries[25], CardSymbol.Infantry, "/pictures/tradeCards/MadagascarCard.jpg"));
    cards.add(
        new Card(26, countries[26], CardSymbol.Cannon, "/pictures/tradeCards/SiberiaCard.jpg"));
    cards.add(new Card(27, countries[27], CardSymbol.Cavalry, "/pictures/tradeCards/UralCard.jpg"));
    cards.add(
        new Card(28, countries[28], CardSymbol.Cavalry, "/pictures/tradeCards/ChinaCard.jpg"));
    cards.add(
        new Card(
            29, countries[29], CardSymbol.Infantry, "/pictures/tradeCards/AfghanistanCard.jpg"));
    cards.add(
        new Card(30, countries[30], CardSymbol.Cannon, "/pictures/tradeCards/Middle-EastCard.jpg"));
    cards.add(
        new Card(31, countries[31], CardSymbol.Infantry, "/pictures/tradeCards/IndiaCard.jpg"));
    cards.add(new Card(32, countries[32], CardSymbol.Cannon, "/pictures/tradeCards/SiamCard.jpg"));
    cards.add(
        new Card(33, countries[33], CardSymbol.Cavalry, "/pictures/tradeCards/YarkutskCard.jpg"));
    cards.add(
        new Card(34, countries[34], CardSymbol.Infantry, "/pictures/tradeCards/IrkutskCard.jpg"));
    cards.add(
        new Card(35, countries[35], CardSymbol.Cannon, "/pictures/tradeCards/MongoliaCard.jpg"));
    cards.add(
        new Card(36, countries[36], CardSymbol.Infantry, "/pictures/tradeCards/JapanCard.jpg"));
    cards.add(
        new Card(37, countries[37], CardSymbol.Cavalry, "/pictures/tradeCards/KamchatkaCard.jpg"));
    cards.add(
        new Card(38, countries[38], CardSymbol.Cavalry, "/pictures/tradeCards/IndonesiaCard.jpg"));
    cards.add(
        new Card(39, countries[39], CardSymbol.Cavalry, "/pictures/tradeCards/New-GuineaCard.jpg"));
    cards.add(
        new Card(
            40,
            countries[40],
            CardSymbol.Cannon,
            "/pictures/tradeCards/Western-AustraliaCard.jpg"));
    cards.add(
        new Card(
            41,
            countries[41],
            CardSymbol.Infantry,
            "/pictures/tradeCards/Eastern-AustraliaCard.jpg"));
    cards.add(new Card(42, null, CardSymbol.Wildcard, "/pictures/tradeCards/wildCard1.jpg"));
    cards.add(new Card(43, null, CardSymbol.Wildcard, "/pictures/tradeCards/wildCard2.jpg"));
  }

  /**
   * Returns all cards.
   *
   * @author floribau
   */
  public ArrayList<Card> getAllCards() {
    return this.cards;
  }
}

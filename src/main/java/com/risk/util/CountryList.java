package com.risk.util;

import com.risk.objects.Country;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

public class CountryList implements Serializable {

  private Country alaska;
  private Country northWestTerritory;
  private Country alberta;
  private Country westernUnitedStates;
  private Country centralAmerica;
  private Country greenland;
  private Country ontario;
  private Country quebec;
  private Country easternUnitedStates;
  private Country venezuela;
  private Country peru;
  private Country brazil;
  private Country argentina;
  private Country iceland;
  private Country scandinavia;
  private Country ukraine;
  private Country greatBritain;
  private Country northernEurope;
  private Country westernEurope;
  private Country southernEurope;
  private Country northAfrica;
  private Country egypt;
  private Country congo;
  private Country eastAfrica;
  private Country southAfrica;
  private Country madagascar;
  private Country siberia;
  private Country ural;
  private Country china;
  private Country afghanistan;
  private Country middleEast;
  private Country india;
  private Country siam;
  private Country yakutsk;
  private Country irkutsk;
  private Country mongolia;
  private Country japan;
  private Country kamchatka;
  private Country indonesia;
  private Country newGuinea;
  private Country westernAustralia;
  private Country easternAustralia;
  private Country[] allCountries;

  public CountryList() {
    alaska = new Country("Alaska", "NorthAmerica");
    northWestTerritory = new Country("NorthWestTerritory", "NorthAmerica");
    alberta = new Country("Alberta", "NorthAmerica");
    westernUnitedStates = new Country("WesternUnitedStates", "NorthAmerica");
    centralAmerica = new Country("CentralAmerica", "NorthAmerica");
    greenland = new Country("Greenland", "NorthAmerica");
    ontario = new Country("Ontario", "NorthAmerica");
    quebec = new Country("Quebec", "NorthAmerica");
    easternUnitedStates = new Country("EasternUnitedStates", "NorthAmerica");
    venezuela = new Country("Venezuela", "SouthAmerica");
    peru = new Country("Peru", "SouthAmerica");
    brazil = new Country("Brazil", "SouthAmerica");
    argentina = new Country("Argentina", "SouthAmerica");
    iceland = new Country("Iceland", "Europe");
    scandinavia = new Country("Scandinavia", "Europe");
    ukraine = new Country("Ukraine", "Europe");
    greatBritain = new Country("GreatBritain", "Europe");
    northernEurope = new Country("NorthernEurope", "Europe");
    westernEurope = new Country("WesternEurope", "Europe");
    southernEurope = new Country("SouthernEurope", "Europe");
    northAfrica = new Country("NorthAfrica", "Africa");
    egypt = new Country("Egypt", "Africa");
    congo = new Country("Congo", "Africa");
    eastAfrica = new Country("EastAfrica", "Africa");
    southAfrica = new Country("SouthAfrica", "Africa");
    madagascar = new Country("Madagascar", "Africa");
    siberia = new Country("Siberia", "Asia");
    ural = new Country("Ural", "Asia");
    china = new Country("China", "Asia");
    afghanistan = new Country("Afghanistan", "Asia");
    middleEast = new Country("MiddleEast", "Asia");
    india = new Country("India", "Asia");
    siam = new Country("Siam", "Asia");
    yakutsk = new Country("Yakutsk", "Asia");
    irkutsk = new Country("Irkutsk", "Asia");
    mongolia = new Country("Mongolia", "Asia");
    japan = new Country("Japan", "Asia");
    kamchatka = new Country("Kamchatka", "Asia");
    indonesia = new Country("Indonesia", "Australia");
    newGuinea = new Country("NewGuinea", "Australia");
    westernAustralia = new Country("WesternAustralia", "Australia");
    easternAustralia = new Country("EasternAustralia", "Australia");

    alaska.setNeighbours(new Country[]{northWestTerritory, alberta, kamchatka});
    northWestTerritory.setNeighbours(new Country[]{alaska, greenland, quebec, ontario, alberta});
    alberta.setNeighbours(new Country[]{alaska, northWestTerritory, ontario, westernUnitedStates});
    westernUnitedStates.setNeighbours(
        new Country[]{alberta, ontario, easternUnitedStates, centralAmerica});
    centralAmerica.setNeighbours(
        new Country[]{westernUnitedStates, easternUnitedStates, venezuela});
    greenland.setNeighbours(new Country[]{northWestTerritory, iceland, quebec, ontario});
    ontario.setNeighbours(
        new Country[]{
            alberta, northWestTerritory, quebec, easternUnitedStates, westernUnitedStates
        });
    quebec.setNeighbours(new Country[]{ontario, easternUnitedStates, greenland});
    easternUnitedStates.setNeighbours(
        new Country[]{westernUnitedStates, ontario, quebec, centralAmerica});
    venezuela.setNeighbours(new Country[]{centralAmerica, peru, brazil});
    peru.setNeighbours(new Country[]{venezuela, brazil, argentina});
    brazil.setNeighbours(new Country[]{peru, venezuela, northAfrica, argentina});
    argentina.setNeighbours(new Country[]{peru, brazil});
    iceland.setNeighbours(new Country[]{greenland, greatBritain});
    scandinavia.setNeighbours(new Country[]{northernEurope, ukraine});
    ukraine.setNeighbours(
        new Country[]{southernEurope, northernEurope, scandinavia, ural, afghanistan, middleEast});
    greatBritain.setNeighbours(new Country[]{iceland, northernEurope, westernEurope});
    northernEurope.setNeighbours(
        new Country[]{greatBritain, scandinavia, ukraine, southernEurope, westernEurope});
    westernEurope.setNeighbours(
        new Country[]{greatBritain, northernEurope, southernEurope, northAfrica});
    southernEurope.setNeighbours(
        new Country[]{westernEurope, northernEurope, ukraine, middleEast, egypt, northAfrica});
    northAfrica.setNeighbours(
        new Country[]{brazil, westernEurope, southernEurope, egypt, eastAfrica, congo});
    egypt.setNeighbours(new Country[]{northAfrica, southernEurope, middleEast, eastAfrica});
    congo.setNeighbours(new Country[]{northAfrica, eastAfrica, southAfrica});
    eastAfrica.setNeighbours(
        new Country[]{congo, northAfrica, egypt, middleEast, madagascar, southAfrica});
    southAfrica.setNeighbours(new Country[]{congo, eastAfrica, madagascar});
    madagascar.setNeighbours(new Country[]{southAfrica, eastAfrica});
    siberia.setNeighbours(new Country[]{ural, yakutsk, irkutsk, mongolia, china});
    ural.setNeighbours(new Country[]{ukraine, siberia, china, afghanistan});
    china.setNeighbours(new Country[]{afghanistan, ural, siberia, mongolia, siam, india});
    afghanistan.setNeighbours(new Country[]{india, middleEast, ukraine, ural, china});
    middleEast.setNeighbours(
        new Country[]{eastAfrica, egypt, southernEurope, ukraine, afghanistan, india});
    india.setNeighbours(new Country[]{middleEast, afghanistan, china, siam});
    siam.setNeighbours(new Country[]{india, china, indonesia});
    yakutsk.setNeighbours(new Country[]{siberia, kamchatka, irkutsk});
    irkutsk.setNeighbours(new Country[]{siberia, yakutsk, kamchatka, mongolia});
    mongolia.setNeighbours(new Country[]{china, siberia, irkutsk, kamchatka, japan});
    japan.setNeighbours(new Country[]{mongolia, kamchatka});
    kamchatka.setNeighbours(new Country[]{alaska, japan, mongolia, irkutsk, yakutsk});
    indonesia.setNeighbours(new Country[]{siam, newGuinea, westernAustralia});
    newGuinea.setNeighbours(new Country[]{indonesia, westernAustralia, easternAustralia});
    westernAustralia.setNeighbours(new Country[]{indonesia, newGuinea, easternAustralia});
    easternAustralia.setNeighbours(new Country[]{westernAustralia, newGuinea});

    allCountries =
        new Country[]{
            alaska,
            northWestTerritory,
            alberta,
            westernUnitedStates,
            centralAmerica,
            greenland,
            ontario,
            quebec,
            easternUnitedStates,
            venezuela,
            peru,
            brazil,
            argentina,
            iceland,
            scandinavia,
            ukraine,
            greatBritain,
            northernEurope,
            westernEurope,
            southernEurope,
            northAfrica,
            egypt,
            congo,
            eastAfrica,
            southAfrica,
            madagascar,
            siberia,
            ural,
            china,
            afghanistan,
            middleEast,
            india,
            siam,
            yakutsk,
            irkutsk,
            mongolia,
            japan,
            kamchatka,
            indonesia,
            newGuinea,
            westernAustralia,
            easternAustralia
        };
  }

  /**
   * Gets country by name.
   *
   * @param countryName - the name of the country to get
   * @return Country object or null if not found by name
   * @author lkuech
   */
  public Country getCountryByName(String countryName) {
    for (Country country : allCountries) {
      if (country.getName().equals(countryName)) {
        return country;
      }
    }
    return null;
  }

  /**
   * Returns ArrayList of all countries.
   *
   * @return ArrayList of type Country
   * @author floribau
   */
  public ArrayList<Country> getAllCountries() {
    ArrayList<Country> countries = new ArrayList<>();
    Collections.addAll(countries, this.allCountries);
    return countries;
  }

  /**
   * Returns an array of all countries.
   *
   * @return Country[] array
   * @author floribau
   */
  public Country[] getAllCountriesAsArray() {
    return this.allCountries;
  }


}

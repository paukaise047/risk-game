package com.risk.util;

import com.risk.objects.Continent;
import java.util.ArrayList;

public class ContinentList {

  private ArrayList<Continent> allContinents;
  private Continent europa;
  private Continent asia;
  private Continent africa;
  private Continent australia;
  private Continent northAmerica;
  private Continent southAmerica;

  public ContinentList() {
    europa = new Continent("Europa", false, null);
    asia = new Continent("Asia", false, null);
    africa = new Continent("Africa", false, null);
    australia = new Continent("Australia", false, null);
    northAmerica = new Continent("NorthAmerica", false, null);
    southAmerica = new Continent("SouthAmerica", false, null);

    allContinents = new ArrayList<Continent>();
    allContinents.add(europa);
    allContinents.add(asia);
    allContinents.add(africa);
    allContinents.add(australia);
    allContinents.add(northAmerica);
    allContinents.add(southAmerica);
  }

  /**
   * Returns all continents.
   *
   * @author lkuech
   */
  public ArrayList<Continent> getAllContinents() {
    return allContinents;
  }
}

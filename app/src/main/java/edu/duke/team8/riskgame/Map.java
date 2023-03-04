package edu.duke.team8.riskgame;

import java.util.Iterator;
public interface Map {
  public void addTerritory(Territory t);
  public boolean containsTerritory(Territory t);
  public Iterator<Territory> getTerritoryIterator();

}

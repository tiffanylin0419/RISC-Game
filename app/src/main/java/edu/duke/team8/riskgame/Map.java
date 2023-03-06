package edu.duke.team8.riskgame;

import java.util.Iterator;
public interface Map {
  public void addTerritory(Territory t);

  public boolean containsTerritory(Territory t);
  public Iterator<Territory> getTerritoryIterator();

  public void addAdjacency(Territory t1, Territory t2);

  public Iterator<Player> getPlayerIterator();

  public void addPlayer(Player p);
}

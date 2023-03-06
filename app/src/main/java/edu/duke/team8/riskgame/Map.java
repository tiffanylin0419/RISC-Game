package edu.duke.team8.riskgame;

import java.util.Iterator;
public interface Map {
  //add t to territories
  public void addTerritory(Territory t);

  //return True if t is in territories
  public boolean containsTerritory(Territory t);

  //get territories's iterator 
  public Iterator<Territory> getTerritoryIterator();

  //add adjacent connection between t1 and t2
  //will update the adjacent_territory field in t1 and t2
  public void addAdjacency(Territory t1, Territory t2);

  //get player's iterator
  public Iterator<Player> getPlayerIterator();

  //add p to players
  public void addPlayer(Player p);
}

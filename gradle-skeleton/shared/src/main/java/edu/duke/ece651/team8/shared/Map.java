package edu.duke.ece651.team8.shared;

import java.util.ArrayList;
import java.util.Iterator;
public interface Map {

  /**
   * add t to territories
   * @param t
   */
  public void addTerritory(Territory t);

  /**
   *
   * @param t
   * @return True if t is in territories, False otherwise
   */
  public boolean containsTerritory(Territory t);

  /**
   * @return territories's iterator
   */
  public Iterator<Territory> getTerritoryIterator();

  /**
   * @return territories
   */
  public ArrayList<Territory> getTerritories();

  /**
   * add adjacent connection between t1 and t2
   * will update the adjacent_territory field in t1 and t2
   * @param t1
   * @param t2
   */
  public void addAdjacency(Territory t1, Territory t2);

//  /**
//   * @return player's iterator
//   */
//  public Iterator<Player> getPlayerIterator();

//  /**
//   * add p to players
//   * @param p
//   */
//  public void addPlayer(Player p);
}

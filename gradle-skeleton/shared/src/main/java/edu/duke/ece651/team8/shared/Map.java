package edu.duke.ece651.team8.shared;

import java.util.ArrayList;
import java.util.Iterator;
public interface Map {

  /**
   * addAmount t to territories
   * @param t territory to be added
   */
   void addTerritory(Territory t);

  /**
   * check if the map contains a specific territory
   * @param t territory to be checked
   * @return True if t is in territories, False otherwise
   */
  boolean containsTerritory(Territory t);

  /**
   * @return territories' iterator
   */
  Iterator<Territory> getTerritoryIterator();

  /**
   * @return territories
   */
  ArrayList<Territory> getTerritories();

  /**
   * addAmount adjacent connection between t1 and t2
   * will update the adjacent_territory field in t1 and t2
   * @param t1 territory to be connected
   * @param t2 territory to be connected
   */
  void addAdjacency(Territory t1, Territory t2);

  /**
   * @return checker
   */
  MovableActionRuleChecker getMovableRuleChecker();

  ResearchActionRuleChecker getResearchRuleChecker();

  UpgradeActionRuleChecker getUpgradeRuleChecker();

  CloakActionRuleChecker getCloakActionRuleChecker();
  /**
   * Do combats in all territory
   * @return the outcome information for all the combats
   */
  void doCombats();

  void addPlayers(ArrayList<Player> players);

  ArrayList<Player> getPlayers();
  public String getOutcome();

  public String getWinner();

  public void setDistance(Territory t1, Territory t2, int distance);
}

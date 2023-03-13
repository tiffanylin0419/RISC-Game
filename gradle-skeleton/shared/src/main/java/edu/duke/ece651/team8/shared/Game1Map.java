package edu.duke.ece651.team8.shared;

import java.util.ArrayList;
import java.util.Iterator;

public class Game1Map implements Map {
  // field
  ArrayList<Territory> territories;
  ArrayList<ArrayList<Territory>> territoryGroups;

//  ArrayList<Player> players;

  // constructor
  public Game1Map(){
    this.territories = new ArrayList<>();
    this.territoryGroups = new ArrayList<>();
//    this.players = new ArrayList<>();
  }

  public Game1Map(ArrayList<ArrayList<Territory>> t) {
    this.territories = new ArrayList<>();
    this.territoryGroups = t;
    updateTerritory();
  }

  /**
   * add Territory from territoryGroups to territories
   */
  private void updateTerritory() {
    for (ArrayList<Territory> list : territoryGroups) {
      for (Territory t : list) {
        territories.add(t);
      }
    }
  }

  @Override
  public void addTerritory(Territory t){
    territories.add(t);
  }
  
  @Override
  public boolean containsTerritory(Territory t){
    for(Territory territory: territories){
      if(territory.equals(t)){
        return true;}
    }
    return false;
  }

  @Override
  public Iterator<Territory> getTerritoryIterator() {
    return territories.iterator();
  }

  @Override
  public void addAdjacency(Territory t1, Territory t2){
    t1.addAdjacent(t2);
    t2.addAdjacent(t1);
  }

  public ArrayList<ArrayList<Territory>> getTerritoryGroups() {
    return this.territoryGroups;
  }

//  public Iterator<Player> getPlayerIterator() {
//    return this.players.iterator();
//  }

//  public void addPlayer(Player p) {
//    if (!players.contains(p)) {
//      players.add(p);
//    }
//  }
}


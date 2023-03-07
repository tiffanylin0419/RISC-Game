package edu.duke.team8.riskgame;

import java.util.ArrayList;
import java.util.Iterator;

public class Game1Map implements Map {
  // field
  ArrayList<Territory> territories;

  ArrayList<Player> players;

  // constructor
  public Game1Map(){
    this.territories = new ArrayList<>();
    this.players = new ArrayList<>();
  }
  public  Game1Map(ArrayList<Territory> territories,ArrayList<Player> players){
    this.territories=territories;
    this.players=players;
  }
  
  //
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

  public Iterator<Player> getPlayerIterator() {
    return this.players.iterator();
  }

  public void addPlayer(Player p) {
    if (!players.contains(p)) {
      players.add(p);
    }
  }
}


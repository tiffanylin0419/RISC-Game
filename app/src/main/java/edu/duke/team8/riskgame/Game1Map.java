package edu.duke.team8.riskgame;

import java.util.ArrayList;

public class Game1Map implements Map {
  // field
  ArrayList<Territory> territories;
  // constructor
  public Game1Map(){
    this.territories = new ArrayList<Territory>();
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
}

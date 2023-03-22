package edu.duke.ece651.team8.shared;

import java.util.ArrayList;
import java.util.Iterator;

public class Game1Map implements Map {
  // field
  private ArrayList<Territory> territories;

  private ActionRuleChecker checker;

  //constructor
  public Game1Map() {
    this.territories = new ArrayList<>();
    this.checker=new TerritoryRuleChecker(new OwnershipRuleChecker(new NumberRuleChecker(new PathRuleChecker(null)))) ;
  }

  //constructor
  public Game1Map(ArrayList<Territory> territories) {

    this.territories = territories;
    this.checker=new TerritoryRuleChecker(new OwnershipRuleChecker(new NumberRuleChecker(new PathRuleChecker(null)))) ;

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
  public ArrayList<Territory> getTerritories(){
    return territories;
  }
  @Override
  public void addAdjacency(Territory t1, Territory t2){
    t1.addAdjacent(t2);
    t2.addAdjacent(t1);
  }

  @Override
  public ActionRuleChecker getChecker(){return checker;}
  public String doCombats() {
    StringBuilder outcomes = new StringBuilder();
    for(Territory t : territories) {
      if(t.getUnitsSize() > 1) {
        t.attack();
        outcomes.append("Player " + t.getOwner().getColor() + " wins combat in " + t.getName() + "\n");
      }
      t.addOne();
    }
    return outcomes.toString();
  }
}


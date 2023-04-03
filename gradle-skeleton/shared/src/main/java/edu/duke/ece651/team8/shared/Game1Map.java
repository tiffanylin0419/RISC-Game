package edu.duke.ece651.team8.shared;

import java.util.ArrayList;
import java.util.Iterator;

public class Game1Map implements Map {
  // field
  private final ArrayList<Territory> territories;

  private ArrayList<Player> players;

  private final ActionRuleChecker checker;
  private String combatOutcome;
  private int combatVisitor;

  //constructor
  public Game1Map() {
    this.territories = new ArrayList<>();
    this.players = new ArrayList<>();
    this.checker=new TerritoryRuleChecker(new OwnershipRuleChecker(new NumberRuleChecker(new PathRuleChecker(null)))) ;
    this.combatVisitor = 0;
  }

  //constructor
  public Game1Map(ArrayList<Territory> territories) {
    this.players = new ArrayList<>();
    this.territories = territories;
    this.checker=new TerritoryRuleChecker(new OwnershipRuleChecker(new NumberRuleChecker(new PathRuleChecker(null)))) ;
    this.combatVisitor = 0;
  }

  @Override
  public void addPlayers(ArrayList<Player> p) {
    this.players = p;
  }

  @Override
  public ArrayList<Player> getPlayers() {
    return players;
  }

  @Override
  public void addTerritory(Territory t){
    territories.add(t);
  }

  /**
   * check if there is the territory
   * @param t territory to be checked
   * @return
   */
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

  /**
   * if the size of unit in one territory is greater than 1, than let them do combats
   * @return
   */
  @Override
  public synchronized void doCombats() {
    combatVisitor++;
    if(getOnlinePlayerSize() != 0 && combatVisitor % getOnlinePlayerSize() != 1) return;  //??
    combatVisitor = 0;
    StringBuilder outcomes = new StringBuilder();
    for(Territory t : territories) {
      if(t.getUnitsSize() > 1) {
        t.attack();
        outcomes.append("Player ").append(t.getOwner().getColor()).append(" wins combat in ").append(t.getName()).append("\n");
      }
      t.addOne();
    }
    combatOutcome = outcomes.toString();
  }

  @Override
  public String getOutcome() {return combatOutcome;}

  @Override
  public int getOnlinePlayerSize() {
    int num = 0;
    for(Player p : players) {
      if(p.isConnected()) {
        num += 1;
      }
    }
    return num;
  }
}


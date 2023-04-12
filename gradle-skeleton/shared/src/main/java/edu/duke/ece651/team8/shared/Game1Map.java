package edu.duke.ece651.team8.shared;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class Game1Map implements Map {
  // field
  private final ArrayList<Territory> territories;

  private ArrayList<Player> players;

  private final MovableActionRuleChecker movableChecker;
  private final ResearchActionRuleChecker researchActionRuleChecker;

//  private final UpgradeActionRuleChecker upgradeActionRuleChecker;
  private String combatOutcome;
  private boolean doneCombat;

  //constructor
  public Game1Map() {
    this.territories = new ArrayList<>();
    this.players = new ArrayList<>();
    this.movableChecker =new TerritoryRuleChecker(new OwnershipRuleChecker(new NumberRuleChecker(new PathRuleChecker(null)))) ;
    this.researchActionRuleChecker = new LevelRuleChecker(new TechResourceRuleChecker(new ResearchTurnLimitChecker(null)),6);
//    this.upgradeActionRuleChecker = new LevelRuleChecker(new TechResourceRuleChecker(), 6);
    this.combatOutcome = "";
    this.doneCombat = false;
  }

  //constructor
  public Game1Map(ArrayList<Territory> territories) {
    this.players = new ArrayList<>();
    this.territories = territories;
    this.movableChecker =new TerritoryRuleChecker(new OwnershipRuleChecker(new NumberRuleChecker(new PathRuleChecker(null)))) ;
    this.researchActionRuleChecker = new LevelRuleChecker(new TechResourceRuleChecker(new ResearchTurnLimitChecker(null)),6);
    this.combatOutcome = "";
    this.doneCombat = false;
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
  public MovableActionRuleChecker getMovableChecker(){return movableChecker;}

  @Override
  public ResearchActionRuleChecker getResearchRuleChecker() {
    return researchActionRuleChecker;
  }

//  @Override
//  public UpgradeActionRuleChecker getUpgradeRuleChecker() {
//    return upgradeActionRuleChecker;
//  }

  /**
   * if the size of unit in one territory is greater than 1, than let them do combats
   * @return
   */
  @Override
  public synchronized void doCombats() {
    if(doneCombat) return;
    doneCombat = true;
    StringBuilder outcomes = new StringBuilder();
    boolean isEffective = false;
    for(Territory t : territories) {
      if(t.getUnitsSize() > 1) {
        isEffective = true;
        t.attack();
        outcomes.append("Player ").append(t.getOwner().getColor()).append(" wins combat in ").append(t.getName()).append("\n");
      }
      t.addOne();
    }
    if(isEffective) {
      combatOutcome = outcomes.toString();
    }
  }

  @Override
  public String getOutcome() {

    doneCombat = false;
    return combatOutcome;
  }

  @Override
  public String getWinner(){
    for(Player p: players){
      if(p.getTerritories().size()==territories.size()){
        return p.getColor();
      }
    }
    return "";
  }

  @Override
  public void setDistance(Territory t1, Territory t2, int distance) {
    t1.setDistance(t2, distance);
    t2.setDistance(t1, distance);
  }

//  public int[] getResearchCostsList(){
//    int[] researchCosts = {0, 20, 40, 80, 160, 320};
//    return researchCosts;
//  }

}


package edu.duke.ece651.team8.shared;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.HashSet;
import java.util.Set;

public class Player {
  protected ArrayList<Territory> territories;

  public Map<String,String> seen_territories;
  private String color;
  private int unitMax;
  private boolean isConnected;

  private FoodResource food;
  private TechResource tech;

  public int currentTurnTechConsumingAmount;
  public int currentTurnFoodConsumingAmount;
  private int level;
  public Boolean hasResearchedThisTurn;
  //constructor
  public Player(String color){
    this();
    this.color = color;
  }
  public Player(){
    this.territories=new ArrayList<>();
    this.seen_territories=new HashMap<>();
    this.color="";
    this.unitMax=0;
    this.isConnected = true;
    this.food = new FoodResource(20);
    this.tech = new TechResource(20);
    this.level = 1;
    this.currentTurnFoodConsumingAmount = 0;
    this.currentTurnTechConsumingAmount = 0;
    this.hasResearchedThisTurn = false;
  }
  public void disconnect() {
    isConnected = false;
  }
  public void connect() {
    isConnected = true;
  }
  public boolean isConnected() {
    return isConnected;
  }
  /**
   * @return color
   */
  public String getColor(){
    return color;
  }

  /**
   * Set color of player
   * @param assignedColor is the color assigned from the server
   */
  public void setColor(String assignedColor){
    color = assignedColor;
  }

  /**
   * set unitMax of player
   * @param unitMax the total initial units a player has
   */
  public void setUnitMax(int unitMax){
    this.unitMax=unitMax;
  }

  /**
   * @return unitMax
   */
  public int getUnitMax(){
    return unitMax;
  }

  /**
   * @return territories
   */
  public ArrayList<Territory> getTerritories(){return territories;}

  /**
   * addAmount t to territories
   * @param t territory
   */
  public void addTerritory(Territory t){
    territories.add(t);
  }

  /**
   * @param t territory
   * @return True if territories contains t, False otherwise
   */
  public boolean containsTerritory(Territory t){
    for(Territory territory: territories){
      if(territory.equals(t)){
        return true;
      }
    }
    return false;
  }

  /**
   * remove t from territories
   * @param t the territory to remove
   * @return True if it succeeds, False if t not in territories
   */
  public boolean tryRemoveTerritory(Territory t){
    if(containsTerritory(t)){
      territories.remove(t);
      return true;
    }
    return false;
  }
  public boolean isDefeated(){
    return territories.size()==0;
  }
  public boolean isWinner(int amount) {
    return this.territories.size() == amount;
  }
  public String display(){
    StringBuilder sb = new StringBuilder();
    sb.append("{\n\"level\":\""+level+"\",");
    sb.append("\n\"food\":\""+getFoodAmount()+"\",");
    sb.append("\n\"tech\":\""+getTechAmount()+"\"");
    sb.append("\n}");
    return sb.toString();
  }

  public int getFoodAmount() {
    return this.food.getAmount();
  }
  public int getTechAmount() {
    return this.tech.getAmount();
  }
  public int getLevel(){return this.level;}

  public void addTechResource(int addAmount) {
    this.tech.addResource(addAmount);
  }
  public void addFoodResource(int amount) {
    this.food.addResource(amount);
  }
  public void collectResources() {
    for (Territory t : territories) {
      if (!t.getStarvationStatus()) {
        t.produceFoodResource(food);
        t.produceTechResource(tech);
      } else {
        t.downgradeUnits();
      }
      if (t.getMeteorStatus()) {
        // todo
        t.killAllUnits();
      }
    }
  }
  public void upgradeTechLevel(){
    level++;
  }

  public void setLevel(int l){level = l;}
}

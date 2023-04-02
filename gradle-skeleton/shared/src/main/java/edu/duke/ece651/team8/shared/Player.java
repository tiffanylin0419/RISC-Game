package edu.duke.ece651.team8.shared;
import java.util.ArrayList;

public class Player {
  private final ArrayList<Territory> territories;
  private String color;
  private int unitMax;
  private boolean isConnected;
  //constructor
  public Player(String color){
    this.territories=new ArrayList<>();
    this.color=color;
    this.unitMax=0;
    this.isConnected = true;
  }

  public void disconnect() {
    isConnected = false;
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
   * add t to territories
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
}

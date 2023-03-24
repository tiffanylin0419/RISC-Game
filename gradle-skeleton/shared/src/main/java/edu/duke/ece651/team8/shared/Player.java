package edu.duke.ece651.team8.shared;
import java.util.ArrayList;

public class Player {
  private ArrayList<Territory> territories;
  private String color;
  private int unitMax;
  private boolean isConnect;
  //constructor
  public Player(String color){
    this.territories=new ArrayList<>();
    this.color=color;
    this.unitMax=0;
    this.isConnect = true;
  }

  public void disconnect() {
    isConnect = false;
  }
  public boolean isConnected() {
    return isConnect;
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
   * @param unitMax
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
  public ArrayList<Territory> getTerritores(){return territories;}

  /**
   * add t to territories
   * @param t
   */
  public void addTerritory(Territory t){
    territories.add(t);
  }

  /**
   * @param t
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
   * @param t
   * @return True if suceed, False if t not in territories
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

package edu.duke.team8.riskgame;
import java.util.ArrayList;
import java.util.Iterator;

public abstract class Player {
  private ArrayList<Territory> territories;
  private String color;

  //constructor
  public Player(String color){
    this.territories=new ArrayList<Territory>();
    this.color=color;
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

  /**
   * @return iterator of territories
   */
  public Iterator<Territory> getTerritoryIterator() {
    return this.territories.iterator();
  }
}

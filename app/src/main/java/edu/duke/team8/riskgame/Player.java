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
  
  //get
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

  public void addTerritory(Territory t){
    territories.add(t);
  }

  public boolean containsTerritory(Territory t){
    for(Territory territory: territories){
      if(territory.equals(t)){
        return true;
      }
    }
    return false;
  }


  public boolean tryRemoveTerritory(Territory t){
    if(containsTerritory(t)){
      territories.remove(t);
      return true;
    }
    return false;
  }

  public Iterator<Territory> getTerritoryIterator() {
    return this.territories.iterator();
  }
}

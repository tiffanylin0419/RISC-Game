package edu.duke.team8.riskgame;

import java.util.HashSet;

public class BasicTerritory implements Territory {
  //field
  private String name;
  private Player owner;
  private HashSet<Territory> adjacent_territory;
  //constructor
  public BasicTerritory(String name){
    this.name=name;
    this.owner=null;
    this.adjacent_territory=new HashSet<Territory>();
  }
  public BasicTerritory(String name, Player owner){
    this(name);
    this.owner=owner;
     owner.addTerritory(this);
  }
  
  //get
  @Override
  public String getName(){return name;}

  @Override
  public HashSet<Territory> getAdjacent(){return adjacent_territory;}
  //equals
  @Override
  public boolean equals(Object other) {
    if (other != null && other.getClass().equals(getClass())) {
      Territory otherTerritory = (Territory) other;//
      return name == otherTerritory.getName();
    }
    return false;
  }
  
  @Override
  public boolean hasOwner(){
    return !(this.owner==null);
  }

  @Override
  public void changeOwner(Player new_owner){
    Player old_owner=this.owner;
    old_owner.tryRemoveTerritory(this);
    new_owner.addTerritory(this);
    this.owner=new_owner;
  }

  /*  @Override
  public Player getOwner(){
    return this.owner;
    }*/
  @Override
  public boolean isOwner(Player owner){
    return this.owner==owner;
  }

  @Override
  public void addAdjacent(Territory adjacent){
    adjacent_territory.add(adjacent);
  }

  @Override
  public boolean isAdjacentEnemy(Territory adjacent){
    return adjacent_territory.contains(adjacent);
  }

  @Override
  public boolean isAdjacentSelf(Territory adjacent){
    HashSet<Territory> to_visit=new HashSet<Territory>();
    HashSet<Territory> visited=new HashSet<Territory>();
    to_visit.add(this);
    while(to_visit.size()>0){
      if(to_visit.contains(adjacent)){
        return true;
      }
      HashSet<Territory> tmp=new HashSet<Territory>();
      for(Territory t: to_visit){
        HashSet<Territory> adjacents=t.getAdjacent();
        for(Territory ad_t: adjacents){
          if(!to_visit.contains(ad_t) && !visited.contains(ad_t) && ad_t.isOwner(owner)){
            tmp.add(ad_t);
          }
        }
      }
      visited.addAll(to_visit);
      to_visit.clear();
      to_visit.addAll(tmp);
    }
    return false;
  }
}

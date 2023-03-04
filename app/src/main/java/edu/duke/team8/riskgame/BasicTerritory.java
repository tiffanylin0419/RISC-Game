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
  
  //get
  @Override
  //equals
  public String getName(){return name;}
  @Override
  public boolean equals(Object other) {
    if (other != null && other.getClass().equals(getClass())) {
      Territory otherTerritory = (Territory) other;//
      return name == otherTerritory.getName();
    }
    return false;
  }

  @Override
  public void addOwner(Player owner){
    this.owner=owner;
    owner.addTerritory(this);
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

  @Override
  public Player getOwner(){
    return this.owner;
  }

  @Override
  public void addAdjacent(Territory adjacent){
    adjacent_territory.add(adjacent);
  }

  @Override
  public boolean isAdjacent(Territory adjacent){
    return adjacent_territory.contains(adjacent);
  }
}

package edu.duke.team8.riskgame;

import java.util.ArrayList;

public class BasicTerritory implements Territory {
  //field
  private String name;
  private Player owner;
  private ArrayList<Territory> adjacent_territory;
  //constructor
  public BasicTerritory(String name){
    this.name=name;
    this.owner=null;
    this.adjacent_territory=new ArrayList<Territory>();
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
}

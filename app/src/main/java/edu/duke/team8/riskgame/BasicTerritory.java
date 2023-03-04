package edu.duke.team8.riskgame;

public class BasicTerritory implements Territory {
  //field
  private String name;
  private Player owner;
  //constructor
  public BasicTerritory(String name){this.name=name;this.owner=null;}
  public BasicTerritory(String name, Player owner){
    this.name=name;
    this.owner=owner;
    owner.addTerritory(this);
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

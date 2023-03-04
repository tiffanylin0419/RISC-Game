package edu.duke.tl330.battleship;

public class BasicTerritory implements Territory {
  //field
  private String name;
  //constructor
  public BasicTerritory(String name){this.name=name;}
  //get
  public String getName(){return name;}
  //equals
  @Override
  public boolean equals(Object other) {
    if (other != null && other.getClass().equals(getClass())) {
      Territory otherTerritory = (Territory) other;//
      return name == otherTerritory.getName();
    }
    return false;
  }
  
}

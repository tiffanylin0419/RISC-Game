package edu.duke.team8.riskgame;

import java.util.HashSet;

public interface Territory {
  //return name
  public String getName();

  //return adjacent_territory
  public HashSet<Territory> getAdjacent();

  //return True if equals
  //return False if not equal
  public boolean equals(Object other);

  //return True if Territory have an owner
  //should always have owner when there is units inside
  public boolean hasOwner();

  //change the owner of territory to new_owner
  public void changeOwner(Player new_owner);

  //return True if input is the owner of the territory
  public boolean isOwner(Player owner);

  //add input into adjacent_territory
  public void addAdjacent(Territory adjacent);

  //input territory has the same owner as this
  //return True if input is adjacent to this
  //adjacent could mean that it crosses multiple of its own territory to get to this
  public boolean isAdjacentSelf(Territory adjacent);

  //input territory has different owner as this
  //return True if input is adjacent to this
  //adjacent mean that it is directly adjacent to this
  public boolean isAdjacentEnemy(Territory adjacent);

  //add unit_in into units
  //will modify existing list if units have unit that has the same owner
  public void moveIn(Unit unit_in);

  //pass in any declared unit
  //will check if the units list have unit that has same owner
  //return True if sucessfully moved out the unit
  //return False if the unit number is not big enough for unit_out to be moved out
  public boolean tryMoveOut(Unit unit_out);

  //resolve the condition when the units list have a size > 1
  //the one with a larger roll number wins
  //if 2 units: defender wins when there is a tie
  //if >2 units: the left one wins when there is a tie
  //units will be left with only one unit, which will become the owner
  public void attack();

  //get amount of the nth unit in units
  public int getUnitAmount(int n);

  //get the size of units
  //for testing 
  public int getUnitsSize();
}


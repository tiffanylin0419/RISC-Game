package edu.duke.team8.riskgame;

import java.util.HashSet;

public interface Territory {
  /**
   * @return name
   */
  public String getName();

  /**
   * @return adjacent_territory
   */
  public HashSet<Territory> getAdjacent();

  /**
   * @param other
   * @return True if equals, False if not equal
   */
  public boolean equals(Object other);

  /**
   * @return True if Territory have an owner
   * should have owner when there is units inside
   */
  public boolean hasOwner();

  /**
   * must use this function when changing owner
   * change the owner to new_owner
   * will remove this territory from old owner into new owner
   * @param new_owner the new owner
   */
  public void changeOwner(Player new_owner);

  /**
   * check if input is the owner
   * @param owner
   * @return True if input is the owner, False if otherwise
   */
  public boolean isOwner(Player owner);

  /**
   * add adjacent into adjacent_territory
   * @param adjacent
   */
  public void addAdjacent(Territory adjacent);

  /**
   * input territory has the same owner as this
   *
   * @param adjacent
   * @return True if input is adjacent to this, False otherwise
   * adjacent means that it can cross multiple of its own territory to get to this
   */
  public boolean isAdjacentSelf(Territory adjacent);

  /**
   * input territory has different owner as this
   * @param adjacent
   * @return True if input is adjacent to this, False otherwise
   * adjacent mean that it is directly adjacent to this
   */
  public boolean isAdjacentEnemy(Territory adjacent);

  /**
   * add unit_in into units
   * will modify existing list if units have unit that has the same owner
   * @param unit_in
   */
  public void moveIn(Unit unit_in);

  /**
   * pass in any declared unit
   * will check if the units list have unit that has same owner and move out the amount
   * @param unit_out
   * @return True if sucessfully moved out the unit, False otherwise
   */
  public boolean tryMoveOut(Unit unit_out);

  /**
   * resolve the condition when the units list have a size > 1
   * the one with a larger roll number wins
   * if 2 units: defender wins when there is a tie
   * if >2 units: the left one wins when there is a tie
   * units will be left with only one unit, which will become the owner
   */
  public void attack();

  /**
   * @param n
   * @return amount of the nth unit in units
   */
  public int getUnitAmount(int n);

  /**
   * @return size of units
   */
  public int getUnitsSize();
}

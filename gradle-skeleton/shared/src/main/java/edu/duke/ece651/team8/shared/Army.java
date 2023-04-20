package edu.duke.ece651.team8.shared;

import java.util.List;

public interface Army {

  /**
   * add 1 unit to list
   */
  void addOne(Unit u);

  /**
   * add unit List to list
   */
  void add(List<Unit> uList);

  /**
   * remove 1 from list
   * we call this function after we checked that the unit.isSurvive=True
   * so no need to tryRemoveOne
   */
  public boolean removeOne(Unit u);
//  /**
//   * remove unit List
//   */
//  void remove(List<Unit> uList);

  public boolean remove(List<Unit> uList);
  /**
   * @return owner
   */
  Player getOwner();

  /**
   * @return list of unit
   */
  List<Unit> getList();

  /**
   *
   */
  int getAmount();

  /**
   * @return amount of units
   */
  int getAmount(int n);

  /**
   * @return True if amount>0, else otherwise
   */
  boolean isSurvive();

  /**
   * @return a random number from 0~19(inclusive)
   */
  int doRoll();

  void upgradeUnits(int unitAmount, int startLevel, int nextLevel);

  void setMoved();

  void setUnmoved();
}

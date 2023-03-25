package edu.duke.ece651.team8.shared;

public interface Unit {

  /**
   * add n to amount
   * @param n the number of unit to add
   */
  void add(int n);
  
  /**
   * add 1 to amount
   */
  void addOne();

  /**
   * remove n from amount
   * @param n the number of unit to remove
   */
  void remove(int n);
  
  /**
   * remove 1 from amount
   * we call this function after we checked that the unit.isSurvive=True
   * so no need to tryRemoveOne
   */
  void removeOne();

  /**
   * @return owner
   */
  Player getOwner();

  /**
   * @return amount
   */
  int getAmount();

  /**
   * @return True if amount>0, else otherwise
   */
  boolean isSurvive();

  /**
   * @return a random number from 0~19(inclusive)
   */
  int doRoll();
}

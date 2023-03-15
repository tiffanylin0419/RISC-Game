package edu.duke.ece651.team8.shared;

public interface Unit {

  /**
   * add n to amount
   * @param n
   */
  public void add(int n);
  
  /**
   * add 1 to amount
   */
  public void addOne();

  /**
   * remove n from amount
   * @param n
   */
  public void remove(int n);
  
  /**
   * remove 1 from amount
   * we call this function after we checked that the unit.isSurvive=True
   * so no need to tryRemoveOne
   */
  public void removeOne();

  /**
   * @return owner
   */
  public Player getOwner();

  /**
   * @return amount
   */
  public int getAmount();

  /**
   * @return True if amount>0, else otherwise
   */
  public boolean isSurvive();

  /**
   * @return a random number from 0~19(inclusive)
   */
  public int doRoll();
}

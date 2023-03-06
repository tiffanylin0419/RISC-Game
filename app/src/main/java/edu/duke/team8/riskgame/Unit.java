package edu.duke.team8.riskgame;

public interface Unit {
  //add n to amount
  public void add(int n);

  //add 1 to amount
  public void addOne();

  //try to remove n from amount
  //return True if sucess
  //return False if fail
  public boolean tryRemove(int n);

  //remove 1 from amount
  //we call this function after we checked that the unit.isSurvive=True
  //so no need to tryRemoveOne
  public void removeOne();

  //get owner 
  public Player getOwner();

  //get amount
  public int getAmount();

  //return True if amount>0
  public boolean isSurvive();

  //return a random number from 0~19(inclusive)
  public int doRoll();
}

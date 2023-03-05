package edu.duke.team8.riskgame;

public interface Unit {
  public void add(int n);
  public void addOne();
  public boolean tryRemove(int n);
  public boolean tryRemoveOne();
  public Player getOwner();
  public int getAmount();
  public int doRoll();
}

package edu.duke.team8.riskgame;

public interface Territory {
  public String getName();
  public boolean equals(Object other);
  public boolean hasOwner();
  public void changeOwner(Player new_owner);
  public Player getOwner();
}

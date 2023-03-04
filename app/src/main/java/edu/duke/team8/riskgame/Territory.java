package edu.duke.team8.riskgame;

import java.util.HashSet;

public interface Territory {
  public String getName();
  public HashSet<Territory> getAdjacent();
  public boolean equals(Object other);
  public boolean hasOwner();
  public void changeOwner(Player new_owner);
  // public Player getOwner();
  public boolean isOwner(Player owner);
  public void addAdjacent(Territory adjacent);
  public boolean isAdjacentSelf(Territory adjacent);
  public boolean isAdjacentEnemy(Territory adjacent);
}

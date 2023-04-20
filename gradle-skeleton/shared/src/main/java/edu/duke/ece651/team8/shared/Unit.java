package edu.duke.ece651.team8.shared;

public interface Unit extends Comparable<Unit>{
    /**
     * upgrade current unit
     * @return the upgraded unit
     */
    public Unit upgrade();


    /**
     * get the cost for upgrade
     * @return cost
     */
    public int getUpgradeCost();

    /**
     * get level of the unit
     * @return level
     */
    public int getLevel();

    /**
     * get type of the unit
     * @return level
     */
    public String getType();

    int getBonus();

    boolean hasMoved();
//    public int getCostOfCurrentLevel(int index);

    void setMoved();

    void setUnmoved();
}

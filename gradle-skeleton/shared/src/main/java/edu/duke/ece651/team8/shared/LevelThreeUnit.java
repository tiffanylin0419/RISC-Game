package edu.duke.ece651.team8.shared;

public class LevelThreeUnit implements Unit{
    private final int level = 3;
    private final String type = "Rider";
    private final int bonus = 5;
    private final int upgradeCost = 25;
    //constructor

    @Override
    public Unit upgrade() {
        return new LevelThreeUnit(); //change to level four later
    }

    @Override
    public int getUpgradeCost() {
        return upgradeCost;
    }

    @Override
    public int getLevel() {
        return level;
    }

    @Override
    public String getType() {
        return type;
    }
    @Override
    public boolean equals(Object other) {
        if(other != null && other.getClass().equals(getClass())) {
            Unit otherUnit = (Unit) other;
            return level == otherUnit.getLevel();
        }
        return false;
    }
}
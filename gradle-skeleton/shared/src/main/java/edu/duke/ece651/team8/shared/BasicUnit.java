package edu.duke.ece651.team8.shared;

public class BasicUnit implements Unit{
    private final int level = 0;
    private final String type = "Servant";
    private final int bonus = 0;
    private final int upgradeCost = 3;
    //constructor

    @Override
    public Unit upgrade() {
        return new LevelOneUnit();
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

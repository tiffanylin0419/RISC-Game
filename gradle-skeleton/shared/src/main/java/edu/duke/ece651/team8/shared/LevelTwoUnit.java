package edu.duke.ece651.team8.shared;

public class LevelTwoUnit implements Unit{
    private final int level = 2;
    private final String type = "Caster";
    private final int bonus = 3;
    private final int upgradeCost = 19;
    //constructor

    @Override
    public Unit upgrade() {
        return new LevelThreeUnit();
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

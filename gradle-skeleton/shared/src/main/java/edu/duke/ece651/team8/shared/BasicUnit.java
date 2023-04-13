package edu.duke.ece651.team8.shared;

import org.jetbrains.annotations.NotNull;

public class BasicUnit implements Unit{
    private final int level;
    private final String type;
    private final int bonus;
    private final int upgradeCost;
    //constructor

    public BasicUnit() {
        this.level = 0;
        this.type = "Servant";
        this.bonus = 0;
        this.upgradeCost = 3;
    }

    public BasicUnit(int level, String type, int bonus, int upgradeCost) {
        this.level = level;
        this.type = type;
        this.bonus = bonus;
        this.upgradeCost = upgradeCost;
    }

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
    @Override
    public int getBonus(){
        return bonus;
    }

    @Override
    public int compareTo(@NotNull Unit o) {
        return this.getBonus() - o.getBonus();
    }
}

package edu.duke.ece651.team8.shared;

import org.jetbrains.annotations.NotNull;

public class Spy implements Unit{
    private final int level = -1;
    private final String type = "Spy";
    private final int bonus = 1000;
    //constructor

    @Override
    public Unit upgrade() {
        return this; //change to level four later
    }

    @Override
    public int getUpgradeCost() {
        return 0;
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

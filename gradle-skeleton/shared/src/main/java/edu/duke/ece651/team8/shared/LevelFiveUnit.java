package edu.duke.ece651.team8.shared;

import org.jetbrains.annotations.NotNull;

public class LevelFiveUnit implements Unit,Spyable{
    private final int level = 5;
    private final String type = "Berserker";
    private final int bonus = 11;
    private UpgradeAction upgradeAction = new UpgradeAction();

    private final int upgradeCost = this.upgradeAction.getCost(5);
    //constructor

    @Override
    public Unit upgrade() {
        return new LevelSixUnit(); //change to level four later
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

    @Override
    public Spy becomeSpy() {
        return new Spy();
    }
}

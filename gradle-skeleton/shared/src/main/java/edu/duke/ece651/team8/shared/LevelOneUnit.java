package edu.duke.ece651.team8.shared;

import org.jetbrains.annotations.NotNull;

public class LevelOneUnit implements Unit,Spyable{
    private final int level = 1;
    private final String type = "Assassin";
    private final int bonus = 1;

    private UpgradeAction upgradeAction = new UpgradeAction();

    private final int upgradeCost = this.upgradeAction.getCost(1);
    //constructor

    @Override
    public Unit upgrade() {
        return new LevelTwoUnit();
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

package edu.duke.ece651.team8.shared;

import org.jetbrains.annotations.NotNull;

public class LevelTwoUnit implements Unit,Spyable{
    private final int level = 2;
    private final String type = "Rider";
    private final int bonus = 3;
    private UpgradeAction upgradeAction = new UpgradeAction();

    private final int upgradeCost = this.upgradeAction.getCost(2);
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

package edu.duke.ece651.team8.shared;

import org.jetbrains.annotations.NotNull;

public class BasicUnit implements Unit,Spyable{
    private final int level;
    private final String type;
    private final int bonus;
    private final int upgradeCost;

    private final UpgradeAction upgradeAction;
    //constructor
    public BasicUnit() {
        this.level = 0;
        this.type = "Servant";
        this.bonus = 0;
        this.upgradeAction = new UpgradeAction();
        this.upgradeCost = this.upgradeAction.getCost(0);
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

    @Override
    public Spy becomeSpy() {
        return new Spy();
    }
}

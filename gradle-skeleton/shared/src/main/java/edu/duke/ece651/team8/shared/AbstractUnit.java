package edu.duke.ece651.team8.shared;

import org.jetbrains.annotations.NotNull;

public abstract class AbstractUnit implements Unit{
    protected int level;
    protected String type;
    protected int bonus;
    protected int upgradeCost;

    protected boolean hasMoved;

    static protected final UpgradeAction upgradeAction = new UpgradeAction();

    static protected final int spyCost = 80;
    //constructor
    public AbstractUnit(int level,String type, int bonus) {
        this.level = level;
        this.type = type;
        this.bonus = bonus;
        if(level>=0){
            this.upgradeCost = upgradeAction.getCost(level);
        }else{
            this.upgradeCost = spyCost;
        }
        this.hasMoved = false;
    }

    @Override
    public Unit upgrade() {
        return this;
    }

    @Override
    public Unit downgrade() {
        return this;
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
    public boolean hasMoved() {
        return hasMoved;
    }

    @Override
    public int compareTo(@NotNull Unit o) {
        return this.getBonus() - o.getBonus();
    }

    @Override
    public void setMoved(){
        this.hasMoved = true;
    }

    @Override
    public void setUnmoved(){
        this.hasMoved = false;
    }
}

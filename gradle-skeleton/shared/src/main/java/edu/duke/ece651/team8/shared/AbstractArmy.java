package edu.duke.ece651.team8.shared;

import java.util.*;

public abstract class AbstractArmy implements Army{
    protected Player owner;
    protected List<Unit> units;
    protected UnitFactory uf;

    public AbstractArmy(Player owner, List<Unit> units){
        this.owner = owner;
        this.units = units;
        this.uf = new UnitFactory();
    }
    public AbstractArmy(int amount, Player owner){
        this(owner,null);
        this.uf = new UnitFactory();
        this.units = uf.makeBasicUnits(amount);
    }
    @Override
    public void add(List<Unit> uList) {
        units.addAll(uList);
    }
    @Override
    public void addOne(Unit u) {
        units.add(u);
    }

    @Override
    public boolean removeOne(Unit u) {
        return units.remove(u);
    }
    @Override
    public boolean remove(List<Unit> uList) {
        for(Unit u : uList) {
            if(!units.remove(u)) return false;
        }
        return true;
    }
    @Override
    public Player getOwner() {
        return owner;
    }

    @Override
    public List<Unit> getList() {
        Collections.sort(units);
        return units;
    }

    @Override
    public int getAmount(){
        return getAmount(0);
    }
    @Override
    public boolean isSurvive(){
        return units.size()>0;
    }

    @Override
    public int doRoll() {
        Random rand = new Random();
        return rand.nextInt(20);
    }

    @Override
    public int getAmount(int n) {
        int count = 0;
        for(Unit u:units){
            if(u.getLevel()==n){
                count++;
            }
        }
        return count;
    }

    @Override
    public int getAllAmount() {
        int count = 0;
        for(Unit u:units){
            count++;
        }
        return count;
    }

    @Override
    public void upgradeUnits(int unitAmount, int startLevel, int nextLevel) {
        int cur = unitAmount;
        for(int i = 0; i < cur; i++) {
            Unit unit = getLevelUnit(startLevel);
            upgradeOneUnit(unit, nextLevel - startLevel);
        }

    }
    private Unit getLevelUnit(int level) {
        for(Unit u : units) {
            if(u.getLevel() == level) {
                return u;
            }
        }
        return null;
    }
    private void upgradeOneUnit(Unit unit, int diff) {
        units.remove(unit);
        Unit upgradedUnit = uf.upgradeTo(diff, unit);
        units.add(upgradedUnit);
    }

    @Override
    public void setMoved(){
        for(Unit u: units){
            u.setMoved();
        }
    }
    @Override
    public void setUnmoved(){
        for(Unit u: units){
            u.setUnmoved();
        }
    }
}

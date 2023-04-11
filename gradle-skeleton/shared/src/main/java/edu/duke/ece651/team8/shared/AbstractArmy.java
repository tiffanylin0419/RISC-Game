package edu.duke.ece651.team8.shared;

import java.util.*;

public abstract class AbstractArmy implements Army{
    protected Player owner;
    protected List<Unit> units;

    public AbstractArmy(Player owner, List<Unit> units){
        this.owner = owner;
        this.units = units;
    }
    public AbstractArmy(int amount, Player owner){
        this(owner,null);
        UnitFactory uf = new UnitFactory();
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
        return units.size();
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
        int count=0;
        for(Unit u:units){
            if(u.getLevel()==n){
                count++;
            }
        }
        return count;
    }

}

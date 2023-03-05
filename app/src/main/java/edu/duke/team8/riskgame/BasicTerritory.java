package edu.duke.team8.riskgame;

import java.util.ArrayList;
import java.util.HashSet;

public class BasicTerritory implements Territory {
  //field
  private String name;
  private Player owner;
  private HashSet<Territory> adjacent_territory;
  private ArrayList<Unit> units;
  //constructor
  public BasicTerritory(String name){
    this.name=name;
    this.owner=null;
    this.adjacent_territory=new HashSet<Territory>();
    this.units=new ArrayList<Unit>();
  }
  public BasicTerritory(String name, Player owner){
    this(name);
    this.owner=owner;
     owner.addTerritory(this);
  }
  
  //get
  @Override
  public String getName(){return name;}

  @Override
  public HashSet<Territory> getAdjacent(){return adjacent_territory;}
  //equals
  @Override
  public boolean equals(Object other) {
    if (other != null && other.getClass().equals(getClass())) {
      Territory otherTerritory = (Territory) other;//
      return name == otherTerritory.getName();
    }
    return false;
  }
  
  @Override
  public boolean hasOwner(){
    return !(this.owner==null);
  }

  @Override
  public void changeOwner(Player new_owner){
    Player old_owner=this.owner;
    old_owner.tryRemoveTerritory(this);
    new_owner.addTerritory(this);
    this.owner=new_owner;
  }

  /*  @Override
  public Player getOwner(){
    return this.owner;
    }*/
  @Override
  public boolean isOwner(Player owner){
    return this.owner==owner;
  }

  @Override
  public void addAdjacent(Territory adjacent){
    adjacent_territory.add(adjacent);
  }

  @Override
  public boolean isAdjacentEnemy(Territory adjacent){
    return adjacent_territory.contains(adjacent);
  }

  @Override
  public boolean isAdjacentSelf(Territory adjacent){
    HashSet<Territory> to_visit=new HashSet<Territory>();
    HashSet<Territory> visited=new HashSet<Territory>();
    to_visit.add(this);
    while(to_visit.size()>0){
      if(to_visit.contains(adjacent)){
        return true;
      }
      HashSet<Territory> tmp=new HashSet<Territory>();
      for(Territory t: to_visit){
        HashSet<Territory> adjacents=t.getAdjacent();
        for(Territory ad_t: adjacents){
          if(!to_visit.contains(ad_t) && !visited.contains(ad_t) && ad_t.isOwner(owner)){
            tmp.add(ad_t);
          }
        }
      }
      visited.addAll(to_visit);
      to_visit.clear();
      to_visit.addAll(tmp);
    }
    return false;
  }
  @Override
  public void moveIn(Unit unit_in) {
    for(Unit unit: units){
      if(unit.getOwner()==unit_in.getOwner()){
        unit.add(unit_in.getAmount());
        return;
      }
    }
    units.add(unit_in);
  }
  @Override
  public boolean tryMoveOut(Unit unit_out) {
    for(Unit unit: units){
      if(unit.getOwner()==unit_out.getOwner()){
        if(!unit.tryRemove(unit_out.getAmount())){
          return false;
        }
        if (unit.getAmount()==0){
          units.remove(unit);
        }
        return true;
      }
    }
    return false;
  }

  private void fight1(Unit u1,Unit u2){
    if(u1.isSurvive() && u2.isSurvive()){
      if(u1.doRoll()<u2.doRoll()){//u2 win
        u1.removeOne();
      }
      else{//u1 win
        u2.removeOne();
      }
    }
  }
  
  @Override
  public void attack() {
    if(units.size()==2){
      while(units.size()>1){
        fight1(units.get(0),units.get(1));
        ArrayList<Unit> remove=new ArrayList<Unit>();
        for(Unit unit: units){
          if(!unit.isSurvive()){
            remove.add(unit);
          }
        }
        for(Unit unit: remove){
            units.remove(unit);
        }
      }
    }
    while(units.size()>1){
      int l=units.size();
      for(int i=0;i<l;i++){
        fight1(units.get(i),units.get((i+1)%l));
      }
      ArrayList<Unit> remove=new ArrayList<Unit>();
      for(Unit unit: units){
        if(!unit.isSurvive()){
          remove.add(unit);
        }
      }
      for(Unit unit: remove){
          units.remove(unit);
        }
    }
  }

  @Override
  public int getUnitAmount(int n){
    return units.get(n).getAmount();
  }

  @Override
  public int getUnitsSize(){
    return units.size();
  }
}

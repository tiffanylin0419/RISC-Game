package edu.duke.ece651.team8.shared;

import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;
import java.util.HashSet;

public class BasicTerritory implements Territory {
  //field
  private final String name;
  private Player owner;
  private final HashSet<Territory> adjacent_territory;

  private final ArrayList<Territory> adjList;
  private final ArrayList<Unit> units;
  //constructor
  public BasicTerritory(String name){
    this.name=name;
    this.owner=null;
    this.adjacent_territory=new HashSet<>();
    this.adjList = new ArrayList<>();
    this.units=new ArrayList<>();
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
      return name.equals(otherTerritory.getName());
    }
    return false;
  }
  
  @Override
  public boolean hasOwner(){
    return !(this.owner==null);
  }

  @Override
  public void changeOwner(){
    if(!this.owner.equals(units.get(0).getOwner())){
      this.owner.tryRemoveTerritory(this);
      this.owner=units.get(0).getOwner();
      this.owner.addTerritory(this);
    }
  }

  @Override
  public boolean isOwner(Player owner){
    if(this.owner==null){
      return false;
    }
    return this.owner==owner;
  }

  @Override
  public void addAdjacent(Territory adjacent){
    adjacent_territory.add(adjacent);
    adjList.add(adjacent);
  }

  @Override
  public boolean isAdjacentEnemy(Territory adjacent){
    return adjacent_territory.contains(adjacent);
  }

  @Override
  public boolean isAdjacentSelf(Territory adjacent){
    HashSet<Territory> to_visit=new HashSet<>();
    HashSet<Territory> visited=new HashSet<>();
    to_visit.add(this);
    while(to_visit.size()>0){
      if(to_visit.contains(adjacent)){
        return true;
      }
      HashSet<Territory> tmp=new HashSet<>();
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
    if(this.units.isEmpty()){
      this.units.add(unit_in);
      this.owner=unit_in.getOwner();
      return;
    }
    for(Unit unit: this.units){
      if(unit.getOwner()==unit_in.getOwner()){
        unit.add(unit_in.getAmount());
        return;
      }
    }
    this.units.add(unit_in);
  }
  @Override
  public void moveOut(Unit unit_out){
    for(Unit unit: units){
      if(unit.getOwner()==unit_out.getOwner()){
        unit.remove(unit_out.getAmount());
        return;
      }
    }

  }

  /**
   * This function process a one-unit fight between 2 units from defender and attacker
   * the loser will lose one unit after the fight
   *
   * @param u1 a unit of defender
   * @param u2 a unit of attacker
   */
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

  /**
   * This function process a fight between 2 players in one territory defender and attacker
   */
  private void oneToOneAttack(){
    while(units.size()>1){
      fight1(units.get(0),units.get(1));
      ArrayList<Unit> remove=new ArrayList<>();
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

  /**
   * This function process a fight between multiple players in one territory
   */
  private void manyToOneAttack(){
    while(units.size()>1){
      int l=units.size();
      for(int i=0;i<l;i++){
        fight1(units.get(i),units.get((i+1)%l));
      }
      ArrayList<Unit> remove=new ArrayList<>();
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
  public void attack() {
    if(units.size()==2){
      oneToOneAttack();
    }else if(units.size()>2){
      manyToOneAttack();
    }
    changeOwner();
  }
  @Override
  public void addOne(){
    if(units.size()>0){
      units.get(0).addOne();
    }
  }
  @Override
  public int getUnitAmount(int n){
    if(n>=units.size()){
      return 0;
    }
    return units.get(n).getAmount();
  }

  @Override
  public int getOwnerUnitAmount(){
    for(Unit unit: units){
      if(unit.getOwner().equals(owner)){
        return unit.getAmount();
      }
    }
    return 0;
  }

  @Override
  public int getUnitsSize(){
    return units.size();
  }

  @Override
  public ArrayList<Territory> getAdjList() {
    return this.adjList;
  }

  @Override
  public Player getOwner() {
    return this.owner;
  }

  @Override
  public Player setOwner(Player player) {
    this.owner = player;
    return this.owner;
  }
}

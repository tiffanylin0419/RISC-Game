package edu.duke.ece651.team8.shared;


import java.util.ArrayList;
import java.util.HashSet;

public class BasicTerritory implements Territory {
  //field
  private final String name;
  private Player owner;
  private final HashSet<Territory> adjacent_territory;

  private final ArrayList<Territory> adjList;
  private final ArrayList<Army> armies;
  //constructor
  public BasicTerritory(String name){
    this.name=name;
    this.owner=null;
    this.adjacent_territory=new HashSet<>();
    this.adjList = new ArrayList<>();
    this.armies =new ArrayList<>();
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
    if(!this.owner.equals(armies.get(0).getOwner())){
      this.owner.tryRemoveTerritory(this);
      this.owner= armies.get(0).getOwner();
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
        HashSet<Territory> adjacentTerritories=t.getAdjacent();
        for(Territory ad_t: adjacentTerritories){
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
  public void moveIn(Army army_in) {
    if(this.armies.isEmpty()){
      this.armies.add(army_in);
      this.owner= army_in.getOwner();
      return;
    }
    for(Army army : this.armies){
      if(army.getOwner()== army_in.getOwner()){
        army.add(army_in.getAmount());
        return;
      }
    }
    this.armies.add(army_in);
  }
  @Override
  public void moveOut(Army army_out){
    for(Army army : armies){
      if(army.getOwner()== army_out.getOwner()){
        army.remove(army_out.getAmount());
        return;
      }
    }

  }

  /**
   * This function process a one-unit fight between 2 armies from defender and attacker
   * the loser will lose one unit after the fight
   *
   * @param u1 a unit of defender
   * @param u2 a unit of attacker
   */
  private void fight1(Army u1, Army u2){
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
    while(armies.size()>1){
      fight1(armies.get(0), armies.get(1));
      ArrayList<Army> remove = new ArrayList<>();
      for(Army army : armies){
        if(!army.isSurvive()){
          remove.add(army);
        }
      }
      for(Army army : remove){
        armies.remove(army);
      }
    }
  }

  /**
   * This function process a fight between multiple players in one territory
   */
  private void manyToOneAttack(){
    while(armies.size()>1){
      int l= armies.size();
      for(int i=0;i<l;i++){
        fight1(armies.get(i), armies.get((i+1)%l));
      }
      ArrayList<Army> remove = new ArrayList<>();
      for(Army army : armies){
        if(!army.isSurvive()){
          remove.add(army);
        }
      }
      for(Army army : remove){
        armies.remove(army);
      }
    }
  }

  @Override
  public synchronized void attack() {
    if(armies.size()==2){
      oneToOneAttack();
    }else if(armies.size()>2){
      manyToOneAttack();
    }
    changeOwner();
  }
  @Override
  public void addOne(){
    if(armies.size()>0){
      armies.get(0).addOne();
    }
  }
  @Override
  public int getUnitAmount(int n){
    if(n>= armies.size()){
      return 0;
    }
    return armies.get(n).getAmount();
  }

  @Override
  public int getOwnerUnitAmount(){
    for(Army army : armies){
      if(army.getOwner().equals(owner)){
        return army.getAmount();
      }
    }
    return 0;
  }

  @Override
  public int getUnitsSize(){
    return armies.size();
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
  public void setOwner(Player player) {
    this.owner = player;
  }
}

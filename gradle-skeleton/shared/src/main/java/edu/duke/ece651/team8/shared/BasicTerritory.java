package edu.duke.ece651.team8.shared;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class BasicTerritory implements Territory {
  //field
  protected final String name;
  protected Player owner;
  protected final HashSet<Territory> adjacent_territory;

  protected final HashMap<Territory, Integer> distances;

  protected final ArrayList<Territory> adjList;
  protected final ArrayList<Army> armies;
  private final ArrayList<Army> spyArmies;
  //constructor
  public BasicTerritory(String name){
    this.name=name;
    this.owner=null;
    this.adjacent_territory=new HashSet<>();
    this.distances = new HashMap<>();
    this.adjList = new ArrayList<>();
    this.armies =new ArrayList<>();
    this.spyArmies = new ArrayList<>();
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
  public boolean isAdjacent(Territory adjacent){
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
        army.add(army_in.getList());
        return;
      }
    }
    this.armies.add(army_in);
  }
  @Override
  public void moveOut(Army army_out){
    for(Army army : armies){
      if(army.getOwner()== army_out.getOwner()){
        army.remove(army_out.getList());
        return;
      }
    }

  }

  /**
   * This function process a one-unit fight between 2 armies from defender and attacker
   * the loser will lose one unit after the fight
   *
   * @param a1 a unit of defender
   * @param a2 a unit of attacker
   */
  private void fight1(Army a1, Army a2, int currentTurn){
    int attackerIndex = (currentTurn%2==0)?0:a2.getList().size()-1;
    int defenderIndex = (currentTurn%2==0)?0:a1.getList().size()-1;
    if(a1.isSurvive() && a2.isSurvive()){
      if(a1.doRoll()+ a1.getList().get(defenderIndex).getBonus()<a2.doRoll()+a2.getList().get(attackerIndex).getBonus()){//u2 win
        a1.removeOne(a1.getList().get(defenderIndex));
      }
      else{//u1 win
        a2.removeOne(a2.getList().get(attackerIndex));
      }
    }
  }

  /**
   * This function process a fight between 2 players in one territory defender and attacker
   */
  private void oneToOneAttack(){
    int currentTurn = 1;
    while(armies.size()>1){
      fight1(armies.get(0), armies.get(1),currentTurn);
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
    int currentTurn = 1;
    while(armies.size()>1){
      int l= armies.size();
      for(int i=0;i<l;i++){
        fight1(armies.get(i), armies.get((i+1)%l),currentTurn);
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
      Unit u = new BasicUnit();
      armies.get(0).addOne(u);
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
  public int getPlayerMovableUnitAmount(Player p ){
    for(Army army : armies){
      if(army.getOwner().equals(p)){
        int count = 0;
        for(Unit u:army.getList()){
          if(!u.hasMoved()){
            count++;
          }
        }
        return count;
      }
    }
    return 0;
  }
  @Override
  public int getPlayerMovableSpyAmount(Player p ){
    for(Army army : spyArmies){
      if(army.getOwner().equals(p)){
        int count = 0;
        for(Unit u:army.getList()){
          if(!u.hasMoved()){
            count++;
          }
        }
        return count;
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

  @Override
  public void setDistance(Territory t, int distance) {
    distances.put(t, distance);
  }

  @Override
  public Integer getDistance(Territory t) {

    if (!distances.containsKey(t)) {
      return null;
    } else {
      return distances.get(t);
    }
  }

  @Override
  public HashMap<Territory, Integer> getDistances() {
    return this.distances;
  }

  @Override
  public void produceFoodResource(Resource resource){return;}
  @Override
  public void produceTechResource(Resource resource){return;}

  @Override
  public void upgradeUnits(Player player, int unitAmount, int startLevel, int nextLevel) {
    Army target = null;
    for (Army army : this.armies) {
      if (army.getOwner().getColor().equals(player.getColor())) {
        target = army;
        break;
      }
    }
    if(nextLevel != -1){
      target.upgradeUnits(unitAmount, startLevel, nextLevel);
    }else{
      target.remove(target.getList().subList(0,unitAmount));
      Army army = new SpyArmy(unitAmount, player);
      spyArmiesMoveIn(army);
    }

  }
  @Override
  public int getOwnerUnitLevelAmount(int level){
    for(Army army : armies){
      if(army.getOwner().equals(owner)){
        return army.getAmount(level);
      }
    }
    return 0;
  }
  @Override
  public Army getArmy(int count, Player p) {
    Army moveArmy = new EvolvableArmy(0, p);
    for(Army army : armies){
      if(army.getOwner()== p){
        for(Unit u : army.getList()) {
          if(moveArmy.getAmount() == count) break;
          moveArmy.addOne(u);
        }

      }
    }
    return moveArmy;
  }
  public void spyArmiesMoveIn(Army army_in) {
    if(this.spyArmies.isEmpty()){
      this.spyArmies.add(army_in);
      return;
    }
    for(Army army : this.spyArmies){
      if(army.getOwner()== army_in.getOwner()){
        army.add(army_in.getList());
        return;
      }
    }
    this.spyArmies.add(army_in);
  }
  public void spyArmiesMoveOut(Army army_out){
    for(Army army : spyArmies){
      if(army.getOwner()== army_out.getOwner()){
        army.remove(army_out.getList());
        return;
      }
    }

  }
//  public int getSpyUnitAmount(int n){
//    if(n>= spyArmies.size()){
//      return 0;
//    }
//    return spyArmies.get(n).getAmount();
//  }
  public Army getSpyArmy(int count, Player p){
    Army moveArmy = new SpyArmy(0, p);
    for(Army army : spyArmies){
      if(army.getOwner()== p){
        for(Unit u : army.getList()) {
          if(moveArmy.getAmount() == count) break;
          moveArmy.addOne(u);
        }
      }
    }
    return moveArmy;
  }

  public Army getPlayerSpyArmy(Player p){
    Army moveArmy = new SpyArmy(0, p);
    for(Army army : spyArmies){
      if(army.getOwner()== p){
        return army;
      }
    }
    return moveArmy;
  }

}

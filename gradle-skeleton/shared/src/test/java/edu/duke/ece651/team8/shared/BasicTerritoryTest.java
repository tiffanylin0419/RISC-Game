package edu.duke.ece651.team8.shared;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class BasicTerritoryTest {
  @Test
  public void test_Constructor() {
    String s = "AbcdE";
    Territory t = new BasicTerritory(s);
    assertEquals(t.getName(), "AbcdE");
  }

  @Test
  public void test_equals() {
    String s = "AbcdE";
    Territory t1 = new BasicTerritory(s);
    Territory t2 = new BasicTerritory(s);
    assertEquals(t1,t2);
    assertNotEquals(t1,s);
  }

  @Test
  public void test_hasOwner(){
    String s = "AbcdE";
    Territory t1 = new BasicTerritory(s);
    assertFalse(t1.hasOwner());

    Player p=new TextPlayer("red");
    Territory t2 = new BasicTerritory(s,p);
    assertTrue(t2.hasOwner());
    assertTrue(t2.isOwner(p));
  }

  @Test
  public void test_changeOwner(){
    String s = "AbcdE";
    Player p1=new TextPlayer("red");
    Player p2=new TextPlayer("blue");
    Territory t = new BasicTerritory(s,p1);
    t.moveIn(new BasicArmy(1,p1));
    t.moveIn(new BasicArmy(2,p2));
    assertTrue(t.isOwner(p1));
    assertTrue(p1.containsTerritory(t));

    t.changeOwner();
    assertTrue(t.isOwner(p1));
    assertTrue(p1.containsTerritory(t));

    t.moveOut(new BasicArmy(1,p1));
    t.attack();

    t.changeOwner();
    assertTrue(t.isOwner(p2));
    assertFalse(p1.containsTerritory(t));
  }

  @Test
  public void test_addAdjacency(){
    Territory t1 = new BasicTerritory("s1");
    Territory t2 = new BasicTerritory("s2");
    Territory t3 = new BasicTerritory("s3");
    
    t1.addAdjacent(t2);
    assertTrue(t1.isAdjacent(t2));
    assertFalse(t1.isAdjacent(t3));
    t1.addAdjacent(t3);
    assertTrue(t1.isAdjacent(t3));
  }

  @Test
  public void test_isAdjacentSelf(){
    Player p1=new TextPlayer("red");
    Player p2=new TextPlayer("blue");


    Territory territories[]={new BasicTerritory("a",p1),new BasicTerritory("b",p1),new BasicTerritory("c",p1),new BasicTerritory("d",p2),new BasicTerritory("e",p1)};
    Map map = new Game1Map();
    for(Territory t: territories){
      map.addTerritory(t);
    }
    map.addAdjacency(territories[0],territories[1]);
    map.addAdjacency(territories[2],territories[1]);
    map.addAdjacency(territories[3],territories[1]);
    map.addAdjacency(territories[3],territories[4]);

    assertTrue(territories[0].isAdjacentSelf(territories[2]));
    assertTrue(territories[0].isAdjacentSelf(territories[1]));
    assertFalse(territories[0].isAdjacentSelf(territories[4]));
    assertFalse(territories[1].isAdjacentSelf(territories[4]));
  }


  @Test
  public void test_move(){
    Player p1=new TextPlayer("red");
    Player p2=new TextPlayer("blue");
    Territory territory=new BasicTerritory("a",p1);
    //move in
    territory.moveIn(new BasicArmy(5,p1));
    territory.moveIn(new BasicArmy(3,p1));
    assertEquals(8,territory.getUnitAmount(0));
    territory.moveIn(new BasicArmy(3,p2));
    assertEquals(8,territory.getUnitAmount(0));
    assertEquals(3,territory.getUnitAmount(1));
    //move out
    territory.moveOut(new BasicArmy(1,p2));
    assertEquals(8,territory.getUnitAmount(0));
    assertEquals(2,territory.getUnitAmount(1));
    territory.moveOut(new BasicArmy(8,p1));

    assertEquals(0,territory.getUnitAmount(0));
    assertEquals(2,territory.getUnitAmount(1));
    assertEquals(0,territory.getPlayerMovableUnitAmount(territory.getOwner()));
    assertTrue(territory.isOwner(p1));
  }
  
  @Test
  public void test_attack2(){
    Player p1=new TextPlayer("red");
    Player p2=new TextPlayer("blue");
    Territory territory=new BasicTerritory("a",p1);
    //p1 3,p2 2
    territory.moveIn(new BasicArmy(3,p1));
    territory.moveIn(new BasicArmy(2,p2));
    territory.attack();
    assertEquals(1,territory.getUnitsSize());
  }
  @Test
  public void test_attack3(){
     Player p1=new TextPlayer("red");
     Player p2=new TextPlayer("blue");
     Player p3=new TextPlayer("green");
     Player p4=new TextPlayer("aa");
     Player p5=new TextPlayer("bb");
     Territory territory=new BasicTerritory("a",p2);
      //p1 4,p2 1,p3 4,p4 1,p5 4,
      territory.moveIn(new BasicArmy(4,p1));
      territory.moveIn(new BasicArmy(0,p2));
      territory.moveIn(new BasicArmy(4,p3));
      territory.moveIn(new BasicArmy(1,p4));
      territory.moveIn(new BasicArmy(4,p5));
      assertEquals(4,territory.getPlayerMovableUnitAmount(territory.getOwner()));
      territory.attack();
      assertEquals(1,territory.getUnitsSize());
      assertEquals(0,territory.getUnitAmount(1));

      //might be wrong but less likely
      //assertTrue(territory.isOwner(p1)||territory.isOwner(p3)||territory.isOwner(p5));
  }

  @Test
  public void test_getOwnerUnitAmount(){

    AbstractMapFactory factory = new V1MapFactory();
    Map theMap = factory.createMap(1);
    ArrayList<Player> players =factory.createPlayers(1,theMap);

    Player p1= players.get(0);
    theMap.getTerritories().get(0).moveIn(new BasicArmy(5,p1));
    theMap.getTerritories().get(0).moveOut(new BasicArmy(2,p1));
    theMap.getTerritories().get(1).moveIn(new BasicArmy(4,p1));
    theMap.getTerritories().get(2).moveIn(new BasicArmy(3,p1));
    theMap.getTerritories().get(3).moveIn(new BasicArmy(2,p1));
    theMap.getTerritories().get(4).moveIn(new BasicArmy(1,p1));
    theMap.getTerritories().get(5).moveIn(new BasicArmy(9,p1));

    assertEquals(1,theMap.getTerritories().get(0).getUnitsSize());
    assertEquals(3,theMap.getTerritories().get(0).getUnitAmount(0));
    assertTrue(theMap.getTerritories().get(0).isOwner(p1));
    assertEquals(3,theMap.getTerritories().get(0).getPlayerMovableUnitAmount(theMap.getTerritories().get(0).getOwner()));
  }

  @Test
  public void testIsOwnerAndHasOwner() {
    Territory territory = new BasicTerritory("a");
    Player player = new TextPlayer("p");
    assertFalse(territory.isOwner(player));
    Territory territory2 = new BasicTerritory("b", player);
    assertTrue(territory2.isOwner(player));
    assertEquals(player, territory2.getOwner());
    assertEquals(null, territory.getOwner());
  }

  @Test
  public void testGetOwnerUnitAmount() {
    Territory territory = new BasicTerritory("a");
    Player p1 = new TextPlayer("p1");
    Player p2 = new TextPlayer("p2");
    Army army1 = new BasicArmy(6, p1);
    Army army2 = new BasicArmy(5, p2);
    assertEquals(0, territory.getPlayerMovableUnitAmount(territory.getOwner()));
    territory.moveIn(army1);
    territory.moveIn(army2);

    assertEquals(6, territory.getPlayerMovableUnitAmount(territory.getOwner()));
    territory.setOwner(p2);
    assertEquals(5, territory.getPlayerMovableUnitAmount(territory.getOwner()));
  }
  @Test
  public void testAddOne() {
    Territory territory = new BasicTerritory("a");
    Player p1 = new TextPlayer("p1");
    Army army1 = new BasicArmy(6, p1);
    territory.moveIn(army1);
    territory.addOne();

    assertEquals(7, territory.getUnitAmount(0));
  }

  @Test
  public void testDistance() {
    Territory t1 = new BasicTerritory("a");
    Territory t2 = new BasicTerritory("b");
    Territory t3 = new BasicTerritory("c");
    Territory t4 = new BasicTerritory("d");
    t1.setDistance(t2, 6);
    t2.setDistance(t1, 6);
    t1.setDistance(t3, 4);
    t3.setDistance(t1, 4);
    t1.addAdjacent(t2);
    t2.addAdjacent(t1);
    t1.addAdjacent(t3);
    t3.addAdjacent(t1);
    assertEquals(6, t2.getDistance(t1));
    assertEquals(6, t1.getDistance(t2));
    assertEquals(2, t1.getDistances().size());
  }
}

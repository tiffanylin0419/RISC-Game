package edu.duke.team8.riskgame;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class BasicTerritoryTest {
  @Test
  public void test_Constructor() {
    String s = "AbcdE";
    Territory t = new BasicTerritory(s);
    assertEquals(t.getName(), "AbcdE");
    return;

  }

  @Test
  public void test_equals() {
    String s = "AbcdE";
    Territory t1 = new BasicTerritory(s);
    Territory t2 = new BasicTerritory(s);
    assertTrue(t1.equals(t2));
    assertFalse(t1.equals(s));
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
    Territory t = new BasicTerritory(s,p1);
    assertTrue(t.isOwner(p1));
    assertTrue(p1.containsTerritory(t));
    Player p2=new TextPlayer("blue");
    t.changeOwner(p2);
    assertTrue(t.isOwner(p2));
    assertFalse(t.isOwner(p1));
    assertFalse(p1.containsTerritory(t));
    assertTrue(p2.containsTerritory(t));
  }

  @Test
  public void test_addAdjacency(){
    Territory t1 = new BasicTerritory("s1");
    Territory t2 = new BasicTerritory("s2");
    Territory t3 = new BasicTerritory("s3");
    
    t1.addAdjacent(t2);
    assertTrue(t1.isAdjacentEnemy(t2));
    assertFalse(t1.isAdjacentEnemy(t3));
    t1.addAdjacent(t3);
    assertTrue(t1.isAdjacentEnemy(t3));
  }

  @Test
  public void test_isAdjacentSelf(){
    Player p1=new TextPlayer("red");
    Player p2=new TextPlayer("blue");

    Map map = new Game1Map();
    Territory territories[]={new BasicTerritory("a",p1),new BasicTerritory("b",p1),new BasicTerritory("c",p1),new BasicTerritory("d",p2),new BasicTerritory("e",p1)};
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
    Player p3=new TextPlayer("green");
    Territory territory=new BasicTerritory("a",p1);
    //move in
    territory.moveIn(new BasicUnit(5,p1));
    territory.moveIn(new BasicUnit(3,p1));
    assertEquals(8,territory.getUnitAmount(0));
    territory.moveIn(new BasicUnit(3,p2));
    assertEquals(8,territory.getUnitAmount(0));
    assertEquals(3,territory.getUnitAmount(1));
    //move out
    territory.tryMoveOut(new BasicUnit(1,p2));
    assertEquals(8,territory.getUnitAmount(0));
    assertEquals(2,territory.getUnitAmount(1));
    assertFalse(territory.tryMoveOut(new BasicUnit(9,p1)));
    territory.tryMoveOut(new BasicUnit(8,p1));
    assertFalse(territory.tryMoveOut(new BasicUnit(1,p3)));
    assertEquals(2,territory.getUnitAmount(0));
    assertTrue(territory.isOwner(p1));
  }
  
  @Test
  public void test_attack2(){
    Player p1=new TextPlayer("red");
    Player p2=new TextPlayer("blue");
    Territory territory=new BasicTerritory("a",p1);
    //p1 3,p2 2
    territory.moveIn(new BasicUnit(3,p1));
    territory.moveIn(new BasicUnit(2,p2));
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
      territory.moveIn(new BasicUnit(4,p1));
      territory.moveIn(new BasicUnit(0,p2));
      territory.moveIn(new BasicUnit(4,p3));
      territory.moveIn(new BasicUnit(1,p4));
      territory.moveIn(new BasicUnit(4,p5));
      territory.attack();
      assertEquals(1,territory.getUnitsSize());
      assertEquals(0,territory.getUnitAmount(1));
      //might be wrong but less likely
      //assertTrue(territory.isOwner(p1)||territory.isOwner(p3)||territory.isOwner(p5));
  }
}

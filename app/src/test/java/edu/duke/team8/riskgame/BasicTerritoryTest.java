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
}

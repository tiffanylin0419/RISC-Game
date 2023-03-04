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
    Territory t2 = new BasicTerritory(s);
    t2.addOwner(p);
    assertTrue(t2.hasOwner());
    assertEquals(p,t2.getOwner());
  }

  @Test
  public void test_changeOwner(){
    String s = "AbcdE";
    Player p1=new TextPlayer("red");
    Territory t = new BasicTerritory(s);
    t.addOwner(p1);
    assertEquals(p1,t.getOwner());
    assertTrue(p1.containsTerritory(t));
    Player p2=new TextPlayer("blue");
    t.changeOwner(p2);
    assertEquals(p2,t.getOwner());
    assertNotEquals(p1, t.getOwner());
    assertFalse(p1.containsTerritory(t));
    assertTrue(p2.containsTerritory(t));
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
}

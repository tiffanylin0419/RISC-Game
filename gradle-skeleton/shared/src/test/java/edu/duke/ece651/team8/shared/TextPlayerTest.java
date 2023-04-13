package edu.duke.ece651.team8.shared;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class TextPlayerTest {
  @Test
  public void test_getColor() {
    Player p=new TextPlayer("red");
    assertEquals(p.getColor(),"red");
  }
  @Test
  public void test_setColor() {
      Player p = new TextPlayer("red");
      p.setColor("blue");
      assertEquals(p.getColor(),"blue");
  }

  @Test
  public void test_unitMax(){
    Player p=new TextPlayer("red");
    assertEquals(0,p.getUnitMax());
    p.setUnitMax(3);
    assertEquals(3,p.getUnitMax());
  }
  
  @Test
  public void test_addTerritory() {
    Player p=new TextPlayer("red");
    String names[]={"Akingdom","Bkingdom","Ckingdom"};
    for(String name: names){
      p.addTerritory(new BasicTerritory(name));
    }
    for(String name: names){
      assertTrue(p.containsTerritory(new BasicTerritory(name)));
    }
     assertFalse(p.containsTerritory(new BasicTerritory("Dkingdom")));
  }

  @Test
  public void test_tryRemoveTerritory() {
     Player p=new TextPlayer("red");
     String names[]={"Akingdom","Bkingdom","Ckingdom"};
     for(String name: names){
       p.addTerritory(new BasicTerritory(name));
     }
     assertTrue(p.tryRemoveTerritory(new BasicTerritory(names[0])));
     assertFalse(p.tryRemoveTerritory(new BasicTerritory(names[0])));
     assertTrue(p.tryRemoveTerritory(new BasicTerritory(names[1])));
     assertFalse(p.tryRemoveTerritory(new BasicTerritory("Dkingdom")));
  }

  @Test
    public void test_display(){
      Player p=new TextPlayer("red");
      assertNull(p.display());
  }
}

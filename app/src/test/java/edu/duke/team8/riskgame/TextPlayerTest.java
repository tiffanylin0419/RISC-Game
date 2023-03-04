package edu.duke.team8.riskgame;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class TextPlayerTest {
  @Test
  public void test_getColor() {
    Player p=new TextPlayer("red");
    assertEquals(p.getColor(),"red");
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

}

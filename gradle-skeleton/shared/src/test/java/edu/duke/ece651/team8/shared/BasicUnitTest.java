package edu.duke.ece651.team8.shared;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class BasicUnitTest {
  @Test
  public void test_add() {
    Player player=new TextPlayer("red");
    Unit unit=new BasicUnit(0,player);
    unit.add(2);
    assertEquals(2,unit.getAmount());
    unit.addOne();
    assertEquals(3,unit.getAmount());
    assertEquals(player,unit.getOwner());
    unit.remove(2);
    assertEquals(1,unit.getAmount());
    assertTrue(unit.isSurvive());
    unit.removeOne();
    assertFalse(unit.isSurvive());
    
    int roll=unit.doRoll();
    assertTrue(roll<20 && 0<=roll);
  }

}

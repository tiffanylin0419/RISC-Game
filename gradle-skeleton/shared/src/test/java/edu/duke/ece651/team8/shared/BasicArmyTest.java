package edu.duke.ece651.team8.shared;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class BasicArmyTest {
  @Test
  public void test_add() {
    Player player=new TextPlayer("red");
    Army army =new BasicArmy(0,player);
    army.add(2);
    assertEquals(2, army.getAmount());
    army.addOne();
    assertEquals(3, army.getAmount());
    assertEquals(player, army.getOwner());
    army.remove(2);
    assertEquals(1, army.getAmount());
    assertTrue(army.isSurvive());
    army.removeOne();
    assertFalse(army.isSurvive());
    
    int roll= army.doRoll();
    assertTrue(roll<20 && 0<=roll);
  }

}

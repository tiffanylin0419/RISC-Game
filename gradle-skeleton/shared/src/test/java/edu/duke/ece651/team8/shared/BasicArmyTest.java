package edu.duke.ece651.team8.shared;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import java.util.List;

public class BasicArmyTest {

  @Test
  public void test_add() {
    UnitFactory uf = new UnitFactory();
    Player player =new TextPlayer("red");
    Army army =new BasicArmy(2, player);
    assertEquals(2, army.getAmount());
    Unit u = new BasicUnit();
    army.addOne(u);
    assertEquals(3, army.getAmount());
    assertEquals(player, army.getOwner());
    army.remove(uf.makeBasicUnits(2));
    assertEquals(1, army.getAmount());
    assertTrue(army.isSurvive());
    army.removeOne(u);
    assertFalse(army.isSurvive());
    
    int roll= army.doRoll();
    assertTrue(roll<20 && 0<=roll);
  }

}

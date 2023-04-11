package edu.duke.ece651.team8.shared;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class EvolvableArmyTest {
    @Test
    public void test_constructor() {
        UnitFactory uf = new UnitFactory();
        Player player =new TextPlayer("red");
        List<Unit> ulist = uf.makeBasicUnits(3);
        Army army =new EvolvableArmy(player, ulist);
        assertEquals(3, army.getAmount());
        Army army1 =new EvolvableArmy(4, player);;
        assertEquals(4, army1.getAmount());
    }
}
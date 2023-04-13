package edu.duke.ece651.team8.shared;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UnitFactoryTest {
    @Test
    public void testMakeAdvancedUnit() {
        UnitFactory uf = new UnitFactory();
        List<Unit> uList = uf.makeAdvancedUnits(2, 2);
        for(Unit u : uList) {
            assertEquals(2, u.getLevel());
        }
    }
    @Test
    public void testCanUpgradeTo() {
        UnitFactory uf = new UnitFactory();
        Unit u = new BasicUnit();
        assertEquals(11, uf.canUpgradeTo(2, u));
    }
}
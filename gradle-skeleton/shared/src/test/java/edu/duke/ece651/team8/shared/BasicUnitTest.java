package edu.duke.ece651.team8.shared;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BasicUnitTest {
    @Test
    public void testUpgrade() {
        Unit u = new BasicUnit();
        assertEquals(0, u.getLevel());
        assertEquals("Servant", u.getType());
        assertEquals(3, u.getUpgradeCost());
        Unit u1 = u.upgrade();
        assertEquals(1, u1.getLevel());
        assertEquals("Assassin", u1.getType());
        assertEquals(8, u1.getUpgradeCost());
        Unit u2 = u1.upgrade();
        assertEquals(2, u2.getLevel());
        assertEquals("Caster", u2.getType());
        assertEquals(19, u2.getUpgradeCost());
        Unit u3 = u2.upgrade();
        assertEquals(3, u3.getLevel());
        assertEquals("Rider", u3.getType());
        assertEquals(25, u3.getUpgradeCost());
    }

}
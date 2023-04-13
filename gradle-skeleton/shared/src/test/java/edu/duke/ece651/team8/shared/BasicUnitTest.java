package edu.duke.ece651.team8.shared;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class BasicUnitTest {
    @Test
    public void testUpgrade() {
        Unit u = new BasicUnit();
        assertEquals(0, u.getLevel());
        assertEquals("Servant", u.getType());
        assertEquals(3, u.getUpgradeCost());
        assertEquals(true, u.equals(new BasicUnit()));
        assertEquals(0, u.getBonus());
        Unit u1 = u.upgrade();
        assertEquals(1, u1.getLevel());
        assertEquals("Assassin", u1.getType());
        assertEquals(8, u1.getUpgradeCost());
        assertEquals(true, u1.equals(u.upgrade()));
        assertEquals(1, u1.getBonus());
        assertEquals(1, u1.compareTo(u));
        Unit u2 = u1.upgrade();
        assertEquals(2, u2.getLevel());
        assertEquals("Rider", u2.getType());
        assertEquals(19, u2.getUpgradeCost());
        assertEquals(true, u2.equals(u1.upgrade()));
        assertEquals(3, u2.getBonus());
        Unit u3 = u2.upgrade();
        assertEquals(3, u3.getLevel());
        assertEquals("Lancer", u3.getType());
        assertEquals(25, u3.getUpgradeCost());
        assertEquals(true, u3.equals(u2.upgrade()));
        assertEquals(5, u3.getBonus());
        Unit u4 = u3.upgrade();
        assertEquals(4, u4.getLevel());
        assertEquals("Archer", u4.getType());
        assertEquals(35, u4.getUpgradeCost());
        assertEquals(true, u4.equals(u3.upgrade()));
        assertEquals(8, u4.getBonus());
        Unit u5 = u4.upgrade();
        assertEquals(5, u5.getLevel());
        assertEquals("Berserker", u5.getType());
        assertEquals(50, u5.getUpgradeCost());
        assertEquals(true, u5.equals(u4.upgrade()));
        assertEquals(11, u5.getBonus());
        Unit u6 = u5.upgrade();
        assertEquals(6, u6.upgrade().getLevel());
        assertEquals(6, u6.getLevel());
        assertEquals("Saber", u6.getType());
        assertEquals(0, u6.getUpgradeCost());
        assertEquals(true, u6.equals(u5.upgrade()));
        assertEquals(15, u6.getBonus());

        assertEquals(3, u2.compareTo(u));
        assertEquals(-6, u3.compareTo(u5));
        assertEquals(7, u4.compareTo(u1));
        assertEquals(11, u5.compareTo(u));
        assertEquals(15, u6.compareTo(u));
        assertEquals(-8, u.compareTo(u4));
    }


//    @Disabled
    @Test
    public void testCompareTo(){
        Map map = new Game1Map();
        Territory territory1 = new BasicTerritory("a");
        Territory territory2 = new BasicTerritory("b");
        map.addTerritory(territory1);
        map.addTerritory(territory2);

        Player player1 = new TextPlayer("p1");
        Player player2 = new TextPlayer("p2");
        Player player3 = new TextPlayer("p3");
        territory1.moveIn(new EvolvableArmy(20, player1));
        map.doCombats();
        map.getOutcome();
        assertEquals(21, territory1.getUnitAmount(0));
        Army player2Army = new EvolvableArmy(6, player2);
        Unit UpgradedPlayer = player2Army.getList().get(3).upgrade().upgrade().upgrade().upgrade().upgrade().upgrade();
        player2Army.getList().set(3,UpgradedPlayer);
        System.out.println(player2Army.getList().get(3));
        territory1.moveIn(player2Army);
        List<Unit> listOfUnits = new ArrayList<>();
        for(int i = 0; i < 5000;i ++){
            listOfUnits.add(new LevelThreeUnit());
        }

        territory1.moveIn(new EvolvableArmy(1000, player2));
        territory1.moveIn(new EvolvableArmy(player3,listOfUnits));
        map.doCombats();
        assertEquals("Player p3 wins combat in a\n", map.getOutcome());


    }

    @Test
    public void testEquals(){
        Unit u0 = new BasicUnit();
        Unit u1 = new LevelOneUnit();
        Unit u2 = new LevelTwoUnit();
        Unit u3 = new LevelThreeUnit();
        Unit u4 = new LevelFourUnit();
        Unit u5 = new LevelFiveUnit();
        Unit u6 = new LevelSixUnit();
        assertNotEquals(u0,u1);
        assertNotEquals(u1,u2);
        assertNotEquals(u2,u3);
        assertNotEquals(u3,u4);
        assertNotEquals(u4,u5);
        assertNotEquals(u5,u6);
        assertNotEquals(u6,u0);
    }

}
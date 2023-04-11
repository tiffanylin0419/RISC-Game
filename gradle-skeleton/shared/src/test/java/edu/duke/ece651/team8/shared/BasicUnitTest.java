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

    @Disabled
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
        territory1.moveIn(new BasicArmy(20, player1));
        map.doCombats();
        map.getOutcome();
        assertEquals(21, territory1.getUnitAmount(0));
        Army player2Army = new BasicArmy(4, player2);
        Unit UpgradedPlayer = player2Army.getList().get(3).upgrade().upgrade().upgrade();
        player2Army.getList().set(3,UpgradedPlayer);
        System.out.println(player2Army.getList().get(3));
        territory1.moveIn(player2Army);
        List<Unit> listOfUnits = new ArrayList<>();
        for(int i = 0; i < 5000;i ++){
            listOfUnits.add(new LevelThreeUnit());
        }

        territory1.moveIn(new BasicArmy(1000, player2));
        territory1.moveIn(new BasicArmy(player3,listOfUnits));
        map.doCombats();
        assertEquals("Player p3 wins combat in a\n", map.getOutcome());


    }

}
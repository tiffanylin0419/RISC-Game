package edu.duke.ece651.team8.shared;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class AttackMovableActionTest {

    @Test
    void test_isValidSource_Destination_Path() {
        V1MapFactory factory = new V1MapFactory();
        Game1Map map = factory.createMap(4);
        ArrayList<Player> players =factory.createPlayers(4,map);

        ArrayList<Territory> territories=map.getTerritories();
        Territory s=territories.get(0);
        Territory d=territories.get(7);
        s.moveIn(new BasicArmy(4, players.get(0)));
        d.moveIn(new BasicArmy(5, players.get(1)));

        MovableAction action1=new AttackAction(players.get(0),"a1","b2",3,map);
        assertTrue(action1.isValidSource());

        MovableAction action2=new AttackAction(players.get(1),"a1","b2",3,map);
        assertFalse(action2.isValidSource());

        MovableAction action3=new AttackAction(players.get(1),"a1","b2",3,map);
        assertFalse(action3.isValidDestination());

        MovableAction action4=new AttackAction(players.get(0),"a1","b2",3,map);
        assertTrue(action4.isValidDestination());
        assertFalse(action4.isValidPath());


        MovableAction action5=new AttackAction(players.get(0),"a2","b2",3,map);
        assertTrue(action5.isValidPath());

        MovableAction action6=new AttackAction(players.get(0),"a2","b3",3,map);
        assertFalse(action6.isValidPath());
    }

    @Test
    void test_doAction() {
        V1MapFactory factory = new V1MapFactory();
        Game1Map map = factory.createMap(4);
        ArrayList<Player> players =factory.createPlayers(4,map);

        ArrayList<Territory> territories=map.getTerritories();
        Territory s=territories.get(0);
        Territory d=territories.get(6);
        //a1,b2
        s.moveIn(new BasicArmy(4, players.get(0)));
        d.moveIn(new BasicArmy(5, players.get(1)));

        MovableAction action1=new AttackAction(players.get(0),"a1","b1",3,map);
        assertNull(map.getMovableRuleChecker().checkAllRule(action1));
        action1.doAction();
        assertEquals(1,s.getUnitAmount(0));
        assertEquals(5,d.getUnitAmount(0));
        assertEquals(3,d.getUnitAmount(1));

        MovableAction action2=new AttackAction(players.get(0),"a1","b1",1,map);
        assertNull(map.getMovableRuleChecker().checkAllRule(action2));
        action2.doAction();
        assertEquals(0,s.getUnitAmount(0));
        s.attack();
        assertTrue(s.isOwner(players.get(0)));
        assertEquals(5,d.getUnitAmount(0));
        assertEquals(4,d.getUnitAmount(1));
    }
}
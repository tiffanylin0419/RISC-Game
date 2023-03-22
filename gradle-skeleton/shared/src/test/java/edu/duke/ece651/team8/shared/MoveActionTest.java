package edu.duke.ece651.team8.shared;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

class MoveActionTest {
    @Test
    public void test_doAction() {
        V1MapFactory factory = new V1MapFactory();
        Game1Map map = factory.createMap(4);
        ArrayList<Player> players=factory.createPlayers(4,map);

        ArrayList<Territory> territories=map.getTerritories();
        Territory s=territories.get(0);
        Territory d=territories.get(4);
        //a1,b2
        s.moveIn(new BasicUnit(4,players.get(0)));
        d.moveIn(new BasicUnit(5,players.get(0)));

        Action action1=new MoveAction(players.get(0),"a1","a5",3,map);
        assertNull(map.getChecker().checkAllRule(action1));
        action1.doAction();
        assertEquals(1,s.getUnitAmount(0));
        assertEquals(8,d.getUnitAmount(0));

        Action action2=new MoveAction(players.get(0),"a1","a5",1,map);
        assertNull(map.getChecker().checkAllRule(action2));
        action2.doAction();
        assertEquals(0,s.getUnitAmount(0));
        s.attack();
        assertFalse(s.isOwner(players.get(0)));
        assertEquals(9,d.getUnitAmount(0));
    }

    @Test
    void test_isValidSource_Destination_Path(){
        V1MapFactory factory = new V1MapFactory();
        Game1Map map = factory.createMap(4);
        ArrayList<Player> players=factory.createPlayers(4,map);

        ArrayList<Territory> territories=map.getTerritories();
        Territory s=territories.get(0);
        Territory d=territories.get(7);
        s.moveIn(new BasicUnit(4,players.get(0)));
        d.moveIn(new BasicUnit(5,players.get(1)));

        Action action1=new MoveAction(players.get(0),"a1","b2",3,map);
        assertTrue(action1.isValidSource());

        Action action2=new MoveAction(players.get(1),"a1","b2",3,map);
        assertFalse(action2.isValidSource());

        Action action3=new MoveAction(players.get(1),"a1","b2",3,map);
        assertTrue(action3.isValidDestination());

        Action action4=new MoveAction(players.get(0),"a1","b2",3,map);
        assertFalse(action4.isValidDestination());
        assertFalse(action4.isValidPath());


        Action action5=new MoveAction(players.get(0),"a2","a3",3,map);
        action5.getSource().moveIn(new BasicUnit(4,players.get(0)));
        action5.getDestination().moveIn(new BasicUnit(6,players.get(0)));
        assertTrue(action5.isValidPath());
    }
}
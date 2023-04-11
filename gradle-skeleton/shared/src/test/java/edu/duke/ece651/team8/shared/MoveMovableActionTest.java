package edu.duke.ece651.team8.shared;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class MoveMovableActionTest {
    @Disabled
    @Test
    public void test_doAction() {
        V1MapFactory factory = new V1MapFactory();
        Game1Map map = factory.createMap(4);
        ArrayList<Player> players =factory.createPlayers(4,map);

        ArrayList<Territory> territories=map.getTerritories();
        Territory s=territories.get(0);
        Territory d=territories.get(4);
        //a1,b2
        s.moveIn(new BasicArmy(4, players.get(0)));
        d.moveIn(new BasicArmy(5, players.get(0)));

        MovableAction action1=new MoveAction(players.get(0),"a1","a5",3,map);
        assertEquals("a1",action1.getSourceText());
        assertEquals("a5",action1.getDestinationText());
        assertNull(map.getMovableChecker().checkAllRule(action1));
        action1.doAction();
        assertEquals(1,s.getUnitAmount(0));
        assertEquals(8,d.getUnitAmount(0));

        MovableAction action2=new MoveAction(players.get(0),"a1","a5",1,map);
        assertNull(map.getMovableChecker().checkAllRule(action2));
        action2.doAction();
        assertEquals(0,s.getUnitAmount(0));
        s.attack();
        assertTrue(s.isOwner(players.get(0)));
        assertEquals(9,d.getUnitAmount(0));
    }

    @Test
    void test_isValidSource_Destination_Path(){
        V1MapFactory factory = new V1MapFactory();
        Game1Map map = factory.createMap(4);
        ArrayList<Player> players =factory.createPlayers(4,map);

        ArrayList<Territory> territories=map.getTerritories();
        Territory s=territories.get(0);
        Territory d=territories.get(7);
        s.moveIn(new BasicArmy(4, players.get(0)));
        d.moveIn(new BasicArmy(5, players.get(1)));

        MovableAction action1=new MoveAction(players.get(0),"a1","b2",3,map);
        assertTrue(action1.isValidSource());

        MovableAction action2=new MoveAction(players.get(1),"a1","b2",3,map);
        assertFalse(action2.isValidSource());

        MovableAction action3=new MoveAction(players.get(1),"a1","b2",3,map);
        assertTrue(action3.isValidDestination());

        MovableAction action4=new MoveAction(players.get(0),"a1","b2",3,map);
        assertFalse(action4.isValidDestination());
        assertFalse(action4.isValidPath());


        MovableAction action5=new MoveAction(players.get(0),"a2","a3",3,map);
        action5.getSource().moveIn(new BasicArmy(4, players.get(0)));
        action5.getDestination().moveIn(new BasicArmy(6, players.get(0)));
        assertTrue(action5.isValidPath());
    }
}
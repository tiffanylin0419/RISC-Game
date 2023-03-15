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
        Territory d=territories.get(7);
        //a1,b2
        s.moveIn(new BasicUnit(4,players.get(0)));
        d.moveIn(new BasicUnit(5,players.get(1)));

        Action action1=new MoveAction(players.get(0),"a1","b2",3);
        //assume it is checked already
        action1.doAction(map);
        assertEquals(1,s.getUnitAmount(0));
        assertEquals(5,d.getUnitAmount(0));
        assertEquals(3,d.getUnitAmount(1));

        Action action2=new MoveAction(players.get(0),"a1","b2",1);
        //assume it is checked already
        action2.doAction(map);
        assertEquals(0,s.getUnitAmount(0));
        s.attack();
        assertEquals(false,s.isOwner(players.get(0)));
        assertEquals(5,d.getUnitAmount(0));
        assertEquals(4,d.getUnitAmount(1));
    }
}
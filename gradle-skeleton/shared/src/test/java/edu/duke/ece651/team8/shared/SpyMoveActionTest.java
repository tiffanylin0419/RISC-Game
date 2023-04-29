package edu.duke.ece651.team8.shared;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
public class SpyMoveActionTest {
    @Test
    public void testDoAction() {
        V1MapFactory factory = new V1MapFactory();
        Game1Map map = factory.createMap(4);
        ArrayList<Player> players = factory.createPlayers(4,map);

        ArrayList<Territory> territories = map.getTerritories();
        Territory source=territories.get(0);
        Territory destination=territories.get(7);
        SpyArmy army1 = new SpyArmy(6, players.get(0));
        SpyArmy army2 = new SpyArmy(6, players.get(1));
        source.moveIn(army1);
        destination.moveIn(army2);
        MovableAction action = new SpyMoveAction(players.get(0),"a1","b1",1, map);
        assertEquals(0, action.numberOfMovableUnits());
        assertEquals(true, action.hasEnoughFood());
        assertEquals(true, action.isValidPath());
        assertEquals(true, action.isValidSource());
        assertEquals(true, action.isValidDestination());
        action.doAction();
        assertEquals(true, action.hasEnoughFood());
    }

    private ArrayList<Territory> createTerritories() {

        Territory t1 = new ResourceTerritory("a");
        Territory t2 = new ResourceTerritory("b");
        Territory t3 = new ResourceTerritory("c");
        Territory t4 = new ResourceTerritory("d");
        Player player = new Player("Green");
        Player player2 = new Player("Red");

        t1.setOwner(player);
        t2.setOwner(player);
        t3.setOwner(player);
        t4.setOwner(player2);

        t1.addAdjacent(t2);
        t1.addAdjacent(t3);
        t2.addAdjacent(t3);
        t1.addAdjacent(t4);
        t3.addAdjacent(t4);

        t1.setDistance(t2, 3);
        t2.setDistance(t1, 3);
        t1.setDistance(t3, 7);
        t3.setDistance(t1, 7);
        t2.setDistance(t3, 3);
        t3.setDistance(t2, 3);
        t1.setDistance(t4, 2);
        t4.setDistance(t1, 2);
        t3.setDistance(t4, 2);
        t4.setDistance(t3, 2);

        ArrayList<Territory> testMap = new ArrayList<>();
        testMap.add(t1);
        testMap.add(t2);
        testMap.add(t3);
        testMap.add(t4);
        return testMap;
    }
}

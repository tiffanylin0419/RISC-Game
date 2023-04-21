package edu.duke.ece651.team8.shared;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class MoveMovableActionTest {
//    @Disabled
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
        assertEquals("Do not have enough food", map.getMovableRuleChecker().checkAllRule(action1));

        assertEquals(4,s.getUnitAmount(0));
        assertEquals(5,d.getUnitAmount(0));

        players.get(0).addFoodResource(3000);
        MovableAction action2=new MoveAction(players.get(0),"a1","a5",1,map);
        System.out.println(players.get(0).getFoodAmount());
        assertNull(map.getMovableRuleChecker().checkAllRule(action2));
        action2.doAction();
        assertEquals(3,s.getUnitAmount(0));
        s.attack();
        assertTrue(s.isOwner(players.get(0)));
        assertEquals(6,d.getUnitAmount(0));
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

    @Test
    public void testIsValidPath_IsolatedTerritory() {
        ArrayList<Territory> gameMap = createIsolatedTerritories();
        Map theMap = new Game1Map(gameMap);
        MinimumPath path = new MinimumPath(gameMap.get(0).getOwner(), theMap);
        int distance = path.findMinPath(theMap.getTerritories().get(0), theMap.getTerritories().get(2));
        gameMap.get(0).moveIn(new BasicArmy(6, gameMap.get(0).getOwner()));
        MovableAction action = new MoveAction(gameMap.get(0).getOwner(), "a", "c", 4, theMap);
        MovableAction action2 = new MoveAction(gameMap.get(0).getOwner(), "a", "a", 4, theMap);
        assertTrue(action2.isValidPath());
        assertFalse(action.isValidPath());
    }



    private ArrayList<Territory> createTerritories() {
        Territory t1 = new ResourceTerritory("a");
        Territory t2 = new ResourceTerritory("b");
        Territory t3 = new ResourceTerritory("c");
        Territory t4 = new ResourceTerritory("d");
        Player player = new Player("Green");
        Player player2 = new Player("Yellow");

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

    private ArrayList<Territory> createIsolatedTerritories() {
        Territory t1 = new ResourceTerritory("a");
        Territory t2 = new ResourceTerritory("b");
        Territory t3 = new ResourceTerritory("c");
        Territory t4 = new ResourceTerritory("d");
        Player player = new Player("Green");
        Player player2 = new Player("Yellow");

        t1.setOwner(player);
        t2.setOwner(player2);
        t3.setOwner(player);
        t4.setOwner(player2);

        t1.addAdjacent(t2);
        t2.addAdjacent(t3);
        t1.addAdjacent(t4);
        t3.addAdjacent(t4);

        t1.setDistance(t2, 3);
        t2.setDistance(t1, 3);
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
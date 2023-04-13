package edu.duke.ece651.team8.shared;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

public class MinimumPathTest {

    @Test
    public void testFindMinPath_NotTheSameOwner() {
        ArrayList<Territory> gameMap = createTerritories();
        Map theMap = new Game1Map(gameMap);
        MinimumPath path = new MinimumPath(gameMap.get(0).getOwner(), theMap);
        assertEquals(4, theMap.getTerritories().size());
        int distance = path.findMinPath(theMap.getTerritories().get(0), theMap.getTerritories().get(3));
        assertEquals(Integer.MAX_VALUE, distance);
    }


    @Test
    public void testFindNextTerritory() {
        ArrayList<Territory> gameMap = createTerritories();
        Territory source = gameMap.get(0);
        HashSet<Territory> visited = new HashSet<>();
        visited.add(source);
        HashMap<Territory, Integer> distances = new HashMap<>();
        for (Territory territory : source.getDistances().keySet()) {
            distances.put(territory, source.getDistance(territory));
        }
        assertEquals(3, source.getDistances().size());
        Map theMap = new Game1Map();
        MinimumPath path = new MinimumPath(source.getOwner(), theMap);
        Territory target = path.findNextTerritory(visited, distances);
        assertEquals(gameMap.get(1), target);
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

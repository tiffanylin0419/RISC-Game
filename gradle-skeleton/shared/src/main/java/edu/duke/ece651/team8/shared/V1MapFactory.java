package edu.duke.ece651.team8.shared;

import java.util.*;

public class V1MapFactory implements AbstractMapFactory {
//    private ArrayList<Territory> territories;

    private final int territoryAmount;

//    private ArrayList<ArrayList<Territory>> territoryGroups;
    private String[] territoryNameList;

    public V1MapFactory() {
        this.territoryAmount = 6;
        this.territoryNameList = new String[] {
                "a1", "a2", "a3", "a4", "a5", "a6",
                "b1", "b2", "b3", "b4", "b5", "b6",
                "c1", "c2", "c3", "c4", "c5", "c6",
                "d1", "d2", "d3", "d4", "d5", "d6"
        };
    }
    /**
     * create a Game1Map
     * @return
     */
    @Override
    public Game1Map createMap(int playerAmount) {
        ArrayList<Territory> territories = createTerritories(playerAmount);
        connectAdjacentTerritory(playerAmount, territories);
        ArrayList<ArrayList<Territory>> territoryGroups = new ArrayList<>();
        createTerritoryGroups(playerAmount, territoryGroups);
        separateTerritoriesToGroups(territoryGroups, territories, playerAmount);
        return new Game1Map(territoryGroups);
    }

    /**
     * create n territory groups (n equals to playerAmount)
     */
    private void createTerritoryGroups(int playerAmount, ArrayList<ArrayList<Territory>> territoryGroups) {
        for (int i = 0; i < playerAmount; ++i) {
            territoryGroups.add(new ArrayList<>());
        }
    }

    private ArrayList<Territory> createTerritories(int playerAmount) {
        ArrayList<Territory> territories = new ArrayList<>();
        for (int i = 0; i < playerAmount * 6; ++i) {
            Territory t = new BasicTerritory(this.territoryNameList[i]);
            territories.add(t);
        }
        return territories;
    }

    private void connectAdjacentTerritory(int playerAmount, ArrayList<Territory> territories) {
        for (int i = 0; i < playerAmount; ++i) {
            for (int j = 0; j < 6; ++j) {
                Territory t = territories.get(i * 6 + j);
                if (i != playerAmount - 1) {
                    Territory adj = territories.get((i + 1) * 6 + j);
                    t.addAdjacent(adj);
                }
                if (j % 6 != 5) {
                    Territory adj = territories.get(i * 6 + j + 1);
                    t.addAdjacent(adj);
                }
            }
        }
    }

    /**
     * separate territories as groups
     */
    public void separateTerritoriesToGroups(ArrayList<ArrayList<Territory>> territoryGroups, ArrayList<Territory> territories, int playerAmount) {
        int index = 0;
        for (int num = 0; num < playerAmount; ++num) {
            for (int i = 0; i < territoryAmount; ++i) {
                territoryGroups.get(num).add(territories.get(index));
                ++index;
            }
        }
    }
}

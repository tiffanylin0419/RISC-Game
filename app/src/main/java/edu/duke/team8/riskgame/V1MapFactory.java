package edu.duke.team8.riskgame;

import java.util.*;

public class V1MapFactory implements AbstractMapFactory {
//    private ArrayList<Territory> territories;

    private final int territoryAmount = 24;

//    private final int playerAmount;

//    private ArrayList<ArrayList<Territory>> territoryGroups;
    private  final String[] territoryNameList = {
            "a1", "a2", "a3", "a4", "a5", "a6",
            "b1", "b2", "b3", "b4", "b5", "b6",
            "c1", "c2", "c3", "c4", "c5", "c6",
            "d1", "d2", "d3", "d4", "d5", "d6"
    };

    /**
     * create a Game1Map
     * @return
     */
    @Override
    public Game1Map createMap(int playerAmount) {
        ArrayList<Territory> territories = createTerritories();
        connectAdjacentTerritory(territories);
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

    private ArrayList<Territory> createTerritories() {
        ArrayList<Territory> territories = new ArrayList<>();
        for (int i = 0; i < this.territoryAmount; ++i) {
            Territory t = new BasicTerritory(this.territoryNameList[i]);
            territories.add(t);
        }
        return territories;
    }

    /**
     * connect territories to make one be adjacent to one other territory
     */
    private void connectAdjacentTerritory(ArrayList<Territory> territories) {
        for (int i = 0; i < 4; ++i) {
            for (int j = 0; j < 6; ++j) {
                Territory t = territories.get(i * 4 + j);
                if (i % 4 != 3) {
                    Territory adj = territories.get((i + 1) * 4 + j);
                    t.addAdjacent(adj);
                }
                if (j % 6 != 5) {
                    Territory adj = territories.get(i * 4 + j + 1);
                    t.addAdjacent(adj);
                }
            }
        }
    }

    /**
     * separate territories as groups
     */
    public void separateTerritoriesToGroups(ArrayList<ArrayList<Territory>> territoryGroups, ArrayList<Territory> territories, int playerAmount) {
        int groups = territoryAmount / playerAmount;
        int index = 0;
        for (int num = 0; num < playerAmount; ++num) {
            for (int i = 0; i < groups; ++i) {
                territoryGroups.get(num).add(territories.get(index));
                ++index;
            }
        }
    }
}

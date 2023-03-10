package edu.duke.team8.riskgame;

import java.util.*;

public class V1MapFactory implements AbstractMapFactory {
    private ArrayList<Territory> territories;

    private final int territoryAmount = 24;

    private final int playerAmount;

    private ArrayList<ArrayList<Territory>> territoryGroups;
    private final String[] playerNameList = {"Green", "Red", "Blue", "Yellow"};
    private  final String[] territoryNameList = {
            "a1", "a2", "a3", "a4", "a5", "a6",
            "b1", "b2", "b3", "b4", "b5", "b6",
            "c1", "c2", "c3", "c4", "c5", "c6",
            "d1", "d2", "d3", "d4", "d5", "d6"
    };

//    private final BufferedReader inputReader;
//    private final PrintStream out;


    public V1MapFactory(int playerAmount) {
        this.territories = new ArrayList<>();
        this.playerAmount = playerAmount;
        this.createTerritories();
        this.territoryGroups = new ArrayList<>();
        createTerritoryGroups();
//        this.allocateTerritoriesToEachPlayer();
        connectAdjacentTerritory();
        separateTerritoriesToGroups();
    }

    /**
     * create a Game1Map
     * @return
     */
    @Override
    public Game1Map createMap() {
        Game1Map map = new Game1Map(this.territoryGroups);
        return map;
    }

//    /**
//     * create n players
//     */
//    private void createPlayers() {
//        for (int i = 0; i < this.playerAmount; ++i) {
//            players.add(new TextPlayer(playerNameList[i]));
//        }
//    }

    /**
     * create n territory groups (n equals to playerAmount)
     */
    private void createTerritoryGroups() {
        for (int i = 0; i < this.playerAmount; ++i) {
            territoryGroups.add(new ArrayList<>());
        }
    }

    /**
     * create 24 territories
     */
    private void createTerritories() {
        for (int i = 0; i < this.territoryAmount; ++i) {
            Territory t = new BasicTerritory(this.territoryNameList[i]);
            this.territories.add(t);
        }
    }

//    /**
//     * allocate m territories to each player (totally n players)
//     */
//    private void allocateTerritoriesToEachPlayer() {
//        Iterator<Territory> it = territories.iterator();
//        int groups = territoryNum / playerAmount;
//        for (Player player : players) {
//            for (int i = 0; i < groups; ++i) {
//                Territory territory = it.next();
//                player.addTerritory(territory);
//                territory.setOwner(player);
//            }
//        }
//    }

    /**
     * connect territories to make one be adjacent to one other territory
     */
    private void connectAdjacentTerritory() {
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
     * for testing
     * @return
     */
    public ArrayList<Territory> getTerritories() {
        return this.territories;
    }

    public ArrayList<ArrayList<Territory>> getTerritoryGroups() {
        return this.territoryGroups;
    }

    /**
     * separate territories as groups
     */
    public void separateTerritoriesToGroups() {
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

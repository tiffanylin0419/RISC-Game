package edu.duke.team8.riskgame;

import java.util.*;

public class V1MapFactory implements AbstractMapFactory {
    private ArrayList<Territory> territories;

    private final int territoryNum = 24;

    private final int playerAmount;
    private ArrayList<Player> players;
    private final String[] playerNameList = {"Green", "Red", "Blue", "Yellow"};
    private  final String[] territoryNameList = {
            "a1", "a2", "a3", "a4", "a5", "a6",
            "b1", "b2", "b3", "b4", "b5", "b6",
            "c1", "c2", "c3", "c4", "c5", "c6",
            "d1", "d2", "d3", "d4", "d5", "d6"
    };
//    private HashSet<TextPlayer> players;
//
//    private HashMap<TextPlayer, ArrayList<Territory>> map;
//
//    private int unitAmount;
//
//    private final BufferedReader inputReader;
//    private final PrintStream out;


    public V1MapFactory(int playerAmount) {
        this.territories = new ArrayList<>();
        this.playerAmount = playerAmount;
        this.players = new ArrayList<>();
        this.createPlayers();
        this.createTerritories();
        this.allocateTerritoriesToEachPlayer();
        this.connectAdjacentTerritory();
    }

    @Override
    public Game1Map createMap() {
        ArrayList<Territory> t = new ArrayList<>();
        for (Territory territory : this.territories) {
            t.add(territory);
        }
        return new Game1Map();
    }

    private void createPlayers() {
        for (int i = 0; i < this.playerAmount; ++i) {
            players.add(new TextPlayer(playerNameList[i]));
        }
    }

    /**
     * create 24 territories
     */
    private void createTerritories() {
        for (int i = 0; i < this.territoryNum; ++i) {
            this.territories.add(new BasicTerritory(territoryNameList[i]));
        }
    }
    private void allocateTerritoriesToEachPlayer() {
        Iterator<Territory> it = territories.iterator();
        int groups = territoryNum / playerAmount;
        for (Player player : players) {
            for (int i = 0; i < groups; ++i) {
                Territory territory = it.next();
                player.addTerritory(territory);
                territory.setOwner(player);
            }
        }
    }

    private void connectAdjacentTerritory() {
        for (int i = 0; i < 4; ++i) {
            for (int j = 0; j < 6; ++j) {
                if (i % 4 == 3 || j % 6 == 5) {
                    continue;
                } else {
                    Territory t = territories.get(i * 4 + j + 1);
                    territories.get(i * 4 + j).addAdjacent(t);
                    t = territories.get((i + 1) * 4 + j);
                    territories.get(i * 4 + j).addAdjacent(t);
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
}

package edu.duke.ece651.team8.shared;

import java.util.ArrayList;
import java.util.HashMap;

public class V2MapFactory implements AbstractMapFactory {
    private final int territoryAmount;

    private final String[] territoryNameList;

    private final int[] distanceList;

    private final String[] colors;

    public V2MapFactory() {
        this.territoryAmount = 6;
        this.territoryNameList = new String[] {
                "a1", "a2", "a3", "a4", "a5", "a6",
                "b1", "b2", "b3", "b4", "b5", "b6",
                "c1", "c2", "c3", "c4", "c5", "c6",
                "d1", "d2", "d3", "d4", "d5", "d6"
        };
        this.distanceList = new int[] {
                //
                2, 2, 3, 3, 4, 7, 2, 3, 4, 3, 2,
                3, 5, 2, 6, 5, 3, 5, 4, 1, 7, 3,
                4, 5, 6, 7, 3, 2, 4, 5, 5, 4, 2,
                1, 1, 5, 4, 7
        };
        this.colors= new String[]{ "Green", "Red", "Blue", "Yellow" };
    }

    /**
     *create a map for specific number of player
     * @param playerAmount total number of players to play the game
     * @return create a Game1Map
     */
    @Override
    public Game1Map createMap(int playerAmount) {
        Game1Map theMap=new Game1Map(createTerritories(playerAmount));
        connectAdjacentTerritory(playerAmount, theMap);
        return theMap;
    }


    /**
     * create n Players (n equals to playerAmount) with their territories inside
     * @param playerAmount total number of players to play the game
     * @return list of players
     */
    public ArrayList<Player> createPlayers(int playerAmount, Map theMap) {
        ArrayList<Player> players =new ArrayList<>();
        ArrayList<Territory> territories=theMap.getTerritories();
        for (int i = 0; i < playerAmount; ++i) {
            players.add(createPlayer(i,territories));
        }
        theMap.addPlayers(players);
        return players;
    }

    /**
     * create a player with its color and territories info
     * @param num total number of players to play the game
     * @return Player
     */
    private Player createPlayer(int num, ArrayList<Territory> territories){
        Player player =new Player(colors[num]);
        for (int i = 0; i < territoryAmount; ++i) {
            player.addTerritory(territories.get(this.territoryAmount * num + i));
            territories.get(this.territoryAmount * num + i).setOwner(player);
        }
        return player;
    }

    /**
     * create 6*playerAmount of territories
     * @param playerAmount total number of players to play the game
     */
    private ArrayList<Territory> createTerritories(int playerAmount) {
        ArrayList<Territory> territories = new ArrayList<>();
        for (int i = 0; i < playerAmount * this.territoryAmount; ++i) {
            Territory t = new ResourceTerritory(this.territoryNameList[i]);

            territories.add(t);
        }
        return territories;
    }

    /**
     * connect the territories in theMap
     * @param playerAmount total number of players to play the game
     * @param theMap the map to build connections
     */
    private void connectAdjacentTerritory(int playerAmount, Map theMap) {
        //up, left, down, right
        ArrayList<Territory> territories=theMap.getTerritories();
        int count = 0;
        for (int i = 0; i < playerAmount; ++i) {
            for (int j = 0; j < this.territoryAmount; ++j) {
                if (i != playerAmount - 1) {
                    theMap.addAdjacency(territories.get(i * this.territoryAmount + j),territories.get((i + 1) * this.territoryAmount + j));
                    theMap.setDistance(territories.get(i * this.territoryAmount + j),territories.get((i + 1) * this.territoryAmount + j), distanceList[count]);
                    ++count;
                }
                if (j % this.territoryAmount != (this.territoryAmount - 1)) {
                    theMap.addAdjacency(territories.get(i * this.territoryAmount + j),territories.get(i * this.territoryAmount + j + 1));
                    theMap.setDistance(territories.get(i * this.territoryAmount + j),territories.get(i * this.territoryAmount + j + 1), distanceList[count]);
                    ++count;
                }
            }
        }
    }

}

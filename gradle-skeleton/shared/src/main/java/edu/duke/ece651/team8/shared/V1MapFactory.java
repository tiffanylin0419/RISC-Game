package edu.duke.ece651.team8.shared;

import java.util.*;

public class V1MapFactory implements AbstractMapFactory {
    private final int territoryAmount;

    private String[] territoryNameList;

    private String[] colors;

    private ArrayList<Territory> territories;

    public V1MapFactory() {
        this.territoryAmount = 6;
        this.territoryNameList = new String[] {
                "a1", "a2", "a3", "a4", "a5", "a6",
                "b1", "b2", "b3", "b4", "b5", "b6",
                "c1", "c2", "c3", "c4", "c5", "c6",
                "d1", "d2", "d3", "d4", "d5", "d6"
        };
        this.colors= new String[]{ "Green", "Red", "Blue", "Yellow" };
        this.territories= new ArrayList<>();
    }

    /**
     *
     * @param playerAmount
     * @return create a Game1Map
     */
    @Override
    public Game1Map createMap(int playerAmount) {
        createTerritories(playerAmount);
        Game1Map theMap=new Game1Map(territories);
        connectAdjacentTerritory(playerAmount, theMap);
        return theMap;
    }


    /**
     * create n Players (n equals to playerAmount) with their territories inside
     * @param playerAmount
     * @return list of players
     */
    public ArrayList<Player> createPlayers(int playerAmount) {
        ArrayList<Player> players=new ArrayList<>();
        for (int i = 0; i < playerAmount; ++i) {
            players.add(createPlayer(i,playerAmount));
        }
        return players;
    }

    /**
     * create a player with its color and territories info
     * @param num
     * @param playerNum
     * @return Player
     */
    private Player createPlayer(int num, int playerNum){
        Player player=new TextPlayer(colors[num]);
        for (int i = 0; i < territoryAmount; ++i) {
            player.addTerritory(territories.get(6*num+i));
        }
        return player;
    }

    /**
     * create 6*playerAmount of territories
     * @param playerAmount
     */
    private void createTerritories(int playerAmount) {
        ArrayList<Territory> territories = new ArrayList<>();
        for (int i = 0; i < playerAmount * 6; ++i) {
            Territory t = new BasicTerritory(this.territoryNameList[i]);
            territories.add(t);
        }
        this.territories =  territories;
    }

    /**
     * connect the territories in theMap
     * @param playerAmount
     * @param theMap
     */
    private void connectAdjacentTerritory(int playerAmount, Map theMap) {
        //up, left, down, right
        for (int i = 0; i < playerAmount; ++i) {
            for (int j = 0; j < 6; ++j) {
                if (i != playerAmount - 1) {
                    theMap.addAdjacency(territories.get(i * 6 + j),territories.get((i + 1) * 6 + j));
                }
                if (j % 6 != 5) {
                    theMap.addAdjacency(territories.get(i * 6 + j),territories.get(i * 6 + j + 1));
                }
            }
        }
    }
}

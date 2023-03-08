package edu.duke.team8.riskgame;

import java.io.*;
import java.util.*;

import static java.lang.System.out;

public class V1MapFactory implements AbstractMapFactory {
    private HashSet<Territory> territories;

    private final int playerAmount;
    private ArrayList<Player> players;
    private final String[] nameList = {"Green", "Red", "Blue", "Yellow"};
//    private HashSet<TextPlayer> players;
//
//    private HashMap<TextPlayer, ArrayList<Territory>> map;
//
//    private int unitAmount;
//
//    private final BufferedReader inputReader;
//    private final PrintStream out;


    public V1MapFactory(HashSet<Territory> territories, int playerAmount) {
        this.territories = territories;
        this.playerAmount = playerAmount;
        this.players = new ArrayList<>();
        this.createPlayer();
        allocateTerritoriesToEachPlayer();
        connectAdjacentTerritory();
    }

    @Override
    public Game1Map createMap() {
        ArrayList<Territory> t = new ArrayList<>();
        for (Territory territory : this.territories) {
            t.add(territory);
        }
        return new Game1Map();
    }

    private void createPlayer() {
        for (int i = 0; i < this.playerAmount; ++i) {
            players.add(new TextPlayer(nameList[i]));
        }
    }
    private void allocateTerritoriesToEachPlayer() {
        Iterator<Territory> it = territories.iterator();
        int groups = territories.size() / playerAmount;
        for (Player player : players) {
            for (int i = 0; i < groups; ++i) {
                Territory territory = it.next();
                player.addTerritory(territory);
                territory.setOwner(player);
            }
        }
    }

    private void connectAdjacentTerritory() {
        Iterator<Territory> it = territories.iterator();
        while (it.hasNext()) {
            Territory t = it.next();
            for (Territory adj : territories) {
                if (t != adj) {
                    t.addAdjacent(adj);
                }
            }
        }
    }



}

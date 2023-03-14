package edu.duke.ece651.team8.shared;

import java.util.ArrayList;

public interface AbstractMapFactory {
    /**
     * enter player amount
     * @param playAmount
     * @return
     */
    public Game1Map createMap(int playAmount);

    public ArrayList<Player> createPlayers(int playerAmount, String colors[]);
}

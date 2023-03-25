package edu.duke.ece651.team8.shared;

import java.util.ArrayList;

public interface AbstractMapFactory {
    /**
     * enter player amount
     * @param playAmount player number
     * @return return a game1 map with playAmount players
     */
    Game1Map createMap(int playAmount);

    ArrayList<Player> createPlayers(int playerAmount, Map theMap);
}

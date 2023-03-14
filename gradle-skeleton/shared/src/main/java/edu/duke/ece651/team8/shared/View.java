package edu.duke.ece651.team8.shared;

import java.util.ArrayList;
import java.util.List;

/**
 * View pattern of the game
 */
public interface View {
    /**
     * Get map string
     * @return the string of map infomation
     */
    public String displayMap(ArrayList<Player> players);

    /**
     * Add the current territory unit display infomation to sb
     * @param sb is StringBuilder of the map
     * @param t is current territory
     */
    public void displayUnitInfo(StringBuilder sb, Territory t);

    public String displayAdjacentInfo(Territory t);
}

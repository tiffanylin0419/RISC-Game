package edu.duke.ece651.team8.shared;

import java.util.ArrayList;

/**
 * View pattern of the game
 */
public interface View {
    /**
     * Get map string
     * @return the string of map information
     */
    String displayMap(Map theMap);

    /**
     * Add the current territory unit display information to sb
     * @param sb is StringBuilder of the map
     * @param t is current territory
     */
    void displayUnitInfo(StringBuilder sb, Territory t);


}

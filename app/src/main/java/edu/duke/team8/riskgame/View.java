package edu.duke.team8.riskgame;

/**
 * View pattern of the game
 */
public interface View {
    /**
     * Get map string
     * @return the string of map infomation
     */
    public String displayMap();

    public String displayEachPlayerInfo();

    /**
     * Add the current territory unit display infomation to sb
     * @param sb is StringBuilder of the map
     * @param t is current territory
     */
    public void displayUnitInfo(StringBuilder sb, Territory t);
}

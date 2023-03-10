package edu.duke.team8.riskgame;

public interface AbstractMapFactory {
    /**
     * enter player amount
     * @param playAmount
     * @return
     */
    public Game1Map createMap(int playAmount);
}

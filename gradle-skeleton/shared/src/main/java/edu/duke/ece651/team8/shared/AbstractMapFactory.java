package edu.duke.ece651.team8.shared;

public interface AbstractMapFactory {
    /**
     * enter player amount
     * @param playAmount
     * @return
     */
    public Game1Map createMap(int playAmount);
}

package edu.duke.ece651.team8.shared;

public abstract class Action {
    private Player player;
    private Territory source;
    private Territory destination;
    private int count;
    /**
     * constructor
     * source and destination will ber null if there is no territory named this stored in theMap
     * @param player
     * @param source
     * @param destination
     * @param count
     * @param theMap
     */
    public Action(Player player, String source, String destination, int count, Map theMap) {
        this.player = player;
        this.source = null;
        this.destination = null;
        this.count = count;

        for(Territory t: theMap.getTerritories()){
            if(t.getName().equals(source)){
                this.source=t;
            }
            if(t.getName().equals(destination)){
                this.destination=t;
            }
        }
    }
    /**
     * conduct the action
     * must apply rule checker before calling this function
     * @param theMap
     */
    protected abstract void doAction(Map theMap);

    /**
     * @return player
     */
    public Player getPlayer(){return player;}
    /**
     * @return source
     */
    public Territory getSource(){return source;}
    /**
     * @return destination
     */
    public Territory getDestination(){return destination;}
    /**
     * @return count
     */
    public int getCount(){return count;}

}

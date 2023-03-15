package edu.duke.ece651.team8.shared;

public abstract class Action {
    private Player player;
    private Territory source;
    private Territory destination;
    private int count;
    //constructor
    public Action(Player player, String source, String destination, int count){
        this.player=player;
        this.source=new BasicTerritory(source);
        this.destination=new BasicTerritory(destination);
        this.count=count;
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

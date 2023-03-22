package edu.duke.ece651.team8.shared;

public abstract class Action {
    private Player player;
    private String sourceText;
    private String destinationText;
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
        this.sourceText=source;
        this.destinationText=destination;
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
     */
    protected abstract void doAction();

    /**
     * check if source is valid for the player
     * @return
     */
    protected abstract boolean isValidSource();

    /**
     * check if detination is valid for the player
     * @return
     */
    protected abstract boolean isValidDestination();
    /**
     * @return player
     */

    /**
     * check if the path from source to destination is valid
     * @return
     */
    protected abstract boolean isValidPath();

    public Player getPlayer(){return player;}

    /**
     * @return sourceText
     */
    public String getSourceText(){
        return sourceText;
    }

    /**
     * @return destinationText
     */
    public String getDestinationText(){
        return destinationText;
    }
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

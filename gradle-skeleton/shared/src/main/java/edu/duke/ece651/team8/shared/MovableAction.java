package edu.duke.ece651.team8.shared;

public abstract class MovableAction extends BasicAction {
    private final String sourceText;
    private final String destinationText;
    private Territory source;
    private Territory destination;
    private final int count;
    /**
     * constructor
     * source and destination will ber null if there is no territory named this stored in theMap
     * @param player the player triggers the action
     * @param source the source territory
     * @param destination the destination territory
     * @param count the number of unit to exec the action
     * @param theMap the map operates the action
     */
    public MovableAction(Player player, String source, String destination, int count, Map theMap) {
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
    public abstract void doAction();

    /**
     * check if source is valid for the player
     * @return the validation of the source territory
     */
    protected abstract boolean isValidSource();

    /**
     * check if destination is valid for the player
     * @return the validation of the destination of the territory
     */
    protected abstract boolean isValidDestination();

    /**
     * check if the path from source to destination is valid
     * @return the validation of the path
     */
    protected abstract boolean isValidPath();

    public int numberOfMovableUnits(){
        return getSource().getPlayerMovableUnitAmount(getPlayer());
    }

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

    protected abstract boolean hasEnoughFood();

    public int getFoodAmount() {
        return super.player.getFoodAmount();
    }

}

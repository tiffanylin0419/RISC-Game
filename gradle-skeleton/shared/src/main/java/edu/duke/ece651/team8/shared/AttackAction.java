package edu.duke.ece651.team8.shared;

public class AttackAction extends MovableAction {
    Map theMap;
    public AttackAction(Player player, String source, String destination, int count, Map theMap) {
        super(player, source, destination, count, theMap);
        this.theMap = theMap;
    }
    public boolean isValidSource(){
        return getSource().isOwner(getPlayer());
    }
    public boolean isValidDestination(){
        return ! getDestination().isOwner(getPlayer());
    }

    public boolean hasEnoughFood() {
        int distance = getSource().getDistance(getDestination());
        return super.player.getFoodAmount() >= (super.getCount() * distance);
    }
    public void doAction(){
        Army eArmy = getSource().getArmy(super.getCount(),super.getPlayer());
        getSource().moveOut(eArmy);
        getDestination().moveIn(eArmy);
        int distance = getSource().getDistance(getDestination());
        super.player.addFoodResource(-(super.getCount() * distance));
    }
    protected boolean isValidPath(){
        return getSource().isAdjacent(getDestination());
    }


}

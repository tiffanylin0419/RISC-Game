package edu.duke.ece651.team8.shared;

public class MoveAction extends MovableAction {
    Map theMap;

    public MoveAction(Player player, String source, String destination, int count, Map theMap) {
        super(player, source, destination, count, theMap);
        this.theMap = theMap;
    }
    public boolean isValidSource(){
        return getSource().isOwner(getPlayer());
    }
    public boolean isValidDestination(){
        return getDestination().isOwner(getPlayer());
    }

    public boolean hasEnoughFood() {
        MinimumPath path = new MinimumPath(player, theMap);
        int minPath = path.findMinPath(getSource(), getDestination());
//        System.out.println(super.player.getFoodAmount() + " " + minPath);
        return super.player.getFoodAmount() >= (super.getCount() * minPath);
    }
    public void doAction(){
        getSource().moveOut(new BasicArmy(super.getCount(),super.getPlayer()));
        getDestination().moveIn(new BasicArmy(super.getCount(),super.getPlayer()));
        MinimumPath path = new MinimumPath(player, theMap);
        int minPath = path.findMinPath(getSource(), getDestination());
        super.player.addFoodResource(-(super.getCount() * minPath));
    }
    protected boolean isValidPath(){
        return getSource().isAdjacentSelf(getDestination());
    }
}

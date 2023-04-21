package edu.duke.ece651.team8.shared;

public class SpyMoveAction extends MoveAction{
    public SpyMoveAction(Player player, String source, String destination, int count, Map theMap) {
        super(player, source, destination, count, theMap);
    }

    @Override
    public boolean isValidSource(){
        return true;
    }
    @Override
    public boolean isValidDestination(){
        return true;
    }
    @Override
    public boolean hasEnoughFood() {
        int minPath;
        if(getDestination().isOwner(getPlayer())&&getSource().isOwner(getPlayer())){
            MinimumPath path = new MinimumPath(player, theMap);
            minPath = path.findMinPath(getSource(), getDestination());
        }else{
            minPath = getSource().getDistance(getDestination());
        }

//        System.out.println(super.player.getFoodAmount() + " " + minPath);
        return super.player.getFoodAmount() >= (super.getCount() * minPath);
    }

    @Override
    public void doAction(){
        Army eArmy = getSource().getSpyArmy(getCount(),getPlayer());
        eArmy.setMoved();
        getSource().spyArmiesMoveOut(eArmy);
        int minPath = 1;
        if(getDestination().isOwner(getPlayer())&&getSource().isOwner(getPlayer())){
            MinimumPath path = new MinimumPath(player, theMap);
            minPath = path.findMinPath(getSource(), getDestination());
        }
        getDestination().spyArmiesMoveIn(eArmy);
        player.addFoodResource(-(getCount())*minPath);
    }
//    public void doAction(){
//        Army eArmy = getSource().getArmy(super.getCount(),super.getPlayer());
//        getSource().moveOut(eArmy);
//        getDestination().moveIn(eArmy);
//        int minPath = 1;
//        if(getDestination().isOwner(getPlayer())&&getSource().isOwner(getPlayer())){
//            MinimumPath path = new MinimumPath(player, theMap);
//            minPath = path.findMinPath(getSource(), getDestination());
//        }
//        super.player.addFoodResource(-(super.getCount() * minPath));
//    }
    @Override
    protected boolean isValidPath(){
        boolean isValid;
        if(getDestination().isOwner(getPlayer())&&getSource().isOwner(getPlayer())){
            isValid = getSource().isAdjacentSelf(getDestination());
        }else{
            isValid = getSource().isAdjacent(getDestination());
        }
        return isValid;
    }

    @Override
    public int numberOfMovableUnits(){
        return getSource().getPlayerMovableSpyAmount(getPlayer());
    }
}

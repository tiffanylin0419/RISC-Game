package edu.duke.ece651.team8.shared;

public class SpyMoveAction extends MoveAction{
    public SpyMoveAction(Player player, String source, String destination, int count, Map theMap) {
        super(player, source, destination, count, theMap);
    }

    @Override
    public boolean isValidDestination(){
        return true;
    }

    @Override
    public void doAction(){
        Army eArmy = getSource().getSpyArmy(super.getCount(),super.getPlayer());
        getSource().spyArmiesMoveOut(eArmy);
        getDestination().spyArmiesMoveIn(eArmy);
        MinimumPath path = new MinimumPath(player, theMap);
        int minPath = path.findMinPath(getSource(), getDestination());
        super.player.addFoodResource(-(super.getCount() * minPath));
    }
    @Override
    protected boolean isValidPath(){
        return true;
    }
}

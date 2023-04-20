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
    public void doAction(){
        Army eArmy = getSource().getSpyArmy(getCount(),getPlayer());
        eArmy.setMoved();
        getSource().spyArmiesMoveOut(eArmy);
        getDestination().spyArmiesMoveIn(eArmy);
        player.addFoodResource(-(getCount()));
    }
    @Override
    protected boolean isValidPath(){
        return getSource().isAdjacent(getDestination());
    }

    @Override
    public int numberOfMovableUnits(){
        return getSource().getPlayerMovableSpyAmount(getPlayer());
    }
}

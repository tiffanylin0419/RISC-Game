package edu.duke.ece651.team8.shared;

public class MoveAction extends Action{

    public MoveAction(Player player, String source, String destination, int count, Map theMap) {
        super(player, source, destination, count, theMap);
    }

    protected void doAction(Map theMap){
        getSource().moveOut(new BasicUnit(super.getCount(),super.getPlayer()));
        getDestination().moveIn(new BasicUnit(super.getCount(),super.getPlayer()));
        return;
    }
}

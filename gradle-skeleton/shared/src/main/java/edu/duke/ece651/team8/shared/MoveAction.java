package edu.duke.ece651.team8.shared;

public class MoveAction extends Action{

    public MoveAction(Player player, String source, String destination, int count) {
        super(player, source, destination, count);
    }

    protected void doAction(Map theMap){
        Territory mapSource=null;
        Territory mapDestination=null;

        for(Territory t: theMap.getTerritories()){
            if(t.equals(super.getSource())){
                mapSource=t;
            }
            if(t.equals(super.getDestination())){
                mapDestination=t;
            }
        }
        mapSource.moveOut(new BasicUnit(super.getCount(),super.getPlayer()));
        mapDestination.moveIn(new BasicUnit(super.getCount(),super.getPlayer()));
        return;
    }
}

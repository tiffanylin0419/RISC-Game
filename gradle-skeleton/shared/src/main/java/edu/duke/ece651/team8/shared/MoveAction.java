package edu.duke.ece651.team8.shared;

public class MoveAction extends Action{

    public MoveAction(String source, String destination, int count) {
        super(source, destination, count);
    }

    protected void doAction(Map theMap, Player player){
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
        mapSource.tryMoveOut(new BasicUnit(super.getCount(),player));
        mapDestination.moveIn(new BasicUnit(super.getCount(),player));
        return;
    }
}

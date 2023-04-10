package edu.duke.ece651.team8.shared;

public class AttackAction extends MovableAction {

    public AttackAction(Player player, String source, String destination, int count, Map theMap) {
        super(player, source, destination, count, theMap);
    }
    public boolean isValidSource(){
        return getSource().isOwner(getPlayer());
    }
    public boolean isValidDestination(){
        return ! getDestination().isOwner(getPlayer());
    }

    public boolean hasEnoughFood() {
        return super.player.getFoodAmount() >= 1;
    }
    public void doAction(){
        getSource().moveOut(new BasicArmy(super.getCount(),super.getPlayer()));
        getDestination().moveIn(new BasicArmy(super.getCount(),super.getPlayer()));
        super.player.addFoodResource(-1);
    }
    protected boolean isValidPath(){
        return getSource().isAdjacentEnemy(getDestination());
    }

}

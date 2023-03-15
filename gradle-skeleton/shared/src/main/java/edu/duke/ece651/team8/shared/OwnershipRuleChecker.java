package edu.duke.ece651.team8.shared;

public class OwnershipRuleChecker extends MovementRuleChecker {
    public OwnershipRuleChecker(MovementRuleChecker next){
        super(next);
    }

    /**
     * check if the source and destination belongs to who it should belong to
     * @param theMap
     * @param action
     * @return
     */
    protected String checkMyRule(Map theMap, Action action){
        if(action.getCount()<=action.getSource().getOwnerUnitAmount()){
            return null;
        }
        else{
            return "Requested "+action.getCount()+" units, but only have "+action.getSource().getOwnerUnitAmount();
        }
    }
}

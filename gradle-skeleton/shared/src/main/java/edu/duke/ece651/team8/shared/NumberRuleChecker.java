package edu.duke.ece651.team8.shared;

public class NumberRuleChecker extends MovementRuleChecker {
    public NumberRuleChecker(MovementRuleChecker next){
        super(next);
    }

    protected String checkMyRule(Map theMap, Player thePlayer, Action action){
        if(action.getSource().getOwnerUnitAmount()<action.getCount()){
            return null;
        }
        else{
            return "Requested "+action.getCount()+" units, but only have "+action.getSource().getOwnerUnitAmount();
        }
    }
}

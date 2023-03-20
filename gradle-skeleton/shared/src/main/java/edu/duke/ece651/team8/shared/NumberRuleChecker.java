package edu.duke.ece651.team8.shared;

public class NumberRuleChecker extends ActionRuleChecker {
    public NumberRuleChecker(ActionRuleChecker next){
        super(next);
    }

    /**
     * check if count of the action is small enough to be conducted
     * @param action
     * @return
     */
    protected String checkMyRule(Action action){
        if(action.getCount()<=action.getSource().getOwnerUnitAmount()){
            return null;
        }
        else{
            return "Requested "+action.getCount()+" units, but only have "+action.getSource().getOwnerUnitAmount();
        }
    }
}

package edu.duke.ece651.team8.shared;

public class NumberRuleChecker extends MovableActionRuleChecker {
    public NumberRuleChecker(MovableActionRuleChecker next){
        super(next);
    }

    /**
     * check if count of the action is small enough to be conducted
     * @param action the action to be checked
     * @return null if valid
     */
    protected String checkMyRule(MovableAction action){
        if(action.getCount()<0){
            return "Unit number need to be positive";
        }
        if(action.getCount()<=action.numberOfMovableUnits()){
            return null;
        }
        else{
            return "Requested "+action.getCount()+" units, but only "+action.numberOfMovableUnits() + " units are movable";
        }
    }
}

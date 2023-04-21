package edu.duke.ece651.team8.shared;

public class MovableOwnershipRuleChecker extends MovableActionRuleChecker {
    public MovableOwnershipRuleChecker(MovableActionRuleChecker next){
        super(next);
    }

    /**
     * check if the source and destination belongs to whom it should belong to
     * @param action the action to be checked
     * @return null if valid
     */
    protected String checkMyRule(MovableAction action){
        if(!action.isValidSource()){
            return "Cannot choose "+action.getSourceText()+" as source for this action";
        }
        else if(!action.isValidDestination()){
            return "Cannot choose "+action.getDestinationText()+" as destination for this action";
        }
        return null;
    }
}

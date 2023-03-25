package edu.duke.ece651.team8.shared;

public class OwnershipRuleChecker extends ActionRuleChecker {
    public OwnershipRuleChecker(ActionRuleChecker next){
        super(next);
    }

    /**
     * check if the source and destination belongs to whom it should belong to
     * @param action the action to be checked
     * @return null if valid
     */
    protected String checkMyRule(Action action){
        if(!action.isValidSource()){
            return "Cannot choose "+action.getSourceText()+" as source for this action";
        }
        else if(!action.isValidDestination()){
            return "Cannot choose "+action.getDestinationText()+" as destination for this action";
        }
        return null;
    }
}

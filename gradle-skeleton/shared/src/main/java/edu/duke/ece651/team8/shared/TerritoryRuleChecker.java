package edu.duke.ece651.team8.shared;

public class TerritoryRuleChecker extends MovableActionRuleChecker {
    public TerritoryRuleChecker(MovableActionRuleChecker next){
        super(next);
    }

    /**
     * check if territory exists in map
     * @param action the action to check
     * @return null if valid
     */
    protected String checkMyRule(MovableAction action){
        if(action.getSource()==null){
            return "Source "+action.getSourceText()+" not in map";
        }
        else if(action.getDestination()==null){
            return "Destination "+action.getDestinationText()+" not in map";
        }
        return null;
    }
}
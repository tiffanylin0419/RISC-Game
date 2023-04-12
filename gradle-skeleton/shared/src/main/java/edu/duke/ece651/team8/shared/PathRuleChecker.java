package edu.duke.ece651.team8.shared;

public class PathRuleChecker  extends MovableActionRuleChecker {
    public PathRuleChecker(MovableActionRuleChecker next){
        super(next);
    }

    /**
     * check if the source can connect to destination
     * @param action the action to be checked
     */
    protected String checkMyRule(MovableAction action){
        if(!action.isValidPath()){
            return "Units in "+action.getSourceText()+" cannot go to "+action.getDestinationText();
        }
        if (!action.hasEnoughFood()) {
            return "Do not have enough food";
        }
        return null;
    }
}
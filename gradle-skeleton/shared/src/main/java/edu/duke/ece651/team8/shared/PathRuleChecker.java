package edu.duke.ece651.team8.shared;

public class PathRuleChecker  extends ActionRuleChecker {
    public PathRuleChecker(ActionRuleChecker next){
        super(next);
    }

    /**
     * check if the source can connect to destination
     * @param action the action to be checked
     */
    protected String checkMyRule(MovableAction action){
        if(action.isValidPath()){
            return null;
        }
        return "Units in "+action.getSourceText()+" cannot go to "+action.getDestinationText();
    }
}
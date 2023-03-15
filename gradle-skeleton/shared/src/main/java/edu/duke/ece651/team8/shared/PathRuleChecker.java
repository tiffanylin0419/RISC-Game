package edu.duke.ece651.team8.shared;

public class PathRuleChecker  extends MovementRuleChecker {
    public PathRuleChecker(MovementRuleChecker next){
        super(next);
    }

    /**
     * check if the source can connect to destination
     * @param action
     * @return
     */
    protected String checkMyRule(Action action){
        if(action.isValidPath()){
            return null;
        }
        return "Units in "+action.getSourceText()+" cannot go to "+action.getDestinationText();
    }
}
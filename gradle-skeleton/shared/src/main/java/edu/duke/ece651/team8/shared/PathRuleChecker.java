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
        //action.isValidPath()
        return "";
    }
}
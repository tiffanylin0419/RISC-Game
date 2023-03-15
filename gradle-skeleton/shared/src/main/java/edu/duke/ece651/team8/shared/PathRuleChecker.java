package edu.duke.ece651.team8.shared;

public class PathRuleChecker  extends MovementRuleChecker {
    public PathRuleChecker(MovementRuleChecker next){
        super(next);
    }

    /**
     * check if the source can connect to destination
     * @param theMap
     * @param action
     * @return
     */
    protected String checkMyRule(Map theMap, Action action){

        return null;
    }
}
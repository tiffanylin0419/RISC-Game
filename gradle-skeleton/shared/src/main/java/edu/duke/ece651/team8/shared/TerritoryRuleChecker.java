package edu.duke.ece651.team8.shared;

public class TerritoryRuleChecker extends MovementRuleChecker {
    public TerritoryRuleChecker(MovementRuleChecker next){
        super(next);
    }

    /**
     * check if territory exists in map
     * @param theMap
     * @param action
     * @return
     */
    protected String checkMyRule(Map theMap, Action action){
        if(action.getSource()==null){
            return "Source not in map";
        }
        if(action.getDestination()==null){
            return "Destination not in map";
        }
        return null;
    }
}
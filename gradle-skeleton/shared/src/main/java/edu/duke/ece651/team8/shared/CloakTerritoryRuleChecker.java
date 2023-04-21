package edu.duke.ece651.team8.shared;

public class CloakTerritoryRuleChecker extends CloakActionRuleChecker {
    public CloakTerritoryRuleChecker(CloakActionRuleChecker next){
        super(next);
    }

    /**
     * check if territory exists in map
     * @param action the action to check
     * @return null if valid
     */
    protected String checkMyRule(CloakAction action){
        if(action.getTerritory()==null){
            return "Cannot choose "+action.getTerritoryText()+" because territory owned by other players or not on map.";
        }
        return null;
    }
}
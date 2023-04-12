package edu.duke.ece651.team8.shared;

public class UpgradeTerritoryRuleChecker extends UpgradeActionRuleChecker{
    public UpgradeTerritoryRuleChecker(UpgradeActionRuleChecker checker) {
        super(checker);
    }

    @Override
    protected String checkMyRule(UpgradeAction action) {
        if(action.getTerritory() == null) {
            return "Territory owned by other players or not on map";
        }
        return null;
    }
}

package edu.duke.ece651.team8.shared;

public class UpgradeCostRuleChecker extends UpgradeActionRuleChecker{
    public UpgradeCostRuleChecker(UpgradeActionRuleChecker checker) {super(checker);}

    @Override
    protected String checkMyRule(UpgradeAction action) {
        int cost = action.costTechResource();
        if(action.getPlayer().getTechAmount() < cost) {
            return "Do not have " + cost + " Technology Resources for upgrading";
        }
        return null;
    }
}

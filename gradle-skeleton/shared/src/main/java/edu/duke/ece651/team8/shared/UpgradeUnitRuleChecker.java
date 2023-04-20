package edu.duke.ece651.team8.shared;

public class UpgradeUnitRuleChecker extends UpgradeActionRuleChecker{
    public UpgradeUnitRuleChecker(UpgradeActionRuleChecker checker) {super(checker);}

    @Override
    protected String checkMyRule(UpgradeAction action) {
        Territory t = action.getTerritory();
        if(action.nextLevel > action.player.getLevel()) {
            return "Level " + action.nextLevel + " is beyond your tech level: " + action.player.getLevel();
        }
        if(action.startLevel < 0 || action.startLevel >= 6) {
            return "Start level " + action.startLevel + " is not valid";
        }
        if(action.unitAmount > t.getOwnerUnitLevelAmount(action.startLevel)) {
            return "You have less than " + action.unitAmount + " units on level " + action.startLevel;
        }
        if((action.nextLevel <= action.startLevel&&action.nextLevel!=-1) || action.nextLevel > 6) {
            return "Next level " + action.nextLevel + " is not valid";
        }
        return null;
    }

}

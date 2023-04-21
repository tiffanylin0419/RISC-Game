package edu.duke.ece651.team8.shared;

public class CloakLevelRuleChecker extends CloakActionRuleChecker{
    protected final int MIN_LEVEL;

    public CloakLevelRuleChecker(CloakActionRuleChecker next, int minLevel) {
        super(next);
        MIN_LEVEL = minLevel;
    }

    @Override
    protected String checkMyRule(CloakAction action) {
        if(action.player.getLevel() < MIN_LEVEL) {
            return "Cloak action is invalid until your level is greater than " + MIN_LEVEL + "!";
        }
        return null;
    }
}

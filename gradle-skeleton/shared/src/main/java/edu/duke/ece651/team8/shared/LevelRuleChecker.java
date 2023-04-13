package edu.duke.ece651.team8.shared;

public class LevelRuleChecker extends ResearchActionRuleChecker{
    protected final int MAX_LEVEL;

    public LevelRuleChecker(ResearchActionRuleChecker next, int maxLevel) {
        super(next);
        MAX_LEVEL = maxLevel;
    }

    @Override
    protected String checkMyRule(ResearchAction action) {
        if(action.player.getLevel() >= MAX_LEVEL) {
            return "Research action is invalid since your level can not be greater than the max level: " + MAX_LEVEL + "!";
        }
//        }else if(action.player.getLevel() < 1){
//            return "Your level must be positive Integer!";
//        }
        return null;
    }
}

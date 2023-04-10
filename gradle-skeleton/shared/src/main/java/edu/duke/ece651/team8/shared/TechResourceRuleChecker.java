package edu.duke.ece651.team8.shared;

public class TechResourceRuleChecker extends ResearchActionRuleChecker{
    static protected final int MAX_LEVEL = 6;

    public TechResourceRuleChecker(ResearchActionRuleChecker next) {
        super(next);
    }

    @Override
    protected String checkMyRule(ResearchAction action) {
        if(action.player.getTechAmount() >= MAX_LEVEL){
            return "Your level can not be greater than the max level: "+ MAX_LEVEL + "!";
        }
        return "";
    }
}

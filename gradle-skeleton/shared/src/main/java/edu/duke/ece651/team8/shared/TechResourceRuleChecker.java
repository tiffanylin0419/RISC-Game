package edu.duke.ece651.team8.shared;

import java.util.HashMap;

public class TechResourceRuleChecker extends ResearchActionRuleChecker{
    public TechResourceRuleChecker(ResearchActionRuleChecker next) {
        super(next);
    }

    @Override
    protected String checkMyRule(ResearchAction action) {
        int level = action.player.getLevel();
        if(action.player.getTechAmount() < action.getResearchCostsList()[level]+action.player.currentTurnTechConsumingAmount){
            return "You do not have enough technique resource for this action!";
        }
        return null;
    }
}

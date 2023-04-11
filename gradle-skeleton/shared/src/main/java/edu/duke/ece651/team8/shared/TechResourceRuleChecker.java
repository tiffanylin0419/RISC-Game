package edu.duke.ece651.team8.shared;

import java.util.HashMap;

public class TechResourceRuleChecker extends ResearchActionRuleChecker{
    HashMap<Integer, Integer> researchCostsOfEachLevel;
    public TechResourceRuleChecker(ResearchActionRuleChecker next, HashMap<Integer, Integer> researchCostsOfEachLevel) {
        super(next);
        this.researchCostsOfEachLevel = researchCostsOfEachLevel;
    }

    @Override
    protected String checkMyRule(ResearchAction action) {
        if(action.player.getTechAmount() < researchCostsOfEachLevel.get(action.player.getLevel())+action.player.currentTurnTechConsumingAmount){
            return "You do not have enough technique resource for this action!";
        }
        return "";
    }
}

package edu.duke.ece651.team8.shared;

public class ResearchTurnLimitChecker extends  ResearchActionRuleChecker{

    public ResearchTurnLimitChecker(ResearchActionRuleChecker next) {
        super(next);
    }

    @Override
    protected String checkMyRule(ResearchAction action) {
        if(action.player.hasResearchedThisTurn){
            return "You can only research once a turn!";
        }else{
            return "";
        }
    }
}

package edu.duke.ece651.team8.shared;

public class CloakTechResourceRuleChecker extends CloakActionRuleChecker{
    public CloakTechResourceRuleChecker(CloakActionRuleChecker checker) {super(checker);}

    @Override
    protected String checkMyRule(CloakAction action) {
        int cost = action.costTechResource();
        if(action.getPlayer().getTechAmount() < cost) {
            return "Do not have " + cost + " Technology Resources for cloaking";
        }
        return null;
    }
}


package edu.duke.ece651.team8.shared;

public class TechResourceRuleChecker extends ResearchActionRuleChecker{
    HashMap<Integer, Integer> researchCostsOfEachLevel;
    public TechResourceRuleChecker(ResearchActionRuleChecker next, HashMap<Integer, Integer> researchCostsOfEachLevel) {
        super(next);
        this.researchCostsOfEachLevel = researchCostsOfEachLevel;
    }

    @Override
    protected String checkMyRule(ResearchAction action) {
<<<<<<< HEAD
        if(action.player.getTechAmount() < researchCostsOfEachLevel.get(action.player.getLevel())+action.player.currentTurnTechConsumingAmount){
            return "You do not have enough technique resource for this action!";
=======
        if(action.player.getTechAmount() >= MAX_LEVEL){
            return "Your level can not be greater than the max level: "+ MAX_LEVEL + "!";
>>>>>>> 20193cb (Add action interface)
        }
        return "";
    }
}

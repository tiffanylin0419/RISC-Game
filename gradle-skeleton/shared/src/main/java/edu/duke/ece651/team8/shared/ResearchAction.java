package edu.duke.ece651.team8.shared;

public class ResearchAction extends BasicAction{
    protected Player player;

    int[] researchCosts;
    public ResearchAction(Player player){
        this.player = player;
        this.researchCosts = getResearchCostsList();
    }
    @Override
    public void doAction() {
        player.hasResearchedThisTurn = true;
        player.upgradeTechLevel();
        // todo
        //  cost technology resource of the player
    }

    public int[] getResearchCostsList(){
        int[] researchCosts = {0, 20, 40, 80, 160, 320};
        return researchCosts;
    }
}

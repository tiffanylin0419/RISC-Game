package edu.duke.ece651.team8.shared;

public class ResearchAction extends BasicAction{
    protected Player player;

    static int[] researchCosts = {0, 20, 40, 80, 160, 320};
    public ResearchAction(Player player){
        this.player = player;
    }
    @Override
    public void doAction() {
        player.hasResearchedThisTurn = true;
        int level = player.getLevel();
        player.addTechResource(-researchCosts[level]);
    }

    public int[] getResearchCostsList() {
        return this.researchCosts;
    }
}
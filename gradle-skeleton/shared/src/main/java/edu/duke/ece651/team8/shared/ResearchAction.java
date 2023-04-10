package edu.duke.ece651.team8.shared;

public class ResearchAction extends BasicAction{
    protected V2Player player;
    protected int theLevelUpgradeTo;
    public ResearchAction(V2Player player,int theLevelUpgradeTo){
        this.player = player;
        this.theLevelUpgradeTo = theLevelUpgradeTo;
    }
    @Override
    public void doAction() {

    }
}

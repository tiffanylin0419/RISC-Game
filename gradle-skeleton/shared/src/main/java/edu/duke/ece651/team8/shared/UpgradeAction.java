package edu.duke.ece651.team8.shared;

public class UpgradeAction extends BasicAction {
    private Territory territory;
    private int unitAmount;
    private int startLevel;
    private int nextLevel;

    private int[] costs = {0, 3, 8, 19, 25, 35, 50};
    public UpgradeAction(Player player, Territory territory, int unitAmount, int startLevel, int nextLevel) {
        this.player = player;
        this.territory = territory;
        this.unitAmount = unitAmount;
        this.startLevel = startLevel;
        this.nextLevel = nextLevel;
    }

    private int costTechResource() {
        int eachCost = 0;
        int diffLevel = nextLevel - startLevel;
        int curLevel = startLevel;
        while (diffLevel > 0) {
            --diffLevel;
            eachCost += this.costs[curLevel];
            ++curLevel;
        }
        return eachCost * unitAmount;
    }

    /**
     * update the technology resource of the player
     * update the unit level in the territory
     */
    @Override
    public void doAction() {
        int totalCost = costTechResource();
        this.player.addTechResource(-totalCost);
        this.territory.upgradeUnits(this.player, this.unitAmount, this.startLevel, this.nextLevel);
    }
}

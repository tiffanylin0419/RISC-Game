package edu.duke.ece651.team8.shared;

public class UpgradeAction extends BasicAction {
    private Territory territory;
    public int unitAmount;
    public int startLevel;
    public int nextLevel;

    private int[] costs = {0, 3, 8, 19, 25, 35, 50};
    public UpgradeAction(Player player, String territoryText, int unitAmount, int startLevel, int nextLevel) {
        this.player = player;
        this.unitAmount = unitAmount;
        this.startLevel = startLevel;
        this.nextLevel = nextLevel;
        for (Territory t : player.getTerritories()) {
            if (t.getName().equals(territoryText)) {
                this.territory = t;
                break;
            }
        }
    }
    public Territory getTerritory() {return territory;}
    public Player getPlayer() {return player;}
    public int costTechResource() {
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

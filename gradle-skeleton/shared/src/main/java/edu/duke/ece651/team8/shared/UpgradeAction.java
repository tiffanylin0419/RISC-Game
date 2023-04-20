package edu.duke.ece651.team8.shared;

import java.util.List;

public class UpgradeAction extends BasicAction {
    private Territory territory;
    public int unitAmount;
    public int startLevel;
    public int nextLevel;

    static private int[] costs = {3, 8, 19, 25, 35, 50, 0};

    static private int spyCost = 80;

    public UpgradeAction() {
        this.player = null;
        this.unitAmount = 0;
        this.startLevel = 0;
        this.nextLevel = 0;
        this.territory = null;
    }
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
        if(nextLevel == Integer.MIN_VALUE){
            return spyCost*unitAmount;
        }
        int diffLevel = nextLevel - startLevel;
        UnitFactory uf = new UnitFactory();
        List<Unit> u = uf.makeAdvancedUnits(1, startLevel);
        int eachCost = uf.canUpgradeTo(diffLevel, u.get(0));
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

    public int getCost(int level) {
        return costs[level];
    }
}

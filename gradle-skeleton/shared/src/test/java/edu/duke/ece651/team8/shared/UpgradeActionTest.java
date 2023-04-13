package edu.duke.ece651.team8.shared;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UpgradeActionTest {
    @Test
    void getTerritory() {
        Player p = new Player("Green");
        Territory t = new BasicTerritory("a1", p);
        UpgradeAction ua = new UpgradeAction(p, "a1", 2, 1, 2);
        assertEquals(t, ua.getTerritory());
        assertEquals(p, ua.getPlayer());
        assertEquals(16, ua.costTechResource());
    }

    @Test
    void doAction() {
        Player p = new Player("Green");
        Territory t = new BasicTerritory("a1", p);
        t.moveIn(new EvolvableArmy(3, p));
        UpgradeAction ua = new UpgradeAction(p, "a1", 2, 0, 2);
        UpgradeActionRuleChecker checker = new UpgradeTerritoryRuleChecker(new UpgradeUnitRuleChecker(new UpgradeCostRuleChecker(null)));
        assertEquals(20, p.getTechAmount());
        assertEquals("Do not have 22 Technology Resources for upgrading", checker.checkAllRule(ua));
        p.addTechResource(2);
        ua.doAction();
        assertEquals(2, t.getOwnerUnitLevelAmount(2));
    }

    @Test
    public void testUpgradeAction() {
        Territory territory = new ResourceTerritory("a");
        Player player = new Player("Green");
        territory.setOwner(player);
        player.addTerritory(territory);
        territory.moveIn(new BasicArmy(6, player));
        UpgradeAction action = new UpgradeAction(player, "a", 1, 0, 2);
        assertEquals(11, action.costTechResource());
        action.doAction();
        assertEquals(9, player.getTechAmount());
    }
}
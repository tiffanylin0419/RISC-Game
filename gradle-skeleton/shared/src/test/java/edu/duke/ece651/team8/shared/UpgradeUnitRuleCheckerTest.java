package edu.duke.ece651.team8.shared;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UpgradeUnitRuleCheckerTest {
    @Test
    public void testUpgradeUnitRuleChecker() {
        Territory territory = new ResourceTerritory("a");
        Territory territory2 = new ResourceTerritory("b");
        Player player = new Player("Green");
        territory.setOwner(player);
        territory2.setOwner(player);
        player.addTerritory(territory);
        player.addTerritory(territory2);
        ArrayList<Territory> territories = new ArrayList<>();
        territories.add(territory);
        territories.add(territory2);
        Map theMap = new Game1Map(territories);

        territory.moveIn(new BasicArmy(8, player));

        UpgradeAction action = new UpgradeAction(player, "a", 1, 0, 1);
        UpgradeAction action2 = new UpgradeAction(player, "a", 1, 1, 2);
        UpgradeAction action3 = new UpgradeAction(player, "a", 1, -1, 1);
        UpgradeAction action4 = new UpgradeAction(player, "a", 1, 0, -1);
        UpgradeAction action5 = new UpgradeAction(player, "a", 1, 1, 0);
        String message = theMap.getUpgradeRuleChecker().checkAllRule(action);
        String message2 = theMap.getUpgradeRuleChecker().checkAllRule(action2);
        String message3 = theMap.getUpgradeRuleChecker().checkAllRule(action3);
        String message4 = theMap.getUpgradeRuleChecker().checkAllRule(action4);
        String message5 = theMap.getUpgradeRuleChecker().checkAllRule(action5);
        assertEquals(null, message);
        assertEquals("Level 2 is beyond your tech level: 1", message2);
        assertEquals("Start level -1 is not valid", message3);
        assertEquals("Do not have 80 Technology Resources for upgrading", message4);
        assertEquals("You have less than 1 units on level 1", message5);
    }
}

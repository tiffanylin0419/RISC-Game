package edu.duke.ece651.team8.shared;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class UpgradeActionRuleCheckerTest {
    @Test
    public void testUpgradeActionRuleChecker() {
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

        UpgradeAction action = new UpgradeAction(player, "a", 3, 0, 1);
        UpgradeAction action2 = new UpgradeAction(player, "a", 1, 1, 2);
        UpgradeAction action3 = new UpgradeAction(player, "a", 8, 0, 1);
        UpgradeAction action4 = new UpgradeAction(player, "b", 1, 0, 1);
        String message = theMap.getUpgradeRuleChecker().checkAllRule(action);
        String message2 = theMap.getUpgradeRuleChecker().checkAllRule(action2);
        String message3 = theMap.getUpgradeRuleChecker().checkAllRule(action3);
        String message4 = theMap.getUpgradeRuleChecker().checkAllRule(action4);
        assertEquals(null, message);
        assertEquals("Level 2 is beyond your tech level: 1", message2);
        assertEquals("Do not have 24 Technology Resources for upgrading", message3);
        assertEquals("You have less than 1 units on level 0", message4);
    }
}

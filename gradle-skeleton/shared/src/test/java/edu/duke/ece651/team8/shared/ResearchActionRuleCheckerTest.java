package edu.duke.ece651.team8.shared;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ResearchActionRuleCheckerTest {

    @Test
    void TestCheckAllRule() {
        Player p = new Player("Green");
        Territory t = new BasicTerritory("a1", p);
        t.moveIn(new EvolvableArmy(3, p));
        ResearchAction ra = new ResearchAction(p);
        ResearchActionRuleChecker checker = new ResearchLevelRuleChecker(new TechResourceRuleChecker(new ResearchTurnLimitChecker(null)),6);
        assertEquals(null, checker.checkAllRule(ra));

        ra.doAction();
    }
}
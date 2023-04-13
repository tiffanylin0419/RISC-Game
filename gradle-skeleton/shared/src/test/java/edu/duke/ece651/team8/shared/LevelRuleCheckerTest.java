package edu.duke.ece651.team8.shared;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LevelRuleCheckerTest {

    @Test
    void TestcheckMyRule() {
        Player p = new Player("Green");
        ResearchAction ac = new ResearchAction(p);
        LevelRuleChecker ch = new LevelRuleChecker(null, 2);
        assertEquals(null, ch.checkMyRule(ac));
        p.upgradeTechLevel();
        p.upgradeTechLevel();
        p.upgradeTechLevel();
        assertEquals("Research action is invalid since your level can not be greater than the max level: 2!", ch.checkMyRule(ac));


    }
}
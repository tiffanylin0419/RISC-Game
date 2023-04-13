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
    @Test
    void TestCheckMyRule2() {
        Player p = new Player();
        p.setLevel(0);
        ResearchAction as = new ResearchAction(p);
        LevelRuleChecker lrc = new LevelRuleChecker(null, 6);
        assertEquals("Your level must be positive Integer!",lrc.checkMyRule(as));
        p.setLevel(6);
        assertEquals("Research action is invalid since your level can not be greater than the max level: 6!",lrc.checkMyRule(as));

    }
}
package edu.duke.ece651.team8.shared;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LevelRuleCheckerTest {

    @Test
    void TestCheckMyRule() {
        Player p = new Player();
        p.setLevel(0);
        ResearchAction as = new ResearchAction(p);
        LevelRuleChecker lrc = new LevelRuleChecker(null, 6);
        assertEquals("Your level must be positive Integer!",lrc.checkMyRule(as));
        p.setLevel(6);
        assertEquals("Research action is invalid since your level can not be greater than the max level: 6!",lrc.checkMyRule(as));

        p.setLevel(1);
        assertNull(lrc.checkMyRule(as));
    }
}
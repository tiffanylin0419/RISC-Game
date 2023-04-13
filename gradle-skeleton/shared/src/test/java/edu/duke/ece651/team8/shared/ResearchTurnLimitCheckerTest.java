package edu.duke.ece651.team8.shared;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ResearchTurnLimitCheckerTest {

    @Test
    void TestCheckMyRule() {
        Player p = new Player();
        ResearchAction as = new ResearchAction(p);
        ResearchTurnLimitChecker rstlc = new ResearchTurnLimitChecker(null);
        assertEquals(null,rstlc.checkMyRule(as));
        as.doAction();
        assertNotNull(rstlc.checkMyRule(as));
    }
}
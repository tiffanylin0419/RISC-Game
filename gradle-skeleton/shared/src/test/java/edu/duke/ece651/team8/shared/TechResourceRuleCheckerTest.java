package edu.duke.ece651.team8.shared;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TechResourceRuleCheckerTest {

    @Test
    void TestCheckMyRule() {
        Player p = new Player();
        ResearchAction as = new ResearchAction(p);
        TechResourceRuleChecker trrc = new TechResourceRuleChecker(null);
        assertEquals(null,trrc.checkMyRule(as));
        as.doAction();
        assertEquals("You do not have enough technique resource for this action!",trrc.checkMyRule(as));

    }
}
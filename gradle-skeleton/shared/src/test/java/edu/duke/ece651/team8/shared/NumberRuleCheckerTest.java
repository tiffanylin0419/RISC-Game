package edu.duke.ece651.team8.shared;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class NumberRuleCheckerTest {
    @Test
    public void test_checkMyRule(){

        AbstractMapFactory factory = new V1MapFactory();
        Map theMap = factory.createMap(1);
        ArrayList<Player> players =factory.createPlayers(1,theMap);

        Player p1= players.get(0);
        theMap.getTerritories().get(0).moveIn(new BasicArmy(5,p1));
        theMap.getTerritories().get(1).moveIn(new BasicArmy(4,p1));
        theMap.getTerritories().get(2).moveIn(new BasicArmy(3,p1));
        theMap.getTerritories().get(3).moveIn(new BasicArmy(2,p1));
        theMap.getTerritories().get(4).moveIn(new BasicArmy(1,p1));
        theMap.getTerritories().get(5).moveIn(new BasicArmy(9,p1));

        MovableActionRuleChecker checker= new NumberRuleChecker(null) ;

        MovableAction action1 = new MoveAction(p1,"a1","a3",3,theMap);
        assertNull(checker.checkMyRule(action1));

        MovableAction action2 = new MoveAction(p1,"a1","a3",5,theMap);
        assertNull(checker.checkAllRule(action2));

        MovableAction action3 = new MoveAction(p1,"a1","a3",6,theMap);
        assertEquals("Requested 6 units, but only have 5",checker.checkAllRule(action3));

        MovableAction action4 = new MoveAction(p1,"a1","a3",-1,theMap);
        assertEquals("Unit number need to be positive",checker.checkAllRule(action3));

    }
}
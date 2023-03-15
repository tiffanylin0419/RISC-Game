package edu.duke.ece651.team8.shared;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class NumberRuleCheckerTest {
    @Test
    public void test_checkMyRule(){

        AbstractMapFactory factory = new V1MapFactory();
        Map theMap = factory.createMap(1);
        ArrayList<Player> players=factory.createPlayers(1,theMap);

        Player p1=players.get(0);
        theMap.getTerritories().get(0).moveIn(new BasicUnit(5,p1));
        theMap.getTerritories().get(1).moveIn(new BasicUnit(4,p1));
        theMap.getTerritories().get(2).moveIn(new BasicUnit(3,p1));
        theMap.getTerritories().get(3).moveIn(new BasicUnit(2,p1));
        theMap.getTerritories().get(4).moveIn(new BasicUnit(1,p1));
        theMap.getTerritories().get(5).moveIn(new BasicUnit(9,p1));

        MovementRuleChecker checker= new NumberRuleChecker(null) ;
        Action action1 =new MoveAction(p1,"a1","a3",3,theMap);
        assertNull(checker.checkMyRule(action1));

        Action action2 =new MoveAction(p1,"a1","a3",5,theMap);
        assertNull(checker.checkAllRule(action2));

        Action action3 =new MoveAction(p1,"a1","a3",6,theMap);
        assertEquals("Requested 6 units, but only have 5",checker.checkAllRule(action3));



    }
}
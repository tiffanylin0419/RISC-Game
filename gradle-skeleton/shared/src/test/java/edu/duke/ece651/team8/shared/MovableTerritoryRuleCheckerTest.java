package edu.duke.ece651.team8.shared;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class MovableTerritoryRuleCheckerTest {

    @Test
    public void test_checkMyRule() {

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

        MovableActionRuleChecker checker= new MovableTerritoryRuleChecker(new NumberRuleChecker(null)) ;
        MovableAction action1 =new MoveAction(p1,"a1","a7",3,theMap);
        assertEquals("Destination a7 not in map",checker.checkAllRule(action1));


        MovableAction action2 =new MoveAction(p1,"a9","a2",3,theMap);
        assertEquals("Source a9 not in map",checker.checkAllRule(action2));

        MovableAction action3 =new MoveAction(p1,"a1","a2",3,theMap);
        assertNull(checker.checkAllRule(action3));

    }
}
package edu.duke.ece651.team8.shared;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class PathRuleCheckerTest {

    @Test
    void test_checkMyRule() {

        AbstractMapFactory factory = new V1MapFactory();
        Map theMap = factory.createMap(2);
        ArrayList<Player> players =factory.createPlayers(2,theMap);

        Player p1= players.get(0);
        Player p2= players.get(1);
        theMap.getTerritories().get(0).moveIn(new BasicArmy(5,p1));
        theMap.getTerritories().get(1).moveIn(new BasicArmy(4,p1));
        theMap.getTerritories().get(2).moveIn(new BasicArmy(3,p1));
        theMap.getTerritories().get(3).moveIn(new BasicArmy(2,p1));
        theMap.getTerritories().get(6).moveIn(new BasicArmy(2,p2));

        MovableActionRuleChecker checker= new TerritoryRuleChecker(new OwnershipRuleChecker(new NumberRuleChecker(new PathRuleChecker(null)))) ;
        MovableAction action1 =new MoveAction(p1,"a1","a4",3, theMap);
        assertEquals("Do not have enough food", checker.checkAllRule(action1));
        theMap.getTerritories().get(0).moveOut(new BasicArmy(4,p1));
        theMap.getTerritories().get(0).attack();
        //assertFalse(theMap.getTerritories().get(1).isOwner(p1));
        assertEquals("Requested 3 units, but only have 1",checker.checkAllRule(action1));

        MovableAction action2 =new AttackAction(p1,"a2","b1",1,theMap);
        assertEquals("Units in a2 cannot go to b1",checker.checkAllRule(action2));

    }
}


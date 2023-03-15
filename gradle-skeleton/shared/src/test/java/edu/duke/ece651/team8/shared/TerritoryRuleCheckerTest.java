package edu.duke.ece651.team8.shared;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class TerritoryRuleCheckerTest {

    @Test
    public void test_checkMyRule() {

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

        MovementRuleChecker checker= new TerritoryRuleChecker(new NumberRuleChecker(null)) ;
        Action action1 =new MoveAction(p1,"a1","a7",3,theMap);
        assertEquals("Destination a7 not in map",checker.checkMyRule(theMap,action1));


        Action action2 =new MoveAction(p1,"a9","a2",3,theMap);
        assertEquals("Source a9 not in map",checker.checkMyRule(theMap,action2));


    }
}
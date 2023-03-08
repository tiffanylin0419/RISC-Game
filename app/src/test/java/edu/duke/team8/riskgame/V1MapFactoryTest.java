package edu.duke.team8.riskgame;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import java.io.IOException;
import java.util.HashSet;

public class V1MapFactoryTest {
    @Test
    public void test_constructor() {
        HashSet<Territory> territories = new HashSet<>();
        Territory t1 = new BasicTerritory("a");
        Territory t2 = new BasicTerritory("b");
        territories.add(t1);
        territories.add(t2);
        V1MapFactory factory = new V1MapFactory(territories, 2);
        assertEquals(true, t1.isAdjacentEnemy(t2));
        assertEquals(false, t2.isAdjacentSelf(t1));
    }

    @Test
    public void test_createMap() {
        HashSet<Territory> territories = new HashSet<>();
        Territory t1 = new BasicTerritory("a");
        Territory t2 = new BasicTerritory("b");
        territories.add(t1);
        territories.add(t2);
        V1MapFactory factory = new V1MapFactory(territories, 2);
        Game1Map map = factory.createMap();
    }
}

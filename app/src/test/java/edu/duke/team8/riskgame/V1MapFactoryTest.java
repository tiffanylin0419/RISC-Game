package edu.duke.team8.riskgame;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;

public class V1MapFactoryTest {
    @Test
    public void test_constructor() {
        V1MapFactory factory = new V1MapFactory(2);
        ArrayList<Territory> list = factory.getTerritories();
        assertEquals(false, list.get(0).isAdjacentEnemy(list.get(6)));
//        assertEquals(true, list.get(12).isAdjacentSelf(list.get(6)));
//        assertEquals(true, list.get(12).isAdjacentEnemy(list.get(6)));
        assertEquals(true, list.get(0).isAdjacentSelf(list.get(1)));

    }

    @Test
    public void test_createMap() {
        V1MapFactory factory = new V1MapFactory(2);
        Game1Map map = factory.createMap();
    }
}

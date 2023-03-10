package edu.duke.ece651.team8.shared;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;

public class V1MapFactoryTest {
    @Test
    public void testConstructor() {
        V1MapFactory factory = new V1MapFactory(2);
        ArrayList<Territory> territories = factory.getTerritories();
        Game1Map map = factory.createMap();
        assertEquals(24, factory.getTerritories().size());
        assertEquals("a1", factory.getTerritories().get(0).getName());
        assertEquals("a6", factory.getTerritories().get(5).getName());
        assertEquals("d6", factory.getTerritories().get(23).getName());
    }

    @Test
    public void testCreateMap() {
        V1MapFactory factory = new V1MapFactory(2);
        Game1Map map = factory.createMap();
        assertEquals(factory.getTerritories().get(0), map.getTerritoryIterator().next());
    }
}

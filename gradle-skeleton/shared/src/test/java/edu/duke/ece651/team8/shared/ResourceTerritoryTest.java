package edu.duke.ece651.team8.shared;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class ResourceTerritoryTest {
    @Test
    public void TestProduceFoodAndTechResource() {
        ResourceTerritory r = new ResourceTerritory("my territory");
        FoodResource fr = new FoodResource(5);
        TechResource tr = new TechResource(7);

        r.produceFoodResource(fr);
        r.produceTechResource(tr);

    }

    @Test
    public void testKillAllUnits() {
        ResourceTerritory territory = new ResourceTerritory("a");
        territory.killAllUnits();
    }
}

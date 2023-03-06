package edu.duke.team8.riskgame;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class MapTextViewTest {
    @Test
    public void testDisplayMap() throws IOException {
        Map m = new Game1Map();
        m.addTerritory(new BasicTerritory("Planto"));
        MapTextView v = new MapTextView(m);
        assertEquals("Planto", v.displayMap());
    }

    private String parseExpected() {
        StringBuilder sb = new StringBuilder();
        sb.append("Yellow player:\n-----------\n");
        sb.append("<amount> units in Aterritory (next to: Bterritory, )\n");
        sb.append("Green player:\n-----------\n");
        sb.append("<amount> units in Bterritory (next to: Aterritory, )\n");
        return sb.toString();
    }
    @Test
    public void displayEachPlayerInfoTest() {
        Map map = new Game1Map();
        TextPlayer p1 = new TextPlayer("Yellow");
        TextPlayer p2 = new TextPlayer("Green");
        BasicTerritory t1 = new BasicTerritory("Aterritory");
        BasicTerritory t2 = new BasicTerritory("Bterritory");
        p1.addTerritory(t1);
        t1.addAdjacent(t2);
        p2.addTerritory(t2);
        t2.addAdjacent(t1);
        map.addPlayer(p1);
        map.addPlayer(p2);
        map.addPlayer(p2);
        MapTextView view = new MapTextView(map);
        assertEquals(parseExpected(), view.displayEachPlayerInfo());
    }

}
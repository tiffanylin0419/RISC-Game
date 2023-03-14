package edu.duke.ece651.team8.shared;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MapTextViewTest {
    @Test
    public void testDisplayMap() throws IOException {
        AbstractMapFactory factory = new V1MapFactory();
        Map m = factory.createMap(1);
        MapTextView v = new MapTextView(m);
        List<String> colorList = new ArrayList<String>();
        String colors[] = { "Green", "Red", "Blue", "Yellow" };
        for(int i = 0; i < 4; i++) {
            colorList.add(colors[i]);
        }
        assertEquals("Green Player:\n" +
                "-------------\n" +
                "0 units in a1 (next to: a2)\n" +
                "0 units in a2 (next to: a1, a3)\n" +
                "0 units in a3 (next to: a2, a4)\n" +
                "0 units in a4 (next to: a3, a5)\n" +
                "0 units in a5 (next to: a4, a6)\n" +
                "0 units in a6 (next to: a5)", v.displayMap(colorList));
    }
    @Test
    public void testDisplayUnitInfo() throws IOException {
        Map m = new Game1Map();
        Territory t = new BasicTerritory("Planto");
        Player p = new TextPlayer("Green");
        Unit u = new BasicUnit(5, p);
        t.moveIn(u);
        m.addTerritory(t);
        MapTextView v = new MapTextView(m);

        StringBuilder sb = new StringBuilder();
        v.displayUnitInfo(sb, t);
        assertEquals("5 units in ", sb.toString());
    }


    @Test
    public void testDisplayAdjacentInfo() {
        Map map = new Game1Map();
        Territory t = new BasicTerritory("Planto");
        Territory a1 = new BasicTerritory("a");
        Territory a2 = new BasicTerritory("b");
        Territory a3 = new BasicTerritory("c");
        t.addAdjacent(a1);
        t.addAdjacent(a2);
        t.addAdjacent(a3);
        map.addTerritory(t);
        MapTextView v = new MapTextView(map);
        StringBuilder sb = new StringBuilder();
        sb.append(v.displayAdjacentInfo(t));
        assertEquals(" (next to: a, b, c)\n", sb.toString());
    }

}
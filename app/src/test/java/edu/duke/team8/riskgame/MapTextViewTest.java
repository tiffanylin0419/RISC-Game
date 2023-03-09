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
        assertEquals("0 units in Planto (next to: )", v.displayMap());
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
package edu.duke.team8.riskgame;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class MapTextViewTest {
    @Test()
    public void testDisplayMap() throws IOException {
        Map m = new Game1Map();
        m.addTerritory(new BasicTerritory("Planto"));
        MapTextView v = new MapTextView(m);
        assertEquals("Planto", v.displayMap());
    }

}
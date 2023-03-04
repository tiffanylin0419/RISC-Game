package edu.duke.team8.riskgame;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class Game1MapTest {
  @Test
  public void test_addTerritory_containsTerritory() {
    Game1Map map = new Game1Map();
    String names[] = { "AbcdE", "hello", "num123" };
    for (int i = 0; i < 3; i++) {
      map.addTerritory(new BasicTerritory(names[i]));
    }
    for (int i = 0; i < 3; i++) {
      assertTrue(map.containsTerritory(new BasicTerritory(names[i])));
    }
    assertFalse(map.containsTerritory(new BasicTerritory("nope")));
  }

}

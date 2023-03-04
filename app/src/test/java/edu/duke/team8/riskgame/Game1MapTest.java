package edu.duke.team8.riskgame;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

public class Game1MapTest {
  @Test
  public void test_addTerritory_containsTerritory() {
    Map map = new Game1Map();
    String names[] = { "AbcdE", "hello", "num123" };
    for (int i = 0; i < 3; i++) {
      map.addTerritory(new BasicTerritory(names[i]));
    }
    for (int i = 0; i < 3; i++) {
      assertTrue(map.containsTerritory(new BasicTerritory(names[i])));
    }
    assertFalse(map.containsTerritory(new BasicTerritory("nope")));
  }

  @Test
  public void test_addAdjacency(){
    Map map = new Game1Map();
    Territory territories[]={new BasicTerritory("a"),new BasicTerritory("b"),new BasicTerritory("c"),new BasicTerritory("d"),new BasicTerritory("e")};
    for(Territory t: territories){
      map.addTerritory(t);
    }
    map.addAdjacency(territories[0],territories[1]);
    map.addAdjacency(territories[0],territories[4]);
    map.addAdjacency(territories[1],territories[3]);
    map.addAdjacency(territories[2],territories[4]);
    //check
    assertTrue(territories[0].isAdjacentEnemy(territories[1]));
    assertTrue(territories[1].isAdjacentEnemy(territories[0]));
    assertTrue(territories[0].isAdjacentEnemy(territories[4]));
    assertTrue(territories[4].isAdjacentEnemy(territories[0]));
    assertTrue(territories[1].isAdjacentEnemy(territories[3]));
    assertTrue(territories[3].isAdjacentEnemy(territories[1]));
    assertTrue(territories[2].isAdjacentEnemy(territories[4]));
    assertTrue(territories[4].isAdjacentEnemy(territories[2]));

    assertFalse(territories[0].isAdjacentEnemy(territories[2]));
    assertFalse(territories[4].isAdjacentEnemy(territories[1]));
  }
}

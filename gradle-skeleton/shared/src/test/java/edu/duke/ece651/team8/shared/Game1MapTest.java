package edu.duke.ece651.team8.shared;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;

import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.Test;

public class Game1MapTest {
  @Test
  public void test_constructor(){
    ArrayList<Territory> territories=new ArrayList<>();
    ArrayList<Player> players =new ArrayList<>();
    players.add(new TextPlayer("red"));
    players.add(new TextPlayer("blue"));
    String names[] = { "AbcdE", "hello", "num123" };
    territories.add(new BasicTerritory(names[0]));
    territories.add(new BasicTerritory(names[1]));
    Map map = new Game1Map(territories);
    assertTrue(map.containsTerritory(new BasicTerritory(names[0])));
    assertTrue(map.containsTerritory(new BasicTerritory(names[1])));
  }
  @Test
  public void testConstructor() {
    V1MapFactory factory = new V1MapFactory();
    Game1Map map = factory.createMap(4);
    assertEquals("a6", map.getTerritories().get(5).getName());
  }
  
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
  public void test_get_territories_iterator() {
    Game1Map map = new Game1Map();
    String names[] = { "AbcdE", "hello", "num123" };
    Territory t = new BasicTerritory(names[0]);
    Territory t1 = new BasicTerritory(names[1]);
    map.addTerritory(t);
    map.addTerritory(t1);
    assertEquals(t, map.getTerritoryIterator().next());
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

  @Test
  public void testDoCombats() {
    Map map = new Game1Map();
    Territory territory1 = new BasicTerritory("a");
    Territory territory2 = new BasicTerritory("b");
    map.addTerritory(territory1);
    map.addTerritory(territory2);

    Player player1 = new TextPlayer("p1");
    Player player2 = new TextPlayer("p2");
    Player player3 = new TextPlayer("p3");
    territory1.moveIn(new BasicArmy(20, player1));
    map.doCombats();
    map.getOutcome();
    assertEquals(21, territory1.getUnitAmount(0));
    territory1.moveIn(new BasicArmy(4, player2));
    map.doCombats();
    assertEquals("Player p1 wins combat in a\n", map.getOutcome());
    territory1.moveIn(new BasicArmy(20, player2));
    territory1.moveIn(new BasicArmy(1000, player3));
    map.doCombats();
    assertEquals("Player p3 wins combat in a\n", map.getOutcome());
  }

  @Test
  public void test_getWinner(){
    Map map = new Game1Map();
    Territory territory1 = new BasicTerritory("a");
    Territory territory2 = new BasicTerritory("b");
    map.addTerritory(territory1);
    map.addTerritory(territory2);

    assertEquals("",map.getWinner());

    Player player1 = new TextPlayer("p1");
    Player player2 = new TextPlayer("p2");
    player2.addTerritory(territory1);
    player2.addTerritory(territory2);
    ArrayList<Player> players=new ArrayList<>();
    players.add(player1);
    players.add(player2);
    map.addPlayers(players);
    assertEquals(player2.getColor(),map.getWinner());
  }

  @Test
  public void test(){
    Map map = new Game1Map();
    ResearchActionRuleChecker rarc=map.getResearchRuleChecker() ;

  }
}

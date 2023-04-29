package edu.duke.ece651.team8.shared;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class MapGuiViewTest {
    @Test
    public void test(){
        V2MapFactory factory = new V2MapFactory();
        Game1Map map = factory.createMap(4);
        ArrayList<Player> players = factory.createPlayers(4, map);

        for(int i=0;i<6;i++){
            map.getTerritories().get(i).resetStatus();
        }
        players.get(0).seen_territories.put("d3","hi");

        
        MapGuiView mgv =new MapGuiView();
        mgv.displayMap(map);
        map.getTerritories().get(0).setCloakingStatus();
        mgv.displayPlayerMap(map,players.get(0));


    }

}
package edu.duke.ece651.team8.shared;

import java.util.ArrayList;
import java.util.HashSet;

public class MapGuiView implements View {

    public MapGuiView() {}
    @Override
    public String displayMap(Map theMap) {
        ArrayList<Player> players = theMap.getPlayers();
        StringBuilder sb = new StringBuilder();
        sb.append("{\"map\":{\n");
        for(Territory t: theMap.getTerritories()){
            displayTerritoryInfo(sb,t);
        }
        sb.append("}\n}");
        return sb.toString();
    }

    /*public String displayPlayerMap(Map theMap, Player player) {
        ArrayList<Player> players = theMap.getPlayers();
        StringBuilder sb = new StringBuilder();
        sb.append("{\"map\":{\n");
        HashSet<Territory> checked_territories= new HashSet<>();
        ArrayList<Territory> territories=new ArrayList<>();
        for(Territory t: theMap.getTerritories()){
            if(t.getOwner().getColor().equals(player.getColor())){
                territories.add(t);
            }
        }
        for(Territory t: territories){
            displayTerritoryInfo(sb,t);
        }
        checked_territories.addAll(territories);

        //black
        for(Territory t: theMap.getTerritories()){
            if(!checked_territories.contains(t)){
                displayBlackTerritoryInfo(sb,t);
            }
        }
        player.seen_territories.addAll(checked_territories);
        sb.append("}\n}");
        return sb.toString();

    }*/

    public String displayPlayerMap(Map theMap, Player player) {
        StringBuilder sb = new StringBuilder();
        sb.append("{\"map\":{\n");
        HashSet<Territory> checked_territories= new HashSet<>();
        //own territory
        ArrayList<Territory> territories=new ArrayList<>();
        for(Territory t: theMap.getTerritories()){
            if(t.isOwner(player)){
                territories.add(t);
            }
        }
        for(Territory t: territories){
            displayTerritoryInfo(sb,t);
        }
        checked_territories.addAll(territories);
        //adjacent territory
        HashSet<Territory> adj_territories=new HashSet<>();
        for(Territory t: territories){
            adj_territories.addAll(t.getAdjacent());
        }
        for(Territory t: adj_territories){
            if(!checked_territories.contains(t)){
                displayTerritoryInfo(sb,t);
            }
        }
        checked_territories.addAll(adj_territories);
        //grey
        for(Territory t: player.seen_territories){
            if(!checked_territories.contains(t)){
                displayGreyTerritoryInfo(sb,t);
            }
        }
        checked_territories.addAll(player.seen_territories);
        //black
        for(Territory t: theMap.getTerritories()){
            if(!checked_territories.contains(t)){
                displayBlackTerritoryInfo(sb,t);
            }
        }
        player.seen_territories.addAll(checked_territories);
        sb.append("}\n}");
        return sb.toString();
    }

    @Override
    public void displayUnitInfo(StringBuilder sb, Territory t) {
        sb.append("    \"army\":\"");
        for(int i=0;i<=6;i++){
            sb.append("L"+i+": "+t.getOwnerUnitLevelAmount(i)+"\\n");
        }
        ResourceTerritory tt=(ResourceTerritory) t;
        sb.append("Food: "+tt.getAddFood()+"\\n");
        sb.append("Tech: "+tt.getAddTech()+"\\n");
        sb.append("\"\n");
    }

    public void displayTerritoryInfo(StringBuilder sb, Territory t) {
        sb.append("  \"").append(t.getName()).append("\":{\n");
        sb.append("    \"color\":\"").append(t.getOwner().getColor()).append("\",\n");
        displayUnitInfo(sb,t);
        sb.append("  },\n");
    }
    public void displayGreyTerritoryInfo(StringBuilder sb, Territory t) {
        sb.append("  \"").append(t.getName()).append("\":{\n");
        sb.append("    \"color\":\"").append("Grey").append("\",\n");
        sb.append("    \"army\":\"Do not have info\"");
        sb.append("  },\n");
    }

    public void displayBlackTerritoryInfo(StringBuilder sb, Territory t) {
        sb.append("  \"").append(t.getName()).append("\":{\n");
        sb.append("    \"color\":\"").append("Black").append("\",\n");
        sb.append("    \"army\":\"Do not have info\"");
        sb.append("  },\n");
    }
}

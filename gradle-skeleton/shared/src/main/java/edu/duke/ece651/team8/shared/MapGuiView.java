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

    /**
     * display map for different players
     * @param theMap: map for a game
     * @param player: the player who wants to see
     * @return a string of map info in json format
     */
    public String displayPlayerMap(Map theMap, Player player) {
        StringBuilder sb = new StringBuilder();
        sb.append("{\"map\":{\n");
        //all territory
        HashSet<Territory> checked_territories= new HashSet<>();
        //cloaked territory
        HashSet<Territory> cloak_territories=new HashSet<>();
        for(Territory t: theMap.getTerritories()){
            if(t.isCloaking()){
                cloak_territories.add(t);
            }
        }

        //spy territory
        HashSet<Territory> spy_territories=new HashSet<>();
        for(Territory t: theMap.getTerritories()){
            if(t.getPlayerSpyArmy(player).getAllAmount()>0){
                spy_territories.add(t);
            }
        }
        for(Territory t: spy_territories){
            if(!checked_territories.contains(t)){
                displayPlayerTerritoryInfo(sb,t, player);
                checked_territories.add(t);
            }
        }
        //own territory
        ArrayList<Territory> territories=new ArrayList<>();
        for(Territory t: theMap.getTerritories()){
            if(t.isOwner(player)){
                territories.add(t);
            }
        }
        for(Territory t: territories){
            if(!checked_territories.contains(t)){
                displayPlayerTerritoryInfo(sb,t, player);
                checked_territories.add(t);
            }
        }
        //adjacent territory
        HashSet<Territory> adj_territories=new HashSet<>();
        for(Territory t: territories){
            adj_territories.addAll(t.getAdjacent());
        }
        for(Territory t: adj_territories){
            if(!checked_territories.contains(t) && !cloak_territories.contains(t)){
                displayPlayerTerritoryInfo(sb,t, player);
                checked_territories.add(t);
            }
        }
        //grey
        HashSet<Territory> seen_territories2=new HashSet<>();
        for(Territory t: theMap.getTerritories()){
            if(player.seen_territories.containsKey(t.getName())){
                seen_territories2.add(t);
            }
        }
        for(Territory t: seen_territories2){
            if(!checked_territories.contains(t)){
                String tname=t.getName();
                String info=player.seen_territories.get(tname);
                displayGreyTerritoryInfo(sb,tname,info);
            }
        }
        checked_territories.addAll(seen_territories2);
        //black
        for(Territory t: theMap.getTerritories()){
            if(!checked_territories.contains(t)){
                displayBlackTerritoryInfo(sb,t);
            }
        }
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


    public String displayPlayerUnitInfo(StringBuilder sb, Territory t, Player player) {
        StringBuilder sb2 = new StringBuilder("");
        sb2.append("    \"army\":\"");
        for(int i=0;i<=6;i++){
            sb2.append("L"+i+": "+t.getOwnerUnitLevelAmount(i)+"\\n");
        }
        sb2.append("My Spy: "+t.getSpyAmount(player)+"\\n");
        ResourceTerritory tt=(ResourceTerritory) t;
        sb2.append("Food: "+tt.getAddFood()+"\\n");
        sb2.append("Tech: "+tt.getAddTech()+"\\n");
        if(tt.isCloaking()){
            sb2.append("Is cloaked\\n");
        }
        sb2.append("Status: ");
        ResourceTerritory territory=(ResourceTerritory)t;
        if(territory.getStarvationStatus()){
            sb2.append("starvation\\n");
        }else if(territory.getFreezeStatus()){
            sb2.append("freeze\\n");
        }else if(territory.getMeteorStatus()){
            sb2.append("meteor\\n");
        }else{
            sb2.append("normal\\n");
        }

        sb2.append("\"\n");
        String ret=sb2.toString();
        sb.append(sb2);
        return ret;
    }

    public void displayTerritoryInfo(StringBuilder sb, Territory t) {
        sb.append("  \"").append(t.getName()).append("\":{\n");
        sb.append("    \"color\":\"").append(t.getOwner().getColor()).append("\",\n");
        displayUnitInfo(sb,t);
        sb.append("  },\n");
    }

    public void displayPlayerTerritoryInfo(StringBuilder sb, Territory t, Player player) {
        sb.append("  \"").append(t.getName()).append("\":{\n");
        sb.append("    \"color\":\"").append(t.getOwner().getColor()).append("\",\n");
        String info=displayPlayerUnitInfo(sb,t, player);
        sb.append("  },\n");
        player.seen_territories.put(t.getName(),info);
    }

    public void displayGreyTerritoryInfo(StringBuilder sb, String tname,String info) {
        sb.append("  \"").append(tname).append("\":{\n");
        sb.append("    \"color\":\"").append("Grey").append("\",\n");
        sb.append(info);
        sb.append("  },\n");
    }

    public void displayBlackTerritoryInfo(StringBuilder sb, Territory t) {
        sb.append("  \"").append(t.getName()).append("\":{\n");
        sb.append("    \"color\":\"").append("Black").append("\",\n");
        sb.append("    \"army\":\"Do not have info\"");
        sb.append("  },\n");
    }
}

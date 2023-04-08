package edu.duke.ece651.team8.shared;

import java.util.ArrayList;

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
        sb.append("}\n}\n");
        sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }

    @Override
    public void displayUnitInfo(StringBuilder sb, Territory t) {
        sb.append("    \"army\":\"").append(t.getOwnerUnitAmount()).append("\"\n");
    }

    public void displayTerritoryInfo(StringBuilder sb, Territory t) {
        sb.append("  \"").append(t.getName()).append("\":{\n");
        sb.append("    \"color\":\"").append(t.getOwner().getColor()).append("\",\n");
        displayUnitInfo(sb,t);
        sb.append("  },\n");
    }
}

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
        sb.append("}\n}");
        return sb.toString();
    }

    @Override
    public void displayUnitInfo(StringBuilder sb, Territory t) {
        sb.append("    \"army\":\"");
        for(int i=0;i<=6;i++){
            sb.append("L"+i+": "+t.getOwnerUnitLevelAmount(i)+"\\n");
            //sb.append(t.produceResource());
        }
        sb.append("\"\n");
    }

    public void displayTerritoryInfo(StringBuilder sb, Territory t) {
        sb.append("  \"").append(t.getName()).append("\":{\n");
        sb.append("    \"color\":\"").append(t.getOwner().getColor()).append("\",\n");
        displayUnitInfo(sb,t);
        sb.append("  },\n");
    }
}

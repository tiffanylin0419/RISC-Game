package edu.duke.ece651.team8.shared;

import java.util.ArrayList;


public class MapTextView implements View {

    public MapTextView() {}
    @Override
    public String displayMap(Map theMap) {
        ArrayList<Player> players = theMap.getPlayers();
        StringBuilder sb = new StringBuilder();
        for(Player player : players) {
            sb.append(player.getColor()).append(" Player:\n-------------\n");
            for(Territory t : player.getTerritories()) {
                displayUnitInfo(sb, t);
                sb.append(t.getName());
                sb.append(displayAdjacentInfo(t));
            }
        }
        sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }

    @Override
    public void displayUnitInfo(StringBuilder sb, Territory t) {
        int num = t.getUnitAmount(0);
        sb.append(num).append(" units in ");
    }


    /**
     * append adjacent territories info to string
     * @param t territory to display
     * @return adjacent info string
     */
    public String displayAdjacentInfo(Territory t) {
        StringBuilder sb = new StringBuilder();
        sb.append(" (next to: ");
        ArrayList<Territory> adj = t.getAdjList();
        int size = adj.size();
        for (int i = 0; i < size; ++i) {
            sb.append(adj.get(i).getName());
            if (i != size - 1) {
                sb.append(", ");
            }
        }
        sb.append(")\n");
        return sb.toString();
    }
}

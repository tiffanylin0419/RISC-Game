package edu.duke.ece651.team8.shared;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MapTextView implements View {
    /**
     * The map to display
     */
    private final Map toDisplay;

    public MapTextView(Map toDisplay) {
        this.toDisplay = toDisplay;
    }
    @Override
    public String displayMap(List<String> color) {
        ArrayList<ArrayList<Territory>> groups = toDisplay.getTerritoryGroups();
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < groups.size(); i++) {
            sb.append(color.get(i) + " Player:\n-------------\n");
            for(Territory t : groups.get(i)) {
                displayUnitInfo(sb, t);
                sb.append(t.getName());
                sb.append(displayAdjacentInfo(t));
            }
        }
        if(sb.length() > 0) {
            sb.deleteCharAt(sb.length() - 1);
        }
        return sb.toString();
    }

    @Override
    public void displayUnitInfo(StringBuilder sb, Territory t) {
        int num = t.getUnitAmount(0);
        sb.append(num + " units in ");
    }

    /**
     * append adjacent territories info to string
     * @param t
     * @return
     */
    @Override
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

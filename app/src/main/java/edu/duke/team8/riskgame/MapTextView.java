package edu.duke.team8.riskgame;

import java.util.ArrayList;
import java.util.Iterator;

public class MapTextView implements View {
    /**
     * The map to display
     */
    private final Map toDisplay;

    public MapTextView(Map toDisplay) {
        this.toDisplay = toDisplay;
    }
    @Override
    public String displayMap() {
        Iterator<Territory> it = toDisplay.getTerritoryIterator();
        StringBuilder sb = new StringBuilder();
        while(it.hasNext()) {
            Territory t = it.next();
            displayUnitInfo(sb, t);
            sb.append(t.getName());
            sb.append(displayAdjacentInfo(t));
        }
        sb.deleteCharAt(sb.length() - 1);
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

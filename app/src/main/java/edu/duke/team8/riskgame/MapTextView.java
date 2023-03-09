package edu.duke.team8.riskgame;

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

    @Override
    public String displayAdjacentInfo(Territory t) {
        StringBuilder sb = new StringBuilder();
        sb.append(" (next to: ");
        Iterator<Territory> it = t.getAdjacent().iterator();
        while (it.hasNext()) {
            sb.append(it.next().getName() + ", ");
        }
        sb.deleteCharAt(sb.length() - 1);
        sb.deleteCharAt(sb.length() - 1);
        sb.append(")");
        return sb.toString();
    }
}

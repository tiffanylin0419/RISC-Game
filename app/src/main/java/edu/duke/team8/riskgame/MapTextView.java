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
            sb.append(t.getName() + "\n");
        }
        sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }

}

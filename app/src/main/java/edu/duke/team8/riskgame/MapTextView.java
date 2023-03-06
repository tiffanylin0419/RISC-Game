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

    @Override
    public String displayEachPlayerInfo() {
        Iterator<Player> it = toDisplay.getPlayerIterator();
        StringBuilder sb = new StringBuilder();
        while (it.hasNext()) {
            Player player = it.next();
            sb.append(parseOnePlayerInfo(player));
        }
        return sb.toString();
    }

    private String parseOnePlayerInfo(Player p) {
        StringBuilder sb = new StringBuilder();
        sb.append(p.getColor() + " player:\n");
        sb.append("-----------\n");
        Iterator<Territory> it = p.getTerritoryIterator();
        while (it.hasNext()) {
            sb.append(parseEachTerritoryInfo(it.next()));
        }
        return sb.toString();
    }

    private String parseEachTerritoryInfo(Territory t) {
        StringBuilder sb = new StringBuilder();
        sb.append("<amount> units in " + t.getName() + " (next to: ");
        Iterator<Territory> it = t.getAdjacent().iterator();
        while (it.hasNext()) {
            sb.append(it.next().getName() + ", ");
        }
        sb.append(")\n");
        return sb.toString();
    }
}

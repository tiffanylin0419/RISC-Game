package edu.duke.ece651.team8.shared;

import java.util.*;

public class MinimumPath {
    Player player;
    Map theMap;

    public MinimumPath(Player player, Map theMap) {
        this.player = player;
        this.theMap = theMap;
    }


    public int findMinPath(Territory source, Territory destination) {
        if (source == destination) {
            return 0;
        }
        this.player = source.getOwner();
        int size = source.getAdjList().size();
        HashMap<Territory, Integer> distances = new HashMap<>();
        for (Territory t : theMap.getTerritories()) {
            distances.put(t, Integer.MAX_VALUE);
        }
        distances.put(source, 0);
        HashSet<Territory> visited = new HashSet<>();
        for (int i = 0; i < size - 1; ++i) {
            // next is the next territory to traverse
            Territory next = findNextTerritory(visited, distances);
            if (next == null) {
                break;
            }
            visited.add(next);
            HashMap<Territory, Integer> nextDistances = next.getDistances();
            for (Territory neighbor : nextDistances.keySet()) {
                if (!visited.contains(neighbor)) {
                    if (neighbor.getOwner().getColor().equals(this.player.getColor())) {
                        if (distances.get(next) + nextDistances.get(neighbor) < distances.get(neighbor)) {
                            distances.put(neighbor, distances.get(next) + nextDistances.get(neighbor));
                        }
                    }
                }
            }
        }
        return distances.get(destination);
    }

    protected Territory findNextTerritory(HashSet<Territory> visited,
                                        HashMap<Territory, Integer> distances) {
        int min = Integer.MAX_VALUE;
        Territory next = null;
        for (Territory t : distances.keySet()) {
            if (!visited.contains(t) && distances.get(t) < min) {
                if (t.getOwner() == this.player) {
                    min = distances.get(t);
                    next = t;
                }
            }
        }
        return next;
    }
}

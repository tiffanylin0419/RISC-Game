package edu.duke.ece651.team8.shared;

import java.util.*;

public class MinimumPath {
    Player player;
    HashMap<Territory, HashMap<Territory, Integer>> map;
    public MinimumPath() {
        this.player = null;
        map = new HashMap<>();
    }

    public int findMinPath(Territory source, Territory destination) {
        // todo
        if (source.equals(destination)) {
            return 0;
        }
        this.player = source.getOwner();
        int size = source.getAdjList().size();
        HashMap<Territory, HashMap<Territory, Integer>> map = new HashMap<>();
        for (Territory t : player.getTerritories()) {
            map.put(t, t.getDistances());
        }
        HashMap<Territory, Integer> distances = new HashMap<>();
        for (Territory t : source.getAdjacent()) {
            distances.put(t, Integer.MAX_VALUE);
        }
        distances.put(source, 0);
        HashSet<Territory> visited = new HashSet<>();
        visited.add(source);
        for (int i = 0; i < size - 1; ++i) {
            Territory next = findNextTerritory(visited, distances);
            if (next == null) {
                continue;
            }
            visited.add(next);
            HashMap<Territory, Integer> temp = next.getDistances();
            for (Territory t : temp.keySet()) {
                if (!visited.contains(t) && distances.get(next) != Integer.MAX_VALUE
                        && distances.get(next) + temp.get(t) < distances.get(t)) {
                    distances.put(t, distances.get(next) + distances.get(t));
                }
            }
        }
        return distances.get(destination);
    }

    private Territory findNextTerritory(HashSet<Territory> visited,
                                        HashMap<Territory, Integer> distances) {
        int min = Integer.MAX_VALUE;
        Territory next = null;
        for (Territory t : map.keySet()) {
            if (!visited.contains(t) && distances.get(t) < min) {
                min = distances.get(t);
                next = t;
            }
        }
        return next;
    }
}

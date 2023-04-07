package edu.duke.ece651.team8.shared;

import java.util.ArrayList;
import java.util.HashSet;

public class ResourceTerritory extends BasicTerritory {
    private FoodResource food;
    private TechResource tech;
    public ResourceTerritory(String name, FoodResource food, TechResource tech) {
        super(name);
        this.food = food;
        this.tech = tech;
    }
    public ResourceTerritory(String name, Player player, FoodResource food, TechResource tech) {
        super(name, player);
        this.food = food;
        this.tech = tech;
    }
}

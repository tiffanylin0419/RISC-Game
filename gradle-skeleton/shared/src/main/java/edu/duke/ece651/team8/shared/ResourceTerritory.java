package edu.duke.ece651.team8.shared;

import java.util.ArrayList;
import java.util.HashSet;

public class ResourceTerritory extends BasicTerritory {
    private FoodFactory foodFactory;
    private TechFactory techFactory;
    public ResourceTerritory(String name) {
        super(name);
        this.foodFactory = new FoodFactory();
        this.techFactory = new TechFactory();
    }
    public ResourceTerritory(String name, Player player) {
        super(name, player);
        this.foodFactory = new FoodFactory();
        this.techFactory = new TechFactory();
    }

    public Resource initFoodResource() {
        return foodFactory.produce(10);
    }

    public Resource initTechResource() {
        return techFactory.produce(10);
    }

    public Resource produceFoodResource() {
        return foodFactory.produce(5);
    }

    public Resource produceTechResource() {
        return techFactory.produce(5);
    }
}

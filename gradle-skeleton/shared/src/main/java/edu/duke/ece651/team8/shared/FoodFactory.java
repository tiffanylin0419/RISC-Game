package edu.duke.ece651.team8.shared;

import java.util.ArrayList;

public class FoodFactory implements ResourceFactory {
//    private int amount;
    public FoodFactory() {
    }

    @Override
    public ArrayList<Resource> produce(int amount) {
        ArrayList<Resource> resources = new ArrayList<>();
        FoodResource food = new FoodResource(amount);
        resources.add(food);
        return resources;
    }
}

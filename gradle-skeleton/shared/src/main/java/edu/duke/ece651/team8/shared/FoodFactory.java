package edu.duke.ece651.team8.shared;

import java.util.ArrayList;

public class FoodFactory implements ResourceFactory {
//    private int amount;
    public FoodFactory() {
    }

    @Override
    public Resource produce(int amount) {
        return new FoodResource(amount);
    }
}

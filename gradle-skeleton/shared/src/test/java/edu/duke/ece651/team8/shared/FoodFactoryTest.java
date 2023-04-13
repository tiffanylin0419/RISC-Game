package edu.duke.ece651.team8.shared;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FoodFactoryTest {

    @Test
    void TestProduce() {
        FoodFactory ff = new FoodFactory(5);
        FoodResource fr = new FoodResource(5);
        ff.produce(fr);
    }
}
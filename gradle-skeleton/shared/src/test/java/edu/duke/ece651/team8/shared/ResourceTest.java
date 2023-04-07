package edu.duke.ece651.team8.shared;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
public class ResourceTest {
    @Test
    public void testFoodResource() {
        FoodResource food = new FoodResource(10);
        food.addResource(5);
        assertEquals(15, food.getAmount());
        food.setResource(20);
        assertEquals(20, food.getAmount());
    }
    
    @Test
    public void testTechResource() {
        TechResource tech = new TechResource(10);
        tech.addResource(5);
        assertEquals(15, tech.getAmount());
        tech.setResource(20);
        assertEquals(20, tech.getAmount());
    }
}

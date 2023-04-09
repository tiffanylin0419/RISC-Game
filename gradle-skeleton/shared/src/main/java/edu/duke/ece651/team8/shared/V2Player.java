package edu.duke.ece651.team8.shared;

public class V2Player extends Player {
    private FoodResource food;
    private TechResource tech;

    public V2Player(String color) {
        super(color);
        this.food = new FoodResource(0);
        this.tech = new TechResource(0);
    }

    public FoodResource getFood() {
        return this.food;
    }

    public TechResource getTech() {
        return this.tech;
    }

    public void addFoodResource(Resource newFood) {
        this.food.addResource(newFood.getAmount());
    }
    public void addTechResource(Resource newTech) {
        this.tech.addResource(newTech.getAmount());
    }
}

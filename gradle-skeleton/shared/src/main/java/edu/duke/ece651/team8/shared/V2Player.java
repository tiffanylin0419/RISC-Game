package edu.duke.ece651.team8.shared;

public class V2Player extends Player {
    private FoodResource food;
    private TechResource tech;
    private int level=1;

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

    @Override
    public String display(){
        StringBuilder sb = new StringBuilder();
        sb.append("{\n\"level\":\""+level+"\",");
        sb.append("\n\"food\":\""+getFood().getAmount()+"\",");
        sb.append("\n\"tech\":\""+getTech().getAmount()+"\"");
        sb.append("\n}");
        return sb.toString();
    }
}

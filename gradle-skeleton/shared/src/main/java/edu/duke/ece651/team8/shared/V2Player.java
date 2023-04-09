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

    public int getFoodAmount() {
        return this.food.getAmount();
    }

    public int getTechAmount() {
        return this.tech.getAmount();
    }

    public void addFoodResource(int addAmount) {
        this.food.addResource(addAmount);
    }
    public void addTechResource(int addAmount) {
        this.tech.addResource(addAmount);
    }

    public void collectResources() {
        for (Territory t : territories) {
            addFoodResource(t.produceResource());
            addTechResource(t.produceResource());
        }
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

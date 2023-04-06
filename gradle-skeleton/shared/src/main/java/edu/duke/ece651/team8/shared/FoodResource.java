package edu.duke.ece651.team8.shared;

public class FoodResource implements Resource {
    private int amount;

    FoodResource(int amount) {
        this.amount = amount;
    }
    @Override
    public void setResource(int amount) {
        this.amount = amount;
    }

    @Override
    public void addResource(int amount) {
        this.amount += amount;
    }

    @Override
    public int getAmount() {
        return this.amount;
    }
}

package edu.duke.ece651.team8.shared;

public class TechResource implements Resource {
    private int amount;

    TechResource(int amount) {
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

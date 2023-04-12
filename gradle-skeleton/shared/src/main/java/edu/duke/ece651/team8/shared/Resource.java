package edu.duke.ece651.team8.shared;

public abstract class Resource {

    private int amount;

    public Resource(int amount){
        this.amount=amount;
    }
    public void setResource(int amount) {
        this.amount = amount;
    }

    public void addResource(int amount) {
        this.amount += amount;
    }

    public int getAmount() {
        return this.amount;
    }

}

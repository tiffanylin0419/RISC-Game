package edu.duke.ece651.team8.shared;

import java.util.ArrayList;

public abstract class ResourceFactory {
    public  int amount;
    public ResourceFactory(int amount) {
        this.amount=amount;
    }

    public abstract void produce(Resource resource) ;
}

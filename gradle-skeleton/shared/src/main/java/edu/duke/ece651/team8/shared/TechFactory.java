package edu.duke.ece651.team8.shared;

import java.util.ArrayList;

public class TechFactory extends ResourceFactory {
    public TechFactory(int amount){
        super(amount);
    }
    @Override
    public void produce(Resource resource) {
        resource.addResource(amount);
    }

}

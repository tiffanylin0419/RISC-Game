package edu.duke.ece651.team8.shared;

import java.util.ArrayList;

public class TechFactory implements ResourceFactory {
    @Override
    public ArrayList<Resource> produce(int amount) {
        ArrayList<Resource> resources = new ArrayList<>();
        TechResource tech = new TechResource(amount);
        resources.add(tech);
        return resources;
    }
}

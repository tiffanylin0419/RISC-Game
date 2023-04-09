package edu.duke.ece651.team8.shared;

import java.util.ArrayList;

public class TechFactory implements ResourceFactory {
    @Override
    public Resource produce(int amount) {
        return new TechResource(amount);
    }
}

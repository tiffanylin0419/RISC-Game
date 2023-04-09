package edu.duke.ece651.team8.shared;

import java.util.ArrayList;

public interface ResourceFactory {
    public ArrayList<Resource> produce(int amount);
}

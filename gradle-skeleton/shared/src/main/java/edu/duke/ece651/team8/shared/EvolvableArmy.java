package edu.duke.ece651.team8.shared;

import java.util.List;

public class EvolvableArmy extends AbstractArmy{
    //constructor
    public EvolvableArmy(int amount, Player owner){
        super(amount, owner);
    }

    public EvolvableArmy(Player owner, List<Unit> list){
        super(owner,list);
    }

}

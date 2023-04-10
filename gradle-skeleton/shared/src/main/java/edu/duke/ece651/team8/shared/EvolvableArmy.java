package edu.duke.ece651.team8.shared;

public class EvolvableArmy extends AbstractArmy{
    //constructor
    public EvolvableArmy(int amount, Player owner){
        super(amount, owner);
    }

    @Override
    public boolean isSurvive() {
        return false;
    }

    @Override
    public int doRoll() {
        return 0;
    }
}

package edu.duke.ece651.team8.shared;

import org.jetbrains.annotations.NotNull;

public class LevelOneUnit extends AbstractUnit{

    public LevelOneUnit() {
        super(1,"Assassin", 1);
    }
    //constructor

    @Override
    public Unit upgrade() {
        return new LevelTwoUnit();
    }

}

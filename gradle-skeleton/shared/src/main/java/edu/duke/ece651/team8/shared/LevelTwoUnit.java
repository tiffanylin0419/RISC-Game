package edu.duke.ece651.team8.shared;

import org.jetbrains.annotations.NotNull;

public class LevelTwoUnit extends AbstractUnit{
    public LevelTwoUnit() {
        super(2, "Rider", 3);
    }
    //constructor

    @Override
    public Unit upgrade() {
        return new LevelThreeUnit();
    }


}

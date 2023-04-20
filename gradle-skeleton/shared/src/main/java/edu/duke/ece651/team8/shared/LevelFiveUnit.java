package edu.duke.ece651.team8.shared;

import org.jetbrains.annotations.NotNull;

public class LevelFiveUnit extends AbstractUnit{

    public LevelFiveUnit() {
        super(5,"Berserker", 11);
    }
    //constructor

    @Override
    public Unit upgrade() {
        return new LevelSixUnit(); //change to level four later
    }

}

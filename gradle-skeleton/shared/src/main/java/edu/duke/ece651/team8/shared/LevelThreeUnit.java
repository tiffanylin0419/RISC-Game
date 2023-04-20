package edu.duke.ece651.team8.shared;

import org.jetbrains.annotations.NotNull;

public class LevelThreeUnit extends AbstractUnit{
    public LevelThreeUnit() {
        super(3, "Lancer", 5);
    }
    //constructor

    @Override
    public Unit upgrade() {
        return new LevelFourUnit(); //change to level four later
    }

}

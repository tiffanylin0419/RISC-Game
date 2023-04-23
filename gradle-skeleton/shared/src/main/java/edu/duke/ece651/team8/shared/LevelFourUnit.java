package edu.duke.ece651.team8.shared;

import org.jetbrains.annotations.NotNull;

public class LevelFourUnit extends AbstractUnit{
    public LevelFourUnit() {
        super(4, "Archer", 8);
    }
    //constructor


    @Override
    public Unit upgrade() {
        return new LevelFiveUnit(); //change to level four later
    }

    @Override
    public Unit downgrade() {
        return new LevelThreeUnit();
    }
}

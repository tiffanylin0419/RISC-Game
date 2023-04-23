package edu.duke.ece651.team8.shared;

import org.jetbrains.annotations.NotNull;

public class BasicUnit extends AbstractUnit{

    //constructor
    public BasicUnit() {
        super(0,"Servant",0);
    }

    @Override
    public Unit upgrade() {
        return new LevelOneUnit();
    }


//    @Override
//    public Unit downgrade() {
//
//    }

}

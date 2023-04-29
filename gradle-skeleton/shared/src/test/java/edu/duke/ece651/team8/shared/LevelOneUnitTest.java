package edu.duke.ece651.team8.shared;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LevelOneUnitTest {

    @Test
    public void test(){
        Unit unit=new LevelOneUnit();
        unit=unit.upgrade();
        unit=unit.upgrade();
        unit=unit.upgrade();
        unit=unit.upgrade();
        unit=unit.upgrade();
        unit=unit.upgrade();
        unit=unit.downgrade();
        unit=unit.downgrade();
        unit=unit.downgrade();
        unit=unit.downgrade();
        unit=unit.downgrade();
        unit=unit.downgrade();


        Unit unit2=new LevelTwoUnit();
        Unit unit3=new LevelThreeUnit();
        Unit unit4=new LevelFourUnit();
        Unit unit5=new LevelFiveUnit();
        Unit unit6=new LevelSixUnit();
    }

}
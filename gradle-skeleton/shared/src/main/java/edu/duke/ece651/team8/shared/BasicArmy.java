package edu.duke.ece651.team8.shared;

import java.util.List;
import java.util.Random;

public class BasicArmy extends AbstractArmy {
  
  //constructor
  public BasicArmy(int amount, Player owner){
    super(amount, owner);
  }

  @Override
  public boolean isSurvive(){
    return units.size()>0;
  }
  
  @Override
  public int doRoll() {
    Random rand = new Random();
    return rand.nextInt(20); 
  }

  public int getAmount() {
    return super.getAmount(0);
  }
}

package edu.duke.team8.riskgame;

import java.util.Random;

public class BasicUnit implements Unit {
  private int amount;
  private Player owner;
  
  //constructor
  public BasicUnit(int amount, Player owner){
    this.amount=amount;
    this.owner=owner;
  }
  
  @Override
  public void add(int n) {
    amount+=n;
  }

  @Override
  public void addOne() {
    add(1);
  }

  @Override
  public boolean tryRemove(int n){
    if(amount<n){
      return false;
    }
    amount-=n;
    return true;
  }
  
  @Override
  public boolean tryRemoveOne() {
    return tryRemove(1);
  }

  @Override
  public Player getOwner() {
    return owner;
  }

  @Override
  public int getAmount() {
    return amount;
  }

  @Override
  public int doRoll() {
    Random rand = new Random();
    return rand.nextInt(20); 
  }

}

package edu.duke.ece651.team8.shared;

import java.util.Random;

public class BasicUnit implements Unit {
  private int amount;
  private final Player owner;
  
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
  public void remove(int n){
    amount-=n;
  }
  
  @Override
  public void removeOne() {
    remove(1);
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
  public boolean isSurvive(){
    return amount>0;
  }
  
  @Override
  public int doRoll() {
    Random rand = new Random();
    return rand.nextInt(20); 
  }

}

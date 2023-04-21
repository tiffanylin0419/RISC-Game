package edu.duke.ece651.team8.shared;

public abstract class AbstractAction implements Action{
    protected Player player;

    public Player getPlayer(){
        return this.player;
    }
}

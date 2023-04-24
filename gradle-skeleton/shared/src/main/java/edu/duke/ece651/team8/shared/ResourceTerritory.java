package edu.duke.ece651.team8.shared;

import java.util.List;
import java.util.Random;

public class ResourceTerritory extends BasicTerritory {
    private FoodFactory foodFactory;
    private TechFactory techFactory;

    private int addFood=5;
    private int addTech=5;
    private boolean starvationStatus;
    private boolean freezeStatus;
    private boolean meteorStatus;

    public ResourceTerritory(String name) {
        super(name);
        this.foodFactory = new FoodFactory(addFood);
        this.techFactory = new TechFactory(addTech);
        this.starvationStatus = false;
        this.freezeStatus = false;
        this.meteorStatus = false;
    }
    @Override
    public void produceFoodResource(Resource resource) {
        foodFactory.produce(resource);
    }
    @Override
    public void produceTechResource(Resource resource) {
        techFactory.produce(resource);
    }
    public int getAddFood(){
        return addFood;
    }

    public int getAddTech(){
        return addTech;
    }

    @Override
    public boolean getStarvationStatus() {
        return this.starvationStatus;
    }

    @Override
    public boolean getFreezeStatus() {
        return this.freezeStatus;
    }

    @Override
    public boolean getMeteorStatus() {
        return this.meteorStatus;
    }

    private void getNewStatus() {
        Random random = new Random();
        int randomNumber = random.nextInt(100);
        if (randomNumber >= 0 && randomNumber < 15) {
            this.starvationStatus = true;
        } else if (randomNumber >= 15 && randomNumber < 30) {
            this.freezeStatus = true;
        } else if (randomNumber >= 30 && randomNumber < 40) {
            this.meteorStatus = true;
        }
    }
    @Override
    public void resetStatus() {
        this.starvationStatus = false;
        this.freezeStatus = false;
        this.meteorStatus = false;
        getNewStatus();
    }

    @Override
    public void downgradeUnits() {
        for (Army army : this.armies) {
            army.downgradeUnits();
        }
    }

    @Override
    public void removeAllUnits() {
        for (Army army : this.armies) {
            List<Unit> units = army.getList();
            for (Unit unit : units) {
                army.removeOne(unit);
            }
//            army.remove(army.getList());
        }
//        for (Army army : this.spyArmies) {
//            List<Unit> units = army.getList();
//            for (Unit unit : units) {
//                units.remove(unit);
//            }
//            army.remove(army.getList());
        }
    }
}

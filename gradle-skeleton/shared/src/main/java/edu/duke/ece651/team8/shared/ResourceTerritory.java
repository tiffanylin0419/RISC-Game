package edu.duke.ece651.team8.shared;

public class ResourceTerritory extends BasicTerritory {
    private FoodFactory foodFactory;
    private TechFactory techFactory;

    private int addFood=5;
    private int addTech=5;
    public ResourceTerritory(String name) {
        super(name);
        this.foodFactory = new FoodFactory(addFood);
        this.techFactory = new TechFactory(addTech);
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

}

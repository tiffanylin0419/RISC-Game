package edu.duke.ece651.team8.shared;

public class CloakAction extends AbstractAction{
    private Territory territory;

    private String territoryText;
    static int cloakCost = 50;
    public CloakAction(Player p, String territoryText){
        this.player = p;
        this.territoryText = territoryText;
        for (Territory t : player.getTerritories()) {
            if (t.getName().equals(territoryText)) {
                this.territory = t;
                break;
            }
        }
    }
    @Override
    public void doAction() {
        territory.setDoCloaking();
        player.addTechResource(-cloakCost);
    }
    public Territory getTerritory(){
        return this.territory;
    }

    public int costTechResource(){
        return cloakCost;
    }

    public String getTerritoryText(){
        return territoryText;
    }
}

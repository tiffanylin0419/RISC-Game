package edu.duke.ece651.team8.shared;

import java.util.ArrayList;
import java.util.List;

public class SpyArmy extends AbstractArmy{
    public SpyArmy(int amount, Player owner){
        super(owner, null);
        units = createSpyList(amount);
    }

//    public SpyArmy(Player owner, List<Unit> list){
//        super(owner,list);
//    }

    protected List<Unit> createSpyList(int amount){
        List<Unit> ans = new ArrayList<>();
        for(int i = 0; i< amount; i++){
            ans.add(new Spy());
        }
        return ans;
    }


}

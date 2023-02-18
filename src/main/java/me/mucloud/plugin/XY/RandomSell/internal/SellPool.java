package me.mucloud.plugin.XY.RandomSell.internal;

import me.mucloud.plugin.XY.RandomSell.Main;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class SellPool {

    private static List<SellRepo> Pool;

    public SellPool(){
        Pool = new ArrayList<>();
    }

    public SellRepo getSellRepo(Player p){
        for(SellRepo sr : Pool){
            if(sr.equals(p)){
                return sr;
            }
        }
        return regNewSellRepo(p);
    }

    private SellRepo regNewSellRepo(Player p){
        SellRepo target = new SellRepo(p, Main.INSTANCE.getPP().RandomProduct());
        Pool.add(target);
        return target;
    }

    public boolean isView(Player p){
        for(SellRepo sr : Pool){
            if(sr.equals(p)){
                return true;
            }
        }
        return false;
    }

    public boolean delSellRepo(Player p){
        for(SellRepo sr : Pool){
           if(sr.equals(p)){
               return true;
           }
        }
        return false;
    }

    public void clear(){
        Pool.clear();
    }

}

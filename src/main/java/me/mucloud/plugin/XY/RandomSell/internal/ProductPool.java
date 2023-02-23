package me.mucloud.plugin.XY.RandomSell.internal;

import me.mucloud.plugin.XY.RandomSell.Main;
import org.bukkit.Material;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.IOException;
import java.util.*;

public class ProductPool {

    private final Configuration C;
    private final List<Product> Pool;

    public ProductPool(Configuration c){
        C = c;
        Pool = new ArrayList<>();
    }

    public void regFromFile(){
        for (String s : C.getRAW_Product_LIST()) {
            if(s.split(",").length != 3){
                MainConsole.warn("当前收购项条目错误" + s + "   已跳过");
                continue;
            }
            String[] rawInf = s.split(",");
            int id = Integer.parseInt(rawInf[0]);
            if(contains(id)){
                MainConsole.warn("当前收购项条目错误" + s + "    已跳过");
                continue;
            }
            Material type;
            if((type = Material.matchMaterial(rawInf[1])) == null){
                MainConsole.warn("当前收购项条目错误" + s + "    已跳过");
                continue;
            }
            int amount = Integer.parseInt(rawInf[2]);
            double price = Double.parseDouble(rawInf[3]);

            Pool.add(new Product(id, type, amount, price));
        }
        MainConsole.sendMessage("§a§l加载了 " + Pool.size() + " 个商品");
    }

    public List<Product> RandomProduct(){
        if(C.getSell_RandomSize() > Pool.size()){
            return null;
        }
        List<Product> list = new ArrayList<>();
        for(int i = 0; i < C.getSell_RandomSize(); i++){
            int random = new Random().nextInt(Pool.size());
            if(!list.contains(Pool.get(random))){
                list.add(Pool.get(random));
            }
        }

        return list;
    }

    public boolean contains(Product p) {
        for(Product l : Pool){
            if(l.equals(p)){
                return true;
            }
        }
        return false;
    }

    public boolean contains(int id){
        for(Product p : Pool){
            if(p.equals(id)){
                return true;
            }
        }
        return false;
    }

    public Product addProduct(Material m, int amount, double price){
        for(int i = 0;; i++){
            if(!contains(i)){
                Product p = new Product(i, m, amount, price);
                if(!contains(p)){
                    Pool.add(p);
                    return p;
                }else{
                    return null;
                }
            }
        }
    }

    public void delProduct(int id){
        Pool.forEach(l -> {
            if(l.equals(id)){
                Main.INSTANCE.getSP().delProductForAll(l);

                FileConfiguration fc = new YamlConfiguration();
                try {
                    fc.load(C.getConfigFile());

                    List<String> ol = fc.getStringList("Sell.Products");
                    ol.remove(l.toConfig());

                    fc.set("Sell.Products", ol);
                } catch (IOException | InvalidConfigurationException e) {
                    throw new RuntimeException(e);
                }
                Pool.remove(l);
            }
        });
    }

    public void clear(){
        Pool.clear();
    }

    public int getID(Product p){
        for(Product l : Pool){
            if(l.equals(p)){
                return l.getID();
            }
        }
        return -1;
    }

    public int getSize(){
        return Pool.size();
    }

    public List<String> getList(){
        List<String> list = new ArrayList<>();
        Pool.forEach(l -> {
            list.add(l.toString());
        });

        return list;
    }

}

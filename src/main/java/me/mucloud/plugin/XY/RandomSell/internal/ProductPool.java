package me.mucloud.plugin.XY.RandomSell.internal;

import org.bukkit.Material;

import java.util.*;

public class ProductPool {

    private final Configuration C;
    private final Map<Integer, Product> Pool;

    public ProductPool(Configuration c){
        C = c;
        Pool = new HashMap<>();
    }

    public void regFromFile(){
        for (String s : C.getRAW_Product_LIST()) {
            if(s.split(",").length != 3){
                MainConsole.warn("当前收购项条目错误" + s + "   已跳过");
                continue;
            }
            String[] rawInf = s.split(",");
            int id = Integer.parseInt(rawInf[0]);
            if(Pool.containsKey(id)){
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

            Pool.put(id, new Product(type, amount, price));
        }
        MainConsole.sendMessage("§a§l加载了 " + Pool.size() + " 个商品");
    }

    public List<Product> RandomProduct(){
        if(C.getSell_RandomSize() > Pool.size()){
            return null;
        }

        List<Product> list = new ArrayList<>();
        for(int i = 0; i < C.getSell_RandomSize(); i++){
            Random r = new Random();
            if(Pool.containsKey(r.nextInt())){
                list.add(Pool.get(r.nextInt()));
            }
        }

        return list;
    }

    public boolean contains(Product p) {
        for(Product l : Pool.values()){
            if(l.equals(p)){
                return true;
            }
        }
        return false;
    }

    public boolean contains(int id){
        return Pool.containsKey(id);
    }

    public void addProduct(Product p){
        Pool.put(Collections.max(Pool.keySet()) +1, p);
    }

    public void delProduct(int id){
        Pool.remove(id);
    }

    public void clear(){
        Pool.clear();
    }

}

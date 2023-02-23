package me.mucloud.plugin.XY.RandomSell.internal;

import me.mucloud.plugin.XY.RandomSell.Main;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

public class SellRepo {

    private final List<Integer> BorderIndex = List.of(
            0,1,2,3,4,5,6,7,8,
            9,17,18,26,27,35,36,44,
            45,46,47,48,49,50,51,52,53
    );
    private final List<Product> Products;
    private final Player Target;
    private int Page;
    private boolean View;
    private boolean WaitingSell;
    private int WaitingProductID;
    private boolean noNext;
    private boolean noPrevious;

    public SellRepo(Player player, List<Product> initProducts){
        Target = player;
        Products = initProducts;
        Page = 0;
        View = false;
        WaitingSell = false;

        new BukkitRunnable(){
            @Override public void run() {
                Main.INSTANCE.getSP().delSellRepo(Target);
            }
        }.runTaskLater(Main.INSTANCE, Main.INSTANCE.getConfiguration().getRefreshInterval() *60 *20L);
    }

    public void toInv(){
        Inventory inv = Bukkit.createInventory(null,
                Main.INSTANCE.getConfiguration().getSell_RandomSize(),
                Messages.requestPlaceholder(Target, Messages.GUI_SELL_TITLE));


        List<ItemStack> list = new ArrayList<>(54);
        for(int i = Page *28; i < (Page -1) *28; i++){
            if(i == Products.size()){
                noNext = true;
                break;
            }
            for(int ii = 0; ii < 54; ii++){
                if(BorderIndex.contains(ii)){
                    continue;
                }
                list.add(ii, Products.get(i).toIcon(Target));
            }
        }
        if(Page == 0){
            noPrevious = true;
        }

        if(!noPrevious){
            ItemStack previous = new ItemStack(Material.AIR);
            ItemMeta im_previous = previous.getItemMeta();
            im_previous.setDisplayName("§b§l上一页");
            previous.setItemMeta(im_previous);
            inv.setItem(53, previous);
        }

        if(!noNext){
            ItemStack next = new ItemStack(Material.AIR);
            ItemMeta im_next = next.getItemMeta();
            im_next.setDisplayName("§b§l下一页");
            next.setItemMeta(im_next);
            inv.setItem(51, next);
        }

        ItemStack cancel = new ItemStack(Material.AIR);
        ItemMeta im_cancel = cancel.getItemMeta();
        im_cancel.setDisplayName("§e§l关闭收购");
        cancel.setItemMeta(im_cancel);
        inv.setItem(52, cancel);

        inv.setContents(list.toArray(new ItemStack[0]));

        Target.closeInventory();
        Target.openInventory(inv);
        View = true;
    }

    public int getPage(){
        return Page;
    }

    public boolean equals(Player p) {
        return Target.equals(p);
    }

    public boolean isView(){
        return View;
    }

    public void setView(boolean view) {
        View = view;
    }

    public boolean isWaitingSell() {
        return WaitingSell;
    }

    public void setWaitingSell(boolean waitingSell) {
        WaitingSell = waitingSell;
        if(WaitingSell){
            Target.closeInventory();
        }else{
            toInv();
        }
    }

    public int getWaitingProductID(){
        return WaitingProductID;
    }

    public void setWaitingProductID(int id){
        for(Product p : Products){
            if(p.equals(id)){
                WaitingProductID = id;
            }
        }
    }

    public void sell(int id, int amount){
        for(Product p : Products){
            p.equals(id);
            p.sell(Target, amount);
            Target.closeInventory();
            WaitingSell = false;
        }
    }

    public boolean isNoNext() {
        return noNext;
    }

    public boolean isNoPrevious(){
        return noPrevious;
    }

    public void nextPage(){
        Page++;
        Target.closeInventory();
        toInv();
    }

    public void previousPage(){
        Page--;
        Target.closeInventory();
        toInv();
    }

    public void cancelView(){
        Page = 0;
        noNext = false;
        noPrevious = false;
        View = false;
        WaitingSell = false;
        Target.closeInventory();
    }

    public boolean clickIsBorder(int indexInView){
        return BorderIndex.contains(indexInView);
    }

    public void delProduct(Product p){
        Products.forEach(l -> {
            if(l.equals(p)){
                Products.remove(l);
                Target.closeInventory();
                toInv();
            }
        });
    }

}

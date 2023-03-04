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

    private static ItemStack BorderFill;
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

        // debug
        MainConsole.sendMessage(BorderIndex.toString());
        MainConsole.sendMessage(Products.toString());

        // 定义边框，INV初始
        BorderFill = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
        Inventory inv = Bukkit.createInventory(null,
                54,
                Messages.requestPlaceholder(Target, Messages.GUI_SELL_TITLE));

        // 定义初始框架和初始内容
        List<ItemStack> list = new ArrayList<>(54);
        for(int i = 0; i < 54; i++){
            list.add(i, new ItemStack(Material.AIR));
        }

        // 安装外围边框
        for(Integer i : BorderIndex){
            list.set(i, BorderFill);
        }

        // 定义内容
        for(int i = Page *28; i < (Page +1) *28; i++){
            if(i == Products.size()){
                noNext = true;
                MainConsole.sendMessage("该页无下一页");
                break;
            }
            for(int ii = 0; ii < 54; ii++){
                if(BorderIndex.contains(ii) || list.get(ii).getType() != Material.AIR){
                    continue;
                }
                list.set(ii, Products.get(i).toIcon(Target));
            }
        }

        // 定义翻页
        if(Page == 0){
            noPrevious = true;
            MainConsole.sendMessage("该页无上一页");
        }

        if(!noPrevious){
            ItemStack previous = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
            ItemMeta im_previous = previous.getItemMeta();
            im_previous.setDisplayName("§b§l上一页");
            previous.setItemMeta(im_previous);
            list.set(51, previous);
        }

        if(!noNext){
            ItemStack next = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
            ItemMeta im_next = next.getItemMeta();
            im_next.setDisplayName("§b§l下一页");
            next.setItemMeta(im_next);
            list.set(53, next);
        }

        ItemStack cancel = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
        ItemMeta im_cancel = cancel.getItemMeta();
        im_cancel.setDisplayName("§e§l关闭收购");
        cancel.setItemMeta(im_cancel);
        list.set(52, cancel);

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

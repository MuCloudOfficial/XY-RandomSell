package me.mucloud.plugin.XY.RandomSell.internal;

import me.mucloud.plugin.XY.RandomSell.Main;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Map;

public class Product {

    private final int ID;
    private final Material ICON;
    private final int Amount;
    private final double Price;

    public Product(int id, Material icon, int amount, double price){
        ID = id;
        ICON = icon;
        Amount = amount;
        Price = price;
    }

    public void sell(Player p, int amount){
        if(amount > Amount){
            p.sendMessage(Messages.requestPlaceholder(p, Messages.SELL_OVERFLOW));
            return;
        }
        Main.getEcon().depositPlayer(p, Price *amount);
        p.sendMessage(Messages.requestPlaceholder(p, Messages.convert(Messages.SELL_FINISH, "{acquired}", String.valueOf(Price *amount))));
    }

    public ItemStack toIcon(Player p){
        ItemStack is = new ItemStack(ICON);
        ItemMeta im = is.getItemMeta();
        im.setDisplayName(im.getLocalizedName() + "ID: " + ID);

        im.setLore(Messages.requestPlaceholder(p, Messages.convert(Messages.PRODUCT_DETAIL, Map.of(
                "{price}",String.valueOf(Price),
                "{amount}",String.valueOf(Amount)
        ))));
        is.setItemMeta(im);

        return is;
    }

    public int getID(){
        return ID;
    }

    public boolean equals(Product p){
        if(equals(p.ID)){
            return true;
        }else if (Main.INSTANCE.getConfiguration().isAllowMultiProduct()) {
            return ICON.equals(p.ICON) && Amount == p.Amount && Price == p.Price;
        }else{
            return ICON.equals(p.ICON);
        }
    }

    public boolean equals(int id){
        return ID == id;
    }

    public String toConfig(){
        return ID + "," + ICON.toString() + "," + Amount + "," + Price;
    }

    public String toString(){
        return ChatColor.GREEN + String.valueOf(ID) + ". " + ICON.toString() + "  " + ChatColor.GREEN + "收购量: " + ChatColor.GOLD + Amount + "  " + ChatColor.GREEN + "收购价: " + ChatColor.GOLD + Price;
    }

}

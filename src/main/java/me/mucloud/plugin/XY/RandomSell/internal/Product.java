package me.mucloud.plugin.XY.RandomSell.internal;

import me.mucloud.plugin.XY.RandomSell.Main;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Map;

public class Product {

    private final Material ICON;
    private final int Amount;
    private final double Price;

    public Product(Material icon, int amount, double price){
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
        p.sendMessage(Messages.requestPlaceholder(p, Messages.SELL_FINISH));
    }

    public ItemStack toIcon(Player p){
        ItemStack is = new ItemStack(ICON);
        ItemMeta im = is.getItemMeta();

        im.setLore(Messages.requestPlaceholder(p, Messages.convert(Messages.PRODUCT_DETAIL, Map.of(
                "{price}",String.valueOf(Price),
                "{amount}",String.valueOf(Amount)
        ))));
        is.setItemMeta(im);

        return is;
    }

    public boolean equals(Product p){
        if (Main.INSTANCE.getConfiguration().isAllowMultiProduct()) {
            return ICON.equals(p.ICON) && Amount == p.Amount && Price == p.Price;
        } else {
            return ICON.equals(p.ICON);
        }
    }

    public String toString(){
        return ICON.toString() + "  " + "收购量: " + Amount + "  " + "收购价: " + Price;
    }

}

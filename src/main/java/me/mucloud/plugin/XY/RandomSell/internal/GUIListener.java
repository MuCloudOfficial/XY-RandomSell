package me.mucloud.plugin.XY.RandomSell.internal;

import me.mucloud.plugin.XY.RandomSell.Main;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.inventory.meta.ItemMeta;

public class GUIListener implements Listener {

    @EventHandler public void onListen(InventoryClickEvent ice){
        Player p = (Player)ice.getWhoClicked();
        if(Main.INSTANCE.getSP().isView(p)){
            SellRepo sr = Main.INSTANCE.getSP().getSellRepo(p);
            int i = ice.getRawSlot();
            if(i == 51 && !sr.isNoPrevious()){
                sr.previousPage();
            }

            if(i == 53 && !sr.isNoNext()){
                sr.nextPage();
            }

            if(i == 52){
                sr.cancelView();
            }

            if(!sr.clickIsBorder(i) && ice.getCurrentItem() != null){
                ItemMeta im = ice.getCurrentItem().getItemMeta();
                String[] rawInf = im.getDisplayName().split(":");
                sr.setWaitingProductID(Integer.parseInt(rawInf[rawInf.length -1].trim()));
                sr.setWaitingSell(true);
                p.sendMessage(Messages.requestPlaceholder(p, Messages.SELL_WAITING));
            }

            ice.setCancelled(true);
        }
    }

    @EventHandler public void onListen(InventoryCloseEvent ice){
        Player p = (Player)ice.getPlayer();
        Main.INSTANCE.getSP().setViewStatus(p, false);
    }

    @EventHandler public void onListen(AsyncPlayerChatEvent apce){
        Player p = apce.getPlayer();
        String message = apce.getMessage();

        SellRepo sr = Main.INSTANCE.getSP().getSellRepo(p);
        if(sr.isWaitingSell()){
            if(message.equalsIgnoreCase("all")){
                sr.sellAll(sr.getWaitingProductID());
                apce.setCancelled(true);
            }else if(message.matches("^[1-9][0-9]*")) {
                sr.sell(sr.getWaitingProductID(), Integer.parseInt(message));
                apce.setCancelled(true);
            }
        }
    }

}

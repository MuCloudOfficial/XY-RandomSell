package me.mucloud.plugin.XY.RandomSell.internal;

import me.mucloud.plugin.XY.RandomSell.Main;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class GUIListener implements Listener {

    @EventHandler public void onListen(InventoryClickEvent ice){
        Player p = (Player)ice.getWhoClicked();
        if(Main.INSTANCE.getSP().isView(p)){

            ice.setCancelled(true);
        }
    }

    @EventHandler public void onListen(InventoryCloseEvent ice){
        Player p = (Player)ice.getPlayer();
    }

    @EventHandler public void onListen(AsyncPlayerChatEvent apce){
        Player p = apce.getPlayer();
    }

}

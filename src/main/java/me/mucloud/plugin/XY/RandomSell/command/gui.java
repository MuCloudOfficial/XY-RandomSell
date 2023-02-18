package me.mucloud.plugin.XY.RandomSell.command;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class gui {

    private final CommandSender Sender;

    public gui(CommandSender sender){
        Sender = sender;
    }

    public void execute(){
        if(Sender instanceof Player){
            Sender.sendMessage();
        }
        Player p = (Player) Sender;
    }

}

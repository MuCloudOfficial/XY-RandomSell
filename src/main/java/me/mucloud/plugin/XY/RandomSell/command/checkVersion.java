package me.mucloud.plugin.XY.RandomSell.command;

import me.mucloud.plugin.XY.RandomSell.Main;
import me.mucloud.plugin.XY.RandomSell.internal.Updater;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class checkVersion {

    private final CommandSender Sender;

    public checkVersion(CommandSender sender){
        Sender = sender;
    }

    public void execute(){
        new Updater(Sender instanceof Player ? (Player)Sender : null).requestRemoteVersion();
    }

}

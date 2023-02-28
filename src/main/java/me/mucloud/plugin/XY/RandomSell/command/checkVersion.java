package me.mucloud.plugin.XY.RandomSell.command;

import me.mucloud.plugin.XY.RandomSell.internal.Updater;
import org.bukkit.command.CommandSender;

/**
 * @deprecated 当前不可用
 */
@Deprecated
public class checkVersion {

    private final CommandSender Sender;

    public checkVersion(CommandSender sender){
        Sender = sender;
    }

    public void execute(){
        Sender.sendMessage("§4§l当前不可用");


        Updater.requestRemoteVersion();
    }

}

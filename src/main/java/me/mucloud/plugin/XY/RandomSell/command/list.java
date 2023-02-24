package me.mucloud.plugin.XY.RandomSell.command;

import me.mucloud.plugin.XY.RandomSell.Main;
import me.mucloud.plugin.XY.RandomSell.internal.Messages;
import org.bukkit.command.CommandSender;

public class list {

    private CommandSender Sender;

    list(CommandSender sender){
        Sender = sender;
    }

    public void execute(){
        Sender.sendMessage(Messages.PRODUCT_LIST_PREFIX);
        Main.INSTANCE.getPP().getList().forEach(Sender::sendMessage);
    }

}

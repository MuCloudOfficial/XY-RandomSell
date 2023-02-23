package me.mucloud.plugin.XY.RandomSell.command;

import me.mucloud.plugin.XY.RandomSell.Main;
import me.mucloud.plugin.XY.RandomSell.internal.Messages;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class list {

    private CommandSender Sender;

    list(CommandSender sender){
        Sender = sender;
    }

    public void execute(){
        Sender.sendMessage(Messages.requestPlaceholder((Player) Sender, Messages.PRODUCT_LIST_PREFIX));
        Main.INSTANCE.getPP().getList().forEach(Sender::sendMessage);
    }

}

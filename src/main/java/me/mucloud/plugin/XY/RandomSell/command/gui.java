package me.mucloud.plugin.XY.RandomSell.command;

import me.mucloud.plugin.XY.RandomSell.Main;
import me.mucloud.plugin.XY.RandomSell.internal.Messages;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class gui {

    private final CommandSender Sender;

    public gui(CommandSender sender){
        Sender = sender;
    }

    public void execute(){
        if(!(Sender instanceof Player)){
            Sender.sendMessage(Messages.GUI_NOT_VIEW);
            return;
        }
        Player p = (Player) Sender;

        Main.INSTANCE.getSP().getSellRepo(p).toInv();
    }

}

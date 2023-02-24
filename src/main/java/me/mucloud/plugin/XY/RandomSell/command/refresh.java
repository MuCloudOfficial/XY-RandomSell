package me.mucloud.plugin.XY.RandomSell.command;

import me.mucloud.plugin.XY.RandomSell.Main;
import me.mucloud.plugin.XY.RandomSell.internal.Messages;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.List;

public class refresh {

    private CommandSender Sender;
    private List<String> Args;

    public refresh(CommandSender sender, String[] args){
        Sender = sender;
        Args = args.length == 1 ? Collections.emptyList() : List.of(args).subList(1, args.length -1);
    }

    public void execute(){
        if(Args.get(0).equalsIgnoreCase("all")){
            Main.INSTANCE.getSP().clear();
            Sender.sendMessage(Messages.requestPlaceholder((Player) Sender, Messages.SELLREPO_REF_ALL_FINISH));
            return;
        }
        Player p = Bukkit.getPlayer(Args.get(0));
        if(p == null){
            Sender.sendMessage(Messages.requestPlaceholder(p, Messages.PLAYER_NOT_FOUND));
            return;
        }
        if(Main.INSTANCE.getSP().delSellRepo(p)){
            Sender.sendMessage(Messages.requestPlaceholder(p, Messages.SELLREPO_REF_FINISH));
        }else{
            Sender.sendMessage(Messages.requestPlaceholder(p, Messages.SELLREPO_REF_ERROR_BY_EMPTY));
        }
    }

}

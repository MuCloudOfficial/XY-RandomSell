package me.mucloud.plugin.XY.RandomSell.command;

import org.bukkit.command.CommandSender;

import java.util.List;

public class refresh {

    private CommandSender Sender;
    private List<String> Args;

    public refresh(CommandSender sender, String[] args){
        Sender = sender;
        Args = List.of(args).subList(1, args.length -1);
    }

    public void execute(){

    }

}

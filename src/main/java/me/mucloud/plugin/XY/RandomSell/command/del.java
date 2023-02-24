package me.mucloud.plugin.XY.RandomSell.command;

import me.mucloud.plugin.XY.RandomSell.Main;
import me.mucloud.plugin.XY.RandomSell.internal.MainConsole;
import me.mucloud.plugin.XY.RandomSell.internal.Messages;
import org.bukkit.command.CommandSender;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 *  用法 /xyrs del [ID]
 */
public class del {

    private CommandSender Sender;
    private List<String> Args;

    public del(CommandSender sender, String[] args){
        Sender = sender;
        Args = args.length == 1 ? Collections.emptyList() : List.of(args).subList(1, args.length);

        MainConsole.sendMessage(Arrays.toString(args));
        MainConsole.sendMessage(Args.toString());
    }

    public void execute(){
        if(Args.size() != 1){
            Sender.sendMessage(Messages.CMD_ARG_ERROR);
        }else{
            if(!Main.INSTANCE.getPP().contains(Integer.parseInt(Args.get(0)))){
                Sender.sendMessage(Messages.CMD_DEL_ERROR_BY_EMPTY);
                return;
            }
            Main.INSTANCE.getPP().delProduct(Integer.parseInt(Args.get(0)));
            Sender.sendMessage(Messages.CMD_DEL_FINISH);
        }
    }

}

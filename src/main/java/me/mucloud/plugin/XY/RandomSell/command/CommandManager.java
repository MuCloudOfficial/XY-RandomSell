package me.mucloud.plugin.XY.RandomSell.command;

import me.mucloud.plugin.XY.RandomSell.Main;
import me.mucloud.plugin.XY.RandomSell.internal.Configuration;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.List;

public class CommandManager implements CommandExecutor, TabCompleter {

    private final Configuration C;
    private final List<String> SubCommands = List.of(
            "info", "gui", "add", "del", "list", "checkVersion"
    );

    public CommandManager(Configuration c){
        C = c;
    }

    private void sendInfo(CommandSender sender){
        sender.sendMessage("§7§l| " + Main.PREFIX + "      " + C.getVersion() + "§6§l版本");
        sender.sendMessage("§7§l| §e§l作者: §7§l" + C.getAuthors());
        sender.sendMessage("§7§l| §e§l开源站: " + C.getWebsite());
        sender.sendMessage("§7§l| §7§m-------------------------------------------------");
        sender.sendMessage("§7§l| §6/xyrs info                           §e打开帮助页面");
        sender.sendMessage("§7§l| §6/xyrs gui                            §e打开商店");
        sender.sendMessage("§7§l| §6/xyrs add [ID] [数量] [价格]          §e加入一个收购项目");
        sender.sendMessage("§7§l| §6/xyrs del [ID]                       §e删除一个收购项目");
        sender.sendMessage("§7§l| §6/xyrs list                           §e列出收购项目");
        sender.sendMessage("§7§l| §6/xyrs checkVersion                   §e检查更新");
        sender.sendMessage("§7§l| §7§m-------------------------------------------------");
    }

    @Override public boolean onCommand(CommandSender sender, Command cmd, String s, String[] ss) {
        if(cmd.getName().equalsIgnoreCase("xyrs")){
            if(ss.length == 0){
                sendInfo(sender);
            }else{
                switch(ss[0].toLowerCase()){
                    case "info": sendInfo(sender); break;
                    case "gui": new gui(sender).execute(); break;
                    case "add": new add(sender, ss).execute(); break;
                    case "del": new del(sender, ss).execute(); break;
                    case "list": new list(sender).execute(); break;
                    case "refresh": new refresh(sender, ss).execute(); break;
                    case "checkversion": new checkVersion(sender).execute(); break;
                }
            }
        }
        return true;
    }

    @Override public List<String> onTabComplete(CommandSender sender, Command cmd, String s, String[] ss) {
        if(cmd.getName().equalsIgnoreCase("xyrs")){
            return SubCommands;
        }
        return null;
    }
}

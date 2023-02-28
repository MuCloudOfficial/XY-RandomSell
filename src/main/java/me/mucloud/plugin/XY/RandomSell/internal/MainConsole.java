package me.mucloud.plugin.XY.RandomSell.internal;

import me.mucloud.plugin.XY.RandomSell.Main;
import org.bukkit.command.ConsoleCommandSender;

public final class MainConsole {

    private static final ConsoleCommandSender CCS = Main.INSTANCE.getServer().getConsoleSender();

    private static String preProcess(String message){
        return "§7§l[" + Main.PREFIX + "§7§l] " + message;
    }

    public static void sendMessage(String message){
        CCS.sendMessage(preProcess(Messages.convert(message, null, null)));
    }

    public static void warn(String messageWithoutColorChar){
        CCS.sendMessage(preProcess("§6" + messageWithoutColorChar));
    }

    public static void err(String messageWithoutColorChar){
        CCS.sendMessage(preProcess("§4" + messageWithoutColorChar));
    }

}

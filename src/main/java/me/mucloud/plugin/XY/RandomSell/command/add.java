package me.mucloud.plugin.XY.RandomSell.command;

import me.mucloud.plugin.XY.RandomSell.Main;
import me.mucloud.plugin.XY.RandomSell.internal.Messages;
import me.mucloud.plugin.XY.RandomSell.internal.Product;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

/**
 *  用法: /xyrs add [MaterialID] [数量] [价格] <br/>
 *       /xyrs add [数量] [价格]
 *
 */
public class add {

    private final CommandSender Sender;
    private final List<String> Args;

    public add(CommandSender sender, String[] args){
        Sender = sender;
        Args = args.length == 1 ? Collections.emptyList() : List.of(args).subList(1, args.length -1);
    }

    public void execute(){
        if(Sender instanceof ConsoleCommandSender){
            if(Args.size() != 3){
                Sender.sendMessage(Messages.CMD_ARG_ERROR);
                return;
            }
            Material m = Material.matchMaterial(Args.get(0));
            if(m == null){
                Sender.sendMessage(Messages.CMD_MAT_ERROR);
                return;
            }
            Product p;
            if((p = Main.INSTANCE.getPP().addProduct(m, Integer.parseInt(Args.get(0)), Double.parseDouble(Args.get(1)))) == null){
                Sender.sendMessage(Messages.CMD_ADD_ERROR_BY_EXIST);
                return;
            }

            FileConfiguration fc = new YamlConfiguration();
            try {
                fc.load(Main.INSTANCE.getConfiguration().getConfigFile());
                List<String> ol = fc.getStringList("Sell.Products");
                ol.add(p.toConfig());

                fc.set("Sell.Products", ol);
            } catch (IOException | InvalidConfigurationException e) {
                throw new RuntimeException(e);
            }

            Sender.sendMessage(Messages.CMD_ADD_FINISH);
        }else if (Sender instanceof Player){
            if(Sender.hasPermission("xyrs.admin")){
                Sender.sendMessage(Messages.CMD_PERMISSION_DENIED);
                return;
            }
            if(Args.size() != 2){
                Sender.sendMessage(Messages.CMD_ARG_ERROR);
                return;
            }
            Material m = ((Player)Sender).getInventory().getItemInMainHand().getType();
            if(m.equals(Material.AIR)){
                Sender.sendMessage(Messages.CMD_ADD_ERROR_BY_EMPTY);
                return;
            }

            Product p;
            if((p = Main.INSTANCE.getPP().addProduct(m, Integer.parseInt(Args.get(0)), Double.parseDouble(Args.get(1)))) == null){
                Sender.sendMessage(Messages.CMD_ADD_ERROR_BY_EXIST);
                return;
            }

            FileConfiguration fc = new YamlConfiguration();
            try {
                fc.load(Main.INSTANCE.getConfiguration().getConfigFile());
                List<String> ol = fc.getStringList("Sell.Products");
                ol.add(p.toConfig());

                fc.set("Sell.Products", ol);
            } catch (IOException | InvalidConfigurationException e) {
                throw new RuntimeException(e);
            }

            Sender.sendMessage(Messages.CMD_ADD_FINISH);
        }
    }

}

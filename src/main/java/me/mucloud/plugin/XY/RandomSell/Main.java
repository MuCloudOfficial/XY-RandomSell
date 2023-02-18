package me.mucloud.plugin.XY.RandomSell;


import me.mucloud.plugin.XY.RandomSell.command.CommandManager;
import me.mucloud.plugin.XY.RandomSell.internal.*;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    public static final String PREFIX = "§a§lXY7§-§6RandomSell";
    public static Main INSTANCE;

    private static Economy Econ;
    private static boolean HookPAPI;
    private static Configuration C;
    private static CommandManager CM;
    private static SellPool SP;
    private static ProductPool PP;


    @Override public void onEnable() {
        INSTANCE = this;

        Messages.loadMessage(INSTANCE);
        requestHookVault();
        requestHookPAPI();
        MainConsole.sendMessage(PREFIX + "§b§lMADE IN STARRY SKY.");
        MainConsole.sendMessage(PREFIX + Messages.PLUGIN_ENABLING);
        C = new Configuration(this);
        C.initialize();

        SP = new SellPool();
        PP = new ProductPool(C); PP.regFromFile();
        CM = new CommandManager(C);
        regCommand();
        regListener();
    }

    @Override public void onDisable() {

        HandlerList.unregisterAll(INSTANCE);
    }

    private void requestHookVault(){
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            MainConsole.sendMessage(Main.PREFIX + Messages.PLUGIN_NOT_FOUND_VAULT);
            getServer().getPluginManager().disablePlugin(INSTANCE);
            return;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            MainConsole.sendMessage(Main.PREFIX + Messages.PLUGIN_NOT_COMPATIBLE_VAULT);
            getServer().getPluginManager().disablePlugin(INSTANCE);
            return;
        }
        Econ = rsp.getProvider();
        MainConsole.sendMessage(Messages.PLUGIN_PAPI_HOOKED);
    }

    private void requestHookPAPI(){
        if(Bukkit.getPluginManager().getPlugin("PlaceholderAPI") == null){
            MainConsole.sendMessage(Main.PREFIX + Messages.PLUGIN_NOT_FOUND_PAPI);
        }else{
            HookPAPI = true;
            MainConsole.sendMessage(Messages.PLUGIN_VAULT_HOOKED);
        }
    }

    @SuppressWarnings("all") private void regCommand(){
        getCommand("xyrs").setExecutor(CM);
        getCommand("xyrs").setTabCompleter(CM);
    }

    private void regListener(){
        getServer().getPluginManager().registerEvents(new GUIListener(), INSTANCE);
    }

    public static boolean isHookPAPI(){
        return HookPAPI;
    }

    public static Economy getEcon(){
        return Econ;
    }

    public Configuration getConfiguration(){
        return C;
    }

    public SellPool getSP(){
        return SP;
    }

    public ProductPool getPP(){
        return PP;
    }

}
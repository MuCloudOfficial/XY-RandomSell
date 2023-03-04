package me.mucloud.plugin.XY.RandomSell.internal;

import me.mucloud.plugin.XY.RandomSell.Main;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import javax.annotation.Nullable;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.List;

public class Updater {

    private static final Main Plugin = Main.INSTANCE;
    private static final String Source = "https://raw.githubusercontent.com/MuCloudOfficial/XY-RandomSell/master/src/main/resources/plugin.yml";

    private static double CurrentInternalVersion;

    private static String RemoteVersion;
    private static String RemoteVersionCN;
    private static double RemoteInternalVersion;
    private static List<String> NewVersionDetail;
    @Nullable private Player Caller;

    public Updater(@Nullable Player caller){
         CurrentInternalVersion = Plugin.getConfiguration().getInternalVersion();
         Caller = caller;
    }

    public void requestRemoteVersion(){
        new BukkitRunnable(){
            @Override public void run() {
                try{
                    BufferedReader br = new BufferedReader(new InputStreamReader(new URL(Source).openStream()));

                    FileConfiguration fc = new YamlConfiguration();
                    fc.load(br);

                    RemoteInternalVersion = fc.getDouble("internalVersion");

                    if(RemoteInternalVersion > CurrentInternalVersion){
                        RemoteVersion = fc.getString("version");
                        RemoteVersionCN = fc.getString("versionCN");
                        NewVersionDetail = fc.getStringList("newVersionDetail");
                        sendTo(true);
                    }else{
                        sendTo(false);
                    }
                } catch (IOException | InvalidConfigurationException e) {
                    throw new RuntimeException(e);
                }
            }
        }.runTaskAsynchronously(Plugin);
    }

    private void sendTo(boolean hasNewerVersion){
        if(Caller != null){
            if(hasNewerVersion){
                Caller.sendMessage(Messages.convert("&7&l| &a&l发现新版本", null, null));
                Caller.sendMessage(Messages.convert("&7&l| &e&l" + RemoteVersion + " &7&l| &e&l" + RemoteVersionCN + "&b&l版本 &7&l| 内部版本 " + RemoteInternalVersion, null, null));
                Caller.sendMessage(Messages.convert("&7&l| ========== &e&l新版本概述 &7&l==========", null, null));
                NewVersionDetail.forEach(l -> {
                    Caller.sendMessage("&7&l| " + Messages.convert(l, null, null));
                });
                Caller.sendMessage(Messages.convert("&7&l| ================================", null, null));
                Caller.sendMessage(Messages.convert("&7&l| &b&l下载链接 >>> " + "https://github.com/MuCloudOfficial/XY-RandomSell/releases/tag/" + RemoteVersion + "_" + RemoteInternalVersion, null ,null));
            }else{
                Caller.sendMessage(Messages.convert("&a&l当前已最新版本", null, null));
            }
        }else{
            if(hasNewerVersion){
                MainConsole.sendMessage("&7&l| &a&l发现新版本");
                MainConsole.sendMessage("&7&l| &e&l" + RemoteVersion + " &7&l| &e&l" + RemoteVersionCN + "&b&l版本 &7&l| 内部版本 " + RemoteInternalVersion);
                MainConsole.sendMessage("&7&l| ========== &e&l新版本概述 &7&l==========");
                NewVersionDetail.forEach(l -> {
                    MainConsole.sendMessage("&7&l| " + l);
                });
                MainConsole.sendMessage("&7&l| ================================");
                MainConsole.sendMessage("&7&l| &b&l下载链接 >>> " + "https://github.com/MuCloudOfficial/XY-RandomSell/releases/tag/" + RemoteVersion + "_" + RemoteInternalVersion);
            }else{
                MainConsole.sendMessage("&a&l当前已最新版本");
            }
        }
    }

}

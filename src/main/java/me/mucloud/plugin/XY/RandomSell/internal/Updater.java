package me.mucloud.plugin.XY.RandomSell.internal;

import me.mucloud.plugin.XY.RandomSell.Main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * @deprecated 更新器未完成
 */
@Deprecated
public class Updater {

    private static final String Source = "https://raw.githubusercontent.com/MuCloudOfficial/XY-RandomSell/master/src/main/resources/plugin.yml?token=GHSAT0AAAAAAB54XEOCRRE25ZXKHDXRO72OY7QOCMQ";
    private static String RemoteVersion;
    private static String RemoteVersionCN;
    private static double RemoteInternalVersion;

    private static List<String> NewVersionDetail;
    private static Configuration C;

    public Updater(Configuration c){
        C = c;
    }

    public static void requestRemoteVersion(){
        CompletableFuture.supplyAsync(() -> {
            try {
                if (C.isCheckUpdate()) {
                    try (BufferedReader br = new BufferedReader(new InputStreamReader(new URL(Source)
                            .openStream()))) {
                        String s;

                        while ((s = br.readLine()) != null) {
                            if(s.contains("internalVersion")){
                                RemoteInternalVersion = Double.parseDouble(s.split(":")[1].trim());
                                break;
                            }
                        }

                        if(Main.INSTANCE.getConfiguration().getInternalVersion() < RemoteInternalVersion){
                            while ((s = br.readLine()) != null) {
                                if(s.contains("version")){
                                    RemoteInternalVersion = Double.parseDouble(s.split(":")[1].trim());
                                    break;
                                }
                                if(s.contains("versionCN")){
                                    RemoteVersionCN = s.split(":")[1].trim();
                                    break;
                                }
                                if(s.contains("newVersionDetail")){
                                    NewVersionDetail.addAll(List.of(s.trim()
                                            .replace('\"', ' ')
                                            .replace('[', ' ')
                                            .replace(']', ' ')
                                            .split(",")));
                                    break;
                                }
                            }
                            MainConsole.warn("当前有新版本");
                            MainConsole.sendMessage("§a§l" + RemoteVersion);
                            MainConsole.sendMessage("§a§l" + RemoteVersionCN);
                            for(String sl : NewVersionDetail){
                                MainConsole.sendMessage(sl);
                            }
                        }
                    }

                }
                return true;
            } catch (IOException e) {
                return false;
            }
        }).thenAccept( l -> {
            if(l){
                MainConsole.sendMessage("§e§l详情与下载参见 §b§lhttps://github.com/MuCloudOfficial/XY-RandomSell/releases");
            }
        });

    }

}

package me.mucloud.plugin.XY.RandomSell.internal;

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
                            if (s.contains("version")) {
                                RemoteVersion = s;
                                break;
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

            }
        });

    }

}

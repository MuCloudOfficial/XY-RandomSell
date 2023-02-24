package me.mucloud.plugin.XY.RandomSell.internal;

import me.clip.placeholderapi.PlaceholderAPI;
import me.mucloud.plugin.XY.RandomSell.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class Messages {

    private static File LangFile;
    private static String LangVersion;
    private static final List<String> LangVersionCompatibility
            = List.of(
                    "StarrySky_L1.3"
            );
    private static String Locale;
    private static final List<String> LocaleCompatibility
            = List.of(
                    "zh_CN", "Custom"
    );

    private static FileConfiguration fc;

    public static String PLUGIN_ENABLING;
    public static String PLUGIN_ENABLED;
    public static String PLUGIN_DISABLING;
    public static String PLUGIN_DISABLED;
    public static String PLUGIN_VAULT_HOOKED;
    public static String PLUGIN_NOT_FOUND_VAULT;
    public static String PLUGIN_NOT_COMPATIBLE_VAULT;
    public static String PLUGIN_PAPI_HOOKED;
    public static String PLUGIN_NOT_FOUND_PAPI;

    public static String CONFIG_VERSION_ERROR;
    public static String CONFIG_VERSION_INCOMPATIBLE;
    public static String CONFIG_ERROR;


    public static String SELLREPO_REF_ERROR_BY_EMPTY;
    public static String SELLREPO_REF_FINISH;
    public static String SELLREPO_REF_ALL_FINISH;
    public static String PLAYER_NOT_FOUND;

    public static String GUI_SELL_TITLE;
    public static String GUI_INCOMPATIBLE;
    public static String GUI_NOT_VIEW;

    public static String SELL_INTERVAL_PREFIX;
    public static String SELL_FINISH;
    public static String SELL_OVERFLOW;
    public static String SELL_WAITING;

    public static String PRODUCT_LIST_PREFIX;
    public static List<String> PRODUCT_DETAIL;

    public static String CMD_ARG_ERROR;
    public static String CMD_MAT_ERROR;
    public static String CMD_PERMISSION_DENIED;
    public static String CMD_ADD_FINISH;
    public static String CMD_ADD_ERROR_BY_EXIST;
    public static String CMD_ADD_ERROR_BY_EMPTY;
    public static String CMD_DEL_ERROR_BY_EMPTY;
    public static String CMD_DEL_FINISH;

    public static void loadMessage(Main main){
        LangFile = new File(main.getDataFolder(), "lang.yml");
        if(!LangFile.exists() || LangFile.isDirectory()){
            main.saveResource("lang.yml", false);
        }

        fc = new YamlConfiguration();
        validate();
        loadMessages();
    }

    @SuppressWarnings("all") private static void validate(){
        try {
            fc.load(LangFile);
            LangVersion = fc.getString("Version", null);
            if (LangVersion == null || !LangVersionCompatibility.contains(LangVersion)) {
                MainConsole.warn("插件语言文件版本错误，这并不会影响启动，但语言文件已经不可用并被备份，将使用内置的语言文件");
                LangFile.renameTo(
                        new File(Main.INSTANCE.getDataFolder(), "lang_" + new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date()) + ".yml.bak")
                );
                Main.INSTANCE.saveResource("lang.yml", true);
            }

            Locale = fc.getString("Locale", null);
            if (Locale == null || !LocaleCompatibility.contains(Locale)) {
                MainConsole.warn("插件语言设置有误，已自动设置为简体中文，这并不会影响启动");
                Locale = "zh_CN";
            }
        }catch (IOException | InvalidConfigurationException e) {
            throw new RuntimeException(e);
        }
    }

    @SuppressWarnings("all") private static void loadMessages(){
        try {
            fc.load(LangFile);

            PLUGIN_ENABLING = convert(fc.getString(Locale + ".PLUGIN_ENABLING"), null, null);
            PLUGIN_ENABLED = convert(fc.getString(Locale + ".PLUGIN_ENABLED"), null, null);
            PLUGIN_DISABLING = convert(fc.getString(Locale + ".PLUGIN_DISABLING"), null, null);
            PLUGIN_DISABLED = convert(fc.getString(Locale + ".PLUGIN_DISABLED"), null, null);
            PLUGIN_VAULT_HOOKED = convert(fc.getString(Locale + ".PLUGIN_VAULT_HOOKED"), null, null);
            PLUGIN_NOT_FOUND_VAULT = convert(fc.getString(Locale + ".PLUGIN_NOT_FOUND_VAULT"), null, null);
            PLUGIN_NOT_COMPATIBLE_VAULT = convert(fc.getString(Locale + ".PLUGIN_NOT_COMPATIBLE_VAULT"), null, null);
            PLUGIN_PAPI_HOOKED = convert(fc.getString(Locale + ".PLUGIN_PAPI_HOOKED"), null, null);
            PLUGIN_NOT_FOUND_PAPI = convert(fc.getString(Locale + ".PLUGIN_NOT_FOUND_PAPI"), null, null);

            CONFIG_VERSION_ERROR = convert(fc.getString(Locale + ".CONFIG_VERSION_ERROR"), null, null);
            CONFIG_VERSION_INCOMPATIBLE = convert(fc.getString(Locale + ".CONFIG_VERSION_INCOMPATIBLE"), null, null);
            CONFIG_ERROR = convert(fc.getString(Locale + ".CONFIG_ERROR"), null, null);

            SELLREPO_REF_ERROR_BY_EMPTY = convert(fc.getString(Locale + ".SELLREPO_REF_ERROR_BY_EMPTY"), null, null);
            SELLREPO_REF_FINISH = convert(fc.getString(Locale + ".SELLREPO_REF_FINISH"), null, null);
            SELLREPO_REF_ALL_FINISH = convert(fc.getString(Locale + ".SELLREPO_REF_ALL_FINISH"), null, null);
            PLAYER_NOT_FOUND = convert(fc.getString(Locale + ".PLAYER_NOT_FOUND"), null, null);

            GUI_SELL_TITLE = convert(fc.getString(Locale + ".GUI_SELL_TITLE"), null, null);
            GUI_INCOMPATIBLE = convert(fc.getString(Locale + ".GUI_INCOMPATIBLE"), null, null);
            GUI_NOT_VIEW = convert(fc.getString(Locale + ".GUI_NOT_VIEW"), null, null);

            SELL_INTERVAL_PREFIX = convert(fc.getString(Locale + ".SELL_INTERVAL_PREFIX"), null, null);
            SELL_FINISH = convert(fc.getString(Locale + ".SELL_FINISH"), null, null);
            SELL_OVERFLOW = convert(fc.getString(Locale + ".SELL_OVERFLOW"), null, null);
            SELL_WAITING = convert(fc.getString(Locale + ".SELL_WAITING"), null, null);

            PRODUCT_LIST_PREFIX = convert(fc.getString(Locale + ".PRODUCT_LIST_PREFIX"),null, null);
            PRODUCT_DETAIL = convert(fc.getStringList(Locale + ".PRODUCT_DETAIL"), null);

            CMD_ARG_ERROR = convert(fc.getString(Locale + ".CMD_ARG_ERROR"), null, null);
            CMD_MAT_ERROR = convert(fc.getString(Locale + ".CMD_MAT_ERROR"), null, null);
            CMD_PERMISSION_DENIED = convert(fc.getString(Locale + ".CMD_PERMISSION_DENIED"), null, null);
            CMD_ADD_FINISH = convert(fc.getString(Locale + ".CMD_ADD_FINISH"), null, null);
            CMD_ADD_ERROR_BY_EXIST = convert(fc.getString(Locale + ".CMD_ADD_ERROR_BY_EXIST"), null, null);
            CMD_ADD_ERROR_BY_EMPTY = convert(fc.getString(Locale + ".CMD_ADD_ERROR_BY_EMPTY"), null, null);
            CMD_DEL_ERROR_BY_EMPTY = convert(fc.getString(Locale + ".CMD_DEL_ERROR_BY_EMPTY"), null, null);
            CMD_DEL_FINISH = convert(fc.getString(Locale + ".CMD_DEL_FINISH"), null, null);

        } catch (IOException | InvalidConfigurationException e) {
            throw new RuntimeException(e);
        } catch (NullPointerException e){
            MainConsole.err("语言文件错误，请重新检查语言文件，本次启动被取消");
            e.printStackTrace();
            LangFile.renameTo(new File(Main.INSTANCE.getDataFolder(), "lang_" + new SimpleDateFormat("yyMMddHHmmss").format(new Date()) + ".yml"));
            Main.INSTANCE.saveResource("lang.yml", false);
            Bukkit.getPluginManager().disablePlugin(Main.INSTANCE);
        }
    }

    public static String convert(String s, String wantReplaced, String replaced){
        return wantReplaced == null || replaced == null ?
                ChatColor.translateAlternateColorCodes('&', s) :
                ChatColor.translateAlternateColorCodes('&', s.replace(wantReplaced, replaced));
    }

    public static List<String> convert(List<String> list, Map<String, String> replacedMap){
        if(replacedMap == null){
            return list;
        }
        List<String> result = new ArrayList<>();
        list.forEach( l -> replacedMap.forEach( (k, v) -> result.add(convert(l, k, v))));
        return result;
    }

    public static String requestPlaceholder(OfflinePlayer p, String s){
        return Main.isHookPAPI() ? PlaceholderAPI.setPlaceholders(p, s) : s;
    }

    public static List<String> requestPlaceholder(OfflinePlayer p, List<String> list){
        if(Main.isHookPAPI()){
            List<String> result = new ArrayList<>();
            list.forEach( l -> result.add(requestPlaceholder(p, l)));
            return result;
        }
        return list;
    }

}

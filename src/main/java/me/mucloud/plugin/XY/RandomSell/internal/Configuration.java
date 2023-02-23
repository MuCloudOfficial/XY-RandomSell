package me.mucloud.plugin.XY.RandomSell.internal;

import me.mucloud.plugin.XY.RandomSell.Main;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Configuration {

    private final File ConfigFolder;
    private final File ConfigFile;
    private static String Version;
    private static String InternalVersion;
    private static String Authors;
    private static String Website;
    private static String ConfigVersion;
    private static List<String> ConfigVersionCompatible =
            List.of(
                    "StarrySky_C1.0",
                    "StarrySky_C1.1"
            );

    private static int Sell_RandomSize;
    private static List<String> RAW_Product_LIST;
    private static String Message_Prefix;
    private static int RefreshInterval;
    private static boolean AllowMultiProduct;

    private static boolean CHECK_UPDATE;

    public Configuration(Main main){
        ConfigFolder = main.getDataFolder();
        ConfigFile = new File(ConfigFolder, "config.yml");
    }

    public void initialize(){
        fetchPluginInfo();
        checkIntegrity();
        try{
            validateConfig();
            loadConfig();
        }catch (IOException | InvalidConfigurationException e){
            throw new RuntimeException(e);
        }
    }

    @SuppressWarnings("unchecked") public void fetchPluginInfo(){
        try{
            Yaml y = new Yaml();
            Map<String, Object> map = y.loadAs(Main.INSTANCE.getResource("plugin.yml"), HashMap.class);
            Version = map.get("versionCN").toString();
            InternalVersion = map.get("internalVersion").toString();
            String authorString = map.get("authors").toString();
            Authors = authorString.substring(1, authorString.length() - 1).replace(",", " ");
            Website = map.get("website").toString();
        }catch (NullPointerException npe){
            npe.printStackTrace();
        }
    }

    public void validateConfig() throws IOException, InvalidConfigurationException{
        FileConfiguration fc = new YamlConfiguration();
        fc.load(ConfigFile);
        if(fc.get("General.Version") == null){
            MainConsole.warn(Messages.CONFIG_VERSION_ERROR);
            ConfigFile.renameTo(
                    new File(ConfigFolder, "config_" + new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date()) + ".yml.bak")
            );
            Main.INSTANCE.saveDefaultConfig();
        }
        ConfigVersion = fc.getString("General.Version");
        if(!ConfigVersionCompatible.contains(ConfigVersion)){
            MainConsole.warn(Messages.CONFIG_VERSION_INCOMPATIBLE);
            ConfigFile.renameTo(
                    new File(ConfigFolder, "config_" + new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date()) + ".yml.bak")
            );
            Main.INSTANCE.saveDefaultConfig();
        }
    }

    public void checkIntegrity(){
        if(!ConfigFolder.exists() || ConfigFolder.isFile()){
            ConfigFolder.mkdir();
        }
        if(!ConfigFile.exists() || ConfigFile.isDirectory()){
            Main.INSTANCE.saveDefaultConfig();
        }
    }

    public void loadConfig() throws IOException, InvalidConfigurationException{
        FileConfiguration fc = new YamlConfiguration();
        fc.load(ConfigFile);

        try{
            CHECK_UPDATE = fc.getBoolean("General.CheckUpdate");
            Message_Prefix = Messages.convert(fc.getString("General.Prefix"), null, null);

            Sell_RandomSize = fc.getInt("Sell.RandomSize");
            RefreshInterval = fc.getInt("Sell.RefreshInterval");
            RAW_Product_LIST = fc.getStringList("Sell.Products");
            AllowMultiProduct = fc.getBoolean("Sell.AllowMultiProduct");
        }catch(NullPointerException e){
            MainConsole.warn(Messages.CONFIG_ERROR);
            ConfigFile.renameTo(
                    new File(ConfigFolder, "config_" + new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date()) + ".yml.bak")
            );
            Main.INSTANCE.saveDefaultConfig();
        }
    }

    public String getVersion() {
        return Version;
    }

    public String getAuthors() {
        return Authors;
    }

    public String getWebsite() {
        return Website;
    }

    public String getConfigVersion() {
        return ConfigVersion;
    }

    public File getConfigFile(){
        return ConfigFile;
    }

    public int getSell_RandomSize() {
        return Sell_RandomSize;
    }

    public boolean isCheckUpdate() {
        return CHECK_UPDATE;
    }

    public String getInternalVersion() {
        return InternalVersion;
    }

    public List<String> getRAW_Product_LIST() {
        return RAW_Product_LIST;
    }

    public String getMessage_Prefix() {
        return Message_Prefix;
    }

    public int getRefreshInterval(){
        return RefreshInterval;
    }

    public boolean isAllowMultiProduct(){
        return AllowMultiProduct;
    }

    /**
     * @deprecated 该插件不允许重载
     */
    @Deprecated public void reloadConfig(){

    }

}

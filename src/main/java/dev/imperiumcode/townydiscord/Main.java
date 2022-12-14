package dev.imperiumcode.townydiscord;

import dev.imperiumcode.townydiscord.listeners.*;
import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.Objects;
import java.util.logging.Logger;

public final class Main extends JavaPlugin {

    public static Main instance;
    public static Main getInstance(){
        return instance;
    }
    public static Logger log = Bukkit.getLogger();

    private File customConfigFile;
    private FileConfiguration customConfig;

    @Override
    public void onEnable() {
        instance = this;
        createCustomConfig();

        if(Objects.equals(customConfig.get("webhook-url"), "https://discord.com/api/webhooks/PUTSOMETHINGHERE")){
            log.severe("[DEFAULT WEBHOOK DETECTED!]: Please specify your webhook-url at 'plugins/TownyDiscord/webhook.yml'!");
            log.severe("TownyDiscord will now unload...");
            getServer().getPluginManager().disablePlugin(this);
        } else {
            registerListeners();
        }
    }

    public void createCustomConfig(){
        customConfigFile = new File(getDataFolder(), "webhook.yml");
        if (!customConfigFile.exists()) {
            customConfigFile.getParentFile().mkdirs();
            saveResource("webhook.yml", false);
        }

        customConfig = new YamlConfiguration();
        try {
            customConfig.load(customConfigFile);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    public FileConfiguration getCustomConfig() {
        return this.customConfig;
    }

    public void registerListeners(){
        instance.getServer().getPluginManager().registerEvents(new NewTown(getLogger()), instance);
        instance.getServer().getPluginManager().registerEvents(new RuinedTown(getLogger()), instance);
        instance.getServer().getPluginManager().registerEvents(new NewNation(getLogger()),instance);
        instance.getServer().getPluginManager().registerEvents(new DeletedNation(getLogger()),instance);
        instance.getServer().getPluginManager().registerEvents(new DeletedTown(getLogger()),instance);
        instance.getServer().getPluginManager().registerEvents(new SiegeSessionEnd(getLogger()), instance);
        instance.getServer().getPluginManager().registerEvents(new SiegeSessionStart(getLogger()), instance);
        instance.getServer().getPluginManager().registerEvents(new SiegeStart(getLogger()), instance);
    }

    @Override
    public void onDisable() {
        log.info("TownyDiscord is now unloaded and disabled.");
    }
}

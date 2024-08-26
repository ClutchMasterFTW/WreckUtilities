package me.clutchmasterftw.wreckutilities;

import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public final class WreckUtilities extends JavaPlugin {
    public static final String PREFIX = ChatColor.BLUE + "Wreck" + ChatColor.DARK_BLUE + "MC" + ChatColor.GRAY + " » " + ChatColor.RESET;

    public static WreckUtilities getPlugin() {
        return plugin;
    }
    private static WreckUtilities plugin;

    @Override
    public void onEnable() {
        // Plugin startup logic
        plugin = this;

        Logger logger = this.getLogger();

        getCommand("wreckutils").setExecutor(new Commands());
        getCommand("wreckutils").setTabCompleter(new CommandsAutoCompletion());

        logger.info("WreckUTILITIES has loaded successfully!");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}

package me.clutchmasterftw.wreckutilities;

import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public final class WreckUtilities extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic

        Logger logger = this.getLogger();
        logger.info("WreckUTILITIES has loaded successfully!");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}

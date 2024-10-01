package me.clutchmasterftw.wreckutilities;

import me.clutchmasterftw.wreckutilities.events.ItemDropOnPressurePlate;
import me.clutchmasterftw.wreckutilities.events.OnSmugglerVoucherUse;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Set;
import java.util.UUID;
import java.util.logging.Logger;

public final class WreckUtilities extends JavaPlugin {
    public static final String PREFIX = ChatColor.BLUE + "Wreck" + ChatColor.DARK_BLUE + "MC" + ChatColor.GRAY + " » " + ChatColor.RESET;
    public static Location smugglerLocation;
    public static Location spawnLocation;

    public static WreckUtilities getPlugin() {
        return plugin;
    }
    private static WreckUtilities plugin;

    @Override
    public void onEnable() {
        // Plugin startup logic
        plugin = this;

        smugglerLocation = new Location(Bukkit.getWorld("world"), 85.916d, -59d, -614.266d, 40.2f, 3.6f);
        spawnLocation = new Location(Bukkit.getWorld("world"), 211.5d, -1d, -423.5d, 0f, 0f);

        Logger logger = this.getLogger();

        getCommand("wreckutils").setExecutor(new Commands());
        getCommand("wreckutils").setTabCompleter(new CommandsAutoCompletion());

        Bukkit.getServer().getPluginManager().registerEvents(new OnSmugglerVoucherUse(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new ItemDropOnPressurePlate(), this);

        logger.info("WreckUTILITIES has loaded successfully!");
        logger.info("Created by ClutchMasterFTW | All Rights Reserved.");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        Set<UUID> taskUUIDs = OnSmugglerVoucherUse.teleportTasks.keySet();
        for(UUID uuid:taskUUIDs) {
            Player player = Bukkit.getPlayer(uuid);
            if(player == null) return;
            player.teleport(spawnLocation);
            OnSmugglerVoucherUse.teleportTasks.get(uuid).cancel();

            getLogger().info("Removed " + player.getName() + "(" + uuid + ") from the smuggler list.");
        }
        OnSmugglerVoucherUse.teleportTasks.clear();
    }
}

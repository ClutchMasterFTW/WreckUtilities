package me.clutchmasterftw.wreckutilities.events;

import me.clutchmasterftw.wreckutilities.WreckUtilities;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashMap;
import java.util.UUID;

import static me.clutchmasterftw.wreckutilities.WreckUtilities.smugglerLocation;
import static me.clutchmasterftw.wreckutilities.WreckUtilities.spawnLocation;

public class OnSmugglerVoucherUse implements Listener {
    public static final HashMap<UUID, BukkitTask> teleportTasks = new HashMap<UUID, BukkitTask>();

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent e) {
        if(e.getAction().isRightClick()) {
            ItemStack item = e.getItem();
            if(item == null) {
                return;
            } else {
                ItemMeta meta = item.getItemMeta();
                if(meta == null) {
                    return;
                } else {
                    PersistentDataContainer data = meta.getPersistentDataContainer();
                    NamespacedKey key = new NamespacedKey(WreckUtilities.getPlugin(), "isSmugglerVoucher");
                    if(data.has(key)) {
                        // Item is a Smuggler Voucher
                        e.setCancelled(true);

                        Player player = e.getPlayer();
                        UUID uuid = player.getUniqueId();

                        WreckUtilities.getPlugin().getLogger().info(player.getName() + " has used a Smuggler Voucher, teleporting them to the Smuggler.");

                        int itemSlot = player.getInventory().getHeldItemSlot();
                        int quantityInSlot = player.getInventory().getItem(itemSlot).getAmount();
                        if(quantityInSlot - 1 == 0) {
                            player.getInventory().setItem(itemSlot, null);
                        } else {
                            item.setAmount(quantityInSlot - 1);
                        }

                        if(teleportTasks.containsKey(uuid)) {
                            // Reset task
                            BukkitTask task = teleportTasks.get(uuid);
                            task.cancel();

                            BukkitTask newTask = Bukkit.getScheduler().runTaskLater(WreckUtilities.getPlugin(), () -> {
                                // Teleport the player back to spawn
                                player.teleport(spawnLocation);
                                player.sendMessage(WreckUtilities.PREFIX + "Your 60 seconds is up. You were teleported back to Spawn.");
                                teleportTasks.remove(uuid);
                            }, 20 * 60);
                            teleportTasks.replace(uuid, newTask);
                        } else {
                            // Start task
                            player.teleport(smugglerLocation);
                            player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 40, 0), true);

                            BukkitTask task = Bukkit.getScheduler().runTaskLater(WreckUtilities.getPlugin(), () -> {
                                // Teleport the player back to spawn
                                player.teleport(spawnLocation);
                                player.sendMessage(WreckUtilities.PREFIX + "Your 60 seconds is up. You were teleported back to Spawn.");
                                teleportTasks.remove(uuid);
                            }, 20 * 60);

                            teleportTasks.put(uuid, task);
                        }

                        player.sendMessage(WreckUtilities.PREFIX + "You've been teleported to the Smuggler!");
                        player.sendMessage(ChatColor.GOLD + "Smuggler" + ChatColor.WHITE + ": You have " + ChatColor.ITALIC + "60 seconds" + ChatColor.RESET + ChatColor.WHITE + " to do your business. Make it quick!");
                    }
                }
            }
        }
    }
    // Later on, make a 30 & 15 second warning

    @EventHandler
    public void onPlayerDisconnect(PlayerQuitEvent e) {
        // Remove player's UUID from the tasks hashmap if it is there
        Player player = e.getPlayer();
        UUID uuid = player.getUniqueId();
        if(teleportTasks.containsKey(uuid)) {
            teleportTasks.get(uuid).cancel();
            teleportTasks.remove(uuid);

            player.teleport(spawnLocation);

            WreckUtilities.getPlugin().getLogger().info("Removed " + player.getName() + " (" + uuid + ") from the smuggler list because they left the server. Automatically teleporting them back to spawn.");
        }
    }
}

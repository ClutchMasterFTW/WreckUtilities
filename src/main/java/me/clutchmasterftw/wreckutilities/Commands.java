package me.clutchmasterftw.wreckutilities;

import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class Commands implements CommandExecutor {
    private final String PREFIX = WreckUtilities.PREFIX;

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        switch(args[0]) {
            case "give":
                // Give function
                Player player = Bukkit.getServer().getPlayer(args[1]);
                if(player == null) {
                    sender.sendMessage(PREFIX + ChatColor.RED + "The player provided could not be found!");
                } else {
                    switch(args[2]) {
                        case "haste_potion_1": {
                                // Haste I Potion
                                ItemStack item = new ItemStack(Material.POTION);
                                PotionMeta meta = (PotionMeta) item.getItemMeta();
                                meta.setBasePotionData(new PotionData(PotionType.UNCRAFTABLE));
                                meta.setColor(Color.fromRGB(255, 225, 0));
                                meta.setDisplayName(ChatColor.GOLD + "" + ChatColor.BOLD + "Potion of Haste " + ChatColor.RESET + ChatColor.GOLD + "(I)");
                                meta.addEnchant(Enchantment.DURABILITY, 1, true);
                                meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                                meta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);

                                List<String> lore = new ArrayList<String>();
                                lore.add(ChatColor.RESET + "" + ChatColor.BLUE + "Haste I (05:00)");
                                lore.add(ChatColor.RESET + "" + ChatColor.WHITE + "Gives Haste I effect for 5 minutes.");
                                meta.setLore(lore);

                                meta.addCustomEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 6000, 0), true);

                                item.setItemMeta(meta);

                                placeItemInPlayerSlot(sender, player, item);
                            }
                            break;
                        case "haste_potion_2": {
                                // Haste II Potion
                                ItemStack item = new ItemStack(Material.POTION);
                                PotionMeta meta = (PotionMeta) item.getItemMeta();
                                meta.setBasePotionData(new PotionData(PotionType.UNCRAFTABLE));
                                meta.setColor(Color.fromRGB(255, 225, 0));
                                meta.setDisplayName(ChatColor.GOLD + "" + ChatColor.BOLD + "Potion of Haste " + ChatColor.RESET + ChatColor.GOLD + "(II)");
                                meta.addEnchant(Enchantment.DURABILITY, 1, true);
                                meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                                meta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);

                                List<String> lore = new ArrayList<String>();
                                lore.add(ChatColor.RESET + "" + ChatColor.BLUE + "Haste II (05:00)");
                                lore.add(ChatColor.RESET + "" + ChatColor.WHITE + "Gives Haste II effect for 5 minutes.");
                                meta.setLore(lore);

                                meta.addCustomEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 6000, 1), true);

                                item.setItemMeta(meta);

                                placeItemInPlayerSlot(sender, player, item);
                            }
                            break;
                        case "wrecker_potion": {
                                // Wrecker Potion
                            }
                            break;
                        case "smuggler_voucher": {
                                // Smuggler Voucher
                                ItemStack item = new ItemStack(Material.PAPER);
                                ItemMeta meta = item.getItemMeta();
                                meta.setDisplayName(ChatColor.GREEN + "" + ChatColor.BOLD + "Smuggler Voucher");
                                PersistentDataContainer data = meta.getPersistentDataContainer();
                                NamespacedKey key = new NamespacedKey(WreckUtilities.getPlugin(), "isSmugglerVoucher");
                                data.set(key, PersistentDataType.BOOLEAN, true);

                                List<String> lore = new ArrayList<String>();
                                lore.add(ChatColor.RESET + "" + ChatColor.WHITE + "Right click this voucher to visit the Smuggler.");
                                lore.add("");
                                lore.add(ChatColor.RESET + "" + ChatColor.WHITE + "The Smuggler buys materials for much more than the prison");
                                lore.add(ChatColor.RESET + "" + ChatColor.WHITE + "shop does. They also buy rare special materials that the prison");
                                lore.add(ChatColor.RESET + "" + ChatColor.WHITE + "doesn't buy. You only get " + ChatColor.BOLD + "60" + ChatColor.RESET + ChatColor.WHITE + " seconds to do your business!");
                                meta.setLore(lore);

                                item.setItemMeta(meta);

                                placeItemInPlayerSlot(sender, player, item);
                            }
                            break;
                        default:
                            if(args.length == 3) {
                                sender.sendMessage(PREFIX + ChatColor.RED + "The provided item name was not found!");
                            } else {
                                sender.sendMessage(PREFIX + ChatColor.RED + "Too many arguments!");
                            }
                    }
                }
                break;
            default:
                if(args.length == 1) {
                    sender.sendMessage(PREFIX + ChatColor.RED + "A provided argument was not found!");
                }
        }
//        if(args.length == 0) {
//            sender.sendMessage(PREFIX + ChatColor.RED + "A provided argument was not found!");
//        }
        return true;
    }

    public void placeItemInPlayerSlot(CommandSender sender, Player player, ItemStack item) {
        for(int i = 0; i < 36; i++) {
            if(player.getInventory().getItem(i) == null) {
                player.getInventory().setItem(i, item);
                sender.sendMessage(PREFIX + "Successfully gave " + ChatColor.GOLD + player.getName() + ChatColor.RESET + " a " + item.getItemMeta().getDisplayName() + ChatColor.RESET + ".");
                player.sendMessage(PREFIX + "You've received a " + item.getItemMeta().getDisplayName() + ChatColor.RESET + "!");
                return;
            }
        }
        sender.sendMessage(PREFIX + "There were no available slots in the player's inventory! Dropped the item onto the ground instead.");
        player.sendMessage(PREFIX + "You don't have enough open inventory slots for " + item.getItemMeta().getDisplayName() + ChatColor.RESET + "! The item has been dropped onto the ground instead.");
        World world = player.getWorld();
        world.dropItemNaturally(player.getLocation(), item);
    }
}

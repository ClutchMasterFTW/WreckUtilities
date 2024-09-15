package me.clutchmasterftw.wreckutilities;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CommandsAutoCompletion implements TabCompleter {
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        List<String> suggestions = new ArrayList<String>();

        if(args.length == 1) {
            StringUtil.copyPartialMatches(args[0], Arrays.asList("give"), suggestions);
        } else if(args.length == 2 && args[0].equalsIgnoreCase("give")) {
            List<String> playerNames = new ArrayList<String>();
            for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                playerNames.add(onlinePlayer.getName());
            }
            StringUtil.copyPartialMatches(args[1], playerNames, suggestions);
        } else if(args.length == 3 && args[0].equalsIgnoreCase("give")) {
            StringUtil.copyPartialMatches(args[2], Arrays.asList("haste_potion_1", "haste_potion_2", "wrecker_potion", "smuggler_voucher"), suggestions);
        }

        return suggestions;
    }
}

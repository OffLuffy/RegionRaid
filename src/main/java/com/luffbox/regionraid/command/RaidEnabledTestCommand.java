package com.luffbox.regionraid.command;

import com.luffbox.regionraid.RegionRaid;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class RaidEnabledTestCommand implements CommandExecutor {
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage(ChatColor.RED + "You must be online to use this");
			return true;
		}
		if (!sender.hasPermission("regionraid.test")) {
			sender.sendMessage(ChatColor.RED + "You don't have permission to do that!");
			return true;
		}

		Player plr = (Player) sender;
		boolean raidEnabled = RegionRaid.raidEnabled(plr.getLocation());
		plr.sendMessage(ChatColor.AQUA + "At your current location, raids are "
				+ (raidEnabled ? ChatColor.GREEN + "allowed" : ChatColor.RED + "suppressed"));
		plr.sendMessage(ChatColor.GRAY + "Note: /rrtest uses current position, so may be inaccurate.");
		plr.sendMessage(ChatColor.GRAY + "During raids, the village center position is checked.");
		return true;
	}
}

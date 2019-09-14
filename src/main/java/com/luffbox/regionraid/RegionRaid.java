package com.luffbox.regionraid;

import com.luffbox.regionraid.command.RaidEnabledTestCommand;
import com.luffbox.regionraid.listeners.RaidListener;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.util.Location;
import com.sk89q.worldedit.world.World;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.flags.StateFlag;
import com.sk89q.worldguard.protection.flags.registry.FlagConflictException;
import com.sk89q.worldguard.protection.flags.registry.FlagRegistry;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import com.sk89q.worldguard.protection.regions.RegionQuery;
import org.bukkit.Raid;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;

public final class RegionRaid extends JavaPlugin {

	public static StateFlag RAID_FLAG;
	private static boolean eventExists = true;

	@Override
	public void onLoad() {
		try {
			Class.forName("org.bukkit.event.raid.RaidTriggerEvent");
		} catch (Exception e) {
			eventExists = false;
			return;
		}

		FlagRegistry registry = WorldGuard.getInstance().getFlagRegistry();
		try {
			StateFlag flag = new StateFlag("raid-enabled", true);
			registry.register(flag);
			RAID_FLAG = flag;
		} catch (FlagConflictException e) {
			getLogger().log(Level.SEVERE, "'raid-enabled' flag already exists! Disabling plugin...");
			getServer().getPluginManager().disablePlugin(this);
		}
	}

	@Override
	public void onEnable() {
		if (eventExists) {
			getServer().getPluginManager().registerEvents(new RaidListener(this), this);

			PluginCommand cmd = getServer().getPluginCommand("regionraidtest");
			if (cmd != null) { cmd.setExecutor(new RaidEnabledTestCommand()); }

		} else {
			getLogger().log(Level.SEVERE, "'RaidTriggerEvent' not found! " +
					"Update your server if you want to use this plugin!");
			getServer().getPluginManager().disablePlugin(this);
		}
	}

	@Override
	public void onDisable() {}

	public static RegionContainer getRegionContainer() {
		return WorldGuard.getInstance().getPlatform().getRegionContainer();
	}

	public static boolean raidEnabled(Raid raid) { return raidEnabled(raid.getLocation()); }

	public static boolean raidEnabled(org.bukkit.Location loc) {
		World w = BukkitAdapter.adapt(loc.getWorld());
		Location l = BukkitAdapter.adapt(loc);

		RegionQuery rq = RegionRaid.getRegionContainer().createQuery();
		ApplicableRegionSet set = rq.getApplicableRegions(l);

		return set.testState(null, RegionRaid.RAID_FLAG);
	}
}

package com.luffbox.regionraid;

import com.luffbox.regionraid.listeners.RaidListener;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.flags.StateFlag;
import com.sk89q.worldguard.protection.flags.registry.FlagConflictException;
import com.sk89q.worldguard.protection.flags.registry.FlagRegistry;
import com.sk89q.worldguard.protection.regions.RegionContainer;
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
}

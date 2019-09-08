package com.luffbox.regionraid;

import com.luffbox.regionraid.listeners.RaidListener;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.flags.StateFlag;
import com.sk89q.worldguard.protection.flags.registry.FlagConflictException;
import com.sk89q.worldguard.protection.flags.registry.FlagRegistry;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import org.bukkit.event.raid.RaidTriggerEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;

public final class RegionRaid extends JavaPlugin {

	public static StateFlag RAID_FLAG;

	@Override
	public void onLoad() {
		try {
			RaidTriggerEvent rte = null;
		} catch (Exception e) {
			getServer().getLogger().log(Level.SEVERE, String.format("[%s] 'RaidTriggerEvent' doesn't exist! " +
					"Update your server if you want to use this plugin!", getDescription().getName()));
			getServer().getPluginManager().disablePlugin(this);
			return;
		}

		FlagRegistry registry = WorldGuard.getInstance().getFlagRegistry();
		try {
			StateFlag flag = new StateFlag("raid-enabled", true);
			registry.register(flag);
			RAID_FLAG = flag;
		} catch (FlagConflictException e) {
			getServer().getLogger().log(Level.SEVERE, "'raid-enabled' flag already exists! Disabling plugin...");
			getServer().getPluginManager().disablePlugin(this);
		}
	}

	@Override
	public void onEnable() {
		getServer().getPluginManager().registerEvents(new RaidListener(), this);
	}

	@Override
	public void onDisable() {}

	public static RegionContainer getRegionContainer() {
		return WorldGuard.getInstance().getPlatform().getRegionContainer();
	}
}

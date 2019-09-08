package com.luffbox.regionraid.listeners;

import com.luffbox.regionraid.RegionRaid;
import com.sk89q.worldedit.util.Location;
import com.sk89q.worldedit.world.World;
import com.sk89q.worldguard.LocalPlayer;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import com.sk89q.worldguard.protection.regions.RegionQuery;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.raid.RaidTriggerEvent;

public class RaidListener implements Listener {

	@EventHandler
	public void onRaid(RaidTriggerEvent e) {
		LocalPlayer plr = WorldGuardPlugin.inst().wrapPlayer(e.getPlayer());
		Location loc = plr.getLocation();
		World w = plr.getWorld();

		RegionContainer rc = RegionRaid.getRegionContainer();
		RegionManager rm = rc.get(w);
		RegionQuery rq = rc.createQuery();
		ApplicableRegionSet set = rq.getApplicableRegions(loc);

		if (!set.testState(plr, RegionRaid.RAID_FLAG)) { e.setCancelled(true); }
	}

}

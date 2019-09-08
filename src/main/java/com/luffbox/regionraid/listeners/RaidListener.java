package com.luffbox.regionraid.listeners;

import com.luffbox.regionraid.RegionRaid;
import com.sk89q.worldedit.util.Location;
import com.sk89q.worldedit.world.World;
import com.sk89q.worldguard.LocalPlayer;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.regions.RegionQuery;
import org.bukkit.Bukkit;
import org.bukkit.Raid;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.raid.RaidTriggerEvent;

import java.util.logging.Level;

public class RaidListener implements Listener {

	@EventHandler
	public void onRaid(RaidTriggerEvent e) {
		LocalPlayer plr = WorldGuardPlugin.inst().wrapPlayer(e.getPlayer());
		World w = plr.getWorld();
		Raid r = e.getRaid();
		Location loc = new Location(w, r.getLocation().getX(), r.getLocation().getY(), r.getLocation().getZ());

		RegionQuery rq = RegionRaid.getRegionContainer().createQuery();
		ApplicableRegionSet set = rq.getApplicableRegions(loc);

		if (!set.testState(plr, RegionRaid.RAID_FLAG)) {
			e.setCancelled(true);
			String raidLoc = r.getLocation().getWorld().getName() + " (" + r.getLocation().getBlockX() + ", "
					+ r.getLocation().getBlockY() + "," + r.getLocation().getBlockZ() + ")";
			Bukkit.getLogger().log(Level.INFO, String.format("[%s] Preventing raid: %s", "RegionRaid", raidLoc));
		}
	}

}

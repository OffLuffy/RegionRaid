package com.luffbox.regionraid.listeners;

import com.luffbox.regionraid.RegionRaid;
import com.luffbox.regionraid.events.RaidSuppressEvent;
import org.bukkit.Location;
import org.bukkit.entity.Raider;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.raid.RaidSpawnWaveEvent;
import org.bukkit.event.raid.RaidTriggerEvent;

import java.util.logging.Level;

public class RaidListener implements Listener {

	private RegionRaid pl;
	public RaidListener(RegionRaid plugin) { pl = plugin; }

	@EventHandler
	public void onWave(RaidSpawnWaveEvent e) {
		if (!RegionRaid.raidEnabled(e.getRaid())) {
			RaidSuppressEvent rse = new RaidSuppressEvent(e.getRaid(), e.getWorld(), RaidSuppressEvent.SuppressType.WAVE);
			pl.getServer().getPluginManager().callEvent(rse);
			if (!rse.isCancelled()) {
				pl.getServer().getScheduler().runTaskLater(pl, () -> {
					for (Raider raider : e.getRaiders()) { raider.remove(); }
				}, 1L);
				pl.getLogger().log(Level.INFO, "Suppressing raid wave: " + stringifyLoc(e.getRaid().getLocation()));
			}
		}
	}

	@EventHandler
	public void onRaid(RaidTriggerEvent e) {
		if (!RegionRaid.raidEnabled(e.getRaid())) {
			RaidSuppressEvent rse = new RaidSuppressEvent(e.getRaid(), e.getWorld(), RaidSuppressEvent.SuppressType.TRIGGER);
			pl.getServer().getPluginManager().callEvent(rse);
			if (!rse.isCancelled()) {
				e.setCancelled(true);
				pl.getLogger().log(Level.INFO, "Suppressing raid trigger: " + stringifyLoc(e.getRaid().getLocation()));
			}
		}
	}

	private String stringifyLoc(Location l) {
		return String.format("%s (%d, %d, %d)", l.getWorld().getName(), l.getBlockX(), l.getBlockY(), l.getBlockZ());
	}

}

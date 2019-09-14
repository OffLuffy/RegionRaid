package com.luffbox.regionraid.events;

import org.bukkit.Raid;
import org.bukkit.World;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.raid.RaidEvent;

public class RaidSuppressEvent extends RaidEvent implements Cancellable {

	public enum SuppressType { TRIGGER, WAVE }

	private static final HandlerList handlers = new HandlerList();
	private boolean cancelled;
	private SuppressType type;

	public RaidSuppressEvent(Raid raid, World world, SuppressType type) {
		super(raid, world);
		this.type = type;
	}

	public SuppressType getSuppressType() { return type; }

	public boolean isCancelled() { return cancelled; }

	public void setCancelled(boolean cancel) { cancelled = cancel; }

	public HandlerList getHandlers() { return handlers; }
	public static HandlerList getHandlerList() { return handlers; }

}

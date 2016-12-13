package net.infernalrealms.quests.objectives;

import org.bukkit.ChatColor;
import org.bukkit.Location;

public abstract class Objective {

	int required;
	int progress;

	Objective(int required, int progress) {
		this.required = required;
		this.progress = progress;
	}

	public int getRequired() {
		return this.required;
	}

	public int getProgress() {
		if (this.progress < this.required) {
			return this.progress;
		}
		return this.required;
	}

	public void setProgress(int progress) {
		this.progress = progress;
	}

	public void addProgress(int amount) {
		if (getProgress() == getRequired()) {
			// Don't add any more. This prevents numbers from going over on quest logs & helps prevent quest objective progress from spamming.
			return;
		}
		this.setProgress(this.getProgress() + 1);
	}

	public boolean isComplete() {
		return getProgress() >= getRequired();
	}

	/**
	 * @return whether or not the objective has been displayed as completed in chat
	 */
	public boolean hasBeenShown() {
		return progress > required;
	}

	public void setShown(boolean flag) {
		if (progress < required) {
			return;
		}
		progress = required + 1;
	}

	public Location getLocation() {
		return null;
	}

	public String getLocationDescription() {
		Location location = getLocation();
		if (location == null) {
			return ChatColor.GRAY + "" + ChatColor.ITALIC + "Unknown";
		}
		return ChatColor.GRAY + "" + location.getBlockX() + ", " + location.getBlockY() + ", " + location.getBlockZ();
	}

	public abstract String getDescription();

	@Override
	public String toString() {
		return getDescription();
	}

}

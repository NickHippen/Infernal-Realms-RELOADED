package net.infernalrealms.npc;

import org.bukkit.Location;

import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;

public class NPCManager {

	private NPCManager() {}

	/**
	 * Gets a clone of the npc's file location
	 * @param name
	 * @return the cloned location of the npc or null if not found
	 */
	public static Location getNPCLocationByName(String name) {
		for (NPC npc : CitizensAPI.getNPCRegistry()) {
			if (!npc.getName().equalsIgnoreCase(name)) {
				continue;
			}
			Location npcLocation = npc.getStoredLocation();
			return new Location(npcLocation.getWorld(), npcLocation.getX(), npcLocation.getY(), npcLocation.getZ(), npcLocation.getYaw(),
					npcLocation.getPitch());
		}
		return null;
	}

}

package net.infernalrealms.mobs;

import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_9_R1.entity.CraftEntity;

import net.infernalrealms.mobs.types.InfernalMob;
import net.minecraft.server.v1_9_R1.Entity;

public class HologramHandler {

	public static InfernalHologramLine createHologramOnEntity(org.bukkit.entity.Entity origin, String tag, boolean nameTag) {
		Entity nmsEntity = ((CraftEntity) origin).getHandle();
		InfernalHologramLine hologram = new InfernalHologramLine(nmsEntity.getWorld(), nmsEntity, tag, nameTag);
		Location spawnLocation;
		if (nmsEntity instanceof InfernalMob) {
			InfernalMob im = (InfernalMob) nmsEntity;
			spawnLocation = im.getNameTagLocation();
		} else {
			spawnLocation = origin.getLocation();
		}
		CustomEntityType.spawnEntity(hologram, spawnLocation);
		return hologram;
	}

	public static InfernalHologramLine createHologramOnEntity(org.bukkit.entity.Entity origin, String tag) {
		return createHologramOnEntity(origin, tag, true);
	}

}

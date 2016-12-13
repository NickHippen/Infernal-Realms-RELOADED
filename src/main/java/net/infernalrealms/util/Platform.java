package net.infernalrealms.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;

import net.infernalrealms.cutscenes.WrappedLocation.SerializableLocation;

public class Platform implements Serializable {

	private static final long serialVersionUID = -320766133661779282L;

	private SerializableLocation c1;
	private SerializableLocation c2;

	private List<Material> materials = new ArrayList<>();

	public Platform(Location c1, Location c2) {
		this(new SerializableLocation(c1), new SerializableLocation(c2));
	}

	public Platform(SerializableLocation c1, SerializableLocation c2) {
		this.c1 = c1;
		this.c2 = c2;
		for (int x = getMinX(); x <= getMaxX(); x++) {
			for (int y = getMinY(); y <= getMaxY(); y++) {
				for (int z = getMinZ(); z <= getMaxZ(); z++) {
					materials.add(new Location(c1.getWorld(), x, y, z).getBlock().getType());
				}
			}
		}
	}

	/**
	 * @param corner the corner with the lowest x, y, z value of where you want the platform to move to
	 */
	public void moveTo(Location corner) {
		fill(true);
		int diffX = getMinX() - corner.getBlockX();
		int diffY = getMinY() - corner.getBlockY();
		int diffZ = getMinZ() - corner.getBlockZ();
		c1.subtract(diffX, diffY, diffZ);
		c2.subtract(diffX, diffY, diffZ);
		fill(false);
	}

	private void fill(boolean clear) {
		// Clear old spot & set new
		int minX = Math.min(c1.getBlockX(), c2.getBlockX());
		int maxX = Math.max(c1.getBlockX(), c2.getBlockX());
		int minY = Math.max(c1.getBlockY(), c2.getBlockY());
		int maxY = Math.min(c1.getBlockY(), c2.getBlockY());
		int minZ = Math.min(c1.getBlockZ(), c2.getBlockZ());
		int maxZ = Math.max(c1.getBlockZ(), c2.getBlockZ());
		int index = 0;
		for (int x = 0; x <= maxX - minX; x++) {
			for (int y = 0; y <= maxY - minY; y++) {
				for (int z = 0; z <= maxZ - minZ; z++) {
					Location l = new Location(c1.getWorld(), minX + x, minY + y, minZ + z);
					l.getBlock().setType(clear ? Material.AIR : materials.get(index));
					index++;
				}
			}
		}
	}

	public int getMinX() {
		return Math.min(c1.getBlockX(), c2.getBlockX());
	}

	public int getMaxX() {
		return Math.max(c1.getBlockX(), c2.getBlockX());
	}

	public int getMinY() {
		return Math.min(c1.getBlockY(), c2.getBlockY());
	}

	public int getMaxY() {
		return Math.max(c1.getBlockY(), c2.getBlockY());
	}

	public int getMinZ() {
		return Math.min(c1.getBlockZ(), c2.getBlockZ());
	}

	public int getMaxZ() {
		return Math.max(c1.getBlockZ(), c2.getBlockZ());
	}

	@Override
	public String toString() {
		return "Platform [c1=" + c1 + ", c2=" + c2 + ", materials=" + materials + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((c1 == null) ? 0 : c1.hashCode());
		result = prime * result + ((c2 == null) ? 0 : c2.hashCode());
		result = prime * result + ((materials == null) ? 0 : materials.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Platform other = (Platform) obj;
		if (c1 == null) {
			if (other.c1 != null)
				return false;
		} else if (!c1.equals(other.c1))
			return false;
		if (c2 == null) {
			if (other.c2 != null)
				return false;
		} else if (!c2.equals(other.c2))
			return false;
		if (materials == null) {
			if (other.materials != null)
				return false;
		} else if (!materials.equals(other.materials))
			return false;
		return true;
	}

}

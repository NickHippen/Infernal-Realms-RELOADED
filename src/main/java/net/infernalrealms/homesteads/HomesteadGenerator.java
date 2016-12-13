package net.infernalrealms.homesteads;

import java.util.Random;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.generator.ChunkGenerator;

public class HomesteadGenerator extends ChunkGenerator {

	@Override
	public Location getFixedSpawnLocation(World world, Random random) {
		return new Location(world, 0, 64, 0);
	}

	@Override
	public boolean canSpawn(World world, int x, int z) {
		return true;
	}

	@Override
	public byte[][] generateBlockSections(World world, Random random, int x, int z, BiomeGrid biomes) {
		byte[][] result = new byte[world.getMaxHeight() / 16][];
		if (x == 0 && z == 0) {
			for (int coordX = 0; coordX < 16; coordX++) {
				for (int coordZ = 0; coordZ < 16; coordZ++) {
					setBlock(result, coordX, 60, coordZ, (byte) Material.GRASS.getId());
				}
			}
			for (int coordX = 0; coordX < 16; coordX++) {
				for (int coordZ = 0; coordZ < 16; coordZ++) {
					for (int coordY = 0; coordY < 60; coordY++) {
						setBlock(result, coordX, coordY, coordZ, (byte) Material.DIRT.getId());
					}
				}
			}
		}
		return result;
	}

	void setBlock(byte[][] result, int x, int y, int z, byte blkid) {
		if (result[y >> 4] == null) {
			result[y >> 4] = new byte[4096];
		}
		result[y >> 4][((y & 0xF) << 8) | (z << 4) | x] = blkid;
	}

}

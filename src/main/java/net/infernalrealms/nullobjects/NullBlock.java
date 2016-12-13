package net.infernalrealms.nullobjects;

import java.util.Collection;
import java.util.List;

import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.block.PistonMoveReaction;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;

public class NullBlock implements Block {

	@Override
	public List<MetadataValue> getMetadata(String s) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean hasMetadata(String s) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void removeMetadata(String s, Plugin plugin) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setMetadata(String s, MetadataValue metadatavalue) {
		// TODO Auto-generated method stub

	}

	@Override
	public byte getData() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Block getRelative(int i, int j, int k) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Block getRelative(BlockFace blockface) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Block getRelative(BlockFace blockface, int i) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Material getType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getTypeId() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public byte getLightLevel() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public byte getLightFromSky() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public byte getLightFromBlocks() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public World getWorld() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getX() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getY() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getZ() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Location getLocation() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Location getLocation(Location location) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Chunk getChunk() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setData(byte byte0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setData(byte byte0, boolean flag) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setType(Material material) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setType(Material material, boolean flag) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean setTypeId(int i) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean setTypeId(int i, boolean flag) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean setTypeIdAndData(int i, byte byte0, boolean flag) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public BlockFace getFace(Block block) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BlockState getState() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Biome getBiome() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setBiome(Biome biome) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isBlockPowered() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isBlockIndirectlyPowered() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isBlockFacePowered(BlockFace blockface) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isBlockFaceIndirectlyPowered(BlockFace blockface) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int getBlockPower(BlockFace blockface) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getBlockPower() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isLiquid() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public double getTemperature() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getHumidity() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public PistonMoveReaction getPistonMoveReaction() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean breakNaturally() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean breakNaturally(ItemStack itemstack) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Collection<ItemStack> getDrops() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<ItemStack> getDrops(ItemStack itemstack) {
		// TODO Auto-generated method stub
		return null;
	}

}

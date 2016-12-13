package net.infernalrealms.nullobjects;

import java.io.File;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.bukkit.BlockChangeDelegate;
import org.bukkit.Chunk;
import org.bukkit.ChunkSnapshot;
import org.bukkit.Difficulty;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.TreeType;
import org.bukkit.World;
import org.bukkit.WorldBorder;
import org.bukkit.WorldType;
import org.bukkit.block.Biome;
import org.bukkit.block.Block;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Item;
import org.bukkit.entity.LightningStrike;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.Vector;

public class NullWorld implements World {

	@Override
	public Set<String> getListeningPluginChannels() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void sendPluginMessage(Plugin plugin, String s, byte[] abyte0) {
		// TODO Auto-generated method stub

	}

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
	public Block getBlockAt(int i, int j, int k) {
		// TODO Auto-generated method stub
		return new NullBlock();
	}

	@Override
	public Block getBlockAt(Location location) {
		// TODO Auto-generated method stub
		return new NullBlock();
	}

	@Override
	public int getBlockTypeIdAt(int i, int j, int k) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getBlockTypeIdAt(Location location) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getHighestBlockYAt(int i, int j) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getHighestBlockYAt(Location location) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Block getHighestBlockAt(int i, int j) {
		// TODO Auto-generated method stub
		return new NullBlock();
	}

	@Override
	public Block getHighestBlockAt(Location location) {
		// TODO Auto-generated method stub
		return new NullBlock();
	}

	@Override
	public Chunk getChunkAt(int i, int j) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Chunk getChunkAt(Location location) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Chunk getChunkAt(Block block) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isChunkLoaded(Chunk chunk) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Chunk[] getLoadedChunks() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void loadChunk(Chunk chunk) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isChunkLoaded(int i, int j) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isChunkInUse(int i, int j) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void loadChunk(int i, int j) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean loadChunk(int i, int j, boolean flag) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean regenerateChunk(int i, int j) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean refreshChunk(int i, int j) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean setSpawnLocation(int i, int j, int k) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setFullTime(long l) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean hasStorm() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setStorm(boolean flag) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isThundering() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setPVP(boolean flag) {
		// TODO Auto-generated method stub

	}

	@Override
	public void save() {
		// TODO Auto-generated method stub

	}

	@Override
	public void playEffect(Location location, Effect effect, int i) {
		// TODO Auto-generated method stub

	}

	@Override
	public void playEffect(Location location, Effect effect, int i, int j) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setSpawnFlags(boolean flag, boolean flag1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setBiome(int i, int j, Biome biome) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setKeepSpawnInMemory(boolean flag) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isAutoSave() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setAutoSave(boolean flag) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setDifficulty(Difficulty difficulty) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setMonsterSpawnLimit(int i) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setAnimalSpawnLimit(int i) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setAmbientSpawnLimit(int i) {
		// TODO Auto-generated method stub

	}

	@Override
	public void playSound(Location location, Sound sound, float f, float f1) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean setGameRuleValue(String s, String s1) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isGameRule(String s) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public <T> void playEffect(Location location, Effect effect, T data) {
		// TODO Auto-generated method stub

	}

	@Override
	public <T> void playEffect(Location location, Effect effect, T data, int radius) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean unloadChunk(Chunk chunk) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean unloadChunk(int i, int j) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean unloadChunk(int i, int j, boolean flag) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean unloadChunk(int i, int j, boolean flag, boolean flag1) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean unloadChunkRequest(int i, int j) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean unloadChunkRequest(int i, int j, boolean flag) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Item dropItem(Location location, ItemStack itemstack) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Item dropItemNaturally(Location location, ItemStack itemstack) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Arrow spawnArrow(Location location, Vector vector, float f, float f1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean generateTree(Location location, TreeType treetype) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean generateTree(Location location, TreeType treetype, BlockChangeDelegate blockchangedelegate) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Entity spawnEntity(Location location, EntityType entitytype) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LightningStrike strikeLightning(Location location) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LightningStrike strikeLightningEffect(Location location) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Entity> getEntities() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<LivingEntity> getLivingEntities() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T extends Entity> Collection<T> getEntitiesByClass(Class<T>... aclass) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T extends Entity> Collection<T> getEntitiesByClass(Class<T> class1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<Entity> getEntitiesByClasses(Class<?>... aclass) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Player> getPlayers() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<Entity> getNearbyEntities(Location location, double d, double d1, double d2) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UUID getUID() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Location getSpawnLocation() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getTime() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setTime(long l) {
		// TODO Auto-generated method stub

	}

	@Override
	public long getFullTime() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getWeatherDuration() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setWeatherDuration(int i) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setThundering(boolean flag) {
		// TODO Auto-generated method stub

	}

	@Override
	public int getThunderDuration() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setThunderDuration(int i) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean createExplosion(double d, double d1, double d2, float f) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean createExplosion(double d, double d1, double d2, float f, boolean flag) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean createExplosion(double d, double d1, double d2, float f, boolean flag, boolean flag1) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean createExplosion(Location location, float f) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean createExplosion(Location location, float f, boolean flag) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Environment getEnvironment() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getSeed() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean getPVP() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public ChunkGenerator getGenerator() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<BlockPopulator> getPopulators() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T extends Entity> T spawn(Location location, Class<T> class1) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public FallingBlock spawnFallingBlock(Location location, Material material, byte byte0) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public FallingBlock spawnFallingBlock(Location location, int i, byte byte0) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ChunkSnapshot getEmptyChunkSnapshot(int i, int j, boolean flag, boolean flag1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean getAllowAnimals() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean getAllowMonsters() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Biome getBiome(int i, int j) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public double getTemperature(int i, int j) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getHumidity(int i, int j) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getMaxHeight() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getSeaLevel() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean getKeepSpawnInMemory() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Difficulty getDifficulty() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public File getWorldFolder() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public WorldType getWorldType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean canGenerateStructures() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public long getTicksPerAnimalSpawns() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setTicksPerAnimalSpawns(int i) {
		// TODO Auto-generated method stub

	}

	@Override
	public long getTicksPerMonsterSpawns() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setTicksPerMonsterSpawns(int i) {
		// TODO Auto-generated method stub

	}

	@Override
	public int getMonsterSpawnLimit() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getAnimalSpawnLimit() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getWaterAnimalSpawnLimit() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setWaterAnimalSpawnLimit(int i) {
		// TODO Auto-generated method stub

	}

	@Override
	public int getAmbientSpawnLimit() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String[] getGameRules() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getGameRuleValue(String s) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Spigot spigot() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public WorldBorder getWorldBorder() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		return obj instanceof NullWorld;
	}

	@Override
	public void playSound(Location location, String s, float f, float f1) {
		// TODO Auto-generated method stub

	}

	@Override
	public <T extends Arrow> T spawnArrow(Location location, Vector vector, float f, float f1, Class<T> class1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void spawnParticle(Particle particle, Location location, int i) {
		// TODO Auto-generated method stub

	}

	@Override
	public <T> void spawnParticle(Particle arg0, Location arg1, int arg2, T arg3) {
		// TODO Auto-generated method stub

	}

	@Override
	public void spawnParticle(Particle particle, double d, double d1, double d2, int i) {
		// TODO Auto-generated method stub

	}

	@Override
	public <T> void spawnParticle(Particle arg0, double arg1, double arg2, double arg3, int arg4, T arg5) {
		// TODO Auto-generated method stub

	}

	@Override
	public void spawnParticle(Particle particle, Location location, int i, double d, double d1, double d2) {
		// TODO Auto-generated method stub

	}

	@Override
	public <T> void spawnParticle(Particle arg0, Location arg1, int arg2, double arg3, double arg4, double arg5, T arg6) {
		// TODO Auto-generated method stub

	}

	@Override
	public void spawnParticle(Particle particle, Location location, int i, double d, double d1, double d2, double d3) {
		// TODO Auto-generated method stub

	}

	@Override
	public void spawnParticle(Particle particle, double d, double d1, double d2, int i, double d3, double d4, double d5) {
		// TODO Auto-generated method stub

	}

	@Override
	public <T> void spawnParticle(Particle arg0, Location arg1, int arg2, double arg3, double arg4, double arg5, double arg6, T arg7) {
		// TODO Auto-generated method stub

	}

	@Override
	public <T> void spawnParticle(Particle arg0, double arg1, double arg2, double arg3, int arg4, double arg5, double arg6, double arg7,
			T arg8) {
		// TODO Auto-generated method stub

	}

	@Override
	public void spawnParticle(Particle particle, double d, double d1, double d2, int i, double d3, double d4, double d5, double d6) {
		// TODO Auto-generated method stub

	}

	@Override
	public <T> void spawnParticle(Particle arg0, double arg1, double arg2, double arg3, int arg4, double arg5, double arg6, double arg7,
			double arg8, T arg9) {
		// TODO Auto-generated method stub

	}

}

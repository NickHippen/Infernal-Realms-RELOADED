package net.infernalrealms.mobs.types;

import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_9_R1.CraftWorld;
import org.bukkit.scheduler.BukkitRunnable;

import net.infernalrealms.general.InfernalRealms;
import net.infernalrealms.mobs.CustomMobData;
import net.infernalrealms.mobs.HologramHandler;
import net.infernalrealms.mobs.MobType;
import net.infernalrealms.mobs.SpawnManager;
import net.minecraft.server.v1_9_R1.Block;
import net.minecraft.server.v1_9_R1.BlockPosition;
import net.minecraft.server.v1_9_R1.DamageSource;
import net.minecraft.server.v1_9_R1.Entity;
import net.minecraft.server.v1_9_R1.EntityInsentient;
import net.minecraft.server.v1_9_R1.EntityLiving;
import net.minecraft.server.v1_9_R1.EntitySpider;
import net.minecraft.server.v1_9_R1.EntityTypes;
import net.minecraft.server.v1_9_R1.MinecraftKey;
import net.minecraft.server.v1_9_R1.NBTTagCompound;
import net.minecraft.server.v1_9_R1.NBTTagList;
import net.minecraft.server.v1_9_R1.SoundEffect;
import net.minecraft.server.v1_9_R1.World;
import net.minecraft.server.v1_9_R1.WorldServer;

public class CustomSpider extends EntitySpider implements InfernalMob {

	public static final int ENTITY_ID = 52;

	private CustomMobData data;

	public CustomSpider(World world) {
		super(world);
		this.fireProof = true;
		this.persistent = true;
	}

	public CustomSpider(org.bukkit.World world, CustomMobData customMobData) {
		this(((CraftWorld) world).getHandle(), customMobData);
	}

	public CustomSpider(World world, CustomMobData customMobData) {
		super(world);
		this.data = customMobData;
		this.fireProof = true;
		this.persistent = true;
		onCreation(goalSelector, targetSelector, true);
	}

	@Override
	public boolean damageEntity(DamageSource damagesource, float damage) {
		damage = handleDamage(damagesource, damage);
		if (damage == -1) {
			return false;
		}
		return super.damageEntity(damagesource, damage);
	}

	@Override
	public void die() {
		onDeath();
		super.die();
	}

	@Override
	public CustomMobData getData() {
		return this.data;
	}

	@Override
	public EntityInsentient getSelf() {
		return this;
	}

	@Override
	public Entity teleportTo(Location exit, boolean portal) {
		WorldServer worldserver = ((CraftWorld) getBukkitEntity().getLocation().getWorld()).getHandle();
		WorldServer worldserver1 = ((CraftWorld) exit.getWorld()).getHandle();
		int i = worldserver1.dimension;
		dimension = i;
		world.kill(this);
		dead = false;
		world.methodProfiler.a("reposition");
		worldserver1.getMinecraftServer().getPlayerList().repositionEntity(this, exit, portal);
		world.methodProfiler.c("reloading");
		Entity entity = EntityTypes.createEntityByName(EntityTypes.b(this), worldserver1);
		if (entity != null) {
			//			entity.a(this);
			boolean flag = entity.attachedToPlayer;
			entity.attachedToPlayer = true;
			worldserver1.addEntity(entity);
			entity.attachedToPlayer = flag;
			worldserver1.entityJoinedWorld(entity, false);
			getBukkitEntity().setHandle(entity);
			if (this instanceof EntityInsentient)
				((EntityInsentient) this).unleash(true, false);
		}
		dead = true;
		world.methodProfiler.b();
		worldserver.m();
		worldserver1.m();
		world.methodProfiler.b();
		return entity;
	}

	/**
	 * Entity "Speak"
	 */
	@Override
	protected SoundEffect G() {
		return speakSound() == null ? super.G() : new SoundEffect(new MinecraftKey(""));
	}

	/**
	 * Entity Hurt
	 */
	@Override
	protected SoundEffect bR() {
		return hurtSound() == null ? super.bR() : new SoundEffect(new MinecraftKey(""));
	}

	/**
	 * Entity Death
	 */
	@Override
	protected SoundEffect bS() {
		return deathSound() == null ? super.bS() : new SoundEffect(new MinecraftKey(""));
	}

	/**
	 * Entity Walk
	 */
	@Override
	protected void a(BlockPosition blockposition, Block block) {
		if (!walkSound()) {
			super.a(blockposition, block);
		}
	}

	/**
	 * Arrow attacks
	 */
	@Override
	public void a(EntityLiving entityliving, float f) {
		fireArrow(entityliving);
	}

	/**
	 * load()
	 */
	@Override
	public void f(NBTTagCompound nbttagcompound) {
		NBTTagCompound dataTag = nbttagcompound.getCompound("CustomMobData");
		CustomMobData data = new CustomMobData();
		data.setMobType(MobType.fromString(dataTag.getString("MobType")));
		data.setLevel(dataTag.getInt("Level"));
		data.setDamage(dataTag.getDouble("Damage"));
		data.setSpeed(dataTag.getDouble("Speed"));
		data.setFollowRangeMult(dataTag.getDouble("FollowRangeMult"));
		data.setKnockBackResist(dataTag.getDouble("KnockBackResist"));
		data.setName(dataTag.getString("Name"));
		data.setFullName(dataTag.getString("FullName"));
		data.setIdentifierName(dataTag.getString("IdentifierName"));
		data.setExp(dataTag.getInt("Exp"));
		data.setMoney(dataTag.getLong("Money"));
		data.setMaxHealth(dataTag.getDouble("MaxHealth"));
		data.setInvisible(dataTag.getBoolean("Invisible"));
		data.setAgressive(dataTag.getBoolean("Aggressive"));
		if (dataTag.hasKey("SpeakSound"))
			data.setSpeakSound(dataTag.getString("SpeakSound"));
		if (dataTag.hasKey("HurtSound"))
			data.setHurtSound(dataTag.getString("HurtSound"));
		if (dataTag.hasKey("DeathSound"))
			data.setDeathSound(dataTag.getString("DeathSound"));
		if (dataTag.hasKey("WalkSound"))
			data.setDeathSound(dataTag.getString("WalkSound"));
		if (dataTag.hasKey("Spawn")) {
			data.setSpawn(dataTag.getString("Spawn"));
			SpawnManager.spawnPointMobs.put(data.getSpawn(), getBukkitLivingEntity());
		}
		NBTTagList nbttaglist = dataTag.getList("Drops", 10);
		for (int i = 0; i < nbttaglist.size(); i++) {
			NBTTagCompound dropTag = nbttaglist.get(i);
			String drop = dropTag.getString("Drop");
			double chance = dropTag.getDouble("DropChance");
			data.addDrop(drop, chance);
		}
		this.data = data;
		super.f(nbttagcompound);
		new BukkitRunnable() {
			@Override
			public void run() {
				data.setNameTag(HologramHandler.createHologramOnEntity(getBukkitEntity(), data.getFullName()));
			}
		}.runTaskLater(InfernalRealms.getPlugin(), 75L);
		onCreation(goalSelector, targetSelector);
	}

	/**
	 * save()
	 */
	@Override
	public void e(NBTTagCompound nbttagcompound) {
		if (getData() != null) {
			nbttagcompound.set("CustomMobData", getData().toNBTTag());
		} else {
			super.die();
		}
		super.e(nbttagcompound);
	}

	@Override
	public void setOnFire(int i) {}

	@Override
	public void move(double d0, double d1, double d2) {
		super.move(d0, d1, d2);
		onMove();
	}

	@Override
	public void U() {
		super.U();
		update();
		/*
		 * The following is the K() method in v1_8_R3:
		 
		public void K()
		{
		super.K();
		world.methodProfiler.a("mobBaseTick");
		if(isAlive() && random.nextInt(1000) < a_++)
		{
		    a_ = -w();
		    x();
		}
		world.methodProfiler.b();
		}
		 
		 */
	}

}
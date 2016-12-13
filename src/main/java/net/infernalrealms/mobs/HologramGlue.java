package net.infernalrealms.mobs;

import java.lang.reflect.Field;

import org.bukkit.craftbukkit.v1_9_R1.util.UnsafeList;
import org.bukkit.entity.Entity;

import net.minecraft.server.v1_9_R1.EntitySlime;
import net.minecraft.server.v1_9_R1.PathfinderGoalSelector;
import net.minecraft.server.v1_9_R1.World;

@Deprecated
public class HologramGlue extends EntitySlime {

	public static final int ENTITY_ID = 55;

	public HologramGlue(World world) {
		super(world);
		getBukkitEntity().remove();
	}

	public HologramGlue(World world, Entity below, boolean first) {
		super(world);
		// Clear existing AI
		try {
			Field bField = PathfinderGoalSelector.class.getDeclaredField("b");
			bField.setAccessible(true);
			Field cField = PathfinderGoalSelector.class.getDeclaredField("c");
			cField.setAccessible(true);
			bField.set(goalSelector, new UnsafeList<PathfinderGoalSelector>());
			bField.set(targetSelector, new UnsafeList<PathfinderGoalSelector>());
			cField.set(goalSelector, new UnsafeList<PathfinderGoalSelector>());
			cField.set(targetSelector, new UnsafeList<PathfinderGoalSelector>());
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.persistent = false;
		setSize(first ? -1 : -3);
		setInvisible(true);
		//		addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, 0));
	}
	//
	//	@Override
	//	public void setSize(int i) {
	//		this.datawatcher.watch(16, Byte.valueOf((byte) i));
	//		a(0.51000005F * 1, 0.51000005F * 1);
	//		setPosition(this.locX, this.locY, this.locZ);
	//		getAttributeInstance(GenericAttributes.maxHealth).setValue(10);
	//		getAttributeInstance(GenericAttributes.MOVEMENT_SPEED).setValue(0);
	//		setHealth(getMaxHealth());
	//		this.b_ = (int) i;
	//	}
	//
	//	@Override
	//	public void mount(net.minecraft.server.v1_8_R3.Entity entity) {}
	//
	//	public void setPassengerOf(net.minecraft.server.v1_8_R3.Entity entity) {
	//		super.mount(entity);
	//	}
	//
	//	@Override
	//	public void die() {
	//		super.die();
	//		//TODO
	//	}
	//
	//	@Override
	//	public boolean damageEntity(DamageSource damagesource, float f) {
	//		// TODO Redirect damage to the connected entity.
	//		return false;
	//	}
	//
	//	@SuppressWarnings("deprecation")
	//	public void addPotionEffect(PotionEffect effect) {
	//		CraftLivingEntity craft = (CraftLivingEntity) getBukkitEntity();
	//		if (craft.hasPotionEffect(effect.getType())) {
	//			craft.removePotionEffect(effect.getType());
	//		}
	//		addEffect(new MobEffect(effect.getType().getId(), effect.getDuration(), effect.getAmplifier(), effect.isAmbient(), false));
	//	}

}

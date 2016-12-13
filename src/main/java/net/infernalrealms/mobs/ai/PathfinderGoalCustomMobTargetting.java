package net.infernalrealms.mobs.ai;

import net.minecraft.server.v1_9_R1.EntityCreature;
import net.minecraft.server.v1_9_R1.EntityLiving;
import net.minecraft.server.v1_9_R1.PathfinderGoalNearestAttackableTarget;

public class PathfinderGoalCustomMobTargetting<T extends EntityLiving> extends PathfinderGoalNearestAttackableTarget<T> {

	public PathfinderGoalCustomMobTargetting(EntityCreature attacker, Class<T> targetType, boolean flag, int aggression /*TODO*/) {
		super(attacker, targetType, 1, flag, false, null);
	}

	/**
	 * getFollowRange()
	 */
	@Override
	protected double f() {
		return super.f();
	}
	
	/**
	 * ?
	 */
	@Override
	public void c() {
		super.c();
	}
	
	/**
	 * clearGoalTarget()
	 */
	@Override
	public void d() {
		super.d();
	}

}

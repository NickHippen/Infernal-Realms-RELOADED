package net.infernalrealms.mobs.ai;

import net.minecraft.server.v1_9_R1.EntityCreature;
import net.minecraft.server.v1_9_R1.EntityLiving;
import net.minecraft.server.v1_9_R1.PathfinderGoalHurtByTarget;

public class PathfinderGoalCustomHurtByTarget extends PathfinderGoalHurtByTarget {

	public PathfinderGoalCustomHurtByTarget(EntityCreature entitycreature, boolean flag, Class[] aclass) {
		super(entitycreature, flag, aclass);
	}

	@Override
	public boolean a() {
		System.out.println("Called a()");
		return super.a();
	}

	@Override
	public void c() {
		System.out.println("Called c()");
		super.c();
	}

	@Override
	protected void a(EntityCreature entitycreature, EntityLiving entityliving) {
		System.out.println("Called a(e, e)");
		super.a(entitycreature, entityliving);
	}

}

package net.infernalrealms.mobs.types;

import net.minecraft.server.v1_9_R1.EntityArrow;
import net.minecraft.server.v1_9_R1.EntityLiving;
import net.minecraft.server.v1_9_R1.ItemStack;
import net.minecraft.server.v1_9_R1.World;

public class CustomArrow extends EntityArrow {

	public static final int ENTITY_ID = 10;

	public CustomArrow(World world) {
		super(world);
	}

	public CustomArrow(World world, EntityLiving source, float f) {
		super(world, source);
	}

	@Override
	protected ItemStack j() {
		return null;
	}

	//	@Override
	//	public void t_() {
	//		super.t_();
	//		if (inGround) {
	//			die();
	//		}
	//	}

}

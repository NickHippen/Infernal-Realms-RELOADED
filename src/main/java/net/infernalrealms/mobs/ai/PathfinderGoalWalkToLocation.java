package net.infernalrealms.mobs.ai;

import org.bukkit.Location;

import net.minecraft.server.v1_9_R1.EntityInsentient;
import net.minecraft.server.v1_9_R1.NavigationAbstract;
import net.minecraft.server.v1_9_R1.PathEntity;
import net.minecraft.server.v1_9_R1.PathfinderGoal;

public class PathfinderGoalWalkToLocation extends PathfinderGoal {

	private double speed;
	private EntityInsentient entity;
	private Location location;
	private NavigationAbstract navigation;

	public PathfinderGoalWalkToLocation(EntityInsentient entity, Location location, double speed) {
		this.entity = entity;
		this.location = location;
		this.navigation = this.entity.getNavigation();
		this.speed = speed;
	}

	/**
	 * shouldStart();
	 */
	@Override
	public boolean a() {
		return true;
	}

	/**
	 * onStart()
	 */
	@Override
	public void c() {
		PathEntity pathEntity = this.navigation.a(this.location.getX(), this.location.getY(), this.location.getZ());
		if (pathEntity == null) {
			return;
		}
		this.navigation.a(pathEntity, speed);
	}

}

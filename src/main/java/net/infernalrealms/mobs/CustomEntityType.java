package net.infernalrealms.mobs;

import java.util.Map;

import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_9_R1.CraftWorld;

import net.infernalrealms.mobs.types.CustomArrow;
import net.infernalrealms.mobs.types.CustomBat;
import net.infernalrealms.mobs.types.CustomBlaze;
import net.infernalrealms.mobs.types.CustomCaveSpider;
import net.infernalrealms.mobs.types.CustomEnderman;
import net.infernalrealms.mobs.types.CustomEndermite;
import net.infernalrealms.mobs.types.CustomGuardian;
import net.infernalrealms.mobs.types.CustomIronGolem;
import net.infernalrealms.mobs.types.CustomRabbit;
import net.infernalrealms.mobs.types.CustomSilverfish;
import net.infernalrealms.mobs.types.CustomSkeleton;
import net.infernalrealms.mobs.types.CustomSpider;
import net.infernalrealms.mobs.types.CustomWitch;
import net.infernalrealms.mobs.types.CustomWolf;
import net.infernalrealms.mobs.types.CustomZombie;
import net.infernalrealms.mobs.types.CustomZombiePigman;
import net.infernalrealms.util.ReflectionUtil;
import net.minecraft.server.v1_9_R1.Entity;
import net.minecraft.server.v1_9_R1.EntityTypes;

public enum CustomEntityType {
	//@formatter:off
	//NAME("Entity name", Entity ID, yourcustomclass.class);
	CUSTOM_BAT("CustomBat", CustomBat.ENTITY_ID, CustomBat.class),
	CUSTOM_ZOMBIE("CustomZombie", CustomZombie.ENTITY_ID, CustomZombie.class),
	CUSTOM_SKELETON("CustomSkeleton", CustomSkeleton.ENTITY_ID, CustomSkeleton.class),
	CUSTOM_WOLF("CustomWolf", CustomWolf.ENTITY_ID, CustomWolf.class),
	CUSTOM_BLAZE("CustomBlaze", CustomBlaze.ENTITY_ID, CustomBlaze.class),
	CUSTOM_SPIDER("CustomSpider", CustomSpider.ENTITY_ID, CustomSpider.class),
	CUSTOM_CAVESPIDER("CustomCaveSpider", CustomCaveSpider.ENTITY_ID, CustomCaveSpider.class),
	CUSTOM_ENDERMAN("CustomEnderman", CustomEnderman.ENTITY_ID, CustomEnderman.class),
	CUSTOM_ENDERMITE("CustomEndermite", CustomEndermite.ENTITY_ID, CustomEndermite.class),
	CUSTOM_GUARDIAN("CustomGuardian", CustomGuardian.ENTITY_ID, CustomGuardian.class),
	CUSTOM_IRONGOLEM("CustomIronGolem", CustomIronGolem.ENTITY_ID, CustomIronGolem.class),
	CUSTOM_RABBIT("CustomRabbit", CustomRabbit.ENTITY_ID, CustomRabbit.class),
	CUSTOM_SILVERFISH("CustomSilverfish", CustomSilverfish.ENTITY_ID, CustomSilverfish.class),
	CUSTOM_WITCH("CustomWitch", CustomWitch.ENTITY_ID, CustomWitch.class),
	CUSTOM_ZOMBIEPIGMAN("CustomZombiePigman", CustomZombiePigman.ENTITY_ID, CustomZombiePigman.class),
	
	// Misc
	CUSTOM_ARROW("CustomArrow", CustomArrow.ENTITY_ID, CustomArrow.class),
	HOLOGRAM_GLUE("HologramGlue", HologramGlue.ENTITY_ID, HologramGlue.class),
	HOLOGRAM_LINE("HologramLine", InfernalHologramLine.ENTITY_ID, InfernalHologramLine.class),
	;
	//@formatter:on

	private String name;
	private int id;
	private Class<? extends Entity> custom;

	private CustomEntityType(String name, int id, Class<? extends Entity> custom) {
		this.name = name;
		this.id = id;
		this.custom = custom;
	}

	public static void spawnEntity(Entity entity, Location loc) {
		entity.setLocation(loc.getX(), loc.getY(), loc.getZ(), loc.getYaw(), loc.getPitch());
		((CraftWorld) loc.getWorld()).getHandle().addEntity(entity);
	}

	private static void addToMaps(Class clazz, String name, int id) {
		System.out.println("Added " + name + " (" + id + ") to custom mob registry.");
		//getPrivateField is the method from above.
		//Remove the lines with // in front of them if you want to override default entities (You'd have to remove the default entity from the map first though).
		((Map) ReflectionUtil.getPrivateField("c", EntityTypes.class, null)).put(name, clazz);
		((Map) ReflectionUtil.getPrivateField("d", EntityTypes.class, null)).put(clazz, name);
		//		((Map) ReflectionUtil.getPrivateField("e", net.minecraft.server.v1_8_R1.EntityTypes.class, null)).put(Integer.valueOf(id), clazz); //
		((Map) ReflectionUtil.getPrivateField("f", EntityTypes.class, null)).put(clazz, Integer.valueOf(id));
		//		((Map) ReflectionUtil.getPrivateField("g", net.minecraft.server.v1_8_R1.EntityTypes.class, null)).put(name, Integer.valueOf(id)); //
	}

	public static void registerCustomMobs() {
		System.out.println("Registering Custom Mobs...");
		for (CustomEntityType e : values()) {
			addToMaps(e.custom, e.name, e.id);
		}
		System.out.println("Registration Complete!");
	}

}
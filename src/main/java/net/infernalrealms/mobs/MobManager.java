package net.infernalrealms.mobs;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_9_R1.entity.CraftLivingEntity;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;

import net.infernalrealms.mobs.types.InfernalMob;

public class MobManager {

	private static ArrayList<LivingEntity> spawnedMob = new ArrayList<>();
	private static ArrayList<LivingEntity> recentlyHurt = new ArrayList<LivingEntity>();

	public static void addNewMob(LivingEntity entity) {
		spawnedMob.add(entity);
	}

	public static ArrayList<LivingEntity> getSpawnedMobs() {
		return spawnedMob;
	}

	public static List<LivingEntity> getRecentlyHurt() {
		return recentlyHurt;
	}

	public static void butcherMobs() {
		for (LivingEntity mob : spawnedMob) {
			if (((CraftLivingEntity) mob).getHandle() instanceof InfernalMob) {
				InfernalMob im = (InfernalMob) ((CraftLivingEntity) mob).getHandle();
				im.getSelf().dead = true;
				if (im.getData().getNameTag() != null) {
					im.getData().getNameTag().delete();
				}
				if (im.getData().getHealthBar() != null) {
					im.getData().getHealthBar().delete();
				}
			}
		}
		spawnedMob.clear();
		SpawnManager.getSpawnPointMobs().clear();
	}

	public static void removeAllEntities() {
		for (World w : Bukkit.getServer().getWorlds()) {
			for (Entity e : w.getEntities()) {
				if (e.getType() != EntityType.PLAYER && e.getType() != EntityType.ITEM_FRAME && e.getType() != EntityType.PAINTING) {
					e.remove();
				}
			}
		}
	}

	public static void removeAllNonSpawnEntities() {
		for (World w : Bukkit.getServer().getWorlds()) {
			for (Entity e : w.getEntities()) {
				if (e instanceof LivingEntity) {
					if (e.getType() != EntityType.PLAYER && e.getType() != EntityType.ITEM_FRAME && e.getType() != EntityType.PAINTING
							&& e.getType() != EntityType.ARMOR_STAND) {
						LivingEntity le = (LivingEntity) e;
						if (!SpawnManager.isSpawnPointMob(le)) {
							le.setHealth(0);
						}
					}
				}
			}
		}
	}
	//	public static void activateHealthBar(final LivingEntity entity) {
	//		InfernalRealms.getPlugin().getServer().getScheduler().scheduleSyncDelayedTask(InfernalRealms.getPlugin(), new Runnable() {
	//			public void run() {
	//				if (entity.isValid()) {
	//					CustomMob customMob = getSpawnedMobs().get(entity);
	//					double healthPercent = ((Damageable) entity).getHealth() / ((Damageable) entity).getMaxHealth();
	//					int redBars = (int) Math.floor(healthPercent * 30);
	//					String spacedBars = "| | | | | | | | | | | | | | | | | | | | | | | | | | | | | |";
	//					String[] barsSplit = spacedBars.split(" ");
	//					String bars = (customMob.getMobType() == MobType.NORMAL ? ChatColor.GREEN : ChatColor.GOLD) + "";
	//					for (int i = 0; i < barsSplit.length; i++) {
	//						if (i == redBars) {
	//							bars = bars + ChatColor.RED + barsSplit[i];
	//						} else {
	//							bars = bars + barsSplit[i];
	//						}
	//					}
	//					entity.setCustomName(bars);
	//					getRecentlyHurt().add(entity);
	//					resetNameTag(entity, customMob);
	//				}
	//			}
	//		}, 1L);
	//	}

}

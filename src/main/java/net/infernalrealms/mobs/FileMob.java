
package net.infernalrealms.mobs;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_9_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_9_R1.entity.CraftLivingEntity;
import org.bukkit.entity.Ageable;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import net.infernalrealms.general.InfernalRealms;
import net.infernalrealms.general.YAMLFile;
import net.infernalrealms.mobs.spells.MobSpell;
import net.infernalrealms.mobs.spells.MobSpellType;
import net.infernalrealms.mobs.spells.controllers.CastController;
import net.infernalrealms.mobs.spells.controllers.CastControllerType;
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
import net.infernalrealms.mobs.types.InfernalMob;
import net.infernalrealms.util.GeneralUtil;
import net.infernalrealms.util.InfernalStrings;
import net.infernalrealms.util.ReflectionUtil;
import net.minecraft.server.v1_9_R1.Entity;
import net.minecraft.server.v1_9_R1.EntityAgeable;

public class FileMob {

	public static boolean isFileMob(String mobID) {
		if (YAMLFile.MOBS.getConfig().contains(mobID)) {
			return true;
		}
		return false;
	}

	public static boolean isFileMob(int mobID) {
		return isFileMob("" + mobID);
	}

	@SuppressWarnings("deprecation")
	public static LivingEntity spawn(int mobID, Location location) {
		CustomMobData customMobData = new CustomMobData();
		int minLevel = 0;
		int maxLevel = 0;
		int bracketLoc = 0;
		String mobName = YAMLFile.MOBS.getConfig().getString(mobID + ".Name");
		for (int i = 0; i < mobName.length(); i++) {
			if (mobName.charAt(i) == '[') {
				bracketLoc = i;
				String[] levelStrings = mobName.substring(i).replace("[", "").replace("]", "").split("-");
				minLevel = Integer.parseInt(levelStrings[0]);
				maxLevel = Integer.parseInt(levelStrings[1]);
				break;
			}
		}
		if (minLevel == 0 || maxLevel == 0)
			return null;
		int level = minLevel + (int) (Math.random() * ((maxLevel - minLevel) + 1));
		customMobData.setLevel(level);
		MobType mobType = MobType.fromString(YAMLFile.MOBS.getConfig().getString(mobID + ".MobType").toLowerCase());
		customMobData.setMobType(mobType);
		customMobData.setName(mobName.substring(0, bracketLoc));
		String typeMod = "" + (mobType == MobType.NORMAL ? "" : ChatColor.UNDERLINE) + (mobType == MobType.BOSS ? ChatColor.BOLD : "");
		String fullName = ChatColor.DARK_GRAY + typeMod + "[" + Integer.toString(level) + "] " + ChatColor.GRAY + typeMod
				+ customMobData.getName();
		customMobData.setFullName(fullName);
		customMobData.setIdentifierName(mobName);
		customMobData.setMaxHealth(YAMLFile.MOBS.getConfig().getDouble(mobID + ".Health"));
		String[] moneySplit = YAMLFile.MOBS.getConfig().getString(mobID + ".Money").split("-");
		String[] minMoneySplit = moneySplit[0].split(",");
		String[] maxMoneySplit = moneySplit[1].split(",");
		long minGold = 0;
		long minSilver = 0;
		long minCopper = 0;
		long maxGold = 0;
		long maxSilver = 0;
		long maxCopper = 0;
		try {
			minGold = Long.parseLong(minMoneySplit[0]);
			minSilver = Long.parseLong(minMoneySplit[1]);
			minCopper = Long.parseLong(minMoneySplit[2]);
			maxGold = Long.parseLong(maxMoneySplit[0]);
			maxSilver = Long.parseLong(maxMoneySplit[1]);
			maxCopper = Long.parseLong(maxMoneySplit[2]);
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		long minMoney = minCopper + (minSilver * 100) + (minGold * 10000);
		long maxMoney = maxCopper + (maxSilver * 100) + (maxGold * 10000);
		long money = minMoney + (long) (new Random().nextDouble() * (maxMoney - minMoney));
		customMobData.setMoney(money);
		List<Map<?, ?>> damageList = YAMLFile.MOBS.getConfig().getMapList(mobID + ".Damage");
		double damage = 0;
		for (Map<?, ?> i : damageList) {
			if (i.containsKey(level)) {
				Object g = i.get(level);
				if (g instanceof Integer) {
					damage = ((Integer) i.get(level)).intValue();
				} else {
					damage = (Double) i.get(level);
				}
			}
		}
		customMobData.setDamage(damage);
		List<Map<?, ?>> expList = YAMLFile.MOBS.getConfig().getMapList(mobID + ".Exp");
		int exp = 0;
		for (Map<?, ?> i : expList) {
			if (i.containsKey(level)) {
				exp = (Integer) i.get(level);
			}
		}
		customMobData.setExp(exp);
		String headString = YAMLFile.MOBS.getConfig().getString(mobID + ".Equipment.Head");
		if (headString.contains("playerhead")) {
			String[] headStringSplit = headString.split(":");
			ItemStack playerHead = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
			SkullMeta playerHeadMeta = (SkullMeta) playerHead.getItemMeta();
			if (headStringSplit[1].startsWith("#")) {
				try {
					headStringSplit[1].replace("#", "");
					ReflectionUtil.setPrivateField("profile", playerHeadMeta.getClass(), playerHeadMeta,
							GeneralUtil.createGameProfile(headStringSplit[1], UUID.randomUUID()));
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				playerHeadMeta.setOwner(headStringSplit[1]);
			}
			playerHead.setItemMeta(playerHeadMeta);
			customMobData.setHelmet(playerHead);
		} else {
			int headId = YAMLFile.MOBS.getConfig().getInt(mobID + ".Equipment.Head");
			ItemStack head = new ItemStack(headId, 1, (short) 0);
			customMobData.setHelmet(head);
		}
		int chestplateId = YAMLFile.MOBS.getConfig().getInt(mobID + ".Equipment.Chest");
		ItemStack chestplate = new ItemStack(chestplateId, 1, (short) 0);
		customMobData.setChestplate(chestplate);
		int leggingsId = YAMLFile.MOBS.getConfig().getInt(mobID + ".Equipment.Leggings");
		ItemStack leggings = new ItemStack(leggingsId, 1, (short) 0);
		customMobData.setLeggings(leggings);
		int bootsId = YAMLFile.MOBS.getConfig().getInt(mobID + ".Equipment.Boots");
		ItemStack boots = new ItemStack(bootsId, 1, (short) 0);
		customMobData.setBoots(boots);
		int handId = YAMLFile.MOBS.getConfig().getInt(mobID + ".Equipment.Hand");
		ItemStack hand = new ItemStack(handId, 1, (short) 0);
		customMobData.setWeapon(hand);
		customMobData.setSpeed(YAMLFile.MOBS.getConfig().getDouble(mobID + ".Speed"));
		customMobData.setFollowRangeMult(YAMLFile.MOBS.getConfig().getDouble(mobID + ".FollowRangeMult"));
		customMobData.setKnockBackResist(YAMLFile.MOBS.getConfig().getDouble(mobID + ".KnockBackResist"));
		customMobData.setSpeakSound(YAMLFile.MOBS.getConfig().getString(mobID + ".Sounds.Speak"));
		customMobData.setHurtSound(YAMLFile.MOBS.getConfig().getString(mobID + ".Sounds.Hurt"));
		customMobData.setDeathSound(YAMLFile.MOBS.getConfig().getString(mobID + ".Sounds.Death"));
		customMobData.setWalkSound(YAMLFile.MOBS.getConfig().getString(mobID + ".Sounds.Walk"));
		List<String> drops = YAMLFile.MOBS.getConfig().getStringList(mobID + ".Drops");
		for (String drop : drops) {
			String[] dropSplit = drop.split(" ");
			double dropChance = Double.parseDouble(dropSplit[1]);
			customMobData.addDrop(drop, dropChance);
		}
		customMobData.setInvisible(YAMLFile.MOBS.getConfig().getBoolean(mobID + ".Invisible"));

		boolean baby = YAMLFile.MOBS.getConfig().getBoolean(mobID + ".Baby");
		InfernalMob customMob;
		switch (YAMLFile.MOBS.getConfig().getString(mobID + ".Type").toLowerCase()) {
		case "zombie":
			customMob = new CustomZombie(location.getWorld(), customMobData, baby);
			break;
		case "skeleton":
			customMob = new CustomSkeleton(location.getWorld(), customMobData, false);
			break;
		case "witherskeleton":
		case "wither skeleton":
			customMob = new CustomSkeleton(location.getWorld(), customMobData, true);
			break;
		case "spider":
			customMob = new CustomSpider(location.getWorld(), customMobData);
			break;
		case "bat":
			customMob = new CustomBat(location.getWorld(), customMobData);
			break;
		case "blaze":
			customMob = new CustomBlaze(location.getWorld(), customMobData);
			break;
		case "cavespider":
		case "cave spider":
			customMob = new CustomCaveSpider(location.getWorld(), customMobData);
			break;
		case "enderman":
			customMob = new CustomEnderman(location.getWorld(), customMobData);
			break;
		case "endermite":
			customMob = new CustomEndermite(location.getWorld(), customMobData);
			break;
		case "guardian":
			customMob = new CustomGuardian(location.getWorld(), customMobData);
			break;
		case "irongolem":
		case "iron golem":
			customMob = new CustomIronGolem(location.getWorld(), customMobData);
			break;
		case "rabbit":
			customMob = new CustomRabbit(location.getWorld(), customMobData);
			break;
		case "silverfish":
			customMob = new CustomSilverfish(location.getWorld(), customMobData);
			break;
		case "witch":
			customMob = new CustomWitch(location.getWorld(), customMobData);
			break;
		case "wolf":
			customMob = new CustomWolf(location.getWorld(), customMobData);
			break;
		case "zombiepigman":
		case "zombie pigman":
			customMob = new CustomZombiePigman(location.getWorld(), customMobData);
			break;
		default:
			return null;
		}

		// Spells
		if (YAMLFile.MOBS.getConfig().contains(mobID + ".Spells")) {
			List<String> spellStringList = YAMLFile.MOBS.getConfig().getStringList(mobID + ".Spells");
			for (String spellString : spellStringList) {
				String[] spellStringSplit = spellString.split("#");
				if (spellStringSplit.length != 2) {
					InfernalRealms.getPlugin().getLogger()
							.warning("Invalid formatting for mob " + mobID + "'s spell string: " + spellString);
					continue;
				}
				String spellName = InfernalStrings.stripFlags(spellStringSplit[0]);
				MobSpellType spellType = MobSpellType.fromString(spellName);
				MobSpell spell;
				try {
					spell = spellType.getSpellConstructor().newInstance(customMob);
				} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
					e.printStackTrace();
					continue;
				}
				spell.handleFlags(InfernalStrings.getValuedFlags(spellStringSplit[0]));

				// Controller
				String controllerName = InfernalStrings.stripFlags(spellStringSplit[1]);
				CastControllerType controllerType = CastControllerType.fromString(controllerName);
				CastController controller;
				try {
					controller = controllerType.getControllerConstructor().newInstance(spell);
				} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
					e.printStackTrace();
					continue;
				}
				controller.handleFlags(InfernalStrings.getValuedFlags(spellStringSplit[1]));
				spell.setController(controller);

				customMobData.addSpell(spell);
			}
		}

		if (baby && customMob instanceof EntityAgeable) {
			((Ageable) customMob.getBukkitLivingEntity()).setBaby();
			((Ageable) customMob.getBukkitLivingEntity()).setAgeLock(true);
		}
		location.getChunk().isLoaded();
		customMob.setPosition(location.getX(), location.getY(), location.getZ());
		((CraftWorld) location.getWorld()).getHandle().addEntity((Entity) customMob, SpawnReason.CUSTOM);
		customMobData.setNameTag(HologramHandler.createHologramOnEntity(customMob.getBukkitLivingEntity(), customMobData.getFullName()));
		MobManager.addNewMob(customMob.getBukkitLivingEntity());
		return customMob.getBukkitLivingEntity();
	}

	public static LivingEntity spawn(int mobID, Location location, String spawn) {
		LivingEntity entity = spawn(mobID, location);
		((InfernalMob) ((CraftLivingEntity) entity).getHandle()).getData().setSpawn(spawn);
		SpawnManager.getSpawnPointMobs().put(spawn, entity);
		return entity;
	}

}

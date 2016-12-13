package net.infernalrealms.mobs.types;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_9_R1.util.UnsafeList;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;
import com.google.common.collect.Sets;

import net.infernalrealms.general.InfernalRealms;
import net.infernalrealms.general.YAMLFile;
import net.infernalrealms.mobs.CustomMobData;
import net.infernalrealms.mobs.HologramHandler;
import net.infernalrealms.mobs.MobManager;
import net.infernalrealms.mobs.SpawnManager;
import net.infernalrealms.mobs.ai.PathfinderGoalCustomMobTargetting;
import net.infernalrealms.mobs.spells.MobSpell;
import net.infernalrealms.mobs.spells.controllers.DamagedCastController;
import net.infernalrealms.mobs.spells.controllers.DeathCastController;
import net.infernalrealms.party.Party;
import net.infernalrealms.player.DamageValue;
import net.infernalrealms.player.PlayerData;
import net.infernalrealms.skills.archer.Concentration;
import net.infernalrealms.skills.general.ArrowSkill;
import net.infernalrealms.skills.warrior.ConcussiveBlows;
import net.infernalrealms.util.GeneralUtil;
import net.infernalrealms.util.ReflectionUtil;
import net.minecraft.server.v1_9_R1.DamageSource;
import net.minecraft.server.v1_9_R1.EnchantmentManager;
import net.minecraft.server.v1_9_R1.Enchantments;
import net.minecraft.server.v1_9_R1.EntityDamageSourceIndirect;
import net.minecraft.server.v1_9_R1.EntityHuman;
import net.minecraft.server.v1_9_R1.EntityInsentient;
import net.minecraft.server.v1_9_R1.EntityLiving;
import net.minecraft.server.v1_9_R1.EntityMonster;
import net.minecraft.server.v1_9_R1.EntityTippedArrow;
import net.minecraft.server.v1_9_R1.EnumItemSlot;
import net.minecraft.server.v1_9_R1.GenericAttributes;
import net.minecraft.server.v1_9_R1.IRangedEntity;
import net.minecraft.server.v1_9_R1.Items;
import net.minecraft.server.v1_9_R1.MathHelper;
import net.minecraft.server.v1_9_R1.PathfinderGoalArrowAttack;
import net.minecraft.server.v1_9_R1.PathfinderGoalFloat;
import net.minecraft.server.v1_9_R1.PathfinderGoalLookAtPlayer;
import net.minecraft.server.v1_9_R1.PathfinderGoalMeleeAttack;
import net.minecraft.server.v1_9_R1.PathfinderGoalMoveTowardsRestriction;
import net.minecraft.server.v1_9_R1.PathfinderGoalRandomLookaround;
import net.minecraft.server.v1_9_R1.PathfinderGoalSelector;
import net.minecraft.server.v1_9_R1.SoundEffects;

public interface InfernalMob extends IRangedEntity {

	static final Random RANDOM = new Random();

	public static boolean isChunkEqual(Chunk chunk1, Chunk chunk2) {
		return chunk1.getWorld().equals(chunk2.getWorld()) && chunk1.getX() == chunk2.getX() && chunk1.getZ() == chunk2.getZ();
	}

	public default void onCreation(PathfinderGoalSelector goalSelector, PathfinderGoalSelector targetSelector, boolean firstRun) {
		if (getData() == null) {
			return;
		}
		if (getData().getSpawn() != null) {
			new BukkitRunnable() {

				@Override
				public void run() {
					// Reset to spawn point
					Location spawnLocation = SpawnManager.getSpawnLocation(getData().getSpawn());
					if (spawnLocation != null) {
						getSelf().setPosition(spawnLocation.getX(), spawnLocation.getY(), spawnLocation.getZ());
					}

					// Remove if duplicate mob
					LivingEntity le = SpawnManager.getSpawnPointMobs().get(getData().getSpawn());
					if (le == null || !le.equals(getBukkitLivingEntity())) {
						getSelf().die();
					}
				}

			}.runTaskLater(InfernalRealms.getPlugin(), 5L);
		}

		// Clear existing AI
		try {
			Field bField = PathfinderGoalSelector.class.getDeclaredField("b");
			bField.setAccessible(true);
			Field cField = PathfinderGoalSelector.class.getDeclaredField("c");
			cField.setAccessible(true);
			bField.set(goalSelector, Sets.newLinkedHashSet());
			bField.set(targetSelector, Sets.newLinkedHashSet());
			cField.set(goalSelector, Sets.newLinkedHashSet());
			cField.set(targetSelector, Sets.newLinkedHashSet());
		} catch (Exception e) {
			e.printStackTrace();
		}

		// Settings
		getSelf().canPickUpLoot = false;
		getSelf().setCustomNameVisible(false);
		//		getSelf().persistent = true;
		if (getData().isInvisible()) {
			applyInvisibility();
		}

		// Equipment
		getBukkitLivingEntity().getEquipment().setItemInHandDropChance(0);
		getBukkitLivingEntity().getEquipment().setHelmetDropChance(0);
		getBukkitLivingEntity().getEquipment().setChestplateDropChance(0);
		getBukkitLivingEntity().getEquipment().setLeggingsDropChance(0);
		getBukkitLivingEntity().getEquipment().setBootsDropChance(0);
		if (firstRun) {
			getBukkitLivingEntity().getEquipment().setItemInHand(getData().getWeapon());
			getBukkitLivingEntity().getEquipment().setHelmet(getData().getHelmet());
			getBukkitLivingEntity().getEquipment().setChestplate(getData().getChestplate());
			getBukkitLivingEntity().getEquipment().setLeggings(getData().getLeggings());
			getBukkitLivingEntity().getEquipment().setBoots(getData().getBoots());
		}

		if (getSelf() instanceof EntityMonster) {
			EntityMonster self = (EntityMonster) getSelf();
			// Aggro
			//		if (getData().isAgressive()) { TODO
			if (self.getEquipment(EnumItemSlot.MAINHAND) != null && self.getEquipment(EnumItemSlot.MAINHAND).getItem() == Items.BOW) {
				goalSelector.a(4, new PathfinderGoalArrowAttack(this, 1.0D, 20, 60, 15.0F));
			} else {
				goalSelector.a(4, new PathfinderGoalMeleeAttack(self, 1.2D, false));
			}
			//		}

			targetSelector.a(2, new PathfinderGoalCustomMobTargetting<EntityHuman>(self, EntityHuman.class, true, 10));
			goalSelector.a(0, new PathfinderGoalFloat(self));
			goalSelector.a(5, new PathfinderGoalMoveTowardsRestriction(self, 1.0D));
			goalSelector.a(8, new PathfinderGoalLookAtPlayer(self, EntityHuman.class, 8.0F));
			goalSelector.a(9, new PathfinderGoalRandomLookaround(self));
		}

		// Testing stuff

		// End testing stuff

		setMaxHealth(getData().getMaxHealth());
		getSelf().setHealth((float) getData().getMaxHealth());
		setDamage(getData().getDamage());
		setKnockBackResist(getData().getKnockBackResist());
		setSpeed(getData().getSpeed());
		setFollowRangeMult(getData().getFollowRangeMult());
	}

	public default void onCreation(PathfinderGoalSelector goalSelector, PathfinderGoalSelector targetSelector) {
		onCreation(goalSelector, targetSelector, false);
	}

	public default void setSpeed(double value) {
		getSelf().getAttributeInstance(GenericAttributes.MOVEMENT_SPEED).setValue(value);
	}

	public default void setMaxHealth(double value) {
		getSelf().getAttributeInstance(GenericAttributes.maxHealth).setValue(value);
	}

	public default void setKnockBackResist(double value) {
		getSelf().getAttributeInstance(GenericAttributes.c).setValue(value);
	}

	public default void setDamage(double value) {
		getSelf().getAttributeInstance(GenericAttributes.ATTACK_DAMAGE).setValue(value);
	}

	public default void setFollowRangeMult(double value) {
		getSelf().getAttributeInstance(GenericAttributes.FOLLOW_RANGE).setValue(value);
	}

	public default float handleDamage(DamageSource damagesource, float damage) {
		LivingEntity target = (LivingEntity) getBukkitLivingEntity();
		if (!getSelf().isAlive()) {
			return -1;
		}
		if (target.getNoDamageTicks() > target.getMaximumNoDamageTicks() / 2) {
			return -1;
		}
		Entity damager;
		try {
			damager = damagesource.getEntity().getBukkitEntity();
		} catch (Exception e) {
			return damage;
		}
		//		EntityLiving nmsDamager = null;
		if (damager instanceof Player) { // When player attacks
			Player player = (Player) damager;
			temporarilyIncreaseFollowRange(2, 200L);
			if (player.getLocation().distanceSquared(getBukkitLivingEntity().getLocation()) >= Math.pow(getData().getFollowRangeMult() * 2,
					2)) {
				// Attacking too far away
				return -1;
			}
			PlayerData playerData = PlayerData.getData(player);

			float accuracy = getAccuracyAgainstAtLevel(playerData.getLevel());
			if (accuracy < RANDOM.nextFloat()) {
				createFloatUpTag(ChatColor.RED + "" + ChatColor.ITALIC + "Missed");
				return -1;
			}
			if (damagesource instanceof EntityDamageSourceIndirect) { // Projectile was shot for damage
				EntityDamageSourceIndirect indirectds = (EntityDamageSourceIndirect) damagesource;
				Entity projectile = indirectds.i().getBukkitEntity();
				if (projectile.hasMetadata("SkillEffect")) {
					target.setNoDamageTicks(0);
					// Always will have SkillEffect & SkillDamage together
					MetadataValue skillDamage = projectile.getMetadata("SkillDamage").get(0);
					target.setMetadata("SkillDamage", skillDamage);
					((ArrowSkill) projectile.getMetadata("SkillEffect").get(0).value()).getEffect(target);
					projectile.removeMetadata("SkillDamage", InfernalRealms.getPlugin());
					projectile.removeMetadata("SkillEffect", InfernalRealms.getPlugin());
				}
				if (playerData.getPlayerClass().equals("Archer")) {
					new Concentration(player).getEffect(target);
				}
			} else {
				if (playerData.getPlayerSuperClass().equalsIgnoreCase("Archer") && damage != -5) { // Archer punched
					target.setVelocity(player.getEyeLocation().getDirection().multiply(1.2).setY(0.3));
					return -1;
				} else if (playerData.getPlayerSuperClass().equalsIgnoreCase("Magician") && damage != -5) {
					return -1;
				}
			}
			if (playerData.getPlayerClass().equals("Warrior")) {
				new ConcussiveBlows(player).getEffect(target);
			}
			final ItemStack weapon = player.getItemInHand();
			DamageValue dv = playerData.calculateDamage(target);
			damage = (float) dv.getDamage();
			if (damage == -1) {
				return -1;
			}
			getData().getDamagers().add(player.getName());

			// Post Damage Effects

			// Floating damage indicator
			if (!dv.isCritical()) {
				createFloatUpTag(ChatColor.RED + "" + (int) damage);
			} else {
				createFloatUpTag(ChatColor.RED + "" + ChatColor.BOLD + (int) damage + "!");
			}
			player.getWorld().spigot().playEffect(getBukkitLivingEntity().getLocation().add(0, getHeight() / 2.0, 0), Effect.TILE_BREAK,
					Material.REDSTONE_BLOCK.getId(), 0, 0.4F, 0.4F, 0.4F, 0.1F, 32, 16);

			new BukkitRunnable() {

				public void run() {
					weapon.setDurability((short) 0);
				}

			}.runTaskLater(InfernalRealms.getPlugin(), 1L);
		} else if (damager instanceof Arrow) { // TODO Make sure this can be removed 
			Arrow arrow = (Arrow) damager;
			if (arrow.getShooter() instanceof Player) { // When player shoots arrow
				Player player = (Player) arrow.getShooter();
				PlayerData playerData = PlayerData.getData(player);
				if (playerData.getPlayerClass().equals("Archer")) {
					new Concentration(player).getEffect(player);
				}
				DamageValue dv = playerData.calculateDamage(target);
				damage = (float) dv.getDamage();
				getData().getDamagers().add(player.getName());

				// Floating damage indicator
				createFloatUpTag(ChatColor.RED + "" + (int) damage);
			}

			if (arrow.hasMetadata("Effect")) {
				if (arrow.getMetadata("Effect").get(0).value() instanceof ArrowSkill)
					((ArrowSkill) arrow.getMetadata("Effect").get(0).value()).getEffect(target);
			}
		}

		try {
			ReflectionUtil.setIgnoreArmor.invoke(damagesource);
		} catch (Exception e) {
			System.out.println("Unable to ignore armor!");
			e.printStackTrace();
		}
		if (damage > 0F) {
			getData().displayHealthBar(getSelf());

			// Trigger DamagedCastController spells
			for (MobSpell spell : getData().getSpells()) {
				if (!(spell.getController() instanceof DamagedCastController)) {
					continue;
				}
				spell.getController().trigger();
			}
		}
		return damage;
	}

	public default void temporarilyIncreaseFollowRange(double multiplier, long duration) {
		if (getSelf().getBukkitEntity().hasMetadata("IncreasedFollow")) {
			((BukkitTask) getSelf().getBukkitEntity().getMetadata("IncreasedFollow").get(0).value()).cancel();
			getSelf().getBukkitEntity().removeMetadata("IncreasedFollow", InfernalRealms.getPlugin());
		} else {
			setFollowRangeMult(getSelf().getAttributeInstance(GenericAttributes.FOLLOW_RANGE).getValue() * multiplier);
		}
		BukkitTask runnable = new BukkitRunnable() {

			@Override
			public void run() {
				if (!getSelf().valid) {
					getSelf().getBukkitEntity().removeMetadata("IncreasedFollow", InfernalRealms.getPlugin());
					return;
				}
				setFollowRangeMult(getData().getFollowRangeMult());
				getSelf().getBukkitEntity().removeMetadata("IncreasedFollow", InfernalRealms.getPlugin());
			}

		}.runTaskLater(InfernalRealms.getPlugin(), duration);
		getSelf().getBukkitEntity().setMetadata("IncreasedFollow", new FixedMetadataValue(InfernalRealms.getPlugin(), runnable));

	}

	public default void createFloatUpTag(String text) {
		Hologram holo = HologramsAPI.createHologram(InfernalRealms.getPlugin(), getBukkitLivingEntity().getLocation().add(0, 1.5, 0));
		holo.appendTextLine(text);
		new BukkitRunnable() {
			int count = 25;

			@Override
			public void run() {
				holo.teleport(holo.getLocation().add(0, 0.1, 0));
				count--;
				if (count <= 0) {
					holo.delete();
					cancel();
				}
			}
		}.runTaskTimer(InfernalRealms.getPlugin(), 0L, 1L);
	}

	public default Location getNameTagLocation() {
		double height = getHeight() - 1.6;
		return new org.bukkit.Location(Bukkit.getWorld(getSelf().world.getWorld().getName()), getSelf().locX, getSelf().locY + height,
				getSelf().locZ);
	}

	public default double getHeight() {
		return getSelf().getBoundingBox().e - getSelf().getBoundingBox().b;
	}

	public default void onMove() {
		// WARNING: CALLED OFTEN
		updateNameTagAtLocation();
	}

	public default void updateNameTagAtLocation() {
		if (getData() == null) {
			return;
		}
		if (getData().getNameTag() == null) {
			getData().setNameTag(HologramHandler.createHologramOnEntity(getBukkitLivingEntity(), getData().getFullName()));
		} else {
			getData().getNameTag().teleport(getNameTagLocation());
		}
		if (getData().getHealthBar() != null) {
			getData().getHealthBar().teleport(getNameTagLocation().add(0, 0.3, 0));
		}
	}

	public default void onDeath() {
		try {
			if (getData() == null) {
				return;
			}
			if (getData().getNameTag() != null) {
				getData().getNameTag().delete();
			}
			if (getData().getHealthBar() != null) {
				getData().getNameTag().delete();
			}

			// Trigger death spells
			for (MobSpell spell : getData().getSpells()) {
				if (!(spell.getController() instanceof DeathCastController)) {
					continue;
				}
				spell.getController().trigger();
			}

			if (getSelf().killer instanceof EntityHuman) {
				HumanEntity he = ((EntityHuman) getSelf().killer).getBukkitEntity();
				if (he instanceof Player) {
					Player player = (Player) he;
					PlayerData playerData = PlayerData.getData(player);

					// Experience & Money
					double exp = getData().getExp();
					long money = (long) (getData().getMoney() * InfernalRealms.DROP_MULTIPLIER);
					if (Party.getParty(player) != null) {
						// Party Distribution
						Party party = Party.getParty(player);
						List<Player> partyDamagers = new ArrayList<>();
						List<Player> expPartyDamagers = new ArrayList<>();
						partyDamagers.add(player);
						if (canEarnExp(playerData)) {
							expPartyDamagers.add(player);
						}
						for (Player member : party.getNearbyPartyMembers(getBukkitLivingEntity().getLocation(), 60D)) {
							if (member.equals(player)) {
								continue;
							}
							if (getData().isDamager(member)) {
								partyDamagers.add(member);
							}
							if (canEarnExp(PlayerData.getData(member))) {
								expPartyDamagers.add(member);
							}
						}
						exp /= (double) expPartyDamagers.size();
						if (partyDamagers.size() > 1) {
							exp *= 1.1D;
						}
						exp *= InfernalRealms.EXP_MULTIPLIER;
						money /= partyDamagers.size();
						for (Player damager : partyDamagers) {
							String deathMessage = "";
							PlayerData damagerData = PlayerData.getData(damager);
							if (expPartyDamagers.contains(damager)) {
								double personalExp = exp * damagerData.getExpMultiplier();
								damagerData.modifyExp((int) personalExp);
								damager.sendMessage(ChatColor.GRAY + "" + ChatColor.BOLD + "+" + ChatColor.GRAY + (int) personalExp
										+ ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + " EXP");
								deathMessage += ChatColor.GRAY + "" + ChatColor.BOLD + "+" + ChatColor.GRAY + ((int) personalExp)
										+ ChatColor.DARK_PURPLE + " EXP ";
							}
							long damagerMoney = (long) (money * damagerData.getDropMultiplier());
							damagerData.modifyMoney(damagerMoney);
							deathMessage += ChatColor.GRAY + "" + ChatColor.BOLD + "+" + ChatColor.GRAY
									+ GeneralUtil.getMoneyAsShortString(damagerMoney, ChatColor.GRAY);
							com.gmail.filoghost.holographicdisplays.api.Hologram holo = HologramsAPI
									.createHologram(InfernalRealms.getPlugin(), getBukkitLivingEntity().getLocation().add(0, 1, 0));
							holo.getVisibilityManager().setVisibleByDefault(false);
							holo.getVisibilityManager().showTo(damager);
							holo.appendTextLine(deathMessage);
							new BukkitRunnable() {

								@Override
								public void run() {
									if (!holo.isDeleted()) {
										holo.delete();
									}
								}
							}.runTaskLater(InfernalRealms.getPlugin(), 60L);
							//Quest Objectives
							damagerData.processObjectiveKill(getData());
						}
					} else {
						// Solo Distribution
						String deathMessage = "";
						if (canEarnExp(playerData)) {
							exp *= playerData.getExpMultiplier();
							playerData.modifyExp((int) exp);
							player.sendMessage(ChatColor.GRAY + "" + ChatColor.BOLD + "+" + ChatColor.GRAY + (int) exp
									+ ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + " EXP");
							deathMessage += ChatColor.GRAY + "" + ChatColor.BOLD + "+" + ChatColor.GRAY + getData().getExp()
									+ ChatColor.DARK_PURPLE + " EXP ";
						}
						playerData.modifyMoney(money);
						deathMessage += ChatColor.GRAY + "" + ChatColor.BOLD + "+" + ChatColor.GRAY
								+ GeneralUtil.getMoneyAsShortString(money, ChatColor.GRAY);
						com.gmail.filoghost.holographicdisplays.api.Hologram holo = HologramsAPI.createHologram(InfernalRealms.getPlugin(),
								getBukkitLivingEntity().getLocation().add(0, 1, 0));
						holo.getVisibilityManager().setVisibleByDefault(false);
						holo.getVisibilityManager().showTo(player);
						holo.appendTextLine(deathMessage);
						new BukkitRunnable() {

							@Override
							public void run() {
								if (!holo.isDeleted()) {
									holo.delete();
								}
							}
						}.runTaskLater(InfernalRealms.getPlugin(), 60L);
						//Quest Objectives
						playerData.processObjectiveKill(getData());
					}

					// Drops Handled in GeneralListener class

					MobManager.getSpawnedMobs().remove(getBukkitLivingEntity());
				}
			}
			if (SpawnManager.getSpawnPointMobs().containsValue(getBukkitLivingEntity())) {

				String spawn = getData().getSpawn();
				long duration;
				if (spawn.contains("DUNGEON")) {
					String[] spawnSplit = spawn.split(":");
					duration = YAMLFile.SPAWNS.getConfig().getLong(spawnSplit[1] + ".RespawnRate") * 20L;
				} else {
					duration = YAMLFile.SPAWNS.getConfig().getLong(spawn + ".RespawnRate") * 20L;
				}
				SpawnManager.respawnLater(spawn, duration);
				SpawnManager.getSpawnPointMobs().remove(spawn);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public default float getAccuracyAgainstAtLevel(int level) {
		int mobLevel = getData().getLevel();
		if (level >= mobLevel - 3) { // at most 3 level difference
			return 1F;
		} else if (level >= mobLevel - 10) { // at most 10 level difference
			return 3.5F / (mobLevel - level);
		} else { // More than 10 levels of difference
			return 0F;
		}
	}

	public default boolean canEarnExp(PlayerData playerData) {
		return Math.abs(playerData.getLevel() - getData().getLevel()) <= 10;
	}

	public default void applyInvisibility() {
		getBukkitLivingEntity().addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, 0, false, false));
	}

	public default LivingEntity getBukkitLivingEntity() {
		return (LivingEntity) getSelf().getBukkitEntity();
	}

	public default void fireArrow(EntityLiving entityliving) {
		EntityTippedArrow entitytippedarrow = new EntityTippedArrow(entityliving.world, entityliving);
		double d0 = entityliving.locX - entityliving.locX;
		double d1 = entityliving.getBoundingBox().b + (double) (entityliving.length / 3.0F) - entitytippedarrow.locY;
		double d2 = entityliving.locZ - entityliving.locZ;
		double d3 = (double) MathHelper.sqrt(d0 * d0 + d2 * d2);

		entitytippedarrow.shoot(d0, d1 + d3 * 0.20000000298023224D, d2, 1.6F, (float) (14 - entityliving.world.getDifficulty().a() * 4));
		int i = EnchantmentManager.a(Enchantments.ARROW_DAMAGE, (EntityLiving) this);
		int j = EnchantmentManager.a(Enchantments.ARROW_KNOCKBACK, (EntityLiving) this);

		entitytippedarrow
				.c((double) 2.0F + RANDOM.nextGaussian() * 0.25D + (double) ((float) entityliving.world.getDifficulty().a() * 0.11F));
		if (i > 0) {
			entitytippedarrow.c(entitytippedarrow.k() + (double) i * 0.5D + 0.5D);
		}

		if (j > 0) {
			entitytippedarrow.setKnockbackStrength(j);
		}

		entityliving.a(SoundEffects.fo, 1.0F, 1.0F / (RANDOM.nextFloat() * 0.4F + 0.8F));
	}

	public default String speakSound() {
		String speak = getData().getSpeakSound();
		if (speak == null) {
			return null;
		}
		getBukkitLivingEntity().getWorld().playSound(getBukkitLivingEntity().getLocation(), speak, 1F,
				(RANDOM.nextFloat() - RANDOM.nextFloat()) * 0.2F + 1.0F);
		return "";
	}

	public default boolean walkSound() {
		String walkSound = getData().getWalkSound();
		if (walkSound == null) {
			return false;
		}
		getBukkitLivingEntity().getWorld().playSound(getBukkitLivingEntity().getLocation(), walkSound, 0.15F, 1F);
		return true;
	}

	public default String deathSound() {
		String death = getData().getDeathSound();
		if (death == null) {
			return null;
		}
		getBukkitLivingEntity().getWorld().playSound(getBukkitLivingEntity().getLocation(), death, 1F,
				(RANDOM.nextFloat() - RANDOM.nextFloat()) * 0.2F + 1.0F);
		return "";
	}

	public default String hurtSound() {
		String hurt = getData().getHurtSound();
		if (hurt == null) {
			return null;
		}
		getBukkitLivingEntity().getWorld().playSound(getBukkitLivingEntity().getLocation(), hurt, 1F,
				(RANDOM.nextFloat() - RANDOM.nextFloat()) * 0.2F + 1.0F);
		return "";
	}

	/**
	 * Called from the K() method, called every tick (every 1/20 second)
	 */
	public default void update() {
		if (getSelf().ticksLived % 10 != 0) {
			// Only update every half-second
			return;
		}
		// Check spell status
		for (MobSpell spell : getData().getSpells()) {
			if (!spell.getController().check()) {
				// Not ready for casting
				continue;
			}
			spell.cast();
		}
	}

	public CustomMobData getData();

	public EntityInsentient getSelf();

	// Misc. (Already made in super-classes)

	public void setPosition(double x, double y, double z);

}

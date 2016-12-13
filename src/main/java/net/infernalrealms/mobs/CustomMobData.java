package net.infernalrealms.mobs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import net.infernalrealms.general.InfernalRealms;
import net.infernalrealms.mobs.spells.MobSpell;
import net.minecraft.server.v1_9_R1.EntityInsentient;
import net.minecraft.server.v1_9_R1.GenericAttributes;
import net.minecraft.server.v1_9_R1.NBTTagCompound;
import net.minecraft.server.v1_9_R1.NBTTagList;

public class CustomMobData {

	public static final ArrayList<EntityInsentient> recentlyHurt = new ArrayList<>();

	private MobType mobType;
	private int level;
	private double damage;
	private double speed;
	private double followRangeMult;
	private double knockBackResist;
	private String name;
	private String fullName;
	private String identifierName;
	private int exp;
	private long money;
	private InfernalHologramLine nameTag;
	private InfernalHologramLine healthBar;
	private double maxHealth;
	private ItemStack[] equipmentContents = new ItemStack[5];
	private boolean invisible;
	private boolean aggressive;
	private String speakSound;
	private String hurtSound;
	private String deathSound;
	private String walkSound;
	private String spawn;
	private final Map<String, Double> drops = new HashMap<String, Double>();
	private final List<String> damagers = new ArrayList<String>();
	private final List<MobSpell> spells = new ArrayList<>();

	public CustomMobData() {}

	public int getLevel() {
		return this.level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFullName() {
		return this.fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getIdentifierName() {
		return this.identifierName;
	}

	public void setIdentifierName(String identifierName) {
		this.identifierName = identifierName;
	}

	public MobType getMobType() {
		return this.mobType;
	}

	public void setMobType(MobType mobType) {
		this.mobType = mobType;
	}

	public double getDamage() {
		return this.damage;
	}

	public void setDamage(double damage) {
		this.damage = damage;
	}

	public double getSpeed() {
		return this.speed;
	}

	public void setSpeed(double speed) {
		this.speed = speed;
	}

	public double getFollowRangeMult() {
		return this.followRangeMult;
	}

	public void setFollowRangeMult(double range) {
		this.followRangeMult = range;
	}

	public double getKnockBackResist() {
		return this.knockBackResist;
	}

	public void setKnockBackResist(double knockBack) {
		this.knockBackResist = knockBack;
	}

	public int getExp() {
		return this.exp;
	}

	public void setExp(int exp) {
		this.exp = exp;
	}

	public void addDrop(String item, double dropChance) {
		this.drops.put(item, dropChance);
	}

	public Map<String, Double> getDrops() {
		return this.drops;
	}

	public long getMoney() {
		return money;
	}

	public void setMoney(long money) {
		this.money = money;
	}

	public InfernalHologramLine getNameTag() {
		return nameTag;
	}

	public void setNameTag(InfernalHologramLine nameTag) {
		this.nameTag = nameTag;
	}

	public InfernalHologramLine getHealthBar() {
		return healthBar;
	}

	public void setHealthBar(InfernalHologramLine healthBar) {
		this.healthBar = healthBar;
	}

	public void displayHealthBar(final EntityInsentient ei) {
		new BukkitRunnable() {

			@Override
			public void run() {
				if (ei.valid) {
					double healthPercent = ei.getHealth() / ei.getAttributeInstance(GenericAttributes.maxHealth).getValue();
					int redBars = (int) Math.floor(healthPercent * 30);
					String[] barsSplit = { "|", "|", "|", "|", "|", "|", "|", "|", "|", "|", "|", "|", "|", "|", "|", "|", "|", "|", "|",
							"|", "|", "|", "|", "|", "|", "|", "|", "|", "|", "|" }; // 30 bars
					String bars = (getMobType() == MobType.NORMAL ? ChatColor.GREEN : ChatColor.GOLD) + "";
					for (int i = 0; i < barsSplit.length; i++) {
						if (i == redBars) {
							bars = bars + ChatColor.RED + barsSplit[i];
						} else {
							bars = bars + barsSplit[i];
						}
					}

					if (getHealthBar() != null) {
						// Update name tag
						getHealthBar().setCustomName(bars);
					} else {
						// Create new name tag
						setHealthBar(HologramHandler.createHologramOnEntity(getNameTag().getBukkitEntity(), bars, false));
					}
					recentlyHurt.add(ei);
					removeHealthBar(ei);
				}
			}

		}.runTaskLater(InfernalRealms.getPlugin(), 1L);
	}

	public void removeHealthBar(final EntityInsentient ei) {
		new BukkitRunnable() {
			public void run() {
				recentlyHurt.remove(ei);
				if (ei.valid && !recentlyHurt.contains(ei)) {
					getHealthBar().delete();
					setHealthBar(null);
				}
			}
		}.runTaskLater(InfernalRealms.getPlugin(), 160L);
	}

	public double getMaxHealth() {
		return maxHealth;
	}

	public void setMaxHealth(double maxHealth) {
		this.maxHealth = maxHealth;
	}

	public ItemStack[] getEquipmentContents() {
		return equipmentContents;
	}

	public void setEquipmentContents(ItemStack[] equipmentContents) {
		this.equipmentContents = equipmentContents;
	}

	public ItemStack getWeapon() {
		return equipmentContents[0];
	}

	public void setWeapon(ItemStack item) {
		equipmentContents[0] = item;
	}

	public ItemStack getHelmet() {
		return equipmentContents[1];
	}

	public void setHelmet(ItemStack item) {
		equipmentContents[1] = item;
	}

	public ItemStack getChestplate() {
		return equipmentContents[2];
	}

	public void setChestplate(ItemStack item) {
		equipmentContents[2] = item;
	}

	public ItemStack getLeggings() {
		return equipmentContents[3];
	}

	public void setLeggings(ItemStack item) {
		equipmentContents[3] = item;
	}

	public ItemStack getBoots() {
		return equipmentContents[4];
	}

	public void setBoots(ItemStack item) {
		equipmentContents[4] = item;
	}

	public boolean isInvisible() {
		return invisible;
	}

	public void setInvisible(boolean invisible) {
		this.invisible = invisible;
	}

	public boolean isAgressive() {
		return aggressive;
	}

	public void setAgressive(boolean agressive) {
		this.aggressive = agressive;
	}

	public String getSpeakSound() {
		return speakSound;
	}

	public void setSpeakSound(String speakSound) {
		this.speakSound = speakSound == null || speakSound.equalsIgnoreCase("default") ? null : speakSound;
	}

	public String getHurtSound() {
		return hurtSound;
	}

	public void setHurtSound(String hurtSound) {
		this.hurtSound = hurtSound == null || hurtSound.equalsIgnoreCase("default") ? null : hurtSound;
	}

	public String getDeathSound() {
		return deathSound;
	}

	public void setDeathSound(String deathSound) {
		this.deathSound = deathSound == null || deathSound.equalsIgnoreCase("default") ? null : deathSound;
	}

	public String getWalkSound() {
		return walkSound;
	}

	public void setWalkSound(String walkSound) {
		this.walkSound = walkSound == null || walkSound.equalsIgnoreCase("default") ? null : walkSound;
	}

	public String getSpawn() {
		return spawn;
	}

	public void setSpawn(String spawn) {
		this.spawn = spawn;
	}

	public List<String> getDamagers() {
		return this.damagers;
	}

	public boolean isDamager(Player player) {
		return getDamagers().contains(player.getName());
	}

	public List<MobSpell> getSpells() {
		return spells;
	}

	public void addSpell(MobSpell spell) {
		getSpells().add(spell);
	}

	public NBTTagCompound toNBTTag() {
		NBTTagCompound nbttagcompound = new NBTTagCompound();
		nbttagcompound.setString("MobType", mobType.toString());
		nbttagcompound.setInt("Level", level);
		nbttagcompound.setDouble("Damage", damage);
		nbttagcompound.setDouble("Speed", speed);
		nbttagcompound.setDouble("FollowRangeMult", followRangeMult);
		nbttagcompound.setDouble("KnockBackResist", knockBackResist);
		nbttagcompound.setString("Name", name);
		nbttagcompound.setString("FullName", fullName);
		nbttagcompound.setString("IdentifierName", identifierName);
		nbttagcompound.setInt("Exp", exp);
		nbttagcompound.setLong("Money", money);
		nbttagcompound.setDouble("MaxHealth", maxHealth);
		nbttagcompound.setBoolean("Invisible", invisible);
		nbttagcompound.setBoolean("Aggressive", aggressive);
		if (speakSound != null && !speakSound.equals("")) {
			nbttagcompound.setString("SpeakSound", speakSound);
		}
		if (hurtSound != null && !hurtSound.equals("")) {
			nbttagcompound.setString("HurtSound", hurtSound);
		}
		if (deathSound != null && !deathSound.equals("")) {
			nbttagcompound.setString("DeathSound", deathSound);
		}
		if (walkSound != null && !walkSound.equals("")) {
			nbttagcompound.setString("WalkSound", walkSound);
		}
		if (spawn != null) {
			nbttagcompound.setString("Spawn", spawn);
		}
		NBTTagList nbttaglist = new NBTTagList();
		for (String drop : drops.keySet()) {
			NBTTagCompound listtag = new NBTTagCompound();
			listtag.setString("Drop", drop);
			listtag.setDouble("DropChance", drops.get(drop));
			nbttaglist.add(listtag);
		}
		nbttagcompound.set("Drops", nbttaglist);
		return nbttagcompound;
	}
}

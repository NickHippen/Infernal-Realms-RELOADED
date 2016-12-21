package net.infernalrealms.player;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Horse.Color;
import org.bukkit.entity.Horse.Style;
import org.bukkit.entity.Horse.Variant;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.scheduler.BukkitRunnable;

import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;
import com.gmail.filoghost.holographicdisplays.api.line.TextLine;

import net.citizensnpcs.api.npc.NPC;
import net.infernalrealms.blacksmithing.RecipeItemType;
import net.infernalrealms.dungeons.Dungeon;
import net.infernalrealms.dungeons2.DungeonInstance;
import net.infernalrealms.general.InfernalRealms;
import net.infernalrealms.general.YAMLFile;
import net.infernalrealms.gui.ActionBarHandler;
import net.infernalrealms.gui.QuestTabList;
import net.infernalrealms.gui.TabList;
import net.infernalrealms.gui.TabListType;
import net.infernalrealms.homesteads.HomesteadUtils;
import net.infernalrealms.inventory.InventoryManager;
import net.infernalrealms.items.ItemReader;
import net.infernalrealms.mining.Ore;
import net.infernalrealms.mining.PickaxeFactory;
import net.infernalrealms.mobs.CustomMobData;
import net.infernalrealms.mount.MountManager;
import net.infernalrealms.mount.Size;
import net.infernalrealms.npc.NPCManager;
import net.infernalrealms.party.Party;
import net.infernalrealms.quests.HardcodedQuest;
import net.infernalrealms.quests.Quest;
import net.infernalrealms.quests.objectives.Deliverable;
import net.infernalrealms.quests.objectives.Objective;
import net.infernalrealms.quests.objectives.ObjectiveBreakBlock;
import net.infernalrealms.quests.objectives.ObjectiveChooseClass;
import net.infernalrealms.quests.objectives.ObjectiveCompleteQuest;
import net.infernalrealms.quests.objectives.ObjectiveInteractWithBlock;
import net.infernalrealms.quests.objectives.ObjectiveKill;
import net.infernalrealms.quests.objectives.ObjectiveLearnSkill;
import net.infernalrealms.quests.objectives.ObjectiveLevelMountSpeed;
import net.infernalrealms.quests.objectives.ObjectiveOpenBank;
import net.infernalrealms.quests.objectives.ObjectiveSummonMount;
import net.infernalrealms.quests.objectives.ObjectiveTalk;
import net.infernalrealms.quests.objectives.ObjectiveTalkWithReward;
import net.infernalrealms.quests.objectives.PassiveCompletion;
import net.infernalrealms.skills.archer.SharpEye;
import net.infernalrealms.skills.general.Skill;
import net.infernalrealms.util.EffectsUtil;
import net.infernalrealms.util.GeneralUtil;
import net.infernalrealms.util.InfernalEffects;
import net.infernalrealms.util.InfernalStrings;

public class PlayerData {

	public static final Map<String, Integer> SELECTED_CHARACTER = new HashMap<String, Integer>();
	public static final Map<UUID, PlayerData> CONNECTED_PLAYERS = new HashMap<>();
	public static final int MAX_LEVEL = 50;
	private static final int MAX_MINING_LEVEL = 100;
	private static File playersFolder = null;

	private static final List<Material> WEAPON_TYPES = Arrays.asList(Material.WOOD_AXE, Material.STONE_AXE, Material.IRON_AXE,
			Material.GOLD_AXE, Material.DIAMOND_AXE, Material.WOOD_SWORD, Material.STONE_SWORD, Material.IRON_SWORD, Material.GOLD_SWORD,
			Material.DIAMOND_SWORD, Material.WOOD_PICKAXE, Material.STONE_PICKAXE, Material.IRON_PICKAXE, Material.GOLD_PICKAXE,
			Material.DIAMOND_PICKAXE, Material.BOW, Material.STICK, Material.BLAZE_ROD);

	private Player player;
	private boolean dirtyEquips = true;
	private final Map<Stat, Integer> stats = new HashMap<>(Stat.values().length);

	private BukkitRunnable buffer;
	private BukkitRunnable clickBuffer;
	private BukkitRunnable healthBuffer;
	private BukkitRunnable manaBuffer;

	private Block targetBlock;
	private Ore targetOre;
	private float blockDamage;

	private FileConfiguration config = null;
	private File configFile = null;
	private int currentCharacterSlot;
	private DungeonInstance dungeon;
	private AestheticType idleParticles, followParticles, damagedParticles, attackParticles;
	private ParticleTimer particleTimer;

	private PlayerData(Player player) {
		this.player = player;
		this.reloadConfig();
		if (!SELECTED_CHARACTER.containsKey(player.getName())) {
			SELECTED_CHARACTER.put(player.getName(), -1);
		}
		this.currentCharacterSlot = SELECTED_CHARACTER.get(player.getName());
	}

	public static PlayerData getData(Player player) {
		if (player == null) {
			return null;
		}
		PlayerData playerData = CONNECTED_PLAYERS.get(player.getUniqueId());
		if (playerData == null) {
			playerData = new PlayerData(player);
			CONNECTED_PLAYERS.put(player.getUniqueId(), playerData);
		}
		if (!SELECTED_CHARACTER.containsKey(player.getName())) {
			SELECTED_CHARACTER.put(player.getName(), -1);
		}
		if (playerData.particleTimer == null) {
			playerData.particleTimer = new ParticleTimer(playerData);
		}
		playerData.currentCharacterSlot = SELECTED_CHARACTER.get(player.getName());
		return playerData;
	}

	public void reloadConfig() {
		if (configFile == null) {
			configFile = new File(playersFolder, player.getUniqueId() + ".yml");
		}
		config = YamlConfiguration.loadConfiguration(configFile);
	}

	public FileConfiguration getConfig() {
		if (config == null) {
			reloadConfig();
		}
		return config;
	}

	public void saveConfig() {
		if (config == null || configFile == null) {
			return;
		}
		try {
			getConfig().save(configFile);
		} catch (IOException ex) {
			InfernalRealms.getPlugin().getLogger().log(Level.SEVERE, "Could not save config to " + configFile, ex);
		}
	}

	public void refreshConfig() {
		this.modifyAgility(0);
		this.modifyDexterity(0);
		this.modifyIntelligence(0);
		this.modifyLevel(0);
		this.modifyExp(0);
		this.modifyMana(0);
		this.modifySpirit(0);
		this.modifyStamina(0);
		this.modifyStrength(0);
		this.modifyGearscore(0);
		this.modifyMoney(0);
	}

	/**
	 * Gets the current character slot
	 * @return the current character slot, or -1 if no slot is selected (in lobby)
	 */
	public int getCurrentCharacterSlot() {
		return currentCharacterSlot;
	}

	public Location getLocation() {
		if (!config.contains(currentCharacterSlot + ".Location"))
			return InfernalRealms.TUTORIAL_START;
		return new Location(Bukkit.getWorld(config.getString(currentCharacterSlot + ".Location.World")),
				config.getDouble(currentCharacterSlot + ".Location.X"), config.getDouble(currentCharacterSlot + ".Location.Y"),
				config.getDouble(currentCharacterSlot + ".Location.Z"));
	}

	public void setLocation() {
		if (getCurrentCharacterSlot() == -1 || hasDungeon() || HomesteadUtils.isInHomestead(getPlayer())) {
			return;
		}
		config.set(currentCharacterSlot + ".Location.World", player.getLocation().getWorld().getName());
		config.set(currentCharacterSlot + ".Location.X", player.getLocation().getX());
		config.set(currentCharacterSlot + ".Location.Y", player.getLocation().getY());
		config.set(currentCharacterSlot + ".Location.Z", player.getLocation().getZ());
		saveConfig();
	}

	public String getPlayerClass() {
		if (!config.contains(currentCharacterSlot + ".Class"))
			return "Beginner";
		return config.getString(currentCharacterSlot + ".Class");
	}

	public String getPlayerSuperClass() {
		switch (getPlayerClass()) {
		case "Archer":
		case "Marksman":
		case "Rogue":
			return "Archer";
		case "Magician":
		case "Priest":
		case "Sorcerer":
			return "Magician";
		case "Warrior":
		case "Crusader":
		case "Paladin":
			return "Warrior";
		default:
			return "Beginner";
		}
	}

	public void setPlayerClass(String playerClass) {
		config.set(currentCharacterSlot + ".Class", playerClass);
		saveConfig();
	}

	public void setPlayerClass(PlayerClass playerClass) {
		config.set(currentCharacterSlot + ".Class", playerClass.getName());
		saveConfig();
	}

	public void setCharacterSlots(int value) {
		config.set("Character Slots", value);
		saveConfig();
	}

	public int getCharacterSlots() {
		if (!config.contains("Character Slots"))
			return 3;
		return config.getInt("Character Slots");
	}

	public void modifyCharacterSlots(int value) {
		if (!config.contains("Character Slots"))
			setCharacterSlots(3);
		setCharacterSlots(getCharacterSlots() + value);
	}

	public void setLevel(int value) {
		config.set(currentCharacterSlot + ".Level", value);
		saveConfig();
	}

	public int getLevel() {
		if (!config.contains(currentCharacterSlot + ".Level"))
			return 1;
		return config.getInt(currentCharacterSlot + ".Level");
	}

	public void modifyLevel(int value) {
		if (!config.contains(currentCharacterSlot + ".Level"))
			setLevel(1);
		setLevel(getLevel() + value);
	}

	public void setExp(int value) {
		if (getLevel() >= MAX_LEVEL) {
			return;
		}
		config.set(currentCharacterSlot + ".Exp", value);
		saveConfig();
		if (getExp() >= expToNextLevel()) { // Level Up
			levelUp();
		}
		try {
			ActionBarHandler.sendActionBar(getPlayer(), getPlayerInfoString());
		} catch (NullPointerException e) {}
	}

	public int getExp() {
		if (!config.contains(currentCharacterSlot + ".Exp"))
			return 0;
		return config.getInt(currentCharacterSlot + ".Exp");
	}

	public void modifyExp(int value) {
		if (!config.contains(currentCharacterSlot + ".Exp"))
			setExp(0);
		setExp(getExp() + value);
	}

	public void updateHealth() {
		player.setMaxHealth((double) getTotalHealth());
	}

	public void levelUp() {
		if (getLevel() < MAX_LEVEL) {
			int newExp = getExp() - expToNextLevel();
			int hpBefore = getTotalHealth();
			int mpBefore = getTotalMaxMana();
			modifyLevel(1);
			modifyAP(5);
			modifySP(1);
			int hpAfter = getTotalHealth();
			int mpAfter = getTotalMaxMana();
			player.setMaxHealth((double) hpAfter);
			player.setHealth((double) hpAfter);
			player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 10f, 10f);
			player.sendMessage(" ");
			player.sendMessage(ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + "LEVEL " + getLevel());
			player.sendMessage(ChatColor.AQUA + "" + ChatColor.BOLD + "AP" + ChatColor.AQUA + " + 5     " + ChatColor.BOLD + "SP"
					+ ChatColor.AQUA + " + 1");
			player.sendMessage(ChatColor.DARK_RED + "" + hpBefore + ChatColor.BOLD + " HP " + ChatColor.RESET + ChatColor.DARK_RED + "-"
					+ ChatColor.BOLD + "-> " + ChatColor.RESET + ChatColor.DARK_RED + hpAfter + ChatColor.BOLD + " HP");
			player.sendMessage(ChatColor.DARK_BLUE + "" + mpBefore + ChatColor.BOLD + " MP " + ChatColor.RESET + ChatColor.DARK_BLUE + "-"
					+ ChatColor.BOLD + "-> " + ChatColor.RESET + ChatColor.DARK_BLUE + mpAfter + ChatColor.BOLD + " MP");
			player.sendMessage(ChatColor.GRAY + "" + ChatColor.ITALIC + expToNextLevel() + " xp to lvl " + (getLevel() + 1));
			player.sendMessage(" ");
			Quest.getNewQuests(getPlayer());
			saveConfig();
			updateHealth();
			refreshQuestMarkers();

			new BukkitRunnable() {
				@Override
				public void run() {
					if (getLevel() != MAX_LEVEL) {
						setExp(newExp);
					} else {
						setExp(0);
					}
				}
			}.runTaskLater(InfernalRealms.getPlugin(), 1L);
		}
	}

	public int expToNextLevel() {
		int currentLevel = getLevel();
		//		int expToNextLevel = (int) (10 * Math.pow(currentLevel, 2) + 4 * Math.pow(currentLevel, 3));
		int expToNextLevel = (int) (Math.pow(((8 * currentLevel) + getDifficultyCategory()), 1.15) * 5 * currentLevel);
		return expToNextLevel;
	}

	public int getDifficultyCategory() {
		int currentLevel = getLevel();
		if (currentLevel <= 28) {
			return 0;
		}
		if (currentLevel == 29) {
			return 1;
		}
		if (currentLevel == 30) {
			return 3;
		}
		if (currentLevel == 31) {
			return 6;
		}
		return 8 * (currentLevel - 30);
	}

	public void setStrength(int value) {
		config.set(currentCharacterSlot + ".Stats.Strength", value);
		saveConfig();
	}

	public int getStrength() {
		if (!config.contains(currentCharacterSlot + ".Stats.Strength"))
			return 0;
		return config.getInt(currentCharacterSlot + ".Stats.Strength");
	}

	public int getTotalStrength() {
		return getStrength() + getEquipStat(Stat.STRENGTH);
	}

	public void modifyStrength(int value) {
		if (!config.contains(currentCharacterSlot + ".Stats.Strength"))
			setStrength(0);
		setStrength(getStrength() + value);
	}

	public void setDexterity(int value) {
		config.set(currentCharacterSlot + ".Stats.Dexterity", value);
		saveConfig();
	}

	public int getDexterity() {
		if (!config.contains(currentCharacterSlot + ".Stats.Dexterity"))
			return 0;
		return config.getInt(currentCharacterSlot + ".Stats.Dexterity");
	}

	public int getTotalDexterity() {
		return getDexterity() + getEquipStat(Stat.DEXTERITY);
	}

	public void modifyDexterity(int value) {
		if (!config.contains(currentCharacterSlot + ".Stats.Dexterity"))
			setDexterity(0);
		setDexterity(getDexterity() + value);
	}

	public void setIntelligence(int value) {
		config.set(currentCharacterSlot + ".Stats.Intelligence", value);
		saveConfig();
	}

	public int getIntelligence() {
		if (!config.contains(currentCharacterSlot + ".Stats.Intelligence"))
			return 0;
		return config.getInt(currentCharacterSlot + ".Stats.Intelligence");
	}

	public int getTotalIntelligence() {
		return getIntelligence() + getEquipStat(Stat.INTELLIGENCE);
	}

	public void modifyIntelligence(int value) {
		if (!config.contains(currentCharacterSlot + ".Stats.Intelligence"))
			setIntelligence(0);
		setIntelligence(getIntelligence() + value);
	}

	public void setStamina(int value) {
		config.set(currentCharacterSlot + ".Stats.Stamina", value);
		saveConfig();
	}

	public int getStamina() {
		if (!config.contains(currentCharacterSlot + ".Stats.Stamina"))
			return 0;
		return config.getInt(currentCharacterSlot + ".Stats.Stamina");
	}

	public int getTotalStamina() {
		return getStamina() + getEquipStat(Stat.STAMINA);
	}

	public void modifyStamina(int value) {
		if (!config.contains(currentCharacterSlot + ".Stats.Stamina"))
			setStamina(0);
		setStamina(getStamina() + value);
	}

	public void setSpirit(int value) {
		config.set(currentCharacterSlot + ".Stats.Spirit", value);
		saveConfig();
	}

	public int getSpirit() {
		if (!config.contains(currentCharacterSlot + ".Stats.Spirit"))
			return 0;
		return config.getInt(currentCharacterSlot + ".Stats.Spirit");
	}

	public int getTotalSpirit() {
		return getSpirit() + getEquipStat(Stat.SPIRIT);
	}

	public void modifySpirit(int value) {
		if (!config.contains(currentCharacterSlot + ".Stats.Spirit"))
			setSpirit(0);
		setSpirit(getSpirit() + value);
	}

	public void setAgility(int value) {
		config.set(currentCharacterSlot + ".Stats.Agility", value);
		saveConfig();
	}

	public int getAgility() {
		if (!config.contains(currentCharacterSlot + ".Stats.Agility"))
			return 0;
		return config.getInt(currentCharacterSlot + ".Stats.Agility");
	}

	public int getTotalAgility() {
		return getAgility() + getEquipStat(Stat.AGILITY);
	}

	public void modifyAgility(int value) {
		if (!config.contains(currentCharacterSlot + ".Stats.Agility"))
			setAgility(0);
		setAgility(getAgility() + value);
	}

	public double getTotalDodgeCrit() {
		double dodgeCrit = ((double) getTotalAgility()) / (20D * getLevel());
		if (dodgeCrit > 0.5) {
			dodgeCrit = 0.5;
		}
		return dodgeCrit;
	}

	public double getTotalDodgeCritBonusForNextLevel() {
		double currentDodgeCrit = getTotalDodgeCrit();
		if (currentDodgeCrit >= 0.5) {
			return 0;
		}
		double nextLevelDodgeCrit = ((double) getTotalAgility() + 1) / (20D * getLevel());
		return nextLevelDodgeCrit - currentDodgeCrit;
	}

	//	public void setHealth(int value) {
	//		config.set(currentCharacterSlot + ".Stats.Health", value);
	//		saveConfig();
	//	}

	//	public int getHealth() {
	//		if (!config.contains(currentCharacterSlot + ".Stats.Health"))
	//			return getHealthFromLevels();
	//		return config.getInt(currentCharacterSlot + ".Stats.Health") + getHealthFromLevels();
	//	}

	public int getTotalHealth() {
		return getHealthFromLevels() + getEquipStat(Stat.HEALTH);
	}

	public int getHealthFromLevels() {
		float multiplier = 0.75F;
		if (getPlayerSuperClass().equalsIgnoreCase("Warrior")) {
			multiplier = 1.6F;
		} else if (getPlayerSuperClass().equalsIgnoreCase("Archer")) {
			multiplier = 1F;
		}
		return (int) ((30 * getLevel() + 70) * multiplier);
	}

	public int getStoredHealth() {
		if (!config.contains(currentCharacterSlot + ".Stats.Health"))
			return 1;
		return config.getInt(currentCharacterSlot + ".Stats.Health");
	}

	public void setStoredHealth() {
		config.set(currentCharacterSlot + ".Stats.Health", (int) player.getHealth());
		saveConfig();
	}

	//	public void modifyHealth(int value) {
	//		if (!config.contains(currentCharacterSlot + ".Stats.Health"))
	//			setHealth(100);
	//		setHealth(getHealth() + value);
	//	}

	//	public void setMaxMana(int value) {
	//		config.set(currentCharacterSlot + ".Stats.MaxMana", value);
	//		saveConfig();
	//	}
	//
	//	public int getMaxMana() {
	//		if (!config.contains(currentCharacterSlot + ".Stats.MaxMana"))
	//			return 0;
	//		return config.getInt(currentCharacterSlot + ".Stats.MaxMana");
	//	}

	public int getTotalMaxMana() {
		return getMaxManaFromLevels() + getEquipStat(Stat.MANA) + getTotalSpirit();
	}

	public int getMaxManaFromLevels() {
		return 5 * getLevel() + 30;
	}

	//	public void modifyMaxMana(int value) {
	//		if (!config.contains(currentCharacterSlot + ".Stats.MaxMana"))
	//			setMaxMana(35);
	//		setMaxMana(getMaxMana() + value);
	//	}

	public void setMana(int value) {
		int maxMana = getTotalMaxMana();
		if (value > maxMana) {
			value = maxMana;
		}
		config.set(currentCharacterSlot + ".Stats.Mana", value);
		saveConfig();
		Stat.refreshManaBar(getPlayer());
	}

	public int getMana() {
		if (!config.contains(currentCharacterSlot + ".Stats.Mana"))
			return 0;
		return config.getInt(currentCharacterSlot + ".Stats.Mana");
	}

	public void modifyMana(int value) {
		if (!config.contains(currentCharacterSlot + ".Stats.Mana"))
			setMana(0);
		setMana(getMana() + value);
	}

	public int getTotalArmor() {
		return getEquipStat(Stat.ARMOR);
	}

	public void setAP(int value) {
		config.set(currentCharacterSlot + ".Stats.AP", value);
		saveConfig();
	}

	public int getAP() {
		if (!config.contains(currentCharacterSlot + ".Stats.AP"))
			return 0;
		return config.getInt(currentCharacterSlot + ".Stats.AP");
	}

	public void modifyAP(int value) {
		if (!config.contains(currentCharacterSlot + ".Stats.AP"))
			setAP(0);
		setAP(getAP() + value);
	}

	public void setSP(int value) {
		config.set(currentCharacterSlot + ".Stats.SP", value);
		saveConfig();
	}

	public int getSP() {
		if (!config.contains(currentCharacterSlot + ".Stats.SP"))
			return 1;
		return config.getInt(currentCharacterSlot + ".Stats.SP");
	}

	public void modifySP(int value) {
		if (!config.contains(currentCharacterSlot + ".Stats.SP"))
			setSP(1);
		setSP(getSP() + value);
	}

	public void setMoney(long value) {
		if (value < 0)
			value = 0;
		config.set(currentCharacterSlot + ".Stats.Money", value);
		saveConfig();
	}

	public long getMoney() {
		if (!config.contains(currentCharacterSlot + ".Stats.Money"))
			return 0;
		return config.getLong(currentCharacterSlot + ".Stats.Money");
	}

	public String getMoneyAsString() {
		return GeneralUtil.getMoneyAsString(getMoney());
	}

	public void modifyMoney(long value) {
		if (!config.contains(currentCharacterSlot + ".Stats.Money"))
			setMoney(0);
		setMoney(getMoney() + value);
	}

	public void setPremiumMoney(long value) {
		if (value < 0)
			value = 0;
		config.set("IP", value);
		saveConfig();
	}

	public long getPremiumMoney() {
		if (!config.contains("IP"))
			return 0;
		return config.getLong("IP");
	}

	public void modifyPremiumMoney(long value) {
		if (!config.contains("IP"))
			setPremiumMoney(0);
		setPremiumMoney(getPremiumMoney() + value);
	}

	public void logout() {
		if (hasDungeon()) {
			player.sendMessage(ChatColor.RED + "You can not log out while in a dungeon.");
			return;
		}
		setLocation();
		setStoredHealth();
		PlayerData.SELECTED_CHARACTER.remove(player.getName());
		player.teleport(InfernalRealms.LOBBY_SPAWN);
		if (player.hasMetadata("Party")) {
			Party party = Party.getParty(player);
			party.leave(player);
		}
		player.getInventory().clear();
		player.getInventory().setArmorContents(null);
		InventoryManager.setDefaultInventory(player);
		TabList.setTabListType(player, TabListType.BLANK);
		TabList.sendBlankTab(player);
		player.setHealth(player.getMaxHealth());
		QuestTabList.removeAllQuestsFromHelper(player);
		player.sendMessage(ChatColor.GREEN + "Logged out.");
		refreshQuestMarkers();
	}

	public void refreshQuestMarkers() {
		new BukkitRunnable() {
			@Override
			public void run() {
				for (Hologram holo : HologramsAPI.getHolograms(InfernalRealms.getPlugin())) {
					if (holo.size() == 1 && holo.getLine(0) instanceof TextLine && holo.getVisibilityManager().isVisibleTo(getPlayer())) {
						TextLine textLine = (TextLine) holo.getLine(0);
						if (textLine.getText().equals(InfernalStrings.QUEST_MARKER)
								|| textLine.getText().equals(InfernalStrings.QUEST_DELIVER_MARKER)) {
							holo.delete();
						}
					}
				}
				if (!getPlayer().isOnline()) {
					return;
				}
				for (Quest quest : getActiveQuests()) {
					for (Objective objective : quest.getCurrentObjectives()) {
						if (!(objective instanceof ObjectiveTalk)) {
							continue;
						}
						String npcName = ((ObjectiveTalk) objective).getNpcName();
						Location npcLocation = NPCManager.getNPCLocationByName(npcName);
						if (npcLocation == null) {
							continue;
						}
						Hologram holo = HologramsAPI.createHologram(InfernalRealms.getPlugin(), npcLocation.add(0, 2.6, 0));
						holo.getVisibilityManager().showTo(getPlayer());
						holo.getVisibilityManager().setVisibleByDefault(false);
						holo.appendTextLine(
								objective instanceof Deliverable ? InfernalStrings.QUEST_DELIVER_MARKER : InfernalStrings.QUEST_MARKER);
						break;
					}
				}
			}
		}.runTaskLater(InfernalRealms.getPlugin(), 20L);
	}

	public void setMountSpeed(int value) {
		config.set("Mount.Speed", value);
		saveConfig();
	}

	public void modifyMountSpeed(int value) {
		if (!config.contains("Mount.Speed"))
			setMountSpeed(1);
		setMountSpeed(getMountSpeed() + value);
	}

	public int getMountSpeed() {
		if (!config.contains("Mount.Speed"))
			return 1;
		return config.getInt("Mount.Speed");
	}

	public void setMountJump(int value) {
		config.set("Mount.Jump", value);
		saveConfig();
	}

	public void modifyMountJump(int value) {
		if (!config.contains("Mount.Jump"))
			setMountJump(1);
		setMountJump(getMountJump() + value);
	}

	public int getMountJump() {
		if (!config.contains("Mount.Jump"))
			return 1;
		return config.getInt("Mount.Jump");
	}

	public void setMountHunger(double value) {
		if (value < 0)
			value = 0;
		int maxHunger = MountManager.convertLevelToMaxHunger(getMountMaxHungerLevel());
		if (value > maxHunger)
			value = maxHunger;
		config.set("Mount.Hunger", value);
		saveConfig();
	}

	public double getMountHunger() {
		if (!config.contains("Mount.Hunger"))
			return 100;
		return config.getDouble("Mount.Hunger");
	}

	public int getMountHungerRounded() {
		return Math.round((float) getMountHunger());
	}

	public void modifyMountHunger(double value) {
		if (!config.contains("Mount.Hunger"))
			setMountHunger(100);
		setMountHunger(getMountHunger() + value);
	}

	public void setMountMaxHungerLevel(int value) {
		config.set("Mount.MaxHungerLevel", value);
		saveConfig();
	}

	public void modifyMountMaxHungerLevel(int value) {
		if (!config.contains("Mount.MaxHungerLevel"))
			setMountMaxHungerLevel(1);
		setMountMaxHungerLevel(getMountMaxHungerLevel() + value);
	}

	public int getMountMaxHungerLevel() {
		if (!config.contains("Mount.MaxHungerLevel"))
			return 1;
		return config.getInt("Mount.MaxHungerLevel");
	}

	public int getMountExpToNextLevel() {
		return (int) (10 * Math.pow(getMountLevel(), 1.2) + 50 + getMountLevel());
	}

	public void setMountExp(int value) {
		config.set("Mount.Exp", value);
		saveConfig();
		int expToNextLevel = getMountExpToNextLevel();
		if (getMountExp() >= expToNextLevel) {
			levelUpMount();
		}
	}

	public void levelUpMount() {
		int newExp = getMountExp() - getMountExpToNextLevel();
		modifyMountLevel(1);
		modifyMountStatPoints(1);
		player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 10f, 10f);
		player.sendMessage(" ");
		player.sendMessage(ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + "MOUNT LEVEL " + getMountLevel());
		player.sendMessage(ChatColor.AQUA + "" + ChatColor.BOLD + "Mount Stat Points" + ChatColor.AQUA + " + 1");
		player.sendMessage(ChatColor.GRAY + "" + ChatColor.ITALIC + getMountExpToNextLevel() + " xp to lvl " + (getMountLevel() + 1));
		player.sendMessage(" ");
		saveConfig();
		new BukkitRunnable() {
			@Override
			public void run() {
				setMountExp(newExp);
			}
		}.runTaskLater(InfernalRealms.getPlugin(), 1L);
	}

	public int getMountExp() {
		if (!config.contains("Mount.Exp"))
			return 0;
		return config.getInt("Mount.Exp");
	}

	public void modifyMountExp(int value) {
		if (!config.contains("Mount.Exp"))
			setMountExp(0);
		setMountExp(getMountExp() + value);
	}

	public void setMountLevel(int value) {
		config.set("Mount.Level", value);
		saveConfig();
	}

	public int getMountLevel() {
		if (!config.contains("Mount.Level"))
			return 1;
		return config.getInt("Mount.Level");
	}

	public void modifyMountLevel(int value) {
		if (!config.contains("Mount.Level"))
			setMountLevel(1);
		setMountLevel(getMountLevel() + value);
	}

	public void setMountStatPoints(int value) {
		config.set("Mount.StatPoints", value);
		saveConfig();
	}

	public int getMountStatPoints() {
		if (!config.contains("Mount.StatPoints"))
			return 0;
		return config.getInt("Mount.StatPoints");
	}

	public void modifyMountStatPoints(int value) {
		if (!config.contains("Mount.StatPoints"))
			setMountStatPoints(0);
		setMountStatPoints(getMountStatPoints() + value);
	}

	public void setMountColor(String value) {
		config.set("Mount.Color", value);
		saveConfig();
	}

	public String getMountColor() {
		if (!config.contains("Mount.Color"))
			return "Brown";
		return config.getString("Mount.Color");
	}

	public void setMountStyle(String value) {
		config.set("Mount.Style", value);
		saveConfig();
	}

	public String getMountStyle() {
		if (!config.contains("Mount.Style"))
			return "Brown";
		return config.getString("Mount.Style");
	}

	public void setMountVariant(String value) {
		config.set("Mount.Skin", value);
		saveConfig();
	}

	public String getMountVariant() {
		if (!config.contains("Mount.Skin"))
			return "Horse";
		return config.getString("Mount.Skin");
	}

	public void setMountSize(String value) {
		config.set("Mount.Size", value);
		saveConfig();
	}

	public String getMountSize() {
		if (!config.contains("Mount.Size"))
			return "Normal";
		return config.getString("Mount.Size");
	}

	public List<String> getUnlockedMountColors() {
		if (!config.contains("Mount.Unlocked Colors"))
			return new ArrayList<String>();
		return config.getStringList("Mount.Unlocked Colors");
	}

	public boolean hasUnlockedMountColor(Color color) {
		return hasUnlockedMountColor(color.toString());
	}

	public boolean hasUnlockedMountColor(String color) {
		if (color.equals(Color.BROWN.toString())) {
			return true;
		}
		return getUnlockedMountColors().contains(color);
	}

	public void addUnlockedMountColor(Color color) {
		List<String> colors = getUnlockedMountColors();
		colors.add(color.toString());
		config.set("Mount.Unlocked Colors", colors);
		saveConfig();
	}

	public List<String> getUnlockedMountStyles() {
		if (!config.contains("Mount.Unlocked Styles"))
			return new ArrayList<String>();
		return config.getStringList("Mount.Unlocked Styles");
	}

	public boolean hasUnlockedMountStyle(Style style) {
		return hasUnlockedMountStyle(style.toString());
	}

	public boolean hasUnlockedMountStyle(String color) {
		if (color.equals(Style.NONE.toString())) {
			return true;
		}
		return getUnlockedMountStyles().contains(color);
	}

	public void addUnlockedMountStyle(Style style) {
		List<String> styles = getUnlockedMountStyles();
		styles.add(style.toString());
		config.set("Mount.Unlocked Styles", styles);
		saveConfig();
	}

	public List<String> getUnlockedMountVariants() {
		if (!config.contains("Mount.Unlocked Variants"))
			return new ArrayList<String>();
		return config.getStringList("Mount.Unlocked Variants");
	}

	public boolean hasUnlockedMountVariant(Variant variant) {
		return hasUnlockedMountVariant(variant.toString());
	}

	public boolean hasUnlockedMountVariant(String variant) {
		if (variant.equals(Variant.HORSE.toString())) {
			return true;
		}
		return getUnlockedMountVariants().contains(variant);
	}

	public void addUnlockedMountVariant(Variant variant) {
		List<String> variants = getUnlockedMountVariants();
		variants.add(variant.toString());
		config.set("Mount.Unlocked Variants", variants);
		saveConfig();
	}

	public List<String> getUnlockedMountSizes() {
		if (!config.contains("Mount.Unlocked Sizes"))
			return new ArrayList<String>();
		return config.getStringList("Mount.Unlocked Sizes");
	}

	public boolean hasUnlockedMountSize(Size size) {
		return hasUnlockedMountSize(size.toString());
	}

	public boolean hasUnlockedMountSize(String size) {
		if (size.equals(Size.NORMAL.toString())) {
			return true;
		}
		return getUnlockedMountSizes().contains(size);
	}

	public void addUnlockedMountSize(int size) {
		addUnlockedMountSize("" + size);
	}

	public void addUnlockedMountSize(String size) {
		List<String> sizes = getUnlockedMountSizes();
		sizes.add(size);
		config.set("Mount.Unlocked Sizes", sizes);
		saveConfig();
	}

	public ItemStack getMountArmor() {
		ItemStack item = config.getItemStack("Mount.Armor");
		if (item != null) {
			item.setAmount(1); // For some reason it becomes 0 if this isn't here...
		}
		return item;
	}

	public void setMountArmor(ItemStack item) {
		config.set("Mount.Armor", item);
		saveConfig();
	}

	public void setMiningLevel(int value) {
		config.set("Mining.Level", value);
		saveConfig();
	}

	public int getMiningLevel() {
		if (!config.contains("Mining.Level"))
			return 1;
		return config.getInt("Mining.Level");
	}

	public void modifyMiningLevel(int value) {
		if (!config.contains("Mining.Level"))
			setMiningLevel(1);
		setMiningLevel(getMiningLevel() + value);
	}

	public void setMiningSpeedLevel(int value) {
		config.set("Mining.Speed", value);
		saveConfig();
	}

	public int getMiningSpeedLevel() {
		if (!config.contains("Mining.Speed"))
			return 1;
		return config.getInt("Mining.Speed");
	}

	public double getTotalMiningSpeed() {
		return PickaxeFactory.convertMiningSpeedLevelToDamagePerTick(getMiningSpeedLevel());
	}

	public void modifyMiningSpeedLevel(int value) {
		if (!config.contains("Mining.Speed"))
			setMiningSpeedLevel(1);
		setMiningSpeedLevel(getMiningSpeedLevel() + value);
	}

	public void setMiningLuckLevel(int value) {
		config.set("Mining.Luck", value);
		saveConfig();
	}

	public int getMiningLuckLevel() {
		if (!config.contains("Mining.Luck"))
			return 1;
		return config.getInt("Mining.Luck");
	}

	public PickaxeFactory.DropChances getTotalMiningLuck() {
		return PickaxeFactory.convertMiningLuckLevelToMiningDropChances(getMiningLuckLevel());
	}

	public void modifyMiningLuckLevel(int value) {
		if (!config.contains("Mining.Luck"))
			setMiningLuckLevel(1);
		setMiningLuckLevel(getMiningLuckLevel() + value);
	}

	public void setMiningMaxDurabilityLevel(int value) {
		config.set("Mining.MaxDurabilityLevel", value);
		saveConfig();
	}

	public int getMiningMaxDurabilityLevel() {
		if (!config.contains("Mining.MaxDurabilityLevel"))
			return 1;
		return config.getInt("Mining.MaxDurabilityLevel");
	}

	public int getTotalMiningMaxDurability() {
		return PickaxeFactory.convertMiningMaxDurabilityLevelToDurability(getMiningMaxDurabilityLevel());
	}

	public void modifyMiningMaxDurabilityLevel(int value) {
		if (!config.contains("Mining.MaxDurabilityLevel"))
			setMiningMaxDurabilityLevel(1);
		setMiningMaxDurabilityLevel(getMiningMaxDurabilityLevel() + value);
	}

	public void setMiningCurrentDurability(int value) {
		if (value < 0) {
			value = 0;
		}
		config.set("Mining.CurrentDurability", value);
		saveConfig();
	}

	public int getMiningCurrentDurability() {
		if (!config.contains("Mining.CurrentDurability"))
			modifyMiningCurrentDurability(0);
		return config.getInt("Mining.CurrentDurability");
	}

	public void modifyMiningCurrentDurability(int value) {
		if (!config.contains("Mining.CurrentDurability"))
			setMiningCurrentDurability(getTotalMiningMaxDurability());
		setMiningCurrentDurability(getMiningCurrentDurability() + value);
	}

	public void setMiningExp(int value) {
		if (getLevel() >= MAX_MINING_LEVEL) {
			return;
		}
		config.set("Mining.Exp", value);
		if (value >= getMiningExpToNextLevel()) { // Level Up
			levelUpMining();
		}
		saveConfig();
	}

	public int getMiningExp() {
		if (!config.contains("Mining.Exp"))
			return 1;
		return config.getInt("Mining.Exp");
	}

	public void modifyMiningExp(int value) {
		if (!config.contains("Mining.Exp"))
			setMiningExp(1);
		setMiningExp(getMiningExp() + value);
		player.sendMessage(ChatColor.GRAY + "" + ChatColor.BOLD + "+" + ChatColor.GRAY + value + ChatColor.DARK_PURPLE + "" + ChatColor.BOLD
				+ " PICK EXP");
	}

	public void levelUpMining() {
		if (getLevel() >= MAX_MINING_LEVEL) {
			return;
		}

		int newExp = getMiningExp() - getMiningExpToNextLevel();
		modifyMiningLevel(1);
		modifyMiningAP(1);
		player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 10f, 10f);
		player.sendMessage(" ");
		player.sendMessage(ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + "MINING LEVEL " + getMiningLevel());
		player.sendMessage(ChatColor.AQUA + "" + ChatColor.BOLD + "Mining Stat Points" + ChatColor.AQUA + " + 1");
		player.sendMessage(ChatColor.GRAY + "" + ChatColor.ITALIC + getMiningExpToNextLevel() + " xp to lvl " + (getMiningLevel() + 1));
		player.sendMessage(" ");
		saveConfig();
		new BukkitRunnable() {
			@Override
			public void run() {
				setMiningExp(newExp);
			}
		}.runTaskLater(InfernalRealms.getPlugin(), 1L);
	}

	public void setMiningAP(int value) {
		config.set("Mining.AP", value);
		saveConfig();
	}

	public int getMiningAP() {
		if (!config.contains("Mining.AP"))
			return 0;
		return config.getInt("Mining.AP");
	}

	public void modifyMiningAP(int value) {
		if (!config.contains("Mining.AP"))
			setMiningAP(0);
		setMiningAP(getMiningAP() + value);
	}

	public void setMiningHilt(ItemStack value) {
		config.set("Mining.Hilt", value);
		saveConfig();
	}

	public ItemStack getMiningHilt() {
		if (!config.contains("Mining.Hilt"))
			return null;
		return config.getItemStack("Mining.Hilt");
	}

	public void setMiningBinding(ItemStack value) {
		config.set("Mining.Binding", value);
		saveConfig();
	}

	public ItemStack getMiningBinding() {
		if (!config.contains("Mining.Binding"))
			return null;
		return config.getItemStack("Mining.Binding");
	}

	public void setMiningReinforcement(ItemStack value) {
		config.set("Mining.Reinforcement", value);
		saveConfig();
	}

	public ItemStack getMiningReinforcement() {
		if (!config.contains("Mining.Reinforcement"))
			return null;
		return config.getItemStack("Mining.Reinforcement");
	}

	public int getMiningExpToNextLevel() {
		int level = getMiningLevel();
		return (int) (getMiningDifficultyMultiplier(level) * Math.pow(level, 2) + 2 * level + 50);
	}

	public double getMiningDifficultyMultiplier(int level) {
		if (level < 20) {
			return 6.5;
		} else if (level < 40) {
			return 30;
		} else if (level < 60) {
			return 99;
		} else if (level < 80) {
			return 213;
		} else {
			return 385;
		}
	}

	public Block getTargetBlock() {
		return targetBlock;
	}

	public void setTargetBlock(Block targetBlock, Ore targetOre) {
		this.targetBlock = targetBlock;
		this.targetOre = targetOre;
	}

	public void damageBlock() {
		if (this.targetBlock == null || this.targetOre == null) {
			resetBlockDamage();
			return;
		}
		this.blockDamage += getTotalMiningSpeed();
		EffectsUtil.sendBreakPacket(player, getTargetBlock(), (int) (9 * (this.blockDamage / (float) this.targetOre.getDurability())));
		if (this.blockDamage >= this.targetOre.getDurability()) {
			// Break the block
			PickaxeFactory.breakBlock(getPlayer(), getTargetBlock(), this.targetOre);
			resetBlockDamage();
		}
	}

	public void resetBlockDamage() {
		this.blockDamage = 0;
	}

	public List<List<String>> getRecipeLog(RecipeItemType type) {
		return (List<List<String>>) getConfig().getList("BlacksmithingRecipes." + type, new ArrayList<>());
	}

	public void addRecipeLogItem(ItemStack item) {
		List<String> oldLore = item.getItemMeta().getLore();
		List<String> adjustedLore = new ArrayList<>(oldLore.size());

		adjustedLore.add(item.getItemMeta().getDisplayName());
		for (int i = 0; i < oldLore.size() - 2; i++) {
			adjustedLore.add(oldLore.get(i));
		}
		RecipeItemType type = RecipeItemType.fromString(ChatColor.stripColor(adjustedLore.get(3)));
		int newLevel = ItemReader.getRequiredLevel(item.getItemMeta().getLore());
		List<List<String>> recipes = getRecipeLog(type);

		if (recipes.isEmpty()) {
			recipes.add(adjustedLore);
		} else {
			// Insert sorted... TODO Change to be O(logn) instead of O(n)
			boolean added = false;
			for (int i = 0; i < recipes.size(); i++) {
				List<String> lore = recipes.get(i);
				int level = ItemReader.getRequiredLevel(lore);
				if (newLevel < level) {
					recipes.add(i, adjustedLore);
					added = true;
					break;
				}
			}
			if (!added) {
				recipes.add(adjustedLore);
			}
		}
		getConfig().set("BlacksmithingRecipes." + type, recipes);
		saveConfig();
		getPlayer().sendMessage(ChatColor.GREEN + "Added new " + type.toString().toLowerCase() + " recipe.");
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	private String getSkillDisplayName(Class<? extends Skill> skill) {
		try {
			return (String) skill.getField("DISPLAY_NAME").get(null);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public void setSkillLevel(Class<Skill> skill, int value) {
		setSkillLevel(getSkillDisplayName(skill), value);
	}

	public void setSkillLevel(String skill, int value) {
		config.set(currentCharacterSlot + ".Skills." + skill, value);
		saveConfig();
	}

	public int getSkillLevel(Class<Skill> skill) {
		return getSkillLevel(getSkillDisplayName(skill));
	}

	public int getSkillLevel(String skill) {
		if (!config.contains(currentCharacterSlot + ".Skills." + skill))
			return 0;
		return config.getInt(currentCharacterSlot + ".Skills." + skill);
	}

	public void addDoubleExp(long minutes) {
		long ms = minutes * 60000;
		long baseTime;
		if (YAMLFile.MULTIPLIERS.getConfig().contains("Exp." + getPlayer().getUniqueId())) {
			baseTime = YAMLFile.MULTIPLIERS.getConfig().getLong("Exp." + getPlayer().getUniqueId());
		} else {
			baseTime = System.currentTimeMillis();
		}
		baseTime += ms;
		YAMLFile.MULTIPLIERS.getConfig().set("Exp." + getPlayer().getUniqueId(), baseTime);
		YAMLFile.MULTIPLIERS.save();
	}

	public boolean hasDoubleExp() {
		if (!YAMLFile.MULTIPLIERS.getConfig().contains("Exp." + getPlayer().getUniqueId())) {
			return false;
		}
		long expiration = YAMLFile.MULTIPLIERS.getConfig().getLong("Exp." + getPlayer().getUniqueId());
		if (expiration < System.currentTimeMillis()) {
			YAMLFile.MULTIPLIERS.getConfig().set("Exp." + getPlayer().getUniqueId(), null);
			return false;
		}
		return true;
	}

	public long getDoubleExpDuration() {
		if (!hasDoubleExp()) {
			return 0;
		}
		long expiration = YAMLFile.MULTIPLIERS.getConfig().getLong("Exp." + getPlayer().getUniqueId());
		return (expiration - System.currentTimeMillis()) / 1000;
	}

	public double getExpMultiplier() {
		return hasDoubleExp() ? 2 : 1;
	}

	public void addDoubleDrop(long minutes) {
		long ms = minutes * 60000;
		long baseTime;
		if (YAMLFile.MULTIPLIERS.getConfig().contains("Drop." + getPlayer().getUniqueId())) {
			baseTime = YAMLFile.MULTIPLIERS.getConfig().getLong("Drop." + getPlayer().getUniqueId());
		} else {
			baseTime = System.currentTimeMillis();
		}
		baseTime += ms;
		YAMLFile.MULTIPLIERS.getConfig().set("Drop." + getPlayer().getUniqueId(), baseTime);
		YAMLFile.MULTIPLIERS.save();
	}

	public boolean hasDoubleDrop() {
		if (!YAMLFile.MULTIPLIERS.getConfig().contains("Drop." + getPlayer().getUniqueId())) {
			return false;
		}
		long expiration = YAMLFile.MULTIPLIERS.getConfig().getLong("Drop." + getPlayer().getUniqueId());
		if (expiration < System.currentTimeMillis()) {
			YAMLFile.MULTIPLIERS.getConfig().set("Drop." + getPlayer().getUniqueId(), null);
			return false;
		}
		return true;
	}

	public long getDoubleDropDuration() {
		if (!hasDoubleDrop()) {
			return 0;
		}
		long expiration = YAMLFile.MULTIPLIERS.getConfig().getLong("Drop." + getPlayer().getUniqueId());
		return (expiration - System.currentTimeMillis()) / 1000;
	}

	public double getDropMultiplier() {
		return hasDoubleDrop() ? 2 : 1;
	}

	public void addDoubleProfessionExp(long minutes) {
		long ms = minutes * 60000;
		long baseTime;
		if (YAMLFile.MULTIPLIERS.getConfig().contains("ProfessionExp." + getPlayer().getUniqueId())) {
			baseTime = YAMLFile.MULTIPLIERS.getConfig().getLong("ProfessionExp." + getPlayer().getUniqueId());
		} else {
			baseTime = System.currentTimeMillis();
		}
		baseTime += ms;
		YAMLFile.MULTIPLIERS.getConfig().set("ProfessionExp." + getPlayer().getUniqueId(), baseTime);
		YAMLFile.MULTIPLIERS.save();
	}

	public boolean hasDoubleProfessionExp() {
		if (!YAMLFile.MULTIPLIERS.getConfig().contains("ProfessionExp." + getPlayer().getUniqueId())) {
			return false;
		}
		long expiration = YAMLFile.MULTIPLIERS.getConfig().getLong("ProfessionExp." + getPlayer().getUniqueId());
		if (expiration < System.currentTimeMillis()) {
			YAMLFile.MULTIPLIERS.getConfig().set("ProfessionExp." + getPlayer().getUniqueId(), null);
			return false;
		}
		return true;
	}

	public long getDoubleProfessionExpDuration() {
		if (!hasDoubleProfessionExp()) {
			return 0;
		}
		long expiration = YAMLFile.MULTIPLIERS.getConfig().getLong("ProfessionExp." + getPlayer().getUniqueId());
		return (expiration - System.currentTimeMillis()) / 1000;
	}

	public double getProfessionExpMultiplier() {
		return hasDoubleProfessionExp() ? 2 : 1;
	}

	public void upgradeSkill(Class<Skill> skill) {
		int value = getSkillLevel(skill) + 1;
		if (value > 8)
			return;
		int cost = value >= 7 ? 2 : 1;
		if (getSP() < cost) {
			getPlayer().sendMessage(ChatColor.RED + "You do not have enough Skill Points (SP)!");
			return;
		}
		modifySP(-cost);
		setSkillLevel(skill, value);
	}

	public boolean hasQuest(int questID) {
		return config.contains(currentCharacterSlot + ".Quests." + questID);
	}

	public boolean isQuestComplete(int questID) {
		return hasQuest(questID) && config.getString(currentCharacterSlot + ".Quests." + questID + ".Progress").equals("COMPLETE");
	}

	public void giveQuest(int questID) {
		if (hasQuest(questID)) {
			return;
		}
		Quest.getQuest(getPlayer(), questID); // isValid() will be false if they aren't the right level.
	}

	public List<Quest> getActiveQuests() {
		List<Quest> quests = new ArrayList<Quest>();
		ConfigurationSection cs = config.getConfigurationSection(currentCharacterSlot + ".Quests");
		if (cs == null)
			return quests;
		Map<String, Object> questMap = cs.getValues(false);
		for (String questIDString : questMap.keySet()) {
			String progress = (String) questMap.get(questIDString);
			int questID;
			try {
				questID = Integer.parseInt(questIDString);
			} catch (Exception e) {
				continue;
			}
			if (!progress.equals("COMPLETE")) {
				Quest quest = Quest.getQuest(getPlayer(), questID);
				if (quest == null) { // TODO Test isValid() ?
					continue;
				}
				quests.add(quest);
			}
		}
		return quests;
	}

	public void addDefaultLoggedQuestsToLog() {
		List<Quest> activeQuests = getActiveQuests();
		for (Quest quest : activeQuests) {
			if (quest.isStory()) {
				QuestTabList.addToHelper(getPlayer(), quest.getQuestID());
			}
		}
	}

	public void processObjectiveKill(CustomMobData customMob) {
		boolean updated = false;
		List<Quest> quests = getActiveQuests();
		Party party = Party.getParty(getPlayer());
		if (party != null) {
			if (party.hasDungeon()) {
				if (party.getDungeon().getQuest() != null && party.getDungeon().getQuest().getProcessedMobData() == null
						|| !party.getDungeon().getQuest().getProcessedMobData().equals(customMob)) {
					quests.add(party.getDungeon().getQuest());
					party.getDungeon().getQuest().setProcessedMobData(customMob);
				}
			}
		}
		for (Quest quest : quests) {
			// Check for objective completion
			if (quest == null) {
				continue;
			}
			for (Objective obj : quest.getCurrentObjectives()) {
				if (obj instanceof ObjectiveKill) {
					if (((ObjectiveKill) obj).getTargetFullName().equals(customMob.getIdentifierName())) {
						obj.addProgress(1);
						updated = true;
						if (obj.isComplete()) {
							quest.tryNext();
						}
						quest.save();
					}
				}
			}
		}
		TabListType currentTab = TabList.getTabListType(player);
		if (updated && (currentTab == TabListType.QUEST || currentTab == TabListType.ROTATE)) {
			QuestTabList.sendTab(getPlayer());
		}
	}

	public ObjectiveTalk processObjectiveTalk(NPC npc) {
		//		if (hasBuffer()) {
		//			return null;
		//		}
		//		applyBuffer();
		List<Quest> quests = getActiveQuests();
		Party party = Party.getParty(getPlayer());
		if (party != null) {
			if (party.hasDungeon()) {
				if (party.getDungeon().getQuest() != null) {
					quests.add(party.getDungeon().getQuest());
				}
			}
		}
		for (Quest quest : quests) {
			// Check for objective completion
			for (Objective obj : quest.getCurrentObjectives()) {
				if (obj instanceof ObjectiveTalk) {
					ObjectiveTalk objTalk = (ObjectiveTalk) obj;
					if (objTalk.getNpcName().equals(npc.getName())) {
						if (objTalk instanceof Deliverable) {
							Deliverable objDel = (Deliverable) objTalk;
							if (!objDel.checkInventory(getPlayer().getInventory())) {
								player.sendMessage(ChatColor.GRAY + "" + ChatColor.ITALIC
										+ "You do not have the necessary items to complete the objective.");
								continue;
							}
						}
						new BukkitRunnable() {
							@Override
							public void run() {
								if (objTalk instanceof Deliverable) {
									Deliverable objDel = (Deliverable) objTalk;
									if (!objDel.checkInventoryAndRemove(getPlayer().getInventory())) {
										player.sendMessage(ChatColor.RED + "" + ChatColor.BOLD
												+ "Unable to continue because the item you need is no longer in your inventory!");
										return;
									}
								}
								objTalk.addProgress(1);
								if (objTalk.isComplete()) {
									if (objTalk instanceof ObjectiveTalkWithReward) {
										ObjectiveTalkWithReward objTalkR = (ObjectiveTalkWithReward) objTalk;
										objTalkR.getReward().giveTo(getPlayer());
									}
									quest.tryNext();
								}
								quest.save();
								TabListType currentTab = TabList.getTabListType(player);
								if (currentTab == TabListType.QUEST || currentTab == TabListType.ROTATE) {
									QuestTabList.sendTab(getPlayer());
								}
							}
						}.runTaskLater(InfernalRealms.getPlugin(),
								ObjectiveTalk.MESSAGE_DELAY * (objTalk.getMessages().length - 1 > 0 ? objTalk.getMessages().length - 1 : 0)
										+ 2L);
						return objTalk;
					}
				}
			}
		}
		return null;
	}

	public ObjectiveChooseClass processObjectiveChooseClass() {
		for (Quest quest : getActiveQuests()) {
			// Check for objective completion
			for (Objective obj : quest.getCurrentObjectives()) {
				if (obj instanceof ObjectiveChooseClass) {
					obj.addProgress(1);
					if (obj.isComplete()) {
						quest.tryNext();
					}
					quest.save();
					TabListType currentTab = TabList.getTabListType(player);
					if (currentTab == TabListType.QUEST || currentTab == TabListType.ROTATE) {
						QuestTabList.sendTab(getPlayer());
					}
					return (ObjectiveChooseClass) obj;
				}
			}
		}
		return null;
	}

	public ObjectiveOpenBank processObjectiveOpenBank() {
		for (Quest quest : getActiveQuests()) {
			// Check for objective completion
			for (Objective obj : quest.getCurrentObjectives()) {
				if (obj instanceof ObjectiveOpenBank) {
					obj.addProgress(1);
					if (obj.isComplete()) {
						quest.tryNext();
					}
					quest.save();
					TabListType currentTab = TabList.getTabListType(player);
					if (currentTab == TabListType.QUEST || currentTab == TabListType.ROTATE) {
						QuestTabList.sendTab(getPlayer());
					}
					return (ObjectiveOpenBank) obj;
				}
			}
		}
		return null;
	}

	public ObjectiveSummonMount processObjectiveSummonMount() {
		//		if (hasBuffer()) {
		//			return null;
		//		}
		//		applyBuffer();
		for (Quest quest : getActiveQuests()) {
			// Check for objective completion
			for (Objective obj : quest.getCurrentObjectives()) {
				if (obj instanceof ObjectiveSummonMount) {
					obj.addProgress(1);
					if (obj.isComplete()) {
						quest.tryNext();
					}
					quest.save();
					TabListType currentTab = TabList.getTabListType(player);
					if (currentTab == TabListType.QUEST || currentTab == TabListType.ROTATE) {
						QuestTabList.sendTab(getPlayer());
					}
					return (ObjectiveSummonMount) obj;
				}
			}
		}
		return null;
	}

	public ObjectiveLearnSkill processObjectiveLearnSkill() {
		for (Quest quest : getActiveQuests()) {
			// Check for objective completion
			for (Objective obj : quest.getCurrentObjectives()) {
				if (obj instanceof ObjectiveLearnSkill) {
					obj.addProgress(1);
					if (obj.isComplete()) {
						quest.tryNext();
					}
					quest.save();
					TabListType currentTab = TabList.getTabListType(player);
					if (currentTab == TabListType.QUEST || currentTab == TabListType.ROTATE) {
						QuestTabList.sendTab(getPlayer());
					}
					return (ObjectiveLearnSkill) obj;
				}
			}
		}
		return null;
	}

	public ObjectiveLevelMountSpeed processObjectiveLevelMountSpeed() {
		for (Quest quest : getActiveQuests()) {
			// Check for objective completion
			for (Objective obj : quest.getCurrentObjectives()) {
				if (obj instanceof ObjectiveLevelMountSpeed) {
					obj.addProgress(1);
					if (obj.isComplete()) {
						quest.tryNext();
					}
					quest.save();
					TabListType currentTab = TabList.getTabListType(player);
					if (currentTab == TabListType.QUEST || currentTab == TabListType.ROTATE) {
						QuestTabList.sendTab(getPlayer());
					}
					return (ObjectiveLevelMountSpeed) obj;
				}
			}
		}
		return null;
	}

	public ObjectiveInteractWithBlock processObjectiveInteractWithBlock(Location location) {
		//		if (hasBuffer()) {
		//			return null;
		//		}
		//		applyBuffer();
		for (Quest quest : getActiveQuests()) {
			// Check for objective completion
			for (Objective obj : quest.getCurrentObjectives()) {
				if (obj instanceof ObjectiveInteractWithBlock) {
					ObjectiveInteractWithBlock objI = (ObjectiveInteractWithBlock) obj;
					if (objI.checkLocation(location)) {
						obj.addProgress(1);
						if (obj.isComplete()) {
							quest.tryNext();
						}
						quest.save();
						TabListType currentTab = TabList.getTabListType(player);
						if (currentTab == TabListType.QUEST || currentTab == TabListType.ROTATE) {
							QuestTabList.sendTab(getPlayer());
						}
						return (ObjectiveInteractWithBlock) obj;
					}
				}
			}
		}
		return null;
	}

	public ObjectiveBreakBlock processObjectiveBreakBlock(String blockName) {
		List<Quest> quests = getActiveQuests();
		Party party = Party.getParty(getPlayer());
		if (party != null) {
			if (party.hasDungeon()) {
				if (party.getDungeon().getQuest() != null) {
					quests.add(party.getDungeon().getQuest());
				}
			}
		}
		for (Quest quest : quests) {
			// Check for objective completion
			for (Objective obj : quest.getCurrentObjectives()) {
				if (obj instanceof ObjectiveBreakBlock) {
					ObjectiveBreakBlock objBB = (ObjectiveBreakBlock) obj;
					if (objBB.getTargetBlock().equalsIgnoreCase(blockName)) {
						obj.addProgress(1);
						if (obj.isComplete()) {
							quest.tryNext();
						}
						quest.save();
						TabListType currentTab = TabList.getTabListType(player);
						if (currentTab == TabListType.QUEST || currentTab == TabListType.ROTATE) {
							QuestTabList.sendTab(getPlayer());
						}
						return (ObjectiveBreakBlock) obj;
					}
				}
			}
		}
		return null;
	}

	public ObjectiveCompleteQuest processObjectiveCompleteQuest(HardcodedQuest.QuestName completedQuest) {
		for (Quest quest : getActiveQuests()) {
			// Check for objective completion
			for (Objective obj : quest.getCurrentObjectives()) {
				if (obj instanceof ObjectiveCompleteQuest) {
					if (((ObjectiveCompleteQuest) obj).getGoalQuest() == completedQuest) {
						obj.addProgress(1);
						if (obj.isComplete()) {
							quest.tryNext();
						}
						quest.save();
						TabListType currentTab = TabList.getTabListType(player);
						if (currentTab == TabListType.QUEST || currentTab == TabListType.ROTATE) {
							QuestTabList.sendTab(getPlayer());
						}
						return (ObjectiveCompleteQuest) obj;
					}
				}
			}
		}
		return null;
	}

	public void checkPassiveObjectives() {
		for (Quest quest : getActiveQuests()) {
			// Check for objective completion
			for (Objective obj : quest.getCurrentObjectives()) {
				if (!(obj instanceof PassiveCompletion)) {
					continue;
				}
				((PassiveCompletion) obj).check(getPlayer());
			}
		}
	}

	public void updateEquipStats() {
		for (Stat stat : Stat.values()) {
			stats.put(stat, getStatFromEquips(stat));
		}
		setGearscore(calculateGearscore());
		setDirtyEquips(false);
	}

	public int getStatFromEquips(Stat stat) {
		return getStatFromEquips(stat.getName());
	}

	public int getStatFromEquips(String stat) {
		stat = " " + stat; // Add space to ensure safe stat grabbing

		int statAmount = 0;
		//@formatter:off
		ItemStack[] equips = new ItemStack[] { 
				player.getInventory().getHelmet(), 
				player.getInventory().getChestplate(),
				player.getInventory().getLeggings(), 
				player.getInventory().getBoots(), 
				player.getInventory().getItem(0)
				};
		//@formatter:on
		for (int i = 0; i < equips.length; i++) {
			ItemStack equip = equips[i];
			if (equip == null || equip.getType() == Material.AIR || !equip.getItemMeta().hasLore()) {
				continue;
			}
			if (i == equips.length - 1) {
				// Last one
				if (!WEAPON_TYPES.contains(equip.getType())) {
					continue;
				}
			}
			List<String> equipLore = equip.getItemMeta().getLore();
			for (String line : equipLore) {
				if (!line.contains(stat)) {
					continue;
				}
				String[] lineSplit = line.split(" ");
				statAmount += Integer.parseInt(ChatColor.stripColor(lineSplit[!line.contains("▣") ? 0 : 2]).replace("+", ""));
			}
		}

		return statAmount;
	}

	public int getEquipStat(Stat stat) {
		if (isDirtyEquips()) {
			updateEquipStats();
		}
		return stats.get(stat);
	}

	public int getEquipStat(String stat) {
		return getEquipStat(Stat.fromName(stat));
	}
	
	public double calculateGearscore() {
		//@formatter:off
		ItemStack[] equips = new ItemStack[] { 
				player.getInventory().getHelmet(), 
				player.getInventory().getChestplate(),
				player.getInventory().getLeggings(), 
				player.getInventory().getBoots(), 
				player.getInventory().getItem(0)
				};
		//@formatter:on
		double totalScore = 0;
		for (int i = 0; i < equips.length; i++) {
			ItemStack equip = equips[i];
			if (equip == null || equip.getType() == Material.AIR || !equip.getItemMeta().hasLore() || !ItemReader.isEquipable(player, equip)) {
				continue;
			}
			if (i == equips.length - 1) {
				// Last one
				if (!WEAPON_TYPES.contains(equip.getType())) {
					continue;
				}
			}
			//player.sendMessage("Adding " + equip + " to gearscore (" + ItemReader.getItemLevel(equip) + ")" );
			totalScore += ItemReader.getItemLevel(equip);
		}
		//player.sendMessage("Gearscore: " + totalScore / 5);
		return totalScore / 5;
	}
	
	public double getGearscore() {
		if (!config.contains(currentCharacterSlot + ".Gearscore"))
			return 0;
		return config.getDouble(currentCharacterSlot + ".Gearscore");
	}
	
	public void setGearscore(double value) {
		config.set(currentCharacterSlot + ".Gearscore", value);
		saveConfig();
	}
	
	public void modifyGearscore(double value) {
		if (!config.contains(currentCharacterSlot + ".Gearscore"))
			setGearscore(0);
		setGearscore(getLevel() + value);
	}

	/**
	 * Calculates damage without taking a target into account
	 * @return the calculated damage
	 */
	public DamageValue calculateDamage() {
		return calculateDamage(null);
	}

	/**
	 * Calculates the damage on a given target
	 * @param target the target that would be damaged
	 * @return the calculated damage
	 */
	public DamageValue calculateDamage(LivingEntity target) {
		Random r = new Random();
		ItemStack weapon = player.getItemInHand();
		if (!ItemReader.isEquipable(getPlayer(), weapon)) {
			getPlayer().sendMessage(InfernalStrings.UNEQUIPABLE_MESSAGE);
			return new DamageValue(-1);
		}
		double damage = 0;
		switch (getPlayerSuperClass().toLowerCase()) {
		case "warrior":
			damage += getTotalStrength();
			break;
		case "archer":
			damage += getTotalDexterity();
			break;
		case "magician":
			damage += getTotalIntelligence();
			break;
		}
		if (weapon != null && weapon.hasItemMeta() && weapon.getType() != Material.AIR) {
			if (weapon.getItemMeta().hasLore()) {
				ItemMeta weaponMeta = weapon.getItemMeta();
				List<String> weaponLore = weaponMeta.getLore();
				for (String s : weaponLore) {
					String[] weaponLoreSplit = s.split(" ");
					for (String s2 : weaponLoreSplit) {
						if (s2.equals("Damage")) {
							int minDamage = Integer.parseInt(ChatColor.stripColor(weaponLoreSplit[0]));
							int maxDamage = Integer.parseInt(weaponLoreSplit[2]);
							int randomDamage = minDamage + (int) (Math.random() * ((maxDamage - minDamage) + 1));
							damage += (double) randomDamage;
						}
					}
				}
			}
		}
		// Multipliers last
		// Criticals
		double critChance = getTotalDodgeCrit();
		if (critChance > 0.5) {
			critChance = 0.5;
		}
		double randomCrit = r.nextDouble();
		boolean crit = false;
		if (randomCrit <= critChance) {
			double critDamage = new SharpEye(getPlayer()).getDamageModifier();
			damage *= critDamage;
			crit = true;
			player.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "Critical strike!");
		}
		// Skills
		if (target != null && target.hasMetadata("SkillDamage")) {
			damage *= (double) target.getMetadata("SkillDamage").get(0).value();
			target.removeMetadata("SkillDamage", InfernalRealms.getPlugin());
		} else {
			if (getPlayerSuperClass().equalsIgnoreCase("Magician")) {
				return new DamageValue(0);
			}
		}
		if (damage <= 0) {
			damage = 1D;
		}
		return new DamageValue(damage, crit);
	}

	public boolean isDirtyEquips() {
		return dirtyEquips;
	}

	public void setDirtyEquips(boolean dirtyEquips) {
		this.dirtyEquips = dirtyEquips;
	}

	public void updateParticles() {
		idleParticles = AestheticType.fromString(config.getString(currentCharacterSlot + ".Particles.Idle"));
		followParticles = AestheticType.fromString(config.getString(currentCharacterSlot + ".Particles.Follow"));
		damagedParticles = AestheticType.fromString(config.getString(currentCharacterSlot + ".Particles.Damaged"));
		attackParticles = AestheticType.fromString(config.getString(currentCharacterSlot + ".Particles.Attack"));
		System.out.println(idleParticles + ", " + followParticles + ", " + damagedParticles + ", " + attackParticles);
	}

	public AestheticType getIdleParticles() {
		if (this.idleParticles == null) {
			AestheticType type = AestheticType.fromString(config.getString(currentCharacterSlot + ".Particles.Idle"));
			if (type == null) {
				type = AestheticType.NONE;
			}
			this.idleParticles = type;
		}
		return this.idleParticles;
	}

	public void setIdleParticles(AestheticType type) {
		this.idleParticles = type;
		config.set(currentCharacterSlot + ".Particles.Idle", type.toString());
		saveConfig();
	}

	public AestheticType getFollowParticles() {
		if (this.followParticles == null) {
			AestheticType type = AestheticType.fromString(config.getString(currentCharacterSlot + ".Particles.Follow"));
			if (type == null) {
				type = AestheticType.NONE;
			}
			this.followParticles = type;
		}
		return this.followParticles;
	}

	public void setFollowParticles(AestheticType type) {
		this.followParticles = type;
		config.set(currentCharacterSlot + ".Particles.Follow", type.toString());
		saveConfig();
	}

	public AestheticType getAttackParticles() {
		if (this.attackParticles == null) {
			AestheticType type = AestheticType.fromString(config.getString(currentCharacterSlot + ".Particles.Attack"));
			if (type == null) {
				type = AestheticType.NONE;
			}
			this.attackParticles = type;
		}
		return this.attackParticles;
	}

	public void setAttackParticles(AestheticType type) {
		this.attackParticles = type;
		config.set(currentCharacterSlot + ".Particles.Attack", type.toString());
		saveConfig();
	}

	public AestheticType getDamagedParticles() {
		if (this.damagedParticles == null) {
			AestheticType type = AestheticType.fromString(config.getString(currentCharacterSlot + ".Particles.Damaged"));
			if (type == null) {
				type = AestheticType.NONE;
			}
			this.damagedParticles = type;
		}
		return this.damagedParticles;
	}

	public void setDamagedParticles(AestheticType type) {
		this.damagedParticles = type;
		config.set(currentCharacterSlot + ".Particles.Damaged", type.toString());
		saveConfig();
	}

	public List<AestheticType> getUnlockedParticles() {
		if (!config.contains("Particles")) {
			return new ArrayList<AestheticType>();
		}
		List<String> stringParticles = config.getStringList("Particles");
		List<AestheticType> particles = new ArrayList<>();
		for (String sp : stringParticles) {
			AestheticType particle = AestheticType.fromString(sp);
			if (particle == null) {
				continue;
			}
			particles.add(particle);
		}
		return particles;
	}

	public boolean hasUnlockedParticle(AestheticType type) {
		if (type.equals(AestheticType.NONE)) {
			return true;
		}
		return getUnlockedParticles().contains(type);
	}

	public void addUnlockedParticle(AestheticType type) {
		List<AestheticType> types = getUnlockedParticles();
		types.add(type);
		List<String> stringTypes = new ArrayList<>();
		types.forEach((particle) -> {
			stringTypes.add(particle.toString());
		});
		config.set("Particles", stringTypes);
		saveConfig();
	}

	public String getPlayerInfoString() {
		int expToNextLevel = expToNextLevel();
		int exp = getExp();
		int expPercent = (int) Math.ceil(100 * ((double) exp / expToNextLevel));
		return ChatColor.DARK_GREEN + "" + ChatColor.BOLD + "LVL: " + ChatColor.RESET + ChatColor.GREEN + getLevel() + ChatColor.DARK_GREEN
				+ ChatColor.BOLD + " HP: " + ChatColor.RESET + ChatColor.GREEN + (int) ((Damageable) getPlayer()).getHealth()
				+ ChatColor.BOLD + " / " + ChatColor.RESET + ChatColor.GREEN + getTotalHealth() + ChatColor.DARK_GREEN + ChatColor.BOLD
				+ " EXP: " + ChatColor.RESET + ChatColor.GREEN + (expPercent > 100 ? 100 : expPercent) + "%";
	}

	private void applyBuffer() {
		if (hasBuffer()) {
			buffer.cancel();
		}

		buffer = new BukkitRunnable() {

			@Override
			public void run() {
				removeBuffer();
			}
		};
		buffer.runTaskLater(InfernalRealms.getPlugin(), 40L);
	}

	private boolean hasBuffer() {
		return buffer != null;
	}

	private void removeBuffer() {
		buffer = null;
	}

	public void applyClickBuffer() {
		if (hasClickBuffer()) {
			clickBuffer.cancel();
		}

		clickBuffer = new BukkitRunnable() {

			@Override
			public void run() {
				removeClickBuffer();
			}
		};
		clickBuffer.runTaskLater(InfernalRealms.getPlugin(), 5L);
	}

	public boolean hasClickBuffer() {
		return clickBuffer != null;
	}

	private void removeClickBuffer() {
		clickBuffer = null;
	}

	public void applyHealthBuffer() {
		if (hasHealthBuffer()) {
			healthBuffer.cancel();
		}

		healthBuffer = new BukkitRunnable() {

			@Override
			public void run() {
				removeHealthBuffer();
			}
		};
		healthBuffer.runTaskLater(InfernalRealms.getPlugin(), 200L);
	}

	public boolean hasHealthBuffer() {
		return healthBuffer != null;
	}

	private void removeHealthBuffer() {
		healthBuffer = null;
	}

	public void applyManaBuffer() {
		if (hasManaBuffer()) {
			manaBuffer.cancel();
		}

		manaBuffer = new BukkitRunnable() {

			@Override
			public void run() {
				removeManaBuffer();
			}
		};
		manaBuffer.runTaskLater(InfernalRealms.getPlugin(), 200L);
	}

	public boolean hasManaBuffer() {
		return manaBuffer != null;
	}

	private void removeManaBuffer() {
		manaBuffer = null;
	}

	public static File getPlayersFolder() {
		return playersFolder;
	}

	public static void prepareFiles() {
		makeFolders();
	}

	public static void makeFolders() {
		playersFolder = new File(InfernalRealms.getPlugin().getDataFolder(), "Players");
		playersFolder.mkdirs();
	}

	public void setDungeon(DungeonInstance dungeon) {
		this.dungeon = dungeon;
	}

	public DungeonInstance getDungeon() {
		return this.dungeon;
	}

	public boolean hasDungeon() {
		return getDungeon() != null;
	}

	public static void setConversation(Player player, boolean flag) {
		if (flag) {
			player.setMetadata("Conversation", new FixedMetadataValue(InfernalRealms.getPlugin(), true));
			InfernalEffects.playConversationStartSound(player);
		} else {
			player.removeMetadata("Conversation", InfernalRealms.getPlugin());
		}
	}

}

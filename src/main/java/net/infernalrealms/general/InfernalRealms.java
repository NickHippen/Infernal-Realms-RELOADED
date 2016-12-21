package net.infernalrealms.general;

import java.io.File;
import java.io.FilenameFilter;
import java.lang.reflect.Field;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.ScoreboardManager;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;
import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;

import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.trait.TraitInfo;
import net.infernalrealms.blacksmithing.BlacksmithingBaseGUI;
import net.infernalrealms.blacksmithing.BlacksmithingCommands;
import net.infernalrealms.blacksmithing.BlacksmithingRecipeFactory;
import net.infernalrealms.blacksmithing.BlacksmithingRecipeListGUI;
import net.infernalrealms.blacksmithing.GeneratedBlacksmithingRecipe;
import net.infernalrealms.blacksmithing.RefineryForgeListener;
import net.infernalrealms.blacksmithing.RefiningGUI;
import net.infernalrealms.chat.BroadcastRunnable;
import net.infernalrealms.dungeons.Dungeon;
import net.infernalrealms.dungeons2.DungeonCleaner;
import net.infernalrealms.economy.EconomyCommands;
import net.infernalrealms.gui.ButtonHubGUI;
import net.infernalrealms.gui.DonationShopGUI;
import net.infernalrealms.gui.DungeonGUI;
import net.infernalrealms.gui.ParticleManagerGUI;
import net.infernalrealms.gui.ParticleShopGUI;
import net.infernalrealms.gui.PlayerInventoryListener;
import net.infernalrealms.gui.TabList;
import net.infernalrealms.homesteads.HomesteadCleaner;
import net.infernalrealms.homesteads.HomesteadEventHandler;
import net.infernalrealms.inventory.InventoryCommands;
import net.infernalrealms.inventory.InventoryUpdateListener;
import net.infernalrealms.items.FileEquipmentModifier;
import net.infernalrealms.items.ItemCommands;
import net.infernalrealms.items.ItemEtherealBankerCoupon;
import net.infernalrealms.items.ItemEtherealMerchantCoupon;
import net.infernalrealms.leaderboard.IntegerScore;
import net.infernalrealms.leaderboard.Leaderboard;
import net.infernalrealms.leaderboard.PartyRecord;
import net.infernalrealms.leaderboard.Record;
import net.infernalrealms.leaderboard.Scoreable;
import net.infernalrealms.leaderboard.SoloRecord;
import net.infernalrealms.leaderboard.TimeScore;
import net.infernalrealms.mining.MiningCommands;
import net.infernalrealms.mining.MiningListener;
import net.infernalrealms.mining.MiningManagementGUI;
import net.infernalrealms.mobs.CustomEntityType;
import net.infernalrealms.mobs.MobManager;
import net.infernalrealms.mobs.MobsCommands;
import net.infernalrealms.mobs.SpawnManager;
import net.infernalrealms.mount.Feed;
import net.infernalrealms.mount.MountCommands;
import net.infernalrealms.npc.NPCCommands;
import net.infernalrealms.npc.TraitBanker;
import net.infernalrealms.npc.TraitDungeonKeeper;
import net.infernalrealms.npc.TraitMountShop;
import net.infernalrealms.npc.TraitQuestGiver;
import net.infernalrealms.npc.TraitShopkeeper;
import net.infernalrealms.party.PartyCommands;
import net.infernalrealms.player.ManaRecovery;
import net.infernalrealms.player.ParticleListener;
import net.infernalrealms.player.PlayerCommands;
import net.infernalrealms.player.PlayerData;
import net.infernalrealms.quests.minigames.IcePushMaze;
import net.infernalrealms.util.MovingPlatform;
import net.minecraft.server.v1_9_R1.Item;
import net.minecraft.server.v1_9_R1.ItemPotion;
import net.minecraft.server.v1_9_R1.Items;

public class InfernalRealms extends JavaPlugin {

	// SETTINGS
	public static final boolean DEBUG_MODE = false;
	public static final boolean BOSS_BAR_ENABLED = false;

	// OTHER
	public static double EXP_MULTIPLIER = 1;
	public static double DROP_MULTIPLIER = 1;

	public static final Random RANDOM = new Random();
	public final Logger logger = Logger.getLogger("Minecraft");
	private static InfernalRealms plugin;
	public static ScoreboardManager manager;
	private static ProtocolManager protocolManager;
	public static Location LOBBY_SPAWN;
	public static Location TUTORIAL_START;
	public static World MAIN_WORLD;
	public static World SECONDARY_WORLD;
	public static World SPAWN_WORLD;
	private static WorldGuardPlugin worldGuardPlugin;
	private static WorldEditPlugin worldEditPlugin;

	public InfernalRealms() {
		ConfigurationSerialization.registerClass(Record.class);
		ConfigurationSerialization.registerClass(Scoreable.class);
		ConfigurationSerialization.registerClass(TimeScore.class);
		ConfigurationSerialization.registerClass(IntegerScore.class);
		ConfigurationSerialization.registerClass(SoloRecord.class);
		ConfigurationSerialization.registerClass(PartyRecord.class);
	}

	@Override
	public void onDisable() {
		PluginDescriptionFile pdfFile = this.getDescription();
		this.logger.info(pdfFile.getName() + " Has Been Disabled!");
		Dungeon.clearMobsAllInstances();
		MobManager.butcherMobs();
		MobManager.removeAllEntities();
		for (Hologram holo : HologramsAPI.getHolograms(getPlugin())) {
			holo.delete();
		}
		for (Player player : Bukkit.getOnlinePlayers()) {
			PlayerData playerData = PlayerData.getData(player);
			if (!playerData.hasDungeon()) {
				playerData.setLocation();
			}
			playerData.setStoredHealth();
		}

		// Recover broken blocks
		for (BukkitRunnable task : GeneralListener.REGEN_BLOCKS) {
			task.run();
		}

	}

	@Override
	public void onEnable() {
		if (!Bukkit.getPluginManager().isPluginEnabled("HolographicDisplays")) {
			getLogger().severe("*** HolographicDisplays is not installed or not enabled. ***");
			getLogger().severe("*** This plugin will be disabled. ***");
			this.setEnabled(false);
			return;
		}
		//		if (!Bukkit.getPluginManager().isPluginEnabled("TabAPI")) {
		//			getLogger().severe("*** TabAPI is not installed or not enabled. ***");
		//			getLogger().severe("*** This plugin will be disabled. ***");
		//			this.setEnabled(false);
		//			return;
		//		}

		// Logging
		PluginDescriptionFile pdfFile = this.getDescription();
		this.logger.info(pdfFile.getName() + " Has Been Enabled!");
		InfernalRealms.plugin = this;

		// Prepare listeners & commands
		this.enableListeners();
		this.enableCommands();

		// NPCs
		if (getServer().getPluginManager().getPlugin("Citizens") == null
				|| getServer().getPluginManager().getPlugin("Citizens").isEnabled() == false) {
			getLogger().log(Level.SEVERE, "Citizens 2.0 not found or not enabled");
			getServer().getPluginManager().disablePlugin(this);
			return;
		}
		registerNPCTraits();

		// World Guard
		Plugin wgpl = getServer().getPluginManager().getPlugin("WorldGuard");
		if (wgpl == null || wgpl.isEnabled() == false) {
			getLogger().log(Level.SEVERE, "World Guard not found or not enabled");
			getServer().getPluginManager().disablePlugin(this);
			return;
		}
		setWorldGuardPlugin((WorldGuardPlugin) wgpl);

		// World Edit
		Plugin wepl = getServer().getPluginManager().getPlugin("WorldEdit");
		if (wepl == null || wepl.isEnabled() == false) {
			getLogger().log(Level.SEVERE, "World Edit not found or not enabled");
			getServer().getPluginManager().disablePlugin(this);
			return;
		}
		setWorldEditPlugin(((WorldEditPlugin) wepl));

		// Locations
		MAIN_WORLD = Bukkit.getWorld("IRWP");
		SECONDARY_WORLD = Bukkit.getWorld("Dungeon");
		SPAWN_WORLD = Bukkit.getWorld("SPAWN");
		LOBBY_SPAWN = new Location(SPAWN_WORLD, 999.5, 65, 1000.5, -90F, 0F);
		TUTORIAL_START = new Location(MAIN_WORLD, 1291.5, 74, -647.5, 90F, 0F);
		RegionManager.prepareRespawnLocations();

		// Misc.
		PlayerData.prepareFiles();
		YAMLFile.prepareFiles();
		new ManaRecovery().runTaskTimer(this, 20L, 20L);
		new HomesteadCleaner();
		new DungeonCleaner();
		BroadcastRunnable.currentRunnable = new BroadcastRunnable();
		SpawnManager.refreshSpawnsDelayed();
		this.logger.info(pdfFile.getName() + " has completed configuration.");
		manager = Bukkit.getScoreboardManager();
		setProtocolManager(ProtocolLibrary.getProtocolManager());
		CustomEntityType.registerCustomMobs();
		applyPacketListeners();
		changeMaxStackSizes();
		FileEquipmentModifier.registerModifiers();
		BlacksmithingRecipeFactory.prepareRecipes();
		// Delayed tasks
		new BukkitRunnable() {

			@Override
			public void run() {
				MobManager.removeAllNonSpawnEntities();
				MovingPlatform.refreshPlatforms();
			}
		}.runTaskLater(InfernalRealms.getPlugin(), 100L);
	}

	private void enableListeners() {
		this.getServer().getPluginManager().registerEvents(new GeneralListener(), this);
		this.getServer().getPluginManager().registerEvents(new InventoryUpdateListener(), this);
		this.getServer().getPluginManager().registerEvents(new TabList(), this);
		this.getServer().getPluginManager().registerEvents(new TraitShopkeeper(), this);

		// GUI
		this.getServer().getPluginManager().registerEvents(new PlayerInventoryListener(), this);
		this.getServer().getPluginManager().registerEvents(new ButtonHubGUI(), this);
		this.getServer().getPluginManager().registerEvents(new ParticleManagerGUI(), this);
		this.getServer().getPluginManager().registerEvents(new ParticleShopGUI(), this);
		this.getServer().getPluginManager().registerEvents(new DonationShopGUI(), this);
		this.getServer().getPluginManager().registerEvents(new MiningManagementGUI(), this);
		this.getServer().getPluginManager().registerEvents(new Leaderboard.GUIListener(), this);
		this.getServer().getPluginManager().registerEvents(new DungeonGUI.GUIListener(), this);

		// MISC
		this.getServer().getPluginManager().registerEvents(new ParticleListener(), this);
		this.getServer().getPluginManager().registerEvents(new MiningListener(), this);
		this.getServer().getPluginManager().registerEvents(new MobsCommands(), this);
		this.getServer().getPluginManager().registerEvents(new MiscCommands(), this);
		this.getServer().getPluginManager().registerEvents(new IcePushMaze.PushListener(), this);
		this.getServer().getPluginManager().registerEvents(new MovingPlatform.PlatformListener(), this);
		this.getServer().getPluginManager().registerEvents(new GeneratedBlacksmithingRecipe.RecipeItem(), this);
		this.getServer().getPluginManager().registerEvents(new BlacksmithingBaseGUI.GUIListener(), this);
		this.getServer().getPluginManager().registerEvents(new BlacksmithingRecipeListGUI.GUIListener(), this);
		this.getServer().getPluginManager().registerEvents(new RefineryForgeListener(), this);
		this.getServer().getPluginManager().registerEvents(new RefiningGUI.GUIListener(), this);
		this.getServer().getPluginManager().registerEvents(new HomesteadEventHandler(), this);

		// ITEMS
		this.getServer().getPluginManager().registerEvents(new ItemEtherealMerchantCoupon(), this);
		this.getServer().getPluginManager().registerEvents(new ItemEtherealBankerCoupon(), this);
		this.getServer().getPluginManager().registerEvents(new Feed(), this);
	}

	//	private void setupMobs() {
	//		CustomEntityType2.addCustomEntity(CustomZombie.class, "CustomZombie", CustomZombie.ENTITY_ID);
	//		CustomEntityType2.addCustomEntity(CustomSpider.class, "CustomSpider", CustomSpider.ENTITY_ID);
	//		CustomEntityType2.addCustomEntity(CustomSkeleton.class, "CustomSkeleton", CustomSkeleton.ENTITY_ID);
	//		CustomEntityType2.addCustomEntity(CustomBat.class, "CustomBat", CustomBat.ENTITY_ID);
	//		CustomEntityType2.addCustomEntity(CustomCaveSpider.class, "CustomCaveSpider", CustomCaveSpider.ENTITY_ID);
	//		CustomEntityType2.addCustomEntity(CustomBlaze.class, "CustomBlaze", CustomBlaze.ENTITY_ID);
	//		CustomEntityType2.addCustomEntity(CustomEnderman.class, "CustomEnderman", CustomEnderman.ENTITY_ID);
	//		CustomEntityType2.addCustomEntity(CustomEndermite.class, "CustomEndermite", CustomEndermite.ENTITY_ID);
	//		CustomEntityType2.addCustomEntity(CustomIronGolem.class, "CustomIronGolem", CustomIronGolem.ENTITY_ID);
	//		CustomEntityType2.addCustomEntity(CustomSilverfish.class, "CustomSilverfish", CustomSilverfish.ENTITY_ID);
	//		CustomEntityType2.addCustomEntity(CustomWitch.class, "CustomWitch", CustomWitch.ENTITY_ID);
	//		CustomEntityType2.addCustomEntity(CustomWolf.class, "CustomWolf", CustomWolf.ENTITY_ID);
	//		CustomEntityType2.addCustomEntity(CustomZombiePigman.class, "CustomZombiePigman", CustomZombiePigman.ENTITY_ID);
	//		CustomEntityType2.addCustomEntity(CustomRabbit.class, "CustomRabbit", CustomRabbit.ENTITY_ID);
	//		CustomEntityType2.addCustomEntity(CustomGuardian.class, "CustomGuardian", CustomGuardian.ENTITY_ID);
	//	}

	private void registerNPCTraits() {
		CitizensAPI.getTraitFactory().registerTrait(TraitInfo.create(TraitShopkeeper.class).withName("shopkeeper"));
		CitizensAPI.getTraitFactory().registerTrait(TraitInfo.create(TraitBanker.class).withName("banker"));
		CitizensAPI.getTraitFactory().registerTrait(TraitInfo.create(TraitQuestGiver.class).withName("questgiver"));
		CitizensAPI.getTraitFactory().registerTrait(TraitInfo.create(TraitDungeonKeeper.class).withName("dungeonkeeper"));
		CitizensAPI.getTraitFactory().registerTrait(TraitInfo.create(TraitMountShop.class).withName("mountshop"));
	}

	private void enableCommands() {
		getCommand("test").setExecutor(new TestingCommands());
		getCommand("testeffect").setExecutor(new TestingCommands());
		getCommand("testsound").setExecutor(new TestingCommands());
		getCommand("testcolor").setExecutor(new TestingCommands());
		getCommand("ia").setExecutor(new TestingCommands());
		getCommand("leavedungeon").setExecutor(new MiscCommands());
		getCommand("wipeinv").setExecutor(new InventoryCommands());
		getCommand("stats").setExecutor(new PlayerCommands());
		getCommand("choose").setExecutor(new PlayerCommands());
		getCommand("skills").setExecutor(new PlayerCommands());
		getCommand("logout").setExecutor(new PlayerCommands());
		getCommand("quests").setExecutor(new PlayerCommands());
		getCommand("particles").setExecutor(new PlayerCommands());
		getCommand("ii").setExecutor(new ItemCommands());
		getCommand("im").setExecutor(new MobsCommands());
		getCommand("infernalmobs").setExecutor(new MobsCommands());
		getCommand("ie").setExecutor(new EconomyCommands());
		getCommand("ip").setExecutor(new EconomyCommands());
		getCommand("mount").setExecutor(new MountCommands());
		getCommand("party").setExecutor(new PartyCommands());
		getCommand("trade").setExecutor(new InventoryCommands());
		getCommand("accepttrade").setExecutor(new InventoryCommands());
		getCommand("inpc").setExecutor(new NPCCommands());
		getCommand("pickaxe").setExecutor(new MiningCommands());
		getCommand("iplatform").setExecutor(new MovingPlatform.PlatformCommands());
		getCommand("ib").setExecutor(new BlacksmithingCommands());
	}

	private void changeMaxStackSizes() {
		modifyMaxStack(Items.POTION, 64);
	}

	public void modifyMaxStack(ItemPotion item, int amount) {
		try {
			Field f = Item.class.getDeclaredField("maxStackSize");
			f.setAccessible(true);
			f.setInt(item, amount);
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
	}

	public static InfernalRealms getPlugin() {
		return InfernalRealms.plugin;
	}

	public static WorldGuardPlugin getWorldGuardPlugin() {
		return worldGuardPlugin;
	}

	public static void setWorldGuardPlugin(WorldGuardPlugin worldGuardPlugin) {
		InfernalRealms.worldGuardPlugin = worldGuardPlugin;
	}

	public static WorldEditPlugin getWorldEditPlugin() {
		return worldEditPlugin;
	}

	public static void setWorldEditPlugin(WorldEditPlugin worldEditPlugin) {
		InfernalRealms.worldEditPlugin = worldEditPlugin;
	}

	public static ProtocolManager getProtocolManager() {
		return protocolManager;
	}

	public static void setProtocolManager(ProtocolManager protocolManager) {
		InfernalRealms.protocolManager = protocolManager;
	}

	private static void applyPacketListeners() {
		//		getProtocolManager().addPacketListener(
		//				new PacketAdapter(InfernalRealms.getPlugin(), ListenerPriority.NORMAL, PacketType.Play.Server.BLOCK_BREAK_ANIMATION) {
		//					@Override
		//					public void onPacketSending(PacketEvent event) {
		//						event.getPlayer().sendMessage("" + event.getPacketType());
		//						System.out.println(event.getPacket().getBlockData());
		//					}
		//				});
	}

	public static void loadAllChunks(World world) {
		final Pattern regionPattern = Pattern.compile("r\\.([0-9-]+)\\.([0-9-]+)\\.mca");

		File worldDir = new File(Bukkit.getWorldContainer(), world.getName());
		File regionDir = new File(worldDir, "region");

		File[] regionFiles = regionDir.listFiles(new FilenameFilter() {
			@Override
			public boolean accept(File dir, String name) {
				return regionPattern.matcher(name).matches();
			}
		});

		InfernalRealms.getPlugin().getLogger().info(
				"Found " + (regionFiles.length * 1024) + " chunk candidates in " + regionFiles.length + " files to check for loading ...");

		for (File f : regionFiles) {
			// extract coordinates from filename
			Matcher matcher = regionPattern.matcher(f.getName());
			if (!matcher.matches()) {
				InfernalRealms.getPlugin().getLogger().warning("FilenameFilter accepted unmatched filename: " + f.getName());
				continue;
			}

			int mcaX = Integer.parseInt(matcher.group(1));
			int mcaZ = Integer.parseInt(matcher.group(2));

			int loadedCount = 0;

			for (int cx = 0; cx < 32; cx++) {
				for (int cz = 0; cz < 32; cz++) {
					// local chunk coordinates need to be transformed into global ones
					boolean didLoad = world.loadChunk((mcaX << 5) + cx, (mcaZ << 5) + cz, false);
					if (didLoad)
						loadedCount++;
				}
			}
			InfernalRealms.getPlugin().getLogger().info("Loaded " + loadedCount + " chunks.");
		}
	}

	public static void shutdownSafe() {
		Dungeon.clearMobsAllInstances();
		MobManager.butcherMobs();
		MobManager.removeAllEntities();
		for (Hologram holo : HologramsAPI.getHolograms(getPlugin())) {
			holo.delete();
		}
		for (Player player : Bukkit.getOnlinePlayers()) {
			PlayerData playerData = PlayerData.getData(player);
			if (!playerData.hasDungeon()) {
				playerData.setLocation();
			}
			playerData.setStoredHealth();
		}
		for (Player player : Bukkit.getOnlinePlayers()) {
			player.kickPlayer("The server is shutting down.");
		}

		// Recover broken blocks
		for (BukkitRunnable task : GeneralListener.REGEN_BLOCKS) {
			task.run();
		}

		// Ensure mobs are not saved
		for (World world : Bukkit.getWorlds()) {
			world.setKeepSpawnInMemory(false);
		}
		Bukkit.shutdown();
	}

}

package net.infernalrealms.general;

import java.awt.Color;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.Sound;
import org.bukkit.block.BlockFace;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Ageable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import de.inventivegames.particle.ParticleEffect;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.npc.CitizensNPCRegistry;
import net.infernalrealms.blacksmithing.BlacksmithingBaseGUI;
import net.infernalrealms.blacksmithing.BlacksmithingRecipeListGUI;
import net.infernalrealms.blacksmithing.RecipeItemType;
import net.infernalrealms.chat.BroadcastRunnable;
import net.infernalrealms.chat.InteractiveChat;
import net.infernalrealms.cutscenes.Cutscene;
import net.infernalrealms.cutscenes.WrappedLocation.SerializableLocation;
import net.infernalrealms.dungeons2.DungeonInstance;
import net.infernalrealms.gui.ItemMessageHandler;
import net.infernalrealms.gui.QuestLogGUI;
import net.infernalrealms.gui.QuestTabList;
import net.infernalrealms.gui.StatsTabList;
import net.infernalrealms.gui.TabList;
import net.infernalrealms.gui.TabListType;
import net.infernalrealms.homesteads.HomesteadInstance;
import net.infernalrealms.items.InfernalItemsRELOADED;
import net.infernalrealms.items.InvalidTraitException;
import net.infernalrealms.mobs.FileMob;
import net.infernalrealms.mobs.HologramHandler;
import net.infernalrealms.mobs.SpawnManager;
import net.infernalrealms.mount.MountArmor;
import net.infernalrealms.npc.NPCManager;
import net.infernalrealms.npc.TraitBanker;
import net.infernalrealms.party.Party;
import net.infernalrealms.player.PlayerData;
import net.infernalrealms.quests.Quest;
import net.infernalrealms.util.EffectsUtil;
import net.infernalrealms.util.GeneralUtil;
import net.infernalrealms.util.MovingPlatform;
import net.infernalrealms.util.Platform;

public class TestingCommands implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args) {
		if (sender.isOp()) {
			if (commandLabel.equalsIgnoreCase("test")) {
				if (sender instanceof Player) {
					Player player = (Player) sender;
					if (args[0].equalsIgnoreCase("exp")) {
						if (args.length == 2) {
							PlayerData playerData = PlayerData.getData(player);
							try {
								playerData.modifyExp(Integer.parseInt(args[1]));
							} catch (NumberFormatException e) {
								player.sendMessage("Expected number.");
							}
						} else {
							player.sendMessage("/test exp [amount]");
						}
					} else if (args[0].equalsIgnoreCase("skiptutorial")) {
						for (int i = 10001; i <= 10013; i++) {
							if (i == 10013) {
								i = 100085;
							}
							Quest quest = Quest.getQuest(player, i);
							if (quest != null) {
								quest.setComplete();
								quest.save();
							}
						}
						player.sendMessage(ChatColor.GREEN + "Skipped tutorial");
					} else if (args[0].equalsIgnoreCase("fullbright")) {
						if (player.hasPotionEffect(PotionEffectType.NIGHT_VISION)) {
							player.removePotionEffect(PotionEffectType.NIGHT_VISION);
						} else {
							player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, Integer.MAX_VALUE, 0));
						}
					} else if (args[0].equalsIgnoreCase("clearmobs")) {
						InfernalRealms.loadAllChunks(InfernalRealms.MAIN_WORLD);
						for (Chunk c : InfernalRealms.MAIN_WORLD.getLoadedChunks()) {
							for (Entity e : c.getEntities()) {
								e.remove();
							}
						}
						player.sendMessage(ChatColor.GREEN + "Cleared mobs in main world.");
					} else if (args[0].equals("1")) {
						if (CitizensAPI.getNPCRegistry() instanceof CitizensNPCRegistry) {
							CitizensNPCRegistry registry = (CitizensNPCRegistry) CitizensAPI.getNPCRegistry();
							try {
								Method generateUniqueId = registry.getClass().getDeclaredMethod("generateUniqueId");
								generateUniqueId.setAccessible(true);
								int uniqueId = (int) generateUniqueId.invoke(registry);
								NPC npc = registry.createNPC(EntityType.PLAYER, player.getUniqueId(), uniqueId, "Banker");
								npc.addTrait(new TraitBanker());
								npc.spawn(player.getLocation());
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					} else if (args[0].equals("2")) {
						Ageable ageable = (Ageable) player.getWorld().spawnEntity(player.getLocation(), EntityType.COW);
						ageable.setBaby();
						System.out.println(ageable.getAge());
					} else if (args[0].equals("3")) {
						QuestLogGUI.openMenu(player, 1);
					} else if (args[0].equals("4")) {
						TabList.setTabListType(player, TabListType.QUEST);
						QuestTabList.sendTab(player);
					} else if (args[0].equals("5")) {
						Quest quest = Quest.getQuest(player, 10002);
						player.sendMessage(quest.toString());
					} else if (args[0].equals("6")) {
						TabList.setTabListType(player, TabListType.ROTATE);
						StatsTabList.sendTab(player);
					} else if (args[0].equals("7")) {
						try {
							de.inventivegames.particle.ParticleEffect.RED_DUST.sendColor(player, player.getLocation(), Color.BLUE);
						} catch (Exception e) {
							e.printStackTrace();
						}
					} else if (args[0].equals("8")) {
						int mobID;
						try {
							mobID = Integer.parseInt(args[1]);
						} catch (Exception e) {
							return false;
						}
						FileMob.spawn(mobID, player.getLocation());
					} else if (args[0].equals("9")) {
						InteractiveChat interactiveChat = new InteractiveChat(
								ChatColor.AQUA + "" + ChatColor.ITALIC + player.getName() + ChatColor.RESET + ChatColor.AQUA
										+ " has asked you to " + ChatColor.BOLD + "trade." + ChatColor.RESET + ChatColor.AQUA + " Type "
										+ ChatColor.ITALIC + "/accepttrade" + ChatColor.RESET + ChatColor.AQUA + " or click "
										+ ChatColor.AQUA + "" + ChatColor.BOLD + "here" + ChatColor.AQUA + " if you would like to trade.");
						interactiveChat.applyHoverMessage(ChatColor.AQUA + "Accept Trade");
						interactiveChat.applyClickCommand("/accepttrade");
						interactiveChat.sendToPlayer(player);
					} else if (args[0].equals("10")) {
						PlayerData playerData = PlayerData.getData(player);
						GeneralListener gl = new GeneralListener();
						PlayerInteractEvent e = new PlayerInteractEvent(player, Action.RIGHT_CLICK_BLOCK, player.getItemInHand(),
								player.getTargetBlock((Set) null, 10), BlockFace.UP);
						System.out.println();
						long delay = System.currentTimeMillis();
						for (int i = 0; i < 100000; i++) {
							// Action NEW
						}
						System.out.println("New: " + (System.currentTimeMillis() - delay) + "ms");
						delay = System.currentTimeMillis();
						for (int i = 0; i < 100000; i++) {
							// Action OLD
							PlayerData.getData(player);
						}
						System.out.println("Old: " + (System.currentTimeMillis() - delay) + "ms");
					} else if (args[0].equals("11")) {
						for (org.bukkit.entity.Entity e : player.getNearbyEntities(10, 10, 10)) {
							player.sendMessage("" + e.getClass());
						}
					} else if (args[0].equals("12")) {
						HologramHandler.createHologramOnEntity(player, "The Bear Slayer", true);
					} else if (args[0].equals("13")) {
						for (String spawn : SpawnManager.getSpawnPointMobs().keySet()) {
							if (spawn.equalsIgnoreCase("test")) {
								System.out.println(SpawnManager.getSpawnPointMobs().get(spawn));
							}
						}
					} else if (args[0].equals("14")) {
						PlayerData playerData = PlayerData.getData(player);
						playerData.refreshQuestMarkers();
					} else if (args[0].equals("15")) {
						Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&',
								GeneralUtil.combineArgsToString(Arrays.copyOfRange(args, 1, args.length))));
					} else if (args[0].equals("16")) {
						player.sendMessage("Packet test.");
						ItemMessageHandler.displayMessage(player, "Test Title");
					} else if (args[0].equals("17")) {
						try {
							player.getInventory()
									.addItem(InfernalItemsRELOADED.generateTierItem(new String[] { "Tutorial", "BOW", "" + 5, "Common" }));
						} catch (InvalidTraitException e) {
							e.printStackTrace();
						}
					} else if (args[0].equals("18")) {
						if (args.length == 1) {
							player.sendMessage("" + SpawnManager.spawnPointMobs);
						}
					} else if (args[0].equals("19")) {
						player.getInventory().addItem(MountArmor.DIAMOND_HORSE_ARMOR.generateItem());
					} else if (args[0].equals("20")) {
						PlayerData playerData = PlayerData.getData(player);
						playerData.addDoubleExp(1);
						playerData.addDoubleDrop(1);
					} else if (args[0].equals("21")) {

					} else if (args[0].equals("22")) {
						player.sendMessage("Location: " + NPCManager.getNPCLocationByName(args[1]));
					} else if (args[0].equals("23")) {
						Cutscene scene = new Cutscene(player, args[1], Integer.parseInt(args[2]));
						scene.recordScene();

						new BukkitRunnable() {

							@Override
							public void run() {
								scene.playScene(player);
							}
						}.runTaskLater(InfernalRealms.getPlugin(), (Integer.parseInt(args[2]) * 20) + 60);
					} else if (args[0].equals("24")) {
						Cutscene scene;
						try {
							scene = Cutscene.loadScene(args[1]);
						} catch (IOException e) {
							e.printStackTrace();
							player.sendMessage("Unabled to load scene.");
							return false;
						}
						scene.playScene(player);
					} else if (args[0].equals("25")) {
						Platform platform = new Platform(new SerializableLocation(player.getLocation().subtract(0, 1, 0)),
								new SerializableLocation(player.getLocation().subtract(2, 1, 2)));
						platform.moveTo(player.getLocation().subtract(3, 1, 3));
					} else if (args[0].equals("26")) {
						MovingPlatform.loadPlatforms();
					} else if (args[0].equals("27")) {
						BlacksmithingRecipeListGUI.openBaseMenu(player, RecipeItemType.SWORD);
					} else if (args[0].equals("28")) {
						BlacksmithingBaseGUI.openBaseMenu(player);
					} else if (args[0].equals("29")) {
						try {
							HomesteadInstance.loadAndVisit(player);
						} catch (IOException e) {
							e.printStackTrace();
						}
					} else if (args[0].equals("30")) {
						player.sendMessage("Loaded worlds:");
						for (org.bukkit.World world : Bukkit.getServer().getWorlds()) {
							player.sendMessage(world.getName());
						}
					} else if (args[0].equals("31")) {
						new DungeonInstance(net.infernalrealms.dungeons2.DungeonType.TEST, Party.getParty(player));
					}
				}
			} else if (commandLabel.equalsIgnoreCase("testeffect")) { // /testeffect [name] [offsetX] [offsetY] [offsetZ] [speed] [count]
				if (sender instanceof Player) {
					Player player = (Player) sender;
					if (args.length != 6) {
						player.sendMessage("Proper usage: /testeffect [name] [offsetX] [offsetY] [offsetZ] [speed] [count]");
						return false;
					}
					try {
						EffectsUtil.sendParticleToLocation(particleEffectFromName(args[0]), player.getEyeLocation(),
								Float.parseFloat(args[1]), Float.parseFloat(args[2]), Float.parseFloat(args[3]), Float.parseFloat(args[4]),
								Integer.parseInt(args[5]));
					} catch (Exception e) {
						player.sendMessage("Error parsing data.");
						return false;
					}
				}
			} else if (commandLabel.equalsIgnoreCase("testsound")) { // /testsound [name] [volume] [pitch]
				if (sender instanceof Player) {
					Player player = (Player) sender;
					if (args.length != 3) {
						player.sendMessage("Proper usage: /testsound [name] [volume] [pitch]");
						return false;
					}
					try {
						player.playSound(player.getLocation(), soundFromName(args[0]), Float.parseFloat(args[1]),
								Float.parseFloat(args[2]));
					} catch (Exception e) {
						player.sendMessage("Error parsing data.");
						return false;
					}
				}
			} else if (commandLabel.equalsIgnoreCase("testcolor")) { // /testcolor [name] [R] [G] [B] [count]
				if (sender instanceof Player) {
					Player player = (Player) sender;
					if (args.length != 5) {
						player.sendMessage("Proper usage: /testcolor [name] [R] [G] [B] [count]");
						return false;
					}
					try {
						EffectsUtil.sendColoredParticleToLocation(particleEffectFromName(args[0]), player.getEyeLocation(),
								new Color(Integer.parseInt(args[1]), Integer.parseInt(args[2]), Integer.parseInt(args[3])),
								Integer.parseInt(args[4]));
					} catch (Exception e) {
						player.sendMessage("Error parsing data.");
						return false;
					}
				}
			} else if (commandLabel.equalsIgnoreCase("ia")) {
				// Admin commands
				if (!(sender instanceof Player)) {
					sender.sendMessage(ChatColor.RED + "Players only.");
					return false;
				}
				Player player = (Player) sender;
				PlayerData playerData = PlayerData.getData(player);
				if (args[0].equalsIgnoreCase("heal")) {
					GeneralUtil.healPlayer(player, 100000D);
					player.sendMessage(ChatColor.GREEN + "Healed.");
				} else if (args[0].equalsIgnoreCase("healmana")) {
					playerData.setMana(playerData.getTotalMaxMana());
					player.sendMessage(ChatColor.GREEN + "Recovered mana.");
				} else if (args[0].equalsIgnoreCase("inspect")) {
					if (args.length != 2) {
						player.sendMessage(ChatColor.RED + "/ia inspect {Player}");
						return false;
					}
					Player target = Bukkit.getPlayer(args[1]);
					if (target == null) {
						player.sendMessage(ChatColor.RED + "Player not found.");
						return false;
					}
					inspect(player, target);
				} else if (args[0].equalsIgnoreCase("reload")) {
					YAMLFile.reloadConfigs();
					BroadcastRunnable.reloadAnnouncements();
					player.sendMessage("Reloaded.");
				}
			}
		}
		return false;
	}

	public static void inspect(Player player, Player target) {
		if (!player.isOp()) {
			return;
		}
		PlayerData targetData = PlayerData.getData(target);
		player.sendMessage("------" + target.getName() + "------");
		player.sendMessage("Character Slot: " + targetData.getCurrentCharacterSlot());
		player.sendMessage("Class: " + targetData.getPlayerClass() + ", Level: " + targetData.getLevel());
		player.sendMessage("Exp: " + targetData.getExp() + "/" + targetData.expToNextLevel());
		player.sendMessage("Mount Level: " + targetData.getMountLevel());
		player.sendMessage("Active Quests:");
		for (Quest quest : targetData.getActiveQuests()) {
			player.sendMessage(quest.getName() + " (" + quest.getQuestID() + ")   Progress: " + quest.getProgressAsString());
		}
		Inventory inv = Bukkit.createInventory(player, 9, target.getName() + "'s Equips");
		for (ItemStack item : target.getInventory().getArmorContents()) {
			inv.addItem(item);
		}
		player.openInventory(inv);
	}

	public static ParticleEffect particleEffectFromName(String name) {
		for (ParticleEffect pe : ParticleEffect.values()) {
			if (pe.name().equalsIgnoreCase(name)) {
				return pe;
			}
		}
		return null;
	}

	public static Sound soundFromName(String name) {
		for (Sound s : Sound.values()) {
			if (s.name().equalsIgnoreCase(name)) {
				return s;
			}
		}
		return null;
	}
}

package net.infernalrealms.general;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.craftbukkit.v1_9_R1.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_9_R1.entity.CraftLivingEntity;
import org.bukkit.craftbukkit.v1_9_R1.entity.CraftPlayer;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Horse;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Item;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityDamageEvent.DamageModifier;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityInteractEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent.RegainReason;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.inventory.InventoryType.SlotType;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerExpChangeEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemBreakEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.vehicle.VehicleExitEvent;
import org.bukkit.event.world.ChunkLoadEvent;
import org.bukkit.inventory.HorseInventory;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import com.sk89q.worldguard.protection.regions.ProtectedRegion;

import de.inventivegames.particle.ParticleEffect;
import net.citizensnpcs.api.event.NPCRightClickEvent;
import net.infernalrealms.dungeons.Dungeon;
import net.infernalrealms.dungeons2.DungeonInstance;
import net.infernalrealms.gui.ChooseClassGUI;
import net.infernalrealms.gui.MountShopGUI;
import net.infernalrealms.gui.QuestLogGUI;
import net.infernalrealms.gui.QuestTabList;
import net.infernalrealms.gui.SelectCharacterGUI;
import net.infernalrealms.gui.SkillsGUI;
import net.infernalrealms.gui.StatGUI;
import net.infernalrealms.gui.TabList;
import net.infernalrealms.gui.TabListType;
import net.infernalrealms.gui.TextBar;
import net.infernalrealms.homesteads.HomesteadUtils;
import net.infernalrealms.inventory.InventoryManager;
import net.infernalrealms.inventory.PouchInventory;
import net.infernalrealms.inventory.Trade;
import net.infernalrealms.items.GeneralItems;
import net.infernalrealms.items.InfernalItems;
import net.infernalrealms.items.InfernalItemsRELOADED;
import net.infernalrealms.items.InvalidTraitException;
import net.infernalrealms.items.ItemReader;
import net.infernalrealms.items.Pouch;
import net.infernalrealms.mining.PickaxeFactory;
import net.infernalrealms.mobs.SpawnManager;
import net.infernalrealms.mobs.types.InfernalMob;
import net.infernalrealms.mount.MountManager;
import net.infernalrealms.npc.TraitShopkeeper;
import net.infernalrealms.party.Party;
import net.infernalrealms.player.PlayerClass;
import net.infernalrealms.player.PlayerData;
import net.infernalrealms.player.Stat;
import net.infernalrealms.quests.HardcodedQuest.QuestName;
import net.infernalrealms.quests.Quest;
import net.infernalrealms.quests.objectives.ObjectiveInteractWithBlock;
import net.infernalrealms.quests.objectives.ObjectiveTalk;
import net.infernalrealms.skills.archer.Haste;
import net.infernalrealms.skills.archer.MagnumShot;
import net.infernalrealms.skills.archer.VenomousArrow;
import net.infernalrealms.skills.magician.Frostbite;
import net.infernalrealms.skills.magician.Levitate;
import net.infernalrealms.skills.magician.MagicBolt;
import net.infernalrealms.skills.magician.Pyroblast;
import net.infernalrealms.skills.warrior.Charge;
import net.infernalrealms.skills.warrior.Leech;
import net.infernalrealms.skills.warrior.Rumble;
import net.infernalrealms.util.EffectsUtil;
import net.infernalrealms.util.GeneralUtil;
import net.infernalrealms.util.InfernalEffects;
import net.infernalrealms.util.InfernalStrings;
import net.minecraft.server.v1_9_R1.DamageSource;
import net.minecraft.server.v1_9_R1.EntityLiving;

public class GeneralListener implements Listener {

	public static HashMap<Arrow, ItemStack> shotArrow = new HashMap<Arrow, ItemStack>();
	private static ArrayList<String> rightClickBuffer = new ArrayList<>();
	private static Random random = new Random();

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		try {
			PlayerData playerData = PlayerData.getData(player);
			playerData.refreshConfig();
			// Stat Handling
			playerData.updateHealth();
			Stat.refreshHealthNumber(player);
			player.setHealthScaled(true);
			player.setHealthScale(20d);
			player.getInventory().clear();
			player.getInventory().setArmorContents(null);
			InventoryManager.setDefaultInventory(player);
			InfernalStrings.displayLoginMessage(player);
		} catch (Exception e) {
			// Catch any exceptions that occur here so that they will at least spawn in the lobby
			e.printStackTrace();
		}

		new BukkitRunnable() {

			public void run() {
				player.teleport(InfernalRealms.LOBBY_SPAWN);
			}

		}.runTaskLater(InfernalRealms.getPlugin(), 3L);
	}

	@EventHandler
	public void onPlayerLeave(PlayerQuitEvent event) {
		Player player = event.getPlayer();
		PlayerData playerData = PlayerData.getData(player);
		InventoryManager.updateMainInventoryContent(player);
		if (player.hasMetadata("Party")) {
			Party party = Party.getParty(player);
			if (party.hasDungeon()) {
				party.getDungeon().exit(player);
			}
			party.leave(player);
		}
		if (playerData.getCurrentCharacterSlot() != -1 && !playerData.hasDungeon()) {
			playerData.setLocation();
		}
		playerData.setStoredHealth();
		if (playerData.hasDungeon()) {
			playerData.setDungeon(null);
		}
		playerData.refreshQuestMarkers();
		PlayerData.SELECTED_CHARACTER.remove(player.getName());
		UUID playerUUID = player.getUniqueId();
		new BukkitRunnable() {

			@Override
			public void run() {
				PlayerData.CONNECTED_PLAYERS.remove(playerUUID);
			}
		}.runTaskLater(InfernalRealms.getPlugin(), 1L);
	}

	@EventHandler
	public void onExpChange(PlayerExpChangeEvent event) {
		event.setAmount(0);
		Stat.refreshHealthNumber(event.getPlayer());
	}

	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent event) {
		event.setKeepInventory(true);
		event.setDeathMessage(null);
		event.setKeepLevel(true);
		Player player = event.getEntity();
		PlayerData playerData = PlayerData.getData(player);
		if (playerData.hasDungeon()) {
			player.setBedSpawnLocation(playerData.getDungeon().getSpawnLocation(), true);
			//			if (playerData.getDungeon().getQuest().isBossMode()) {
			//				player.sendMessage(ChatColor.RED
			//						+ "You will respawn in the dungeon in 30 seconds. If your party is entirely wiped out at the same time, you will fail the dungeon.");
			//			}
		} else {
			if (playerData.getCurrentCharacterSlot() != -1) {
				player.setBedSpawnLocation(RegionManager.getRespawnLocation(player), true);
			} else {
				player.setBedSpawnLocation(InfernalRealms.LOBBY_SPAWN, true);
			}
		}
		System.out.println("Player respawn Location:\n" + player.getBedSpawnLocation());
	}

	@EventHandler
	public void onItemConsume(PlayerItemConsumeEvent event) {
		event.setCancelled(true);
	}

	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		PlayerData playerData = PlayerData.getData(player);

		if (event.getAction() == Action.RIGHT_CLICK_BLOCK && event.getPlayer().getGameMode() == GameMode.SURVIVAL
				&& !HomesteadUtils.isInHomestead(player)) {
			event.setCancelled(true);
		}
		if (event.getAction() == Action.PHYSICAL) { // Stepping on pressure plates
			return;
		}
		if (player.getItemInHand().getType() == Material.BOW) {
			if (event.getAction() != Action.LEFT_CLICK_AIR && event.getAction() != Action.LEFT_CLICK_BLOCK) {
				event.setCancelled(true);
			}
		}

		if (playerData.getCurrentCharacterSlot() == -1 && player.getInventory().getItemInHand().hasItemMeta()) {
			if (player.getInventory().getItemInHand().getItemMeta().getDisplayName()
					.equals(ChatColor.RESET + "" + ChatColor.DARK_PURPLE + ChatColor.BOLD + "Right Click to View Character Selection")) {
				SelectCharacterGUI.open(player);
			}
		} else if (playerData.getPlayerSuperClass().equals("Magician")) {
			if (event.getAction() != Action.LEFT_CLICK_BLOCK && event.getAction() != Action.LEFT_CLICK_AIR) {
				if (player.getItemInHand().getType() == Material.STICK || player.getItemInHand().getType() == Material.BLAZE_ROD) {
					MagicBolt.cast(player);
				}
			}
		} else if (playerData.getPlayerSuperClass().equals("Archer")) {
			if (player.getItemInHand().getType() == Material.BOW) {
				if (event.getAction() != Action.LEFT_CLICK_BLOCK && event.getAction() != Action.LEFT_CLICK_AIR) {
					// Fire arrow
					GeneralUtil.fireNormalArrow(player);
				}
			}
		}
		if (event.getClickedBlock() != null) {
			ObjectiveInteractWithBlock obj = playerData.processObjectiveInteractWithBlock(event.getClickedBlock().getLocation());
			if (obj != null) {
				PlayerData.setConversation(player, true);
				obj.sendMessages(player);
			}
		}

		if (!ItemReader.isEquipable(player, player.getInventory().getItemInHand()) && player.getItemInHand().getType() != Material.PAPER) {
			player.sendMessage(InfernalStrings.UNEQUIPABLE_MESSAGE);
			final ItemStack beforeItem = player.getInventory().getItemInHand();
			ItemStack[] armor = player.getInventory().getArmorContents();
			new BukkitRunnable() {

				@Override
				public void run() {
					for (int i = 0; i < armor.length; i++) {
						if (armor[i].equals(beforeItem)) {
							player.getInventory().setItemInHand(beforeItem);
							armor[i] = null;
							player.getInventory().setArmorContents(armor);
						}
					}
					playerData.setDirtyEquips(true);
				}
			}.runTaskLater(InfernalRealms.getPlugin(), 1L);

			event.setCancelled(true);
		}
	}

	@EventHandler
	public void onPlayerInteractWithEntity(PlayerInteractEntityEvent event) {
		final Player player = event.getPlayer();
		// Buffer Handling (Prevents double-executions)
		if (rightClickBuffer.contains(player.getName())) {
			event.setCancelled(true);
			return;
		}
		rightClickBuffer.add(player.getName());
		new BukkitRunnable() {
			public void run() {
				rightClickBuffer.remove(player.getName());
			}
		}.runTaskLater(InfernalRealms.getPlugin(), 1L);

		// All things beyond this point will not occur with NPCs!
		if (event.getRightClicked().hasMetadata("NPC")) {
			return;
		}
		if (event.getRightClicked() instanceof Player) {
			if (player.isSneaking()) { // Prompt trade
				Player target = (Player) event.getRightClicked();
				Trade trade = new Trade(event.getPlayer(), target);
				trade.sendInvite();
			}
		}
	}

	@EventHandler
	public void onEntityInteract(EntityInteractEvent event) {
		if (event.getBlock().getType() == Material.SOIL) {
			event.setCancelled(true);
		}
	}

	@EventHandler
	public void onPlayerRespawn(PlayerRespawnEvent event) {
		Player player = event.getPlayer();
		PlayerData playerData = PlayerData.getData(player);
		Stat.refreshHealthNumber(player);
		InventoryManager.setDefaultInventory(player);
		if (playerData.getCurrentCharacterSlot() == -1) {
			event.setRespawnLocation(InfernalRealms.LOBBY_SPAWN);
		}
	}

	@EventHandler
	public void onInventoryClick(InventoryClickEvent event) {
		onInventoryClick(event, false);
	}

	public void onInventoryClick(InventoryClickEvent event, boolean ignoreBuffer) {
		final Inventory inventory = event.getInventory();
		InventoryView iv = event.getView();
		if (event.getWhoClicked() instanceof Player) {
			final Player player = (Player) event.getWhoClicked();
			PlayerData playerData = PlayerData.getData(player);
			/*
			if (!ignoreBuffer) {
				if (playerData.hasClickBuffer()) {
					player.sendMessage(ChatColor.RED + "You are clicking too fast.");
					event.setCancelled(true);
					return;
				}
				playerData.applyClickBuffer();
			}
			*/
			if (inventory instanceof HorseInventory) {
				if (playerData.getCurrentCharacterSlot() == -1) {
					event.setCancelled(true);
				}
				if (event.getRawSlot() == 1) { // Armor Slot
					if (player.getVehicle() instanceof Horse) {
						if (event.getCursor() == null
								|| event.getCursor().getType() == Material.AIR && event.getCurrentItem().getType() != Material.AIR) { // Grabbing armor
							event.setCancelled(false);
							playerData.setMountArmor(null);
							MountManager.updateHorseStats(player, (Horse) player.getVehicle(), false);
							return;
						} else if (ItemReader.isMountArmor(event.getCursor()) && event.getCurrentItem().getType() == Material.AIR) { // Safe to place
							event.setCancelled(false);
							playerData.setMountArmor(event.getCursor());
							MountManager.updateHorseStats(player, (Horse) player.getVehicle(), false);
							return;
						}
					}
				} else if (event.getRawSlot() == 0) {
					event.setCancelled(true);
				}
			}
			playerData.updateHealth();
			if (player.getGameMode() != GameMode.CREATIVE) {
				if (event.getRawSlot() >= iv.getTopInventory().getSize()) { // Bottom inv
					if (InventoryManager.getNoInteractSlots().contains(event.getSlot())
							|| InventoryManager.getNoInteractSlots().contains(event.getHotbarButton()))
						event.setCancelled(true);
					if (InventoryManager.getPouchSlots().contains(event.getSlot())) {
						event.setCancelled(true);
						ItemStack clickedWith = iv.getCursor();
						ItemStack clickedOn = iv.getItem(event.getRawSlot());
						if (!event.isShiftClick()) {
							if (clickedWith.hasItemMeta() && clickedOn.hasItemMeta() && clickedWith.getAmount() == 1) {
								if (clickedOn.getItemMeta().getDisplayName()
										.equals(ChatColor.RESET + "" + ChatColor.GRAY + ChatColor.UNDERLINE + "Empty Pouch Slot")
										&& clickedOn.getType().equals(Material.IRON_FENCE) && clickedWith.getItemMeta().hasLore()
										&& clickedWith.getType() == Material.INK_SACK && InventoryManager.getPouchSize(clickedWith) > 0) {
									if (!InventoryManager.hasPouch(player,
											ChatColor.stripColor(clickedWith.getItemMeta().getDisplayName()))) {
										event.setCurrentItem(clickedWith);
										iv.setCursor(new ItemStack(Material.AIR));
										InventoryManager.activatePouch(player, event.getSlot(), clickedWith);
									}
								}
							}
						}
						if (clickedOn.hasItemMeta() && clickedWith.getType() == Material.AIR) {
							if (clickedOn.getItemMeta().hasLore() && clickedOn.getType() == Material.INK_SACK) {
								String pouchName = ChatColor.stripColor(clickedOn.getItemMeta().getDisplayName());
								int pouchSize = InventoryManager.getPouchSize(clickedOn);
								if (event.isShiftClick()) {
									if (!InventoryManager.pouchHasContents(player, clickedOn)) {
										if (player.getInventory().firstEmpty() != -1) {
											InventoryManager.removePouch(player, event.getSlot());
											player.getInventory().setItem(player.getInventory().firstEmpty(), clickedOn);
											event.setCurrentItem(GeneralItems.EMPTY_POUCH_SLOT);
											player.closeInventory();
										} else {
											player.sendMessage(ChatColor.RED + "Insufficient inventory space!");
											InfernalEffects.playErrorSound(player);
										}
									} else {
										player.sendMessage(ChatColor.RED + "You must empty the pouch before unequipping it!");
										InfernalEffects.playErrorSound(player);
									}
								} else {
									PouchInventory pInv = new PouchInventory(player, pouchName, pouchSize, event.getSlot());
									pInv.open();
								}
							}
						}
					} else if (inventory.getType() == InventoryType.CRAFTING) {
						if (event.getRawSlot() >= 5 && event.getRawSlot() <= 8) {
							if (!ItemReader.isEquipable(player, event.getCursor())) {
								event.setCancelled(true);
								player.sendMessage(InfernalStrings.UNEQUIPABLE_MESSAGE);
								InfernalEffects.playErrorSound(player);
							}
						} else if (event.isShiftClick()) {
							ItemStack clickedOn = iv.getItem(event.getRawSlot());
							if (!ItemReader.isEquipable(player, clickedOn)) {
								event.setCancelled(true);
								player.sendMessage(InfernalStrings.UNEQUIPABLE_MESSAGE);
								InfernalEffects.playErrorSound(player);
							}
						}
					}
				}
			}
			// Pouch Inventory
			if (inventory.getTitle().contains("Pouch: ")) {
				if (PickaxeFactory.isProfessionPickaxe(event.getCursor()) || PickaxeFactory.isProfessionPickaxe(event.getCurrentItem())) {
					event.setCancelled(true);
				}
				InventoryManager.updatePouchContents(player, event.getInventory());
			}
			// Bank Inventory
			else if (inventory.getTitle().equals("Bank")) {
				InventoryManager.updateBankContents(player, event.getInventory());
			}
			// Dungeon Queue Prompt
			else if (inventory.getTitle().equals("Dungeon Confirmation")) {
				event.setCancelled(true);
				Party party = Party.getParty(player);
				if (party == null) {
					return;
				}
				if (event.getRawSlot() == 8) {
					for (int i = 0; i < inventory.getSize(); i++) {
						ItemStack item = inventory.getItem(i);
						if (item == null || !item.hasItemMeta()) {
							continue;
						}
						if (ChatColor.stripColor(item.getItemMeta().getDisplayName()).equalsIgnoreCase(player.getName())) {
							item.setDurability((short) (item.getDurability() == 8 ? 10 : 8));
							if (Dungeon.checkAllReady(inventory)) {
								new DungeonInstance(party.getQueuedDungeonType(), party);
							}
							break;
						}
					}
				}
			} else if (inventory.getTitle().contains("Mount SP: ")) {
				MountManager.manageClick(event);
			} else if (inventory.getTitle().equals("Mount Shop")) {
				MountShopGUI.handleClick(event);
			}
			// Stat inventory
			else if (ChatColor.stripColor(inventory.getName()).contains("Current AP:")) {
				event.setCancelled(true);
				int rawSlot = event.getRawSlot();
				ap: {
					if (rawSlot <= 6 && rawSlot >= 1) {
						if (playerData.getAP() <= 0) {
							player.sendMessage(ChatColor.RED + "Insufficent Ability Power (AP)");
							InfernalEffects.playErrorSound(player);
						} else {
							switch (rawSlot) {
							case 1:
								if (!playerData.getPlayerSuperClass().equalsIgnoreCase("Warrior")) {
									player.sendMessage(ChatColor.RED + "Adding this stat will not improve your abilities at all!");
									InfernalEffects.playErrorSound(player);
									break ap;
								}
								playerData.modifyStrength(1);
								break;
							case 2:
								if (!playerData.getPlayerSuperClass().equalsIgnoreCase("Archer")) {
									player.sendMessage(ChatColor.RED + "Adding this stat will not improve your abilities at all!");
									InfernalEffects.playErrorSound(player);
									break ap;
								}
								playerData.modifyDexterity(1);
								break;
							case 3:
								if (!playerData.getPlayerSuperClass().equalsIgnoreCase("Magician")) {
									player.sendMessage(ChatColor.RED + "Adding this stat will not improve your abilities at all!");
									InfernalEffects.playErrorSound(player);
									break ap;
								}
								playerData.modifyIntelligence(1);
								break;
							case 4:
								playerData.modifyAgility(1);
								break;
							case 5:
								playerData.modifyStamina(1);
								break;
							case 6:
								playerData.modifySpirit(1);
								break;
							}
							player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 0.5F, 8F);
							playerData.modifyAP(-1);
							StatGUI.open(player);
						}
					}
				}
			} else if (inventory.getTitle().equals("Please select your character.")) {
				event.setCancelled(true);
				if (event.getRawSlot() < playerData.getCharacterSlots() && event.getRawSlot() >= 0) {
					PlayerData.SELECTED_CHARACTER.put(player.getName(), event.getRawSlot() + 1);
					TabList.setTabListType(player, TabListType.ROTATE);
					final PlayerData newPlayerData = PlayerData.getData(player);
					new BukkitRunnable() {

						public void run() {
							newPlayerData.refreshConfig();
							newPlayerData.setDirtyEquips(true);
							player.sendMessage(ChatColor.GREEN + "You have selected your Level " + newPlayerData.getLevel() + " "
									+ newPlayerData.getPlayerClass() + "!");
							new BukkitRunnable() {

								public void run() {
									Location location = newPlayerData.getLocation();
									location.getChunk().load();
									player.teleport(location);
									InventoryManager.setPlayerInventory(player);
									playerData.updateHealth();
									player.setHealth(newPlayerData.getStoredHealth());
									newPlayerData.updateParticles();
									if (Quest.checkQuestCompletion(player, QuestName.PATH_TO_ENEN)
											&& !Quest.checkQuestCompletion(player, QuestName.CHOOSE_YOUR_PATH)) {
										ChooseClassGUI.open(player);
									}
									new BukkitRunnable() {

										@Override
										public void run() {
											Quest.getNewQuests(player);
											playerData.refreshQuestMarkers();
										}

									}.runTaskLater(InfernalRealms.getPlugin(), 40L);
								}

							}.runTaskLater(InfernalRealms.getPlugin(), 1L);
						}

					}.runTaskLater(InfernalRealms.getPlugin(), 1L);
				}
			} else if (inventory.getTitle().contains(TraitShopkeeper.SELL_SHOP_TITLE)) {
				if (event.getRawSlot() == 8) {
					event.setCancelled(true);
					long sellPrice = ItemReader.getMoneyValue(inventory.getItem(8));
					if (sellPrice == 0) {
						player.sendMessage(ChatColor.RED + "You're not even offering me anything of value to buy!");
						InfernalEffects.playErrorSound(player);
					} else {
						boolean success = true;
						for (int i = 0; i < 8; i++) {
							if (ItemReader.isUnremovable(inventory.getItem(i))) {
								player.sendMessage(ChatColor.RED + "You can't offer me an unremovable item!");
								InfernalEffects.playErrorSound(player);
								success = false;
								break;
							}
						}
						if (success) {
							for (int i = 0; i < 8; i++) {
								inventory.remove(inventory.getItem(i));
							}
							playerData.modifyMoney(sellPrice);
							player.sendMessage(ChatColor.GREEN + "  + " + GeneralUtil.getMoneyAsString(sellPrice));
							InfernalEffects.playSellSound(player);
							player.closeInventory();
							player.sendMessage(ChatColor.GREEN + "Thank you! Come again soon!");
						}
					}
				} else {

					new BukkitRunnable() {

						public void run() {
							long totalSellPrice = 0;
							for (int i = 0; i < 8; i++) {
								ItemStack item = inventory.getItem(i);
								if (item != null) {
									totalSellPrice += ItemReader.getMoneyValue(item);
								}
							}
							ItemStack button = inventory.getItem(8);
							ItemMeta buttonMeta = button.getItemMeta();
							buttonMeta.setLore(Arrays.asList(ChatColor.RESET + "" + ChatColor.GRAY + "amount and receive "
									+ ItemReader.getMoneyAsString(totalSellPrice)));
							button.setItemMeta(buttonMeta);
							inventory.setItem(8, button);
						}

					}.runTaskLater(InfernalRealms.getPlugin(), 5L);
				}
			} else if (inventory.getTitle().contains(TraitShopkeeper.BUY_SHOP_TITLE)) {
				event.setCancelled(true);
				if (!player.hasMetadata("CurrentShop")) {
					player.sendMessage(ChatColor.RED + "An error has occurred.");
					return;
				}

				String shop = (String) player.getMetadata("CurrentShop").get(0).value();
				TraitShopkeeper.processBuyShopClick(event, player, shop);
			} else if (inventory.getTitle().equals(TraitShopkeeper.CHOICES_SHOP_TITLE)) {
				TraitShopkeeper.processChoicesClick(event, player);
			} else if (inventory.getTitle().equals("Please select your class.")) {
				event.setCancelled(true);

				boolean selected = false;
				switch (event.getRawSlot())

				{
				case 10:
					if (playerData.getPlayerClass().equals(PlayerClass.BEGINNER.getName()))

					{
						playerData.setPlayerClass(PlayerClass.WARRIOR);
						new TextBar(player, 20, 80, 20, ChatColor.GOLD + "Path of the Warrior", " ");
						selected = true;
					}
					break;
				case 13:
					if (playerData.getPlayerClass().equals(PlayerClass.BEGINNER.getName()))

					{
						playerData.setPlayerClass(PlayerClass.ARCHER);
						new TextBar(player, 20, 80, 20, ChatColor.GOLD + "Path of the Archer", " ");
						selected = true;
					}
					break;
				case 16:
					if (playerData.getPlayerClass().equals(PlayerClass.BEGINNER.getName()))

					{
						playerData.setPlayerClass(PlayerClass.MAGICIAN);
						new TextBar(player, 20, 80, 20, ChatColor.GOLD + "Path of the Magician", " ");
						selected = true;
					}
					break;
				}
				if (selected) {
					player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_USE, 1F, 0F);
					player.closeInventory();
					playerData.processObjectiveChooseClass();
					InventoryManager.updateSkillIcons(player);
				}
			} else if (inventory.getTitle().contains("SP: ")) { // Skills GUI
				event.setCancelled(true);
				SkillsGUI.processClick(event.getRawSlot(), player, PlayerClass.fromString(playerData.getPlayerClass()));
			} else if (inventory.getTitle().equals("Trading...")) { // Trading GUI
				if (Trade.NO_INTERACT_SLOTS.contains(event.getRawSlot()) || event.getClick() == ClickType.DOUBLE_CLICK
						|| event.getClick() == ClickType.SHIFT_LEFT || event.getClick() == ClickType.SHIFT_RIGHT) {
					event.setCancelled(true);
				}
				if (player.hasMetadata("Trade")) {

					Trade trade = (Trade) player.getMetadata("Trade").get(0).value();
					boolean prompter = player.equals(trade.prompter);
					if (prompter)

					{
						if (Trade.TARGET_SLOTS.contains(event.getRawSlot())) {
							event.setCancelled(true);
						} else if (Trade.PROMPTER_SLOTS.contains(event.getRawSlot())
								&& (event.getCurrentItem() != null || event.getCursor() != null)) {
							if (!ItemReader.isTradeable(event.getCursor())) {
								event.setCancelled(true);
								player.sendMessage(ChatColor.RED + "This item cannot be traded.");
							}
							trade.cancelReadyTrade();
						}
					} else

					{
						if (Trade.PROMPTER_SLOTS.contains(event.getRawSlot())) {
							event.setCancelled(true);
						} else if (Trade.TARGET_SLOTS.contains(event.getRawSlot())
								&& (event.getCurrentItem() != null || event.getCursor() != null)) {
							if (!ItemReader.isTradeable(event.getCursor())) {
								event.setCancelled(true);
								player.sendMessage(ChatColor.RED + "This item cannot be traded.");
							}
							trade.cancelReadyTrade();
						}
					}
					if (event.getSlot() == Trade.COPPER_SLOT || event.getSlot() == Trade.SILVER_SLOT || event.getSlot() == Trade.GOLD_SLOT)

					{
						int increment = 0;
						switch (event.getRawSlot()) {
						case Trade.COPPER_SLOT:
							increment = 1;
							break;
						case Trade.SILVER_SLOT:
							increment = 100;
							break;
						case Trade.GOLD_SLOT:
							increment = 10000;
							break;
						}
						if (event.isShiftClick())
							increment *= 10;
						if (prompter)
							trade.modifyMoney(event.isLeftClick() ? increment : -increment, Trade.Trader.PROMPTER);
						else
							trade.modifyMoney(event.isLeftClick() ? increment : -increment, Trade.Trader.TARGET);
					}
					if (event.getRawSlot() == 46)

					{
						if (prompter)
							trade.readyPrompterTrade();
					} else if (event.getRawSlot() == 52)

					{
						if (!prompter)
							trade.readyTargetTrade();
					}
				} else {
					System.out.println("Error: Trade not found?");
				}
			} else if (inventory.getTitle().equals("Quest Log")) {
				event.setCancelled(true);

				List<Quest> activeQuests = playerData.getActiveQuests();
				if (event.getRawSlot() < activeQuests.size() && event.getRawSlot() >= 0) {
					QuestLogGUI.openDetails(player, activeQuests.get(event.getRawSlot()));
				}
			} else if (inventory.getTitle().contains("Quest Details: ")) {
				event.setCancelled(true);
				switch (event.getRawSlot()) {
				case 5:
					for (Iterator<String> it = event.getCurrentItem().getItemMeta().getLore().iterator(); it.hasNext();) {
						String line = it.next();
						if (!it.hasNext()) {
							// We're on the 'click here' line
							break;
						}
						player.sendMessage(line);
					}
					break;
				case 7:
					try {
						int questID = Integer.parseInt(inventory.getTitle().replace("Quest Details: ", ""));
						if (!QuestTabList.playerHasQuestIDOnHelper(player, questID)) {
							QuestTabList.addToHelper(player, questID);
							player.sendMessage(ChatColor.GREEN + "The quest has been added to your quest helper.");
							inventory.setItem(7, QuestLogGUI.questHelperButton(false));
						} else {
							QuestTabList.removeFromHelper(player, questID);
							player.sendMessage(ChatColor.YELLOW + "The quest has been removed from your quest helper.");
							inventory.setItem(7, QuestLogGUI.questHelperButton(true));
						}
						if (TabList.getTabListType(player) == TabListType.QUEST) {
							QuestTabList.sendTab(player);
						}
					} catch (Exception e) {
						System.out.println("An error occured while trying to add/remove a quest to the quest helper");
					}
					break;
				case 8:
					QuestLogGUI.openMenu(player, 1);
					break;
				}
			}
			if (event.getRawSlot() >= iv.getTopInventory().getSize()) {

				ItemStack clickedWith = iv.getCursor();
				ItemStack clickedOn = iv.getItem(event.getRawSlot());
				if (clickedWith.hasItemMeta() && clickedWith.getItemMeta().getDisplayName().contains("Gem of")
						&& clickedWith.getType() == Material.INK_SACK) {
					if (ItemReader.getRequiredLevel(clickedOn) >= ItemReader.getRequiredLevel(clickedWith)) {
						if (clickedOn.getType() != Material.PAPER && clickedOn.hasItemMeta()) {
							if (clickedWith.getItemMeta().getDisplayName().contains("Red")) {
								ItemMeta clickedOnMeta = clickedOn.getItemMeta();
								List<String> clickedOnLore = clickedOnMeta.getLore();
								List<String> clickedWithLore = clickedWith.getItemMeta().getLore();
								String statToAdd = "";
								for (String line : clickedWithLore) {
									if (line.contains("+")) {
										statToAdd = ChatColor.stripColor(line);
									}
								}
								int lineNumber = 0;
								search: {
									for (String line : clickedOnLore) {
										if (line.equals(
												ChatColor.RESET + "" + ChatColor.DARK_RED + " ▣ " + ChatColor.WHITE + "Red Socket")) {
											clickedOnLore.set(lineNumber,
													ChatColor.RESET + "" + ChatColor.DARK_RED + " ▣ " + ChatColor.GREEN + statToAdd);
											if (clickedWith.getAmount() <= 1) {
												iv.setCursor(new ItemStack(Material.AIR));
											} else {
												clickedWith.setAmount(clickedWith.getAmount() - 1);
												iv.setCursor(clickedWith);
											}
											event.setCancelled(true);
											// Effects
											player.playSound(player.getLocation(), Sound.ENTITY_SLIME_ATTACK, 1F, 1F);
											EffectsUtil.sendParticleToLocation(ParticleEffect.FIREWORKS_SPARK,
													player.getLocation().add(0, 0.5D, 0), 0.35F, 0.35F, 0.35F, 0F, 15);
											player.sendMessage(ChatColor.GREEN + "You have successfully added the socket to your item.");
											break search;
										}
										lineNumber++;
									}
								}
								clickedOnMeta.setLore(clickedOnLore);
								clickedOn.setItemMeta(clickedOnMeta);
							} else if (clickedWith.getItemMeta().getDisplayName().contains("Yellow")) {
								ItemMeta clickedOnMeta = clickedOn.getItemMeta();
								List<String> clickedOnLore = clickedOnMeta.getLore();
								List<String> clickedWithLore = clickedWith.getItemMeta().getLore();
								String statToAdd = "";
								for (String line : clickedWithLore) {
									if (line.contains("+")) {
										statToAdd = ChatColor.stripColor(line);
									}
								}
								int lineNumber = 0;
								search: {
									for (String line : clickedOnLore) {
										if (line.equals(
												ChatColor.RESET + "" + ChatColor.YELLOW + " ▣ " + ChatColor.WHITE + "Yellow Socket")) {
											clickedOnLore.set(lineNumber,
													ChatColor.RESET + "" + ChatColor.YELLOW + " ▣ " + ChatColor.GREEN + statToAdd);
											if (clickedWith.getAmount() <= 1) {
												iv.setCursor(new ItemStack(Material.AIR));
											} else {
												clickedWith.setAmount(clickedWith.getAmount() - 1);
												iv.setCursor(clickedWith);
											}
											event.setCancelled(true);
											// Effects
											player.playSound(player.getLocation(), Sound.ENTITY_SLIME_ATTACK, 1F, 1F);
											EffectsUtil.sendParticleToLocation(ParticleEffect.FIREWORKS_SPARK,
													player.getLocation().add(0, 0.5D, 0), 0.35F, 0.35F, 0.35F, 0F, 15);
											player.sendMessage(ChatColor.GREEN + "You have successfully added the socket to your item.");
											break search;
										}
										lineNumber++;
									}
								}
								clickedOnMeta.setLore(clickedOnLore);
								clickedOn.setItemMeta(clickedOnMeta);
							} else if (clickedWith.getItemMeta().getDisplayName().contains("Blue")) {
								ItemMeta clickedOnMeta = clickedOn.getItemMeta();
								List<String> clickedOnLore = clickedOnMeta.getLore();
								List<String> clickedWithLore = clickedWith.getItemMeta().getLore();
								String statToAdd = "";
								for (String line : clickedWithLore) {
									if (line.contains("+")) {
										statToAdd = ChatColor.stripColor(line);
									}
								}
								int lineNumber = 0;
								search: {
									for (String line : clickedOnLore) {
										if (line.equals(ChatColor.RESET + "" + ChatColor.BLUE + " ▣ " + ChatColor.WHITE + "Blue Socket")) {
											clickedOnLore.set(lineNumber,
													ChatColor.RESET + "" + ChatColor.BLUE + " ▣ " + ChatColor.GREEN + statToAdd);
											if (clickedWith.getAmount() <= 1) {
												iv.setCursor(new ItemStack(Material.AIR));
											} else {
												clickedWith.setAmount(clickedWith.getAmount() - 1);
												iv.setCursor(clickedWith);
											}
											event.setCancelled(true);
											// Effects
											player.playSound(player.getLocation(), Sound.ENTITY_SLIME_ATTACK, 1F, 1F);
											EffectsUtil.sendParticleToLocation(ParticleEffect.FIREWORKS_SPARK,
													player.getLocation().add(0, 0.5D, 0), 0.35F, 0.35F, 0.35F, 0F, 15);
											player.sendMessage(ChatColor.GREEN + "You have successfully added the socket to your item.");
											break search;
										}
										lineNumber++;
									}
								}
								clickedOnMeta.setLore(clickedOnLore);
								clickedOn.setItemMeta(clickedOnMeta);
							}
						}
					} else {
						if (clickedOn != null && clickedOn.getType() != Material.AIR) {
							player.sendMessage(ChatColor.RED + "You cannot use a higher level gem on a lower level equip.");
							InfernalEffects.playErrorSound(player);
						}
					}
				}

			}
		}
	}

	@EventHandler
	public void onInventoryDrag(InventoryDragEvent event) {
		if (event.getInventory() instanceof HorseInventory) {
			event.setCancelled(true);
			return;
		}
		for (int i : event.getRawSlots()) {
			if (event.getInventory().getType() == InventoryType.CRAFTING) {
				if (i < 5) {
					event.setCancelled(true);
				}
			}
			event.getView().setCursor(event.getOldCursor());
			InventoryClickEvent ice = new InventoryClickEvent(event.getView(), SlotType.CONTAINER, i, ClickType.LEFT,
					InventoryAction.PLACE_SOME);
			onInventoryClick(ice);
			if (ice.isCancelled()) {
				event.setCancelled(true);
				return;
			}
		}
	}

	public boolean buffer = false;

	@EventHandler
	public void onInventoryClose(InventoryCloseEvent event) {
		Inventory inventory = event.getInventory();
		if (event.getPlayer() instanceof Player) {
			Player player = (Player) event.getPlayer();
			if (player.hasMetadata("CurrentShop")) {
				player.removeMetadata("CurrentShop", InfernalRealms.getPlugin());
			}

			if (inventory.getTitle().equals("Trading...")) {
				if (player.hasMetadata("Trade")) {
					Trade trade = (Trade) player.getMetadata("Trade").get(0).value();
					trade.clearTrade();
				}
			} else if (inventory.getTitle().equals("Dungeon Confirmation")) {
				if (buffer) {
					return;
				}
				if (player.hasMetadata("SafeClose")) {
					player.removeMetadata("SafeClose", InfernalRealms.getPlugin());
					return;
				}
				Party party = Party.getParty(player);
				if (party == null) {
					return;
				}
				buffer = true;
				for (Player member : party.getAllOnlineMembers()) {
					member.closeInventory();
				}
				buffer = false;
				party.broadcastToParty(
						ChatColor.GRAY + player.getName() + " has " + ChatColor.RED + "cancelled" + ChatColor.GRAY + " the dungeon queue.");
			} else if (inventory.getTitle().equals("Please select your class.")) {
				new BukkitRunnable() {

					@Override
					public void run() {
						if (!Quest.checkQuestCompletion(player, QuestName.CHOOSE_YOUR_PATH)) {
							ChooseClassGUI.open(player);
							player.sendMessage(ChatColor.RED + "You cannot continue until you select a class.");
						}
					}
				}.runTaskLater(InfernalRealms.getPlugin(), 1L);

			}
		}
	}

	@EventHandler
	public void onInventoryOpen(InventoryOpenEvent event) {
		Inventory inventory = event.getInventory();
		if (event.getPlayer().hasMetadata("Trade")) {
			if (!inventory.getTitle().equals("Trading...")) {
				Trade trade = (Trade) event.getPlayer().getMetadata("Trade").get(0).value();
				trade.clearTrade();
			}
		}
	}

	@EventHandler
	public void onEntityHeal(EntityRegainHealthEvent event) {
		if (event.getEntity() instanceof Player) {
			Player player = (Player) event.getEntity();
			PlayerData playerData = PlayerData.getData(player);
			playerData.updateHealth();
			double regenAmount = event.getAmount();
			if (event.getRegainReason() == RegainReason.SATIATED || event.getRegainReason() == RegainReason.REGEN) {
				regenAmount = 1 + (playerData.getTotalStamina() * 0.4);
			}
			GeneralUtil.healPlayer(player, regenAmount);
			event.setCancelled(true);
		}
	}

	@EventHandler
	public void onEntityTakeDamage(EntityDamageEvent event) {
		if (event.getEntity() instanceof LivingEntity) {
			LivingEntity le = (LivingEntity) event.getEntity();
			try {
				for (ItemStack item : le.getEquipment().getArmorContents()) {
					if (item != null) {
						new BukkitRunnable() {

							@Override
							public void run() {
								item.setDurability((short) 0);
							}
						}.runTaskLater(InfernalRealms.getPlugin(), 1L);
					}
				}
			} catch (NullPointerException e) {}
			if (event.getEntity() instanceof Player) {
				Player player = (Player) event.getEntity();
				if (event.getCause() == DamageCause.FALL) {
					double fallHeight = event.getDamage() + 3;
					double dmgMult = fallHeight * 0.014;	// falling 70ish blocks will kill you
					double dmgValue = Math.abs(player.getMaxHealth() * 0.03 - dmgMult * player.getMaxHealth());
					player.sendMessage("You took " + dmgValue + " out of " + player.getMaxHealth() + " height is " + fallHeight);
					if (fallHeight > 5.0) { // no damage if the fall is 5 blocks and under
						event.setDamage(dmgValue);
					} else {
						event.setCancelled(true);
					}
				}
				Stat.refreshHealthNumber(player);
			}
		}
	}

	@EventHandler
	public void onItemBreak(PlayerItemBreakEvent event) {
		Player player = event.getPlayer();
		ItemStack brokenItem = event.getBrokenItem();
		for (ItemStack i : player.getInventory().getArmorContents()) {
			if (brokenItem.equals(i)) {
				// Broken item is armor.
				return;
			}
		}
		brokenItem.setAmount(1);
		player.setItemInHand(brokenItem);
	}

	@EventHandler
	public void onPlayerPickupItem(PlayerPickupItemEvent event) {
		Item item = event.getItem();
		if (item.getItemStack().getType() == Material.ARROW) {
			event.setCancelled(true);
		}
		if (item.hasMetadata("OwnedItem")) { // Item Protection
			String itemOwnerName = (String) item.getMetadata("OwnedItem").get(0).value();
			if (!event.getPlayer().getName().equals(itemOwnerName)) {
				event.setCancelled(true);
			}
		}
	}

	@EventHandler
	public void onMobDeath(EntityDeathEvent event) {
		event.getDrops().clear();
		event.setDroppedExp(0);
		EntityLiving nmsEntity = ((CraftLivingEntity) event.getEntity()).getHandle();
		if (nmsEntity instanceof InfernalMob) {
			InfernalMob customMob = (InfernalMob) nmsEntity;
			double multiplier = InfernalRealms.DROP_MULTIPLIER;
			Player player = event.getEntity().getKiller();
			if (player == null) {
				return;
			}
			multiplier *= PlayerData.getData(player).getDropMultiplier();
			// Drops
			Map<String, Double> drops = customMob.getData().getDrops();
			for (Map.Entry<String, Double> entry : drops.entrySet()) {
				String item = entry.getKey();
				double dropChance = entry.getValue() * multiplier;
				if (new Random().nextDouble() <= dropChance) {
					Location dropLocation = new Location(Bukkit.getWorld(nmsEntity.world.getWorld().getName()), nmsEntity.locX,
							nmsEntity.locY, nmsEntity.locZ);
					if (item.contains("ITEM ")) {
						String[] itemSplit = item.split(" ");
						ItemStack itemDrop = InfernalItems.generateCustomItem(itemSplit[2]);
						event.getEntity().getWorld().dropItemNaturally(dropLocation, itemDrop);
					} else if (item.contains("TIER ")) {
						String[] itemSplit = item.split(" ")[2].split("#");
						ItemStack itemDrop = null;
						try {
							itemDrop = InfernalItemsRELOADED.generateTierItem(itemSplit, customMob);
						} catch (InvalidTraitException e) {
							e.printStackTrace();
							return;
						}
						event.getEntity().getWorld().dropItemNaturally(dropLocation, itemDrop);
					} else if (item.contains("GEM ")) {
						String[] itemSplit = item.split(" ")[2].split("#");
						ItemStack itemDrop = null;
						try {
							itemDrop = InfernalItemsRELOADED.generateGem(itemSplit, customMob);
						} catch (InvalidTraitException e) {
							e.printStackTrace();
							return;
						}
						event.getEntity().getWorld().dropItemNaturally(dropLocation, itemDrop);
					} else if (item.contains("MISC ")) {
						String[] itemSplit = item.split(" ");
						ItemStack itemDrop = InfernalItems.generateMiscItem(itemSplit[2], 1);
						event.getEntity().getWorld().dropItemNaturally(dropLocation, itemDrop);
					} else if (item.contains("POTION ")) {
						String[] itemSplit = item.split(" ")[2].split("#");
						String[] amounts = itemSplit[1].split("-");
						int min = Integer.parseInt(amounts[0]);
						int max = Integer.parseInt(amounts[1]);
						ItemStack itemDrop = InfernalItems.generatePotion(itemSplit[0].replaceAll("_", " "),
								random.nextInt(max - min + 1) + min);
						event.getEntity().getWorld().dropItemNaturally(dropLocation, itemDrop);
					} else if (item.contains("POUCH ")) {
						String[] itemSplit = item.split(" ");
						ItemStack itemDrop = Pouch.fromString(itemSplit[2]).generateItemStack();
						event.getEntity().getWorld().dropItemNaturally(dropLocation, itemDrop);
					}
				}
			}
		}
	}

	@EventHandler
	public void onEntityTakeDamageByEntity(EntityDamageByEntityEvent event) {
		// Stop any vanilla damage reduction modifiers & catch these exceptions because it is set up horribly...
		try {
			event.setDamage(DamageModifier.ABSORPTION, 0);
		} catch (UnsupportedOperationException e) {}
		try {
			event.setDamage(DamageModifier.ARMOR, 0);
		} catch (UnsupportedOperationException e) {}
		try {
			event.setDamage(DamageModifier.MAGIC, 0);
		} catch (UnsupportedOperationException e) {}
		try {
			event.setDamage(DamageModifier.RESISTANCE, 0);
		} catch (UnsupportedOperationException e) {}
		try {
			event.setDamage(DamageModifier.BLOCKING, 0);
		} catch (UnsupportedOperationException e) {}
		try {
			event.setDamage(DamageModifier.HARD_HAT, 0);
		} catch (UnsupportedOperationException e) {}

		if (event.getEntity() instanceof Player) { // When player is attacked
			Player player = (Player) event.getEntity();
			MountManager.cancelSummoning(player); // Cancel mount summoning if it is in progress.
			if (player.getVehicle() != null) {
				player.getVehicle().remove();
			}
			PlayerData playerData = PlayerData.getData(player);
			double damage = 0;
			Entity attacker = event.getDamager();
			if (attacker instanceof Projectile) {
				Projectile projectile = (Projectile) attacker;
				if (projectile.getShooter() instanceof Entity) {
					attacker = (Entity) projectile.getShooter(); // Redefine attacker as shooter
				}
			}
			if (((CraftEntity) attacker).getHandle() instanceof InfernalMob) { // When attacker is custom mob
				InfernalMob im = (InfernalMob) ((CraftEntity) attacker).getHandle();
				player.setNoDamageTicks(0);
				damage += im.getData().getDamage(); // Base Damage
				double armor = playerData.getTotalArmor();
				double damagePercTaken = 1 - ((3 * armor) / (35 * im.getData().getLevel() + armor)); // 1 - ((3*armor) / (35*mobLevel + armor))
				if (damagePercTaken < 0.2) {
					damagePercTaken = 0.2;
				}
				damage *= damagePercTaken;
				if (attacker.hasMetadata("Weakness")) {
					double damageMultiplier = 1 - (double) attacker.getMetadata("Weakness").get(0).value();
					damage *= damageMultiplier;
				}
			}
			// Add more damage modifiers here.
			if (damage <= 0) {
				event.setDamage(DamageModifier.BASE, 1.0);
			} else {
				event.setDamage(DamageModifier.BASE, damage);
			}
		} else if (event.getEntity() instanceof ArmorStand) {
			((ArmorStand) event.getEntity()).setVisible(true);
			Player player;
			if (event.getDamager() instanceof Player) {
				player = (Player) event.getDamager();
			} else if (event.getDamager() instanceof Projectile && ((Projectile) event.getDamager()).getShooter() instanceof Player) {
				player = (Player) ((Projectile) event.getDamager()).getShooter();
			} else {
				return;
			}
			List<Entity> mobs = GeneralUtil.getNearbyEntities(event.getEntity().getLocation(), 0.1, 0.1, 0.1);
			if (mobs.size() == 1) {
				net.minecraft.server.v1_9_R1.Entity entity = ((CraftEntity) mobs.get(0)).getHandle();
				if (entity instanceof InfernalMob) {
					InfernalMob mob = (InfernalMob) entity;
					mob.getSelf().damageEntity(DamageSource.playerAttack(((CraftPlayer) player).getHandle()), (float) event.getDamage());
				}
			}
		} else if (event.getEntity() instanceof Horse) {
			if (event.getDamager() instanceof Player) {
				event.setCancelled(true);
			}
		}
		if (event.getDamager() instanceof LivingEntity) {
			LivingEntity mob = (LivingEntity) event.getDamager();
			if (((CraftLivingEntity) mob).getHandle() instanceof InfernalMob)
				if (SpawnManager.isOutOfRange((InfernalMob) ((CraftLivingEntity) mob).getHandle())) {
					SpawnManager.resetMob((InfernalMob) ((CraftLivingEntity) mob).getHandle());
				}
		}

	}

	@EventHandler
	public void onPlayerHeldItemChange(final PlayerItemHeldEvent event) {
		Player player = event.getPlayer();
		if (player.getGameMode() != GameMode.CREATIVE) {
			ItemStack item = player.getInventory().getItem(event.getNewSlot());
			if (ItemReader.isPotion(item)) {
				int[] hpValues = ItemReader.getPotionValue(item, "health");
				int[] mpValues = ItemReader.getPotionValue(item, "mana");
				boolean used = false;
				PlayerData playerData = PlayerData.getData(player);
				potion: {
					if (hpValues != null) {
						if (((Damageable) player).getHealth() != ((Damageable) player).getMaxHealth()) {
							if (playerData.hasHealthBuffer()) {
								player.sendMessage(ChatColor.RED + "You can only use a health potion once every 10 seconds!");
								break potion;
							}
							playerData.applyHealthBuffer();
							used = true;
							if (hpValues.length == 1) {
								double newHealth = ((Damageable) player).getHealth() + hpValues[0];
								newHealth = newHealth > ((Damageable) player).getMaxHealth() ? ((Damageable) player).getMaxHealth()
										: newHealth;
								if (player.getHealth() > 0) {
									player.setHealth(newHealth);
								}
							} else {
								double healAmount = hpValues[0];
								final double healPerTick = healAmount / hpValues[1];
								new BukkitRunnable() {
									int count = hpValues[1];

									public void run() {
										double newHealth = ((Damageable) player).getHealth() + healPerTick;
										newHealth = newHealth > ((Damageable) player).getMaxHealth() ? ((Damageable) player).getMaxHealth()
												: newHealth;
										player.setHealth(newHealth);

										count--;
										if (count <= 0) {
											cancel();
										}
									}

								}.runTaskTimer(InfernalRealms.getPlugin(), 0L, 20L);
							}
						}
					}
					if (mpValues != null) {
						if (playerData.getMana() != playerData.getTotalMaxMana()) {
							if (playerData.hasManaBuffer()) {
								player.sendMessage(ChatColor.RED + "You can only use a mana potion once every 10 seconds!");
								break potion;
							}
							playerData.applyManaBuffer();
							used = true;
							if (mpValues.length == 1) {
								int newMana = playerData.getMana() + mpValues[0];
								playerData.setMana(newMana);
							} else {
								double manaAmount = mpValues[0];
								final double manaPerTick = manaAmount / mpValues[1];
								new BukkitRunnable() {
									int count = mpValues[1];

									public void run() {
										double newMana = playerData.getMana() + manaPerTick;
										playerData.setMana((int) newMana);

										count--;
										if (count <= 0) {
											cancel();
										}
									}

								}.runTaskTimer(InfernalRealms.getPlugin(), 0L, 20L);
							}
						}
					}
				}
				if (used) {
					player.playSound(player.getLocation(), Sound.ENTITY_GENERIC_DRINK, 1L, 1L);
					if (!GeneralUtil.takeOne(player.getInventory().getItem(event.getNewSlot()))) {
						player.getInventory().setItem(event.getNewSlot(), null);
					}
				}
			}

			if (event.getNewSlot() > 0 && event.getNewSlot() < 4) { // Skills

				PlayerData playerData = PlayerData.getData(player);
				switch (playerData.getPlayerSuperClass()) {
				case "Archer":
					switch (event.getNewSlot())

					{
					case 1:
						new MagnumShot(player).activate();
						break;
					case 2:
						new VenomousArrow(player).activate();
						break;
					case 3:
						new Haste(player).activate();
						break;
					}
					break;
				case "Magician":
					switch (event.getNewSlot())

					{
					case 1:
						new Levitate(player).activate();
						break;
					case 2:
						new Pyroblast(player).activate();
						break;
					case 3:
						new Frostbite(player).activate();
						break;
					}
					break;
				case "Warrior":
					switch (event.getNewSlot())

					{
					case 1:
						new Charge(player).activate();
						break;
					case 2:
						new Rumble(player).activate();
						break;
					case 3:
						new Leech(player).activate();
						break;
					}
					break;

				}
				MountManager.cancelSummoning(player); // Cancel mount summoning if it is in progress.
				if (player.getVehicle() != null) {
					player.getVehicle().remove();
				}
			}

			if (!ItemReader.isHoldable(item)) {
				player.getInventory().setHeldItemSlot(0);
			}
		}
	}

	@EventHandler
	public void onPlayerDismountVehicle(VehicleExitEvent event) {
		if (event.getVehicle() instanceof Horse) {
			Horse horse = (Horse) event.getVehicle();
			horse.setHealth(0d);
			horse.remove();
		}
	}

	@EventHandler
	public void onRightClickNPC(NPCRightClickEvent event) {
		Player player = event.getClicker();
		PlayerData playerData = PlayerData.getData(player);
		String npcName = event.getNPC().getName();
		if (npcName.equals("Zen of Irion")) {
			if (Quest.checkQuestCompletion(player, QuestName.ZENS_TASK)) {
				player.teleport(new Location(InfernalRealms.MAIN_WORLD, 881, 131, -422, -180F, 4.2F));
				player.sendMessage(ChatColor.GRAY + "" + ChatColor.ITALIC + "The Zen has sent you into battle once more.");
				return;
			}
		} else if (npcName.equals("Ramsey")) {
			player.teleport(new Location(InfernalRealms.SECONDARY_WORLD, 3589.5, 47.5, 3861.5, 180F, 0F));
			player.sendMessage(ChatColor.GRAY + "" + ChatColor.ITALIC + "You are brought to the entrance of the cave towards Gorwin.");
			return;
		} else if (npcName.equals(ChatColor.RESET + "Ramsey")) {
			player.teleport(new Location(InfernalRealms.MAIN_WORLD, 447.5, 71.5, 435.5, -90F, 0F));
			player.sendMessage(ChatColor.GRAY + "" + ChatColor.ITALIC + "You are brought to the exit of the cave from Gorwin.");
			return;
		} else if (npcName.equals("Theophilus")) {
			if (Quest.checkQuestCompletion(player, QuestName.LICH_KING)) {
				player.teleport(new Location(InfernalRealms.SECONDARY_WORLD, 3378.5, 99, 3356.5, 90F, 0F));
				return;
			}
		} else if (npcName.equals("Focis")) {
			player.teleport(new Location(InfernalRealms.SECONDARY_WORLD, 3383.5, 99, 3356.5, -90F, 0F));
			return;
		} else if (npcName.equals("Bludwan")) {
			if (Quest.checkQuestCompletion(player, QuestName.THROUGH_THE_GATE)) {
				player.teleport(new Location(InfernalRealms.MAIN_WORLD, 281, 100, 634, 135F, 0F));
				return;
			}
		} else if (npcName.equals(ChatColor.RESET + "Gate Guard")) {
			player.teleport(new Location(InfernalRealms.MAIN_WORLD, 294, 100, 645, -44F, 0F));
			return;
		}
		if (player.hasMetadata("Conversation")) {
			return;
		}
		// Conversations stop actions below here
		ObjectiveTalk obj = playerData.processObjectiveTalk(event.getNPC());
		if (obj != null) {
			PlayerData.setConversation(player, true);
			obj.sendMessages(player);
		}
	}

	private static final Map<Player, ItemStack> dropAttempt = new HashMap<>();

	@EventHandler
	public void onItemDrop(PlayerDropItemEvent event) {
		if (event.getItemDrop().getItemStack().hasItemMeta()) {
			ItemStack item = event.getItemDrop().getItemStack();
			if (item.getItemMeta().getDisplayName().contains(PlayerClass.SKILL_INDICATOR)) {
				event.setCancelled(true);
			} else if (!ItemReader.isTradeable(item)) {
				if (ItemReader.isUnremovable(item)) {
					event.setCancelled(true);
					event.getPlayer().sendMessage(ChatColor.RED + "You cannot drop this item.");
					return;
				}
				boolean dropQueue = true;
				if (dropAttempt.containsKey(event.getPlayer())) {
					if (dropAttempt.get(event.getPlayer()).equals(item)) {
						event.getItemDrop().remove();
						dropAttempt.remove(event.getPlayer());
						dropQueue = false;
					}
				}
				if (dropQueue) {
					event.getPlayer().sendMessage(ChatColor.RED
							+ "You cannot drop this item as it is untradeable. If you attempt to drop it again in the next 5 seconds, it will be removed from your inventory but it will not be dropped.");
					event.setCancelled(true);
					dropAttempt.put(event.getPlayer(), item);
					new BukkitRunnable() {

						@Override
						public void run() {
							dropAttempt.remove(event.getPlayer());
						}
					}.runTaskLater(InfernalRealms.getPlugin(), 100L);
				}
			}
		}
	}

	@EventHandler
	public void onFoodLevelChange(FoodLevelChangeEvent event) {
		event.setCancelled(true);
	}

	@SuppressWarnings("deprecation")
	@EventHandler
	public void onProjectileLaunch(ProjectileLaunchEvent event) {
		if (event.getEntity().getShooter() instanceof Player) {
			Player player = (Player) event.getEntity().getShooter();
			if (player.getInventory().getHeldItemSlot() == 3 && event.getEntityType() == EntityType.SNOWBALL) {
				event.setCancelled(true);
				new BukkitRunnable() {
					@Override
					public void run() {
						player.getInventory().setItem(3, new Frostbite(player).getIcon());
					}
				}.runTaskLater(InfernalRealms.getPlugin(), 1L);
			}
		}
	}

	public static boolean hasRecentlyShotArrow(Player player) {
		return player.hasMetadata("ArrowCooldown");
	}

	public static void setRecentlyShotArrow(final Player player) {
		if (hasRecentlyShotArrow(player)) {
			return;
		}
		player.setMetadata("ArrowCooldown", new FixedMetadataValue(InfernalRealms.getPlugin(), true));
		new BukkitRunnable() {

			@Override
			public void run() {
				if (player.isOnline()) {
					player.removeMetadata("ArrowCooldown", InfernalRealms.getPlugin());
				}
			}
		}.runTaskLater(InfernalRealms.getPlugin(), 15L);
	}

	@EventHandler
	public void onChunkLoad(ChunkLoadEvent event) {
		Chunk chunk = event.getChunk();
		for (Entity e : chunk.getEntities()) {
			if (e instanceof LivingEntity) {
				if (e instanceof HumanEntity) {
					continue;
				}
				LivingEntity le = (LivingEntity) e;
				if (((CraftLivingEntity) le).getHandle() instanceof InfernalMob) {
					InfernalMob mob = (InfernalMob) ((CraftLivingEntity) le).getHandle();
					if (mob.getData().getSpawn() == null) {
						continue;
					}
					LivingEntity spawnPointMob = SpawnManager.getSpawnPointMobs().get(mob.getData().getSpawn());
					if (spawnPointMob == null || !spawnPointMob.equals(le)) {
						mob.onDeath();
						le.remove();
					}
				}
			}
		}
	}

	public static final List<BukkitRunnable> REGEN_BLOCKS = new ArrayList<>();

	@EventHandler
	public void onBlockBreak(BlockBreakEvent event) {
		if (event.getPlayer().getGameMode() == GameMode.SURVIVAL) {
			event.setCancelled(true);
		}
		if (event.getPlayer().getGameMode() == GameMode.CREATIVE) {
			// Don't mess with anything for builders
			return;
		}
		if (HomesteadUtils.isInHomestead(event.getPlayer())) {
			// Don't mess with them here
			return;
		}

		// Prevent Durability Decay
		new BukkitRunnable() {

			@Override
			public void run() {
				if (event.getPlayer().getItemInHand() != null) {
					event.getPlayer().getItemInHand().setDurability((short) 0);
					event.getPlayer().updateInventory();
				}
			}
		}.runTaskLater(InfernalRealms.getPlugin(), 1L);

		Block block = event.getBlock();
		block.getDrops().clear();
		FileConfiguration config = YAMLFile.CUSTOM_BLOCKS.getConfig();
		byte data = (byte) (block.getType() == Material.LEAVES || block.getType() == Material.LEAVES_2 ? block.getData() % 4
				: block.getData());
		for (String key : config.getKeys(true)) {
			if (!key.contains(block.getTypeId() + "#" + data)) {
				continue;
			}
			List<String> regions = config.getStringList(key + ".Regions");
			if (regions == null) {
				continue;
			}
			for (String regionName : regions) {
				ProtectedRegion region = InfernalRealms.getWorldGuardPlugin().getRegionManager(block.getWorld()).getRegion(regionName);
				if (region == null || !region.contains(block.getLocation().getBlockX(), block.getLocation().getBlockY(),
						block.getLocation().getBlockZ())) {
					continue;
				}
				int amount;
				int respawnTime;
				try {
					String[] dropQuantityRange = config.getString(key + ".Amount").split("-");
					int min = Integer.parseInt(dropQuantityRange[0]);
					int max = Integer.parseInt(dropQuantityRange[1]);
					amount = min + random.nextInt((max - min) + 1);
					respawnTime = config.getInt(key + ".Respawn");
				} catch (Exception e) {
					System.out.println("There may be an error in customblocks.yml #1 @ " + key);
					return;
				}

				if (config.getString(key + ".Drop") != null) {
					ItemStack drop;
					try {
						drop = InfernalItems.generateMiscItem(config.getString(key + ".Drop"), amount);
					} catch (NullPointerException e) {
						System.out.println("There may be an error in customblocks.yml #2 @ " + key);
						return;
					}
					block.getWorld().dropItemNaturally(block.getLocation(), drop);
				}

				Material oldMaterial = block.getType();
				byte oldData = block.getData();
				block.setType(Material.AIR);
				PlayerData playerData = PlayerData.getData(event.getPlayer());
				String blockName;
				try {
					blockName = key.split("\\.")[2];
				} catch (Exception e) {
					System.out.println("There may be an error in customblocks.yml #3 @ " + key);
					return;
				}
				playerData.processObjectiveBreakBlock(blockName);
				BukkitRunnable runnable = new BukkitRunnable() {
					@Override
					public void run() {
						block.setType(oldMaterial);
						block.setData(oldData);
					}
				};
				runnable.runTaskLater(InfernalRealms.getPlugin(), 20 * respawnTime);
				new BukkitRunnable() {
					@Override
					public void run() {
						REGEN_BLOCKS.remove(runnable);
					}
				}.runTaskLater(InfernalRealms.getPlugin(), 20 * respawnTime);
				REGEN_BLOCKS.add(runnable);
				return;
			}
		}
	}

	@EventHandler
	public void onBlockPlace(BlockPlaceEvent event) {
		System.out.println("TEST");
		if (event.getPlayer().getGameMode() == GameMode.CREATIVE) {
			// Don't mess with them.
			return;
		}
		if (HomesteadUtils.isInHomestead(event.getPlayer())) {
			// Don't block quite yet, at least
			return;
		}
		event.setCancelled(true);
	}

}
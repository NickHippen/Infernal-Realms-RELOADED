package net.infernalrealms.mount;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.v1_9_R1.entity.CraftHorse;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Horse.Color;
import org.bukkit.entity.Horse.Style;
import org.bukkit.entity.Horse.Variant;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import de.inventivegames.particle.ParticleEffect;
import net.infernalrealms.general.InfernalRealms;
import net.infernalrealms.gui.MountGUI;
import net.infernalrealms.player.PlayerData;
import net.infernalrealms.util.EffectsUtil;
import net.minecraft.server.v1_9_R1.AttributeInstance;
import net.minecraft.server.v1_9_R1.EntityInsentient;
import net.minecraft.server.v1_9_R1.GenericAttributes;

public class MountManager {

	public static final Color[] COLOR_ORDER = new Color[] { Color.BROWN, Color.CHESTNUT, Color.CREAMY, Color.DARK_BROWN, Color.GRAY,
			Color.BLACK, Color.WHITE };
	public static final Size[] SIZE_ORDER = new Size[] { Size.NORMAL, Size.TINY };
	public static final Style[] STYLE_ORDER = new Style[] { Style.NONE, Style.BLACK_DOTS, Style.WHITE, Style.WHITE_DOTS, Style.WHITEFIELD };
	public static final Variant[] VARIANT_ORDER = new Variant[] { Variant.HORSE, Variant.UNDEAD_HORSE, Variant.SKELETON_HORSE,
			Variant.DONKEY };

	public static void beginSummoning(Player player) {
		if (player.hasMetadata("MountSummoning")) {
			return;
		}
		PlayerData playerData = PlayerData.getData(player);
		if (playerData.hasDungeon()) {
			player.sendMessage(ChatColor.RED + "Mounts are not permitted in dungeons.");
			return;
		}
		BukkitRunnable summoning = new BukkitRunnable() {
			int secondsPassed = 1;

			@Override
			public void run() {
				if (secondsPassed == 4) { // Summon Mount
					spawnMount(player);
					cancel();
					player.removeMetadata("MountSummoning", InfernalRealms.getPlugin());
					return;
				}
				player.sendMessage(ChatColor.BLUE + "...Summoning...(" + ChatColor.BOLD + secondsPassed + ChatColor.BLUE + ")");
				player.playSound(player.getLocation(), Sound.BLOCK_FIRE_EXTINGUISH, 0.5F, 2F);
				EffectsUtil.sendParticleToLocation(ParticleEffect.SMOKE_NORMAL, player.getLocation().add(0, 0.5, 0), 1F, 0.5F, 1F, 0.05F,
						10);
				secondsPassed++;
			}
		};
		summoning.runTaskTimer(InfernalRealms.getPlugin(), 0L, 20L);
		player.setMetadata("MountSummoning", new FixedMetadataValue(InfernalRealms.getPlugin(), summoning));
	}

	public static boolean isSurroundingSpaceSafe(Player player) {
		for (int y = 0; y <= 1; y++) {
			for (int x = -1; x <= 1; x++) {
				for (int z = -1; z <= 1; z++) {
					Location loc = player.getLocation();
					loc.add(new Vector(x, y, z));
					if (loc.getBlock().getType().isSolid() && !isSlab(loc.getBlock().getType())) {
						return false;
					}
				}
			}
		}
		return true;
	}

	private static boolean isSlab(Material material) {
		return material == Material.STEP || material == Material.WOOD_STEP || material == Material.STONE_SLAB2;
	}

	public static void cancelSummoning(Player player) {
		if (!player.hasMetadata("MountSummoning")) {
			return;
		}
		((BukkitRunnable) player.getMetadata("MountSummoning").get(0).value()).cancel();
		player.removeMetadata("MountSummoning", InfernalRealms.getPlugin());
		player.sendMessage(ChatColor.BLUE + "...Summoning cancelled.");
	}

	public static void updateSaddle(Player player, Horse horse) {
		ItemStack saddle = horse.getInventory().getSaddle();
		ItemMeta saddleMeta = saddle.getItemMeta();
		saddleMeta.setLore(MountManager.getSaddleLore(player));
		saddle.setItemMeta(saddleMeta);
		horse.getInventory().setSaddle(saddle);
	}

	public static void spawnMount(Player player) {
		if (!isSurroundingSpaceSafe(player)) {
			player.sendMessage(ChatColor.RED + "Not enough space, summoning cancelled.");
			return;
		}
		player.playSound(player.getLocation(), Sound.ENTITY_HORSE_AMBIENT, 1F, 1F);
		PlayerData playerData = PlayerData.getData(player);
		playerData.processObjectiveSummonMount();
		Horse horse = (Horse) player.getWorld().spawnEntity(player.getLocation(), EntityType.HORSE);
		horse.setTamed(true);
		horse.setOwner(player);
		horse.setPassenger(player);
		horse.setBreed(false);
		horse.setCanPickupItems(false);
		horse.setColor(stringToHorseColor(playerData.getMountColor()));
		horse.setStyle(stringToHorseStyle(playerData.getMountStyle()));
		horse.setVariant(stringToHorseSkin(playerData.getMountVariant()));
		if (playerData.getMountSize().equals(Size.TINY.toString())) {
			horse.setBaby();
		} else {
			horse.setAdult();
		}
		horse.setAgeLock(true);
		ItemStack saddle = new ItemStack(Material.SADDLE, 1);
		ItemMeta saddleMeta = saddle.getItemMeta();
		saddleMeta.setDisplayName(ChatColor.RESET + "" + ChatColor.DARK_PURPLE + ChatColor.BOLD + player.getName() + "'s Mount");
		saddleMeta.setLore(getSaddleLore(player));
		saddle.setItemMeta(saddleMeta);
		horse.getInventory().setSaddle(saddle);
		updateHorseStats(player, horse);
		new HungerDecay(player, (CraftHorse) horse).runTaskTimer(InfernalRealms.getPlugin(), 1L, 100L);
	}

	public static void updateHorseStats(Player player, Horse horse) {
		updateHorseStats(player, horse, true);
	}

	public static void updateHorseStats(Player player, Horse horse, boolean updateInventory) {
		PlayerData playerData = PlayerData.getData(player);
		ItemStack armor = playerData.getMountArmor();
		if (updateInventory && armor != null) {
			horse.getInventory().setArmor(armor);
		}
		double speedMult = 1;
		double jumpMult = 1;
		if (armor != null && armor.hasItemMeta() && armor.getItemMeta().hasLore()) {
			for (String line : armor.getItemMeta().getLore()) {
				try {
					if (line.contains("Speed")) {
						speedMult *= 1D + (Double.parseDouble(line.replaceAll("[^\\d.]", "")) / 100D);
					} else if (line.contains("Jump")) {
						jumpMult *= 1D + (Double.parseDouble(line.replaceAll("[^\\d.]", "")) / 100D);
					}
				} catch (Exception e) {
					e.printStackTrace();
					continue;
				}
			}
		}
		CraftHorse craftHorse = (CraftHorse) horse;
		AttributeInstance speedAttribute = ((EntityInsentient) (craftHorse).getHandle())
				.getAttributeInstance(GenericAttributes.MOVEMENT_SPEED);
		speedAttribute.setValue(convertLevelToSpeed(playerData.getMountSpeed()) * speedMult);
		craftHorse.setJumpStrength(convertLevelToJump(playerData.getMountJump()) * jumpMult);
	}

	public static void upgradeSpeed(Player player) {
		PlayerData playerData = PlayerData.getData(player);
		if (playerData.getMountSpeed() >= 8) {
			return;
		}
		int apCost = getAPCostForNextLevel(playerData.getMountSpeed());
		if (playerData.getMountStatPoints() < apCost) {
			player.sendMessage(ChatColor.RED + "You do not have enough mount stat points.");
			return;
		}
		playerData.modifyMountSpeed(1);
		playerData.modifyMountStatPoints(-apCost);
		playerData.processObjectiveLevelMountSpeed();
	}

	public static void upgradeJump(Player player) {
		PlayerData playerData = PlayerData.getData(player);
		if (playerData.getMountJump() >= 8) {
			return;
		}
		int apCost = getAPCostForNextLevel(playerData.getMountJump());
		if (playerData.getMountStatPoints() < apCost) {
			player.sendMessage(ChatColor.RED + "You do not have enough mount stat points.");
			return;
		}
		playerData.modifyMountJump(1);
		playerData.modifyMountStatPoints(-apCost);
	}

	public static void upgradeHunger(Player player) {
		PlayerData playerData = PlayerData.getData(player);
		if (playerData.getMountMaxHungerLevel() >= 8) {
			return;
		}
		int apCost = getAPCostForNextLevel(playerData.getMountMaxHungerLevel());
		if (playerData.getMountStatPoints() < apCost) {
			player.sendMessage(ChatColor.RED + "You do not have enough mount stat points.");
			return;
		}
		playerData.modifyMountMaxHungerLevel(1);
		playerData.modifyMountStatPoints(-apCost);
	}

	public static int getAPCostForNextLevel(int level) {
		switch (level) {
		case 1:
			return 1;
		case 2:
			return 3;
		case 3:
			return 5;
		case 4:
			return 8;
		case 5:
			return 11;
		case 6:
			return 15;
		case 7:
			return 20;
		}
		return 0;
	}

	/**
	 * Level 1 = 0.17 <br>
	 * Level 2 = 0.2 <br>
	 * Level 3 = 0.22 <br>
	 * Level 4 = 0.24 <br>
	 * Level 5 = 0.25 <br>
	 * Level 6 = 0.26 <br>
	 * Level 7 = 0.27 <br>
	 * Level 8 = 0.29 <br>
	 * @param level
	 * @return the proper speed for the level
	 */
	public static double convertLevelToSpeed(int level) {
		switch (level) {
		case 1:
			return 0.17;
		case 2:
			return 0.2;
		case 3:
			return 0.22;
		case 4:
			return 0.24;
		case 5:
			return 0.25;
		case 6:
			return 0.26;
		case 7:
			return 0.27;
		case 8:
			return 0.29;
		default:
			return 0;
		}
	}

	/**
	 * Level 1 = 0.5 <br>
	 * Level 2 = 0.53 <br>
	 * Level 3 = 0.6 <br>
	 * Level 4 = 0.7 <br>
	 * Level 5 = 0.75 <br>
	 * Level 6 = 0.81 <br>
	 * Level 7 = 0.85 <br>
	 * Level 8 = 1 <br>
	 * @param level
	 * @return the proper jump for the level
	 */
	public static double convertLevelToJump(int level) {
		switch (level) {
		case 1:
			return 0.5;
		case 2:
			return 0.53;
		case 3:
			return 0.6;
		case 4:
			return 0.7;
		case 5:
			return 0.75;
		case 6:
			return 0.81;
		case 7:
			return 0.85;
		case 8:
			return 1;
		default:
			return 0;
		}
	}

	/**
	 * Level 1 = 100 <br>
	 * Level 2 = 125 <br>
	 * Level 3 = 150 <br>
	 * Level 4 = 175 <br>
	 * Level 5 = 200 <br>
	 * Level 6 = 225 <br>
	 * Level 7 = 250 <br>
	 * Level 8 = 300 <br>
	 * @param level
	 * @return the proper maximum hunger for the level
	 */
	public static int convertLevelToMaxHunger(int level) {
		if (level >= 8)
			return 300;
		return 100 + ((level - 1) * 25);
	}

	/**
	 * "Black", "Brown", "Chestnut", "Creamy", "Dark Brown", "Gray", "White"
	 * @param color
	 */
	public static Color stringToHorseColor(String color) {
		for (Color i : Color.values()) {
			if (color.replace(" ", "_").equalsIgnoreCase(i.toString()))
				return i;
		}
		return Color.BROWN;
	}

	/**
	 * "Black Dots", "None", "White", "White Dots", "Whitefield"
	 * @param style
	 */
	public static Style stringToHorseStyle(String style) {
		for (Style i : Style.values()) {
			if (style.replace(" ", "_").equalsIgnoreCase(i.toString()))
				return i;
		}
		return Style.NONE;
	}

	/**
	 * "Horse", "Donkey", "Mule", "Undead Horse", "Skeleton Horse"
	 * @param skin
	 */
	public static Variant stringToHorseSkin(String skin) {
		for (Variant i : Variant.values()) {
			if (skin.replace(" ", "_").equalsIgnoreCase(i.toString()))
				return i;
		}
		return Variant.HORSE;
	}

	public static List<String> getSaddleLore(Player player) {
		PlayerData playerData = PlayerData.getData(player);
		ArrayList<String> lore = new ArrayList<String>();
		lore.add(ChatColor.RESET + "" + ChatColor.GRAY + ChatColor.BOLD + "LEVEL: " + ChatColor.RESET + ChatColor.WHITE
				+ playerData.getMountLevel());
		lore.add(ChatColor.RESET + "" + ChatColor.GRAY + ChatColor.BOLD + "SPEED: " + ChatColor.RESET + ChatColor.WHITE
				+ playerData.getMountSpeed());
		lore.add(ChatColor.RESET + "" + ChatColor.GRAY + ChatColor.BOLD + "JUMP: " + ChatColor.RESET + ChatColor.WHITE
				+ playerData.getMountJump());
		lore.add(ChatColor.RESET + "" + ChatColor.GRAY + ChatColor.BOLD + "HUNGER: " + ChatColor.RESET + ChatColor.WHITE
				+ playerData.getMountHungerRounded() + " / " + convertLevelToMaxHunger(playerData.getMountMaxHungerLevel()));
		lore.add(" ");
		lore.add(ChatColor.RESET + "" + ChatColor.GRAY + ChatColor.BOLD + "COLOR: " + ChatColor.RESET + ChatColor.WHITE
				+ playerData.getMountColor());
		lore.add(ChatColor.RESET + "" + ChatColor.GRAY + ChatColor.BOLD + "STYLE: " + ChatColor.RESET + ChatColor.WHITE
				+ playerData.getMountStyle());
		lore.add(ChatColor.RESET + "" + ChatColor.GRAY + ChatColor.BOLD + "SKIN: " + ChatColor.RESET + ChatColor.WHITE
				+ playerData.getMountVariant());
		lore.add(ChatColor.RESET + "" + ChatColor.GRAY + ChatColor.BOLD + "SIZE: " + ChatColor.RESET + ChatColor.WHITE
				+ playerData.getMountSize());
		lore.add(" ");
		lore.add(ChatColor.RESET + "" + ChatColor.GRAY + ChatColor.BOLD + "EXP: " + ChatColor.GREEN
				+ (int) (((double) playerData.getMountExp() / playerData.getMountExpToNextLevel()) * 100) + "%");
		lore.add(ChatColor.RESET + "" + ChatColor.GRAY + ChatColor.BOLD + "STAT POINTS: " + ChatColor.GREEN
				+ playerData.getMountStatPoints());
		return lore;
	}

	public static void manageClick(InventoryClickEvent event) {
		event.setCancelled(true);
		Player player = (Player) event.getWhoClicked();
		PlayerData playerData = PlayerData.getData(player);
		// TODO Handle when they click on the level icon instead of the first one
		if (event.getRawSlot() == 0 || event.getRawSlot() == playerData.getMountSpeed()) {
			upgradeSpeed(player);
		} else if (event.getRawSlot() == 9 || event.getRawSlot() == playerData.getMountJump() + 9) {
			upgradeJump(player);
		} else if (event.getRawSlot() == 18 || event.getRawSlot() == playerData.getMountMaxHungerLevel() + 18) {
			upgradeHunger(player);
		} else if (event.getRawSlot() >= 27 && event.getRawSlot() <= 33) {
			// Colors
			Color color = COLOR_ORDER[event.getRawSlot() - 27];
			if (playerData.hasUnlockedMountColor(color)) {
				playerData.setMountColor(color.toString());
				player.sendMessage(
						ChatColor.DARK_GREEN + "" + ChatColor.BOLD + "Color: " + ChatColor.GREEN + color.toString().substring(0, 1)
								+ color.toString().substring(1).toLowerCase().replaceAll("_", " ") + " (Equipped)");
			}
		} else if (event.getRawSlot() >= 34 && event.getRawSlot() <= 35) {
			// Size
			Size size = SIZE_ORDER[event.getRawSlot() - 34];
			if (playerData.hasUnlockedMountSize(size)) {
				playerData.setMountSize(size.toString());
				player.sendMessage(ChatColor.DARK_GREEN + "" + ChatColor.BOLD + "Size: " + ChatColor.GREEN + size.toString().substring(0, 1)
						+ size.toString().substring(1).toLowerCase().replaceAll("_", " ") + " (Equipped)");
			}
		} else if (event.getRawSlot() >= 36 && event.getRawSlot() <= 40) {
			// Style
			Style style = STYLE_ORDER[event.getRawSlot() - 36];
			if (playerData.hasUnlockedMountStyle(style)) {
				playerData.setMountStyle(style.toString());
				player.sendMessage(
						ChatColor.DARK_GREEN + "" + ChatColor.BOLD + "Style: " + ChatColor.GREEN + style.toString().substring(0, 1)
								+ style.toString().substring(1).toLowerCase().replaceAll("_", " ") + " (Equipped)");
			}
		} else if (event.getRawSlot() >= 41 && event.getRawSlot() <= 44) {
			Variant variant = VARIANT_ORDER[event.getRawSlot() - 41];
			if (playerData.hasUnlockedMountVariant(variant)) {
				playerData.setMountVariant(variant.toString());
				player.sendMessage(
						ChatColor.DARK_GREEN + "" + ChatColor.BOLD + "Skin: " + ChatColor.GREEN + variant.toString().substring(0, 1)
								+ variant.toString().substring(1).toLowerCase().replaceAll("_", " ") + " (Equipped)");
			}
		}
		MountGUI.open(player);
	}
}

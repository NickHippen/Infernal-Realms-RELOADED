package net.infernalrealms.mining;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import de.inventivegames.particle.ParticleEffect;
import net.infernalrealms.general.GeneralListener;
import net.infernalrealms.general.InfernalRealms;
import net.infernalrealms.mining.Ore.OreItem;
import net.infernalrealms.player.PlayerData;
import net.infernalrealms.util.EffectsUtil;
import net.infernalrealms.util.GeneralUtil;
import net.infernalrealms.util.InfernalStrings;

public class PickaxeFactory {

	public static final int MAX_LEVEL = 100;
	private static final Material[] PICKAXE_MATERIAL = new Material[] { Material.WOOD_PICKAXE, Material.STONE_PICKAXE,
			Material.IRON_PICKAXE, Material.GOLD_PICKAXE, Material.DIAMOND_PICKAXE };

	private static final Random RANDOM = new Random();

	private PickaxeFactory() {}

	/**
	 * Gets the corresponding pickaxe material for the level
	 * @param level
	 * @return the corresponding pickaxe material
	 */
	public static Material getPickaxeTypeForLevel(int level) {
		if (level >= 100) {
			return Material.DIAMOND_PICKAXE;
		}
		return PICKAXE_MATERIAL[level / 20];
	}

	/**
	 * Generates a new profession pickaxe for the player
	 * @param player
	 * @return the generated pickaxe
	 */
	public static ItemStack getNewPickaxe(Player player) {
		PlayerData playerData = PlayerData.getData(player);
		int miningLevel = playerData.getMiningLevel();
		ItemStack pickaxe = new ItemStack(getPickaxeTypeForLevel(miningLevel), 1, (short) 0);
		ItemMeta pickaxeMeta = pickaxe.getItemMeta();
		pickaxeMeta.setDisplayName(InfernalStrings.HOLDABLE + ChatColor.DARK_PURPLE + ChatColor.BOLD + player.getName() + "'s Pickaxe");
		pickaxeMeta.setLore(getPickaxeLore(playerData));
		pickaxe.setItemMeta(pickaxeMeta);
		return pickaxe;
	}

	/**
	 * Generates the lore of the profession pickaxe for the player
	 * @param playerData
	 * @return the generated lore
	 */
	private static List<String> getPickaxeLore(PlayerData playerData) {
		ItemStack hilt = playerData.getMiningHilt();
		ItemStack binding = playerData.getMiningBinding();
		ItemStack reinforcement = playerData.getMiningReinforcement();

		List<String> pickaxeLore = new ArrayList<>();
		pickaxeLore.add(ChatColor.GRAY + "" + ChatColor.BOLD + "LEVEL: " + ChatColor.WHITE + playerData.getMiningLevel());
		pickaxeLore.add(ChatColor.GRAY + "" + ChatColor.BOLD + "SPEED: " + ChatColor.WHITE + playerData.getMiningSpeedLevel());
		pickaxeLore.add(ChatColor.GRAY + "" + ChatColor.BOLD + "LUCK: " + ChatColor.WHITE + playerData.getMiningLuckLevel());
		pickaxeLore.add(ChatColor.GRAY + "" + ChatColor.BOLD + "DURA.: " + ChatColor.WHITE + playerData.getMiningMaxDurabilityLevel());
		pickaxeLore.add(" ");
		pickaxeLore.add(ChatColor.GRAY + "" + ChatColor.BOLD + "HILT: " + ChatColor.WHITE
				+ (hilt == null ? "None" : hilt.getItemMeta().getDisplayName()));
		pickaxeLore.add(ChatColor.GRAY + "" + ChatColor.BOLD + "BINDING: " + ChatColor.WHITE
				+ (binding == null ? "None" : binding.getItemMeta().getDisplayName()));
		pickaxeLore.add(ChatColor.GRAY + "" + ChatColor.BOLD + "REINF.: " + ChatColor.WHITE
				+ (reinforcement == null ? "None" : reinforcement.getItemMeta().getDisplayName()));
		pickaxeLore.add(" ");
		pickaxeLore.add(ChatColor.GRAY + "" + ChatColor.BOLD + "CURRENT DURA.: " + ChatColor.WHITE
				+ String.format("%.0f",
						100D * ((double) playerData.getMiningCurrentDurability() / (double) playerData.getTotalMiningMaxDurability()))
				+ "%");
		pickaxeLore.add(ChatColor.GRAY + "" + ChatColor.BOLD + "EXP: " + ChatColor.DARK_PURPLE + ChatColor.BOLD + "["
				+ InfernalStrings.formatPercentageBar("||||||||||||||||||||||||||||||||||||||||||||||||||", ChatColor.DARK_PURPLE,
						ChatColor.GRAY, (double) playerData.getMiningExp() / (double) playerData.getMiningExpToNextLevel())
				+ ChatColor.DARK_PURPLE + ChatColor.BOLD + "]");
		pickaxeLore.add(InfernalStrings.UNTRADEABLE);
		return pickaxeLore;
	}

	/**
	 * Checks if the player has a mining profession pickaxe in their inventory
	 * @param player
	 * @return whether or not the pickaxe was found
	 */
	public static boolean hasPickaxeInInventory(Player player) {
		return getPickaxeInInventory(player) != null;
	}

	/**
	 * Gets the profession pickaxe in the player's inventory
	 * @param player
	 * @return the found pickaxe or null if not found
	 */
	public static ItemStack getPickaxeInInventory(Player player) {
		for (ItemStack item : player.getInventory().getContents()) {
			if (isProfessionPickaxe(item)) {
				return item;
			}
		}
		return null;
	}

	/**
	 * Updates the pickaxe in the player's inventory
	 * @param player
	 */
	public static void updatePickaxeInInventory(Player player) {
		new BukkitRunnable() {

			@Override
			public void run() {
				ItemStack pickaxe = getPickaxeInInventory(player);
				if (pickaxe == null) {
					return;
				}
				PlayerData playerData = PlayerData.getData(player);
				ItemMeta pickaxeMeta = pickaxe.getItemMeta();
				pickaxeMeta.setLore(getPickaxeLore(playerData));
				pickaxe.setItemMeta(pickaxeMeta);
				pickaxe.setType(getPickaxeTypeForLevel(playerData.getLevel()));
				updatePickaxeDurability(playerData, pickaxe);
			}
		}.runTaskLater(InfernalRealms.getPlugin(), 0L);
	}

	/**
	 * Updates the durability of the player's pickaxe. Assumes pickaxe is valid
	 * @param playerData
	 * @param pickaxe the valid pickaxe
	 */
	private static void updatePickaxeDurability(PlayerData playerData, ItemStack pickaxe) {
		float trueDuraPercent = (float) playerData.getMiningCurrentDurability() / (float) playerData.getTotalMiningMaxDurability();
		short maxDura = getMaxDurabilityOfPickMaterial(pickaxe.getType());
		pickaxe.setDurability((short) (maxDura - (trueDuraPercent * (float) maxDura)));
	}

	/**
	 * Gets the corresponding durability of the pickaxe material
	 * @param material the pickaxe material - assumed to be valid on passing
	 * @return the maximum durability for the corresponding material
	 */
	private static short getMaxDurabilityOfPickMaterial(Material material) {
		switch (material) {
		case WOOD_PICKAXE:
			return 59;
		case STONE_PICKAXE:
			return 131;
		case IRON_PICKAXE:
			return 250;
		case GOLD_PICKAXE:
			return 32;
		case DIAMOND_PICKAXE:
			return 1561;
		default:
			return 1;
		}
	}

	/**
	 * Checks if item is a profession pickaxe
	 * @param item
	 * @return whether or not the item is a profession pickaxe
	 */
	public static boolean isProfessionPickaxe(ItemStack item) {
		return item != null && isPickaxeMaterial(item.getType()) && item.getItemMeta().hasDisplayName()
				&& item.getItemMeta().getDisplayName().contains("'s Pickaxe");
	}

	/**
	 * Checks if the material is a pickaxe material
	 * @param material
	 * @return whether or not the material is pickaxe material
	 */
	public static boolean isPickaxeMaterial(Material material) {
		for (Material pickMaterial : PICKAXE_MATERIAL) {
			if (material == pickMaterial) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Gives a new profession pickaxe to the player if there is room in their inventory
	 * @param player
	 * @return whether or not there was room for the pickaxe
	 */
	public static boolean giveNewPickaxe(Player player) {
		return player.getInventory().addItem(getNewPickaxe(player)).isEmpty();
	}

	/**
	 * Converts the mining speed level to the speed multiplier for the level
	 * @param level
	 * @return the converted speed
	 */
	public static double convertMiningSpeedLevelToDamagePerTick(int level) {
		switch (level) {
		case 1:
			return 0.05; // 1
		case 2:
			return 0.055; // 1.1
		case 3:
			return 0.0625; // 1.25
		case 4:
			return 0.07; // 1.4
		case 5:
			return 0.08; // 1.6
		case 6:
			return 0.0925; // 1.85
		case 7:
			return 0.1075; // 2.15
		case 8:
			return 0.125; // 2.5
		}
		return 0.05;
	}

	/**
	 * Converts the mining max durability level to the durability for the level
	 * @param level
	 * @return the converted durability
	 */
	public static int convertMiningMaxDurabilityLevelToDurability(int level) {
		switch (level) {
		case 1:
			return 400;
		case 2:
			return 800;
		case 3:
			return 1200;
		case 4:
			return 2000;
		case 5:
			return 3000;
		case 6:
			return 4500;
		case 7:
			return 6000;
		case 8:
			return 8000;
		}
		return 400;
	}

	/**
	 * Converts the mining luck level to the luck for the level
	 * @param level
	 * @return the drop chances
	 */
	public static DropChances convertMiningLuckLevelToMiningDropChances(int level) {
		return new DropChances(level);
	}

	/**
	 * Converts the given level to a corresponding AP cost
	 * @param level
	 * @return the corresponding AP cost
	 */
	public static int convertLevelToApCost(int level) {
		switch (level) {
		case 1:
			return 0;
		case 2:
			return 1;
		case 3:
			return 2;
		case 4:
			return 3;
		case 5:
			return 4;
		case 6:
			return 5;
		case 7:
			return 7;
		case 8:
			return 8;
		}
		return 0;
	}

	/**
	 * Rolls a random drop amount
	 * @param playerData
	 * @return Either 1, 2, or 3 randomly weighted based on the player's drop rates
	 */
	public static int rollDropAmount(PlayerData playerData) {
		DropChances dropChances = playerData.getTotalMiningLuck();
		float doubleDrop = dropChances.getDoubleDropChance();
		float tripleDrop = doubleDrop + dropChances.getTripleDropChance();
		float roll = RANDOM.nextFloat();
		if (roll < doubleDrop) {
			return 2;
		} else if (roll < tripleDrop) {
			return 3;
		} else {
			return 1;
		}
	}

	/**
	 * Breaks the block as though the player mined it
	 * @param player the player who is breaking
	 * @param block the block to be broken
	 * @param ore the corresponding ore for the block
	 */
	public static void breakBlock(Player player, Block block, Ore ore) {
		if (ore == null) {
			return;
		}
		PlayerData playerData = PlayerData.getData(player);
		if (!ore.isMineable(playerData)) {
			return;
		}
		if (playerData.getMiningCurrentDurability() <= 0) {
			player.sendMessage(ChatColor.RED + "Your pickaxe is too worn out to break anything.");
			return;
		}
		// All checks complete, mining is safe.

		EffectsUtil.sendBlockDustParticleToLocation(block.getType(), block.getLocation(), 0.1F, 0.1F, 0.1F, 0.2F, 30);
		// Drops
		OreItem oreItem = ore.getRandomRarityDrop();
		oreItem.getBukkitItem().setAmount(rollDropAmount(playerData));
		GeneralUtil.addItemToInventoryOrDrop(player, oreItem.getBukkitItem());
		playerData.modifyMiningExp(ore.getRandomExp(oreItem.getRarityIndex()));
		if (oreItem.getRarityIndex() == 3) {
			player.playSound(block.getLocation(), Sound.ENTITY_FIREWORK_BLAST, 1F, 0F);
			EffectsUtil.sendParticleToLocation(ParticleEffect.FIREWORKS_SPARK, block.getLocation(), 0.1F, 0.1F, 0.1F, 0.1F, 20);
		}
		playerData.modifyMiningCurrentDurability(-1);
		updatePickaxeInInventory(player);

		// Block Respawning
		Material oldMaterial = block.getType();
		block.setType(Material.AIR);
		BukkitRunnable runnable = new BukkitRunnable() {
			@Override
			public void run() {
				block.setType(oldMaterial);
			}
		};
		runnable.runTaskLater(InfernalRealms.getPlugin(), 800L);
		new BukkitRunnable() {
			@Override
			public void run() {
				GeneralListener.REGEN_BLOCKS.remove(runnable);
			}
		}.runTaskLater(InfernalRealms.getPlugin(), 800L);
		GeneralListener.REGEN_BLOCKS.add(runnable);
	}

	/**
	 * Represents a the possible drop chances for the constructed level
	 */
	public static class DropChances {

		private float doubleDropChance;
		private float tripleDropChance;

		/**
		 * @param level the level to calculate
		 */
		public DropChances(int level) {
			switch (level) {
			case 1:
				this.doubleDropChance = 0.05F;
				break;
			case 2:
				this.doubleDropChance = 0.1F;
				break;
			case 3:
				this.doubleDropChance = 0.15F;
				break;
			case 4:
				this.doubleDropChance = 0.2F;
				break;
			case 5:
				this.doubleDropChance = 0.2F;
				this.tripleDropChance = 0.05F;
				break;
			case 6:
				this.doubleDropChance = 0.25F;
				this.tripleDropChance = 0.05F;
				break;
			case 7:
				this.doubleDropChance = 0.3F;
				this.tripleDropChance = 0.05F;
				break;
			case 8:
				this.doubleDropChance = 0.3F;
				this.tripleDropChance = 0.1F;
			}
		}

		public float getDoubleDropChance() {
			return doubleDropChance;
		}

		public float getTripleDropChance() {
			return tripleDropChance;
		}

	}

}

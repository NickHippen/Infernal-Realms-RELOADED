package net.infernalrealms.mining;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import net.infernalrealms.items.GeneralItems;
import net.infernalrealms.mining.PickaxeFactory.DropChances;
import net.infernalrealms.player.PlayerData;
import net.infernalrealms.util.InfernalStrings;

public class MiningManagementGUI implements Listener {

	public static final String TITLE = "Mining Management";

	private static final String LEFT_CLICK_MESSAGE = ChatColor.DARK_PURPLE + "" + ChatColor.UNDERLINE + "Left-click" + ChatColor.GRAY
			+ "to upgrade.";
	private static final String DRAG_AND_DROP_MESSAGE = ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + "Drag " + ChatColor.GRAY + "and "
			+ ChatColor.DARK_PURPLE + ChatColor.BOLD + "drop " + ChatColor.GRAY + "your";
	private static final int[] BLANK_SLOTS = new int[] { 27, 28, 29, 33, 34, 35, 36, 37, 38, 42, 43, 44 };

	public static void open(Player player) {
		Inventory inv = Bukkit.createInventory(player, 45, TITLE);
		PlayerData playerData = PlayerData.getData(player);

		ItemStack speedIcon = new ItemStack(Material.GOLD_PICKAXE, 1, (byte) 0);
		ItemMeta speedIconMeta = speedIcon.getItemMeta();
		speedIconMeta.setDisplayName(ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + "Speed " + ChatColor.GRAY + ChatColor.BOLD + "[ "
				+ ChatColor.GRAY + "LVL " + playerData.getMiningSpeedLevel() + ChatColor.BOLD + " ]");
		List<String> speedIconLore = new ArrayList<String>(3);
		speedIconLore.add(ChatColor.GRAY + "Increases the mining");
		speedIconLore.add(ChatColor.GRAY + "speed of your pickaxe.");
		speedIconLore.add(LEFT_CLICK_MESSAGE);
		speedIconMeta.setLore(speedIconLore);
		speedIcon.setItemMeta(speedIconMeta);
		inv.setItem(0, speedIcon);
		for (int i = 1; i < 9; i++) {
			inv.setItem(i, generateIconForLevel(playerData, i, playerData::getMiningSpeedLevel, (level) -> {
				return String.format("%d%% speed", Math.round(2000F * PickaxeFactory.convertMiningSpeedLevelToDamagePerTick(level)));
			}));
		}

		ItemStack luckIcon = new ItemStack(Material.EMERALD, 1, (byte) 0);
		ItemMeta luckIconMeta = luckIcon.getItemMeta();
		luckIconMeta.setDisplayName(ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + "Luck " + ChatColor.GRAY + ChatColor.BOLD + "[ "
				+ ChatColor.GRAY + "LVL " + playerData.getMiningLuckLevel() + ChatColor.BOLD + " ]");
		List<String> luckIconLore = new ArrayList<String>(3);
		luckIconLore.add(ChatColor.GRAY + "Increases the chance");
		luckIconLore.add(ChatColor.GRAY + "of recieving extra ore.");
		luckIconLore.add(LEFT_CLICK_MESSAGE);
		luckIconMeta.setLore(luckIconLore);
		luckIcon.setItemMeta(luckIconMeta);
		inv.setItem(9, luckIcon);
		for (int i = 10; i < 19; i++) {
			inv.setItem(i, generateIconForLevel(playerData, i % 9, playerData::getMiningLuckLevel, (level) -> {
				DropChances dc = PickaxeFactory.convertMiningLuckLevelToMiningDropChances(level);
				return String.format("%d%% double ore", Math.round(100F * dc.getDoubleDropChance())) + (dc.getTripleDropChance() > 0
						? String.format(", %d%% triple ore", Math.round(100F * dc.getTripleDropChance())) : "");
			}));
		}

		ItemStack durabilityIcon = new ItemStack(Material.ANVIL, 1, (byte) 0);
		ItemMeta durabilityIconMeta = durabilityIcon.getItemMeta();
		durabilityIconMeta.setDisplayName(ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + "Durability " + ChatColor.GRAY + ChatColor.BOLD
				+ "[ " + ChatColor.GRAY + "LVL " + playerData.getMiningMaxDurabilityLevel() + ChatColor.BOLD + " ]");
		List<String> durabilityIconLore = new ArrayList<String>(3);
		durabilityIconLore.add(ChatColor.GRAY + "Increases the durability");
		durabilityIconLore.add(ChatColor.GRAY + "of your pickaxe.");
		durabilityIconLore.add(LEFT_CLICK_MESSAGE);
		durabilityIconMeta.setLore(durabilityIconLore);
		durabilityIcon.setItemMeta(durabilityIconMeta);
		inv.setItem(18, durabilityIcon);
		for (int i = 19; i < 29; i++) {
			inv.setItem(i, generateIconForLevel(playerData, i % 9, playerData::getMiningMaxDurabilityLevel, (level) -> {
				return String.format("%d uses", PickaxeFactory.convertMiningMaxDurabilityLevelToDurability(level));
			}));
		}

		for (int i : BLANK_SLOTS) {
			inv.setItem(i, GeneralItems.BLANK_BLACK_GLASS_PANE);
		}

		ItemStack hiltSlot = new ItemStack(Material.IRON_FENCE, 1, (byte) 0);
		ItemMeta hiltSlotMeta = hiltSlot.getItemMeta();
		hiltSlotMeta.setDisplayName(DRAG_AND_DROP_MESSAGE);
		List<String> hiltSlotLore = new ArrayList<>();
		hiltSlotLore.add(ChatColor.GRAY + "pickaxe " + ChatColor.BOLD + "HILT" + ChatColor.GRAY + " into the");
		hiltSlotLore.add(ChatColor.GRAY + "above slot.");
		hiltSlotMeta.setLore(hiltSlotLore);
		hiltSlot.setItemMeta(hiltSlotMeta);
		inv.setItem(39, hiltSlot);

		ItemStack bindingSlot = new ItemStack(Material.IRON_FENCE, 1, (byte) 0);
		ItemMeta bindingSlotMeta = bindingSlot.getItemMeta();
		bindingSlotMeta.setDisplayName(DRAG_AND_DROP_MESSAGE);
		List<String> bindingSlotLore = new ArrayList<>();
		bindingSlotLore.add(ChatColor.GRAY + "pickaxe " + ChatColor.BOLD + "BINDING" + ChatColor.GRAY + " into");
		bindingSlotLore.add(ChatColor.GRAY + "the above slot.");
		bindingSlotMeta.setLore(bindingSlotLore);
		bindingSlot.setItemMeta(bindingSlotMeta);
		inv.setItem(40, bindingSlot);

		ItemStack reinforcementSlot = new ItemStack(Material.IRON_FENCE, 1, (byte) 0);
		ItemMeta reinforcementSlotMeta = reinforcementSlot.getItemMeta();
		reinforcementSlotMeta.setDisplayName(DRAG_AND_DROP_MESSAGE);
		List<String> reinforcementSlotLore = new ArrayList<>();
		reinforcementSlotLore.add(ChatColor.GRAY + "pickaxe " + ChatColor.BOLD + "REINFORCEMENT");
		reinforcementSlotLore.add(ChatColor.GRAY + "into the above slot.");
		reinforcementSlotMeta.setLore(reinforcementSlotLore);
		reinforcementSlot.setItemMeta(reinforcementSlotMeta);
		inv.setItem(41, reinforcementSlot);

		player.openInventory(inv);
	}

	private static ItemStack generateIconForLevel(PlayerData playerData, int level, LevelFunction levelFunction,
			IconFunction iconFunction) {
		boolean unlocked = levelFunction.value() >= level;
		ItemStack icon = new ItemStack(Material.INK_SACK, 1, (byte) (unlocked ? 13/*Unlocked*/ : 8/*Locked*/));
		ItemMeta iconMeta = icon.getItemMeta();
		iconMeta.setDisplayName(ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + "RANK " + level);
		List<String> lore = new ArrayList<>();
		lore.add(ChatColor.GRAY + iconFunction.value(level));
		lore.add(ChatColor.GRAY + "" + ChatColor.BOLD + "COST: " + ChatColor.GRAY + PickaxeFactory.convertLevelToApCost(level)
				+ " stat points");
		lore.add(unlocked ? InfernalStrings.UNLOCKED_LINE : InfernalStrings.LOCKED_LINE);
		iconMeta.setLore(lore);
		icon.setItemMeta(iconMeta);
		return icon;
	}

	@EventHandler
	public void onGUIClick(InventoryClickEvent event) {
		if (!event.getInventory().getTitle().equals(TITLE) || !(event.getWhoClicked() instanceof Player)) {
			return;
		}
		event.setCancelled(true);
		Player player = (Player) event.getWhoClicked();
		PlayerData playerData = PlayerData.getData(player);
		boolean update = false;
		if (event.getRawSlot() < 27) { // Leveling mining stats
			int statNumber = event.getRawSlot() / 9;
			int level = event.getRawSlot() % 9;
			if (level == 0) {
				level = playerData.getMiningLevel() + 1;
			}
			int apCost = PickaxeFactory.convertLevelToApCost(level);
			switch (statNumber) {
			case 0: // Speed
				if (playerData.getMiningSpeedLevel() + 1 == level) {
					if (playerData.getMiningAP() > apCost) {
						playerData.modifyMiningAP(-apCost);
						playerData.modifyMiningSpeedLevel(1);
						update = true;
					} else {
						player.sendMessage(ChatColor.RED + "You do not have enough mining AP.");
					}
				}
				break;
			case 1: // Luck
				if (playerData.getMiningLuckLevel() + 1 == level) {
					if (playerData.getMiningAP() > apCost) {
						playerData.modifyMiningAP(-apCost);
						playerData.modifyMiningLuckLevel(1);
						update = true;
					} else {
						player.sendMessage(ChatColor.RED + "You do not have enough mining AP.");
					}
				}
				break;
			case 2: // Durability
				if (playerData.getMiningMaxDurabilityLevel() + 1 == level) {
					if (playerData.getMiningAP() > apCost) {
						playerData.modifyMiningAP(-apCost);
						playerData.modifyMiningMaxDurabilityLevel(1);
						update = true;
					} else {
						player.sendMessage(ChatColor.RED + "You do not have enough mining AP.");
					}
				}
				break;
			}
		} else {
			if (event.getRawSlot() >= 30 && event.getRawSlot() <= 32) { // Pickaxe Equips
				event.setCancelled(true); // TODO Make false after adding these
				switch (event.getRawSlot()) {
				case 30: // Hilt

					break;
				case 31: // Binding

					break;
				case 32: // Reinforcement

					break;
				}
			}
		}

		if (update) { // Changes were made
			PickaxeFactory.updatePickaxeInInventory(player);
			open(player);
		}
	}

	@FunctionalInterface
	private interface IconFunction {
		public String value(int v);
	}

	@FunctionalInterface
	private interface LevelFunction {
		public int value();
	}

}

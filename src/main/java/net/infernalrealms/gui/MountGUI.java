package net.infernalrealms.gui;

import java.util.Arrays;
import java.util.List;

import net.infernalrealms.mount.MountManager;
import net.infernalrealms.mount.Size;
import net.infernalrealms.player.PlayerData;
import net.infernalrealms.util.GeneralUtil;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Horse.Variant;
import org.bukkit.entity.Player;
import org.bukkit.entity.Horse.Color;
import org.bukkit.entity.Horse.Style;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class MountGUI {

	public static void open(Player player) {
		PlayerData playerData = PlayerData.getData(player);
		Inventory inv = player.getServer().createInventory(player, 45, "Mount SP: " + playerData.getMountStatPoints());
		String unlocked = ChatColor.RESET + "" + ChatColor.GREEN + "" + ChatColor.BOLD + "[ " + ChatColor.GREEN + "UNLOCKED"
				+ ChatColor.BOLD + " ]";
		String locked = ChatColor.RESET + "" + ChatColor.RED + "" + ChatColor.BOLD + "[" + ChatColor.RESET + " " + ChatColor.RED + "LOCKED"
				+ ChatColor.BOLD + " ]";
		String lmbEquip = ChatColor.RESET + "" + ChatColor.DARK_PURPLE + "" + ChatColor.UNDERLINE + "Left-click" + ChatColor.RESET + ""
				+ ChatColor.GRAY + " to equip.";
		String lmbUpgrade = ChatColor.RESET + "" + ChatColor.DARK_PURPLE + "" + ChatColor.UNDERLINE + "Left-click" + ChatColor.RESET + ""
				+ ChatColor.GRAY + " to upgrade.";

		ItemStack speedIcon = new ItemStack(Material.IRON_BOOTS, 1, (short) 0);
		ItemMeta speedIconMeta = speedIcon.getItemMeta();
		speedIconMeta.setDisplayName(ChatColor.RESET + "" + ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + "Speed " + ChatColor.GRAY + ""
				+ ChatColor.BOLD + "[ " + ChatColor.RESET + "" + ChatColor.GRAY + "LVL " + playerData.getMountSpeed() + ChatColor.BOLD
				+ " ]");
		speedIconMeta.setLore(Arrays.asList(ChatColor.RESET + "" + ChatColor.GRAY + "Increases the speed", ChatColor.RESET + ""
				+ ChatColor.GRAY + "of your mount.", lmbUpgrade));
		speedIcon.setItemMeta(speedIconMeta);
		inv.setItem(0, speedIcon);

		ItemStack jumpIcon = new ItemStack(Material.SLIME_BALL, 1, (short) 0);
		ItemMeta jumpIconMeta = jumpIcon.getItemMeta();
		jumpIconMeta.setDisplayName(ChatColor.RESET + "" + ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + "Jump " + ChatColor.GRAY + ""
				+ ChatColor.BOLD + "[ " + ChatColor.RESET + "" + ChatColor.GRAY + "LVL " + playerData.getMountJump() + ChatColor.BOLD
				+ " ]");
		jumpIconMeta.setLore(Arrays.asList(ChatColor.RESET + "" + ChatColor.GRAY + "Increases the jumping", ChatColor.RESET + ""
				+ ChatColor.GRAY + "height of your mount.", lmbUpgrade));
		jumpIcon.setItemMeta(jumpIconMeta);
		inv.setItem(9, jumpIcon);

		ItemStack hungerIcon = new ItemStack(Material.WHEAT, 1, (short) 0);
		ItemMeta hungerIconMeta = hungerIcon.getItemMeta();
		hungerIconMeta.setDisplayName(ChatColor.RESET + "" + ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + "Hunger " + ChatColor.GRAY + ""
				+ ChatColor.BOLD + "[ " + ChatColor.RESET + "" + ChatColor.GRAY + "LVL " + playerData.getMountMaxHungerLevel()
				+ ChatColor.BOLD + " ]");
		hungerIconMeta.setLore(Arrays.asList(ChatColor.RESET + "" + ChatColor.GRAY + "Increases the maximum", ChatColor.RESET + ""
				+ ChatColor.GRAY + "hunger of your mount.", lmbUpgrade));
		hungerIcon.setItemMeta(hungerIconMeta);
		inv.setItem(18, hungerIcon);

		ItemStack speed2Off = new ItemStack(Material.INK_SACK, 1, (short) 8);
		ItemMeta speed2OffMeta = speed2Off.getItemMeta();
		speed2OffMeta.setDisplayName(ChatColor.RESET + "" + ChatColor.GRAY + "" + ChatColor.BOLD + "RANK 2");
		speed2OffMeta.setLore(Arrays.asList(ChatColor.RESET + "" + ChatColor.GRAY + "Default Speed + 3", ChatColor.RESET + ""
				+ ChatColor.GRAY + "" + ChatColor.BOLD + "COST: " + ChatColor.RESET + "" + ChatColor.GRAY + "1 stat point", locked));
		speed2Off.setItemMeta(speed2OffMeta);
		inv.setItem(2, speed2Off);

		ItemStack speed3Off = new ItemStack(Material.INK_SACK, 1, (short) 8);
		ItemMeta speed3OffMeta = speed3Off.getItemMeta();
		speed3OffMeta.setDisplayName(ChatColor.RESET + "" + ChatColor.GRAY + "" + ChatColor.BOLD + "RANK 3");
		speed3OffMeta.setLore(Arrays.asList(ChatColor.RESET + "" + ChatColor.GRAY + "Default Speed + 5", ChatColor.RESET + ""
				+ ChatColor.GRAY + "" + ChatColor.BOLD + "COST: " + ChatColor.RESET + "" + ChatColor.GRAY + "3 stat points", locked));
		speed3Off.setItemMeta(speed3OffMeta);
		inv.setItem(3, speed3Off);

		ItemStack speed4Off = new ItemStack(Material.INK_SACK, 1, (short) 8);
		ItemMeta speed4OffMeta = speed4Off.getItemMeta();
		speed4OffMeta.setDisplayName(ChatColor.RESET + "" + ChatColor.GRAY + "" + ChatColor.BOLD + "RANK 4");
		speed4OffMeta.setLore(Arrays.asList(ChatColor.RESET + "" + ChatColor.GRAY + "Default Speed + 7", ChatColor.RESET + ""
				+ ChatColor.GRAY + "" + ChatColor.BOLD + "COST: " + ChatColor.RESET + "" + ChatColor.GRAY + "5 stat points", locked));
		speed4Off.setItemMeta(speed4OffMeta);
		inv.setItem(4, speed4Off);

		ItemStack speed5Off = new ItemStack(Material.INK_SACK, 1, (short) 8);
		ItemMeta speed5OffMeta = speed5Off.getItemMeta();
		speed5OffMeta.setDisplayName(ChatColor.RESET + "" + ChatColor.GRAY + "" + ChatColor.BOLD + "RANK 5");
		speed5OffMeta.setLore(Arrays.asList(ChatColor.RESET + "" + ChatColor.GRAY + "Default Speed + 8", ChatColor.RESET + ""
				+ ChatColor.GRAY + "" + ChatColor.BOLD + "COST: " + ChatColor.RESET + "" + ChatColor.GRAY + "8 stat points", locked));
		speed5Off.setItemMeta(speed5OffMeta);
		inv.setItem(5, speed5Off);

		ItemStack speed6Off = new ItemStack(Material.INK_SACK, 1, (short) 8);
		ItemMeta speed6OffMeta = speed6Off.getItemMeta();
		speed6OffMeta.setDisplayName(ChatColor.RESET + "" + ChatColor.GRAY + "" + ChatColor.BOLD + "RANK 6");
		speed6OffMeta.setLore(Arrays.asList(ChatColor.RESET + "" + ChatColor.GRAY + "Default Speed + 9", ChatColor.RESET + ""
				+ ChatColor.GRAY + "" + ChatColor.BOLD + "COST: " + ChatColor.RESET + "" + ChatColor.GRAY + "11 stat points", locked));
		speed6Off.setItemMeta(speed6OffMeta);
		inv.setItem(6, speed6Off);

		ItemStack speed7Off = new ItemStack(Material.INK_SACK, 1, (short) 8);
		ItemMeta speed7OffMeta = speed7Off.getItemMeta();
		speed7OffMeta.setDisplayName(ChatColor.RESET + "" + ChatColor.GRAY + "" + ChatColor.BOLD + "RANK 7");
		speed7OffMeta.setLore(Arrays.asList(ChatColor.RESET + "" + ChatColor.GRAY + "Default Speed + 10", ChatColor.RESET + ""
				+ ChatColor.GRAY + "" + ChatColor.BOLD + "COST: " + ChatColor.RESET + "" + ChatColor.GRAY + "15 stat points", locked));
		speed7Off.setItemMeta(speed7OffMeta);
		inv.setItem(7, speed7Off);

		ItemStack speed8Off = new ItemStack(Material.INK_SACK, 1, (short) 8);
		ItemMeta speed8OffMeta = speed8Off.getItemMeta();
		speed8OffMeta.setDisplayName(ChatColor.RESET + "" + ChatColor.GRAY + "" + ChatColor.BOLD + "RANK 8");
		speed8OffMeta.setLore(Arrays.asList(ChatColor.RESET + "" + ChatColor.GRAY + "Default Speed + 12", ChatColor.RESET + ""
				+ ChatColor.GRAY + "" + ChatColor.BOLD + "COST: " + ChatColor.RESET + "" + ChatColor.GRAY + "20 stat points", locked));
		speed8Off.setItemMeta(speed8OffMeta);
		inv.setItem(8, speed8Off);

		ItemStack speed1On = new ItemStack(Material.INK_SACK, 1, (short) 13);
		ItemMeta speed1OnMeta = speed1On.getItemMeta();
		speed1OnMeta.setDisplayName(ChatColor.RESET + "" + ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + "RANK 1");
		speed1OnMeta.setLore(Arrays.asList(ChatColor.RESET + "" + ChatColor.GRAY + "Default Speed", ChatColor.RESET + "" + ChatColor.GRAY
				+ "" + ChatColor.BOLD + "COST: " + ChatColor.RESET + "" + ChatColor.GRAY + "None", unlocked));
		speed1On.setItemMeta(speed1OnMeta);
		inv.setItem(1, speed1On);

		int mountSpeed = playerData.getMountSpeed();

		switch (mountSpeed) {
		case 8:
			ItemStack speed8On = new ItemStack(Material.INK_SACK, 1, (short) 13);
			ItemMeta speed8OnMeta = speed8On.getItemMeta();
			speed8OnMeta.setDisplayName(ChatColor.RESET + "" + ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + "RANK 8");
			speed8OnMeta
					.setLore(Arrays.asList(ChatColor.RESET + "" + ChatColor.GRAY + "Default Speed + 12", ChatColor.RESET + ""
							+ ChatColor.GRAY + "" + ChatColor.BOLD + "COST: " + ChatColor.RESET + "" + ChatColor.GRAY + "20 stat points",
							unlocked));
			speed8On.setItemMeta(speed8OnMeta);
			inv.setItem(8, speed8On);
		case 7:
			ItemStack speed7On = new ItemStack(Material.INK_SACK, 1, (short) 13);
			ItemMeta speed7OnMeta = speed7On.getItemMeta();
			speed7OnMeta.setDisplayName(ChatColor.RESET + "" + ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + "RANK 7");
			speed7OnMeta
					.setLore(Arrays.asList(ChatColor.RESET + "" + ChatColor.GRAY + "Default Speed + 10", ChatColor.RESET + ""
							+ ChatColor.GRAY + "" + ChatColor.BOLD + "COST: " + ChatColor.RESET + "" + ChatColor.GRAY + "15 stat points",
							unlocked));
			speed7On.setItemMeta(speed7OnMeta);
			inv.setItem(7, speed7On);
		case 6:
			ItemStack speed6On = new ItemStack(Material.INK_SACK, 1, (short) 13);
			ItemMeta speed6OnMeta = speed6On.getItemMeta();
			speed6OnMeta.setDisplayName(ChatColor.RESET + "" + ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + "RANK 6");
			speed6OnMeta
					.setLore(Arrays.asList(ChatColor.RESET + "" + ChatColor.GRAY + "Default Speed + 9", ChatColor.RESET + ""
							+ ChatColor.GRAY + "" + ChatColor.BOLD + "COST: " + ChatColor.RESET + "" + ChatColor.GRAY + "11 stat points",
							unlocked));
			speed6On.setItemMeta(speed6OnMeta);
			inv.setItem(6, speed6On);
		case 5:
			ItemStack speed5On = new ItemStack(Material.INK_SACK, 1, (short) 13);
			ItemMeta speed5OnMeta = speed5On.getItemMeta();
			speed5OnMeta.setDisplayName(ChatColor.RESET + "" + ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + "RANK 5");
			speed5OnMeta.setLore(Arrays.asList(ChatColor.RESET + "" + ChatColor.GRAY + "Default Speed + 8", ChatColor.RESET + ""
					+ ChatColor.GRAY + "" + ChatColor.BOLD + "COST: " + ChatColor.RESET + "" + ChatColor.GRAY + "8 stat points", unlocked));
			speed5On.setItemMeta(speed5OnMeta);
			inv.setItem(5, speed5On);
		case 4:
			ItemStack speed4On = new ItemStack(Material.INK_SACK, 1, (short) 13);
			ItemMeta speed4OnMeta = speed4On.getItemMeta();
			speed4OnMeta.setDisplayName(ChatColor.RESET + "" + ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + "RANK 4");
			speed4OnMeta.setLore(Arrays.asList(ChatColor.RESET + "" + ChatColor.GRAY + "Default Speed + 7", ChatColor.RESET + ""
					+ ChatColor.GRAY + "" + ChatColor.BOLD + "COST: " + ChatColor.RESET + "" + ChatColor.GRAY + "5 stat points", unlocked));
			speed4On.setItemMeta(speed4OnMeta);
			inv.setItem(4, speed4On);
		case 3:
			ItemStack speed3On = new ItemStack(Material.INK_SACK, 1, (short) 13);
			ItemMeta speed3OnMeta = speed3On.getItemMeta();
			speed3OnMeta.setDisplayName(ChatColor.RESET + "" + ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + "RANK 3");
			speed3OnMeta.setLore(Arrays.asList(ChatColor.RESET + "" + ChatColor.GRAY + "Default Speed + 5", ChatColor.RESET + ""
					+ ChatColor.GRAY + "" + ChatColor.BOLD + "COST: " + ChatColor.RESET + "" + ChatColor.GRAY + "3 stat points", unlocked));
			speed3On.setItemMeta(speed3OnMeta);
			inv.setItem(3, speed3On);
		case 2:
			ItemStack speed2On = new ItemStack(Material.INK_SACK, 1, (short) 13);
			ItemMeta speed2OnMeta = speed2On.getItemMeta();
			speed2OnMeta.setDisplayName(ChatColor.RESET + "" + ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + "RANK 2");
			speed2OnMeta.setLore(Arrays.asList(ChatColor.RESET + "" + ChatColor.GRAY + "Default Speed + 3", ChatColor.RESET + ""
					+ ChatColor.GRAY + "" + ChatColor.BOLD + "COST: " + ChatColor.RESET + "" + ChatColor.GRAY + "1 stat point", unlocked));
			speed2On.setItemMeta(speed2OnMeta);
			inv.setItem(2, speed2On);
		}

		ItemStack jump2Off = new ItemStack(Material.INK_SACK, 1, (short) 8);
		ItemMeta jump2OffMeta = jump2Off.getItemMeta();
		jump2OffMeta.setDisplayName(ChatColor.RESET + "" + ChatColor.GRAY + "" + ChatColor.BOLD + "RANK 2");
		jump2OffMeta.setLore(Arrays.asList(ChatColor.RESET + "" + ChatColor.GRAY + "1.5 block jump", ChatColor.RESET + "" + ChatColor.GRAY
				+ "" + ChatColor.BOLD + "COST: " + ChatColor.RESET + "" + ChatColor.GRAY + "1 stat point", locked));
		jump2Off.setItemMeta(jump2OffMeta);
		inv.setItem(11, jump2Off);

		ItemStack jump3Off = new ItemStack(Material.INK_SACK, 1, (short) 8);
		ItemMeta jump3OffMeta = jump3Off.getItemMeta();
		jump3OffMeta.setDisplayName(ChatColor.RESET + "" + ChatColor.GRAY + "" + ChatColor.BOLD + "RANK 3");
		jump3OffMeta.setLore(Arrays.asList(ChatColor.RESET + "" + ChatColor.GRAY + "2 block jump", ChatColor.RESET + "" + ChatColor.GRAY
				+ "" + ChatColor.BOLD + "COST: " + ChatColor.RESET + "" + ChatColor.GRAY + "3 stat points", locked));
		jump3Off.setItemMeta(jump3OffMeta);
		inv.setItem(12, jump3Off);

		ItemStack jump4Off = new ItemStack(Material.INK_SACK, 1, (short) 8);
		ItemMeta jump4OffMeta = jump4Off.getItemMeta();
		jump4OffMeta.setDisplayName(ChatColor.RESET + "" + ChatColor.GRAY + "" + ChatColor.BOLD + "RANK 4");
		jump4OffMeta.setLore(Arrays.asList(ChatColor.RESET + "" + ChatColor.GRAY + "2.5 block jump", ChatColor.RESET + "" + ChatColor.GRAY
				+ "" + ChatColor.BOLD + "COST: " + ChatColor.RESET + "" + ChatColor.GRAY + "5 stat points", locked));
		jump4Off.setItemMeta(jump4OffMeta);
		inv.setItem(13, jump4Off);

		ItemStack jump5Off = new ItemStack(Material.INK_SACK, 1, (short) 8);
		ItemMeta jump5OffMeta = jump5Off.getItemMeta();
		jump5OffMeta.setDisplayName(ChatColor.RESET + "" + ChatColor.GRAY + "" + ChatColor.BOLD + "RANK 5");
		jump5OffMeta.setLore(Arrays.asList(ChatColor.RESET + "" + ChatColor.GRAY + "3 block jump", ChatColor.RESET + "" + ChatColor.GRAY
				+ "" + ChatColor.BOLD + "COST: " + ChatColor.RESET + "" + ChatColor.GRAY + "8 stat points", locked));
		jump5Off.setItemMeta(jump5OffMeta);
		inv.setItem(14, jump5Off);

		ItemStack jump6Off = new ItemStack(Material.INK_SACK, 1, (short) 8);
		ItemMeta jump6OffMeta = jump6Off.getItemMeta();
		jump6OffMeta.setDisplayName(ChatColor.RESET + "" + ChatColor.GRAY + "" + ChatColor.BOLD + "RANK 6");
		jump6OffMeta.setLore(Arrays.asList(ChatColor.RESET + "" + ChatColor.GRAY + "3.5 block jump", ChatColor.RESET + "" + ChatColor.GRAY
				+ "" + ChatColor.BOLD + "COST: " + ChatColor.RESET + "" + ChatColor.GRAY + "11 stat points", locked));
		jump6Off.setItemMeta(jump6OffMeta);
		inv.setItem(15, jump6Off);

		ItemStack jump7Off = new ItemStack(Material.INK_SACK, 1, (short) 8);
		ItemMeta jump7OffMeta = jump7Off.getItemMeta();
		jump7OffMeta.setDisplayName(ChatColor.RESET + "" + ChatColor.GRAY + "" + ChatColor.BOLD + "RANK 7");
		jump7OffMeta.setLore(Arrays.asList(ChatColor.RESET + "" + ChatColor.GRAY + "4 block jump", ChatColor.RESET + "" + ChatColor.GRAY
				+ "" + ChatColor.BOLD + "COST: " + ChatColor.RESET + "" + ChatColor.GRAY + "15 stat points", locked));
		jump7Off.setItemMeta(jump7OffMeta);
		inv.setItem(16, jump7Off);

		ItemStack jump8Off = new ItemStack(Material.INK_SACK, 1, (short) 8);
		ItemMeta jump8OffMeta = jump8Off.getItemMeta();
		jump8OffMeta.setDisplayName(ChatColor.RESET + "" + ChatColor.GRAY + "" + ChatColor.BOLD + "RANK 8");
		jump8OffMeta.setLore(Arrays.asList(ChatColor.RESET + "" + ChatColor.GRAY + "5 block jump", ChatColor.RESET + "" + ChatColor.GRAY
				+ "" + ChatColor.BOLD + "COST: " + ChatColor.RESET + "" + ChatColor.GRAY + "20 stat points", locked));
		jump8Off.setItemMeta(jump8OffMeta);
		inv.setItem(17, jump8Off);

		ItemStack jump1On = new ItemStack(Material.INK_SACK, 1, (short) 13);
		ItemMeta jump1OnMeta = jump1On.getItemMeta();
		jump1OnMeta.setDisplayName(ChatColor.RESET + "" + ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + "RANK 1");
		jump1OnMeta.setLore(Arrays.asList(ChatColor.RESET + "" + ChatColor.GRAY + "0.5 block jump", ChatColor.RESET + "" + ChatColor.GRAY
				+ "" + ChatColor.BOLD + "COST: " + ChatColor.RESET + "" + ChatColor.GRAY + "None", unlocked));
		jump1On.setItemMeta(jump1OnMeta);
		inv.setItem(10, jump1On);

		int mountJump = playerData.getMountJump();

		switch (mountJump) {
		case 8:
			ItemStack jump8On = new ItemStack(Material.INK_SACK, 1, (short) 13);
			ItemMeta jump8OnMeta = jump8On.getItemMeta();
			jump8OnMeta.setDisplayName(ChatColor.RESET + "" + ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + "RANK 8");
			jump8OnMeta.setLore(Arrays.asList(ChatColor.RESET + "" + ChatColor.GRAY + "5 block jump", ChatColor.RESET + "" + ChatColor.GRAY
					+ "" + ChatColor.BOLD + "COST: " + ChatColor.RESET + "" + ChatColor.GRAY + "20 stat points", unlocked));
			jump8On.setItemMeta(jump8OnMeta);
			inv.setItem(17, jump8On);
		case 7:
			ItemStack jump7On = new ItemStack(Material.INK_SACK, 1, (short) 13);
			ItemMeta jump7OnMeta = jump7On.getItemMeta();
			jump7OnMeta.setDisplayName(ChatColor.RESET + "" + ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + "RANK 7");
			jump7OnMeta.setLore(Arrays.asList(ChatColor.RESET + "" + ChatColor.GRAY + "4 block jump", ChatColor.RESET + "" + ChatColor.GRAY
					+ "" + ChatColor.BOLD + "COST: " + ChatColor.RESET + "" + ChatColor.GRAY + "15 stat points", unlocked));
			jump7On.setItemMeta(jump7OnMeta);
			inv.setItem(16, jump7On);
		case 6:
			ItemStack jump6On = new ItemStack(Material.INK_SACK, 1, (short) 13);
			ItemMeta jump6OnMeta = jump6On.getItemMeta();
			jump6OnMeta.setDisplayName(ChatColor.RESET + "" + ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + "RANK 6");
			jump6OnMeta
					.setLore(Arrays.asList(ChatColor.RESET + "" + ChatColor.GRAY + "3.5 block jump", ChatColor.RESET + "" + ChatColor.GRAY
							+ "" + ChatColor.BOLD + "COST: " + ChatColor.RESET + "" + ChatColor.GRAY + "11 stat points", unlocked));
			jump6On.setItemMeta(jump6OnMeta);
			inv.setItem(15, jump6On);

		case 5:
			ItemStack jump5On = new ItemStack(Material.INK_SACK, 1, (short) 13);
			ItemMeta jump5OnMeta = jump5On.getItemMeta();
			jump5OnMeta.setDisplayName(ChatColor.RESET + "" + ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + "RANK 5");
			jump5OnMeta.setLore(Arrays.asList(ChatColor.RESET + "" + ChatColor.GRAY + "3 block jump", ChatColor.RESET + "" + ChatColor.GRAY
					+ "" + ChatColor.BOLD + "COST: " + ChatColor.RESET + "" + ChatColor.GRAY + "8 stat points", unlocked));
			jump5On.setItemMeta(jump5OnMeta);
			inv.setItem(14, jump5On);
		case 4:
			ItemStack jump4On = new ItemStack(Material.INK_SACK, 1, (short) 13);
			ItemMeta jump4OnMeta = jump4On.getItemMeta();
			jump4OnMeta.setDisplayName(ChatColor.RESET + "" + ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + "RANK 4");
			jump4OnMeta.setLore(Arrays.asList(ChatColor.RESET + "" + ChatColor.GRAY + "2.5 block jump", ChatColor.RESET + ""
					+ ChatColor.GRAY + "" + ChatColor.BOLD + "COST: " + ChatColor.RESET + "" + ChatColor.GRAY + "5 stat points", unlocked));
			jump4On.setItemMeta(jump4OnMeta);
			inv.setItem(13, jump4On);
		case 3:
			ItemStack jump3On = new ItemStack(Material.INK_SACK, 1, (short) 13);
			ItemMeta jump3OnMeta = jump3On.getItemMeta();
			jump3OnMeta.setDisplayName(ChatColor.RESET + "" + ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + "RANK 3");
			jump3OnMeta.setLore(Arrays.asList(ChatColor.RESET + "" + ChatColor.GRAY + "2 block jump", ChatColor.RESET + "" + ChatColor.GRAY
					+ "" + ChatColor.BOLD + "COST: " + ChatColor.RESET + "" + ChatColor.GRAY + "3 stat points", unlocked));
			jump3On.setItemMeta(jump3OnMeta);
			inv.setItem(12, jump3On);
		case 2:
			ItemStack jump2On = new ItemStack(Material.INK_SACK, 1, (short) 13);
			ItemMeta jump2OnMeta = jump2On.getItemMeta();
			jump2OnMeta.setDisplayName(ChatColor.RESET + "" + ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + "RANK 2");
			jump2OnMeta.setLore(Arrays.asList(ChatColor.RESET + "" + ChatColor.GRAY + "1.5 block jump", ChatColor.RESET + ""
					+ ChatColor.GRAY + "" + ChatColor.BOLD + "COST: " + ChatColor.RESET + "" + ChatColor.GRAY + "1 stat point", unlocked));
			jump2On.setItemMeta(jump2OnMeta);
			inv.setItem(11, jump2On);
		}

		ItemStack hunger2Off = new ItemStack(Material.INK_SACK, 1, (short) 8);
		ItemMeta hunger2OffMeta = hunger2Off.getItemMeta();
		hunger2OffMeta.setDisplayName(ChatColor.RESET + "" + ChatColor.GRAY + "" + ChatColor.BOLD + "RANK 2");
		hunger2OffMeta.setLore(Arrays.asList(ChatColor.RESET + "" + ChatColor.GRAY + "Default Hunger + 25%", ChatColor.RESET + ""
				+ ChatColor.GRAY + "" + ChatColor.BOLD + "COST: " + ChatColor.RESET + "" + ChatColor.GRAY + "1 stat point", locked));
		hunger2Off.setItemMeta(hunger2OffMeta);
		inv.setItem(20, hunger2Off);

		ItemStack hunger3Off = new ItemStack(Material.INK_SACK, 1, (short) 8);
		ItemMeta hunger3OffMeta = hunger3Off.getItemMeta();
		hunger3OffMeta.setDisplayName(ChatColor.RESET + "" + ChatColor.GRAY + "" + ChatColor.BOLD + "RANK 3");
		hunger3OffMeta.setLore(Arrays.asList(ChatColor.RESET + "" + ChatColor.GRAY + "Default Hunger + 50%", ChatColor.RESET + ""
				+ ChatColor.GRAY + "" + ChatColor.BOLD + "COST: " + ChatColor.RESET + "" + ChatColor.GRAY + "3 stat points", locked));
		hunger3Off.setItemMeta(hunger3OffMeta);
		inv.setItem(21, hunger3Off);

		ItemStack hunger4Off = new ItemStack(Material.INK_SACK, 1, (short) 8);
		ItemMeta hunger4OffMeta = hunger4Off.getItemMeta();
		hunger4OffMeta.setDisplayName(ChatColor.RESET + "" + ChatColor.GRAY + "" + ChatColor.BOLD + "RANK 4");
		hunger4OffMeta.setLore(Arrays.asList(ChatColor.RESET + "" + ChatColor.GRAY + "Default Hunger + 75%", ChatColor.RESET + ""
				+ ChatColor.GRAY + "" + ChatColor.BOLD + "COST: " + ChatColor.RESET + "" + ChatColor.GRAY + "5 stat points", locked));
		hunger4Off.setItemMeta(hunger4OffMeta);
		inv.setItem(22, hunger4Off);

		ItemStack hunger5Off = new ItemStack(Material.INK_SACK, 1, (short) 8);
		ItemMeta hunger5OffMeta = hunger5Off.getItemMeta();
		hunger5OffMeta.setDisplayName(ChatColor.RESET + "" + ChatColor.GRAY + "" + ChatColor.BOLD + "RANK 5");
		hunger5OffMeta.setLore(Arrays.asList(ChatColor.RESET + "" + ChatColor.GRAY + "Default Hunger + 100%", ChatColor.RESET + ""
				+ ChatColor.GRAY + "" + ChatColor.BOLD + "COST: " + ChatColor.RESET + "" + ChatColor.GRAY + "8 stat points", locked));
		hunger5Off.setItemMeta(hunger5OffMeta);
		inv.setItem(23, hunger5Off);

		ItemStack hunger6Off = new ItemStack(Material.INK_SACK, 1, (short) 8);
		ItemMeta hunger6OffMeta = hunger6Off.getItemMeta();
		hunger6OffMeta.setDisplayName(ChatColor.RESET + "" + ChatColor.GRAY + "" + ChatColor.BOLD + "RANK 6");
		hunger6OffMeta.setLore(Arrays.asList(ChatColor.RESET + "" + ChatColor.GRAY + "Default Hunger + 125%", ChatColor.RESET + ""
				+ ChatColor.GRAY + "" + ChatColor.BOLD + "COST: " + ChatColor.RESET + "" + ChatColor.GRAY + "11 stat points", locked));
		hunger6Off.setItemMeta(hunger6OffMeta);
		inv.setItem(24, hunger6Off);

		ItemStack hunger7Off = new ItemStack(Material.INK_SACK, 1, (short) 8);
		ItemMeta hunger7OffMeta = hunger7Off.getItemMeta();
		hunger7OffMeta.setDisplayName(ChatColor.RESET + "" + ChatColor.GRAY + "" + ChatColor.BOLD + "RANK 7");
		hunger7OffMeta.setLore(Arrays.asList(ChatColor.RESET + "" + ChatColor.GRAY + "Default Hunger + 150%", ChatColor.RESET + ""
				+ ChatColor.GRAY + "" + ChatColor.BOLD + "COST: " + ChatColor.RESET + "" + ChatColor.GRAY + "15 stat points", locked));
		hunger7Off.setItemMeta(hunger7OffMeta);
		inv.setItem(25, hunger7Off);

		ItemStack hunger8Off = new ItemStack(Material.INK_SACK, 1, (short) 8);
		ItemMeta hunger8OffMeta = hunger8Off.getItemMeta();
		hunger8OffMeta.setDisplayName(ChatColor.RESET + "" + ChatColor.GRAY + "" + ChatColor.BOLD + "RANK 8");
		hunger8OffMeta.setLore(Arrays.asList(ChatColor.RESET + "" + ChatColor.GRAY + "Default Hunger + 200%", ChatColor.RESET + ""
				+ ChatColor.GRAY + "" + ChatColor.BOLD + "COST: " + ChatColor.RESET + "" + ChatColor.GRAY + "20 stat points", locked));
		hunger8Off.setItemMeta(hunger8OffMeta);
		inv.setItem(26, hunger8Off);

		ItemStack hunger1On = new ItemStack(Material.INK_SACK, 1, (short) 13);
		ItemMeta hunger1OnMeta = hunger1On.getItemMeta();
		hunger1OnMeta.setDisplayName(ChatColor.RESET + "" + ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + "RANK 1");
		hunger1OnMeta.setLore(Arrays.asList(ChatColor.RESET + "" + ChatColor.GRAY + "Default Hunger", ChatColor.RESET + "" + ChatColor.GRAY
				+ "" + ChatColor.BOLD + "COST: " + ChatColor.RESET + "" + ChatColor.GRAY + "None", unlocked));
		hunger1On.setItemMeta(hunger1OnMeta);
		inv.setItem(19, hunger1On);

		int maxHunger = playerData.getMountMaxHungerLevel();

		switch (maxHunger) {
		case 8:
			ItemStack hunger8On = new ItemStack(Material.INK_SACK, 1, (short) 13);
			ItemMeta hunger8OnMeta = hunger8On.getItemMeta();
			hunger8OnMeta.setDisplayName(ChatColor.RESET + "" + ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + "RANK 8");
			hunger8OnMeta
					.setLore(Arrays.asList(ChatColor.RESET + "" + ChatColor.GRAY + "Default Hunger + 200%", ChatColor.RESET + ""
							+ ChatColor.GRAY + "" + ChatColor.BOLD + "COST: " + ChatColor.RESET + "" + ChatColor.GRAY + "20 stat points",
							unlocked));
			hunger8On.setItemMeta(hunger8OnMeta);
			inv.setItem(26, hunger8On);
		case 7:
			ItemStack hunger7On = new ItemStack(Material.INK_SACK, 1, (short) 13);
			ItemMeta hunger7OnMeta = hunger7On.getItemMeta();
			hunger7OnMeta.setDisplayName(ChatColor.RESET + "" + ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + "RANK 7");
			hunger7OnMeta
					.setLore(Arrays.asList(ChatColor.RESET + "" + ChatColor.GRAY + "Default Hunger + 150%", ChatColor.RESET + ""
							+ ChatColor.GRAY + "" + ChatColor.BOLD + "COST: " + ChatColor.RESET + "" + ChatColor.GRAY + "15 stat points",
							unlocked));
			hunger7On.setItemMeta(hunger7OnMeta);
			inv.setItem(25, hunger7On);
		case 6:
			ItemStack hunger6On = new ItemStack(Material.INK_SACK, 1, (short) 13);
			ItemMeta hunger6OnMeta = hunger6On.getItemMeta();
			hunger6OnMeta.setDisplayName(ChatColor.RESET + "" + ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + "RANK 6");
			hunger6OnMeta
					.setLore(Arrays.asList(ChatColor.RESET + "" + ChatColor.GRAY + "Default Hunger + 125%", ChatColor.RESET + ""
							+ ChatColor.GRAY + "" + ChatColor.BOLD + "COST: " + ChatColor.RESET + "" + ChatColor.GRAY + "11 stat points",
							unlocked));
			hunger6On.setItemMeta(hunger6OnMeta);
			inv.setItem(24, hunger6On);
		case 5:
			ItemStack hunger5On = new ItemStack(Material.INK_SACK, 1, (short) 13);
			ItemMeta hunger5OnMeta = hunger5On.getItemMeta();
			hunger5OnMeta.setDisplayName(ChatColor.RESET + "" + ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + "RANK 5");
			hunger5OnMeta.setLore(Arrays.asList(ChatColor.RESET + "" + ChatColor.GRAY + "Default Hunger + 100%", ChatColor.RESET + ""
					+ ChatColor.GRAY + "" + ChatColor.BOLD + "COST: " + ChatColor.RESET + "" + ChatColor.GRAY + "8 stat points", unlocked));
			hunger5On.setItemMeta(hunger5OnMeta);
			inv.setItem(23, hunger5On);
		case 4:
			ItemStack hunger4On = new ItemStack(Material.INK_SACK, 1, (short) 13);
			ItemMeta hunger4OnMeta = hunger4On.getItemMeta();
			hunger4OnMeta.setDisplayName(ChatColor.RESET + "" + ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + "RANK 4");
			hunger4OnMeta.setLore(Arrays.asList(ChatColor.RESET + "" + ChatColor.GRAY + "Default Hunger + 75%", ChatColor.RESET + ""
					+ ChatColor.GRAY + "" + ChatColor.BOLD + "COST: " + ChatColor.RESET + "" + ChatColor.GRAY + "5 stat points", unlocked));
			hunger4On.setItemMeta(hunger4OnMeta);
			inv.setItem(22, hunger4On);

		case 3:
			ItemStack hunger3On = new ItemStack(Material.INK_SACK, 1, (short) 13);
			ItemMeta hunger3OnMeta = hunger3On.getItemMeta();
			hunger3OnMeta.setDisplayName(ChatColor.RESET + "" + ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + "RANK 3");
			hunger3OnMeta.setLore(Arrays.asList(ChatColor.RESET + "" + ChatColor.GRAY + "Default Hunger + 50%", ChatColor.RESET + ""
					+ ChatColor.GRAY + "" + ChatColor.BOLD + "COST: " + ChatColor.RESET + "" + ChatColor.GRAY + "3 stat points", unlocked));
			hunger3On.setItemMeta(hunger3OnMeta);
			inv.setItem(21, hunger3On);
		case 2:
			ItemStack hunger2On = new ItemStack(Material.INK_SACK, 1, (short) 13);
			ItemMeta hunger2OnMeta = hunger2On.getItemMeta();
			hunger2OnMeta.setDisplayName(ChatColor.RESET + "" + ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + "RANK 2");
			hunger2OnMeta.setLore(Arrays.asList(ChatColor.RESET + "" + ChatColor.GRAY + "Default Hunger + 25%", ChatColor.RESET + ""
					+ ChatColor.GRAY + "" + ChatColor.BOLD + "COST: " + ChatColor.RESET + "" + ChatColor.GRAY + "1 stat point", unlocked));
			hunger2On.setItemMeta(hunger2OnMeta);
			inv.setItem(20, hunger2On);
		}

		List<String> unlockedColors = playerData.getUnlockedMountColors();
		ItemStack brownColorUnl = new ItemStack(Material.WOOD, 1, (short) 0);
		ItemMeta brownColorUnlMeta = brownColorUnl.getItemMeta();
		brownColorUnlMeta.setDisplayName(ChatColor.RESET + "" + ChatColor.GRAY + ChatColor.BOLD + "Brown");
		brownColorUnlMeta.setLore(Arrays
				.asList(ChatColor.RESET + "" + ChatColor.GRAY + "Trash Color", ChatColor.RESET + "" + ChatColor.GRAY + ChatColor.UNDERLINE
						+ "Left-click" + ChatColor.RESET + "" + ChatColor.GRAY + " to equip.", " ", unlocked));
		brownColorUnl.setItemMeta(brownColorUnlMeta);
		if (playerData.getMountColor().equals(Color.BROWN.toString()))
			brownColorUnl = GeneralUtil.addGlow(brownColorUnl);
		inv.setItem(27, brownColorUnl);

		if (unlockedColors.contains(Color.CHESTNUT.toString())) {
			ItemStack chestnutColorUnl = new ItemStack(Material.LOG, 1, (short) 0);
			ItemMeta chestnutColorUnlMeta = chestnutColorUnl.getItemMeta();
			chestnutColorUnlMeta.setDisplayName(ChatColor.RESET + "" + ChatColor.WHITE + ChatColor.BOLD + "Chestnut");
			chestnutColorUnlMeta.setLore(Arrays.asList(ChatColor.RESET + "" + ChatColor.WHITE + "Common Color", ChatColor.RESET + ""
					+ ChatColor.WHITE + ChatColor.UNDERLINE + "Left-click" + ChatColor.RESET + "" + ChatColor.WHITE + " to equip.", " ",
					unlocked));
			chestnutColorUnl.setItemMeta(chestnutColorUnlMeta);
			if (playerData.getMountColor().equals(Color.CHESTNUT.toString()))
				chestnutColorUnl = GeneralUtil.addGlow(chestnutColorUnl);
			inv.setItem(28, chestnutColorUnl);
		} else {
			ItemStack chestnutColorL = new ItemStack(Material.LOG, 1, (short) 0);
			ItemMeta chestnutColorLMeta = chestnutColorL.getItemMeta();
			chestnutColorLMeta.setDisplayName(ChatColor.RESET + "" + ChatColor.WHITE + ChatColor.BOLD + "Chestnut");
			chestnutColorLMeta.setLore(Arrays.asList(ChatColor.RESET + "" + ChatColor.WHITE + "Common Color", ChatColor.RESET + ""
					+ ChatColor.WHITE + ChatColor.UNDERLINE + "Left-click" + ChatColor.RESET + "" + ChatColor.WHITE + " to equip.", " ",
					locked));
			chestnutColorL.setItemMeta(chestnutColorLMeta);
			inv.setItem(28, chestnutColorL);
		}
		if (unlockedColors.contains(Color.CREAMY.toString())) {
			ItemStack creamyColorUnl = new ItemStack(Material.WOOD, 1, (short) 2);
			ItemMeta creamyColorUnlMeta = creamyColorUnl.getItemMeta();
			creamyColorUnlMeta.setDisplayName(ChatColor.RESET + "" + ChatColor.GREEN + ChatColor.BOLD + "Creamy");
			creamyColorUnlMeta.setLore(Arrays.asList(ChatColor.RESET + "" + ChatColor.GREEN + "Uncommon Color", ChatColor.RESET + ""
					+ ChatColor.GREEN + ChatColor.UNDERLINE + "Left-click" + ChatColor.RESET + "" + ChatColor.GREEN + " to equip.", " ",
					unlocked));
			creamyColorUnl.setItemMeta(creamyColorUnlMeta);
			if (playerData.getMountColor().equals(Color.CREAMY.toString()))
				creamyColorUnl = GeneralUtil.addGlow(creamyColorUnl);
			inv.setItem(29, creamyColorUnl);
		} else {
			ItemStack creamyColorL = new ItemStack(Material.WOOD, 1, (short) 2);
			ItemMeta creamyColorLMeta = creamyColorL.getItemMeta();
			creamyColorLMeta.setDisplayName(ChatColor.RESET + "" + ChatColor.GREEN + ChatColor.BOLD + "Creamy");
			creamyColorLMeta.setLore(Arrays.asList(ChatColor.RESET + "" + ChatColor.GREEN + "Uncommon Color", ChatColor.RESET + ""
					+ ChatColor.GREEN + ChatColor.UNDERLINE + "Left-click" + ChatColor.RESET + "" + ChatColor.GREEN + " to equip.", " ",
					locked));
			creamyColorL.setItemMeta(creamyColorLMeta);
			inv.setItem(29, creamyColorL);
		}
		if (unlockedColors.contains(Color.DARK_BROWN.toString())) {
			ItemStack darkBrownColorUnl = new ItemStack(Material.WOOL, 1, (short) 12);
			ItemMeta darkBrownColorUnlMeta = darkBrownColorUnl.getItemMeta();
			darkBrownColorUnlMeta.setDisplayName(ChatColor.RESET + "" + ChatColor.BLUE + ChatColor.BOLD + "Dark Brown");
			darkBrownColorUnlMeta.setLore(Arrays.asList(ChatColor.RESET + "" + ChatColor.BLUE + "Rare Color", ChatColor.RESET + ""
					+ ChatColor.BLUE + ChatColor.UNDERLINE + "Left-click" + ChatColor.RESET + "" + ChatColor.BLUE + " to equip.", " ",
					unlocked));
			darkBrownColorUnl.setItemMeta(darkBrownColorUnlMeta);
			if (playerData.getMountColor().equals(Color.DARK_BROWN.toString()))
				darkBrownColorUnl = GeneralUtil.addGlow(darkBrownColorUnl);
			inv.setItem(30, darkBrownColorUnl);
		} else {
			ItemStack darkBrownColorL = new ItemStack(Material.WOOL, 1, (short) 12);
			ItemMeta darkBrownColorLMeta = darkBrownColorL.getItemMeta();
			darkBrownColorLMeta.setDisplayName(ChatColor.RESET + "" + ChatColor.BLUE + ChatColor.BOLD + "Dark Brown");
			darkBrownColorLMeta.setLore(Arrays.asList(ChatColor.RESET + "" + ChatColor.BLUE + "Rare Color", ChatColor.RESET + ""
					+ ChatColor.BLUE + ChatColor.UNDERLINE + "Left-click" + ChatColor.RESET + "" + ChatColor.BLUE + " to equip.", " ",
					locked));
			darkBrownColorL.setItemMeta(darkBrownColorLMeta);
			inv.setItem(30, darkBrownColorL);
		}
		if (unlockedColors.contains(Color.GRAY.toString())) {
			ItemStack grayColorUnl = new ItemStack(Material.WOOL, 1, (short) 7);
			ItemMeta grayColorUnlMeta = grayColorUnl.getItemMeta();
			grayColorUnlMeta.setDisplayName(ChatColor.RESET + "" + ChatColor.DARK_PURPLE + ChatColor.BOLD + "Gray");
			grayColorUnlMeta.setLore(Arrays.asList(ChatColor.RESET + "" + ChatColor.DARK_PURPLE + "Rare Color", ChatColor.RESET + ""
					+ ChatColor.DARK_PURPLE + ChatColor.UNDERLINE + "Left-click" + ChatColor.RESET + "" + ChatColor.DARK_PURPLE
					+ " to equip.", " ", unlocked));
			grayColorUnl.setItemMeta(grayColorUnlMeta);
			if (playerData.getMountColor().equals(Color.GRAY.toString()))
				grayColorUnl = GeneralUtil.addGlow(grayColorUnl);
			inv.setItem(31, grayColorUnl);
		} else {
			ItemStack grayColorL = new ItemStack(Material.WOOL, 1, (short) 7);
			ItemMeta grayColorLMeta = grayColorL.getItemMeta();
			grayColorLMeta.setDisplayName(ChatColor.RESET + "" + ChatColor.DARK_PURPLE + ChatColor.BOLD + "Gray");
			grayColorLMeta.setLore(Arrays.asList(ChatColor.RESET + "" + ChatColor.DARK_PURPLE + "Rare Color", ChatColor.RESET + ""
					+ ChatColor.DARK_PURPLE + ChatColor.UNDERLINE + "Left-click" + ChatColor.RESET + "" + ChatColor.DARK_PURPLE
					+ " to equip.", " ", locked));
			grayColorL.setItemMeta(grayColorLMeta);
			inv.setItem(31, grayColorL);
		}
		if (unlockedColors.contains(Color.BLACK.toString())) {
			ItemStack blackColorUnl = new ItemStack(Material.WOOL, 1, (short) 15);
			ItemMeta blackColorUnlMeta = blackColorUnl.getItemMeta();
			blackColorUnlMeta.setDisplayName(ChatColor.RESET + "" + ChatColor.GOLD + ChatColor.BOLD + "Black");
			blackColorUnlMeta.setLore(Arrays.asList(ChatColor.RESET + "" + ChatColor.GOLD + "Legendary Color", ChatColor.RESET + ""
					+ ChatColor.GOLD + ChatColor.UNDERLINE + "Left-click" + ChatColor.RESET + "" + ChatColor.GOLD + " to equip.", " ",
					unlocked));
			blackColorUnl.setItemMeta(blackColorUnlMeta);
			if (playerData.getMountColor().equals(Color.BLACK.toString()))
				blackColorUnl = GeneralUtil.addGlow(blackColorUnl);
			inv.setItem(32, blackColorUnl);
		} else {
			ItemStack blackColorL = new ItemStack(Material.WOOL, 1, (short) 15);
			ItemMeta blackColorLMeta = blackColorL.getItemMeta();
			blackColorLMeta.setDisplayName(ChatColor.RESET + "" + ChatColor.GOLD + ChatColor.BOLD + "Black");
			blackColorLMeta.setLore(Arrays.asList(ChatColor.RESET + "" + ChatColor.GOLD + "Legendary Color", ChatColor.RESET + ""
					+ ChatColor.GOLD + ChatColor.UNDERLINE + "Left-click" + ChatColor.RESET + "" + ChatColor.GOLD + " to equip.", " ",
					locked));
			blackColorL.setItemMeta(blackColorLMeta);
			inv.setItem(32, blackColorL);
		}
		if (unlockedColors.contains(Color.WHITE.toString())) {
			ItemStack whiteColorUnl = new ItemStack(Material.WOOL, 1, (short) 0);
			ItemMeta whiteColorUnlMeta = whiteColorUnl.getItemMeta();
			whiteColorUnlMeta.setDisplayName(ChatColor.RESET + "" + ChatColor.GOLD + ChatColor.BOLD + "White");
			whiteColorUnlMeta.setLore(Arrays.asList(ChatColor.RESET + "" + ChatColor.GOLD + "Legendary Color", ChatColor.RESET + ""
					+ ChatColor.GOLD + ChatColor.UNDERLINE + "Left-click" + ChatColor.RESET + "" + ChatColor.GOLD + " to equip.", " ",
					unlocked));
			whiteColorUnl.setItemMeta(whiteColorUnlMeta);
			if (playerData.getMountColor().equals(Color.WHITE.toString()))
				whiteColorUnl = GeneralUtil.addGlow(whiteColorUnl);
			inv.setItem(33, whiteColorUnl);
		} else {
			ItemStack whiteColorL = new ItemStack(Material.WOOL, 1, (short) 0);
			ItemMeta whiteColorLMeta = whiteColorL.getItemMeta();
			whiteColorLMeta.setDisplayName(ChatColor.RESET + "" + ChatColor.GOLD + ChatColor.BOLD + "White");
			whiteColorLMeta.setLore(Arrays.asList(ChatColor.RESET + "" + ChatColor.GOLD + "Legendary Color", ChatColor.RESET + ""
					+ ChatColor.GOLD + ChatColor.UNDERLINE + "Left-click" + ChatColor.RESET + "" + ChatColor.GOLD + " to equip.", " ",
					locked));
			whiteColorL.setItemMeta(whiteColorLMeta);
			inv.setItem(33, whiteColorL);
		}
		List<String> unlockedSizes = playerData.getUnlockedMountSizes();
		ItemStack normalSizeUnl = new ItemStack(Material.LEATHER, 1, (short) 0);
		ItemMeta normalSizeUnlMeta = normalSizeUnl.getItemMeta();
		normalSizeUnlMeta.setDisplayName(ChatColor.RESET + "" + ChatColor.DARK_PURPLE + ChatColor.BOLD + "Normal Size");
		normalSizeUnlMeta.setLore(Arrays.asList(ChatColor.RESET + "" + ChatColor.GRAY + "Large Mount Size", lmbEquip, " ", unlocked));
		normalSizeUnl.setItemMeta(normalSizeUnlMeta);
		if (playerData.getMountSize().equals(Size.NORMAL.toString()))
			normalSizeUnl = GeneralUtil.addGlow(normalSizeUnl);
		inv.setItem(34, normalSizeUnl);

		if (unlockedSizes.contains(Size.TINY.toString())) {
			ItemStack tinySizeUnl = new ItemStack(Material.LEATHER, 1, (short) 0);
			ItemMeta tinySizeUnlMeta = tinySizeUnl.getItemMeta();
			tinySizeUnlMeta.setDisplayName(ChatColor.RESET + "" + ChatColor.DARK_PURPLE + ChatColor.BOLD + "Tiny Size");
			tinySizeUnlMeta.setLore(Arrays.asList(ChatColor.RESET + "" + ChatColor.GRAY + "Tiny Mount Size", lmbEquip, " ", unlocked));
			tinySizeUnl.setItemMeta(tinySizeUnlMeta);
			if (playerData.getMountSize().equals(Size.TINY.toString()))
				tinySizeUnl = GeneralUtil.addGlow(tinySizeUnl);
			inv.setItem(35, tinySizeUnl);
		} else {
			ItemStack tinySizeL = new ItemStack(Material.LEATHER, 1, (short) 0);
			ItemMeta tinySizeLMeta = tinySizeL.getItemMeta();
			tinySizeLMeta.setDisplayName(ChatColor.RESET + "" + ChatColor.DARK_PURPLE + ChatColor.BOLD + "Tiny Size");
			tinySizeLMeta.setLore(Arrays.asList(ChatColor.RESET + "" + ChatColor.GRAY + "Tiny Mount Size", lmbEquip, " ", locked));
			tinySizeL.setItemMeta(tinySizeLMeta);
			inv.setItem(35, tinySizeL);
		}

		List<String> unlockedStyles = playerData.getUnlockedMountStyles();
		ItemStack noMarkingsUnl = new ItemStack(Material.MONSTER_EGG, 1, (short) 100);
		ItemMeta noMarkingsUnlMeta = noMarkingsUnl.getItemMeta();
		noMarkingsUnlMeta.setDisplayName(ChatColor.RESET + "" + ChatColor.DARK_PURPLE + ChatColor.BOLD + "No Markings");
		noMarkingsUnlMeta.setLore(Arrays.asList(ChatColor.RESET + "" + ChatColor.GRAY + "Clean style.", lmbEquip, " ", unlocked));
		noMarkingsUnl.setItemMeta(noMarkingsUnlMeta);
		if (playerData.getMountStyle().equals(Style.NONE.toString()))
			noMarkingsUnl = GeneralUtil.addGlow(noMarkingsUnl);
		inv.setItem(36, noMarkingsUnl);

		if (unlockedStyles.contains(Style.BLACK_DOTS.toString())) {
			ItemStack blackDotsUnl = new ItemStack(Material.MONSTER_EGG, 1, (short) 51);
			ItemMeta blackDotsUnlMeta = blackDotsUnl.getItemMeta();
			blackDotsUnlMeta.setDisplayName(ChatColor.RESET + "" + ChatColor.DARK_PURPLE + ChatColor.BOLD + "Black Dots");
			blackDotsUnlMeta.setLore(Arrays.asList(ChatColor.RESET + "" + ChatColor.GRAY + "Black dotted style.", lmbEquip, " ", unlocked));
			blackDotsUnl.setItemMeta(blackDotsUnlMeta);
			if (playerData.getMountStyle().equals(Style.BLACK_DOTS.toString()))
				blackDotsUnl = GeneralUtil.addGlow(blackDotsUnl);
			inv.setItem(37, blackDotsUnl);
		} else {
			ItemStack blackDotsL = new ItemStack(Material.MONSTER_EGG, 1, (short) 51);
			ItemMeta blackDotsLMeta = blackDotsL.getItemMeta();
			blackDotsLMeta.setDisplayName(ChatColor.RESET + "" + ChatColor.DARK_PURPLE + ChatColor.BOLD + "Black Dots");
			blackDotsLMeta.setLore(Arrays.asList(ChatColor.RESET + "" + ChatColor.GRAY + "Black dotted style.", lmbEquip, " ", locked));
			blackDotsL.setItemMeta(blackDotsLMeta);
			inv.setItem(37, blackDotsL);
		}

		if (unlockedStyles.contains(Style.WHITE.toString())) {
			ItemStack whiteStyleUnl = new ItemStack(Material.MONSTER_EGG, 1, (short) 56);
			ItemMeta whiteStyleUnlMeta = whiteStyleUnl.getItemMeta();
			whiteStyleUnlMeta.setDisplayName(ChatColor.RESET + "" + ChatColor.DARK_PURPLE + ChatColor.BOLD + "White Style");
			whiteStyleUnlMeta.setLore(Arrays.asList(ChatColor.RESET + "" + ChatColor.GRAY + "Style with white stripes.", lmbEquip, " ",
					unlocked));
			whiteStyleUnl.setItemMeta(whiteStyleUnlMeta);
			if (playerData.getMountStyle().equals(Style.WHITE.toString()))
				whiteStyleUnl = GeneralUtil.addGlow(whiteStyleUnl);
			inv.setItem(38, whiteStyleUnl);
		} else {
			ItemStack whiteStyleL = new ItemStack(Material.MONSTER_EGG, 1, (short) 56);
			ItemMeta whiteStyleLMeta = whiteStyleL.getItemMeta();
			whiteStyleLMeta.setDisplayName(ChatColor.RESET + "" + ChatColor.DARK_PURPLE + ChatColor.BOLD + "White Style");
			whiteStyleLMeta.setLore(Arrays.asList(ChatColor.RESET + "" + ChatColor.GRAY + "Style with white stripes.", lmbEquip, " ",
					locked));
			whiteStyleL.setItemMeta(whiteStyleLMeta);
			inv.setItem(38, whiteStyleL);
		}
		if (unlockedStyles.contains(Style.WHITE_DOTS.toString())) {
			ItemStack whiteDotsUnl = new ItemStack(Material.MONSTER_EGG, 1, (short) 95);
			ItemMeta whiteDotsUnlMeta = whiteDotsUnl.getItemMeta();
			whiteDotsUnlMeta.setDisplayName(ChatColor.RESET + "" + ChatColor.DARK_PURPLE + ChatColor.BOLD + "White Dots");
			whiteDotsUnlMeta.setLore(Arrays.asList(ChatColor.RESET + "" + ChatColor.GRAY + "White dotted style.", lmbEquip, " ", unlocked));
			whiteDotsUnl.setItemMeta(whiteDotsUnlMeta);
			if (playerData.getMountStyle().equals(Style.WHITE_DOTS.toString()))
				whiteDotsUnl = GeneralUtil.addGlow(whiteDotsUnl);
			inv.setItem(39, whiteDotsUnl);
		} else {
			ItemStack whiteDotsL = new ItemStack(Material.MONSTER_EGG, 1, (short) 95);
			ItemMeta whiteDotsLMeta = whiteDotsL.getItemMeta();
			whiteDotsLMeta.setDisplayName(ChatColor.RESET + "" + ChatColor.DARK_PURPLE + ChatColor.BOLD + "White Dots");
			whiteDotsLMeta.setLore(Arrays.asList(ChatColor.RESET + "" + ChatColor.GRAY + "White dotted style.", lmbEquip, " ", locked));
			whiteDotsL.setItemMeta(whiteDotsLMeta);
			inv.setItem(39, whiteDotsL);
		}
		if (unlockedStyles.contains(Style.WHITEFIELD.toString())) {
			ItemStack whitefieldUnl = new ItemStack(Material.MONSTER_EGG, 1, (short) 91);
			ItemMeta whitefieldUnlMeta = whitefieldUnl.getItemMeta();
			whitefieldUnlMeta.setDisplayName(ChatColor.RESET + "" + ChatColor.DARK_PURPLE + ChatColor.BOLD + "Whitefield");
			whitefieldUnlMeta.setLore(Arrays.asList(ChatColor.RESET + "" + ChatColor.GRAY + "Milky splotched style.", lmbEquip, " ",
					unlocked));
			whitefieldUnl.setItemMeta(whitefieldUnlMeta);
			if (playerData.getMountStyle().equals(Style.WHITEFIELD.toString()))
				whitefieldUnl = GeneralUtil.addGlow(whitefieldUnl);
			inv.setItem(40, whitefieldUnl);
		} else {
			ItemStack whitefieldL = new ItemStack(Material.MONSTER_EGG, 1, (short) 91);
			ItemMeta whitefieldLMeta = whitefieldL.getItemMeta();
			whitefieldLMeta.setDisplayName(ChatColor.RESET + "" + ChatColor.DARK_PURPLE + ChatColor.BOLD + "Whitefield");
			whitefieldLMeta.setLore(Arrays.asList(ChatColor.RESET + "" + ChatColor.GRAY + "Milky splotched style.", lmbEquip, " ", locked));
			whitefieldL.setItemMeta(whitefieldLMeta);
			inv.setItem(40, whitefieldL);
		}
		List<String> unlockedSkins = playerData.getUnlockedMountVariants();
		ItemStack normalSkinUnl = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
		ItemMeta normalSkinUnlMeta = normalSkinUnl.getItemMeta();
		normalSkinUnlMeta.setDisplayName(ChatColor.RESET + "" + ChatColor.DARK_PURPLE + ChatColor.BOLD + "Normal Horse");
		normalSkinUnlMeta.setLore(Arrays.asList(ChatColor.RESET + "" + ChatColor.GRAY + "The default skin for your", ChatColor.RESET + ""
				+ ChatColor.GRAY + "horse (cosmetic only).", lmbEquip, " ", unlocked));
		normalSkinUnl.setItemMeta(normalSkinUnlMeta);
		if (playerData.getMountVariant().equals(Variant.HORSE.toString()))
			normalSkinUnl = GeneralUtil.addGlow(normalSkinUnl);
		inv.setItem(41, normalSkinUnl);

		if (unlockedSkins.contains(Variant.UNDEAD_HORSE.toString())) {
			ItemStack zombieSkinUnl = new ItemStack(Material.SKULL_ITEM, 1, (short) 2);
			ItemMeta zombieSkinUnlMeta = zombieSkinUnl.getItemMeta();
			zombieSkinUnlMeta.setDisplayName(ChatColor.RESET + "" + ChatColor.DARK_PURPLE + ChatColor.BOLD + "Zombie Horse");
			zombieSkinUnlMeta.setLore(Arrays.asList(ChatColor.RESET + "" + ChatColor.GRAY + "A zombie skin for your", ChatColor.RESET + ""
					+ ChatColor.GRAY + "horse (cosmetic only).", lmbEquip, " ", unlocked));
			zombieSkinUnl.setItemMeta(zombieSkinUnlMeta);
			if (playerData.getMountVariant().equals(Variant.UNDEAD_HORSE.toString()))
				zombieSkinUnl = GeneralUtil.addGlow(zombieSkinUnl);
			inv.setItem(42, zombieSkinUnl);
		} else {
			ItemStack zombieSkinL = new ItemStack(Material.SKULL_ITEM, 1, (short) 2);
			ItemMeta zombieSkinLMeta = zombieSkinL.getItemMeta();
			zombieSkinLMeta.setDisplayName(ChatColor.RESET + "" + ChatColor.DARK_PURPLE + ChatColor.BOLD + "Zombie Horse");
			zombieSkinLMeta.setLore(Arrays.asList(ChatColor.RESET + "" + ChatColor.GRAY + "A zombie skin for your", ChatColor.RESET + ""
					+ ChatColor.GRAY + "horse (cosmetic only).", lmbEquip, " ", locked));
			zombieSkinL.setItemMeta(zombieSkinLMeta);
			inv.setItem(42, zombieSkinL);
		}

		if (unlockedSkins.contains(Variant.SKELETON_HORSE.toString())) {
			ItemStack skeletonSkinUnl = new ItemStack(Material.SKULL_ITEM, 1, (short) 0);
			ItemMeta skeletonSkinUnlMeta = skeletonSkinUnl.getItemMeta();
			skeletonSkinUnlMeta.setDisplayName(ChatColor.RESET + "" + ChatColor.DARK_PURPLE + ChatColor.BOLD + "Skeleton Horse");
			skeletonSkinUnlMeta.setLore(Arrays.asList(ChatColor.RESET + "" + ChatColor.GRAY + "A skeleton skin for your", ChatColor.RESET
					+ "" + ChatColor.GRAY + "horse (cosmetic only).", lmbEquip, " ", unlocked));
			skeletonSkinUnl.setItemMeta(skeletonSkinUnlMeta);
			if (playerData.getMountVariant().equals(Variant.SKELETON_HORSE.toString()))
				skeletonSkinUnl = GeneralUtil.addGlow(skeletonSkinUnl);
			inv.setItem(43, skeletonSkinUnl);
		} else {
			ItemStack skeletonSkinL = new ItemStack(Material.SKULL_ITEM, 1, (short) 0);
			ItemMeta skeletonSkinLMeta = skeletonSkinL.getItemMeta();
			skeletonSkinLMeta.setDisplayName(ChatColor.RESET + "" + ChatColor.DARK_PURPLE + ChatColor.BOLD + "Skeleton Horse");
			skeletonSkinLMeta.setLore(Arrays.asList(ChatColor.RESET + "" + ChatColor.GRAY + "A skeleton skin for your", ChatColor.RESET
					+ "" + ChatColor.GRAY + "horse (cosmetic only).", lmbEquip, " ", locked));
			skeletonSkinL.setItemMeta(skeletonSkinLMeta);
			inv.setItem(43, skeletonSkinL);
		}

		if (unlockedSkins.contains(Variant.DONKEY.toString())) {
			ItemStack muleSkinUnl = new ItemStack(Material.SKULL_ITEM, 1, (short) 1);
			ItemMeta muleSkinUnlMeta = muleSkinUnl.getItemMeta();
			muleSkinUnlMeta.setDisplayName(ChatColor.RESET + "" + ChatColor.DARK_PURPLE + ChatColor.BOLD + "Mule/Donkey");
			muleSkinUnlMeta.setLore(Arrays.asList(ChatColor.RESET + "" + ChatColor.GRAY + "A mule/donkey skin for your", ChatColor.RESET
					+ "" + ChatColor.GRAY + "mount (cosmetic only).", ChatColor.RESET + "" + ChatColor.DARK_PURPLE + ""
					+ ChatColor.UNDERLINE + "Left-click" + ChatColor.RESET + "" + ChatColor.GRAY + " to equip and switch", ChatColor.RESET
					+ "" + ChatColor.GRAY + "between donkey and mule.", " ", unlocked));
			if (playerData.getMountVariant().equals(Variant.DONKEY.toString()))
				muleSkinUnl = GeneralUtil.addGlow(muleSkinUnl);
			muleSkinUnl.setItemMeta(muleSkinUnlMeta);
			inv.setItem(44, muleSkinUnl);
		} else {
			ItemStack muleSkinL = new ItemStack(Material.SKULL_ITEM, 1, (short) 1);
			ItemMeta muleSkinLMeta = muleSkinL.getItemMeta();
			muleSkinLMeta.setDisplayName(ChatColor.RESET + "" + ChatColor.DARK_PURPLE + ChatColor.BOLD + "Mule/Donkey");
			muleSkinLMeta.setLore(Arrays.asList(ChatColor.RESET + "" + ChatColor.GRAY + "A mule/donkey skin for your", ChatColor.RESET + ""
					+ ChatColor.GRAY + "mount (cosmetic only).", ChatColor.RESET + "" + ChatColor.DARK_PURPLE + "" + ChatColor.UNDERLINE
					+ "Left-click" + ChatColor.RESET + "" + ChatColor.GRAY + " to equip and switch", ChatColor.RESET + "" + ChatColor.GRAY
					+ "between donkey and mule.", " ", locked));
			muleSkinL.setItemMeta(muleSkinLMeta);
			inv.setItem(44, muleSkinL);
		}

		player.openInventory(inv);
	}

	public static void handleClick(InventoryClickEvent event) {
		event.setCancelled(true);
		if (event.getWhoClicked() instanceof Player) {
			Player player = (Player) event.getWhoClicked();
			PlayerData playerData = PlayerData.getData(player);
			if (event.getRawSlot() < 27) { // Leveling mount stats
				int statNumber = event.getRawSlot() / 9;
				int level = event.getRawSlot() % 9;
				int apCost = MountManager.getAPCostForNextLevel(level);
				switch (statNumber) {
				case 0:
					if (playerData.getMountSpeed() + 1 == level) {
						if (playerData.getMountStatPoints() <= apCost) {
							playerData.modifyMountStatPoints(apCost);
							playerData.setMountSpeed(level);
						}
					}
					break;
				case 1:
					if (playerData.getMountJump() + 1 == level) {
						if (playerData.getMountStatPoints() <= apCost) {
							playerData.modifyMountStatPoints(apCost);
							playerData.setMountJump(level);
						}
					}
					break;
				case 2:
					if (playerData.getMountMaxHungerLevel() + 1 == level) {
						if (playerData.getMountStatPoints() <= apCost) {
							playerData.modifyMountStatPoints(apCost);
							playerData.setMountMaxHungerLevel(level);
						}
					}
					break;
				}
			}
		}
	}
}

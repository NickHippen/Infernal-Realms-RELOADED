package net.infernalrealms.gui;

import net.infernalrealms.player.PlayerData;
import net.infernalrealms.skills.archer.MagnumShot;
import net.infernalrealms.skills.archer.VenomousArrow;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class HotbarBindGUI {

	public static void open(Player player) {
		PlayerData playerData = PlayerData.getData(player);
		Inventory inv = player.getServer().createInventory(player, 27, "Hotbar Bindings");

		ItemStack[] binds = new ItemStack[8];
		for (int i = 0; i < 8; i++) {
			binds[i] = playerData.getConfig().getItemStack(playerData.getCurrentCharacterSlot() + ".Inventory.Main." + (i + 1));
		}

		// Black Panes
		ItemStack blankGlassPane = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 15);
		ItemMeta blankGlassPaneMeta = blankGlassPane.getItemMeta();
		blankGlassPaneMeta.setDisplayName(" ");
		blankGlassPane.setItemMeta(blankGlassPaneMeta);
		for (int l : new int[] { 9, 10, 11, 15, 16, 17, 18, 19, 20, 24, 26 })
			inv.setItem(l, blankGlassPane);

		// Skills
		switch (playerData.getPlayerSuperClass()) {
		case "Archer":
			inv.setItem(12, new MagnumShot(player).getIcon());
			inv.setItem(13, new VenomousArrow(player).getIcon());
		}

		// Top row (binds)
		ItemStack unboundSlot = new ItemStack(Material.IRON_FENCE, 1);
		ItemMeta unboundSlotMeta = unboundSlot.getItemMeta();
		unboundSlotMeta.setDisplayName(ChatColor.RESET + "" + ChatColor.GRAY + "UNBOUND");
		unboundSlot.setItemMeta(unboundSlotMeta);
		inv.setItem(0, player.getInventory().getItem(0));
		for (int i = 0; i < 8; i++)
			inv.setItem(i + 1, binds[i] != null || binds[i].getType() != Material.AIR ? binds[i] : unboundSlot);

		player.openInventory(inv);
	}
}

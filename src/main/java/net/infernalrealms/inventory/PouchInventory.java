package net.infernalrealms.inventory;

import net.infernalrealms.player.PlayerData;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class PouchInventory {

	private Player player;
	private Inventory inventory;

	public PouchInventory(Player player, String pouchName, int size, int slot) {
		this.player = player;
		this.inventory = player.getServer().createInventory(player, size, "Pouch: " + pouchName);
		PlayerData playerData = PlayerData.getData(player);
		for (int i = 0; i < size; i++) {
			ItemStack item = playerData.getConfig().getItemStack(
					playerData.getCurrentCharacterSlot() + ".Inventory." + slot + "." + pouchName + "." + i);
			this.inventory.setItem(i, item);
		}
	}

	public void open() {
		this.player.openInventory(inventory);
	}
}

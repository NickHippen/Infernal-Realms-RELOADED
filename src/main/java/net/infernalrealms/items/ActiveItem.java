package net.infernalrealms.items;

import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class ActiveItem implements Listener {

	private ItemStack bukkitItem;

	public ActiveItem() {}

	public ActiveItem(ItemStack item) {
		this.setBukkitItem(item);
	}

	public boolean onUse(InventoryClickEvent event) {
		if (event.getClick() != ClickType.RIGHT || !(event.getWhoClicked() instanceof Player)) {
			return false;
		}
		if (event.getCurrentItem() == null || !event.getCurrentItem().isSimilar(bukkitItem)) {
			return false;
		}
		return true;
	}

	public ItemStack getBukkitItem() {
		return bukkitItem;
	}

	public void setBukkitItem(ItemStack bukkitItem) {
		this.bukkitItem = bukkitItem;
	}

}

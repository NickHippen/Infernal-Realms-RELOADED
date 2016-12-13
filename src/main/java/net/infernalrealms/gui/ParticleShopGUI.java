package net.infernalrealms.gui;

import net.infernalrealms.items.GeneralItems;
import net.infernalrealms.player.AestheticType;
import net.infernalrealms.player.PlayerData;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

public class ParticleShopGUI implements Listener {

	private static final String TITLE_START = ChatColor.AQUA + "" + ChatColor.BOLD + " Current I.P: " + ChatColor.DARK_GRAY
			+ ChatColor.BOLD;

	public static void open(Player player) {
		PlayerData playerData = PlayerData.getData(player);
		Inventory inv = Bukkit.createInventory(player, (((AestheticType.values().length - 1) / 9) + 1) * 9,
				TITLE_START + playerData.getPremiumMoney());

		for (int i = 1; i <= inv.getSize(); i++) {
			if (i < AestheticType.values().length) {
				AestheticType type = AestheticType.values()[i];
				inv.setItem(i - 1, type.getShopIcon());
			} else {
				inv.setItem(i - 1, GeneralItems.BLANK_BLACK_GLASS_PANE);
			}
		}

		player.openInventory(inv);
	}

	@EventHandler
	public void onGUIClick(InventoryClickEvent event) {
		if (!event.getInventory().getTitle().contains(TITLE_START) || !(event.getWhoClicked() instanceof Player)) {
			return;
		}
		Player player = (Player) event.getWhoClicked();
		event.setCancelled(true);
		if (event.getRawSlot() + 1 >= AestheticType.values().length || event.getRawSlot() < 0) {
			return;
		}
		AestheticType.values()[event.getRawSlot() + 1].purchase(player);
	}

}

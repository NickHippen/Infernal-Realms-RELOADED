package net.infernalrealms.gui;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Achievement;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_9_R1.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_9_R1.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerAchievementAwardedEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import net.minecraft.server.v1_9_R1.PacketPlayOutSetSlot;

public class PlayerInventoryListener implements Listener {

	@EventHandler
	public void onJoin(PlayerJoinEvent event) {
		event.getPlayer().removeAchievement(Achievement.OPEN_INVENTORY);
	}

	@EventHandler
	public void onPlayerInventoryOpen(PlayerAchievementAwardedEvent event) {
		if (event.getAchievement().equals(Achievement.OPEN_INVENTORY)) { // Player opens their personal inventory
			event.setCancelled(true);
			ItemStack button = new ItemStack(Material.INK_SACK, 1, (short) 10);
			ItemMeta buttonMeta = button.getItemMeta();
			buttonMeta.setDisplayName(ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + "Button Hub");
			List<String> buttonLore = new ArrayList<>();
			buttonLore.add(ChatColor.GRAY + "Click here to view");
			buttonLore.add(ChatColor.GRAY + "the button hub.");
			buttonMeta.setLore(buttonLore);
			button.setItemMeta(buttonMeta);
			net.minecraft.server.v1_9_R1.ItemStack nmsButton = CraftItemStack.asNMSCopy(button);
			((CraftPlayer) event.getPlayer()).getHandle().playerConnection.sendPacket(new PacketPlayOutSetSlot(0, 0, nmsButton));
		}
	}

	@EventHandler
	public void onInventoryClick(InventoryClickEvent event) {
		if (!(event.getWhoClicked() instanceof Player)) {
			return;
		}
		Player player = (Player) event.getWhoClicked();
		if (event.getInventory().getType() == InventoryType.CRAFTING) {
			if (event.getRawSlot() < 5 && event.getRawSlot() >= 0) {
				event.setCancelled(true);
			}
			if (event.getRawSlot() == 0) {
				ButtonHubGUI.open(player);
			}
		}
	}

}

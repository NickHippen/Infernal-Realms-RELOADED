package net.infernalrealms.items;

import java.util.ArrayList;
import java.util.List;

import net.infernalrealms.npc.TraitShopkeeper;
import net.infernalrealms.util.GeneralUtil;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemEtherealMerchantCoupon extends ActiveItem {

	public ItemEtherealMerchantCoupon() {
		super(generateItem());
	}

	@Override
	@EventHandler
	public boolean onUse(InventoryClickEvent event) {
		if (!super.onUse(event)) {
			return false;
		}
		Player player = (Player) event.getWhoClicked();
		TraitShopkeeper.openSellShop(player);
		if (!GeneralUtil.takeOne(event.getCurrentItem())) {
			event.setCurrentItem(null);
		}
		event.setCancelled(true);
		return true;
	}

	public static ItemStack generateItem() {
		ItemStack item = new ItemStack(Material.PAPER, 1, (short) 0);
		ItemMeta itemMeta = item.getItemMeta();
		itemMeta.setDisplayName(ChatColor.AQUA + "" + ChatColor.BOLD + "Item Ethereal Merchant Coupon");
		List<String> itemLore = new ArrayList<>();
		itemLore.add(ChatColor.GRAY + "Opens up the shopkeeper menu.");
		itemLore.add(ChatColor.GRAY + "Right-click to consume");
		itemMeta.setLore(itemLore);
		item.setItemMeta(itemMeta);
		return item;
	}

}

package net.infernalrealms.quests.objectives;

import net.infernalrealms.items.InfernalItems;
import net.infernalrealms.util.GeneralUtil;

import org.bukkit.ChatColor;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class ObjectiveDeliver extends ObjectiveTalk implements Deliverable {

	private String itemName;
	private int amount;

	public ObjectiveDeliver(String npcName, String itemName, String... messages) {
		this(npcName, itemName, 1, messages);
	}

	public ObjectiveDeliver(String npcName, String itemName, int amount, String... messages) {
		super(npcName, messages);
		ItemStack miscItem = InfernalItems.generateMiscItem(itemName, 1);
		this.itemName = miscItem.getItemMeta().getDisplayName();
		this.amount = amount;
	}

	public String getItemName() {
		return this.itemName;
	}

	public boolean checkInventory(Inventory inv) {
		for (int i = 0; i < inv.getSize(); i++) {
			ItemStack item = inv.getItem(i);
			if (item != null) {
				ItemStack found = checkItem(item);
				boolean check = found != null;
				if (check) {
					if (found.getAmount() >= amount) {
						return true;
					}
				}
			}
		}
		return false;
	}

	public boolean checkInventoryAndRemove(Inventory inv) {
		for (int i = 0; i < inv.getSize(); i++) {
			ItemStack item = inv.getItem(i);
			if (item != null) {
				ItemStack found = checkItem(item);
				boolean check = found != null;
				if (check) {
					if (!GeneralUtil.take(found, amount)) {
						inv.setItem(i, null);
					}
					return true;
				}
			}
		}
		return false;
	}

	public ItemStack checkItem(ItemStack item) {
		if (item.hasItemMeta() && item.getItemMeta().hasDisplayName()) {
			if (checkName(item.getItemMeta().getDisplayName())) {
				return item;
			}
		}
		return null;
	}

	public boolean checkName(String name) {
		return name.equalsIgnoreCase(itemName);
	}

	@Override
	public String getDescription() {
		return (this.isComplete() ? ChatColor.STRIKETHROUGH : "") + "Deliver " + amount + " " + ChatColor.stripColor(itemName) + " to "
				+ this.getNpcName();
	}

}

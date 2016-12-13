package net.infernalrealms.quests.objectives;

import net.infernalrealms.items.InfernalItems;
import net.infernalrealms.util.GeneralUtil;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public abstract class ObjectiveDeliverMulti extends ObjectiveTalk implements Deliverable {

	private String[] itemNames;

	public ObjectiveDeliverMulti(String npcName, String[] itemNames, String... messages) {
		super(npcName, messages);
		String[] trueItemNames = new String[itemNames.length];
		for (int i = 0; i < itemNames.length; i++) {
			ItemStack miscItem = InfernalItems.generateMiscItem(itemNames[i], 1);
			trueItemNames[i] = miscItem.getItemMeta().getDisplayName();
		}
		this.itemNames = trueItemNames;
	}

	public String[] getItemName() {
		return this.itemNames;
	}

	public boolean checkInventory(Inventory inv) {
		boolean[] checks = new boolean[itemNames.length];
		for (int j = 0; j < itemNames.length; j++) {
			for (int i = 0; i < inv.getSize(); i++) {
				ItemStack item = inv.getItem(i);
				if (item != null) {
					ItemStack found = checkItem(item, j);
					if (found != null) {
						checks[j] = true;
					}
				}
			}
		}
		for (int i = 0; i < checks.length; i++) {
			if (!checks[i]) {
				return false;
			}
		}
		return true;
	}

	public boolean checkInventoryAndRemove(Inventory inv) { // TODO Make it remove items from inventory. Make sure to check that all items are there before removing. You could perhaps just called method above before doing this in all honesty...
		if (!checkInventory(inv)) {
			return false;
		}
		//		boolean[] checks = new boolean[itemNames.length];
		for (int j = 0; j < itemNames.length; j++) {
			for (int i = 0; i < inv.getSize(); i++) {
				ItemStack item = inv.getItem(i);
				if (item != null) {
					ItemStack found = checkItem(item, j);
					if (found != null) {
						if (!GeneralUtil.takeOne(found)) {
							inv.setItem(i, null);
						}
					}
				}
			}
		}
		return true;
	}

	public ItemStack checkItem(ItemStack item, int i) {
		if (item.hasItemMeta() && item.getItemMeta().hasDisplayName()) {
			if (checkName(item.getItemMeta().getDisplayName(), i)) {
				return item;
			}
		}
		return null;
	}

	public boolean checkName(String name, int i) {
		return name.equalsIgnoreCase(itemNames[i]);
	}

	@Override
	public abstract String getDescription();

}

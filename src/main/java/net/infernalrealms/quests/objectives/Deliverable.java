package net.infernalrealms.quests.objectives;

import org.bukkit.inventory.Inventory;

public interface Deliverable {

	public boolean checkInventory(Inventory inv);

	public boolean checkInventoryAndRemove(Inventory inv);

}

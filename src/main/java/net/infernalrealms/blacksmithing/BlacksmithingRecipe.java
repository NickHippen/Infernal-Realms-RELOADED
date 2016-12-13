package net.infernalrealms.blacksmithing;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import net.infernalrealms.general.YAMLFile;
import net.infernalrealms.util.GeneralUtil;

public class BlacksmithingRecipe {

	private int requiredLevel;
	private String result;
	private int resultQuantity;
	private Map<String, Integer> components;

	public BlacksmithingRecipe() {}

	public int getRequiredLevel() {
		return requiredLevel;
	}

	public void setRequiredLevel(int requiredLevel) {
		this.requiredLevel = requiredLevel;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public int getResultQuantity() {
		return resultQuantity;
	}

	public void setResultQuantity(int resultQuantity) {
		this.resultQuantity = resultQuantity;
	}

	public Map<String, Integer> getComponents() {
		return components;
	}

	public void setComponents(Map<String, Integer> components) {
		this.components = components;
	}

	/**
	 * Checks the inventory for the materials of the recipe
	 * @param inv
	 * @return whether or not all of the materials were found
	 */
	public boolean hasNeededMaterials(Inventory inv) {
		return getSlotsOfNeededMaterials(inv) != null;
	}

	/**
	 * Removes the needed materials in the inventory, provided they are all there
	 * @param inv
	 * @return whether or not they were removed successfully
	 */
	public boolean removeNeededMaterials(Inventory inv) {
		Map<Integer, Integer> slots = getSlotsOfNeededMaterials(inv);
		if (slots == null) {
			return false;
		}

		for (int slot : slots.keySet()) {
			int amount = slots.get(slot);
			if (!GeneralUtil.take(inv.getItem(slot), amount)) {
				inv.setItem(slot, null);
			}
		}
		return true;
	}

	/**
	 * Gets the slots of the needed materials
	 * @param inv
	 * @return the found slots and their corresponding quantities, or null if it wasn't able to find all of the needed items
	 */
	private Map<Integer, Integer> getSlotsOfNeededMaterials(Inventory inv) {
		Map<Integer, Integer> slots = new HashMap<>();
		for (String miscName : getComponents().keySet()) {
			String name = YAMLFile.MISC_ITEMS.getConfig().getString(miscName + ".Name");
			boolean found = false;
			for (int i = 0; i < inv.getSize(); i++) {
				ItemStack item = inv.getItem(i);
				if (!item.hasItemMeta()) {
					continue;
				}
				ItemMeta itemMeta = item.getItemMeta();
				if (!itemMeta.hasDisplayName()) {
					continue;
				}
				if (!itemMeta.getDisplayName().equals(name)) {
					continue;
				}
				int amount = getComponents().get(miscName);
				if (item.getAmount() < amount) {
					continue;
				}
				// All checks complete, found item
				found = true;
				slots.put(i, amount);
				break;
			}
			if (!found) {
				return null;
			}
		}
		return slots;
	}

}

package net.infernalrealms.blacksmithing;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import net.infernalrealms.general.YAMLFile;
import net.infernalrealms.util.GeneralUtil;

public class SetBlacksmithingRecipe extends BlacksmithingRecipe {

	protected SetBlacksmithingRecipe(String recipeName) throws InvalidRecipeFormatException {
		// Components
		List<String> components = YAMLFile.BLACKSMITHING_RECIPES.getConfig().getStringList(recipeName + ".Recipe");
		if (!BlacksmithingRecipeFactory.checkComponents(components)) {
			throw new InvalidRecipeFormatException();
		}
		Map<String, Integer> formattedComponents = new HashMap<>();
		for (String component : components) {
			String[] componentSplit = component.split(" ");
			formattedComponents.put(componentSplit[0], Integer.parseInt(componentSplit[1]));
		}
		setComponents(formattedComponents);

		// Result
		String result = YAMLFile.BLACKSMITHING_RECIPES.getConfig().getString(recipeName + ".Result");
		if (!BlacksmithingRecipeFactory.checkResult(result)) {
			throw new InvalidRecipeFormatException();
		}
		String[] resultSplit = result.split(" ");
		setResult(resultSplit[0]);
		setResultQuantity(Integer.parseInt(resultSplit[1]));

		// Level
		setRequiredLevel(YAMLFile.BLACKSMITHING_RECIPES.getConfig().getInt(recipeName + ".Level", 0));
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

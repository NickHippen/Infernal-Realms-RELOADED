package net.infernalrealms.blacksmithing;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import net.infernalrealms.general.YAMLFile;
import net.infernalrealms.items.ActiveItem;
import net.infernalrealms.player.PlayerData;
import net.infernalrealms.util.GeneralUtil;

public class GeneratedBlacksmithingRecipe extends BlacksmithingRecipe implements Listener {

	private ItemStack recipeItem;

	private GeneratedBlacksmithingRecipe(ItemStack recipeItem) throws InvalidRecipeFormatException {
		this.recipeItem = recipeItem;
	}

	/**
	 * Checks whether or not the initial display name & lore suggest it is a valid recipe
	 * @param recipeItem the item to be checked
	 * @return whether or not it appears to be valid (no guarantees)
	 */
	private static boolean isPotentiallyValidGeneratedRecipeItem(ItemStack recipeItem) {
		if (recipeItem == null || !recipeItem.hasItemMeta() || !recipeItem.getItemMeta().hasDisplayName()
				|| !recipeItem.getItemMeta().hasLore()) {
			return false;
		}
		ItemMeta recipeMeta = recipeItem.getItemMeta();
		if (!recipeMeta.getDisplayName().startsWith(ChatColor.GRAY + "" + ChatColor.BOLD + "RECIPE: ")) {
			return false;
		}
		return true;
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

	public static class RecipeItem extends ActiveItem {

		@EventHandler
		public boolean onUse(InventoryClickEvent event) {
			if (event.getClick() != ClickType.RIGHT || !(event.getWhoClicked() instanceof Player)) {
				return false;
			}
			if (!isPotentiallyValidGeneratedRecipeItem(event.getCurrentItem())) {
				return false;
			}
			Player player = (Player) event.getWhoClicked();
			event.setCancelled(true);
			PlayerData playerData = PlayerData.getData(player);
			playerData.addRecipeLogItem(event.getCurrentItem());
			if (!GeneralUtil.takeOne(event.getCurrentItem())) {
				event.setCurrentItem(null);
			}
			return true;
		}

	}

}

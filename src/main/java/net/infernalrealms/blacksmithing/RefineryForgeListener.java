package net.infernalrealms.blacksmithing;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_9_R1.block.CraftBlockState;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;

import net.infernalrealms.general.YAMLFile;

public class RefineryForgeListener implements Listener {

	@EventHandler
	public void onFurnaceOpen(InventoryOpenEvent event) {
		if (event.getInventory().getType() != InventoryType.FURNACE || !(event.getPlayer() instanceof Player)) {
			return;
		}
		event.setCancelled(true);
		Player player = (Player) event.getPlayer();
		List<Location> refineryLocations = getRefineryLocations();
		Location furnaceLocation = ((CraftBlockState) event.getInventory().getHolder()).getLocation();
		if (refineryLocations.contains(furnaceLocation)) {
			RefiningGUI.openGUI(player);
		}
	}

	@EventHandler
	public void onAnvilOpen(InventoryOpenEvent event) {
		if (event.getInventory().getType() != InventoryType.ANVIL || !(event.getPlayer() instanceof Player)) {
			return;
		}
		event.setCancelled(true);
		Player player = (Player) event.getPlayer();
		List<Location> forgeLocations = getForgeLocations();
		Location anvilLocation = player.getTargetBlock((Set) null, 7).getLocation();
		if (forgeLocations.contains(anvilLocation)) {
			BlacksmithingBaseGUI.openBaseMenu(player);
		}
	}

	public static List<Location> getRefineryLocations() {
		return (List<Location>) YAMLFile.BLACKSMTIHING_LOCATIONS.getConfig().getList("Refinery", new ArrayList<Location>());
	}

	public static List<Location> getForgeLocations() {
		return (List<Location>) YAMLFile.BLACKSMTIHING_LOCATIONS.getConfig().getList("Forge", new ArrayList<Location>());
	}

}

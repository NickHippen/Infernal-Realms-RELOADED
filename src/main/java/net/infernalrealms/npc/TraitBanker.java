package net.infernalrealms.npc;

import net.citizensnpcs.api.event.NPCRightClickEvent;
import net.citizensnpcs.api.trait.Trait;
import net.infernalrealms.player.PlayerData;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class TraitBanker extends Trait {

	public TraitBanker() {
		super("banker");
	}

	@EventHandler
	public void onRightClick(NPCRightClickEvent event) {
		if (event.getNPC() == this.getNPC()) {
			Player player = event.getClicker();
			openBank(player);
		}
	}

	public static void openBank(Player player) {
		Inventory inv = player.getServer().createInventory(player, 9, "Bank");
		PlayerData playerData = PlayerData.getData(player);
		playerData.processObjectiveOpenBank();
		for (int i = 0; i < inv.getSize(); i++) {
			ItemStack item = playerData.getConfig().getItemStack(playerData.getCurrentCharacterSlot() + ".Inventory.Bank." + i);
			inv.setItem(i, item);
		}
		player.openInventory(inv);

	}

}

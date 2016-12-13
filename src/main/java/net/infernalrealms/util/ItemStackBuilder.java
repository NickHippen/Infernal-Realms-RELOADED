package net.infernalrealms.util;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemStackBuilder {

	private Material material;
	private byte damage = 0;
	private int quantity = 1;

	private String displayName;
	private List<String> lore = new ArrayList<>();

	public ItemStackBuilder(Material material) {
		this.material = material;
	}

	public ItemStackBuilder(Material material, byte damage) {
		this.material = material;
		this.damage = damage;
	}

	public ItemStackBuilder withDamage(byte damage) {
		this.damage = damage;
		return this;
	}

	public ItemStackBuilder withQuantity(int quantity) {
		this.quantity = quantity;
		return this;
	}

	public ItemStackBuilder withDisplayName(String displayName) {
		this.displayName = displayName;
		return this;
	}

	public ItemStackBuilder withLore(List<String> lore) {
		this.lore = lore;
		return this;
	}

	public ItemStackBuilder addLoreLine(String line) {
		this.lore.add(line);
		return this;
	}

	public ItemStack build() {
		ItemStack item = new ItemStack(material, quantity, damage);
		ItemMeta itemMeta = item.getItemMeta();
		if (displayName == null) {
			itemMeta.setDisplayName(displayName);
		}
		itemMeta.setLore(lore);
		item.setItemMeta(itemMeta);
		return item;
	}

}

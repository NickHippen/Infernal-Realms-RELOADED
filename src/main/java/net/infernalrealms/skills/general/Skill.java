package net.infernalrealms.skills.general;

import org.bukkit.inventory.ItemStack;

public abstract class Skill {

	public abstract ItemStack getIcon();

	public abstract ItemStack getIconAtLevel(int level);

}

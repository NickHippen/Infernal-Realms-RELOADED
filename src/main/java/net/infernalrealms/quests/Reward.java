package net.infernalrealms.quests;

import net.infernalrealms.items.InfernalItems;
import net.infernalrealms.items.InfernalItemsRELOADED;
import net.infernalrealms.items.InvalidTraitException;
import net.infernalrealms.items.Pouch;
import net.infernalrealms.player.PlayerData;
import net.infernalrealms.util.GeneralUtil;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Reward {

	private Type type;
	private long amount;
	private String data;
	private Pouch pouch;

	public Reward(Type type) {
		this(type, 1);
	}

	public Reward(Type type, long amount) {
		this(type, null, amount);
	}

	public Reward(Type type, String data) {
		this(type, data, 1);
	}

	public Reward(Type type, String data, long amount) {
		this.type = type;
		this.data = data;
		this.amount = amount;
	}

	public Reward(Pouch pouch) {
		this.type = Type.POUCH;
		this.pouch = pouch;
	}

	public void giveTo(Player player) {
		PlayerData playerData = PlayerData.getData(player);
		switch (this.type) {
		case MONEY:
			playerData.modifyMoney(amount);
			break;
		case EXP:
			playerData.modifyExp((int) amount);
			break;
		case MISC_ITEM:
			ItemStack miscItem = InfernalItems.generateMiscItem(data, (int) amount);
			GeneralUtil.addItemToInventoryOrDrop(player, miscItem);
			break;
		case SET_ITEM:
			ItemStack setItem = InfernalItems.generateCustomItem(data);
			GeneralUtil.addItemToInventoryOrDrop(player, setItem);
			break;
		case TIER_ITEM:
			ItemStack tierItem = null;
			try {
				tierItem = InfernalItemsRELOADED.generateTierItem(data);
			} catch (InvalidTraitException e) {
				e.printStackTrace();
			}
			GeneralUtil.addItemToInventoryOrDrop(player, tierItem);
			break;
		case POTION:
			ItemStack potion = InfernalItems.generatePotion(data, (int) amount);
			GeneralUtil.addItemToInventoryOrDrop(player, potion);
			break;
		case GEM:
			ItemStack gem = null;
			try {
				gem = InfernalItemsRELOADED.generateGem(data);
			} catch (InvalidTraitException e) {
				e.printStackTrace();
			}
			GeneralUtil.addItemToInventoryOrDrop(player, gem);
		case POUCH:
			if (pouch == null) {
				return;
			}
			GeneralUtil.addItemToInventoryOrDrop(player, pouch.generateItemStack());
		case CUSTOM:
			break;
		}
	}

	public String getQuestLogString() {
		String prefix = ChatColor.RESET + "" + ChatColor.GRAY + "- ";
		switch (this.type) {
		case EXP:
			return prefix + this.amount + " EXP";
		case MISC_ITEM:
			return prefix + this.data + " [x" + this.amount + "]";
		case MONEY:
			return prefix + GeneralUtil.getMoneyAsString(this.amount);
		case SET_ITEM:
			return prefix + data;
		case TIER_ITEM:
			String[] splitTier = this.data.split("#");
			return prefix + "A random " + splitTier[0].toLowerCase() + " equip for level " + splitTier[1];
		case POTION:
			return prefix + this.data + " [x" + this.amount + "]";
		case GEM:
			String[] splitGem = this.data.split("#");
			return prefix + "A random " + splitGem[0].toLowerCase() + " gem for level " + splitGem[1];
		case POUCH:
			return prefix + pouch.getDisplayName();
		case CUSTOM:
			return prefix + data;
		}
		return null;
	}

	public enum Type {
		MONEY, EXP, SET_ITEM, TIER_ITEM, MISC_ITEM, POTION, GEM, POUCH, CUSTOM;
	}

}

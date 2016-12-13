package net.infernalrealms.items;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import net.infernalrealms.player.PlayerClass;
import net.infernalrealms.player.Stat;
import net.infernalrealms.util.GeneralUtil;
import net.md_5.bungee.api.ChatColor;

public class EquipmentBuilder implements ItemBuilder {

	private EquipmentGroup equipGroup;
	private Rarity rarity;
	private EquipmentModifier equipModifier;

	private String staticName;
	private Stat staticDamageStat;
	private List<GemColor> staticSockets = new ArrayList<>(4);

	private int level;
	private PlayerClass playerClass;

	private int minimumDamage;
	private int maximumDamage;

	private int hp;
	private int mp;

	private int damageStat;
	private int agility;
	private int stamina;
	private int spirit;
	private int armor;

	private int sockets;
	private int price;

	public EquipmentBuilder(EquipmentGroup equipGroup, int level) {
		this.setEquipGroup(equipGroup);
		this.setLevel(level);
	}

	public EquipmentGroup getEquipGroup() {
		return equipGroup;
	}

	public void setEquipGroup(EquipmentGroup equipGroup) {
		this.equipGroup = equipGroup;
	}

	public Rarity getRarity() {
		return rarity;
	}

	public void setRarity(Rarity rarity) {
		this.rarity = rarity;
	}

	public EquipmentModifier getEquipModifier() {
		return equipModifier;
	}

	public void setEquipModifier(EquipmentModifier equipModifier) {
		this.equipModifier = equipModifier;
	}

	public String getStaticName() {
		return staticName;
	}

	public void setStaticName(String staticName) {
		this.staticName = staticName;
	}

	public boolean hasStaticName() {
		return getStaticName() != null;
	}

	public Stat getStaticDamageStat() {
		return staticDamageStat;
	}

	public void setStaticDamageStat(Stat staticDamageStat) {
		this.staticDamageStat = staticDamageStat;
	}

	public boolean hasStaticDamageStat() {
		return getStaticDamageStat() != null;
	}

	public List<GemColor> getStaticSockets() {
		return staticSockets;
	}

	public void addStaticSocket(GemColor staticSocket) {
		this.getStaticSockets().add(staticSocket);
	}

	public boolean hasStaticSockets() {
		return !this.getStaticSockets().isEmpty();
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public PlayerClass getPlayerClass() {
		return playerClass;
	}

	public void setPlayerClass(PlayerClass playerClass) {
		this.playerClass = playerClass;
	}

	public int getHp() {
		return hp;
	}

	public void setHp(int hp) {
		this.hp = hp;
	}

	public int getMp() {
		return mp;
	}

	public void setMp(int mp) {
		this.mp = mp;
	}

	public int getMinimumDamage() {
		return minimumDamage;
	}

	public void setMinimumDamage(int minimumDamage) {
		this.minimumDamage = minimumDamage;
	}

	public int getMaximumDamage() {
		return maximumDamage;
	}

	public void setMaximumDamage(int maximumDamage) {
		this.maximumDamage = maximumDamage;
	}

	public int getDamageStat() {
		return damageStat;
	}

	public void setDamageStat(int damageStat) {
		this.damageStat = damageStat;
	}

	public int getAgility() {
		return agility;
	}

	public void setAgility(int agility) {
		this.agility = agility;
	}

	public int getStamina() {
		return stamina;
	}

	public void setStamina(int stamina) {
		this.stamina = stamina;
	}

	public int getSockets() {
		return sockets;
	}

	public int getItemLevel() {
		int dpsOrArmor = (int) (getEquipGroup().isWeapon() ? Math.round((getMinimumDamage() + getMaximumDamage()) / 2D) : getArmor() * 2);

		int allStats = 0;
		if (getEquipModifier().hasStamina()) {
			allStats += stamina;
		}
		if (getEquipModifier().hasAgility()) {
			allStats += agility;
		}
		if (getEquipModifier().hasSpirit()) {
			allStats += spirit;
		}
		if (getEquipModifier().hasDamageStat()) {
			allStats += damageStat;
		}
		int hpmp = 0;
		if (getEquipModifier().hasHP()) {
			hpmp += hp;
		}
		if (getEquipModifier().hasMP()) {
			hpmp += mp;
		}
		int sockets = hasStaticSockets() ? getStaticSockets().size() : getSockets();
		return (int) ((dpsOrArmor / 4F) + (allStats / 4F) + (hpmp / 15F) + ((0.95F + (sockets * 0.1F)) * ((allStats / 4F) + (hpmp / 15F))));
	}

	public void setSockets(int sockets) {
		this.sockets = sockets;
	}

	public int getSpirit() {
		return spirit;
	}

	public void setSpirit(int spirit) {
		this.spirit = spirit;
	}

	public int getArmor() {
		return armor;
	}

	public void setArmor(int armor) {
		this.armor = armor;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	@Override
	public ItemStack buildItemStack() {
		ItemStack equip = new ItemStack(getEquipGroup().getMaterialForLevel(getLevel()), 1, (short) 0);
		ItemMeta equipMeta = equip.getItemMeta();
		equipMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);

		// Apply display name
		String equipName = getEquipGroup().getPrimaryDisplayName();
		if (hasStaticName()) {
			equipMeta.setDisplayName(rarity.getColor() + "" + ChatColor.BOLD + getStaticName());
		} else {
			equipName = getEquipGroup().getRandomDisplayName();
			equipMeta.setDisplayName(rarity.getColor() + "" + ChatColor.BOLD + rarity.getRandomDisplayName() + " "
					+ (getEquipModifier().isPrefix() ? getEquipModifier().getName() + " " : "") + equipName
					+ (!getEquipModifier().isPrefix() ? " " + getEquipModifier().getName() : ""));
		}

		// Lore
		List<String> equipLore = new ArrayList<>();
		String damageStat = hasStaticDamageStat() ? getStaticDamageStat().getName() : getEquipGroup().getStatType();
		if (getEquipGroup().isWeapon()) {
			int dps = (int) Math.round((getMinimumDamage() + getMaximumDamage()) / 2D);
			equipLore.add(ChatColor.RESET + "" + ChatColor.WHITE + equipName);
			equipLore.add(ChatColor.RESET + "" + ChatColor.WHITE + getMinimumDamage() + " - " + getMaximumDamage() + " Damage");
			equipLore.add(ChatColor.RESET + "" + ChatColor.GRAY + "(" + dps + " damage average)");
		} else {
			equipLore.add(ChatColor.RESET + "" + ChatColor.WHITE + getArmor() + " Armor");
		}
		if (getEquipModifier().hasDamageStat() && getDamageStat() > 0) {
			equipLore.add(ChatColor.RESET + "" + ChatColor.WHITE + "+" + getDamageStat() + " " + damageStat);
		}
		if (getEquipModifier().hasAgility() && getAgility() > 0) {
			equipLore.add(ChatColor.RESET + "" + ChatColor.WHITE + "+" + getAgility() + " Agility");
		}
		if (getEquipModifier().hasStamina() && getStamina() > 0) {
			equipLore.add(ChatColor.RESET + "" + ChatColor.WHITE + "+" + getStamina() + " Stamina");
		}
		if (getEquipModifier().hasSpirit() && getSpirit() > 0) {
			equipLore.add(ChatColor.RESET + "" + ChatColor.WHITE + "+" + getSpirit() + " Spirit");
		}
		if (getEquipModifier().hasHP() && getHp() > 0) {
			equipLore.add(ChatColor.RESET + "" + ChatColor.WHITE + "+" + getHp() + " Health");
		}
		if (getEquipModifier().hasMP() && getMp() > 0) {
			equipLore.add(ChatColor.RESET + "" + ChatColor.WHITE + "+" + getMp() + " Mana");
		}
		equipLore.add("");

		// Sockets
		if (hasStaticDamageStat()) {
			for (GemColor socket : getStaticSockets()) {
				equipLore.add(socket.getSocketLoreLine());
			}
			equipLore.add("");
		} else if (getSockets() > 0) {
			for (int i = 0; i < getSockets(); i++) {
				equipLore.add(GemColor.getRandomGemColor().getSocketLoreLine());
			}
			equipLore.add("");
		}
		equipLore.add(ChatColor.RESET + "" + ChatColor.YELLOW + "Requires Level " + level);
		equipLore.add(ChatColor.RESET + "" + ChatColor.GRAY + "Item Level " + getItemLevel());
		if (getEquipGroup().isWeapon() || (getEquipModifier().hasDamageStat() && getDamageStat() > 0)) {
			String playerClass = damageStat.equals("Strength") ? "Warrior" : (damageStat.equals("Dexterity") ? "Archer" : "Magician");
			equipLore.add(ChatColor.RESET + "" + ChatColor.RED + "Class: " + playerClass);
		}
		equipLore.add(ChatColor.RESET + "" + ChatColor.WHITE + "Sell Price: " + GeneralUtil.getMoneyAsString(getPrice()));

		equipMeta.setLore(equipLore);
		equip.setItemMeta(equipMeta);
		return equip;
	}

}

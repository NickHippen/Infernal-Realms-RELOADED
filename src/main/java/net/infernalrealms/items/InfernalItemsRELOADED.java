package net.infernalrealms.items;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import net.infernalrealms.mobs.types.InfernalMob;
import net.infernalrealms.util.GeneralUtil;
import net.md_5.bungee.api.ChatColor;

public class InfernalItemsRELOADED {

	private static final Random random = new Random();
	private static final String[] sockets = new String[] { "Red", "Yellow", "Blue" };

	private static final int TIER_MODIFIER_GROUP = 0;
	private static final int TIER_TYPE = 1;
	private static final int TIER_LEVEL = 2;
	private static final int TIER_RARITY = 3;

	private InfernalItemsRELOADED() {}

	public static ItemStack generateTierItem(String traits) throws InvalidTraitException {
		return generateTierItem(traits.split("#"));
	}

	/**
	 * Generates a random tier from the given traits.
	 * @param traits Example input: {MODIFIER_GROUP, ("SWORD,BOW"), LEVEL-LEVEL, RARITY}
	 * @return the generated tier
	 * @throws InvalidTraitException
	 */
	public static ItemStack generateTierItemOLD(String[] traits) throws InvalidTraitException {
		if (traits.length != 4) {
			throw new InvalidTraitException("Trait length is " + traits.length + ". Expected 4.");
		}
		String[] equipmentTypes = traits[TIER_TYPE].split(",");
		List<String> equipTypes = new ArrayList<>(Arrays.asList(equipmentTypes));
		for (String type : equipmentTypes) {
			if (type.contains("ARMOR")) {
				equipTypes.addAll(Arrays.asList(type.replaceAll("ARMOR", "HELMET,CHESTPLATE,LEGGINGS,BOOTS").split(",")));
				equipTypes.remove("ARMOR");
			} else if (type.contains("WEAPONS")) {
				equipTypes.addAll(Arrays.asList(type.replaceAll("WEAPONS", "SWORD,BOW,WAND").split(",")));
				equipTypes.remove("WEAPONS");
			} else if (type.contains("ALL")) {
				equipTypes.addAll(Arrays.asList(type.replaceAll("ALL", "HELMET,CHESTPLATE,LEGGINGS,BOOTS,SWORD,BOW,WAND").split(",")));
				equipTypes.remove("ALL");
			}
		}
		equipmentTypes = equipTypes.toArray(new String[equipTypes.size()]);
		String equipmentTypeString = equipmentTypes[random.nextInt(equipmentTypes.length)];
		EquipmentGroup equipmentType = EquipmentGroup.fromString(equipmentTypeString);
		if (equipmentType == null) {
			throw new InvalidTraitException("Equipment type is null. (\"" + equipmentTypeString + "\")");
		}
		String[] levelSplit = traits[TIER_LEVEL].split("-");
		int level;
		try {
			int minLevel = Integer.parseInt(levelSplit[0]);
			int maxLevel = Integer.parseInt(levelSplit[1]);
			level = minLevel + random.nextInt((maxLevel - minLevel) + 1);
		} catch (Exception e) {
			throw new InvalidTraitException("Error parsing level range.");
		}
		double armor = Math.round(1 + Math.pow(level, 1.3) / 1.6);
		double dps = Math.ceil(10D + Math.pow(level, 1.5) / 1.4);
		double min = Math.round(dps - (0.2 * dps));
		double max = Math.round(dps + (0.2 * dps));
		double stats = Math.round(3 + (Math.pow(level, 1.22) / 1.6) - level * 0.6);
		double hp = Math.round(10 + (Math.pow(level, 1.44) / 1.19) - level * 0.6);
		double mp = Math.round(8 + (Math.pow(level, 1.3) / 1.7) - level * 0.6);
		double money = (int) Math.round(10 + Math.pow(level, 1.74) / 1.3);
		String rarityString = traits[TIER_RARITY];
		Rarity rarity = Rarity.fromString(rarityString);
		if (rarity == null) {
			throw new InvalidTraitException("Rarity is null. (\"" + rarityString + "\")");
		}
		int sockets = rarity.getRandomSockets();
		armor *= rarity.getRandomMultiplier();
		min *= rarity.getRandomMultiplier();
		max *= rarity.getRandomMultiplier();
		stats *= rarity.getRandomMultiplier();
		hp *= rarity.getRandomMultiplier();
		mp *= rarity.getRandomMultiplier();
		money *= rarity.getRandomMultiplier();
		String modifierGroupName = traits[TIER_MODIFIER_GROUP];
		List<String> modifierGroup = FileEquipmentModifier.getModifierGroup(modifierGroupName);
		if (modifierGroup == null) {
			throw new InvalidTraitException("Modifier group is null. (\"" + modifierGroupName + "\")");
		}
		FileEquipmentModifier modifier;
		do {
			String modifierName = modifierGroup.get(random.nextInt(modifierGroup.size()));
			if (equipmentType.isWeapon()) {
				modifier = FileEquipmentModifier.getWeaponModifier(modifierName);
			} else {
				modifier = FileEquipmentModifier.getArmorModifier(modifierName);
			}
		} while (modifier == null);
		double dmgStat = stats;
		double stamina = stats;
		double agility = stats;
		double spirit = stats;
		armor *= modifier.getRandomArmor();
		dmgStat *= modifier.getRandomDamageStat();
		stamina *= modifier.getRandomStamina();
		agility *= modifier.getRandomAgility();
		spirit *= modifier.getRandomSpirit();
		hp *= modifier.getRandomHP();
		mp *= modifier.getRandomMP();
		String dmgStatType = equipmentType.getStatType();
		int newDps = (int) Math.round((min + max) / 2D);
		int allStats = (int) (stamina + agility + spirit + dmgStat);
		int hpmp = (int) (hp + mp);
		int itemLevel = (int) ((newDps / 4F) + (allStats / 4F) + (hpmp / 15F)
				+ ((0.95F + (sockets * 0.1F)) * ((allStats / 4F) + (hpmp / 15F))));

		// Create item from stats
		ItemStack equip = new ItemStack(equipmentType.getMaterialForLevel(level), 1, (short) 0);
		ItemMeta equipMeta = equip.getItemMeta();
		equipMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		// Apply display name
		String equipName = equipmentType.getRandomDisplayName();
		equipMeta.setDisplayName(rarity.getColor() + "" + ChatColor.BOLD + rarity.getRandomDisplayName() + " "
				+ (modifier.isPrefix() ? modifier.getName() + " " : "") + equipName
				+ (!modifier.isPrefix() ? " " + modifier.getName() : ""));
		// Apply all of the lore
		List<String> equipLore = new ArrayList<>();
		if (equipmentType.isWeapon()) {
			equipLore.add(ChatColor.RESET + "" + ChatColor.WHITE + equipName);
			equipLore.add(ChatColor.RESET + "" + ChatColor.WHITE + (int) min + " - " + (int) max + " Damage");
			equipLore.add(ChatColor.RESET + "" + ChatColor.GRAY + "(" + newDps + " damage average)");
		} else {
			equipLore.add(ChatColor.RESET + "" + ChatColor.WHITE + (int) armor + " Armor");
		}
		if (modifier.hasDamageStat()) {
			equipLore.add(ChatColor.RESET + "" + ChatColor.WHITE + "+" + (int) dmgStat + " " + dmgStatType);
		}
		if (modifier.hasAgility()) {
			equipLore.add(ChatColor.RESET + "" + ChatColor.WHITE + "+" + (int) agility + " Agility");
		}
		if (modifier.hasStamina()) {
			equipLore.add(ChatColor.RESET + "" + ChatColor.WHITE + "+" + (int) stamina + " Stamina");
		}
		if (modifier.hasSpirit()) {
			equipLore.add(ChatColor.RESET + "" + ChatColor.WHITE + "+" + (int) spirit + " Spirit");
		}
		if (modifier.hasHP()) {
			equipLore.add(ChatColor.RESET + "" + ChatColor.WHITE + "+" + (int) hp + " Health");
		}
		if (modifier.hasMP()) {
			equipLore.add(ChatColor.RESET + "" + ChatColor.WHITE + "+" + (int) mp + " Mana");
		}
		equipLore.add("");
		// Sockets
		if (sockets > 0) {
			for (int i = 0; i < sockets; i++) {
				String socket = InfernalItemsRELOADED.sockets[random.nextInt(3)];
				equipLore
						.add(ChatColor.RESET + ""
								+ (socket.equals("Red") ? ChatColor.DARK_RED
										: (socket.equals("Yellow") ? ChatColor.YELLOW : ChatColor.BLUE))
								+ " ▣ " + ChatColor.WHITE + socket + " Socket");
			}
			equipLore.add("");
		}
		equipLore.add(ChatColor.RESET + "" + ChatColor.YELLOW + "Requires Level " + level);
		equipLore.add(ChatColor.RESET + "" + ChatColor.GRAY + "Item Level " + itemLevel);
		if (equipmentType.isWeapon() || modifier.hasDamageStat()) {
			String playerClass = dmgStatType.equals("Strength") ? "Warrior" : (dmgStatType.equals("Dexterity") ? "Archer" : "Magician");
			equipLore.add(ChatColor.RESET + "" + ChatColor.RED + "Class: " + playerClass);
		}
		equipLore.add(ChatColor.RESET + "" + ChatColor.WHITE + "Sell Price: " + GeneralUtil.getMoneyAsString((long) money));
		equipMeta.setLore(equipLore);
		equip.setItemMeta(equipMeta);
		return equip;
	}

	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/**
	 * Generates a random tier from the given traits.
	 * @param traits Example input: {MODIFIER_GROUP, ("SWORD,BOW"), LEVEL-LEVEL, RARITY}
	 * @return the generated tier
	 * @throws InvalidTraitException
	 */
	public static ItemStack generateTierItem(String[] traits) throws InvalidTraitException {
		if (traits.length != 4) {
			throw new InvalidTraitException("Trait length is " + traits.length + ". Expected 4.");
		}

		String[] equipmentTypes = traits[TIER_TYPE].split(",");
		List<String> equipTypes = new ArrayList<>(Arrays.asList(equipmentTypes));
		for (String type : equipmentTypes) {
			if (type.contains("ARMOR")) {
				equipTypes.addAll(Arrays.asList(type.replaceAll("ARMOR", "HELMET,CHESTPLATE,LEGGINGS,BOOTS").split(",")));
				equipTypes.remove("ARMOR");
			} else if (type.contains("WEAPONS")) {
				equipTypes.addAll(Arrays.asList(type.replaceAll("WEAPONS", "SWORD,BOW,WAND").split(",")));
				equipTypes.remove("WEAPONS");
			} else if (type.contains("ALL")) {
				equipTypes.addAll(Arrays.asList(type.replaceAll("ALL", "HELMET,CHESTPLATE,LEGGINGS,BOOTS,SWORD,BOW,WAND").split(",")));
				equipTypes.remove("ALL");
			}
		}
		equipmentTypes = equipTypes.toArray(new String[equipTypes.size()]);
		String equipmentTypeString = equipmentTypes[random.nextInt(equipmentTypes.length)];
		EquipmentGroup equipmentType = EquipmentGroup.fromString(equipmentTypeString);
		if (equipmentType == null) {
			throw new InvalidTraitException("Equipment type is null. (\"" + equipmentTypeString + "\")");
		}
		String[] levelSplit = traits[TIER_LEVEL].split("-");
		int level;
		try {
			int minLevel = Integer.parseInt(levelSplit[0]);
			int maxLevel = Integer.parseInt(levelSplit[1]);
			level = minLevel + random.nextInt((maxLevel - minLevel) + 1);
		} catch (Exception e) {
			throw new InvalidTraitException("Error parsing level range.");
		}
		double armor = Math.round(1 + Math.pow(level, 1.3) / 1.6);
		double dps = Math.ceil(10D + Math.pow(level, 1.5) / 1.4);
		double min = Math.round(dps - (0.2 * dps));
		double max = Math.round(dps + (0.2 * dps));
		double stats = Math.round(3 + (Math.pow(level, 1.22) / 1.6) - level * 0.6);
		double hp = Math.round(10 + (Math.pow(level, 1.44) / 1.19) - level * 0.6);
		double mp = Math.round(8 + (Math.pow(level, 1.3) / 1.7) - level * 0.6);
		double money = (int) Math.round(10 + Math.pow(level, 1.74) / 1.3);
		String rarityString = traits[TIER_RARITY];
		Rarity rarity = Rarity.fromString(rarityString);
		if (rarity == null) {
			throw new InvalidTraitException("Rarity is null. (\"" + rarityString + "\")");
		}
		int sockets = rarity.getRandomSockets();
		armor *= rarity.getRandomMultiplier();
		min *= rarity.getRandomMultiplier();
		max *= rarity.getRandomMultiplier();
		stats *= rarity.getRandomMultiplier();
		hp *= rarity.getRandomMultiplier();
		mp *= rarity.getRandomMultiplier();
		money *= rarity.getRandomMultiplier();
		String modifierGroupName = traits[TIER_MODIFIER_GROUP];
		List<String> modifierGroup = FileEquipmentModifier.getModifierGroup(modifierGroupName);
		if (modifierGroup == null) {
			throw new InvalidTraitException("Modifier group is null. (\"" + modifierGroupName + "\")");
		}
		FileEquipmentModifier modifier;
		do {
			String modifierName = modifierGroup.get(random.nextInt(modifierGroup.size()));
			if (equipmentType.isWeapon()) {
				modifier = FileEquipmentModifier.getWeaponModifier(modifierName);
			} else {
				modifier = FileEquipmentModifier.getArmorModifier(modifierName);
			}
		} while (modifier == null);
		double dmgStat = stats;
		double stamina = stats;
		double agility = stats;
		double spirit = stats;
		armor *= modifier.getRandomArmor();
		dmgStat *= modifier.getRandomDamageStat();
		stamina *= modifier.getRandomStamina();
		agility *= modifier.getRandomAgility();
		spirit *= modifier.getRandomSpirit();
		hp *= modifier.getRandomHP();
		mp *= modifier.getRandomMP();

		// Create item from stats
		EquipmentBuilder equipBuilder = new EquipmentBuilder(equipmentType, level);
		equipBuilder.setAgility((int) agility);
		equipBuilder.setArmor((int) armor);
		equipBuilder.setDamageStat((int) dmgStat);
		equipBuilder.setEquipModifier(modifier);
		equipBuilder.setHp((int) hp);
		equipBuilder.setMp((int) mp);
		equipBuilder.setLevel(level);
		equipBuilder.setMaximumDamage((int) max);
		equipBuilder.setMinimumDamage((int) min);
		equipBuilder.setPrice((int) money);
		equipBuilder.setRarity(rarity);
		equipBuilder.setSockets(sockets);
		equipBuilder.setSpirit((int) spirit);
		equipBuilder.setStamina((int) stamina);

		return equipBuilder.buildItemStack();
	}

	public static ItemStack generateTierItem(String[] traits, InfernalMob mob) throws InvalidTraitException {
		if (traits[TIER_LEVEL].toLowerCase().contains("mob")) {
			traits[TIER_LEVEL] = mob.getData().getLevel() + "-" + mob.getData().getLevel();
		}
		return generateTierItem(traits);
	}

	public static ItemStack generateGem(String traits) throws InvalidTraitException {
		return generateGem(traits.split("#"));
	}

	/**
	 * Generates a random gem from the given traits
	 * @param traits Example Input: {COLOR, LEVEL-LEVEL, RARITY}
	 * @return the generated gem
	 * @throws InvalidTraitException
	 */
	public static ItemStack generateGem(String[] traits) throws InvalidTraitException {
		if (traits.length != 3) {
			throw new InvalidTraitException("Trait length is " + traits.length + ". Expected 3.");
		}

		// Color
		if (traits[0].equalsIgnoreCase("ALL")) {
			traits[0] = sockets[random.nextInt(sockets.length)];
		}
		GemColor gemColor = GemColor.fromString(traits[0]);
		if (gemColor == null) {
			throw new InvalidTraitException("Gem color is null. (\"" + traits[0] + "\")");
		}
		String statType = gemColor.getRandomStat();

		// Level
		String[] levelSplit = traits[1].split("-");
		int level;
		try {
			int minLevel = Integer.parseInt(levelSplit[0]);
			int maxLevel = Integer.parseInt(levelSplit[1]);
			level = minLevel + random.nextInt((maxLevel - minLevel) + 1);
		} catch (Exception e) {
			throw new InvalidTraitException("Error parsing level range.");
		}

		// Stats
		double statAmount = (Math.round(5 + (Math.pow(level, 1.4) / 1.4) - level * 0.6) / 3);
		if (statType.equals("Health")) {
			statAmount *= 2;
		}
		double money = (int) Math.round(10 + Math.pow(level, 1.74) / 1.3);
		String rarityString = traits[2];
		Rarity rarity = Rarity.fromString(rarityString);
		if (rarity == null) {
			throw new InvalidTraitException("Rarity is null. (\"" + rarityString + "\")");
		}
		statAmount *= rarity.getRandomMultiplier();
		money *= rarity.getRandomMultiplier();

		// Create item from stats
		ItemStack equip = new ItemStack(Material.INK_SACK, 1, gemColor.getDamage());
		// Apply display name
		ItemMeta equipMeta = equip.getItemMeta();
		equipMeta.setDisplayName(rarity.getColor() + "" + ChatColor.BOLD + rarity.getRandomDisplayName() + " " + gemColor.getDisplayName()
				+ " Gem of " + statType);
		List<String> equipLore = new ArrayList<>();
		equipLore.add(ChatColor.RESET + "" + ChatColor.WHITE + "+" + (int) statAmount + " " + statType);
		equipLore.add(ChatColor.RESET + "" + ChatColor.YELLOW + "Requires Level " + level);
		if (gemColor.equals(GemColor.RED)) {
			String playerClass = statType.equals("Strength") ? "Warrior" : (statType.equals("Dexterity") ? "Archer" : "Magician");
			equipLore.add(ChatColor.RESET + "" + ChatColor.RED + "Class: " + playerClass);
		}
		//equiplore.add((ChatColor.RESET + "" + ChatColor.RED + "Class: " + 
		equipLore.add(ChatColor.RESET + "" + ChatColor.WHITE + "Sell Price: " + GeneralUtil.getMoneyAsString((long) money));
		equipMeta.setLore(equipLore);
		equip.setItemMeta(equipMeta);
		return equip;
	}

	public static ItemStack generateGem(String[] traits, InfernalMob mob) throws InvalidTraitException {
		if (traits[1].toLowerCase().contains("mob")) {
			traits[1] = mob.getData().getLevel() + "-" + mob.getData().getLevel();
		}
		return generateGem(traits);
	}
	
	public static boolean canSocket(ItemStack clickedOn, ItemStack clickedWith) {
		String clickedOnReqClass = ItemReader.getRequiredClass(clickedOn);
		String clickedWithReqClass = ItemReader.getRequiredClass(clickedWith);
		if (clickedOnReqClass.equals(null) && clickedWithReqClass.equals(null)) {
			return true;
		}
		if (clickedOnReqClass.equals(clickedWithReqClass)) {
			return true;
		} 
		return false;
	}

}

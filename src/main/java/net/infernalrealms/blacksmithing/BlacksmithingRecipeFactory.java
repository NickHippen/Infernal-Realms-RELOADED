package net.infernalrealms.blacksmithing;

import static net.infernalrealms.general.InfernalRealms.RANDOM;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import net.infernalrealms.general.YAMLFile;
import net.infernalrealms.items.EquipmentBuilder;
import net.infernalrealms.items.EquipmentGroup;
import net.infernalrealms.items.GemColor;
import net.infernalrealms.items.InvalidTraitException;
import net.infernalrealms.items.ItemReader;
import net.infernalrealms.items.Rarity;
import net.infernalrealms.items.ValueBasedEquipmentModifier;
import net.infernalrealms.player.PlayerClass;
import net.infernalrealms.player.Stat;
import net.infernalrealms.util.GeneralUtil;
import net.infernalrealms.util.IndividualMap;

public class BlacksmithingRecipeFactory {

	private static final Stat[] STATS = new Stat[] { Stat.STRENGTH, Stat.AGILITY, Stat.STAMINA, Stat.SPIRIT, Stat.MANA, Stat.HEALTH };
	private static final Stat[] DAMAGE_STATS = new Stat[] { Stat.STRENGTH, Stat.DEXTERITY, Stat.INTELLIGENCE };
	private static final String[] SOCKETS = new String[] { "Red", "Yellow", "Blue" };

	/**
	 * Contains all of the successfully registered recipes
	 */
	public static final List<SetBlacksmithingRecipe> RECIPES = new ArrayList<>();

	/**
	 * Prepares all of the blacksmithing recipes 
	 */
	public static void prepareRecipes() {
		Set<String> recipes = YAMLFile.BLACKSMITHING_RECIPES.getConfig().getKeys(false);
		Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "Registering blacksmithing recipes...");
		for (String recipeName : recipes) {
			Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "Checking recipe for " + recipeName + "...");
			try {
				registerNewRecipe(recipeName);
			} catch (InvalidRecipeFormatException e) {
				Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "Recipe registry for " + recipeName + " was unsuccessful, skipping.");
				continue;
			}
			Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "Recipe registry for " + recipeName + " complete!");
		}
		Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "Blacksmithing recipe registration completed.");
	}

	/**
	 * Checks to see if all of the components are valid misc. items
	 * @param components the list of components
	 * @return Whether or not the recipe is safe to register (all components are valid)
	 */
	public static boolean checkComponents(List<String> components) {
		if (components == null) {
			Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "Components was null.");
			return false;
		}
		boolean safe = true;
		for (String component : components) {
			String[] componentSplit = component.split(" ");

			if (componentSplit.length != 2) {
				Bukkit.getConsoleSender()
						.sendMessage(ChatColor.RED + "Component " + ChatColor.BOLD + component + ChatColor.RED + " formatted incorrectly.");
				safe = false;
				continue;
			}

			String componentName = componentSplit[0];

			try {
				Integer.parseInt(componentSplit[1]);
			} catch (NumberFormatException e) {
				Bukkit.getConsoleSender().sendMessage(
						ChatColor.RED + "Component " + ChatColor.BOLD + component + ChatColor.RED + " has incorrectly formatted quantity.");
				safe = false;
				continue;
			}

			if (!YAMLFile.MISC_ITEMS.getConfig().contains(componentName)) {
				Bukkit.getConsoleSender().sendMessage(
						ChatColor.RED + "Component " + ChatColor.BOLD + component + ChatColor.RED + " not found in misc. items file.");
				safe = false;
				continue;
			}

			// Safe
		}
		return safe;
	}

	public static boolean checkResult(String result) {
		if (result == null) {
			Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "Result was null.");
			return false;
		}
		String[] resultSplit = result.split(" ");

		if (resultSplit.length != 2) {
			Bukkit.getConsoleSender().sendMessage(
					ChatColor.RED + "Result for " + ChatColor.BOLD + result + ChatColor.RED + " formatted incorrectly: " + result);
			return false;
		}

		String componentName = resultSplit[0];

		try {
			Integer.parseInt(resultSplit[1]);
		} catch (NumberFormatException e) {
			Bukkit.getConsoleSender().sendMessage(
					ChatColor.RED + "Result for " + ChatColor.BOLD + result + ChatColor.RED + " has incorrectly formatted quantity.");
			return false;
		}

		if (!YAMLFile.MISC_ITEMS.getConfig().contains(componentName)) {
			Bukkit.getConsoleSender().sendMessage(
					ChatColor.RED + "Result for " + ChatColor.BOLD + result + ChatColor.RED + " not found in misc. items file.");
			return false;
		}
		return true;
	}

	public static SetBlacksmithingRecipe registerNewRecipe(String recipeName) throws InvalidRecipeFormatException {
		SetBlacksmithingRecipe recipe = new SetBlacksmithingRecipe(recipeName);
		BlacksmithingRecipeFactory.RECIPES.add(recipe);
		return recipe;
	}

	/**
	 * Generates a recipe based on the given traits
	 * @param traits Example input: {"SWORD, BOW", LEVEL-LEVEL, RARITY}
	 * @return the recipe ItemStack
	 * @throws InvalidTraitException 
	 */
	public static ItemStack generateRecipe(String[] traits) throws InvalidTraitException {
		if (traits.length != 3) {
			throw new InvalidTraitException("Trait length is " + traits.length + ". Expected 3.");
		}
		// Type
		String[] equipmentTypes = traits[0].split(",");
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
		String equipmentTypeString = equipmentTypes[RANDOM.nextInt(equipmentTypes.length)];
		EquipmentGroup equipmentType = EquipmentGroup.fromString(equipmentTypeString);
		if (equipmentType == null) {
			throw new InvalidTraitException("Equipment type is null. (\"" + equipmentTypeString + "\")");
		}

		// Level
		String[] levelSplit = traits[1].split("-");
		int level;
		try {
			int minLevel = Integer.parseInt(levelSplit[0]);
			int maxLevel = Integer.parseInt(levelSplit[1]);
			level = minLevel + RANDOM.nextInt((maxLevel - minLevel) + 1);
		} catch (Exception e) {
			throw new InvalidTraitException("Error parsing level range.");
		}

		// Name
		String name;
		switch (equipmentType) {
		case SWORD:
			name = SwordNameGenerator.generateName();
			break;
		case BOW:
			name = BowNameGenerator.generateName();
			break;
		case WAND:
			name = WandNameGenerator.generateName();
			break;
		case CHESTPLATE:
			name = ChestplateNameGenerator.generateName(level >= 20);
			break;
		case BOOTS:
			name = BootNameGenerator.generateName(level >= 20);
			break;
		case LEGGINGS:
			name = LeggingNameGenerator.generateName(level >= 20);
			break;
		case HELMET:
			name = HelmetNameGenerator.generateName(level >= 20);
			break;
		default:
			throw new InvalidTraitException("Unknown equipment type \"" + traits[0] + "\".");
		}
		int hash = GeneralUtil.hashString(name);

		// Rarity
		String rarityString = traits[2];
		Rarity rarity = Rarity.fromString(rarityString);
		if (rarity == null) {
			throw new InvalidTraitException("Rarity is null. (\"" + rarityString + "\")");
		}

		int statAmount = 0;
		float roll = RANDOM.nextFloat();
		switch (rarity) {
		case TRASH:
			if (roll < 0.6F) {
				statAmount = 1;
			} else {
				statAmount = 2;
			}
			break;
		case COMMON:
			if (roll < 0.6F) {
				statAmount = 2;
			} else {
				statAmount = 3;
			}
			break;
		case UNCOMMON:
			if (roll < 0.2F) {
				statAmount = 2;
			} else if (roll < 0.9F) {
				statAmount = 3;
			} else {
				statAmount = 4;
			}
			break;
		case RARE:
			if (roll < 0.2F) {
				statAmount = 3;
			} else if (roll < 0.9F) {
				statAmount = 4;
			} else {
				statAmount = 5;
			}
			break;
		case EPIC:
			if (roll < 0.7F) {
				statAmount = 4;
			} else {
				statAmount = 5;
			}
			break;
		}

		// Sockets
		int sockets = rarity.getRandomSockets();

		// Determine Stats
		int[] statType = new int[statAmount];
		int[] statMults = new int[statAmount];
		for (int i = 0; i < statAmount; i++) {
			// Type
			int type = hash & 0x7;
			if (type > 5) {
				type = 0;
			}
			if (containsType(type, statType, i)) {
				type = ((hash >> 2) & 0x3) + ((hash >> 3) & 0x1) + ((hash >> 4) & 0x1) - ((hash >> 5) & 0x1);

				do {
					if (type >= 5) {
						type = 0;
					} else {
						type++;
					}
				} while (containsType(type, statType, i));
			}
			statType[i] = type;

			hash >>= 3;

			// Mult
			int mult = hash & 0x3;
			statMults[i] = mult + 1;
			hash >>= 2;
		}
		// Determine multiplier pool
		float multPool = (((float) statAmount) - 0.2F) + (RANDOM.nextFloat() / 2.5F); // Random variation of 0.4 for pool

		// Determine total mutliplier values
		float multSum = 0;
		for (int i = 0; i < statAmount; i++) {
			multSum += statMults[i];
		}
		// Create final multipliers & use rarity
		float[] multipliers = new float[statAmount];
		for (int i = 0; i < statAmount; i++) {
			multipliers[i] = (float) ((statMults[i] / multSum) * multPool * (rarity.getRandomMultiplier() - 0.05F));
		}

		double armor = Math.round(1 + Math.pow(level, 1.3) / 1.6);
		double dps = Math.ceil(10D + Math.pow(level, 1.5) / 1.4);
		double stats = Math.round(3 + (Math.pow(level, 1.22) / 1.6) - level * 0.6);
		double money = (int) Math.round(10 + Math.pow(level, 1.74) / 1.3);
		int[] statValues = new int[statAmount];

		// Apply mutipliers
		dps *= rarity.getRandomMultiplier() - 0.05;
		armor *= rarity.getRandomMultiplier() - 0.05;
		int allStats = 0;
		int hpmp = 0;
		int damageStatIndex = -1;
		for (int i = 0; i < statAmount; i++) {
			statValues[i] = (int) Math.round(stats * multipliers[i]);
			if (STATS[statType[i]] != Stat.HEALTH && STATS[statType[i]] != Stat.MANA) {
				if (STATS[statType[i]] == Stat.STRENGTH) {
					damageStatIndex = i;
				}
				allStats += statValues[i];
			} else {
				hpmp += statValues[i];
			}
		}

		int itemLevel = (int) (((equipmentType.isWeapon() ? dps : armor * 2) / 4F) + (allStats / 4F) + (hpmp / 15F)
				+ ((0.95F + (sockets * 0.1F)) * ((allStats / 4F) + (hpmp / 15F))));
		int gemsNeeded = rarity == Rarity.RARE || rarity == Rarity.EPIC ? (int) Math.ceil((statAmount + sockets) / 2) : 0;
		//		Ore FIXME

		// Assign damage stat value, if applicable
		Stat damageStat = null;
		if (damageStatIndex != -1) {
			if (equipmentType.isWeapon()) {
				damageStat = Stat.fromName(equipmentType.getStatType());
			} else {
				damageStat = DAMAGE_STATS[RANDOM.nextInt(DAMAGE_STATS.length)];
			}
		}

		double min = Math.round(dps - (0.2 * dps));
		double max = Math.round(dps + (0.2 * dps));

		// Create recipe itemstack
		ItemStack recipeItem = new ItemStack(Material.PAPER, 1, (short) 0);
		ItemMeta recipeMeta = recipeItem.getItemMeta();
		recipeMeta.setDisplayName(ChatColor.GRAY + "" + ChatColor.BOLD + "RECIPE: " + rarity.getColor() + ChatColor.BOLD + name);
		List<String> recipeLore = new ArrayList<>();
		recipeLore.add(ChatColor.GRAY + "Item Information:");
		recipeLore.add(" ");
		recipeLore.add(ChatColor.WHITE + equipmentType.getPrimaryDisplayName());
		if (equipmentType.isWeapon()) {
			recipeLore.add(ChatColor.RESET + "" + ChatColor.WHITE + (int) min + " - " + (int) max + " Damage");
			recipeLore.add(ChatColor.GRAY + "(" + (int) dps + " damage average)");
		} else {
			recipeLore.add(ChatColor.RESET + "" + ChatColor.WHITE + (int) armor + " Armor");
		}
		for (int i = 0; i < statAmount; i++) {
			recipeLore.add(ChatColor.RESET + "" + ChatColor.WHITE + "+" + statValues[i] + " "
					+ (i == damageStatIndex ? damageStat.getName() : STATS[statType[i]].getName()));
		}
		recipeLore.add(" ");
		if (sockets > 0) {
			for (int i = 0; i < sockets; i++) {
				String socket = SOCKETS[RANDOM.nextInt(3)];
				recipeLore
						.add(ChatColor.RESET + ""
								+ (socket.equals("Red") ? ChatColor.DARK_RED
										: (socket.equals("Yellow") ? ChatColor.YELLOW : ChatColor.BLUE))
								+ " ▣ " + ChatColor.WHITE + socket + " Socket");
			}
			recipeLore.add(" ");
		}
		recipeLore.add(ChatColor.RESET + "" + ChatColor.YELLOW + "Requires Level " + level);
		if (equipmentType.isWeapon() || damageStat != null) {
			if (damageStat == null) {
				damageStat = Stat.fromName(equipmentType.getStatType());
			}
			String playerClass = damageStat == Stat.STRENGTH ? "Warrior" : (damageStat == Stat.DEXTERITY ? "Archer" : "Magician");
			recipeLore.add(ChatColor.RESET + "" + ChatColor.RED + "Class: " + playerClass);
		}
		recipeLore.add(" ");
		recipeLore.add(ChatColor.GRAY + "Crafting Value: " + ChatColor.UNDERLINE + (itemLevel * 5));
		recipeLore.add(ChatColor.RESET + "" + ChatColor.WHITE + "Sell Price: " + GeneralUtil.getMoneyAsString((long) money * 4));
		recipeLore.add(ChatColor.GRAY + "" + ChatColor.ITALIC + "Right click this in your inventory");
		recipeLore.add(ChatColor.GRAY + "" + ChatColor.ITALIC + "to add it to your recipe log.");
		recipeMeta.setLore(recipeLore);
		recipeItem.setItemMeta(recipeMeta);
		return recipeItem;
	}

	/**
	 * Checks whether or not the array 'statTypes' contains 'type', only checking to the 'maxAdded' index.
	 * @param type the type to check
	 * @param statTypes the array to check
	 * @param maxAdded the maximum index to check at
	 * @return whether or not the array contains the type
	 * @throws ArrayIndexOutOfBoundsException if maxAdded is larger than the size of the array
	 */
	private static boolean containsType(int type, int[] statTypes, int maxAdded) {
		for (int i = 0; i < maxAdded; i++) {
			if (statTypes[i] == type) {
				return true;
			}
		}
		return false;
	}

	public static ItemStack convertRecipeLoreToItem(String displayName, List<String> recipeLore) {
		EquipmentGroup equipmentGroup = EquipmentGroup.fromString(ChatColor.stripColor(recipeLore.get(2)));
		int level = ItemReader.getRequiredLevel(recipeLore);
		EquipmentBuilder equipBuilder = new EquipmentBuilder(equipmentGroup, level);
		equipBuilder.setPlayerClass(PlayerClass.fromString(ItemReader.getRequiredClass(recipeLore)));
		equipBuilder.setStaticName(displayName.replaceFirst(ChatColor.GRAY + "" + ChatColor.BOLD + "RECIPE: ", ""));
		equipBuilder.setAgility(ItemReader.getStatFromItem(recipeLore, Stat.AGILITY));
		equipBuilder.setArmor(ItemReader.getStatFromItem(recipeLore, Stat.ARMOR));
		if (equipBuilder.getPlayerClass() != null) {
			Stat damageStat = Stat.getDamageStatForClass(equipBuilder.getPlayerClass());
			equipBuilder.setStaticDamageStat(damageStat);
			equipBuilder.setDamageStat(ItemReader.getStatFromItem(recipeLore, damageStat));
		}
		equipBuilder.setHp(ItemReader.getStatFromItem(recipeLore, Stat.HEALTH));
		equipBuilder.setMp(ItemReader.getStatFromItem(recipeLore, Stat.MANA));
		equipBuilder.setPrice((int) ItemReader.getMoneyValue(recipeLore, 1) / 4);
		IndividualMap<Integer, Integer> minMax = ItemReader.getMinMaxFromItem(recipeLore);
		if (equipmentGroup.isWeapon()) {
			equipBuilder.setMinimumDamage(minMax.getFirstValue());
			equipBuilder.setMaximumDamage(minMax.getSecondValue());
		} else {
			equipBuilder.setArmor(ItemReader.getStatFromItem(recipeLore, Stat.ARMOR));
		}
		for (GemColor socket : ItemReader.getSocketsSlots(recipeLore)) {
			equipBuilder.addStaticSocket(socket);
		}
		equipBuilder.setSpirit(ItemReader.getStatFromItem(recipeLore, Stat.SPIRIT));
		equipBuilder.setStamina(ItemReader.getStatFromItem(recipeLore, Stat.STAMINA));
		equipBuilder.setRarity(Rarity.fromChatColor(ChatColor.getByChar(equipBuilder.getStaticName().charAt(1))));
		equipBuilder.setEquipModifier(new ValueBasedEquipmentModifier());

		return equipBuilder.buildItemStack();
	}

}

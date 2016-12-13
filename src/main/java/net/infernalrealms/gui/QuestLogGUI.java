package net.infernalrealms.gui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import net.infernalrealms.items.GeneralItems;
import net.infernalrealms.player.PlayerData;
import net.infernalrealms.quests.Quest;
import net.infernalrealms.quests.Reward;
import net.infernalrealms.quests.objectives.Objective;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class QuestLogGUI {

	public static void openMenu(Player player, int page) {
		PlayerData playerData = PlayerData.getData(player);
		List<Quest> activeQuests = playerData.getActiveQuests();
		if (activeQuests.size() == 0) {
			player.sendMessage(ChatColor.RED + "You currently do not have any active quests.");
			return;
		}
		int chestSize;
		int difference = 0;
		if (activeQuests.size() % 9 == 0) {
			chestSize = activeQuests.size();
		} else {
			chestSize = ((activeQuests.size() / 9) + 1) * 9;
		}
		if (chestSize > 54) { // Too many quests for one page.
			chestSize = 54;
			difference = activeQuests.size() - 54;
		}

		Inventory inv = player.getServer().createInventory(player, chestSize, "Quest Log");
		Collection<String> moreInfoStrings = Arrays.asList(ChatColor.RESET + "" + ChatColor.GRAY + ChatColor.ITALIC + "Click to see more",
				ChatColor.RESET + "" + ChatColor.GRAY + ChatColor.ITALIC + "quest information.");
		int slot = 0;
		for (int i = 0; i < activeQuests.size() - difference; i++) {
			Quest quest = activeQuests.get(i);
			if (quest != null && quest.isComplete()) {
				continue;
			}
			ItemStack questItem = new ItemStack(Material.NETHER_STAR);
			ItemMeta questItemMeta = questItem.getItemMeta();
			questItemMeta.setDisplayName(ChatColor.RESET + "" + ChatColor.GOLD + ChatColor.BOLD + quest.getName());
			ArrayList<String> questItemLore = new ArrayList<String>();
			questItemLore.add(ChatColor.RESET + "" + ChatColor.GRAY + ChatColor.BOLD + "LEVEL: " + ChatColor.RESET + ChatColor.GRAY
					+ (quest.getRequiredLevel() == -1 ? "N/A" : quest.getRequiredLevel()));
			questItemLore.add(ChatColor.RESET + "" + ChatColor.GRAY + ChatColor.BOLD + "REWARDS:");
			for (Reward reward : quest.getRewards()) {
				questItemLore.add(reward.getQuestLogString());
			}
			questItemLore.addAll(moreInfoStrings);
			questItemMeta.setLore(questItemLore);
			questItem.setItemMeta(questItemMeta);
			inv.setItem(slot, questItem);
			slot++;
		}
		player.openInventory(inv);
	}

	public static void openDetails(Player player, Quest quest) {
		Inventory inv = player.getServer().createInventory(player, 9, "Quest Details: " + quest.getQuestID());
		inv.setItem(1, GeneralItems.BLANK_BLACK_GLASS_PANE);
		inv.setItem(5, GeneralItems.BLANK_BLACK_GLASS_PANE);
		inv.setItem(6, GeneralItems.BLANK_BLACK_GLASS_PANE);

		ItemStack questItem = new ItemStack(Material.NETHER_STAR);
		ItemMeta questItemMeta = questItem.getItemMeta();
		questItemMeta.setDisplayName(ChatColor.RESET + "" + ChatColor.GOLD + ChatColor.BOLD + quest.getName());
		questItemMeta.setLore(Arrays.asList(ChatColor.RESET + "" + ChatColor.GRAY + ChatColor.BOLD + "LEVEL: " + ChatColor.RESET
				+ ChatColor.GRAY + (quest.getRequiredLevel() == -1 ? "N/A" : quest.getRequiredLevel())));
		questItem.setItemMeta(questItemMeta);
		inv.setItem(0, questItem);

		ItemStack questDescription = new ItemStack(Material.SIGN);
		ItemMeta questDescriptionMeta = questDescription.getItemMeta();
		questDescriptionMeta.setDisplayName(ChatColor.RESET + "" + ChatColor.GOLD + ChatColor.BOLD + "Quest Information");
		questDescriptionMeta.setLore(quest.getDescription());
		questDescription.setItemMeta(questDescriptionMeta);
		inv.setItem(2, questDescription);

		ItemStack questRequirements = new ItemStack(Material.SIGN);
		ItemMeta questRequirementsMeta = questRequirements.getItemMeta();
		questRequirementsMeta.setDisplayName(ChatColor.RESET + "" + ChatColor.GREEN + ChatColor.BOLD + "Current Quest Requirements");
		ArrayList<String> questRequirementsLore = new ArrayList<String>();
		for (Objective objective : quest.getCurrentObjectives()) {
			questRequirementsLore.add(ChatColor.RESET + "" + ChatColor.GRAY + "- " + objective.toString());
		}
		questRequirementsMeta.setLore(questRequirementsLore);
		questRequirements.setItemMeta(questRequirementsMeta);
		inv.setItem(3, questRequirements);

		ItemStack questRewards = new ItemStack(Material.SIGN);
		ItemMeta questRewardsMeta = questRewards.getItemMeta();
		questRewardsMeta.setDisplayName(ChatColor.RESET + "" + ChatColor.GREEN + ChatColor.BOLD + "Quest Rewards");
		ArrayList<String> questRewardsLore = new ArrayList<String>();
		for (Reward reward : quest.getRewards()) {
			questRewardsLore.add(reward.getQuestLogString());
		}
		questRewardsMeta.setLore(questRewardsLore);
		questRewards.setItemMeta(questRewardsMeta);
		inv.setItem(4, questRewards);

		ItemStack locationIcon = new ItemStack(Material.MAP);
		ItemMeta locationIconMeta = locationIcon.getItemMeta();
		locationIconMeta.setDisplayName(ChatColor.GRAY + "" + ChatColor.BOLD + "Objective Location");
		List<String> locationIconLore = new ArrayList<>();
		for (Objective objective : quest.getCurrentObjectives()) {
			locationIconLore.add(objective.getLocationDescription());
		}
		locationIconLore.add(ChatColor.GRAY + "Click here to display in chat.");
		locationIconMeta.setLore(locationIconLore);
		locationIcon.setItemMeta(locationIconMeta);
		inv.setItem(5, locationIcon);

		inv.setItem(7, questHelperButton(!QuestTabList.playerHasQuestIDOnHelper(player, quest.getQuestID())));

		ItemStack returnButton = new ItemStack(Material.INK_SACK, 1, (short) 8);
		ItemMeta returnButtonMeta = returnButton.getItemMeta();
		returnButtonMeta.setDisplayName(ChatColor.RESET + "" + ChatColor.GRAY + ChatColor.BOLD + "Return to Quest Log");
		returnButton.setItemMeta(returnButtonMeta);
		inv.setItem(8, returnButton);
		player.openInventory(inv);
	}

	public static ItemStack questHelperButton(boolean add) {
		ItemStack questHelper = new ItemStack(Material.GOLD_INGOT);
		ItemMeta questHelperMeta = questHelper.getItemMeta();
		questHelperMeta.setDisplayName(ChatColor.RESET + "" + ChatColor.GOLD + ChatColor.BOLD
				+ (add ? "Add to Quest Helper (Tab Menu)" : "Remove from Quest Helper (Tab Menu)"));
		questHelper.setItemMeta(questHelperMeta);
		return questHelper;
	}
}

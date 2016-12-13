package net.infernalrealms.gui;

import net.infernalrealms.inventory.InventoryManager;
import net.infernalrealms.player.PlayerClass;
import net.infernalrealms.player.PlayerData;
import net.infernalrealms.quests.HardcodedQuest.QuestName;
import net.infernalrealms.quests.Quest;
import net.infernalrealms.skills.general.Skill;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class SkillsGUI {

	public static void open(Player player) {
		PlayerData playerData = PlayerData.getData(player);
		if (playerData.getPlayerClass().equals(PlayerClass.BEGINNER.getName())
				|| !Quest.checkQuestCompletion(player, QuestName.TO_FORTRESS_ULRIA)) {
			player.sendMessage(ChatColor.RED + "You cannot do this yet!");
			return;
		}
		Inventory inv = player.getServer().createInventory(player, 45, "SP: " + playerData.getSP());
		player.openInventory(getClassPage(inv, player, PlayerClass.fromString(playerData.getPlayerClass())));
	}

	private static Inventory getClassPage(Inventory inv, Player player, PlayerClass playerClass) {
		try {
			int rowStart = 0;
			for (Class<? extends Skill> skill : playerClass.getSkills()) {
				ItemStack[] line = getGUILine(skill.getConstructor(Player.class).newInstance(player));
				for (int i = 0; i < line.length; i++) {
					inv.setItem(rowStart + i, line[i]);
				}
				rowStart += 9;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return inv;
	}

	private static ItemStack[] getGUILine(Skill skill) {
		ItemStack[] line = new ItemStack[9];
		line[0] = skill.getIcon();
		for (int i = 1; i < 9; i++) {
			line[i] = skill.getIconAtLevel(i);
		}
		return line;
	}

	@SuppressWarnings("unchecked")
	public static void processClick(int slot, Player player, PlayerClass playerClass) {
		if (slot < 0 || slot >= 45)
			return;

		PlayerData playerData = PlayerData.getData(player);
		int skillIndex = slot / 9;
		int levelToUpgrade = (slot % 9) - 1;
		Class<Skill> skillClass = (Class<Skill>) playerClass.getSkills()[skillIndex];
		if (levelToUpgrade != -1 && playerData.getSkillLevel(skillClass) != levelToUpgrade) {
			return;
		}
		playerData.upgradeSkill(skillClass);
		playerData.processObjectiveLearnSkill();
		InventoryManager.updateSkillIcons(player);
		open(player);
	}
}

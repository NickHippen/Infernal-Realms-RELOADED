package net.infernalrealms.gui;

import java.util.ArrayList;
import java.util.HashMap;

import net.infernalrealms.general.InfernalRealms;
import net.infernalrealms.quests.Quest;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class QuestTabList {

	/** Player Name, Quest IDs */
	private static HashMap<String, ArrayList<Integer>> questHelper = new HashMap<>();

	public static void sendTab(final Player player) {
		new BukkitRunnable() {
			@Override
			public void run() {
				ArrayList<String> lines = new ArrayList<String>();
				lines.add("");
				lines.add(ChatColor.GOLD + "" + ChatColor.BOLD + ChatColor.UNDERLINE + "Quest Helper" + ChatColor.RESET);
				lines.add("");

				if (!questHelper.containsKey(player.getName())) {
					questHelper.put(player.getName(), new ArrayList<Integer>());
				}

				ArrayList<Integer> quests = questHelper.get(player.getName());
				boolean secondQuest = false;
				boolean odd = quests.size() % 2 != 0;
				String[] colName = new String[2];
				String[][] colObjectives = new String[2][];
				int larger = -1;

				for (int i = 0; i < quests.size(); i++) {
					Quest quest = Quest.getQuest(player, quests.get(i));
					int index = secondQuest ? 1 : 0;
					boolean readyToAdd = secondQuest || (odd && quests.size() - 1 == i);

					// Quest Title
					colName[index] = ChatColor.GOLD + "" + ChatColor.BOLD + quest.getName();

					// Objectives
					if (quest.getCurrentObjectives().length > larger) {
						larger = quest.getCurrentObjectives().length;
					}
					colObjectives[index] = new String[quest.getCurrentObjectives().length];
					for (int j = 0; j < quest.getCurrentObjectives().length; j++) {
						colObjectives[index][j] = ChatColor.GRAY + quest.getCurrentObjectives()[j].toString();
					}
					if (readyToAdd) {
						StringBuilder sbNames = new StringBuilder("                                                              ");
						sbNames.replace(0, colName[0].length(), colName[0] + ChatColor.RESET);
						sbNames.insert(colName[0].length() + 15, (colName[1] != null ? colName[1] : "") + ChatColor.RESET);
						lines.add(sbNames.toString().substring(0, 62));

						for (int j = 0; j < larger; j++) {
							String q1;
							String q2;
							try {
								q1 = colObjectives[0].length >= j && colObjectives[0][j] != null ? colObjectives[0][j] : "";
							} catch (ArrayIndexOutOfBoundsException e) {
								q1 = "";
							}
							try {
								q2 = colObjectives[1] != null
										? (colObjectives[1].length >= j && colObjectives[1][j] != null ? colObjectives[1][j] : "") : "";
							} catch (ArrayIndexOutOfBoundsException e) {
								q2 = "";
							}
							StringBuilder sbObjectives = new StringBuilder(
									"                                                              "); // 62 spaces long
							sbObjectives.insert(0, q1 + ChatColor.RESET);
							sbObjectives.insert(colName[0].length() + 15, q2 + ChatColor.RESET);
							lines.add(sbObjectives.toString().substring(0, 62));
						}

						// Restart counters for next two quests.
						colName = new String[2];
						colObjectives = new String[2][];
						larger = -1;

						lines.add("");
					}
					secondQuest = !secondQuest;
				}

				TabList.sendTab(player, lines);
			}

		}.runTaskLater(InfernalRealms.getPlugin(), 1L);
	}

	public static void addToHelper(Player player, int questID) {
		ArrayList<Integer> questIDs;
		if (questHelper.containsKey(player.getName())) {
			questIDs = questHelper.get(player.getName());
			if (questIDs.contains(questID)) {
				return;
			}
		} else {
			questIDs = new ArrayList<>();
		}
		questIDs.add(questID);
		questHelper.put(player.getName(), questIDs);
	}

	public static void removeFromHelper(Player player, int questID) {
		if (questHelper.containsKey(player.getName())) {
			questHelper.get(player.getName()).remove((Integer) questID);
		}
	}

	public static boolean playerHasQuestIDOnHelper(Player player, int questID) {
		if (!questHelper.containsKey(player.getName())) {
			return false;
		}
		return questHelper.get(player.getName()).contains(questID);
	}

	public static void removeAllQuestsFromHelper(Player player) {
		questHelper.remove(player.getName());
	}
}

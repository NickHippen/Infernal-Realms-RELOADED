package net.infernalrealms.quests;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.avaje.ebeaninternal.server.lib.util.StringParsingException;

import net.infernalrealms.gui.QuestTabList;
import net.infernalrealms.gui.TextBar;
import net.infernalrealms.player.PlayerData;
import net.infernalrealms.quests.HardcodedQuest.QuestName;
import net.infernalrealms.quests.objectives.Objective;
import net.infernalrealms.quests.objectives.PassiveCompletion;

public abstract class Quest {

	public static final String COMPLETE = "COMPLETE";

	private Player player;
	private int questID;
	private int requiredLevel;
	private Objective[][] objectives; // [Part][Objective(s)]
	private List<Reward> rewards;
	private List<String> description;
	private int part;

	private boolean valid = true;
	boolean complete = false;

	Quest(Player player, int questID, int requiredLevel, Objective[][] objectives, List<Reward> rewards, ArrayList<String> description) {
		this.player = player;
		this.questID = questID;
		this.requiredLevel = requiredLevel;
		this.objectives = objectives;
		this.rewards = rewards;
		this.description = description;
		if (player != null) {
			PlayerData playerData = PlayerData.getData(player);
			if (getRequiredLevel() > playerData.getLevel()) {
				setValid(false);
			} else {
				String progress = playerData.getConfig().getString(playerData.getCurrentCharacterSlot() + ".Quests." + questID);
				if (progress == null) {
					this.setPart(0);
					progress = getProgressAsString();
					playerData.getConfig().set(playerData.getCurrentCharacterSlot() + ".Quests." + questID, progress);
					playerData.saveConfig();
					new TextBar(player, 15, 60, 15, ChatColor.GOLD + "" + ChatColor.BOLD + "NEW QUEST:", ChatColor.GRAY + this.getName())
							.queue();
				}
				setProgressFromString(progress);
			}
		} else {
			this.setPart(0);
		}
	}

	/**
	 * Saves the quest progress in the player's data file.
	 */
	public void save() {
		final PlayerData playerData = PlayerData.getData(player);
		playerData.getConfig().set(playerData.getCurrentCharacterSlot() + ".Quests." + getQuestID(),
				complete ? COMPLETE : getProgressAsString());
		playerData.saveConfig();
	}

	/**
	 * Checks if the quest is ready to progress to the next part. If there are no more parts, the reward is given and quest marked as complete.
	 */
	public void tryNext() {
		if (this.isComplete()) {
			return;
		}
		if (player != null) {
			PlayerData playerData = PlayerData.getData(getPlayer());
			playerData.refreshQuestMarkers();
		}
		for (Objective objective : getCurrentObjectives()) {
			if (!objective.isComplete()) {
				return;
			}
		}
		// Never returned, therefore all objectives are complete
		if (part < objectives.length - 1) { // Move to next part of quest
			this.nextPart();
		} else { // Quest is complete, dish out them sweet rewards
			this.setComplete();
		}
	}

	public void displayObjectivesIfNewProgress() {

	}

	/**
	 * <strong> Format: </strong>
	 * PART#OBJECTIVE_PROGRESS,OBJECTIVE_PROGRESS...
	 * @return progress in string format
	 */
	public String getProgressAsString() {
		if (this.complete) {
			return COMPLETE;
		}
		String progress = getPart() + "#";
		Objective[] currentObjectives = getCurrentObjectives();
		for (int i = 0; i < currentObjectives.length; i++) {
			progress += currentObjectives[i].getProgress() + ",";
		}
		progress = progress.substring(0, progress.length() - 1);
		return progress;
	}

	public void setProgressFromString(String progress) {
		if (progress.equals(COMPLETE)) {
			this.complete = true;
			this.setValid(false);
			return;
		}
		try {
			String[] progressSplit = progress.split("#");
			this.setPart(Integer.parseInt(progressSplit[0]));
			String[] objectiveProgress = progressSplit[1].split(",");
			for (int i = 0; i < objectiveProgress.length; i++) {
				getCurrentObjectives()[i].setProgress(Integer.parseInt(objectiveProgress[i]));
			}
		} catch (Exception e) {
			System.out.println("ERROR WITH THIS STRING: " + progress);
			throw new StringParsingException("Progress is formatted incorrectly.");
		}
	}

	public void setComplete() {
		this.complete = true;
		PlayerData.getData(getPlayer()).processObjectiveCompleteQuest(HardcodedQuest.QuestName.fromQuestID(getQuestID()));
		giveRewards();
		new TextBar(player, 15, 60, 15, ChatColor.GOLD + "" + ChatColor.BOLD + "QUEST COMPLETE:", ChatColor.GRAY + this.getName()).queue();
		QuestTabList.removeFromHelper(getPlayer(), getQuestID());
	}

	public boolean isComplete() {
		return this.complete;
	}

	public void giveRewards() {
		for (Reward reward : getRewards()) {
			reward.giveTo(getPlayer());
		}
	}

	public abstract String getName();

	public final Player getPlayer() {
		return this.player;
	}

	public final void setPlayer(Player player) {
		this.player = player;
	}

	public final int getQuestID() {
		return this.questID;
	}

	public final void setQuestID(int questID) {
		this.questID = questID;
	}

	public final int getRequiredLevel() {
		return this.requiredLevel;
	}

	public final void setRequiredLevel(int level) {
		this.requiredLevel = level;
	}

	public final int getPart() {
		return this.part;
	}

	public final void setPart(int part) {
		this.part = part;

		//		 Check passive objectives in new part
		for (Objective obj : getCurrentObjectives()) {
			if (obj instanceof PassiveCompletion) {
				if (((PassiveCompletion) obj).check(getPlayer())) {
					this.tryNext();
					save();
				}
			}
		}
	}

	public final List<Reward> getRewards() {
		return this.rewards;
	}

	public final void setRewards(ArrayList<Reward> rewards) {
		this.rewards = rewards;
	}

	public List<String> getDescription() {
		if (this.description != null && this.description.size() != 0) {
			ArrayList<String> coloredDescription = new ArrayList<String>();
			for (String line : this.description) {
				coloredDescription.add(ChatColor.RESET + "" + ChatColor.GRAY + ChatColor.ITALIC + line);
			}
			return coloredDescription;
		} else {
			return Arrays.asList(ChatColor.RESET + "" + ChatColor.GRAY + ChatColor.ITALIC + "No description available.");
		}
	}

	public void nextPart() {
		this.setPart(this.getPart() + 1);
	}

	public boolean isValid() {
		return this.valid;
	}

	public void setValid(boolean valid) {
		this.valid = valid;
	}

	public Objective[] getCurrentObjectives() {
		return this.objectives[getPart()];
	}

	public Objective[][] getObjectives() {
		return this.objectives;
	}

	public abstract boolean isStory();

	public static boolean checkQuestCompletion(Player player, HardcodedQuest.QuestName quest) {
		PlayerData playerData = PlayerData.getData(player);
		String progress = playerData.getConfig().getString(playerData.getCurrentCharacterSlot() + ".Quests." + quest.getQuestID());
		return progress != null && progress.equals(COMPLETE);
	}

	public static Quest getQuest(Player player, HardcodedQuest.QuestName quest) {
		return getQuest(player, quest.getQuestID());
	}

	public static Quest getQuest(Player player, int questID) {
		//		PlayerData playerData = PlayerData.getData(player);
		//		if (!playerData.hasQuest(questID)) {
		//			return null;
		//		}
		if (questID < 10000) { // File Quest
			// TODO
			System.out.println("ERROR: Quest ID Too Low");
			return null;
		}
		QuestName questName = HardcodedQuest.QuestName.fromQuestID(questID);
		if (questName != null) {
			HardcodedQuest hardcodedQuest;
			try {
				hardcodedQuest = questName.getQuestClass().getDeclaredConstructor(Player.class).newInstance(player);
			} catch (InvocationTargetException ite) {
				Bukkit.getConsoleSender().sendMessage(
						ChatColor.RED + "ERROR WITH QUEST: " + questName + " (Possible trying to create an item that does not exist?)");
				ite.printStackTrace();
				return null;
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
			if (hardcodedQuest.isValid() && !hardcodedQuest.isComplete() && hardcodedQuest.isStory()) {
				QuestTabList.addToHelper(player, questID);
			}
			return hardcodedQuest;
		}
		return null;
	}

	public static void getNewQuests(Player player) {
		PlayerData playerData = PlayerData.getData(player);
		int playerLevel = playerData.getLevel();
		for (HardcodedQuest.QuestName questName : HardcodedQuest.QuestName.values()) {
			if (questName.getRequiredLevel() != -1 && questName.getRequiredLevel() <= playerLevel) {
				getQuest(player, questName.getQuestID());
			}
		}
	}

	@Override
	public String toString() {
		return "[Quest: " + getName() + ", Progress: " + getProgressAsString() + "]";
	}

}

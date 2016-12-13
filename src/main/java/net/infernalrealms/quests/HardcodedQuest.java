package net.infernalrealms.quests;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import net.infernalrealms.quests.hardcoded.HardcodedQuest10001NewBeginnings;
import net.infernalrealms.quests.hardcoded.HardcodedQuest10002FenorsTask;
import net.infernalrealms.quests.hardcoded.HardcodedQuest10003PathToEnen;
import net.infernalrealms.quests.hardcoded.HardcodedQuest10004ChooseYourPath;
import net.infernalrealms.quests.hardcoded.HardcodedQuest10005ReadyForAdventure;
import net.infernalrealms.quests.hardcoded.HardcodedQuest10006ToFortressUlria;
import net.infernalrealms.quests.hardcoded.HardcodedQuest10007ShowYourWorth;
import net.infernalrealms.quests.hardcoded.HardcodedQuest100085BraemirsTask;
import net.infernalrealms.quests.hardcoded.HardcodedQuest10008AidToThePeople;
import net.infernalrealms.quests.hardcoded.HardcodedQuest10009Aleandria;
import net.infernalrealms.quests.hardcoded.HardcodedQuest10010TheZen;
import net.infernalrealms.quests.hardcoded.HardcodedQuest10011ZensTask;
import net.infernalrealms.quests.hardcoded.HardcodedQuest10012MorDok;
import net.infernalrealms.quests.hardcoded.HardcodedQuest10013Unknown_Places;
import net.infernalrealms.quests.hardcoded.HardcodedQuest10014Meet_the_Town;
import net.infernalrealms.quests.hardcoded.HardcodedQuest10015Master_Magician;
import net.infernalrealms.quests.hardcoded.HardcodedQuest10016Dungeoneering;
import net.infernalrealms.quests.hardcoded.N10_20059_Soul_Searching;
import net.infernalrealms.quests.hardcoded.N10_20060_Freeing_Our_Souls;
import net.infernalrealms.quests.hardcoded.N11_20018_Finding_Hagwin;
import net.infernalrealms.quests.hardcoded.N11_20019_Goblin_Extermination;
import net.infernalrealms.quests.hardcoded.N11_20020_Clear_the_Path;
import net.infernalrealms.quests.hardcoded.N12_20021_Lost_Halls;
import net.infernalrealms.quests.hardcoded.N12_20022_Corrupt_Smithing;
import net.infernalrealms.quests.hardcoded.N13_20023_Gorraks_Dagger;
import net.infernalrealms.quests.hardcoded.N13_20024_Mindless_Gorks;
import net.infernalrealms.quests.hardcoded.N14_20025_Spider_Sacs;
import net.infernalrealms.quests.hardcoded.N14_20026_Potion_Magic;
import net.infernalrealms.quests.hardcoded.N15_20027_Lost_Spirits;
import net.infernalrealms.quests.hardcoded.N15_20028_Guardians_of_Cern_Kek_Nono;
import net.infernalrealms.quests.hardcoded.N15_20029_The_Ancient_Spirit;
import net.infernalrealms.quests.hardcoded.N16_20030_Ardent_Relic;
import net.infernalrealms.quests.hardcoded.N16_20031_Bone_Spells;
import net.infernalrealms.quests.hardcoded.N16_20032_Fragments_of_Corruption;
import net.infernalrealms.quests.hardcoded.N16_20033_Vengeance;
import net.infernalrealms.quests.hardcoded.N17_20034_Clear_the_Path2;
import net.infernalrealms.quests.hardcoded.N17_20035_Clear_the_Way;
import net.infernalrealms.quests.hardcoded.N17_20036_Origin_of_the_Growth;
import net.infernalrealms.quests.hardcoded.N17_20037_Corruption_of_the_Growth;
import net.infernalrealms.quests.hardcoded.N18_20038_Forsaken_Extermination;
import net.infernalrealms.quests.hardcoded.N18_20039_Mysterious_Material;
import net.infernalrealms.quests.hardcoded.N18_20040_A_Weapon_to_Fight_Back;
import net.infernalrealms.quests.hardcoded.N18_20041_Sending_a_Message;
import net.infernalrealms.quests.hardcoded.N19_20042_The_Risen;
import net.infernalrealms.quests.hardcoded.N19_20043_The_Greater_Lich;
import net.infernalrealms.quests.hardcoded.N19_20044_Lich_King;
import net.infernalrealms.quests.hardcoded.N19_20045_Forsaken_Extermination2;
import net.infernalrealms.quests.hardcoded.N19_20046_The_Doomed;
import net.infernalrealms.quests.hardcoded.N20_20047_The_Valley;
import net.infernalrealms.quests.hardcoded.N20_20048_The_Doomed2;
import net.infernalrealms.quests.hardcoded.N20_20049_Treasure_of_the_Doomed;
import net.infernalrealms.quests.hardcoded.N20_20050_Anarchist_Revolution;
import net.infernalrealms.quests.hardcoded.N20_20051_Revolution_Extermination;
import net.infernalrealms.quests.hardcoded.N21_20052_Xoumien;
import net.infernalrealms.quests.hardcoded.N21_20053_The_Dwellers;
import net.infernalrealms.quests.hardcoded.N21_20054_Dreaded_Bandits;
import net.infernalrealms.quests.hardcoded.N21_20061_Supply_Crate_Destruction;
import net.infernalrealms.quests.hardcoded.N21_20062_Bandit_Mining;
import net.infernalrealms.quests.hardcoded.N22_20063_Through_the_Gate;
import net.infernalrealms.quests.hardcoded.N22_20064_An_Honorable_Test;
import net.infernalrealms.quests.hardcoded.N22_20065_An_Honorable_Test_2;
import net.infernalrealms.quests.hardcoded.N22_20066_Sleasurs_Task;
import net.infernalrealms.quests.hardcoded.N22_20067_Creeps_in_Alyuin;
import net.infernalrealms.quests.hardcoded.N23_20068_Tamed_Spiders;
import net.infernalrealms.quests.hardcoded.N23_20068_Tamed_Spiders;
import net.infernalrealms.quests.hardcoded.N23_20069_Alder_Fungi;
import net.infernalrealms.quests.hardcoded.N23_20070_Spider_Eggs;
import net.infernalrealms.quests.hardcoded.N23_20071_Shelob_the_Devourer;
import net.infernalrealms.quests.hardcoded.N23_20072_Fendor_Reports;
import net.infernalrealms.quests.hardcoded.N23_20073_Zmem_War;
import net.infernalrealms.quests.hardcoded.N5_20001_CleanseTheLands;
import net.infernalrealms.quests.hardcoded.N5_20002_KillFalseWorshippers;
import net.infernalrealms.quests.hardcoded.N5_20003_Kans_Recommendation;
import net.infernalrealms.quests.hardcoded.N5_20004_Lernin_Herbs;
import net.infernalrealms.quests.hardcoded.N5_20005_ABountyUnclaimed;
import net.infernalrealms.quests.hardcoded.N5_20006_For_Reasons;
import net.infernalrealms.quests.hardcoded.N5_20007_Nora_Plants;
import net.infernalrealms.quests.hardcoded.N5_20055_While_Your_At_It;
import net.infernalrealms.quests.hardcoded.N6_20008_Homeless_Problem;
import net.infernalrealms.quests.hardcoded.N6_20009_Faster;
import net.infernalrealms.quests.hardcoded.N6_20056_Eliminate_the_Thieving_Scum;
import net.infernalrealms.quests.hardcoded.N6_20057_Kill_Hogni;
import net.infernalrealms.quests.hardcoded.N7_20010_Spider_Eyes;
import net.infernalrealms.quests.hardcoded.N7_20011_Zelk_and_her_Minions;
import net.infernalrealms.quests.hardcoded.N8_20012_Elromian_Feathers;
import net.infernalrealms.quests.hardcoded.N8_20013_They_Scare_Me;
import net.infernalrealms.quests.hardcoded.N8_20014_Elrom;
import net.infernalrealms.quests.hardcoded.N8_20058_Elromian_Must_Die;
import net.infernalrealms.quests.hardcoded.N9_20015_Exterminate_the_Plague;
import net.infernalrealms.quests.hardcoded.N9_20016_Vile_Ghouls;
import net.infernalrealms.quests.hardcoded.N9_20017_Illitran_Seed;
import net.infernalrealms.quests.objectives.Objective;

public abstract class HardcodedQuest extends Quest {

	protected HardcodedQuest(Player player, int questID, int requiredLevel, Objective[][] objectives, List<Reward> rewards,
			ArrayList<String> description) {
		super(player, questID, requiredLevel, objectives, rewards, description);
	}

	@Override
	public void tryNext() {
		getPlayer().playSound(getPlayer().getLocation(), Sound.ENTITY_ARROW_HIT, 1F, 5F);
		boolean display = false;
		for (Objective objective : getCurrentObjectives()) {
			if (objective.hasBeenShown() || !objective.isComplete()) {
				continue;
			}
			// New objective has been completed
			display = true;
			objective.setShown(true);
			break;
		}
		if (display) {
			for (Objective objective : getCurrentObjectives()) {
				if (objective.isComplete()) {
					getPlayer().sendMessage(ChatColor.DARK_GREEN + "" + ChatColor.BOLD + "[" + ChatColor.DARK_GREEN + "✔" + ChatColor.BOLD
							+ "]" + ChatColor.GREEN + ChatColor.BOLD + " [" + ChatColor.GREEN + objective.getDescription() + ChatColor.BOLD
							+ "]" + ChatColor.GREEN + ChatColor.ITALIC + " (" + getName() + ")");
				} else {
					getPlayer().sendMessage(ChatColor.DARK_RED + "" + ChatColor.BOLD + "[" + ChatColor.DARK_RED + "X" + ChatColor.BOLD + "]"
							+ ChatColor.RED + ChatColor.BOLD + " [" + ChatColor.RED + objective.getDescription() + ChatColor.BOLD + "]"
							+ ChatColor.RED + ChatColor.ITALIC + " (" + getName() + ")");
				}
			}
		}
		super.tryNext();
	}

	public enum QuestName {
		// @formatter:off
		// (ID, QuestClass, RequiredLevel (-1 = n/a))
		
		// Tutorial Quests
		NEW_BEGINNINGS(HardcodedQuest10001NewBeginnings.QUEST_ID, HardcodedQuest10001NewBeginnings.class, HardcodedQuest10001NewBeginnings.REQUIRED_LEVEL),
		FENORS_TASK(HardcodedQuest10002FenorsTask.QUEST_ID, HardcodedQuest10002FenorsTask.class, HardcodedQuest10002FenorsTask.REQUIRED_LEVEL),
		PATH_TO_ENEN(HardcodedQuest10003PathToEnen.QUEST_ID, HardcodedQuest10003PathToEnen.class, HardcodedQuest10003PathToEnen.REQUIRED_LEVEL),
		CHOOSE_YOUR_PATH(HardcodedQuest10004ChooseYourPath.QUEST_ID, HardcodedQuest10004ChooseYourPath.class, HardcodedQuest10004ChooseYourPath.REQUIRED_LEVEL),
		READY_FOR_ADVENTURE(HardcodedQuest10005ReadyForAdventure.QUEST_ID, HardcodedQuest10005ReadyForAdventure.class, HardcodedQuest10005ReadyForAdventure.REQUIRED_LEVEL),
		TO_FORTRESS_ULRIA(HardcodedQuest10006ToFortressUlria.QUEST_ID, HardcodedQuest10006ToFortressUlria.class, HardcodedQuest10006ToFortressUlria.REQUIRED_LEVEL),
		SHOW_YOUR_WORTH(HardcodedQuest10007ShowYourWorth.QUEST_ID, HardcodedQuest10007ShowYourWorth.class, HardcodedQuest10007ShowYourWorth.REQUIRED_LEVEL),
		BRAEMIRS_TASK(HardcodedQuest100085BraemirsTask.QUEST_ID, HardcodedQuest100085BraemirsTask.class, HardcodedQuest100085BraemirsTask.REQUIRED_LEVEL),
		AID_TO_THE_PEOPLE(HardcodedQuest10008AidToThePeople.QUEST_ID, HardcodedQuest10008AidToThePeople.class, HardcodedQuest10008AidToThePeople.REQUIRED_LEVEL),
		ALEANDRIA(HardcodedQuest10009Aleandria.QUEST_ID, HardcodedQuest10009Aleandria.class, HardcodedQuest10009Aleandria.REQUIRED_LEVEL),
		THE_ZEN(HardcodedQuest10010TheZen.QUEST_ID, HardcodedQuest10010TheZen.class, HardcodedQuest10010TheZen.REQUIRED_LEVEL),
		ZENS_TASK(HardcodedQuest10011ZensTask.QUEST_ID, HardcodedQuest10011ZensTask.class, HardcodedQuest10011ZensTask.REQUIRED_LEVEL),
		MOR_DOK(HardcodedQuest10012MorDok.QUEST_ID, HardcodedQuest10012MorDok.class, HardcodedQuest10012MorDok.REQUIRED_LEVEL),
		
		// Story Quests
		UNKNOWN_PLACES(HardcodedQuest10013Unknown_Places.QUEST_ID, HardcodedQuest10013Unknown_Places.class, HardcodedQuest10013Unknown_Places.REQUIRED_LEVEL),
		MEET_THE_TOWN(HardcodedQuest10014Meet_the_Town.QUEST_ID, HardcodedQuest10014Meet_the_Town.class, HardcodedQuest10014Meet_the_Town.REQUIRED_LEVEL),
		MASTER_MAGICIAN(HardcodedQuest10015Master_Magician.QUEST_ID, HardcodedQuest10015Master_Magician.class, HardcodedQuest10015Master_Magician.REQUIRED_LEVEL),
		DUNGEONEERING(HardcodedQuest10016Dungeoneering.QUEST_ID, HardcodedQuest10016Dungeoneering.class, HardcodedQuest10016Dungeoneering.REQUIRED_LEVEL),

		// Normal Quests
		CLEANSE_THE_LANDS(N5_20001_CleanseTheLands.QUEST_ID, N5_20001_CleanseTheLands.class, N5_20001_CleanseTheLands.REQUIRED_LEVEL),
		KILL_FALSE_WORSHIPPERS(N5_20002_KillFalseWorshippers.QUEST_ID, N5_20002_KillFalseWorshippers.class, N5_20002_KillFalseWorshippers.REQUIRED_LEVEL),
		KANS_RECOMMENDATION(N5_20003_Kans_Recommendation.QUEST_ID, N5_20003_Kans_Recommendation.class, N5_20003_Kans_Recommendation.REQUIRED_LEVEL),
		LERNIN_HERBS(N5_20004_Lernin_Herbs.QUEST_ID, N5_20004_Lernin_Herbs.class, N5_20004_Lernin_Herbs.REQUIRED_LEVEL),
		A_BOUNTY_UNCLAIMED(N5_20005_ABountyUnclaimed.QUEST_ID, N5_20005_ABountyUnclaimed.class, N5_20005_ABountyUnclaimed.REQUIRED_LEVEL),
		FOR_REASONS(N5_20006_For_Reasons.QUEST_ID, N5_20006_For_Reasons.class, N5_20006_For_Reasons.REQUIRED_LEVEL),
		NORA_PLANTS(N5_20007_Nora_Plants.QUEST_ID, N5_20007_Nora_Plants.class, N5_20007_Nora_Plants.REQUIRED_LEVEL),
		HOMELESS_PROBLEM(N6_20008_Homeless_Problem.QUEST_ID, N6_20008_Homeless_Problem.class, N6_20008_Homeless_Problem.REQUIRED_LEVEL),
		FASTER(N6_20009_Faster.QUEST_ID, N6_20009_Faster.class, N6_20009_Faster.REQUIRED_LEVEL),
		SPIDER_EYES(N7_20010_Spider_Eyes.QUEST_ID, N7_20010_Spider_Eyes.class, N7_20010_Spider_Eyes.REQUIRED_LEVEL),
		ZELK_AND_HIS_MINIONS(N7_20011_Zelk_and_her_Minions.QUEST_ID, N7_20011_Zelk_and_her_Minions.class, N7_20011_Zelk_and_her_Minions.REQUIRED_LEVEL),
		ELROMIAN_FEATHERS(N8_20012_Elromian_Feathers.QUEST_ID, N8_20012_Elromian_Feathers.class, N8_20012_Elromian_Feathers.REQUIRED_LEVEL),
		THEY_SCARE_ME(N8_20013_They_Scare_Me.QUEST_ID, N8_20013_They_Scare_Me.class, N8_20013_They_Scare_Me.REQUIRED_LEVEL),
		ELROM(N8_20014_Elrom.QUEST_ID, N8_20014_Elrom.class, N8_20014_Elrom.REQUIRED_LEVEL),
		EXTERMINATE_THE_PLAGUE(N9_20015_Exterminate_the_Plague.QUEST_ID, N9_20015_Exterminate_the_Plague.class, N9_20015_Exterminate_the_Plague.REQUIRED_LEVEL),
		VILE_GHOULS(N9_20016_Vile_Ghouls.QUEST_ID, N9_20016_Vile_Ghouls.class, N9_20016_Vile_Ghouls.REQUIRED_LEVEL),
		ILLITRAN_SEED(N9_20017_Illitran_Seed.QUEST_ID, N9_20017_Illitran_Seed.class, N9_20017_Illitran_Seed.REQUIRED_LEVEL),
		FINDING_HAGWIN(N11_20018_Finding_Hagwin.QUEST_ID, N11_20018_Finding_Hagwin.class, N11_20018_Finding_Hagwin.REQUIRED_LEVEL),
		GOBLIN_EXTERMINATION(N11_20019_Goblin_Extermination.QUEST_ID, N11_20019_Goblin_Extermination.class, N11_20019_Goblin_Extermination.REQUIRED_LEVEL),
		CLEAR_THE_PATH(N11_20020_Clear_the_Path.QUEST_ID, N11_20020_Clear_the_Path.class, N11_20020_Clear_the_Path.REQUIRED_LEVEL),
		LOST_HALLS(N12_20021_Lost_Halls.QUEST_ID, N12_20021_Lost_Halls.class, N12_20021_Lost_Halls.REQUIRED_LEVEL),
		CORRUPT_SMITHING(N12_20022_Corrupt_Smithing.QUEST_ID, N12_20022_Corrupt_Smithing.class, N12_20022_Corrupt_Smithing.REQUIRED_LEVEL),
		GORRAKS_DAGGER(N13_20023_Gorraks_Dagger.QUEST_ID, N13_20023_Gorraks_Dagger.class, N13_20023_Gorraks_Dagger.REQUIRED_LEVEL),
		MINDLESS_GORKS(N13_20024_Mindless_Gorks.QUEST_ID, N13_20024_Mindless_Gorks.class, N13_20024_Mindless_Gorks.REQUIRED_LEVEL),
		SPIDER_SACS(N14_20025_Spider_Sacs.QUEST_ID, N14_20025_Spider_Sacs.class, N14_20025_Spider_Sacs.REQUIRED_LEVEL),
		POTION_MAGIC(N14_20026_Potion_Magic.QUEST_ID, N14_20026_Potion_Magic.class, N14_20026_Potion_Magic.REQUIRED_LEVEL),
		LOST_SPIRITS(N15_20027_Lost_Spirits.QUEST_ID, N15_20027_Lost_Spirits.class, N15_20027_Lost_Spirits.REQUIRED_LEVEL),
		GUARDIANS_OF_CERN_KEK_NONO(N15_20028_Guardians_of_Cern_Kek_Nono.QUEST_ID, N15_20028_Guardians_of_Cern_Kek_Nono.class, N15_20028_Guardians_of_Cern_Kek_Nono.REQUIRED_LEVEL),
		THE_ANCIENT_SPIRIT(N15_20029_The_Ancient_Spirit.QUEST_ID, N15_20029_The_Ancient_Spirit.class, N15_20029_The_Ancient_Spirit.REQUIRED_LEVEL),
		ARDENT_RELIC(N16_20030_Ardent_Relic.QUEST_ID, N16_20030_Ardent_Relic.class, N16_20030_Ardent_Relic.REQUIRED_LEVEL),
		BONE_SPELLS(N16_20031_Bone_Spells.QUEST_ID, N16_20031_Bone_Spells.class, N16_20031_Bone_Spells.REQUIRED_LEVEL),
		FRAGMENTS_OF_CORRUPTION(N16_20032_Fragments_of_Corruption.QUEST_ID, N16_20032_Fragments_of_Corruption.class, N16_20032_Fragments_of_Corruption.REQUIRED_LEVEL),
		VENGEANCE(N16_20033_Vengeance.QUEST_ID, N16_20033_Vengeance.class, N16_20033_Vengeance.REQUIRED_LEVEL),
		CLEAR_THE_PATH2(N17_20034_Clear_the_Path2.QUEST_ID, N17_20034_Clear_the_Path2.class, N17_20034_Clear_the_Path2.REQUIRED_LEVEL),
		CLEAR_THE_WAY(N17_20035_Clear_the_Way.QUEST_ID, N17_20035_Clear_the_Way.class, N17_20035_Clear_the_Way.REQUIRED_LEVEL),
		ORIGIN_OF_THE_GROWTH(N17_20036_Origin_of_the_Growth.QUEST_ID, N17_20036_Origin_of_the_Growth.class, N17_20036_Origin_of_the_Growth.REQUIRED_LEVEL),
		CORRUPTION_OF_THE_GROWTH(N17_20037_Corruption_of_the_Growth.QUEST_ID, N17_20037_Corruption_of_the_Growth.class, N17_20037_Corruption_of_the_Growth.REQUIRED_LEVEL),
		FORSAKEN_EXTERMINATION(N18_20038_Forsaken_Extermination.QUEST_ID, N18_20038_Forsaken_Extermination.class, N18_20038_Forsaken_Extermination.REQUIRED_LEVEL),
		MYSTERIOUS_MATERIAL(N18_20039_Mysterious_Material.QUEST_ID, N18_20039_Mysterious_Material.class, N18_20039_Mysterious_Material.REQUIRED_LEVEL),
		A_WEAPON_TO_FIGHT_BACK(N18_20040_A_Weapon_to_Fight_Back.QUEST_ID, N18_20040_A_Weapon_to_Fight_Back.class, N18_20040_A_Weapon_to_Fight_Back.REQUIRED_LEVEL),
		SENDING_A_MESSAGE(N18_20041_Sending_a_Message.QUEST_ID, N18_20041_Sending_a_Message.class, N18_20041_Sending_a_Message.REQUIRED_LEVEL),
		THE_RISEN(N19_20042_The_Risen.QUEST_ID, N19_20042_The_Risen.class, N19_20042_The_Risen.REQUIRED_LEVEL),
		THE_GREATER_LICH(N19_20043_The_Greater_Lich.QUEST_ID, N19_20043_The_Greater_Lich.class, N19_20043_The_Greater_Lich.REQUIRED_LEVEL),
		LICH_KING(N19_20044_Lich_King.QUEST_ID, N19_20044_Lich_King.class, N19_20044_Lich_King.REQUIRED_LEVEL),
		FORSAKEN_EXTERMINATION2(N19_20045_Forsaken_Extermination2.QUEST_ID, N19_20045_Forsaken_Extermination2.class, N19_20045_Forsaken_Extermination2.REQUIRED_LEVEL),
		THE_DOOMED(N19_20046_The_Doomed.QUEST_ID, N19_20046_The_Doomed.class, N19_20046_The_Doomed.REQUIRED_LEVEL),
		THE_VALLEY(N20_20047_The_Valley.QUEST_ID, N20_20047_The_Valley.class, N20_20047_The_Valley.REQUIRED_LEVEL),
		THE_DOOMED2(N20_20048_The_Doomed2.QUEST_ID, N20_20048_The_Doomed2.class, N20_20048_The_Doomed2.REQUIRED_LEVEL),
		TREASURE_OF_THE_DOOMED(N20_20049_Treasure_of_the_Doomed.QUEST_ID, N20_20049_Treasure_of_the_Doomed.class, N20_20049_Treasure_of_the_Doomed.REQUIRED_LEVEL),
		ANARCHIST_REVOLUTION(N20_20050_Anarchist_Revolution.QUEST_ID, N20_20050_Anarchist_Revolution.class, N20_20050_Anarchist_Revolution.REQUIRED_LEVEL),
		REVOLUTION_EXTERMINATION(N20_20051_Revolution_Extermination.QUEST_ID, N20_20051_Revolution_Extermination.class, N20_20051_Revolution_Extermination.REQUIRED_LEVEL),
		XOUMIEN(N21_20052_Xoumien.QUEST_ID, N21_20052_Xoumien.class, N21_20052_Xoumien.REQUIRED_LEVEL),
		THE_DWELLERS(N21_20053_The_Dwellers.QUEST_ID, N21_20053_The_Dwellers.class, N21_20053_The_Dwellers.REQUIRED_LEVEL),
		DREADED_BANDITS(N21_20054_Dreaded_Bandits.QUEST_ID, N21_20054_Dreaded_Bandits.class, N21_20054_Dreaded_Bandits.REQUIRED_LEVEL),
		WHILE_YOUR_AT_IT(N5_20055_While_Your_At_It.QUEST_ID, N5_20055_While_Your_At_It.class, N5_20055_While_Your_At_It.REQUIRED_LEVEL),
		ELIMINATE_THE_THIEVING_SCUM(N6_20056_Eliminate_the_Thieving_Scum.QUEST_ID, N6_20056_Eliminate_the_Thieving_Scum.class, N6_20056_Eliminate_the_Thieving_Scum.REQUIRED_LEVEL),
		KILL_HOGNI(N6_20057_Kill_Hogni.QUEST_ID, N6_20057_Kill_Hogni.class, N6_20057_Kill_Hogni.REQUIRED_LEVEL),
		ELROMIAN_MUST_DIE(N8_20058_Elromian_Must_Die.QUEST_ID, N8_20058_Elromian_Must_Die.class, N8_20058_Elromian_Must_Die.REQUIRED_LEVEL),
		SOUL_SEARCHING(N10_20059_Soul_Searching.QUEST_ID, N10_20059_Soul_Searching.class, N10_20059_Soul_Searching.REQUIRED_LEVEL),
		FREEING_OUR_SOULS(N10_20060_Freeing_Our_Souls.QUEST_ID, N10_20060_Freeing_Our_Souls.class, N10_20060_Freeing_Our_Souls.REQUIRED_LEVEL),
		SUPPLY_CRATE_DESTRUCTION(N21_20061_Supply_Crate_Destruction.QUEST_ID, N21_20061_Supply_Crate_Destruction.class, N21_20061_Supply_Crate_Destruction.REQUIRED_LEVEL),
		BANDIT_MINING(N21_20062_Bandit_Mining.QUEST_ID, N21_20062_Bandit_Mining.class, N21_20062_Bandit_Mining.REQUIRED_LEVEL),
		THROUGH_THE_GATE(N22_20063_Through_the_Gate.QUEST_ID, N22_20063_Through_the_Gate.class, N22_20063_Through_the_Gate.REQUIRED_LEVEL),
		AN_HONORABLE_TEST(N22_20064_An_Honorable_Test.QUEST_ID, N22_20064_An_Honorable_Test.class, N22_20064_An_Honorable_Test.REQUIRED_LEVEL),
		AN_HONORABLE_TEST_2(N22_20065_An_Honorable_Test_2.QUEST_ID, N22_20065_An_Honorable_Test_2.class, N22_20065_An_Honorable_Test_2.REQUIRED_LEVEL),
		SLEASURS_TASK(N22_20066_Sleasurs_Task.QUEST_ID, N22_20066_Sleasurs_Task.class, N22_20066_Sleasurs_Task.REQUIRED_LEVEL),
		CREEPS_IN_ALYUIN(N22_20067_Creeps_in_Alyuin.QUEST_ID, N22_20067_Creeps_in_Alyuin.class, N22_20067_Creeps_in_Alyuin.REQUIRED_LEVEL),
		TAMED_SPIDERS(N23_20068_Tamed_Spiders.QUEST_ID, N23_20068_Tamed_Spiders.class, N23_20068_Tamed_Spiders.REQUIRED_LEVEL),
		ALDER_FUNGI(N23_20069_Alder_Fungi.QUEST_ID, N23_20069_Alder_Fungi.class, N23_20069_Alder_Fungi.REQUIRED_LEVEL),
		SPIDER_EGGS(N23_20070_Spider_Eggs.QUEST_ID, N23_20070_Spider_Eggs.class, N23_20070_Spider_Eggs.REQUIRED_LEVEL),
		SHELOB_THE_DEVOURER(N23_20071_Shelob_the_Devourer.QUEST_ID, N23_20071_Shelob_the_Devourer.class, N23_20071_Shelob_the_Devourer.REQUIRED_LEVEL),
		FENDOR_REPORTS(N23_20072_Fendor_Reports.QUEST_ID, N23_20072_Fendor_Reports.class, N23_20072_Fendor_Reports.REQUIRED_LEVEL),
		ZMEM_WAR(N23_20073_Zmem_War.QUEST_ID, N23_20073_Zmem_War.class, N23_20073_Zmem_War.REQUIRED_LEVEL),
		;
		// @formatter:on

		private int questID;
		private Class<? extends HardcodedQuest> questClass;
		private int requiredLevel;

		private QuestName(int questID, Class<? extends HardcodedQuest> questClass, int requiredLevel) {
			this.questID = questID;
			this.questClass = questClass;
			this.requiredLevel = requiredLevel;
		}

		public int getQuestID() {
			return this.questID;
		}

		public Class<? extends HardcodedQuest> getQuestClass() {
			return this.questClass;
		}

		public int getRequiredLevel() {
			return this.requiredLevel;
		}

		public static QuestName fromQuestID(int questID) {
			for (QuestName q : values()) {
				if (q.getQuestID() == questID) {
					return q;
				}
			}
			return null;
		}
	}

}

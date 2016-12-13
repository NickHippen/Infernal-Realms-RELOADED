//package net.infernalrealms.quests.hardcoded;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import org.bukkit.ChatColor;
//import org.bukkit.Location;
//import org.bukkit.entity.Player;
//import org.bukkit.scheduler.BukkitRunnable;
//
//import net.infernalrealms.dungeons.Dungeon;
//import net.infernalrealms.dungeons.DungeonType;
//import net.infernalrealms.general.InfernalRealms;
//import net.infernalrealms.quests.DungeonQuest;
//import net.infernalrealms.quests.Reward;
//import net.infernalrealms.quests.objectives.Objective;
//import net.infernalrealms.quests.objectives.ObjectiveBreakBlock;
//import net.infernalrealms.quests.objectives.ObjectiveKill;
//import net.infernalrealms.quests.objectives.ObjectiveTalk;
//import net.infernalrealms.util.GeneralUtil;
//
//public class DungeonQuestYggdrasil extends DungeonQuest {
//
//	public static final String QUEST_NAME = "Yggdrasil";
//	private static final List<Reward> rewards = new ArrayList<>();
//
//	static {
//		// Rewards
//		rewards.add(new Reward(Reward.Type.EXP, 300));
//		rewards.add(new Reward(Reward.Type.MONEY, GeneralUtil.convertCoinsToMoney(0, 2, 0)));
//		rewards.add(new Reward(Reward.Type.CUSTOM, "Chances for dungeon-unique drops"));
//	}
//
//	public DungeonQuestYggdrasil() {
//		super(getSpecificObjectives(), rewards);
//	}
//
//	public static Objective[][] getSpecificObjectives() {
//		// @formatter:off
//		Objective[][] objectives = new Objective[][] {
//			new Objective[]
//			{ // Part 1
//				new ObjectiveTalk(true, "Aenaelal", "Ah! I'm glad you've made it in time!",
//													"There is a powerful evil stirring in the depths of its primodial roots.",
//													"We need you to vanquish its power before it rises and consumes Nym!",
//													"Be swift, hero, and beware of the tree men."),
//			},
//			{ // Part 2
//				new ObjectiveKill(17, 25),
//				new ObjectiveKill(18, 15),
//				new ObjectiveKill(19, 8),
//				new ObjectiveBreakBlock("Tree Essence", 15),
//			},
//			new Objective[] 
//			{ // Part 3
// 				new ObjectiveKill(20, 1),
//			}
//		};
//		return objectives;
//		// @formatter:on
//	}
//
//	@Override
//	public String getName() {
//		return QUEST_NAME;
//	}
//
//	@Override
//	public DungeonType getDungeonType() {
//		return DungeonType.YGGDRASIL;
//	}
//
//	@Override
//	public void nextPart() {
//		super.nextPart();
//		if (getPart() == 2) { // Fight boss
//			getDungeon().getParty().broadcastToDungeonParty(ChatColor.GRAY + "" + ChatColor.ITALIC
//					+ "In response to your interference, Ler Maim has begun to summon you to him. Prepare for battle!");
//			this.setBossMode(true);
//			new BukkitRunnable() {
//
//				@Override
//				public void run() {
//					Location l = getBossLocation();
//					for (Player player : getDungeon().getParty().getDungeonMembers()) {
//						player.teleport(l);
//					}
//					getDungeon().getParty()
//							.broadcastToDungeonParty(ChatColor.GRAY + "" + ChatColor.ITALIC + "You have been summoned! Defeat Ler Maim!");
//				}
//			}.runTaskLater(InfernalRealms.getPlugin(), 200L);
//		}
//	}
//
//	@Override
//	public void displayEntranceMessages() {
//		if (getDungeon() == null || getDungeon().getParty() == null) {
//			return;
//		}
//		getDungeon().getParty()
//				.broadcastToDungeonParty(ChatColor.GRAY + "" + ChatColor.ITALIC + "You have arrived at the ancient tree of Yggdrasil.");
//	}
//
//	@Override
//	public Location getBossLocation() {
//		return new Location(Dungeon.INSTANCES_WORLD, 860.5, 23.5, 179.5, -35F, 0F);
//	}
//
//}

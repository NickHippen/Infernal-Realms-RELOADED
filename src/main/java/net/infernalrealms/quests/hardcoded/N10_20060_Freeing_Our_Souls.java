package net.infernalrealms.quests.hardcoded;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import de.inventivegames.particle.ParticleEffect;
import net.infernalrealms.general.InfernalRealms;
import net.infernalrealms.quests.HardcodedQuest;
import net.infernalrealms.quests.Reward;
import net.infernalrealms.quests.objectives.Objective;
import net.infernalrealms.quests.objectives.ObjectiveDeliver;
import net.infernalrealms.quests.objectives.ObjectiveKill;
import net.infernalrealms.quests.objectives.ObjectiveTalk;
import net.infernalrealms.util.EffectsUtil;
import net.infernalrealms.util.GeneralUtil;

public class N10_20060_Freeing_Our_Souls extends HardcodedQuest {
	private static final ArrayList<Reward> rewards = new ArrayList<>();
	private static final ArrayList<String> description = new ArrayList<>();

	// Basic Information
	public static final String QUEST_NAME = "Freeing Our Souls";
	public static final int QUEST_ID = 20060;
	public static final int REQUIRED_LEVEL = -1;

	public N10_20060_Freeing_Our_Souls(Player player) {
		super(player, QUEST_ID, REQUIRED_LEVEL, getSpecificObjectives(), rewards, description);
	}

	public static Objective[][] getSpecificObjectives() {
		// @formatter:off
		Objective[][] objectives = new Objective[][] {
			new Objective[]
				{ // Part 1
					new ObjectiveTalk("Nokealo", "Further up the tower lie many more evil beings.", 
												 "I ask of you to kill as many of these soul creatures as you can, and retrieve the stolen souls that they sometimes carry with them."),
				},
				{ // Part 2
					new ObjectiveKill(127, 18) {
						@Override
						public Location getLocation() {
							return new Location(InfernalRealms.MAIN_WORLD, 828, 81, 822);
						}
					},
					new ObjectiveKill(128, 12) {
						@Override
						public Location getLocation() {
							return new Location(InfernalRealms.MAIN_WORLD, 828, 81, 822);
						}
					},
					new ObjectiveKill(129, 14) {
						@Override
						public Location getLocation() {
							return new Location(InfernalRealms.MAIN_WORLD, 828, 81, 822);
						}
					},
					new ObjectiveDeliver("Nokealo", "DyingSoul", 8, "If you've killed the Soul Soldiers and Soul Guards, continue on to defeat Luehmk!"),
				},
				{ // Part 3
					new ObjectiveKill(131, 1) {
						@Override
						public Location getLocation() {
							return new Location(InfernalRealms.MAIN_WORLD, 829, 111, 824);
						}
					},
					new ObjectiveDeliver("Marden", "OrbOfControl", 1, "Our people will forever be grateful for your actions!"),
				},
		};
		return objectives;
		// @formatter:on
	}

	static {
		// Rewards
		rewards.add(new Reward(Reward.Type.EXP, 900));
		rewards.add(new Reward(Reward.Type.MONEY, GeneralUtil.convertCoinsToMoney(0, 1, 60)));

		// Quest Log Description
		description.add("Further up the tower lie many more evil beings. I");
		description.add("ask of you to kill as many of these Soul Soldiers as");
		description.add("you can, and retrieve the stolen souls that they");
		description.add("sometimes carry with them. Once you complete this");
		description.add("task, defeat Luehmk the Trapper and return the Orb");
		description.add("of Control to Marden so he can destroy it.");
	}

	@Override
	public void giveRewards() {
		EffectsUtil.sendParticleToLocation(ParticleEffect.LARGE_EXPLODE, getPlayer().getLocation(), 0F, 0F, 0F, 1F, 2);
		EffectsUtil.sendParticleToLocation(ParticleEffect.SMOKE_LARGE, getPlayer().getLocation(), 0F, 0F, 0F, 0.1F, 10);
		getPlayer().playSound(getPlayer().getLocation(), Sound.ENTITY_GENERIC_EXPLODE, 1F, 0F);
		getPlayer().sendMessage(ChatColor.ITALIC + "The Orb of Control has been destroyed!");
		super.giveRewards();
	}

	@Override
	public String getName() {
		return QUEST_NAME;
	}

	@Override
	public boolean isStory() {
		return false;
	}

}

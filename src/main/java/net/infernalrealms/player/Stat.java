package net.infernalrealms.player;

import org.bukkit.entity.Player;

import net.infernalrealms.general.InfernalRealms;
import net.infernalrealms.gui.ActionBarHandler;
import net.infernalrealms.party.Party;

public enum Stat {

	//@formatter:off
	STRENGTH("Strength"),
	DEXTERITY("Dexterity"),
	INTELLIGENCE("Intelligence"),
	AGILITY("Agility"),
	STAMINA("Stamina"),
	SPIRIT("Spirit"),
	HEALTH("Health"),
	MANA("Mana"),
	ARMOR("Armor"),
	;
	//@formatter:on

	private String name;

	private Stat(String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}

	public static Stat fromName(String name) {
		for (Stat stat : values()) {
			if (name.equalsIgnoreCase(stat.getName())) {
				return stat;
			}
		}
		return null;
	}

	public static void refreshHealthNumber(final Player player) {
		PlayerData playerData = PlayerData.getData(player);
		ActionBarHandler.sendActionBar(player, playerData.getPlayerInfoString());
		InfernalRealms.getPlugin().getServer().getScheduler().scheduleSyncDelayedTask(InfernalRealms.getPlugin(), new Runnable() {
			public void run() {
				Party party = Party.getParty(player);
				if (party != null) {
					party.updatePlayerHealth(player);
				}
			}
		}, 1L);
	}

	public static void refreshManaBar(Player player) {
		PlayerData playerData = PlayerData.getData(player);
		float exp = ((float) playerData.getMana()) / playerData.getTotalMaxMana();
		player.setExp(exp);
		player.setLevel(playerData.getMana());
	}

	public static Stat getDamageStatForClass(PlayerClass playerClass) {
		switch (playerClass) {
		case ARCHER:
			return Stat.DEXTERITY;
		case MAGICIAN:
			return Stat.INTELLIGENCE;
		case WARRIOR:
			return Stat.STRENGTH;
		default:
			return null;

		}
	}

}
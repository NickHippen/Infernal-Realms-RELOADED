package net.infernalrealms.util;

import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class InfernalEffects {

	private InfernalEffects() {}

	public static void playErrorSound(Player player) {
		player.playSound(player.getLocation(), Sound.BLOCK_NOTE_SNARE, 1F, 1F);
	}

	public static void playInsufficientManaSound(Player player) {
		player.playSound(player.getLocation(), Sound.BLOCK_WOOD_BREAK, 1F, 0F);
	}

	public static void playConversationStartSound(Player player) {
		player.playSound(player.getLocation(), Sound.BLOCK_STONE_BUTTON_CLICK_ON, 1, 1);
	}

	public static void playConversationContinueSound(Player player) {
		player.playSound(player.getLocation(), Sound.BLOCK_STONE_BUTTON_CLICK_ON, 1, 2);
	}

	public static void playSellSound(Player player) {
		player.playSound(player.getLocation(), Sound.ENTITY_ARROW_HIT, 1, 1);
	}

	public static void playBuySound(Player player) {
		player.playSound(player.getLocation(), Sound.ENTITY_ARROW_HIT, 1, 2);
	}

}

package net.infernalrealms.mining;

import java.util.Set;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerAnimationEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import net.infernalrealms.player.PlayerData;

public class MiningListener implements Listener {

	private static final PotionEffect MINING_POTION_EFFECT = new PotionEffect(PotionEffectType.SLOW_DIGGING, 10, 2, true, false);

	@EventHandler
	public void onPlayerAnimation(PlayerAnimationEvent event) {
		if (!PickaxeFactory.isPickaxeMaterial(event.getPlayer().getItemInHand().getType())) {
			return;
		}

		event.getPlayer().addPotionEffect(MINING_POTION_EFFECT, true); // Stops the cracking
		Block block = event.getPlayer().getTargetBlock((Set<Material>) null, 5);
		Ore ore = Ore.getOreFromMaterial(block.getType());
		if (ore == null) {
			// Not mining ore
			return;
		}
		PlayerData playerData = PlayerData.getData(event.getPlayer());
		if (playerData.getTargetBlock() == null || !playerData.getTargetBlock().equals(block)) {
			// New block, reset block damage
			playerData.setTargetBlock(block, ore);
			playerData.resetBlockDamage();
		}
		playerData.damageBlock();
	}

}

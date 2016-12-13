package net.infernalrealms.mobs.ai;

import static net.infernalrealms.general.InfernalRealms.RANDOM;

import org.bukkit.scheduler.BukkitRunnable;

import net.infernalrealms.mobs.spells.MobSpell;
import net.infernalrealms.mobs.types.InfernalMob;

public class SpellInterval extends BukkitRunnable {

	private InfernalMob mob;
	private MobSpell[] spells;

	public SpellInterval(InfernalMob mob, MobSpell... spells) {
		this.mob = mob;
		this.spells = spells;
	}

	@Override
	public void run() {
		if (!mob.getSelf().isAlive() || !mob.getSelf().valid) {
			cancel();
			return;
		}
		spells[RANDOM.nextInt(spells.length)].cast();
	}

}

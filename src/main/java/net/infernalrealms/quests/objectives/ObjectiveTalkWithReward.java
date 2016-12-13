package net.infernalrealms.quests.objectives;

import net.infernalrealms.quests.Reward;

public class ObjectiveTalkWithReward extends ObjectiveTalk {

	private Reward reward;

	public ObjectiveTalkWithReward(String npcName, Reward reward, String... messages) {
		super(npcName, messages);
		this.reward = reward;
	}

	public Reward getReward() {
		return this.reward;
	}

}

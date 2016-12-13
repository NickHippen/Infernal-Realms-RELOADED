package net.infernalrealms.homesteads;

public enum HomesteadTier {

	// @formatter:off
	ONE(1, 10),
	TWO(2, 20),
	THREE(3, 30),
	FOUR(4, 40),
	FIVE(5, 50),
	SIX(6, 60)
	;
	// @formatter:on

	private int tierValue;
	private int buildDistanceFromCenter;

	private HomesteadTier(int tierValue, int buildDistanceFromCenter) {
		this.tierValue = tierValue;
		this.buildDistanceFromCenter = buildDistanceFromCenter;
	}

	public int getTierValue() {
		return this.tierValue;
	}

	public int getBuildDistanceFromCenter() {
		return this.buildDistanceFromCenter;
	}

}

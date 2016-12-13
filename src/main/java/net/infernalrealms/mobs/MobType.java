package net.infernalrealms.mobs;

public enum MobType {
	NORMAL("normal"), MINI_BOSS("mini_boss"), BOSS("boss");

	private String text;

	MobType(String text) {
		this.text = text;
	}

	public String getText() {
		return this.text;
	}

	public static MobType fromString(String text) {
		if (text != null) {
			for (MobType b : MobType.values()) {
				if (text.equalsIgnoreCase(b.text)) {
					return b;
				}
			}
		}
		return NORMAL;
	}
}
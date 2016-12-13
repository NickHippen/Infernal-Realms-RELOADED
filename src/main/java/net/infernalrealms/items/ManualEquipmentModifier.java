package net.infernalrealms.items;

public class ManualEquipmentModifier implements EquipmentModifier {

	private boolean armor;
	private boolean damageStat;
	private boolean stamina;
	private boolean agility;
	private boolean spirit;
	private boolean hp;
	private boolean mp;

	public ManualEquipmentModifier(boolean armor, boolean damageStat, boolean stamina, boolean agility, boolean spirit, boolean hp,
			boolean mp) {
		this.armor = armor;
		this.damageStat = damageStat;
		this.stamina = stamina;
		this.agility = agility;
		this.spirit = spirit;
		this.hp = hp;
		this.mp = mp;

	}

	@Override
	public boolean hasArmor() {
		return armor;
	}

	@Override
	public boolean hasDamageStat() {
		return damageStat;
	}

	@Override
	public boolean hasStamina() {
		return stamina;
	}

	@Override
	public boolean hasAgility() {
		return agility;
	}

	@Override
	public boolean hasSpirit() {
		return spirit;
	}

	@Override
	public boolean hasHP() {
		return hp;
	}

	@Override
	public boolean hasMP() {
		return mp;
	}

	@Override
	public boolean isPrefix() {
		return false;
	}

	@Override
	public String getName() {
		return "";
	}

}

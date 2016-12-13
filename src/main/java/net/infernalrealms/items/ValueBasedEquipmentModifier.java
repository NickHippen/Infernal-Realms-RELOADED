package net.infernalrealms.items;

/**
 * Assumes true for every stat until proven guilty by the actual value 
 */
public class ValueBasedEquipmentModifier implements EquipmentModifier {

	@Override
	public boolean hasArmor() {
		return true;
	}

	@Override
	public boolean hasDamageStat() {
		return true;
	}

	@Override
	public boolean hasStamina() {
		return true;
	}

	@Override
	public boolean hasAgility() {
		return true;
	}

	@Override
	public boolean hasSpirit() {
		return true;
	}

	@Override
	public boolean hasHP() {
		return true;
	}

	@Override
	public boolean hasMP() {
		return true;
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

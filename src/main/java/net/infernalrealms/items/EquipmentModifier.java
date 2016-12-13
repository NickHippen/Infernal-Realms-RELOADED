package net.infernalrealms.items;

public interface EquipmentModifier {

	public boolean hasArmor();

	public boolean hasDamageStat();

	public boolean hasStamina();

	public boolean hasAgility();

	public boolean hasSpirit();

	public boolean hasHP();

	public boolean hasMP();

	public boolean isPrefix();

	public String getName();

}

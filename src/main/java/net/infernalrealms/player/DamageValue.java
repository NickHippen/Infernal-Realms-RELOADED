package net.infernalrealms.player;

public class DamageValue {

	private double damage;
	private boolean critical;

	public DamageValue(double damage) {
		this(damage, false);
	}

	public DamageValue(double damage, boolean critical) {
		this.setDamage(damage);
		this.setCritical(critical);
	}

	public double getDamage() {
		return damage;
	}

	public void setDamage(double damage) {
		this.damage = damage;
	}

	public boolean isCritical() {
		return critical;
	}

	public void setCritical(boolean critical) {
		this.critical = critical;
	}

}

package net.infernalrealms.player;

import java.util.ArrayList;
import java.util.List;

import net.infernalrealms.util.EffectsUtil;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import de.inventivegames.particle.ParticleEffect;

public enum AestheticType {

	//@formatter:off
	NONE("None", null, null, (short) 0),
	HEART("Heart", ParticleEffect.HEART, Material.INK_SACK, (short) 1),
	BROKEN_HEART("Broken Heart", ParticleEffect.ANGRY_VILLAGER, Material.INK_SACK,(short) 3),
	NOTE("Note", ParticleEffect.NOTE, Material.GREEN_RECORD, (short) 0),
	CLOUD("Cloud", ParticleEffect.CLOUD, Material.INK_SACK, (short) 7),
	GLITTER("Glitter", ParticleEffect.FIREWORKS_SPARK, Material.NETHER_STAR, (short) 0),
	FLAME("Flame", ParticleEffect.FLAME, Material.FIREBALL, (short) 0),
	SPARK("Spark", ParticleEffect.LAVA, Material.MONSTER_EGG, (short) 61),
	SLIME("Slime", ParticleEffect.SLIME, Material.SLIME_BALL, (short) 0),
	WATER_SPLASH("Water Splash", ParticleEffect.WATER_SPLASH, Material.WATER_BUCKET, (short) 0, 8),
	;
	//@formatter:on

	public static final int COST = 249; // IP

	private String displayName;
	private ParticleEffect effect;
	private Material material;
	private short damage;
	private int count;

	private AestheticType(String displayName, ParticleEffect effect, Material material, short damage) {
		this(displayName, effect, material, damage, 1);
	}

	private AestheticType(String displayName, ParticleEffect effect, Material material, short damage, int count) {
		this.displayName = displayName;
		this.effect = effect;
		this.material = material;
		this.damage = damage;
		this.count = count;
	}

	public ParticleEffect getEffect() {
		return this.effect;
	}

	public void playFollowEffect(Player player) {
		if (this == NONE) {
			return;
		}
		EffectsUtil.sendParticleToLocation(getEffect(), player.getLocation(), 0.1F, 0F, 0.1F, 0F, count);
	}

	public void playIdleEffect(Player player) {
		if (this == NONE) {
			return;
		}
		EffectsUtil.sendParticleToLocation(getEffect(), player.getLocation().add(0, 2.5, 0), 0.5F, 0.5F, 0.5F, 0F, count * 3);
	}

	public void playAttackEffect(Entity target) {
		if (this == NONE) {
			return;
		}
		EffectsUtil.sendParticleToLocation(getEffect(), target.getLocation(), 0.5F, 0.5F, 0.5F, 0F, count * 3);
	}

	public void playDamagedEffect(Player player) {
		if (this == NONE) {
			return;
		}
		EffectsUtil.sendParticleToLocation(getEffect(), player.getLocation(), 0.5F, 0.5F, 0.5F, 0F, count * 3);
	}

	private ItemStack getIcon(boolean shop) {
		if (this == NONE) {
			return null;
		}
		ItemStack icon = new ItemStack(material, 1, damage);
		ItemMeta iconMeta = icon.getItemMeta();
		iconMeta.setDisplayName(ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + getDisplayName());
		if (shop) {
			List<String> iconLore = new ArrayList<>();
			iconLore.add(ChatColor.GRAY + "" + ChatColor.BOLD + "PRICE: " + ChatColor.AQUA + COST + ChatColor.BOLD + " I.P");
			iconMeta.setLore(iconLore);
		}
		icon.setItemMeta(iconMeta);
		return icon;
	}

	public void purchase(Player player) {
		PlayerData playerData = PlayerData.getData(player);
		if (playerData.hasUnlockedParticle(this)) {
			player.sendMessage(ChatColor.RED + "You already own this particle!");
			return;
		}
		if (playerData.getPremiumMoney() < COST) {
			player.sendMessage(ChatColor.RED + "You do not have enough IP to purchase this!");
			return;
		}
		playerData.addUnlockedParticle(this);
		playerData.modifyPremiumMoney(-COST);
		player.sendMessage(ChatColor.GREEN + "Purchase successful!");
	}

	public ItemStack getManagerIcon() {
		return getIcon(false);
	}

	public ItemStack getShopIcon() {
		return getIcon(true);
	}

	public String getDisplayName() {
		return this.displayName;
	}

	public static AestheticType fromString(String name) {
		for (AestheticType at : values()) {
			if (at.toString().equalsIgnoreCase(name)) {
				return at;
			}
		}
		return null;
	}

	public static AestheticType fromManagerIcon(ItemStack icon) {
		for (AestheticType at : values()) {
			if (at == NONE) {
				continue;
			}
			if (at.getManagerIcon().equals(icon)) {
				return at;
			}
		}
		return null;
	}

}

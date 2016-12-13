package net.infernalrealms.mount;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import de.inventivegames.particle.ParticleEffect;
import net.infernalrealms.items.ActiveItem;
import net.infernalrealms.player.PlayerData;
import net.infernalrealms.util.EffectsUtil;
import net.infernalrealms.util.GeneralUtil;

public class Feed extends ActiveItem {

	public static final Feed NORMAL_SMALL = new Feed(1);
	public static final Feed NORMAL_MEDIUM = new Feed(5);
	public static final Feed NORMAL_LARGE = new Feed(10);
	public static final Feed NORMAL_BUNDLE = new Feed(20);
	public static final Feed STEROID_SMALL = new Feed(1, true);
	public static final Feed STEROID_MEDIUM = new Feed(5, true);
	public static final Feed STEROID_LARGE = new Feed(10, true);

	private int quantity;
	private boolean steroid;

	public Feed() {}

	private Feed(int quantity) {
		this(quantity, false);
	}

	private Feed(int quantity, boolean steroid) {
		super();
		this.quantity = quantity;
		this.steroid = steroid;
		setBukkitItem(generateItem());
	}

	public int getQuantity() {
		return this.quantity;
	}

	@Override
	@EventHandler
	public boolean onUse(InventoryClickEvent event) {
		if (event.getClick() != ClickType.RIGHT || !(event.getWhoClicked() instanceof Player)) {
			return false;
		}
		if (!isFeed(event.getCurrentItem())) {
			return false;
		}
		Player player = (Player) event.getWhoClicked();
		if (!(player.getVehicle() instanceof Horse)) {
			return false;
		}
		event.setCancelled(true);
		Horse horse = (Horse) player.getVehicle();
		PlayerData playerData = PlayerData.getData(player);
		ItemStack feed = event.getCurrentItem();
		boolean steroid = Feed.isSteroid(feed);
		if (!steroid && playerData.getMountHunger() >= MountManager.convertLevelToMaxHunger(playerData.getMountMaxHungerLevel()) - 3) {
			player.sendMessage(ChatColor.RED + "Your mount is currently not hungry enough!");
			return false;
		} else {
			playerData.modifyMountHunger(steroid ? 10 : 5);
			EffectsUtil.sendParticleToLocation(ParticleEffect.HEART, player.getLocation(), 0.5F, 0.5F, 0.5F, 1F, 10);
			int expToAdd = (steroid ? 64 : 32) * (int) playerData.getProfessionExpMultiplier();
			player.sendMessage(
					ChatColor.GRAY + "" + ChatColor.BOLD + "+" + ChatColor.GRAY + expToAdd + ChatColor.DARK_PURPLE + " Mount EXP");
			playerData.modifyMountExp(expToAdd);
			MountManager.updateSaddle(player, horse);
		}
		if (!GeneralUtil.takeOne(event.getCurrentItem())) {
			event.setCurrentItem(null);
		}
		return true;
	}

	public ItemStack generateItem() {
		ItemStack feed = new ItemStack(Material.WHEAT, getQuantity(), (short) 0);
		ItemMeta feedMeta = feed.getItemMeta();
		List<String> feedLore = new ArrayList<>();
		if (!steroid) {
			feedMeta.setDisplayName(ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + "Horse Feed");
			feedLore.add(ChatColor.GRAY + "Instantly feeds your horse for");
			feedLore.add(ChatColor.GRAY + "5 hunger.");
		} else {
			feedMeta.setDisplayName(ChatColor.GOLD + "" + ChatColor.BOLD + "Horse Steroid Feed");
			feedLore.add(ChatColor.GRAY + "Instantly feeds your horse for");
			feedLore.add(ChatColor.GRAY + "10 hunger and double experience");
			feedLore.add(ChatColor.GRAY + "(no hunger required).");
		}
		feedLore.add(ChatColor.GRAY + "" + ChatColor.ITALIC + "Right-click this in your inventory");
		feedLore.add(ChatColor.GRAY + "" + ChatColor.ITALIC + "while mounted to use.");
		feedMeta.setLore(feedLore);
		feed.setItemMeta(feedMeta);
		return feed;
	}

	public static boolean isSteroid(ItemStack item) {
		return item != null && item.getType() == Material.WHEAT && item.hasItemMeta() && item.getItemMeta().hasLore()
				&& item.getItemMeta().getLore().contains(ChatColor.GRAY + "(no hunger required).");
	}

	public static boolean isFeed(ItemStack item) {
		return item != null && item.getType() == Material.WHEAT && item.hasItemMeta() && item.getItemMeta().hasLore()
				&& item.getItemMeta().getLore().contains(ChatColor.GRAY + "" + ChatColor.ITALIC + "Right-click this in your inventory");
	}
}

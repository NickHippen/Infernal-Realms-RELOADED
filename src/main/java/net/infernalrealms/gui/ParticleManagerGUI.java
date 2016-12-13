package net.infernalrealms.gui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import net.infernalrealms.items.GeneralItems;
import net.infernalrealms.player.AestheticType;
import net.infernalrealms.player.PlayerData;

public class ParticleManagerGUI implements Listener {

	public static final String TITLE = "Particle Manager";

	private static final List<Integer> PARTICLE_SLOTS = Arrays.asList(0, 1, 2, 3, 9, 10, 11, 12, 18, 19, 20, 21, 27, 28, 29, 30, 36, 37, 38,
			39);
	private static final int ATTACK_SLOT = 14;
	private static final int HURT_SLOT = 16;
	private static final int MOVE_SLOT = 32;
	private static final int IDLE_SLOT = 34;

	public static void open(Player player) {
		Inventory inv = Bukkit.createInventory(player, 45, TITLE);
		PlayerData playerData = PlayerData.getData(player);

		for (int i : new int[] { 4, 5, 6, 7, 8, 13, 15, 17, 22, 23, 24, 25, 26, 31, 33, 35, 40, 41, 42, 43, 44 }) {
			inv.setItem(i, GeneralItems.BLANK_BLACK_GLASS_PANE);
		}

		AestheticType attackParticle = playerData.getAttackParticles();
		AestheticType hurtParticle = playerData.getDamagedParticles();
		AestheticType moveParticle = playerData.getFollowParticles();
		AestheticType idleParticle = playerData.getIdleParticles();

		if (attackParticle == AestheticType.NONE) {
			ItemStack attackSlot = new ItemStack(Material.IRON_FENCE, 1, (short) 0);
			ItemMeta attackSlotMeta = attackSlot.getItemMeta();
			attackSlotMeta.setDisplayName(ChatColor.AQUA + "" + ChatColor.BOLD + "Attack Particle");
			List<String> attackSlotLore = new ArrayList<>();
			attackSlotLore.add(ChatColor.GRAY + "" + ChatColor.ITALIC + "Drag and drop particle effects");
			attackSlotLore.add(ChatColor.GRAY + "" + ChatColor.ITALIC + "here to equip them.");
			attackSlotLore.add(ChatColor.GRAY + "" + ChatColor.BOLD + "Modifies " + ChatColor.GRAY + "the particles that show");
			attackSlotLore.add(ChatColor.GRAY + "up when you attack an entity.");
			attackSlotMeta.setLore(attackSlotLore);
			attackSlot.setItemMeta(attackSlotMeta);
			inv.setItem(ATTACK_SLOT, attackSlot);
		} else {
			ItemStack icon = attackParticle.getManagerIcon();
			ItemMeta iconMeta = icon.getItemMeta();
			String displayName = iconMeta.getDisplayName();
			iconMeta.setDisplayName(ChatColor.AQUA + "" + ChatColor.BOLD + "Attack Particle");
			iconMeta.setLore(Arrays.asList(displayName));
			icon.setItemMeta(iconMeta);
			inv.setItem(ATTACK_SLOT, icon);
		}

		if (hurtParticle == AestheticType.NONE) {
			ItemStack hurtSlot = new ItemStack(Material.IRON_FENCE, 1, (short) 0);
			ItemMeta hurtSlotMeta = hurtSlot.getItemMeta();
			hurtSlotMeta.setDisplayName(ChatColor.AQUA + "" + ChatColor.BOLD + "Hurt Particle");
			List<String> hurtSlotLore = new ArrayList<>();
			hurtSlotLore.add(ChatColor.GRAY + "" + ChatColor.ITALIC + "Drag and drop particle effects");
			hurtSlotLore.add(ChatColor.GRAY + "" + ChatColor.ITALIC + "here to equip them.");
			hurtSlotLore.add(ChatColor.GRAY + "" + ChatColor.BOLD + "Modifies " + ChatColor.GRAY + "the particles that show");
			hurtSlotLore.add(ChatColor.GRAY + "up when you take damage.");
			hurtSlotMeta.setLore(hurtSlotLore);
			hurtSlot.setItemMeta(hurtSlotMeta);
			inv.setItem(HURT_SLOT, hurtSlot);
		} else {
			ItemStack icon = hurtParticle.getManagerIcon();
			ItemMeta iconMeta = icon.getItemMeta();
			String displayName = iconMeta.getDisplayName();
			iconMeta.setDisplayName(ChatColor.AQUA + "" + ChatColor.BOLD + "Hurt Particle");
			iconMeta.setLore(Arrays.asList(displayName));
			icon.setItemMeta(iconMeta);
			inv.setItem(HURT_SLOT, icon);
		}

		if (moveParticle == AestheticType.NONE) {
			ItemStack moveSlot = new ItemStack(Material.IRON_FENCE, 1, (short) 0);
			ItemMeta moveSlotMeta = moveSlot.getItemMeta();
			moveSlotMeta.setDisplayName(ChatColor.AQUA + "" + ChatColor.BOLD + "Move Particle");
			List<String> moveSlotLore = new ArrayList<>();
			moveSlotLore.add(ChatColor.GRAY + "" + ChatColor.ITALIC + "Drag and drop particle effects");
			moveSlotLore.add(ChatColor.GRAY + "" + ChatColor.ITALIC + "here to equip them.");
			moveSlotLore.add(ChatColor.GRAY + "" + ChatColor.BOLD + "Modifies " + ChatColor.GRAY + "the particles that are");
			moveSlotLore.add(ChatColor.GRAY + "trailed behind your feet.");
			moveSlotMeta.setLore(moveSlotLore);
			moveSlot.setItemMeta(moveSlotMeta);
			inv.setItem(MOVE_SLOT, moveSlot);
		} else {
			ItemStack icon = moveParticle.getManagerIcon();
			ItemMeta iconMeta = icon.getItemMeta();
			String displayName = iconMeta.getDisplayName();
			iconMeta.setDisplayName(ChatColor.AQUA + "" + ChatColor.BOLD + "Move Particle");
			iconMeta.setLore(Arrays.asList(displayName));
			icon.setItemMeta(iconMeta);
			inv.setItem(MOVE_SLOT, icon);
		}

		if (idleParticle == AestheticType.NONE) {
			ItemStack idleSlot = new ItemStack(Material.IRON_FENCE, 1, (short) 0);
			ItemMeta idleSlotMeta = idleSlot.getItemMeta();
			idleSlotMeta.setDisplayName(ChatColor.AQUA + "" + ChatColor.BOLD + "Idle Particle");
			List<String> idleSlotLore = new ArrayList<>();
			idleSlotLore.add(ChatColor.GRAY + "" + ChatColor.ITALIC + "Drag and drop particle effects");
			idleSlotLore.add(ChatColor.GRAY + "" + ChatColor.ITALIC + "here to equip them.");
			idleSlotLore.add(ChatColor.GRAY + "" + ChatColor.BOLD + "Modifies " + ChatColor.GRAY + "the particles that");
			idleSlotLore.add(ChatColor.GRAY + "shows when you become idle.");
			idleSlotMeta.setLore(idleSlotLore);
			idleSlot.setItemMeta(idleSlotMeta);
			inv.setItem(IDLE_SLOT, idleSlot);
		} else {
			ItemStack icon = idleParticle.getManagerIcon();
			ItemMeta iconMeta = icon.getItemMeta();
			String displayName = iconMeta.getDisplayName();
			iconMeta.setDisplayName(ChatColor.AQUA + "" + ChatColor.BOLD + "Idle Particle");
			iconMeta.setLore(Arrays.asList(displayName));
			icon.setItemMeta(iconMeta);
			inv.setItem(IDLE_SLOT, icon);
		}

		List<AestheticType> particles = playerData.getUnlockedParticles();

		if (!particles.isEmpty()) {
			for (AestheticType type : particles) {
				if (type == null) {
					System.out.println("Error with particle: " + type);
					continue;
				}
				inv.addItem(type.getManagerIcon());
			}
		} else {
			ItemStack icon = new ItemStack(Material.SPONGE, 1, (short) 0);
			ItemMeta iconMeta = icon.getItemMeta();
			iconMeta.setDisplayName(ChatColor.RED + "You do not own any particles!");
			iconMeta.setDisplayName(
					ChatColor.RED + "You can buy more at the " + ChatColor.AQUA + ChatColor.BOLD + "I.P." + ChatColor.AQUA + " shop.");
			icon.setItemMeta(iconMeta);
			inv.addItem(icon);
		}

		player.openInventory(inv);
	}

	@EventHandler
	public void onGUIClick(InventoryClickEvent event) {
		if (!event.getInventory().getTitle().equals(TITLE) || !(event.getWhoClicked() instanceof Player)) {
			return;
		}
		if (event.getClick() != ClickType.LEFT && event.getClick() != ClickType.RIGHT) {
			event.setCancelled(true);
		}
		Player player = (Player) event.getWhoClicked();
		PlayerData playerData = PlayerData.getData(player);
		ItemStack clickedOn = event.getCurrentItem();
		if (!PARTICLE_SLOTS.contains(event.getRawSlot()) || clickedOn.getType() == Material.SPONGE) {
			event.setCancelled(true);
		}
		ItemStack clickedWith = event.getCursor();
		if (clickedWith == null || clickedWith.getType() == Material.AIR) {
			if (event.getRawSlot() == ATTACK_SLOT) {
				// Unequip attack
				playerData.setAttackParticles(AestheticType.NONE);
			} else if (event.getRawSlot() == HURT_SLOT) {
				// Unequip hurt
				playerData.setDamagedParticles(AestheticType.NONE);
			} else if (event.getRawSlot() == MOVE_SLOT) {
				// Unequip move
				playerData.setFollowParticles(AestheticType.NONE);
			} else if (event.getRawSlot() == IDLE_SLOT) {
				// Unequip idle
				playerData.setIdleParticles(AestheticType.NONE);
			} else {
				return;
			}
			open(player);
			return;
		}
		AestheticType heldAesthetic = AestheticType.fromManagerIcon(clickedWith);
		if (heldAesthetic == null) {
			return;
		}
		if (event.getRawSlot() == ATTACK_SLOT) {
			playerData.setAttackParticles(heldAesthetic);
		} else if (event.getRawSlot() == HURT_SLOT) {
			playerData.setDamagedParticles(heldAesthetic);
		} else if (event.getRawSlot() == MOVE_SLOT) {
			playerData.setFollowParticles(heldAesthetic);
		} else if (event.getRawSlot() == IDLE_SLOT) {
			playerData.setIdleParticles(heldAesthetic);
		} else {
			return;
		}
		event.setCursor(null);
		open(player);
	}

	@EventHandler(priority = EventPriority.HIGH)
	public void onItemDrop(PlayerDropItemEvent event) {
		if (AestheticType.fromManagerIcon(event.getItemDrop().getItemStack()) != null) {
			event.getItemDrop().remove();
		}
	}
}

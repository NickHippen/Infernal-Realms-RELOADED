package net.infernalrealms.util;

import static net.infernalrealms.general.InfernalRealms.RANDOM;

import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.v1_9_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_9_R1.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_9_R1.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_9_R1.inventory.CraftItemStack;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Item;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import com.mojang.authlib.properties.PropertyMap;

import net.infernalrealms.general.GeneralListener;
import net.infernalrealms.general.InfernalRealms;
import net.infernalrealms.inventory.InventoryManager;
import net.infernalrealms.mobs.types.CustomArrow;
import net.infernalrealms.player.PlayerData;
import net.infernalrealms.player.Stat;
import net.infernalrealms.skills.warrior.Recovery;
import net.minecraft.server.v1_9_R1.Entity;
import net.minecraft.server.v1_9_R1.EntityExperienceOrb;
import net.minecraft.server.v1_9_R1.EntityLiving;
import net.minecraft.server.v1_9_R1.EntityPlayer;
import net.minecraft.server.v1_9_R1.EntityTrackerEntry;
import net.minecraft.server.v1_9_R1.NBTTagCompound;
import net.minecraft.server.v1_9_R1.NBTTagList;

public class GeneralUtil {

	private GeneralUtil() {}

	/**
	* Adds the glow.
	*
	* @param item
	* the item
	* @return the item stack
	*/
	public static ItemStack addGlow(ItemStack item) {
		net.minecraft.server.v1_9_R1.ItemStack nmsStack = CraftItemStack.asNMSCopy(item);
		NBTTagCompound tag = null;
		if (!nmsStack.hasTag()) {
			tag = new NBTTagCompound();
			nmsStack.setTag(tag);
		}
		if (tag == null)
			tag = nmsStack.getTag();
		NBTTagList ench = new NBTTagList();
		tag.set("ench", ench);
		nmsStack.setTag(tag);
		return CraftItemStack.asCraftMirror(nmsStack);
	}

	/**
	* Removes the glow.
	*
	* @param item
	* the item
	* @return the item stack
	*/
	public static ItemStack removeGlow(ItemStack item) {
		net.minecraft.server.v1_9_R1.ItemStack nmsStack = CraftItemStack.asNMSCopy(item);
		NBTTagCompound tag = null;
		if (!nmsStack.hasTag())
			return item;
		tag = nmsStack.getTag();
		tag.set("ench", null);
		nmsStack.setTag(tag);
		return CraftItemStack.asCraftMirror(nmsStack);
	}

	public static String getMoneyAsString(long bal) {
		return getMoneyAsString(bal, ChatColor.WHITE);
	}

	public static String getMoneyAsString(long bal, ChatColor color) {
		long copper = bal;
		long silver = copper / 100L;
		copper -= silver * 100L;
		long gold = silver / 100L;
		silver -= gold * 100L;
		return color + "" + gold + ChatColor.YELLOW + "● " + color + silver + ChatColor.GRAY + "● " + color + copper + ChatColor.GOLD + "●";
	}

	public static String getMoneyAsShortString(long bal, ChatColor color) {
		long copper = bal;
		long silver = copper / 100L;
		copper -= silver * 100L;
		long gold = silver / 100L;
		silver -= gold * 100L;
		return ChatColor.RESET + "" + (gold != 0 ? color + "" + gold + ChatColor.YELLOW + "● " : "")
				+ (silver != 0 ? color + "" + silver + ChatColor.GRAY + "● " : "") + color + copper + ChatColor.GOLD + "●";
	}

	public static long convertCoinsToMoney(long gold, long silver, long copper) {
		return copper + (silver * 100) + (gold * 10000);
	}

	public static EntityLiving getHandle(LivingEntity entity) {
		return (EntityLiving) getHandle((org.bukkit.entity.Entity) entity);
	}

	public static Entity getHandle(org.bukkit.entity.Entity entity) {
		if (!(entity instanceof CraftEntity))
			return null;
		return ((CraftEntity) entity).getHandle();
	}

	public static void playItemSpray(Material m, Location location, int num, float force) {
		if (m == null)
			return;

		// Spawn Items
		Random rand = new Random();
		Location loc = location.clone().add(0, 1, 0);
		final Item[] items = new Item[num];
		for (int i = 0; i < num; i++) {
			items[i] = loc.getWorld().dropItem(loc, new ItemStack(m));
			items[i].setVelocity(
					new Vector((rand.nextDouble() - .5) * force, (rand.nextDouble() - .5) * force, (rand.nextDouble() - .5) * force));
			items[i].setPickupDelay(20);
		}

		// Delete Items
		Bukkit.getScheduler().scheduleSyncDelayedTask(InfernalRealms.getPlugin(), new Runnable() {
			public void run() {
				for (int i = 0; i < items.length; i++) {
					items[i].remove();
				}
			}
		}, 10L);
	}

	public static Class<?> getNMSClass(String name) throws ClassNotFoundException {
		String version = Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3] + ".";
		String className = "net.minecraft.server." + version + name;
		return Class.forName(className);
	}

	/**
	 * @param player player to be healed
	 * @param amount base heal
	 * @return the modified heal amount
	 */
	public static double healPlayer(Player player, double amount) {
		PlayerData playerData = PlayerData.getData(player);
		if (playerData.getPlayerSuperClass().equalsIgnoreCase("Warrior")) {
			amount *= new Recovery(player).getHealing();
		}
		double newHealth = player.getHealth() + amount;
		player.setHealth(newHealth > player.getMaxHealth() ? player.getMaxHealth() : newHealth);
		Stat.refreshHealthNumber(player);
		return amount;
	}

	/**
	 * 
	 * @param inv The inventory being modified.
	 * @param slot The slot being modified.
	 * @param rate The rate (in ticks) each frame will change
	 * @param frames The frames of the animation
	 */
	public static void animateFrames(final Inventory inv, final int slot, long rate, final ItemMeta... frames) {
		if (inv == null)
			return;
		final ItemStack item = inv.getItem(slot);
		if (item == null || item.getType() == Material.AIR)
			return;

		new BukkitRunnable() {

			private int f = 0;

			@Override
			public void run() {
				if (inv == null || inv.getViewers().size() == 0)
					cancel();
				if (f > frames.length - 1)
					f = 0;
				item.setItemMeta(frames[f]);
				inv.setItem(slot, item);
				f++;
			}
		}.runTaskTimer(InfernalRealms.getPlugin(), rate, rate);
	}

	/**
	 * Clones the contents of the inventory with the respective index of the array is relative to its inventory slot location.
	 * @param inv
	 * @return the cloned inventory contents
	 */
	public static ItemStack[] cloneInventoryContents(Inventory inv) {
		ItemStack[] clone = new ItemStack[inv.getSize()];
		for (int i = 0; i < clone.length; i++) {
			clone[i] = inv.getItem(i);
		}
		return clone;
	}

	public static void setInventoryContents(Inventory inv, ItemStack[] contents) {
		for (int i = 0; i < contents.length; i++) {
			inv.setItem(i, contents[i]);
		}
	}

	public static boolean hasInventorySpace(Inventory inv, ItemStack... items) {
		Inventory cloneInv = Bukkit.createInventory(null, inv.getSize());
		for (int i = 0; i < inv.getSize(); i++) {
			cloneInv.setItem(i, inv.getItem(i));
		}
		return addItemsToInventory(cloneInv, items).size() == 0;
	}

	public static HashMap<Integer, ItemStack> addItemsToInventory(Inventory inv, ItemStack... items) {
		HashMap<Integer, ItemStack> noSpaceItems = new HashMap<>();
		for (int i = 0; i < items.length; i++) {
			if (items[i] != null) {
				HashMap<Integer, ItemStack> nsi = inv.addItem(items[i]);
				if (nsi.size() != 0) {
					noSpaceItems.put(0, nsi.get(0));
				}
			}
		}
		return noSpaceItems;
	}

	public static void addItemToInventoryOrDrop(Player player, ItemStack item) {
		boolean playerDropped = false;
		HashMap<Integer, ItemStack> droppedItem = player.getInventory().addItem(item);
		if (droppedItem.size() != 0) {
			Item itemDropped = player.getWorld().dropItem(player.getLocation(), droppedItem.get(0));
			itemDropped.setMetadata("OwnedItem", new FixedMetadataValue(InfernalRealms.getPlugin(), player.getName())); // Sets the item to be safe from robbers.
			playerDropped = true;
		}
		if (playerDropped) {
			player.sendMessage(ChatColor.RED + "" + ChatColor.BOLD
					+ "Because your inventory was full, an item has dropped to the ground! No one else can pick it up, but it may disappear if you wait too long to reclaim it.");
		}
		InventoryManager.updateMainInventoryContent(player);
	}

	/**
	 * @param item
	 * @return Whether or not the item amount is > 1
	 */
	public static boolean takeOne(ItemStack item) {
		if (item.getAmount() <= 1) {
			return false;
		}
		item.setAmount(item.getAmount() - 1);
		return true;
	}

	public static boolean take(ItemStack item, int amount) { // TODO test
		if (item.getAmount() <= amount) {
			return false;
		}
		item.setAmount(item.getAmount() - amount);
		return true;
	}

	//	public static boolean hasInventorySpace(Inventory inv, ItemStack item) {
	//		if (inv.firstEmpty() == -1)
	//			return true;
	//		for (int i = 0; i < inv.getSize(); i++) {
	//			ItemStack indexItem = inv.getItem(i);
	//			if (item.isSimilar(indexItem) && indexItem.getAmount() + item.getAmount() < indexItem.getMaxStackSize()) {
	//				return true;
	//			}
	//		}
	//		return false;
	//	}

	public static EntityTrackerEntry getEnttiyTrackerEntry(Player player) {
		return ((EntityTrackerEntry) ((CraftWorld) player.getWorld()).getHandle().tracker.trackedEntities.get(player.getEntityId()));
	}

	/**
	 * 
	 * @param player the player that is checking if the target is loaded for them
	 * @param target the target that player is checking
	 * @return if player has target loaded
	 */
	public static boolean playerHasPlayerLoaded(Player player, Player target) {
		return ((EntityTrackerEntry) ((CraftWorld) player.getWorld()).getHandle().tracker.trackedEntities
				.get(player.getEntityId())).trackedPlayers.contains(((CraftPlayer) target).getHandle());
	}

	public static String combineArgsToString(String[] args) {
		if (args.length == 0) {
			return "";
		}
		String combined = "";
		for (int i = 0; i < args.length; i++) {
			combined += args[i] + " ";
		}
		return combined.substring(0, combined.length() - 1);
	}

	public static List<org.bukkit.entity.Entity> getNearbyEntities(Location location, double x, double y, double z) {
		Entity entity = new EntityExperienceOrb(((CraftWorld) location.getWorld()).getHandle());
		entity.setLocation(location.getX(), location.getY(), location.getZ(), 0F, 0F);
		List<org.bukkit.entity.Entity> nearbyEntities = entity.getBukkitEntity().getNearbyEntities(x, y, z);
		entity.getBukkitEntity().remove();
		return nearbyEntities;
	}

	public static Arrow fireArrow(Player player) {
		return fireArrow(player, 3F, 0F);
	}

	public static Arrow fireArrow(Player player, float force, float accuracyVariation) {
		Vector v = player.getEyeLocation().getDirection().multiply(3.5);
		EntityPlayer nmsPlayer = ((CraftPlayer) player).getHandle();
		//		Location shootLocation = player.getEyeLocation().add(v.multiply(0.7));
		CustomArrow arrow = new CustomArrow(nmsPlayer.getWorld(), nmsPlayer, 1F);
		//		arrow.setPositionRotation(player.getEyeLocation().getX(), player.getEyeLocation().getY(), player.getEyeLocation().getZ(),
		//				player.getEyeLocation().getYaw(), player.getEyeLocation().getPitch());
		//		arrow.shoot(v.getX(), v.getY(), v.getZ(), 3F, 0F);
		arrow.getBukkitEntity().setVelocity(v);
		nmsPlayer.getWorld().addEntity(arrow);
		return (Arrow) arrow.getBukkitEntity();
	}

	public static void fireNormalArrow(Player player) {
		if (!GeneralListener.hasRecentlyShotArrow(player)) {
			GeneralUtil.fireArrow(player);
			GeneralListener.setRecentlyShotArrow(player);
			player.playSound(player.getLocation(), Sound.ENTITY_ARROW_SHOOT, 0.5F, 1F);
		}
	}

	public static GameProfile createGameProfile(String texture, UUID id) {
		GameProfile profile = new GameProfile(id, null);
		PropertyMap propertyMap = profile.getProperties();
		if (propertyMap == null) {
			Bukkit.getLogger().log(Level.INFO, "No property map found in GameProfile, can't continue.");
			return null;
		}
		propertyMap.put("textures", new Property("textures", texture));
		propertyMap.put("Signature", new Property("Signature", "1234"));
		return profile;
	}

	/**
	 * Generates a random int from min to max, both inclusive
	 * @param min the minimun bound, inclusive
	 * @param max the maximum bound, inclusive
	 * @throws IllegalArgumentException if min is greater than max
	 * @return the randomly generated int
	 */
	public static int randomIntRange(int min, int max) {
		if (min > max) {
			throw new IllegalArgumentException("Min must be less than max!");
		}
		return RANDOM.nextInt((max - min) + 1) + min;
	}

	/**
	 * Performs the action until the number of iterations have been performed or the action returns false
	 * @param iterations the number of iterations to perform. Use -1 for an infinite loop
	 * @param startDelay the initial delay before iterations begin
	 * @param iterationDelay the delay between iterations
	 * @param action the action to be performed. This controls the flow of the loop, where if it returns false, the loop will break
	 */
	public static void delayedLoop(int iterations, long startDelay, long iterationDelay, FlowAction action) {
		new BukkitRunnable() {
			int count = iterations;

			@Override
			public void run() {
				if (count != -1 && count > 0) {
					cancel();
					return;
				}
				if (!action.run()) {
					cancel();
					return;
				}
				count--;
			}
		}.runTaskTimer(InfernalRealms.getPlugin(), startDelay, iterationDelay);
	}

	/**
	 * Hashes the string into a mostly unique integer value
	 * @param string the string to be hashed
	 * @return the hashed value
	 */
	public static int hashString(String string) {
		int hash = 7;
		for (int i = 0; i < string.length(); i++) {
			hash = hash * 31 + string.charAt(i);
		}
		return hash;
	}

	@FunctionalInterface
	public static interface FlowAction {

		public boolean run();

	}

}

package net.infernalrealms.gui;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import net.infernalrealms.general.InfernalRealms;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class BossBarDisplay {

	private static Constructor<?> packetPlayOutSpawnEntityLiving;
	private static Constructor<?> entityEnderdragon;

	private static Method setLocation;
	private static Method setCustomName;
	private static Method setHealth;
	private static Method setInvisible;

	private static Method getWorldHandle;
	private static Method getPlayerHandle;
	private static Field playerConnection;
	private static Method sendPacket;

	private static Method getDatawatcher;
	private static Method a;
	private static Field d;

	public static Map<String, Object> playerDragons = new HashMap<String, Object>();
	public static Map<String, String> playerTextDragon = new HashMap<String, String>();

	static {
		try {

			packetPlayOutSpawnEntityLiving = getMCClass("PacketPlayOutSpawnEntityLiving").getConstructor(getMCClass("EntityLiving"));
			entityEnderdragon = getMCClass("EntityEnderDragon").getConstructor(getMCClass("World"));

			setLocation = getMCClass("EntityEnderDragon").getMethod("setLocation", double.class, double.class, double.class, float.class,
					float.class);
			setCustomName = getMCClass("EntityEnderDragon").getMethod("setCustomName", new Class<?>[] { String.class });
			setHealth = getMCClass("EntityEnderDragon").getMethod("setHealth", new Class<?>[] { float.class });
			setInvisible = getMCClass("EntityEnderDragon").getMethod("setInvisible", new Class<?>[] { boolean.class });

			getWorldHandle = getCraftClass("CraftWorld").getMethod("getHandle");
			getPlayerHandle = getCraftClass("entity.CraftPlayer").getMethod("getHandle");
			playerConnection = getMCClass("EntityPlayer").getDeclaredField("playerConnection");
			sendPacket = getMCClass("PlayerConnection").getMethod("sendPacket", getMCClass("Packet"));

			getDatawatcher = getMCClass("EntityEnderDragon").getMethod("getDataWatcher");
			a = getMCClass("DataWatcher").getMethod("a", int.class, Object.class);
			d = getMCClass("DataWatcher").getDeclaredField("d");
			d.setAccessible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static Object getEnderDragon(Player p) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException,
			InstantiationException {
		if (playerDragons.containsKey(p.getName())) {
			return playerDragons.get(p.getName());
		} else {
			Object nms_world = getWorldHandle.invoke(p.getWorld());
			playerDragons.put(p.getName(), entityEnderdragon.newInstance(nms_world));
			return getEnderDragon(p);
		}
	}

	public static void setBossBartext(Player p, String text) {
		playerTextDragon.put(p.getName(), text);
		try {
			Object nms_dragon = getEnderDragon(p);
			//setLocation.invoke(nms_dragon, loc.getX(), 150, loc.getZ(), 0F, 0F);
			setLocation.invoke(nms_dragon, getPlayerLoc(p).getX(), getPlayerLoc(p).getY() + 0, getPlayerLoc(p).getZ(), 0F, 0F);
			setCustomName.invoke(nms_dragon, text);
			setHealth.invoke(nms_dragon, 200);
			setInvisible.invoke(nms_dragon, true);
			//			changeWatcher(nms_dragon, text);
			Object nms_packet = packetPlayOutSpawnEntityLiving.newInstance(nms_dragon);
			Object nms_player = getPlayerHandle.invoke(p);
			Object nms_connection = playerConnection.get(nms_player);
			sendPacket.invoke(nms_connection, nms_packet);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void setBossBar(final Player p, final String text) {
		if (!InfernalRealms.BOSS_BAR_ENABLED) {
			return;
		}
		playerTextDragon.put(p.getName(), text);

		if (playerDragons.containsKey(p.getName()))
			removeBossBar(p);

		new BukkitRunnable() {

			@Override
			public void run() {
				try {
					Object nms_dragon = getEnderDragon(p);
					setLocation.invoke(nms_dragon, p.getLocation().getX(), -85, p.getLocation().getZ(), 0F, 0F);
					setCustomName.invoke(nms_dragon, text);
					setHealth.invoke(nms_dragon, (float) (200F * ((Damageable) p).getHealth() / ((Damageable) p).getMaxHealth()));
					setInvisible.invoke(nms_dragon, true);
					//					changeWatcher(nms_dragon, text);
					Object nms_packet = packetPlayOutSpawnEntityLiving.newInstance(nms_dragon);
					Object nms_player = getPlayerHandle.invoke(p);
					Object nms_connection = playerConnection.get(nms_player);
					playerDragons.put(p.getName(), nms_dragon);
					sendPacket.invoke(nms_connection, nms_packet);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}.runTaskLater(InfernalRealms.getPlugin(), 1L);

	}

	public static void removeBossBar(Player p) {
		if (!InfernalRealms.BOSS_BAR_ENABLED) {
			return;
		}
		playerTextDragon.remove(p.getName());
		try {
			Object nms_dragon = getEnderDragon(p);
			setLocation.invoke(nms_dragon, p.getLocation().getX(), -5000, p.getLocation().getZ(), 0F, 0F);
			setCustomName.invoke(nms_dragon, " ");
			setHealth.invoke(nms_dragon, 0);
			setInvisible.invoke(nms_dragon, true);
			//			changeWatcher(nms_dragon, " ");
			Object nms_packet = packetPlayOutSpawnEntityLiving.newInstance(nms_dragon);
			Object nms_player = getPlayerHandle.invoke(p);
			Object nms_connection = playerConnection.get(nms_player);
			sendPacket.invoke(nms_connection, nms_packet);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void removehorligneD(Player p) {
		playerDragons.remove(p.getName());
		playerTextDragon.remove(p.getName());
	}

	private static void changeWatcher(Object nms_entity, String text) throws IllegalAccessException, IllegalArgumentException,
			InvocationTargetException {
		Object nms_watcher = getDatawatcher.invoke(nms_entity);
		Map<?, ?> map = (Map<?, ?>) d.get(nms_watcher);
		map.remove(10);
		try {
			a.invoke(nms_watcher, 10, text);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static Class<?> getMCClass(String name) throws ClassNotFoundException {
		String version = Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3] + ".";
		String className = "net.minecraft.server." + version + name;
		return Class.forName(className);
	}

	private static Class<?> getCraftClass(String name) throws ClassNotFoundException {
		String version = Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3] + ".";
		String className = "org.bukkit.craftbukkit." + version + name;
		return Class.forName(className);
	}

	public static Location getPlayerLoc(Player p) {
		Location loc = p.getLocation();
		loc.setY(-85D);

		return loc;

	}

}

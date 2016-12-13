package net.infernalrealms.homesteads;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Level;

import org.apache.commons.io.FileUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import net.infernalrealms.general.InfernalRealms;
import net.infernalrealms.homesteads.exceptions.InvalidPlayerNameException;

public class HomesteadInstance implements Serializable {

	public static final Map<UUID, HomesteadInstance> HOMESTEAD_INSTANCES = new HashMap<>();

	private static final long serialVersionUID = -97298084086101632L;

	private transient World world;
	private UUID playerUUID;
	private String playerName;
	private HomesteadTier tier;

	private HomesteadInstance(Player player) {
		setPlayerUUID(player.getUniqueId());
		setPlayerName(player.getName());
		setTier(HomesteadTier.ONE);
		loadWorldThenVisit(player);
	}

	public void visit(Player player) {
		if (getWorld() == null) {
			loadWorldThenVisit();
			return;
		}

		// Run in task in case visit called async
		new BukkitRunnable() {
			@Override
			public void run() {
				if (getWorld() == null) {
					player.sendMessage(ChatColor.RED
							+ "Error visiting homestead because the world was not loaded. If this error persists, contact an admin.");
					return;
				}
				player.sendMessage(ChatColor.GREEN + "Entering " + getPlayerName() + "'s homestead.");
				player.teleport(getWorld().getSpawnLocation());
			}
		}.runTaskLater(InfernalRealms.getPlugin(), 0L);
	}

	private void loadWorldThenVisit(Player player) {
		try {
			new BukkitRunnable() {
				@Override
				public void run() {
					File homesteadFolder = getHomesteadFolder();
					if (!homesteadFolder.exists()) {
						// Create new world
						try {
							FileUtils.copyDirectory(new File(Bukkit.getWorldContainer(), "BaseHomestead"), homesteadFolder);
							new File(homesteadFolder, "uid.dat").delete();
						} catch (IOException e) {
							e.printStackTrace();
							return;
						}
					}
					try {
						setWorld(WorldCreator.name("homesteads/Homestead_" + getPlayerUUID().toString()).generator(new HomesteadGenerator())
								.createWorld());
					} catch (IllegalStateException e) {
						System.out.println(getWorld());
					}
					visit(player);
				}
			}.runTaskAsynchronously(InfernalRealms.getPlugin());
		} catch (Exception e) {
			System.out.println("An error occurred while creating the homestead.");
		}
	}

	public void loadWorldThenVisit() {
		loadWorldThenVisit(getPlayer());
	}

	public void save() {
		ObjectOutputStream out = null;
		try {
			out = new ObjectOutputStream(new FileOutputStream(new File(getHomesteadFolder(), "homestead.dat")));
			out.writeObject(this);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static void loadAndVisit(Player player) throws IOException {
		if (HomesteadUtils.isInOwnHomestead(player)) {
			player.sendMessage(ChatColor.RED + "You are already in your own homestead.");
			return;
		}
		HomesteadInstance homestead = HOMESTEAD_INSTANCES.get(player.getUniqueId());
		if (homestead != null) {
			homestead.visit(player);
			return;
		}
		homestead = HomesteadInstance.loadFromFile(player);
		HOMESTEAD_INSTANCES.put(player.getUniqueId(), homestead);
	}

	private static HomesteadInstance loadFromFile(Player player) throws IOException {
		File homesteadFolder = new File(Bukkit.getWorldContainer(), "homesteads/Homestead_" + player.getUniqueId().toString());
		if (!homesteadFolder.exists()) {
			return new HomesteadInstance(player);
		}
		ObjectInputStream in = null;
		try {
			in = new ObjectInputStream(new FileInputStream(new File(homesteadFolder, "homestead.dat")));
			HomesteadInstance homestead = (HomesteadInstance) in.readObject();
			homestead.visit(player);
			return homestead;
		} catch (FileNotFoundException e) {
			return new HomesteadInstance(player);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		throw new IOException();
	}

	public void unload() {
		InfernalRealms.getPlugin().getLogger().log(Level.INFO, "Unloading homestead world: " + getWorld().getName());
		save();
		Bukkit.getServer().unloadWorld(getWorld(), true);
		HOMESTEAD_INSTANCES.remove(getPlayerUUID());
	}

	public File getHomesteadFolder() {
		return new File(Bukkit.getWorldContainer(), "homesteads/Homestead_" + getPlayerUUID().toString());
	}

	public Player getPlayer() {
		Player player = Bukkit.getPlayer(getPlayerName());
		if (player == null) {
			throw new InvalidPlayerNameException();
		}
		return player;
	}

	public World getWorld() {
		return world;
	}

	public void setWorld(World world) {
		this.world = world;
	}

	public UUID getPlayerUUID() {
		return playerUUID;
	}

	public void setPlayerUUID(UUID playerUUID) {
		this.playerUUID = playerUUID;
	}

	public String getPlayerName() {
		return playerName;
	}

	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}

	public HomesteadTier getTier() {
		return tier;
	}

	public void setTier(HomesteadTier tier) {
		this.tier = tier;
	}

}

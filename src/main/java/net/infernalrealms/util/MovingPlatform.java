package net.infernalrealms.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import com.sk89q.worldedit.bukkit.selections.Selection;

import net.infernalrealms.cutscenes.WrappedLocation.SerializableLocation;
import net.infernalrealms.general.InfernalRealms;

public class MovingPlatform implements Serializable {

	private static final long serialVersionUID = -7317919505261496238L;
	private static final List<MovingPlatform> PLATFORMS = new ArrayList<MovingPlatform>();
	private static final String RECORDING = "MovingPlatform_RECORDING";

	private String name;
	private transient BukkitTask runner;
	private int tickRate;
	private List<SerializableLocation> locations = new ArrayList<>();
	private Platform platform;

	public MovingPlatform(String name, Platform platform) {
		this.name = name;
		this.platform = platform;
		this.tickRate = 20;
	}

	public void beginRecording(Player recorder) throws AlreadyRecordingException {
		if (isRecording(recorder)) {
			throw new AlreadyRecordingException("Player is already recording!");
		}
		recorder.setMetadata(RECORDING, new FixedMetadataValue(InfernalRealms.getPlugin(), this));
	}

	public void addLocation(Location location) {
		locations.add(new SerializableLocation(location));
	}

	public void run() {
		stop();
		this.runner = new BukkitRunnable() {
			private Iterator<SerializableLocation> itr = locations.iterator();

			@Override
			public void run() {
				if (!itr.hasNext()) {
					itr = locations.iterator();
				}
				Location platLoc = itr.next();
				platform.moveTo(platLoc);
			}
		}.runTaskTimer(InfernalRealms.getPlugin(), tickRate, tickRate);
	}

	public void stop() {
		if (this.runner != null) {
			this.runner.cancel();
		}
	}

	public static boolean isRecording(Player player) {
		return player.hasMetadata(RECORDING);
	}

	public static MovingPlatform getCurrentlyRecording(Player player) {
		return (MovingPlatform) player.getMetadata(RECORDING).get(0).value();
	}

	public void save() {
		ObjectOutputStream out = null;
		try {
			File parentDir = new File(InfernalRealms.getPlugin().getDataFolder(), "/MovingPlatforms");
			if (!parentDir.exists()) {
				parentDir.mkdirs();
			}
			out = new ObjectOutputStream(new FileOutputStream(new File(parentDir, "/" + this.name + ".imp")));
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

	public int getTickRate() {
		return tickRate;
	}

	public void setTickRate(int tickRate) {
		this.tickRate = tickRate;
	}

	public static void loadPlatforms() {
		ObjectInputStream in = null;
		try {
			File parentDir = new File(InfernalRealms.getPlugin().getDataFolder(), "/MovingPlatforms");
			if (!parentDir.exists()) {
				parentDir.mkdirs();
			}
			File[] children = parentDir.listFiles();
			for (File file : children) {
				if (hasMovingPlatformExtension(file)) {
					Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "Loading " + file.getName() + "...");
					try (ObjectInputStream in2 = new ObjectInputStream(new FileInputStream(file))) {
						MovingPlatform mp = (MovingPlatform) in2.readObject();
						mp.run();
						PLATFORMS.add(mp);
					}
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
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
	}

	public static void unloadPlatforms() {
		for (MovingPlatform mp : PLATFORMS) {
			mp.stop();
		}
		PLATFORMS.clear();
	}

	public static void refreshPlatforms() {
		unloadPlatforms();
		loadPlatforms();
	}

	private static boolean hasMovingPlatformExtension(File file) {
		int i = file.getName().lastIndexOf(".");
		if (i > 0) {
			return file.getName().substring(i + 1).equals("imp");
		}
		return false;
	}

	public static class PlatformCommands implements CommandExecutor {

		@Override
		public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args) {
			if (!sender.isOp()) {
				return false;
			}
			switch (commandLabel.toLowerCase()) {
			case "iplatform":
			case "imp":
				if (args.length == 0) {
					sendHelpMessage(sender);
					return false;
				}
				if (args[0].equalsIgnoreCase("record")) {
					if (args.length != 2) {
						sendHelpMessage(sender);
						return false;
					}
					if (!(sender instanceof Player)) {
						InfernalStrings.displayPlayerRequiredMessage(sender);
						return false;
					}
					Player player = (Player) sender;
					Selection selection = InfernalRealms.getWorldEditPlugin().getSelection(player);
					if (selection == null) {
						player.sendMessage(ChatColor.RED + "You must make a WorldEdit selection for your platform first!");
						return false;
					}
					MovingPlatform mp = new MovingPlatform(args[1], new Platform(selection.getMinimumPoint(), selection.getMaximumPoint()));
					try {
						mp.beginRecording(player);
					} catch (AlreadyRecordingException e) {
						player.sendMessage("You are already recording!");
						return false;
					}
					player.sendMessage(ChatColor.GREEN + "Recording has begun, right click the minimum point to record a new location.");
				} else if (args[0].equalsIgnoreCase("stop")) {
					if (args.length != 1) {
						sendHelpMessage(sender);
						return false;
					}
					if (!(sender instanceof Player)) {
						InfernalStrings.displayPlayerRequiredMessage(sender);
						return false;
					}
					Player player = (Player) sender;
					if (!isRecording(player)) {
						player.sendMessage(ChatColor.RED + "You are not currently recording!");
						return false;
					}
					MovingPlatform mp = getCurrentlyRecording(player);
					mp.save();
					player.removeMetadata(RECORDING, InfernalRealms.getPlugin());
					player.sendMessage(ChatColor.GREEN + "Stopped recording, saved to /$PluginFolder/MovingPlatforms/" + mp.name + ".imp/");
				} else if (args[0].equalsIgnoreCase("tickrate")) {
					if (args.length != 2) {
						sendHelpMessage(sender);
						return false;
					}
					if (!(sender instanceof Player)) {
						InfernalStrings.displayPlayerRequiredMessage(sender);
						return false;
					}
					Player player = (Player) sender;
					if (!isRecording(player)) {
						player.sendMessage(ChatColor.RED + "You are not currently recording!");
						return false;
					}
					int tickRate;
					try {
						tickRate = Integer.parseInt(args[1]);
					} catch (NumberFormatException e) {
						player.sendMessage(ChatColor.RED + "Unable to parse number!");
						return false;
					}
					MovingPlatform mp = getCurrentlyRecording(player);
					mp.setTickRate(tickRate);
					player.sendMessage(ChatColor.GREEN + "The tickrate has been set to " + tickRate + ".");
				} else if (args[0].equalsIgnoreCase("refresh")) {
					if (args.length != 1) {
						sendHelpMessage(sender);
						return false;
					}
					refreshPlatforms();
					sender.sendMessage(ChatColor.GREEN + "Platforms refreshed!");
				}
				break;
			}
			return false;
		}

		private void sendHelpMessage(CommandSender sender) {
			// @formatter:off
			sender.sendMessage(new String[] {
					ChatColor.AQUA + "" + ChatColor.BOLD + "Moving Platform Help",
					ChatColor.AQUA + "/imp - " + ChatColor.GRAY + "Displays help.",
					ChatColor.AQUA + "/imp record {name} - " + ChatColor.GRAY + "Begins recording platform for 'name'.",
					ChatColor.AQUA + "/imp stop - " + ChatColor.GRAY + "Stops recording.",
					ChatColor.AQUA + "/imp tickrate {amount} - " + ChatColor.GRAY + "Changes the tickrate of your recording to 'amount'.",
					ChatColor.AQUA + "/imp refresh - " + ChatColor.GRAY + "Refreshes all of the moving platforms.",
					});
			// @formatter:on
		}
	}

	public static class PlatformListener implements Listener {

		@EventHandler
		public void onPlayerInteract(PlayerInteractEvent event) {
			if (event.getAction() != Action.RIGHT_CLICK_BLOCK || !event.getPlayer().isOp() || !isRecording(event.getPlayer())) {
				return;
			}
			MovingPlatform mp = getCurrentlyRecording(event.getPlayer());
			mp.addLocation(event.getClickedBlock().getLocation());
			event.getPlayer().sendMessage(ChatColor.GREEN + "Added location.");
		}

	}

}

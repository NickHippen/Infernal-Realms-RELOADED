package net.infernalrealms.cutscenes;

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

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import net.infernalrealms.cutscenes.WrappedLocation.SerializableLocation;
import net.infernalrealms.general.InfernalRealms;

public class Cutscene implements Serializable {

	private static final long serialVersionUID = 994558944335375048L;

	private transient Player recorder;
	private String name;
	private long ticks;
	private List<SerializableLocation> locations = new ArrayList<>();

	public Cutscene(Player recorder, String name, int duration) {
		this.recorder = recorder;
		this.name = name;
		this.ticks = duration * 20;
	}

	public void recordScene() {
		if (recorder == null) {
			throw new NullPointerException("Recorder is null!");
		}
		recorder.sendMessage("Beginning recording...");
		new BukkitRunnable() {
			int count = 0; // Temp

			@Override
			public void run() {
				if (count >= ticks) {
					recorder.sendMessage("Stopped recording and saved as " + name + ".ics.");
					save();
					cancel();
					return;
				}
				locations.add(new SerializableLocation(recorder.getLocation()));
				count++;
			}
		}.runTaskTimer(InfernalRealms.getPlugin(), 0L, 1L);
	}

	public void playScene(Player player) {
		player.sendMessage("Playing scene...");
		Location beforeLoc = player.getLocation();
		new BukkitRunnable() {
			Iterator<SerializableLocation> itr = locations.iterator();

			@Override
			public void run() {
				if (!itr.hasNext()) {
					player.sendMessage("Scene ended.");
					player.teleport(beforeLoc);
					cancel();
					return;
				}
				player.teleport(itr.next());
			}
		}.runTaskTimer(InfernalRealms.getPlugin(), 0L, 1L);
	}

	public void save() {
		ObjectOutputStream out = null;
		try {
			File parentDir = new File(InfernalRealms.getPlugin().getDataFolder(), "/Cutscenes");
			if (!parentDir.exists()) {
				parentDir.mkdirs();
			}
			out = new ObjectOutputStream(new FileOutputStream(new File(parentDir, "/" + this.name + ".ics")));
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

	public static Cutscene loadScene(String name) throws IOException {
		ObjectInputStream in = null;
		try {
			File parentDir = new File(InfernalRealms.getPlugin().getDataFolder(), "/Cutscenes");
			if (!parentDir.exists()) {
				parentDir.mkdirs();
			}
			in = new ObjectInputStream(new FileInputStream(new File(parentDir, "/" + name + ".ics")));
			return (Cutscene) in.readObject();
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
		throw new IOException();
	}

}

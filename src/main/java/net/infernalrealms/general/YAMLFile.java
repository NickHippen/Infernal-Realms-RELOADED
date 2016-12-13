package net.infernalrealms.general;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public enum YAMLFile {

	// @formatter:off
	TIERS("tiers"),
	ITEMS("items"),
	GEMS("gems"),
	POTIONS("potions"),
	MOBS("mobs"),
	SPAWNS("spawns"),
	MISC_ITEMS("miscItems"),
	DUNGEON_SPAWNS("dungeonSpawns"),
	WEAPON_MODIFIERS("weaponModifiers"),
	ARMOR_MODIFIERS("armorModifiers"),
	MODIFIER_GROUPS("modifierGroups"),
	MULTIPLIERS("multipliers"),
	CUSTOM_BLOCKS("customblocks"),
	SHOPS("shops"),
	ANNOUNCEMENTS("announcements"),
	BLACKSMITHING_RECIPES("blacksmithingRecipes"),
	DUNGEON_LEADERBOARDS("dungeon-leaderboards"),
	BLACKSMTIHING_LOCATIONS("blacksmithingLocations");
	;
	// @formatter:on

	private final String fileName;
	private File file;
	private FileConfiguration config;

	private YAMLFile(String name) {
		this.fileName = name + ".yml";
	}

	public String getFileName() {
		return fileName;
	}

	public File getFile() {
		return file;
	}

	public FileConfiguration getConfig() {
		return config;
	}

	public synchronized void save() {
		try {
			config.save(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void makeFiles() {
		for (YAMLFile yaml : values()) {
			yaml.file = new File(InfernalRealms.getPlugin().getDataFolder(), yaml.getFileName());
		}
	}

	public static void firstRun() throws Exception {
		for (YAMLFile yaml : values()) {
			if (!yaml.getFile().exists()) {
				yaml.getFile().getParentFile().mkdirs();
				copy(InfernalRealms.getPlugin().getResource(yaml.getFileName()), yaml.getFile());
			}
		}
	}

	private static void copy(InputStream in, File file) {
		try {
			OutputStream out = new FileOutputStream(file);
			byte[] buf = new byte[1024];
			int len;
			while ((len = in.read(buf)) > 0) {
				out.write(buf, 0, len);
			}
			out.close();
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void loadFiles() {
		for (YAMLFile yaml : values()) {
			try {
				yaml.getConfig().load(yaml.getFile());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@SuppressWarnings("deprecation")
	public static void reloadConfigs() {
		for (YAMLFile yaml : values()) {
			if (yaml.getFile() == null) {
				yaml.file = new File(InfernalRealms.getPlugin().getDataFolder(), yaml.getFileName());
			}
			yaml.config = YamlConfiguration.loadConfiguration(yaml.getFile());

			// Look for defaults in the jar
			InputStream defConfigStream = InfernalRealms.getPlugin().getResource(yaml.getFileName());
			if (defConfigStream != null) {
				YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
				yaml.getConfig().setDefaults(defConfig);
			}
		}
	}

	public static void prepareFiles() {
		for (YAMLFile yaml : values()) {
			yaml.file = new File(InfernalRealms.getPlugin().getDataFolder(), yaml.fileName);
			yaml.config = new YamlConfiguration();
		}

		try {
			firstRun();
		} catch (Exception e) {
			e.printStackTrace();
		}
		loadFiles();
	}

}

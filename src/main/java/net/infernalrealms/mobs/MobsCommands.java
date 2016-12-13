package net.infernalrealms.mobs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

import net.infernalrealms.dungeons.Dungeon;
import net.infernalrealms.dungeons2.DungeonType;
import net.infernalrealms.general.YAMLFile;
import net.infernalrealms.util.GeneralUtil;

public class MobsCommands implements CommandExecutor, Listener {

	private static Map<String, List<SpawnBinding>> spawnBindings = new HashMap<>(0);

	@Override
	public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args) {
		if (sender instanceof Player) {
			Player player = (Player) sender;
			if (player.isOp()) {
				if (commandLabel.equalsIgnoreCase("infernalmobs") || commandLabel.equalsIgnoreCase("im")) {
					if (args.length == 0) {
						displayHelp(player);
					} else if (args[0].equalsIgnoreCase("spawn")) { // /im spawn <Mob[Min-Max]>
						args[1] = args[1].replaceAll("_", " ");
						int mobID;
						try {
							mobID = Integer.parseInt(args[1]);
						} catch (Exception e) {
							player.sendMessage("Expected number");
							return false;
						}
						if (FileMob.isFileMob(mobID)) {
							FileMob.spawn(mobID, player.getLocation());
							player.sendMessage("Spawned " + mobID);
						} else {
							player.sendMessage("Mob not found! (" + mobID + ")");
						}
					} else if (args[0].equalsIgnoreCase("setspawn")) { // /im setspawn <SpawnName> <Mob>
						if (args.length >= 3) {
							String mobName = GeneralUtil.combineArgsToString(Arrays.copyOfRange(args, 2, args.length));
							if (!FileMob.isFileMob(mobName)) {
								player.sendMessage(ChatColor.RED + "That is not a valid mob.");
								return false;
							}
							SpawnManager.setSpawn(args[1], player.getWorld(), player.getLocation(), mobName);
							SpawnManager.refreshSpawn(args[1]);
							player.sendMessage("Spawn set!");
						}
					} else if (args[0].equalsIgnoreCase("editspawn")) {
						if (args[1].equalsIgnoreCase("mob")) { // /im editspawn mob <SpawnName> <Mobs>
							if (SpawnManager.containsSpawn(args[2])) {
								args[2] = args[2].replaceAll("_", " ");
								String mobName = GeneralUtil.combineArgsToString(Arrays.copyOfRange(args, 3, args.length));
								if (YAMLFile.MOBS.getConfig().contains(mobName)) {
									YAMLFile.SPAWNS.getConfig().set(args[2] + ".Mobs", mobName);
									player.sendMessage("Mob for " + args[2] + " set to " + mobName);
								} else {
									player.sendMessage("Invalid mob.");
								}
							} else {
								player.sendMessage("Invalid spawn.");
							}
						} else if (args[1].equalsIgnoreCase("despawnrange")) { // /im editspawn despawnrange <SpawnName> <Range>
							if (SpawnManager.containsSpawn(args[2])) {
								YAMLFile.SPAWNS.getConfig().set(args[2] + ".DespawnRange", Integer.parseInt(args[3]));
								player.sendMessage("Despawn range set.");
							} else {
								player.sendMessage("Invalid spawn.");
							}
						} else if (args[1].equalsIgnoreCase("maxspawned")) {
							if (SpawnManager.containsSpawn(args[2])) {
								YAMLFile.SPAWNS.getConfig().set(args[2] + ".MaxSpawned", Integer.parseInt(args[3]));
								System.out.println("Max spawned set.");
							} else {
								player.sendMessage("Invalid spawn.");
							}
						} else if (args[1].equalsIgnoreCase("respawnrate")) {
							if (SpawnManager.containsSpawn(args[2])) {
								YAMLFile.SPAWNS.getConfig().set(args[2] + ".RespawnRate", Integer.parseInt(args[3]));
								player.sendMessage("Respawn rate set.");
							} else {
								player.sendMessage("Invalid spawn.");
							}
						}
						YAMLFile.SPAWNS.save();
					} else if (args[0].equalsIgnoreCase("butcher")) {
						if (args.length == 1) {
							MobManager.butcherMobs();
							player.sendMessage("Butchered!");
						}
					} else if (args[0].equalsIgnoreCase("refreshspawn")) {
						SpawnManager.refreshSpawn(args[1]);
						player.sendMessage("Spawn " + args[1] + " refreshed!");
					} else if (args[0].equalsIgnoreCase("refreshspawns")) {
						SpawnManager.refreshSpawns();
						player.sendMessage("Spawns refreshed!");
					} else if (args[0].equalsIgnoreCase("help")) {
						displayHelp(player);
					} else if (args[0].equalsIgnoreCase("copyspawn")) {
						if (SpawnManager.containsSpawn(args[1])) {
							SpawnManager.setSpawn(args[2], player.getWorld(), player.getLocation(),
									YAMLFile.SPAWNS.getConfig().getString(args[1] + ".Mobs"));
							YAMLFile.SPAWNS.getConfig().set(args[2] + ".DespawnRange",
									YAMLFile.SPAWNS.getConfig().getInt(args[1] + ".DespawnRange"));
							YAMLFile.SPAWNS.getConfig().set(args[2] + ".MaxSpawned",
									YAMLFile.SPAWNS.getConfig().getInt(args[1] + ".MaxSpawned"));
							YAMLFile.SPAWNS.getConfig().set(args[2] + ".RespawnRate",
									YAMLFile.SPAWNS.getConfig().getInt(args[1] + ".RespawnRate"));
							if (YAMLFile.SPAWNS.getConfig().contains(args[1] + ".Dungeon")) {
								YAMLFile.SPAWNS.getConfig().set(args[2] + ".Dungeon", true);
							}
							YAMLFile.SPAWNS.save();
							player.sendMessage("Spawn set!");
						}
					} else if (args[0].equalsIgnoreCase("bindspawn")) { // /im bindspawn <BaseSpawnName> <MobID>
						if (player.getItemInHand() == null || player.getItemInHand().getType() == Material.AIR) {
							player.sendMessage("Cannot bind to air.");
							return false;
						}
						List<SpawnBinding> bindings = spawnBindings.get(player.getName());
						if (bindings == null) {
							bindings = new ArrayList<>();
						} else {
							SpawnBinding.clearBindingForMaterial(bindings, player.getItemInHand().getType());
						}
						bindings.add(new SpawnBinding(player.getItemInHand().getType(), args[1], Integer.parseInt(args[2])));
						spawnBindings.put(player.getName(), bindings);
						player.sendMessage("Bound base spawn " + args[1] + " to " + player.getItemInHand().getType());
					} else if (args[0].equalsIgnoreCase("clearbindings")) { // /im clearbindings
						List<SpawnBinding> bindings = spawnBindings.get(player.getName());
						if (bindings == null || bindings.isEmpty()) {
							player.sendMessage("No bindings to clear.");
							return false;
						}
						bindings.clear();
						player.sendMessage("Bindings cleared.");
					} else if (args[0].equalsIgnoreCase("dungeon")) {
						if (args[1].equalsIgnoreCase("setspawn")) { // /im dungeon setspawn <Dungeon Name> <Mob Name>
							if (args.length < 4) {
								displayHelp(player);
								return false;
							}
							String mobName = GeneralUtil.combineArgsToString(Arrays.copyOfRange(args, 3, args.length));
							String dungeonName = args[2].replaceAll("_", " ");
							if (!player.getWorld().equals(Dungeon.INSTANCES_WORLD)) {
								player.sendMessage(ChatColor.RED + "You cannot set dungeon spawns in this world.");
								return false;
							}
							if (!FileMob.isFileMob(mobName)) {
								player.sendMessage(ChatColor.RED + "That is not a valid mob.");
								return false;
							}
							if (DungeonType.fromDisplayName(dungeonName) == null) {
								player.sendMessage(ChatColor.RED + "That is not a valid dungeon.");
								return false;
							}
							SpawnManager.setDungeonSpawn(dungeonName, player.getLocation(), mobName);
							player.sendMessage(ChatColor.GREEN + "Dungeon spawn set successfully.");
							return true;
						} else if (args[1].equalsIgnoreCase("marknearest")) { // /im dungeon marknearest <Dungeon Name>
							if (args.length < 3) {
								displayHelp(player);
								return false;
							}
							String dungeonName = args[2].replaceAll("_", " ");
							if (!player.getWorld().equals(Dungeon.INSTANCES_WORLD)) {
								player.sendMessage(ChatColor.RED + "Dungeon spawns are not in this world.");
								return false;
							}
							if (DungeonType.fromDisplayName(dungeonName) == null) {
								player.sendMessage(ChatColor.RED + "That is not a valid dungeon.");
								return false;
							}
							// TODO
						} else if (args[1].equalsIgnoreCase("settimerspawn")) { // /im dungeon settimerspawn <Spawn Name> <Mob Name>
							if (args.length < 4) {
								displayHelp(player);
								return false;
							}
							if (!player.getWorld().equals(Dungeon.INSTANCES_WORLD)) {
								player.sendMessage(ChatColor.RED + "You cannot set dungeon spawns in this world.");
								return false;
							}
							String mobName = GeneralUtil.combineArgsToString(Arrays.copyOfRange(args, 3, args.length));
							if (!FileMob.isFileMob(mobName)) {
								player.sendMessage(ChatColor.RED + "That is not a valid mob.");
								return false;
							}
							SpawnManager.setSpawn(args[2], player.getWorld(), player.getLocation(), mobName);
							YAMLFile.SPAWNS.getConfig().set(args[2] + ".Dungeon", true);
							YAMLFile.SPAWNS.save();
							player.sendMessage("Dungeon Spawn set!");
							SpawnManager.refreshSpawn(args[2]);
						}
					} else if (args[0].equalsIgnoreCase("reload")) {
						YAMLFile.reloadConfigs();
						player.sendMessage("Reloaded.");
					}
				} else {
					player.sendMessage(ChatColor.RED + "Insufficient permissions!");
				}
			}
		}
		return false;
	}

	private static void displayHelp(Player p) {
		p.sendMessage("------------Regular------------");
		p.sendMessage("/im spawn §b<Mob[min-max]>");
		p.sendMessage("/im setspawn §b<SpawnName> <Mob>");
		p.sendMessage("/im bindspawn §b<BaseSpawnName> <MobID>");
		p.sendMessage("/im clearbindings");
		p.sendMessage("/im copyspawn §b<OldSpawnName> <NewSpawnName>");
		p.sendMessage("/im editspawn mob §b<SpawnName> <Mob1,Mob2,etc.>");
		p.sendMessage("/im editspawn despawnrange §b<SpawnName> <Range>");
		p.sendMessage("/im editspawn maxspawned §b<SpawnName> <Max>");
		p.sendMessage("/im editspawn respawnrate §b<SpawnName> <Rate>");
		p.sendMessage("/im butcher");
		p.sendMessage("/im refreshspawn §b<SpawnName>");
		p.sendMessage("/im refreshspawns");
		p.sendMessage("------------Dungeon------------");
		p.sendMessage("/im dungeon setspawn §b<DungeonName> <MobName>");
		p.sendMessage("/im dungeon marknearest §b<Dungeon Name>");
		p.sendMessage("/im dungeon settimerspawn §b<Spawn Name> <Mob Name>");
	}

	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event) {
		if (!event.getPlayer().isOp()) {
			return;
		}

		// OP only
		List<SpawnBinding> bindings = spawnBindings.get(event.getPlayer().getName());
		if (bindings == null) {
			// No bindings
			return;
		}
		SpawnBinding binding = SpawnBinding.getBindingForMaterial(bindings, event.getPlayer().getItemInHand().getType());
		if (binding == null) {
			// Not correct binding material
			return;
		}
		if (!FileMob.isFileMob(binding.getMobID())) {
			event.getPlayer().sendMessage(ChatColor.RED + "That is not a valid mob.");
			return;
		}
		Location targetLocation = event.getPlayer().getTargetBlock((Set<Material>) null, 100).getLocation();
		SpawnManager.setSpawn(binding.getFullSpawnName(), event.getPlayer().getWorld(), targetLocation.add(0, 1, 0),
				"" + binding.getMobID());
		SpawnManager.refreshSpawn(binding.getFullSpawnName());
		event.getPlayer().sendMessage("Spawn set for " + binding.getFullSpawnName());
		binding.nextSpawn();
	}

}

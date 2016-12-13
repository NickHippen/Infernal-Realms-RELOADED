package net.infernalrealms.npc;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.api.trait.Trait;
import net.citizensnpcs.npc.CitizensNPCRegistry;
import net.infernalrealms.dungeons2.DungeonType;
import net.infernalrealms.util.GeneralUtil;

public class NPCCommands implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args) {
		if (sender instanceof Player) {
			Player player = (Player) sender;
			if (commandLabel.equalsIgnoreCase("inpc")) {
				if (args.length == 0 || args[0].equalsIgnoreCase("help")) {
					sendHelpMessages(player);
				} else {
					if (args[0].equalsIgnoreCase("create")) {
						if (args[1].equalsIgnoreCase("questgiver")) { // /inpc create questgiver [NPC Name] [Quest ID]
							if (args.length == 4) {
								try {
									TraitQuestGiver trait = new TraitQuestGiver();
									trait.setQuestID(Integer.parseInt(args[3]));
									spawnCustomNPC(player.getLocation(), args[2].replaceAll("_", " "), trait);
								} catch (NumberFormatException e) {
									player.sendMessage("Expected number.");
								}
							} else {
								sendHelpMessages(player);
							}
						} else if (args[1].equalsIgnoreCase("dungeonkeeper")) { // /inpc create dungeonkeeper [NPC Name] [Dungeon Type]
							if (args.length >= 4) {
								TraitDungeonKeeper trait = new TraitDungeonKeeper();
								DungeonType dt = DungeonType
										.fromDisplayName(GeneralUtil.combineArgsToString(Arrays.copyOfRange(args, 3, args.length)));
								if (dt == null) {
									player.sendMessage("Invalid dungeon type.");
									return false;
								}
								trait.setDungeonType(dt);
								spawnCustomNPC(player.getLocation(), args[2].replaceAll("_", " "), trait);
								player.sendMessage("Created NPC " + args[2].replaceAll("_", " ") + " with dungeon keeper trait.");
							} else {
								sendHelpMessages(player);
							}
						} else if (args[1].equalsIgnoreCase("mountshop")) { // /inpc create mountshop [NPC Name]
							if (args.length != 3) {
								sendHelpMessages(player);
								return false;
							}
							TraitMountShop trait = new TraitMountShop();
							spawnCustomNPC(player.getLocation(), args[2].replaceAll("_", " "), trait);
							player.sendMessage("Created NPC " + args[2].replaceAll("_", " ") + " with mount shop trait.");
						} else if (args[1].equalsIgnoreCase("banker")) { // /inpc create banker [NPC Name]
							if (args.length != 3) {
								sendHelpMessages(player);
								return false;
							}
							TraitBanker trait = new TraitBanker();
							spawnCustomNPC(player.getLocation(), args[2].replaceAll("_", " "), trait);
							player.sendMessage("Created NPC " + args[2].replaceAll("_", " ") + " with banker trait.");
						} else if (args[1].equalsIgnoreCase("shopkeeper")) {
							if (args.length != 4) {
								sendHelpMessages(player);
								return false;
							}
							TraitShopkeeper trait = new TraitShopkeeper();
							trait.setShop(args[3].replaceAll("_", " "));
							spawnCustomNPC(player.getLocation(), args[2].replaceAll("_", " "), trait);
							player.sendMessage("Created NPC " + args[2].replaceAll("_", " ") + " with shopkeeper trait.");
						}
					}
				}
			}
		}
		return false;
	}

	public static void sendHelpMessages(Player player) {
		player.sendMessage("-----------Infernal NPC Commands-----------");
		player.sendMessage("[Note] NPC names with spaces should use '_' instead.");
		player.sendMessage("/inpc create questgiver [NPC Name] [Quest ID]");
		player.sendMessage("/inpc create dungeonkeeper [NPC Name] [Dungeon Type]");
		player.sendMessage("/inpc create mountshop [NPC Name]");
		player.sendMessage("/inpc create banker [NPC Name]");
		player.sendMessage("/inpc create shopkeeper [NPC Name] [Shop Name]");
	}

	public static void spawnCustomNPC(Location location, String name, Trait... traits) {
		if (CitizensAPI.getNPCRegistry() instanceof CitizensNPCRegistry) {
			CitizensNPCRegistry registry = (CitizensNPCRegistry) CitizensAPI.getNPCRegistry();
			try {
				Method generateUniqueId = registry.getClass().getDeclaredMethod("generateUniqueId");
				generateUniqueId.setAccessible(true);
				int uniqueId = (int) generateUniqueId.invoke(registry);
				NPC npc = registry.createNPC(EntityType.PLAYER, UUID.randomUUID(), uniqueId, name);
				for (Trait trait : traits) {
					npc.addTrait(trait);
				}
				npc.spawn(location);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}

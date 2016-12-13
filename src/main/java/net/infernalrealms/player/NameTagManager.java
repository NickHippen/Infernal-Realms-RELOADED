package net.infernalrealms.player;

public class NameTagManager {

	//	public static Scoreboard GENERAL_SCOREBOARD;
	//
	//	public static void setupNametags() {
	//		GENERAL_SCOREBOARD = InfernalRealms.manager.getMainScoreboard();
	//	}
	//
	//	public static Team applyNameTag(Player player) {
	//		//		PlayerData playerData = PlayerData.getData(player);
	//		//		PlayerClass playerClass = PlayerClass.fromString(playerData.getPlayerClass());
	//		//		Team team;
	//		//		try {
	//		//			team = GENERAL_SCOREBOARD.registerNewTeam(player.getUniqueId().toString().substring(0, 16));
	//		//		} catch (Exception e) {
	//		//			e.printStackTrace();
	//		//			team = GENERAL_SCOREBOARD.registerNewTeam("R" + new Random().nextInt(Integer.MAX_VALUE));
	//		//		}
	//		//		team.setPrefix(ChatColor.GRAY + "" + ChatColor.BOLD + "[" + ChatColor.GRAY + playerData.getLevel() + ChatColor.BOLD + "]"
	//		//				+ playerClass.getColor());
	//		//		team.setSuffix(playerClass.getSuffixString());
	//
	//		PlayerData playerData = PlayerData.getData(player);
	//		PlayerClass playerClass = PlayerClass.fromString(playerData.getPlayerClass());
	//		Team team = GENERAL_SCOREBOARD.getPlayerTeam(player);
	//		if (team == null) {
	//			try {
	//				team = GENERAL_SCOREBOARD.registerNewTeam(player.getUniqueId().toString().substring(0, 16));
	//			} catch (Exception e) {
	//				team = GENERAL_SCOREBOARD.registerNewTeam("R" + new Random().nextInt(Integer.MAX_VALUE));
	//			}
	//		}
	//		team.setPrefix(ChatColor.GRAY + "" + ChatColor.BOLD + "[" + ChatColor.GRAY + playerData.getLevel() + ChatColor.BOLD + "]  "
	//				+ playerClass.getColor());
	//		team.setSuffix("  " + playerClass.getSuffixString());
	//		team.addPlayer(player);
	//		player.setScoreboard(GENERAL_SCOREBOARD);
	//		return team;
	//	}

}

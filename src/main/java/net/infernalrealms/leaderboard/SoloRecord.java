package net.infernalrealms.leaderboard;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

public class SoloRecord<T extends Scoreable> extends Record<T> {

	private String playerName;

	public SoloRecord(String playerName, T score) {
		super(score);
		this.setPlayerName(playerName);
	}

	public SoloRecord(Map<String, Object> data) {
		super(data);
		this.playerName = (String) data.get("playerName");
	}

	@Override
	public ItemStack generateIcon(int placement) {
		ItemStack icon = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
		SkullMeta iconMeta = (SkullMeta) icon.getItemMeta();
		iconMeta.setOwner(getPlayerName());
		iconMeta.setDisplayName(ChatColor.DARK_PURPLE + "#" + placement + " - " + ChatColor.GRAY + ChatColor.UNDERLINE + getScore());
		List<String> iconLore = new ArrayList<>();
		iconLore.add(ChatColor.GRAY + "" + ChatColor.ITALIC + getPlayerName());
		iconMeta.setLore(iconLore);
		icon.setItemMeta(iconMeta);

		return icon;
	}

	@Override
	public boolean hasEqualParticipants(Record<T> record) {
		if (!(record instanceof SoloRecord)) {
			return false;
		}
		return this.getPlayerName().equals(((SoloRecord<T>) record).getPlayerName());
	}

	public String getPlayerName() {
		return playerName;
	}

	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}

	@Override
	public Map<String, Object> serialize() {
		Map<String, Object> data = super.serialize();
		data.put("playerName", this.playerName);
		return data;
	}

	@Override
	public String toString() {
		return "SoloRecord [playerName=" + playerName + ", super.toString()=" + super.toString() + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((playerName == null) ? 0 : playerName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SoloRecord other = (SoloRecord) obj;
		if (playerName == null) {
			if (other.playerName != null)
				return false;
		} else if (!playerName.equals(other.playerName))
			return false;
		return super.equals(obj);
	}

}
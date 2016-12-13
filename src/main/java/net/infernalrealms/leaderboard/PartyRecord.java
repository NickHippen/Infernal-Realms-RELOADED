package net.infernalrealms.leaderboard;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import net.infernalrealms.party.Party;

public class PartyRecord<T extends Scoreable> extends Record<T> {

	private String leaderName;
	private List<String> memberNames;

	public PartyRecord(Map<String, Object> data) {
		super(data);
		this.memberNames = (List<String>) data.get("memberNames");
	}

	public PartyRecord(Party party, T score) {
		super(score);
		this.setLeaderName(party.getOwner());
		List<String> memberNames = new ArrayList<>();
		for (Player member : party.getAllOnlineMembers()) {
			memberNames.add(member.getName());
		}
		this.setMemberNames(memberNames);
	}

	@Override
	public ItemStack generateIcon(int placement) {
		ItemStack icon = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
		SkullMeta iconMeta = (SkullMeta) icon.getItemMeta();
		iconMeta.setOwner(getLeaderName());
		iconMeta.setDisplayName(ChatColor.DARK_PURPLE + "#" + placement + " - " + ChatColor.GRAY + ChatColor.UNDERLINE + getScore());
		List<String> iconLore = new ArrayList<>();
		for (String member : getMemberNames()) {
			iconLore.add(ChatColor.GRAY + "" + ChatColor.ITALIC + member);
		}
		iconMeta.setLore(iconLore);
		icon.setItemMeta(iconMeta);

		return icon;
	}

	@Override
	public boolean hasEqualParticipants(Record<T> record) {
		if (!(record instanceof PartyRecord)) {
			return false;
		}
		PartyRecord<T> partyRecord = ((PartyRecord<T>) record);
		Collections.sort(this.getMemberNames());
		Collections.sort(partyRecord.getMemberNames());

		return this.getMemberNames().equals(((PartyRecord<T>) record).getMemberNames());
	}

	public String getLeaderName() {
		return leaderName;
	}

	public void setLeaderName(String leaderName) {
		this.leaderName = leaderName;
	}

	public List<String> getMemberNames() {
		return memberNames;
	}

	public void setMemberNames(List<String> memberNames) {
		this.memberNames = memberNames;
	}

	@Override
	public Map<String, Object> serialize() {
		Map<String, Object> data = super.serialize();
		data.put("memberName", this.memberNames);
		return data;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((leaderName == null) ? 0 : leaderName.hashCode());
		result = prime * result + ((memberNames == null) ? 0 : memberNames.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		PartyRecord other = (PartyRecord) obj;
		if (leaderName == null) {
			if (other.leaderName != null)
				return false;
		} else if (!leaderName.equals(other.leaderName))
			return false;
		if (memberNames == null) {
			if (other.memberNames != null)
				return false;
		} else if (!memberNames.equals(other.memberNames))
			return false;
		return super.equals(obj);
	}

}
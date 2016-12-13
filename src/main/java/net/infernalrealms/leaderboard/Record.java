package net.infernalrealms.leaderboard;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.inventory.ItemStack;

public abstract class Record<T extends Scoreable> implements ConfigurationSerializable, Comparable<Record<T>> {

	private T score;

	@SuppressWarnings("unchecked")
	protected Record(Map<String, Object> data) {
		this.score = (T) data.get("score");
	}

	protected Record(T score) {
		this.setScore(score);
	}

	public T getScore() {
		return score;
	}

	public void setScore(T score) {
		this.score = score;
	}

	public abstract boolean hasEqualParticipants(Record<T> record);

	public abstract ItemStack generateIcon(int placement);

	@Override
	public Map<String, Object> serialize() {
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("score", this.score);
		return data;
	}

	@Override
	public int compareTo(Record<T> record) {
		return getScore().compareTo(record.getScore());
	}

	@Override
	public String toString() {
		return "Record [score=" + score + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((score == null) ? 0 : score.hashCode());
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
		Record other = (Record) obj;
		if (score == null) {
			if (other.score != null)
				return false;
		} else if (!score.equals(other.score))
			return false;
		return true;
	}

	public static <T extends Scoreable> void insertRecordSorted(List<Record<T>> list, Record<T> element) {
		for (int i = 0; i < list.size(); i++) {
			Record<T> curr = list.get(i);
			if (curr.compareTo(element) <= 0) {
				if (curr.hasEqualParticipants(element)) {
					// Time is worse than current participant's best
					return;
				}
				continue;
			}
			list.add(i, element);
			// Search if this was a new best for the person
			for (int j = i + 1; j < list.size(); j++) {
				Record<T> curr2 = list.get(j);
				if (curr2.hasEqualParticipants(element)) {
					list.remove(j);
					break;
				}
			}
			return;
		}
		list.add(element);
	}

	public static enum RecordType {
		SOLO("Solo"), PARTY("Party");

		private String path;

		private RecordType(String path) {
			this.path = path;
		}

		public String getPath() {
			return path;
		}

	}

}

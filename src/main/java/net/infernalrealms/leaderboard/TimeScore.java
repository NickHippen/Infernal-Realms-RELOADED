package net.infernalrealms.leaderboard;

import java.security.InvalidParameterException;
import java.util.HashMap;
import java.util.Map;

public class TimeScore implements Scoreable {

	private int totalSeconds;

	public TimeScore(Map<String, Object> data) {
		this.totalSeconds = (int) data.get("totalSeconds");
	}

	public TimeScore() {
		this(0);
	}

	public TimeScore(int totalSeconds) {
		this.setTotalSeconds(totalSeconds);
	}

	public int getHours() {
		return totalSeconds / 3600;
	}

	public int getMinutes() {
		return (totalSeconds - (getHours() * 3600)) / 60;
	}

	public int getSeconds() {
		return totalSeconds - (getHours() * 3600) - (getMinutes() * 60);
	}

	public int getTotalSeconds() {
		return this.totalSeconds;
	}

	public void setTotalSeconds(int totalSeconds) {
		this.totalSeconds = totalSeconds;
	}

	@Override
	public Map<String, Object> serialize() {
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("totalSeconds", this.totalSeconds);

		return data;
	}

	@Override
	public int compareTo(Scoreable score) {
		if (!(score instanceof TimeScore)) {
			throw new InvalidParameterException("Comparisons must be of the same type!");
		}

		int thisSeconds = this.getTotalSeconds();
		int scoreSeconds = ((TimeScore) score).getTotalSeconds();
		if (thisSeconds == scoreSeconds) {
			return 0;
		}
		return thisSeconds < scoreSeconds ? -1 : 1;
	}

	@Override
	public String toString() {
		return String.format("%02d:%02d:%02d", getHours(), getMinutes(), getSeconds());
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + totalSeconds;
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
		TimeScore other = (TimeScore) obj;
		if (totalSeconds != other.totalSeconds)
			return false;
		return true;
	}

}

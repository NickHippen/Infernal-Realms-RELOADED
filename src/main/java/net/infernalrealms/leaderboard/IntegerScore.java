package net.infernalrealms.leaderboard;

import java.security.InvalidParameterException;
import java.util.HashMap;
import java.util.Map;

public class IntegerScore implements Scoreable {

	private int score;

	public IntegerScore(Map<String, Object> data) {
		this.score = (int) data.get("score");
	}

	public IntegerScore() {
		this(0);
	}

	public IntegerScore(int score) {
		this.setScore(score);
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	@Override
	public Map<String, Object> serialize() {
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("score", this.score);
		return data;
	}

	@Override
	public int compareTo(Scoreable score) {
		if (!(score instanceof IntegerScore)) {
			throw new InvalidParameterException("Comparisons must be of the same type!");
		}

		IntegerScore intScore = (IntegerScore) score;
		if (this.getScore() == intScore.getScore()) {
			return 0;
		}
		return this.getScore() < intScore.getScore() ? -1 : 1;
	}

	@Override
	public String toString() {
		return "" + score;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + score;
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
		IntegerScore other = (IntegerScore) obj;
		if (score != other.score)
			return false;
		return true;
	}

}

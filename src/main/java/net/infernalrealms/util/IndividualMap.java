package net.infernalrealms.util;

public class IndividualMap<T, K> {

	private T firstValue;
	private K secondValue;

	public IndividualMap(T firstValue, K secondValue) {
		this.setFirstValue(firstValue);
		this.setSecondValue(secondValue);
	}

	public IndividualMap() {}

	public T getFirstValue() {
		return firstValue;
	}

	public void setFirstValue(T firstValue) {
		this.firstValue = firstValue;
	}

	public K getSecondValue() {
		return secondValue;
	}

	public void setSecondValue(K secondValue) {
		this.secondValue = secondValue;
	}

}

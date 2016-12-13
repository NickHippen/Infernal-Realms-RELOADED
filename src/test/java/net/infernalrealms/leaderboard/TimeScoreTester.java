package net.infernalrealms.leaderboard;

import static org.junit.Assert.assertEquals;

import java.security.InvalidParameterException;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class TimeScoreTester {

	@Rule
	public final ExpectedException thrown = ExpectedException.none();

	private TimeScore time;
	private TimeScore time2;

	@Before
	public void init() {
		time = new TimeScore(15650);
		time2 = new TimeScore(1200);
	}

	@Test
	public void testTotalSeconds() throws Exception {
		assertEquals(time.getTotalSeconds(), 15650);
	}

	@Test
	public void testTimeHours() throws Exception {
		assertEquals(time.getHours(), 4);
	}

	@Test
	public void testTimeMinutes() throws Exception {
		assertEquals(time.getMinutes(), 20);
	}

	@Test
	public void testTimeSeconds() throws Exception {
		assertEquals(time.getSeconds(), 50);
	}

	@Test
	public void testToString() throws Exception {
		assertEquals(time.toString(), "04:20:50");
	}

	@Test
	public void testCompareTo() throws Exception {
		assertEquals(time.compareTo(time2), 1);
		assertEquals(time.compareTo(time), 0);
		assertEquals(time2.compareTo(time), -1);

		thrown.expect(InvalidParameterException.class);
		time.compareTo(new IntegerScore());
	}

}

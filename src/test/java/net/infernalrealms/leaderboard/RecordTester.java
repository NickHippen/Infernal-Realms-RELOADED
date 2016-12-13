package net.infernalrealms.leaderboard;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

public class RecordTester {

	private Record<TimeScore> record;
	private Record<TimeScore> record2;
	private List<Record<TimeScore>> records;

	@Before
	public void init() {
		record = new SoloRecord<>("Example", new TimeScore(60));
		record2 = new SoloRecord<>("Example2", new TimeScore(20));

		records = new ArrayList<>();
	}

	@Test
	public void testCompareTo() throws Exception {
		assertEquals(record.compareTo(record2), 1);
		assertEquals(record.compareTo(record), 0);
	}

	@Test
	public void testEquals() throws Exception {
		assertEquals(record, record);
		assertEquals(new SoloRecord<>("Example", new TimeScore(60)), record);
	}

	@Test
	public void testInsertSorted() throws Exception {
		// Set up base records
		records.add(new SoloRecord<>("Nick", new TimeScore(7)));
		records.add(new SoloRecord<>("Ingrid", new TimeScore(8)));

		Record.insertRecordSorted(records, new SoloRecord<>("Ingrid", new TimeScore(7)));

		List<Record<TimeScore>> expected = new ArrayList<>();
		expected.add(new SoloRecord<>("Nick", new TimeScore(7)));
		expected.add(new SoloRecord<>("Ingrid", new TimeScore(7)));
		assertEquals(expected, records);
	}

	@Test
	public void testEqualParticipants() throws Exception {
		Map<String, Object> data = new HashMap<>();
		List<String> names = new ArrayList<>();
		names.add("Nick");
		names.add("Ingrid");
		data.put("memberNames", names);
		data.put("score", new TimeScore(1));
		PartyRecord<TimeScore> record1 = new PartyRecord<TimeScore>(data);

		names = new ArrayList<>();
		names.add("Ingrid");
		names.add("Nick");
		data.put("memberNames", names);
		data.put("score", new TimeScore(3));
		PartyRecord<TimeScore> record2 = new PartyRecord<TimeScore>(data);

		assertEquals(record1.hasEqualParticipants(record2), true);
	}

}

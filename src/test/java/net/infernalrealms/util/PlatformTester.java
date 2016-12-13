package net.infernalrealms.util;

import org.bukkit.Location;
import org.junit.Test;

import junit.framework.Assert;
import net.infernalrealms.cutscenes.WrappedLocation.SerializableLocation;
import net.infernalrealms.nullobjects.NullWorld;
import net.infernalrealms.util.Platform;

// @formatter:off
public class PlatformTester {

	@Test
	public void testIncrease() throws Exception {
		Platform p = new Platform(
				new SerializableLocation(new NullWorld(), 0, 0, 0, 0, 0),
				new SerializableLocation(new NullWorld(), 1, 1, 1, 0, 0));
		p.moveTo(new Location(new NullWorld(), 1, 1, 1));
		Assert.assertEquals(new Platform(
				new SerializableLocation(new NullWorld(), 1, 1, 1, 0, 0),
				new SerializableLocation(new NullWorld(), 2, 2, 2, 0, 0)), p);
	}
	
	@Test
	public void testAll() throws Exception {
		final int amount = 10;
		for (int i = -amount; i <= amount; i++) {
			for (int j = -amount; j <= amount ; j++) {
				for (int k = -amount ; k <= amount ; k++) {
					Platform p = new Platform(
							new SerializableLocation(new NullWorld(), 0, 0, 0, 0, 0),
							new SerializableLocation(new NullWorld(), 1, 1, 1, 0, 0));
					p.moveTo(new Location(new NullWorld(), i, j, k));
					Assert.assertEquals(new Platform(
							new SerializableLocation(new NullWorld(), i, j, k, 0, 0),
							new SerializableLocation(new NullWorld(), 1+i, 1+j, 1+k, 0, 0)), p);
				}
			}
		}
	}
	
	@Test
	public void testAwkward() throws Exception {
		Platform p = new Platform(
				new SerializableLocation(new NullWorld(), 1000, 98, 1002, 0, 0),
				new SerializableLocation(new NullWorld(), 1001, 99, 1001, 0, 0));
		p.moveTo(new Location(new NullWorld(), 1001, 100, 1004));
		Assert.assertEquals(new Platform(
				new SerializableLocation(new NullWorld(), 1001, 100, 1005, 0, 0),
				new SerializableLocation(new NullWorld(), 1002, 101, 1004, 0, 0)), p);
	}

}
// @formatter:on

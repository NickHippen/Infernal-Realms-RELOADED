package net.infernalrealms.leaderboard;

import org.bukkit.configuration.serialization.ConfigurationSerializable;

public interface Scoreable extends ConfigurationSerializable, Comparable<Scoreable> {}
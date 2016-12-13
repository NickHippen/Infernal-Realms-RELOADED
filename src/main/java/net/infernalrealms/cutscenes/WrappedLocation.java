package net.infernalrealms.cutscenes;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

public class WrappedLocation extends Location {

	public WrappedLocation() {
		super(null, 0, 0, 0);
	}

	public WrappedLocation(Location location) {
		super(location.getWorld(), location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch());
	}

	public WrappedLocation(World world, double x, double y, double z, float yaw, float pitch) {
		super(world, x, y, z, yaw, pitch);
	}

	public static class SerializableLocation extends WrappedLocation implements Serializable {

		private static final long serialVersionUID = -7133625830923349603L;

		public SerializableLocation() {
			super();
		}

		public SerializableLocation(Location location) {
			super(location.getWorld(), location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch());
		}

		public SerializableLocation(World world, double x, double y, double z, float yaw, float pitch) {
			super(world, x, y, z, yaw, pitch);
		}

		private void writeObject(ObjectOutputStream out) throws IOException {
			out.writeObject(super.getWorld().getUID());
			out.writeDouble(super.getX());
			out.writeDouble(super.getY());
			out.writeDouble(super.getZ());
			out.writeFloat(super.getYaw());
			out.writeFloat(super.getPitch());
		}

		private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
			super.setWorld(Bukkit.getWorld((UUID) in.readObject()));
			super.setX(in.readDouble());
			super.setY(in.readDouble());
			super.setZ(in.readDouble());
			super.setYaw(in.readFloat());
			super.setPitch(in.readFloat());
		}

	}

}

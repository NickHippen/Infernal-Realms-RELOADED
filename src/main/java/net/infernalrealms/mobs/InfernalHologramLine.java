package net.infernalrealms.mobs;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import com.gmail.filoghost.holographicdisplays.util.Utils;

import net.infernalrealms.general.InfernalRealms;
import net.infernalrealms.mobs.types.InfernalMob;
import net.infernalrealms.player.PlayerData;
import net.infernalrealms.skills.magician.MagicBolt;
import net.infernalrealms.util.GeneralUtil;
import net.minecraft.server.v1_9_R1.AxisAlignedBB;
import net.minecraft.server.v1_9_R1.DamageSource;
import net.minecraft.server.v1_9_R1.Entity;
import net.minecraft.server.v1_9_R1.EntityArmorStand;
import net.minecraft.server.v1_9_R1.EntityHuman;
import net.minecraft.server.v1_9_R1.EntityPlayer;
import net.minecraft.server.v1_9_R1.EnumHand;
import net.minecraft.server.v1_9_R1.EnumInteractionResult;
import net.minecraft.server.v1_9_R1.EnumItemSlot;
import net.minecraft.server.v1_9_R1.ItemStack;
import net.minecraft.server.v1_9_R1.NBTTagCompound;
import net.minecraft.server.v1_9_R1.PacketPlayOutEntityTeleport;
import net.minecraft.server.v1_9_R1.Vec3D;
import net.minecraft.server.v1_9_R1.World;

public class InfernalHologramLine extends EntityArmorStand {

	public static final int ENTITY_ID = 30;

	private Entity link;

	public InfernalHologramLine(World world) {
		super(world);
		delete();
	}

	public InfernalHologramLine(World world, Entity link, String name) {
		this(world, link, name, true);
	}

	public InfernalHologramLine(World world, Entity link, String name, boolean nameTag) {
		super(world);
		setCustomName(name);
		setCustomNameVisible(true);
		setGravity(false);
		setInvisible(true);
		setBasePlate(true);
		setArms(false);
		this.link = link;
		((LivingEntity) getBukkitEntity()).setRemoveWhenFarAway(true);

		if (nameTag) {
			InfernalHologramLine self = this;

			new BukkitRunnable() {

				@Override
				public void run() {
					if (link instanceof InfernalMob) {
						InfernalMob im = (InfernalMob) link;
						if (im.getData() == null) {
							return;
						}
						if (im.getData().getNameTag() != self) {
							delete();
							return;
						}
						LivingEntity le = SpawnManager.getSpawnPointMobs().get(im.getData().getSpawn());
						if (le == null) {
							return;
						}
					}
				}
			}.runTaskLater(InfernalRealms.getPlugin(), 5L);
		}

	}

	@Override
	public boolean damageEntity(DamageSource damagesource, float f) {
		if (link == null) {
			die();
		} else {
			link.damageEntity(damagesource, f);
		}
		return false;
	}

	//	public void setPassengerOf(Entity entity) {
	//		super.mount(entity);
	//	}

	public void setText(String text) {
		setCustomName(text);
	}

	public void delete() {
		super.die();
	}

	public void teleport(Location location) {
		double x;
		double y;
		double z;
		x = location.getX();
		y = location.getY();
		z = location.getZ();

		super.setPosition(x, y, z);

		// Send a packet near to update the position.
		PacketPlayOutEntityTeleport teleportPacket = new PacketPlayOutEntityTeleport(this);

		for (Object obj : this.world.players) {
			if (obj instanceof EntityPlayer) {
				EntityPlayer nmsPlayer = (EntityPlayer) obj;

				double distanceSquared = Utils.square(nmsPlayer.locX - this.locX) + Utils.square(nmsPlayer.locZ - this.locZ);
				if (distanceSquared < 8192 && nmsPlayer.playerConnection != null) {
					nmsPlayer.playerConnection.sendPacket(teleportPacket);
				}
			}
		}
	}

	@Override
	public void b(NBTTagCompound nbttagcompound) {
		// Do not save NBT.
	}

	@Override
	public boolean c(NBTTagCompound nbttagcompound) {
		// Do not save NBT.
		return false;
	}

	@Override
	public boolean d(NBTTagCompound nbttagcompound) {
		// Do not save NBT.
		return false;
	}

	@Override
	public void e(NBTTagCompound nbttagcompound) {
		// Do not save NBT.
	}

	@Override
	public EnumInteractionResult a(EntityHuman human, Vec3D vec3d, ItemStack itemstack, EnumHand enumhand) {
		// Prevent stand being equipped
		Player player = Bukkit.getPlayer(human.getUniqueID());
		if (player == null) {
			return EnumInteractionResult.FAIL;
		}
		PlayerData playerData = PlayerData.getData(player);
		if (playerData.getPlayerSuperClass().equals("Magician")) {
			if (player.getItemInHand().getType() == Material.STICK || player.getItemInHand().getType() == Material.BLAZE_ROD) {
				MagicBolt.cast(player);
			}
		} else if (playerData.getPlayerSuperClass().equals("Archer")) {
			if (player.getItemInHand().getType() == Material.BOW) {
				// Fire arrow
				GeneralUtil.fireNormalArrow(player);
			}
		}
		return EnumInteractionResult.FAIL;
	}

	@Override
	public boolean c(int i, ItemStack item) {
		// Prevent stand being equipped.
		return false;
	}

	@Override
	public void setEquipment(EnumItemSlot enumitemslot1, ItemStack itemstack1) {
		// Prevent stand being equipped
	}

	@Override
	public void a(AxisAlignedBB boundingBox) {
		// Do not change it!
	}

	@Override
	public int getId() {

		StackTraceElement[] elements = Thread.currentThread().getStackTrace();
		if (elements.length > 2 && elements[2] != null && elements[2].getFileName().equals("EntityTrackerEntry.java")
				&& elements[2].getLineNumber() > 137 && elements[2].getLineNumber() < 147) {
			// Then this method is being called when creating a new packet, we return a fake ID
			return -1;
		}

		return super.getId();
	}
	// FIXME
	//	@Override
	//	public void makeSound(String sound, float f1, float f2) {
	//		// Remove sounds.
	//	}

	@Override
	public void m() {
		if (link == null || !link.isAlive() || !link.valid) {
			delete();
			return;
		}
		if (link instanceof InfernalMob) {
			InfernalMob im = (InfernalMob) link;
			if (im.getData() == null) {
				return;
			}
			if (im.getData().getNameTag() != this) {
				delete();
				return;
			}
		}
	}

}

package net.infernalrealms.mobs;

import java.util.List;
import java.util.function.Consumer;

import org.bukkit.Material;

public class SpawnBinding {

	private Material bindingMaterial;
	private String baseSpawnName;
	private int mobID;
	private int count;

	public SpawnBinding(Material bindingMaterial, String baseSpawnName, int mobID) {
		this.bindingMaterial = bindingMaterial;
		this.baseSpawnName = baseSpawnName;
		this.mobID = mobID;
		this.count = 0;
	}

	public Material getBindingMaterial() {
		return bindingMaterial;
	}

	public void setBindingMaterial(Material bindingMaterial) {
		this.bindingMaterial = bindingMaterial;
	}

	public String getBaseSpawnName() {
		return baseSpawnName;
	}

	public void setBaseSpawnName(String baseSpawnName) {
		this.baseSpawnName = baseSpawnName;
	}

	public int getMobID() {
		return mobID;
	}

	public void setMobID(int mobID) {
		this.mobID = mobID;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public String getFullSpawnName() {
		return getBaseSpawnName() + getCount();
	}

	public void nextSpawn() {
		this.count++;
	}

	private static SpawnBinding performActionOnBindingForMaterial(List<SpawnBinding> bindings, Material target,
			Consumer<SpawnBinding> action) {
		for (int i = 0; i < bindings.size(); i++) {
			SpawnBinding binding = bindings.get(i);
			if (binding == null) {
				continue;
			}
			if (binding.getBindingMaterial() == target) {
				if (action != null) {
					action.accept(binding);
				}
				return binding;
			}
		}
		return null;
	}

	public static SpawnBinding clearBindingForMaterial(List<SpawnBinding> bindings, Material target) {
		return performActionOnBindingForMaterial(bindings, target, bindings::remove);
	}

	public static SpawnBinding getBindingForMaterial(List<SpawnBinding> bindings, Material target) {
		return performActionOnBindingForMaterial(bindings, target, null);
	}

}

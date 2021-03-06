package net.infernalrealms.npc;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;

import net.citizensnpcs.api.event.NPCRightClickEvent;
import net.citizensnpcs.api.trait.Trait;
import net.citizensnpcs.api.util.DataKey;
import net.infernalrealms.dungeons2.DungeonType;
import net.infernalrealms.gui.DungeonGUI;

public class TraitDungeonKeeper extends Trait {

	private DungeonType dungeonType;

	public TraitDungeonKeeper() {
		super("dungeonkeeper");
	}

	//Here you should load up any values you have previously saved. 
	//This does NOT get called when applying the trait for the first time, only loading onto an existing npc at server start.
	//This is called AFTER onAttach so you can load defaults in onAttach and they will be overridden here.
	//This is called BEFORE onSpawn so do not try to access npc.getBukkitEntity(). It will be null.
	public void load(DataKey key) {
		this.dungeonType = DungeonType.fromDisplayName(key.getString("dungeonType"));
	}

	//Save settings for this NPC. These values will be added to the citizens saves.yml under this NPC.
	public void save(DataKey key) {
		key.setRaw("dungeonType", this.dungeonType.getDisplayName());
	}

	@EventHandler
	public void onRightClick(NPCRightClickEvent event) {
		if (event.getNPC() == this.getNPC()) {
			Player player = event.getClicker();

			DungeonGUI.openDungeonBaseMenu(player, dungeonType);
		}
	}

	//Run code when your trait is attached to a NPC. 
	//This is called BEFORE onSpawn so do not try to access npc.getBukkitEntity(). It will be null.
	@Override
	public void onAttach() {}

	// Run code when the NPC is despawned. This is called before the entity actually despawns so npc.getBukkitEntity() is still valid.
	@Override
	public void onDespawn() {}

	//Run code when the NPC is spawned. Note that npc.getBukkitEntity() will be null until this method is called.
	//This is called AFTER onAttach and AFTER Load when the server is started.
	@Override
	public void onSpawn() {

	}

	//run code when the NPC is removed. Use this to tear down any repeating tasks.
	@Override
	public void onRemove() {}

	public void setDungeonType(DungeonType dt) {
		this.dungeonType = dt;
	}
}

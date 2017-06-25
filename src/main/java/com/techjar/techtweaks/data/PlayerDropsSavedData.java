package com.techjar.techtweaks.data;


import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;
import net.minecraft.world.WorldSavedData;
import net.minecraft.world.storage.MapStorage;

public class PlayerDropsSavedData extends WorldSavedData {
	public static final String DATA_NAME = "TechTweaks_PlayerDrops";
	private Map<UUID, NBTTagList> map = new HashMap<UUID, NBTTagList>();
	
	public PlayerDropsSavedData() {
		super(DATA_NAME);
	}
	
	public PlayerDropsSavedData(String name) {
		super(name);
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		map.clear();
		NBTTagList list = nbt.getTagList("Data", 10);
		for (int i = 0; i < list.tagCount(); i++) {
			NBTTagCompound tag = list.getCompoundTagAt(i);
			map.put(UUID.fromString(tag.getString("UUID")), tag.getTagList("Inventory", 10));
		}
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		NBTTagList list = new NBTTagList();
		for (Map.Entry<UUID, NBTTagList> entry : map.entrySet()) {
			NBTTagCompound tag = new NBTTagCompound();
			tag.setString("UUID", entry.getKey().toString());
			tag.setTag("Inventory", entry.getValue());
			list.appendTag(tag);
		}
		nbt.setTag("Data", list);
	}
	
	public void cachePlayer(EntityPlayer player) {
		if (!map.containsKey(player.getGameProfile().getId())) {
			map.put(player.getGameProfile().getId(), player.inventory.writeToNBT(new NBTTagList()));
			this.markDirty();
		}
	}
	
	public void restorePlayer(EntityPlayer player) {
		NBTTagList list = map.remove(player.getGameProfile().getId());
		if (list != null) {
			player.inventory.readFromNBT(list);
			this.markDirty();
		}
	}
	
	public static PlayerDropsSavedData get(World world) {
		MapStorage storage = world.mapStorage;
		PlayerDropsSavedData instance = (PlayerDropsSavedData)storage.loadData(PlayerDropsSavedData.class, DATA_NAME);
		if (instance == null) {
			instance = new PlayerDropsSavedData();
			storage.setData(DATA_NAME, instance);
		}
		return instance;
	}
}

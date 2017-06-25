package com.techjar.techtweaks.proxy;

import java.lang.reflect.Method;
import java.util.Set;

import com.techjar.techtweaks.event.PlayerEventHandler;
import com.techjar.techtweaks.util.LogHelper;
import com.techjar.techtweaks.util.ReflectionHelper;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.INetHandler;
import net.minecraft.network.NetHandlerPlayServer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Property;
import cpw.mods.fml.common.FMLCommonHandler;

public class ProxyCommon {
	public void preInit() {
	}

	public void init() {
		registerRenderers();
		registerEventHandlers();
	}
	
	@SuppressWarnings("unchecked")
	public void postInit() {
		try {
			Method setBiomeList = ReflectionHelper.getDeclaredMethod("buildcraft.BuildCraftEnergy", "setBiomeList", Set.class, Property.class);
			Method get = ReflectionHelper.getDeclaredMethod("buildcraft.core.config.ConfigManager", "get", String.class, String.class);
			Object buildCraftEnergy = ReflectionHelper.getDeclaredField("buildcraft.BuildCraftEnergy", "instance").get(null);
			Object mainConfigManager = ReflectionHelper.getDeclaredField("buildcraft.BuildCraftCore", "mainConfigManager").get(null);
			Object oilPopulate = ReflectionHelper.getDeclaredField("buildcraft.energy.worldgen.OilPopulate", "INSTANCE").get(null);
			Set<Integer> surfaceDepositBiomes = (Set<Integer>)ReflectionHelper.getDeclaredField("buildcraft.energy.worldgen.OilPopulate", "surfaceDepositBiomes").get(oilPopulate);
			Set<Integer> excessiveBiomes = (Set<Integer>)ReflectionHelper.getDeclaredField("buildcraft.energy.worldgen.OilPopulate", "excessiveBiomes").get(oilPopulate);
			Set<Integer> excludedBiomes = (Set<Integer>)ReflectionHelper.getDeclaredField("buildcraft.energy.worldgen.OilPopulate", "excludedBiomes").get(oilPopulate);
			surfaceDepositBiomes.clear();
			excessiveBiomes.clear();
			excludedBiomes.clear();
			setBiomeList.invoke(buildCraftEnergy, surfaceDepositBiomes, get.invoke(mainConfigManager, "worldgen.biomes", "increasedOilIDs"));
			setBiomeList.invoke(buildCraftEnergy, excessiveBiomes, get.invoke(mainConfigManager, "worldgen.biomes", "excessiveOilIDs"));
			setBiomeList.invoke(buildCraftEnergy, excludedBiomes, get.invoke(mainConfigManager, "worldgen.biomes", "excludeOilIDs"));
			LogHelper.debug("BuildCraft reflection succeeded! Biome lists have been post-loaded!");
		} catch (Exception ex) {
			LogHelper.warning("BuildCraft reflection failed!");
			ex.printStackTrace();
		}
	}
	
	public void registerRenderers() {
		// Nothing here as the server doesn't render graphics!
	}
	
	public void registerEventHandlers() {
		MinecraftForge.EVENT_BUS.register(new PlayerEventHandler());
		FMLCommonHandler.instance().bus().register(new PlayerEventHandler());
	}

	public EntityPlayer getPlayerFromNetHandler(INetHandler netHandler) {
		if (netHandler instanceof NetHandlerPlayServer) {
			return ((NetHandlerPlayServer)netHandler).playerEntity;
		}
		return null;
	}
}

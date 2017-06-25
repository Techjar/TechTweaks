package com.techjar.techtweaks;

import net.minecraftforge.common.config.Configuration;

import com.techjar.techtweaks.command.*;
import com.techjar.techtweaks.proxy.ProxyCommon;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;

@Mod(modid = "TechTweaks", name = "TechTweaks", version = "@VERSION@", dependencies = "required-after:Forge@[10.13.4.1614,)", acceptableRemoteVersions = "@RAW_VERSION@.*")
public class TechTweaks {
	@Instance("TechTweaks")
	public static TechTweaks instance;
	
	@SidedProxy(clientSide = "com.techjar.techtweaks.proxy.ProxyClient", serverSide = "com.techjar.techtweaks.proxy.ProxyServer")
	public static ProxyCommon proxy;

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		Configuration config = new Configuration(event.getSuggestedConfigurationFile());
		config.load();
		Config.exactSpawnY = config.get(Configuration.CATEGORY_GENERAL, "exactSpawnY", false, "Spawn players at the exact Y coord with fuzz still applied on X and Z. Default: false").getBoolean(false);
		if (config.hasChanged()) config.save();
		proxy.preInit();
	}

	@EventHandler
	public void load(FMLInitializationEvent event) {
		proxy.init();
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		proxy.postInit();
	}
	
	@EventHandler
	public void serverStart(FMLServerStartingEvent event) {
		event.registerServerCommand(new CommandMemory());
		event.registerServerCommand(new CommandTestSpawn());
	}
}

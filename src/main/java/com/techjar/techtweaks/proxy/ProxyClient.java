package com.techjar.techtweaks.proxy;

import com.techjar.techtweaks.event.SoundEventHandler;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.INetHandler;
import net.minecraft.network.NetHandlerPlayServer;
import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.client.FMLClientHandler;

public class ProxyClient extends ProxyCommon {
	public void preInit() {
		super.preInit();
		MinecraftForge.EVENT_BUS.register(new SoundEventHandler());
	}
	
	@Override
	public void registerRenderers() {
	}

	@Override
	public void registerEventHandlers() {
		super.registerEventHandlers();
	}

	@Override
	public EntityPlayer getPlayerFromNetHandler(INetHandler netHandler) {
		if (netHandler instanceof NetHandlerPlayServer) {
			return ((NetHandlerPlayServer)netHandler).playerEntity;
		}
		return FMLClientHandler.instance().getClientPlayerEntity();
	}
}

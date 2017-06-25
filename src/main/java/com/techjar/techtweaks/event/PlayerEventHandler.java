package com.techjar.techtweaks.event;

import com.techjar.techtweaks.data.PlayerDropsSavedData;

import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.PlayerRespawnEvent;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.PlayerDropsEvent;

public class PlayerEventHandler {
	@SubscribeEvent(priority = EventPriority.HIGHEST)
	public void onPlayerDrops(PlayerDropsEvent event) {
		if (event.source != null && event.source.getEntity() instanceof EntityPlayer) {
			event.setCanceled(true);
		}
	}
	
	@SubscribeEvent(priority = EventPriority.HIGHEST)
	public void onPlayerDeath(LivingDeathEvent event) {
		if (!event.entityLiving.worldObj.getGameRules().getGameRuleBooleanValue("keepInventory")) {
			if (event.entityLiving instanceof EntityPlayer && event.source != null && event.source.getEntity() instanceof EntityPlayer) {
				EntityPlayer player = (EntityPlayer)event.entityLiving;
				PlayerDropsSavedData.get(player.worldObj).cachePlayer(player);
				//player.inventory.clearInventory(null, -1);
			}
		}
	}
	
	@SubscribeEvent(priority = EventPriority.HIGHEST)
	public void onPlayerRespawn(PlayerRespawnEvent event) {
		PlayerDropsSavedData.get(event.player.worldObj).restorePlayer(event.player);
	}
}

package com.techjar.techtweaks.util;

import com.techjar.techtweaks.Config;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.BiomeGenMutated;

public class ASMDelegator {
	private ASMDelegator() {
	}
	
	public static boolean checkBiomeMutation(int id) {
		BiomeGenBase base = BiomeGenBase.getBiome(id);
		BiomeGenBase mutation = BiomeGenBase.getBiome(id + 128);
		return mutation != null && (mutation instanceof BiomeGenMutated || base.getClass().isAssignableFrom(mutation.getClass()));
	}
	
	public static void spawnPosY(ChunkCoordinates coord, World world) {
		if (!Config.exactSpawnY || world.provider.dimensionId != 0) {
			coord.posY = world.getTopSolidOrLiquidBlock(coord.posX, coord.posZ);
		}
	}
	
	public static boolean checkPVPDrops(DamageSource damageSource) {
		return damageSource == null || !(damageSource.getEntity() instanceof EntityPlayer);
	}
}

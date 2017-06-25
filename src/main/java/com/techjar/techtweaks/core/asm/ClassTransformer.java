package com.techjar.techtweaks.core.asm;

import java.util.ArrayList;
import java.util.List;

import com.techjar.techtweaks.core.asm.handler.*;
import com.techjar.techtweaks.util.LogHelper;

import net.minecraft.launchwrapper.IClassTransformer;

public class ClassTransformer implements IClassTransformer {
	private static final List<ASMClassHandler> asmHandlers = new ArrayList<ASMClassHandler>();
	static {
		asmHandlers.add(new ASMHandlerEntityAILagReduction());
		asmHandlers.add(new ASMHandlerBiomeMutationFix());
		asmHandlers.add(new ASMHandlerPossibleChunkManagerCrash());
		asmHandlers.add(new ASMHandlerBuildCraftBiomeListStupidness());
		asmHandlers.add(new ASMHandlerMrTJPCoreHandlers());
		asmHandlers.add(new ASMHandlerChickenChunksLTSaving());
		asmHandlers.add(new ASMHandlerChickenChunksLTSaving2());
		asmHandlers.add(new ASMHandlerExactSpawnY());
		asmHandlers.add(new ASMHandlerNoPVPDrops());
		asmHandlers.add(new ASMHandlerExUFECompatFix());
		asmHandlers.add(new ASMHandlerOpenBlocksXPShowerFix());
	}
	
	@Override
	public byte[] transform(String name, String transformedName, byte[] bytes) {
		for (ASMClassHandler handler : asmHandlers) {
			if (!handler.shouldPatchClass()) continue;
			ClassTuple tuple = handler.getDesiredClass();
			if (transformedName.equals(tuple.className)) {
				LogHelper.debug("Patching class: " + transformedName);
				bytes = handler.patchClass(bytes, false);
			}
		}
		return bytes;
	}
}

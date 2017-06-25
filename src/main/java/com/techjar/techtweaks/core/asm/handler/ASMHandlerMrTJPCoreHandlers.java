package com.techjar.techtweaks.core.asm.handler;

import org.objectweb.asm.tree.ClassNode;

import com.techjar.techtweaks.core.asm.ASMClassHandler;
import com.techjar.techtweaks.core.asm.ASMMethodHandler;
import com.techjar.techtweaks.core.asm.ASMUtil;
import com.techjar.techtweaks.core.asm.ClassTuple;
import com.techjar.techtweaks.core.asm.MethodTuple;
import com.techjar.techtweaks.util.LogHelper;

public class ASMHandlerMrTJPCoreHandlers extends ASMClassHandler {
	@Override
	public ClassTuple getDesiredClass() {
		return new ClassTuple("mrtjp.core.world.BlockUpdateHandler$");
	}

	@Override
	public ASMMethodHandler[] getMethodHandlers() {
		return new ASMMethodHandler[]{};
	}

	@Override
	public boolean getComputeFrames() {
		return false;
	}
	
	@Override
	protected void patchClass(ClassNode node, boolean obfuscated) {
		if (ASMUtil.deleteMethod(node, new MethodTuple("getActiveChunkSet", "(Lnet/minecraft/world/World;)Ljava/util/Set;"))) {
			LogHelper.debug("Deleted getActiveChunkSet");
		}
		if (ASMUtil.deleteMethod(node, new MethodTuple("onTick", "(Lcpw/mods/fml/common/gameevent/TickEvent$WorldTickEvent;)V"))) {
			LogHelper.debug("Deleted onTick");
		}
	}
}

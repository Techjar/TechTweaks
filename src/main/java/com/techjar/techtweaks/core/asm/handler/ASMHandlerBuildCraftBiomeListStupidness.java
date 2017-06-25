package com.techjar.techtweaks.core.asm.handler;

import java.util.Iterator;

import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;

import com.techjar.techtweaks.core.asm.ASMClassHandler;
import com.techjar.techtweaks.core.asm.ASMMethodHandler;
import com.techjar.techtweaks.core.asm.ClassTuple;
import com.techjar.techtweaks.core.asm.MethodTuple;
import com.techjar.techtweaks.util.LogHelper;

public class ASMHandlerBuildCraftBiomeListStupidness extends ASMClassHandler {
	@Override
	public ClassTuple getDesiredClass() {
		return new ClassTuple("buildcraft.BuildCraftEnergy");
	}

	@Override
	public ASMMethodHandler[] getMethodHandlers() {
		return new ASMMethodHandler[]{new MethodHandler()};
	}

	@Override
	public boolean getComputeFrames() {
		return false;
	}

	public static class MethodHandler implements ASMMethodHandler {
		@Override
		public MethodTuple getDesiredMethod() {
			return new MethodTuple("setBiomeList", "(Ljava/util/Set;Lnet/minecraftforge/common/config/Property;)V");
		}

		@Override
		public void patchMethod(MethodNode methodNode, ClassNode classNode, boolean obfuscated) {
			int count = 0;
			for (Iterator<AbstractInsnNode> it = methodNode.instructions.iterator(); it.hasNext(); ) {
				AbstractInsnNode insn = it.next();
				if (insn instanceof MethodInsnNode) {
					MethodInsnNode insn2 = (MethodInsnNode)insn;
					if (insn2.name.equals("toUpperCase")) {
						it.remove();
						count++;
					}
				}
			}
			LogHelper.debug("Removed " + count + " toUpperCase calls.");
		}
	}
}

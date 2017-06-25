package com.techjar.techtweaks.core.asm.handler;

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.VarInsnNode;

import com.techjar.techtweaks.core.asm.ASMClassHandler;
import com.techjar.techtweaks.core.asm.ASMMethodHandler;
import com.techjar.techtweaks.core.asm.ASMUtil;
import com.techjar.techtweaks.core.asm.ClassTuple;
import com.techjar.techtweaks.core.asm.MethodTuple;
import com.techjar.techtweaks.util.LogHelper;

public class ASMHandlerOpenBlocksXPShowerFix extends ASMClassHandler {
	@Override
	public ClassTuple getDesiredClass() {
		return new ClassTuple("openblocks.common.tileentity.TileEntityXPShower");
	}

	@Override
	public ASMMethodHandler[] getMethodHandlers() {
		return new ASMMethodHandler[]{new MethodHandler()};
	}

	@Override
	public boolean getComputeFrames() {
		return true;
	}

	public static class MethodHandler implements ASMMethodHandler {
		@Override
		public MethodTuple getDesiredMethod() {
			return new MethodTuple("updateEntity", "()V", "func_145845_h", "()V");
		}

		@Override
		public void patchMethod(MethodNode methodNode, ClassNode classNode, boolean obfuscated) {
			AbstractInsnNode insert = ASMUtil.findFirstOpcode(methodNode, Opcodes.IFNE);
			InsnList list = new InsnList();
			list.add(new VarInsnNode(Opcodes.ALOAD, 0));
			list.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL, "openblocks/common/tileentity/TileEntityXPShower", "updateState", "()V", false));
			methodNode.instructions.insert(insert, list);
			LogHelper.debug("Inserted method call.");
		}
	}
}

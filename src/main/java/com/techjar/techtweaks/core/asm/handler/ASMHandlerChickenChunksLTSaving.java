package com.techjar.techtweaks.core.asm.handler;

import net.minecraftforge.classloading.FMLForgePlugin;

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;

import com.techjar.techtweaks.core.asm.ASMClassHandler;
import com.techjar.techtweaks.core.asm.ASMMethodHandler;
import com.techjar.techtweaks.core.asm.ASMUtil;
import com.techjar.techtweaks.core.asm.ClassTuple;
import com.techjar.techtweaks.core.asm.MethodTuple;
import com.techjar.techtweaks.util.LogHelper;

public class ASMHandlerChickenChunksLTSaving extends ASMClassHandler {
	@Override
	public ClassTuple getDesiredClass() {
		return new ClassTuple("codechicken.chunkloader.ChunkLoaderManager");
	}

	@Override
	public ASMMethodHandler[] getMethodHandlers() {
		return new ASMMethodHandler[]{new MethodHandler1(), new MethodHandler2()};
	}

	@Override
	public boolean getComputeFrames() {
		return false;
	}

	public static class MethodHandler1 implements ASMMethodHandler {
		@Override
		public MethodTuple getDesiredMethod() {
			return new MethodTuple("tickEnd", "(Lnet/minecraft/world/WorldServer;)V");
		}

		@Override
		public void patchMethod(MethodNode methodNode, ClassNode classNode, boolean obfuscated) {
			AbstractInsnNode insn = ASMUtil.findFirstInstruction(methodNode, Opcodes.INVOKESTATIC, "codechicken/chunkloader/ChunkLoaderManager", "updateLoginTimes", "()V", false);
			methodNode.instructions.remove(insn);
			LogHelper.debug("Removed method call.");
			insn = ASMUtil.findNthInstruction(methodNode, Opcodes.ALOAD, 1, 0);
			InsnList list = new InsnList();
			list.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "net/minecraft/server/MinecraftServer", FMLForgePlugin.RUNTIME_DEOBF ? "func_71276_C" : "getServer", "()Lnet/minecraft/server/MinecraftServer;", false));
			list.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL, "net/minecraft/server/MinecraftServer", FMLForgePlugin.RUNTIME_DEOBF ? "func_71259_af" : "getTickCounter", "()I", false));
			list.add(new FieldInsnNode(Opcodes.GETSTATIC, "codechicken/chunkloader/ChunkLoaderManager", "cleanupTicks", "I"));
			list.add(new InsnNode(Opcodes.IREM));
			methodNode.instructions.insertBefore(insn, list);
			ASMUtil.deleteInstructions(methodNode, methodNode.instructions.indexOf(insn), 7);
			LogHelper.debug("Changed if condition.");
		}
	}

	public static class MethodHandler2 implements ASMMethodHandler {
		@Override
		public MethodTuple getDesiredMethod() {
			return new MethodTuple("updateLoginTimes", "()V");
		}

		@Override
		public void patchMethod(MethodNode methodNode, ClassNode classNode, boolean obfuscated) {
			methodNode.access = Opcodes.ACC_PUBLIC | Opcodes.ACC_STATIC;
			LogHelper.debug("Changed access to public static.");
		}
	}
}

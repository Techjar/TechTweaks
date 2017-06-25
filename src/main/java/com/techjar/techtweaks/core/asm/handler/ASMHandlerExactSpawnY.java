package com.techjar.techtweaks.core.asm.handler;

import net.minecraftforge.classloading.FMLForgePlugin;

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;

import com.techjar.techtweaks.core.asm.ASMClassHandler;
import com.techjar.techtweaks.core.asm.ASMMethodHandler;
import com.techjar.techtweaks.core.asm.ASMUtil;
import com.techjar.techtweaks.core.asm.ClassTuple;
import com.techjar.techtweaks.core.asm.MethodTuple;
import com.techjar.techtweaks.util.LogHelper;

public class ASMHandlerExactSpawnY extends ASMClassHandler {
	@Override
	public ClassTuple getDesiredClass() {
		return new ClassTuple("net.minecraft.world.WorldProvider");
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
			return new MethodTuple("getRandomizedSpawnPoint", "()Lnet/minecraft/util/ChunkCoordinates;");
		}

		@Override
		public void patchMethod(MethodNode methodNode, ClassNode classNode, boolean obfuscated) {
			AbstractInsnNode insn = ASMUtil.findFirstInstruction(methodNode, Opcodes.PUTFIELD, "net/minecraft/util/ChunkCoordinates", FMLForgePlugin.RUNTIME_DEOBF ? "field_71572_b" : "posY", "I");
			methodNode.instructions.insert(insn, new MethodInsnNode(Opcodes.INVOKESTATIC, "com/techjar/techtweaks/util/ASMDelegator", "spawnPosY", "(Lnet/minecraft/util/ChunkCoordinates;Lnet/minecraft/world/World;)V", false));
			int remove = methodNode.instructions.indexOf(insn) - 5;
			ASMUtil.deleteInstructions(methodNode, remove, 6);
			LogHelper.debug("Inserted delegate method call.");
		}
	}
}

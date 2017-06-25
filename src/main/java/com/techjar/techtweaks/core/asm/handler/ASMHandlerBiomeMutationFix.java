package com.techjar.techtweaks.core.asm.handler;

import lombok.libs.org.objectweb.asm.Opcodes;
import net.minecraftforge.classloading.FMLForgePlugin;

import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.JumpInsnNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;

import com.techjar.techtweaks.core.asm.ASMClassHandler;
import com.techjar.techtweaks.core.asm.ASMMethodHandler;
import com.techjar.techtweaks.core.asm.ASMUtil;
import com.techjar.techtweaks.core.asm.ClassTuple;
import com.techjar.techtweaks.core.asm.MethodTuple;
import com.techjar.techtweaks.util.LogHelper;

public class ASMHandlerBiomeMutationFix extends ASMClassHandler {
	@Override
	public ClassTuple getDesiredClass() {
		return new ClassTuple("net.minecraft.world.gen.layer.GenLayerHills");
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
			return new MethodTuple("getInts", "(IIII)[I", "func_75904_a", "(IIII)[I");
		}

		@Override
		public void patchMethod(MethodNode methodNode, ClassNode classNode, boolean obfuscated) {
			int remove = methodNode.instructions.indexOf(ASMUtil.findFirstInstruction(methodNode, Opcodes.INVOKESTATIC, "net/minecraft/world/biome/BiomeGenBase", FMLForgePlugin.RUNTIME_DEOBF ? "func_150568_d" : "getBiome", "(I)Lnet/minecraft/world/biome/BiomeGenBase;", false)) - 2;
			JumpInsnNode jump = (JumpInsnNode)methodNode.instructions.get(remove + 3);
			jump.setOpcode(Opcodes.IFEQ);
			AbstractInsnNode insert = methodNode.instructions.get(remove - 1);
			ASMUtil.deleteInstructions(methodNode, remove, 3);
			methodNode.instructions.insert(insert, new MethodInsnNode(Opcodes.INVOKESTATIC, "com/techjar/techtweaks/util/ASMDelegator", "checkBiomeMutation", "(I)Z", false));
			LogHelper.debug("Inserted delegate method call.");
		}
	}
}

package com.techjar.techtweaks.core.asm.handler;

import net.minecraftforge.classloading.FMLForgePlugin;

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.JumpInsnNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.VarInsnNode;

import com.techjar.techtweaks.core.asm.ASMClassHandler;
import com.techjar.techtweaks.core.asm.ASMMethodHandler;
import com.techjar.techtweaks.core.asm.ASMUtil;
import com.techjar.techtweaks.core.asm.ClassTuple;
import com.techjar.techtweaks.core.asm.MethodTuple;
import com.techjar.techtweaks.util.LogHelper;

public class ASMHandlerNoPVPDrops extends ASMClassHandler {
	@Override
	public ClassTuple getDesiredClass() {
		return new ClassTuple("net.minecraft.entity.player.EntityPlayerMP");
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
			return new MethodTuple("onDeath", "(Lnet/minecraft/util/DamageSource;)V", "func_70645_a", "(Lnet/minecraft/util/DamageSource;)V");
		}

		@Override
		public void patchMethod(MethodNode methodNode, ClassNode classNode, boolean obfuscated) {
			AbstractInsnNode insn = ASMUtil.findFirstInstruction(methodNode, Opcodes.INVOKEVIRTUAL, "net/minecraft/world/GameRules", FMLForgePlugin.RUNTIME_DEOBF ? "func_82766_b" : "getGameRuleBooleanValue", "(Ljava/lang/String;)Z", false);
			JumpInsnNode jump = (JumpInsnNode)methodNode.instructions.get(methodNode.instructions.indexOf(insn) + 1);
			InsnList list = new InsnList();
			list.add(new VarInsnNode(Opcodes.ALOAD, 1));
			list.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "com/techjar/techtweaks/util/ASMDelegator", "checkPVPDrops", "(Lnet/minecraft/util/DamageSource;)Z", false));
			list.add(new JumpInsnNode(Opcodes.IFEQ, jump.label));
			methodNode.instructions.insert(jump, list);
			LogHelper.debug("Inserted delegate method call.");
		}
	}
}

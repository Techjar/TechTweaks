package com.techjar.techtweaks.core.asm.handler;

import net.minecraftforge.classloading.FMLForgePlugin;

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FrameNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.IntInsnNode;
import org.objectweb.asm.tree.JumpInsnNode;
import org.objectweb.asm.tree.LabelNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;

import com.techjar.techtweaks.core.asm.ASMClassHandler;
import com.techjar.techtweaks.core.asm.ASMMethodHandler;
import com.techjar.techtweaks.core.asm.ASMUtil;
import com.techjar.techtweaks.core.asm.ClassTuple;
import com.techjar.techtweaks.core.asm.MethodTuple;
import com.techjar.techtweaks.util.LogHelper;

public class ASMHandlerChickenChunksLTSaving2 extends ASMClassHandler {
	@Override
	public ClassTuple getDesiredClass() {
		return new ClassTuple("codechicken.chunkloader.ChunkLoaderEventHandler");
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
			return new MethodTuple("serverTick", "(Lcpw/mods/fml/common/gameevent/TickEvent$ServerTickEvent;)V");
		}

		@Override
		public void patchMethod(MethodNode methodNode, ClassNode classNode, boolean obfuscated) {
			AbstractInsnNode insert = ASMUtil.findFirstInstruction(methodNode, Opcodes.INVOKEVIRTUAL, "codechicken/chunkloader/PlayerChunkViewerManager", "update", "()V", false);
			InsnList list = new InsnList();
			list.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "net/minecraft/server/MinecraftServer", FMLForgePlugin.RUNTIME_DEOBF ? "func_71276_C" : "getServer", "()Lnet/minecraft/server/MinecraftServer;", false));
			list.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL, "net/minecraft/server/MinecraftServer", FMLForgePlugin.RUNTIME_DEOBF ? "func_71259_af" : "getTickCounter", "()I", false));
			list.add(new IntInsnNode(Opcodes.SIPUSH, 1200));
			list.add(new InsnNode(Opcodes.IREM));
			LabelNode label1 = new LabelNode();
			list.add(new JumpInsnNode(Opcodes.IFNE, label1));
			list.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "codechicken/chunkloader/ChunkLoaderManager", "updateLoginTimes", "()V", false));
			list.add(label1);
			list.add(new FrameNode(Opcodes.F_SAME, 0, null, 0, null));
			methodNode.instructions.insert(insert, list);
			LogHelper.debug("Added method call.");
		}
	}
}

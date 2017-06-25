package com.techjar.techtweaks.core.asm.handler;

import lombok.libs.org.objectweb.asm.Opcodes;

import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.FrameNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.JumpInsnNode;
import org.objectweb.asm.tree.LabelNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.VarInsnNode;

import com.techjar.techtweaks.core.asm.ASMClassHandler;
import com.techjar.techtweaks.core.asm.ASMMethodHandler;
import com.techjar.techtweaks.core.asm.ClassTuple;
import com.techjar.techtweaks.core.asm.MethodTuple;
import com.techjar.techtweaks.util.LogHelper;

public class ASMHandlerPossibleChunkManagerCrash extends ASMClassHandler {
	@Override
	public ClassTuple getDesiredClass() {
		return new ClassTuple("net.minecraftforge.common.ForgeChunkManager");
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
			return new MethodTuple("unforceChunk", "(Lnet/minecraftforge/common/ForgeChunkManager$Ticket;Lnet/minecraft/world/ChunkCoordIntPair;)V");
		}

		@Override
		public void patchMethod(MethodNode methodNode, ClassNode classNode, boolean obfuscated) {
			InsnList list = new InsnList();
			list.add(new FieldInsnNode(Opcodes.GETSTATIC, "net/minecraftforge/common/ForgeChunkManager", "forcedChunks", "Ljava/util/Map;"));
			list.add(new VarInsnNode(Opcodes.ALOAD, 0));
			list.add(new FieldInsnNode(Opcodes.GETFIELD, "net/minecraftforge/common/ForgeChunkManager$Ticket", "world", "Lnet/minecraft/world/World;"));
			list.add(new MethodInsnNode(Opcodes.INVOKEINTERFACE, "java/util/Map", "get", "(Ljava/lang/Object;)Ljava/lang/Object;", true));
			LabelNode label1 = new LabelNode();
			list.add(new JumpInsnNode(Opcodes.IFNONNULL, label1));
			list.add(new InsnNode(Opcodes.RETURN));
			list.add(label1);
			list.add(new FrameNode(Opcodes.F_SAME, 0, null, 0, null));
			methodNode.instructions.insert(list);
			LogHelper.debug("Inserted null check.");
		}
	}
}

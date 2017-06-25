package com.techjar.techtweaks.core.asm.handler;

import lombok.libs.org.objectweb.asm.Opcodes;
import net.minecraftforge.classloading.FMLForgePlugin;

import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.FieldNode;
import org.objectweb.asm.tree.FrameNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.JumpInsnNode;
import org.objectweb.asm.tree.LabelNode;
import org.objectweb.asm.tree.LdcInsnNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.VarInsnNode;

import com.techjar.techtweaks.core.asm.ASMClassHandler;
import com.techjar.techtweaks.core.asm.ASMMethodHandler;
import com.techjar.techtweaks.core.asm.ASMUtil;
import com.techjar.techtweaks.core.asm.ClassTuple;
import com.techjar.techtweaks.core.asm.MethodTuple;
import com.techjar.techtweaks.util.LogHelper;

public class ASMHandlerEntityAILagReduction extends ASMClassHandler {
	@Override
	public ClassTuple getDesiredClass() {
		return new ClassTuple("net.minecraft.entity.ai.EntityAINearestAttackableTarget");
	}

	@Override
	public ASMMethodHandler[] getMethodHandlers() {
		return new ASMMethodHandler[]{new MethodHandler()};
	}

	@Override
	public boolean getComputeFrames() {
		return true;
	}
	
	@Override
	protected void patchClass(ClassNode node, boolean obfuscated) {
		node.fields.add(new FieldNode(Opcodes.ACC_PRIVATE, "ticks", "J", null, null));
		LogHelper.debug("Added new field.");
	}

	public static class MethodHandler implements ASMMethodHandler {
		@Override
		public MethodTuple getDesiredMethod() {
			return new MethodTuple("shouldExecute", "()Z", "func_75250_a", "()Z");
		}

		@Override
		public void patchMethod(MethodNode methodNode, ClassNode classNode, boolean obfuscated) {
			InsnList ifList = new InsnList();
			ifList.add(new FrameNode(Opcodes.F_SAME, 0, null, 0, null));
			ifList.add(new VarInsnNode(Opcodes.ALOAD, 0));
			ifList.add(new InsnNode(Opcodes.DUP));
			ifList.add(new FieldInsnNode(Opcodes.GETFIELD, "net/minecraft/entity/ai/EntityAINearestAttackableTarget", "ticks", "J"));
			ifList.add(new InsnNode(Opcodes.DUP2_X1));
			ifList.add(new InsnNode(Opcodes.LCONST_1));
			ifList.add(new InsnNode(Opcodes.LADD));
			ifList.add(new FieldInsnNode(Opcodes.PUTFIELD, "net/minecraft/entity/ai/EntityAINearestAttackableTarget", "ticks", "J"));
			ifList.add(new LdcInsnNode(5L)); // number of ticks between target searches
			ifList.add(new InsnNode(Opcodes.LREM));
			ifList.add(new InsnNode(Opcodes.LCONST_0));
			ifList.add(new InsnNode(Opcodes.LCMP));
			LabelNode label1 = new LabelNode();
			ifList.add(new JumpInsnNode(Opcodes.IFEQ, label1));
			ifList.add(new VarInsnNode(Opcodes.ALOAD, 0));
			ifList.add(new FieldInsnNode(Opcodes.GETFIELD, "net/minecraft/entity/ai/EntityAINearestAttackableTarget", FMLForgePlugin.RUNTIME_DEOBF ? "field_75309_a" : "targetEntity", "Lnet/minecraft/entity/EntityLivingBase;"));
			LabelNode label2 = new LabelNode();
			ifList.add(new JumpInsnNode(Opcodes.IFNULL, label2));
			ifList.add(new VarInsnNode(Opcodes.ALOAD, 0));
			ifList.add(new FieldInsnNode(Opcodes.GETFIELD, "net/minecraft/entity/ai/EntityAINearestAttackableTarget", FMLForgePlugin.RUNTIME_DEOBF ? "field_75309_a" : "targetEntity", "Lnet/minecraft/entity/EntityLivingBase;"));
			ifList.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL, "net/minecraft/entity/EntityLivingBase", FMLForgePlugin.RUNTIME_DEOBF ? "func_70089_S" : "isEntityAlive", "()Z", false));
			ifList.add(new JumpInsnNode(Opcodes.IFNE, label2));
			ifList.add(label1);
			AbstractInsnNode beforeInsertPoint = methodNode.instructions.get(methodNode.instructions.indexOf(ASMUtil.findFirstInstruction(methodNode, Opcodes.INVOKEVIRTUAL, "net/minecraft/entity/ai/EntityAINearestAttackableTarget", FMLForgePlugin.RUNTIME_DEOBF ? "func_111175_f" : "getTargetDistance", "()D", false)) - 2);
			methodNode.instructions.insertBefore(beforeInsertPoint, ifList);
			LogHelper.debug("Inserted if statement.");
			InsnList endList = new InsnList();
			endList.add(label2);
			endList.add(new FrameNode(Opcodes.F_CHOP, 0, null, 0, null));
			endList.add(new InsnNode(Opcodes.ICONST_1));
			endList.add(new InsnNode(Opcodes.IRETURN));
			AbstractInsnNode endInsertPoint = ASMUtil.findLastOpcode(methodNode, Opcodes.IRETURN);
			methodNode.instructions.insert(endInsertPoint, endList);
			LogHelper.debug("Inserted else statement.");
		}
	}
}

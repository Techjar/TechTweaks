package com.techjar.techtweaks.core.asm.handler;

import net.minecraftforge.classloading.FMLForgePlugin;

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.FrameNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.JumpInsnNode;
import org.objectweb.asm.tree.LabelNode;
import org.objectweb.asm.tree.LdcInsnNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.TypeInsnNode;
import org.objectweb.asm.tree.VarInsnNode;

import com.techjar.techtweaks.core.asm.ASMClassHandler;
import com.techjar.techtweaks.core.asm.ASMMethodHandler;
import com.techjar.techtweaks.core.asm.ASMUtil;
import com.techjar.techtweaks.core.asm.ClassTuple;
import com.techjar.techtweaks.core.asm.MethodTuple;
import com.techjar.techtweaks.util.LogHelper;

public class ASMHandlerExUFECompatFix extends ASMClassHandler {
	@Override
	public ClassTuple getDesiredClass() {
		return new ClassTuple("com.rwtema.extrautils.crafting.RecipeUnstableIngotCrafting");
	}

	@Override
	public ASMMethodHandler[] getMethodHandlers() {
		return new ASMMethodHandler[]{new MethodHandler1(), new MethodHandler2()};
	}

	@Override
	public boolean getComputeFrames() {
		return true;
	}
	
	/*@Override
	protected void patchClass(ClassNode node, boolean obfuscated) {
		node.fields.add(new FieldNode(Opcodes.ACC_PRIVATE, "theEventHandler", "Lnet/minecraft/inventory/Container;", null, null));
		LogHelper.debug("Added new field.");
	}*/

	public static class MethodHandler1 implements ASMMethodHandler {
		@Override
		public MethodTuple getDesiredMethod() {
			return new MethodTuple("getCraftingResult", "(Lnet/minecraft/inventory/InventoryCrafting;)Lnet/minecraft/item/ItemStack;", "func_77572_b", "(Lnet/minecraft/inventory/InventoryCrafting;)Lnet/minecraft/item/ItemStack;");
		}

		@Override
		public void patchMethod(MethodNode methodNode, ClassNode classNode, boolean obfuscated) {
			AbstractInsnNode insert = ASMUtil.findLastInstruction(methodNode, Opcodes.ALOAD, 0);
			InsnList list = new InsnList();
			list.add(new VarInsnNode(Opcodes.ALOAD, 5));
			list.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL, "net/minecraft/item/ItemStack", FMLForgePlugin.RUNTIME_DEOBF ? "func_77978_p" : "getTagCompound", "()Lnet/minecraft/nbt/NBTTagCompound;", false));
			list.add(new LdcInsnNode("stable"));
			list.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL, "net/minecraft/nbt/NBTTagCompound", FMLForgePlugin.RUNTIME_DEOBF ? "func_74764_b" : "hasKey", "(Ljava/lang/String;)Z", false));
			LabelNode label = new LabelNode();
			list.add(new JumpInsnNode(Opcodes.IFNE, label));
			list.add(new VarInsnNode(Opcodes.ALOAD, 0));
			list.add(new TypeInsnNode(Opcodes.INSTANCEOF, "com/rwtema/extrautils/crafting/RecipeUnstableNuggetCrafting"));
			list.add(new JumpInsnNode(Opcodes.IFNE, label));
			list.add(new VarInsnNode(Opcodes.ALOAD, 1));
			list.add(new FieldInsnNode(Opcodes.GETFIELD, "net/minecraft/inventory/InventoryCrafting", FMLForgePlugin.RUNTIME_DEOBF ? "field_70465_c" : "eventHandler", "Lnet/minecraft/inventory/Container;"));
			LabelNode label2 = new LabelNode();
			list.add(new JumpInsnNode(Opcodes.IFNULL, label2));
			list.add(new VarInsnNode(Opcodes.ALOAD, 1));
			list.add(new FieldInsnNode(Opcodes.GETFIELD, "net/minecraft/inventory/InventoryCrafting", FMLForgePlugin.RUNTIME_DEOBF ? "field_70465_c" : "eventHandler", "Lnet/minecraft/inventory/Container;"));
			list.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL, "java/lang/Object", "getClass", "()Ljava/lang/Class;", false));
			list.add(new LdcInsnNode(Type.getType("Lnet/minecraft/inventory/ContainerWorkbench;")));
			list.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL, "java/lang/Object", "equals", "(Ljava/lang/Object;)Z", false));
			list.add(new JumpInsnNode(Opcodes.IFNE, label));
			list.add(label2);
			list.add(new FrameNode(Opcodes.F_SAME, 0, null, 0, null));
			list.add(new InsnNode(Opcodes.ACONST_NULL));
			list.add(new InsnNode(Opcodes.ARETURN));
			list.add(label);
			list.add(new FrameNode(Opcodes.F_SAME, 0, null, 0, null));
			/*list.add(new VarInsnNode(Opcodes.ALOAD, 0));
			list.add(new VarInsnNode(Opcodes.ALOAD, 1));
			list.add(new FieldInsnNode(Opcodes.GETFIELD, "net/minecraft/inventory/InventoryCrafting", FMLForgePlugin.RUNTIME_DEOBF ? "field_70465_c" : "eventHandler", "Lnet/minecraft/inventory/Container;"));
			list.add(new FieldInsnNode(Opcodes.PUTFIELD, "com/rwtema/extrautils/crafting/RecipeUnstableIngotCrafting", "theEventHandler", "Lnet/minecraft/inventory/Container;"));*/
			methodNode.instructions.insertBefore(insert, list);
			LogHelper.debug("Inserted if statement.");
		}
	}

	public static class MethodHandler2 implements ASMMethodHandler {
		@Override
		public MethodTuple getDesiredMethod() {
			return new MethodTuple("makeResult", "(Lnet/minecraft/item/ItemStack;Lnet/minecraft/item/ItemStack;Lnet/minecraft/item/ItemStack;)Lnet/minecraft/item/ItemStack;");
		}

		@Override
		public void patchMethod(MethodNode methodNode, ClassNode classNode, boolean obfuscated) {
			int remove = methodNode.instructions.indexOf(ASMUtil.findFirstOpcode(methodNode, Opcodes.ACONST_NULL));
			/*int remove = methodNode.instructions.indexOf(insn) - 18;
			JumpInsnNode jump = (JumpInsnNode)methodNode.instructions.get(methodNode.instructions.indexOf(insn) - 2);
			InsnList list = new InsnList();
			list.add(new VarInsnNode(Opcodes.ALOAD, 0));
			list.add(new FieldInsnNode(Opcodes.GETFIELD, "com/rwtema/extrautils/crafting/RecipeUnstableIngotCrafting", "theEventHandler", "Lnet/minecraft/inventory/Container;"));
			list.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL, "java/lang/Object", "getClass", "()Ljava/lang/Class;", false));
			list.add(new LdcInsnNode(Type.getType("Lnet/minecraft/inventory/ContainerWorkbench;")));
			list.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL, "java/lang/Object", "equals", "(Ljava/lang/Object;)Z", false));
			list.add(new JumpInsnNode(Opcodes.IFNE, jump.label));*/
			ASMUtil.deleteInstructions(methodNode, remove, 2);
			//methodNode.instructions.insertBefore(insn, list);
			LogHelper.debug("Deleted return null.");
		}
	}
}

package com.techjar.techtweaks.command;

import java.util.ArrayList;
import java.util.List;

import com.techjar.techtweaks.util.MetricUnit;

import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumChatFormatting;

public class CommandMemory implements ICommand {
	private List<String> aliases;
	
	public CommandMemory() {
		this.aliases = new ArrayList<String>();
		this.aliases.add("memory");
		this.aliases.add("mem");
	}
	
	@Override
	public int compareTo(Object arg0) {
		return 0;
	}

	@Override
	public String getCommandName() {
		return "memory";
	}

	@Override
	public String getCommandUsage(ICommandSender sender) {
		return "memory";
	}

	@Override
	public List<String> getCommandAliases() {
		return this.aliases;
	}

	@Override
	public void processCommand(ICommandSender sender, String[] args) {
		System.gc();
		Runtime runtime = Runtime.getRuntime();
		long maxMemory = runtime.maxMemory();
	    long allocatedMemory = runtime.totalMemory();
	    long freeMemory = runtime.freeMemory();
	    long totalFreeMemory = freeMemory + (maxMemory - allocatedMemory);
	    long usedMemory = maxMemory - totalFreeMemory;
	    
		sender.addChatMessage(new ChatComponentText("Memory Usage Info").setChatStyle(new ChatStyle().setColor(EnumChatFormatting.GREEN)));
		sender.addChatMessage(new ChatComponentText("Used memory: " + (int)Math.floor(((double)usedMemory / maxMemory) * 100) + "% (" + MetricUnit.NONE.convertTo(usedMemory, MetricUnit.MEBI) + "MB) of " + MetricUnit.NONE.convertTo(maxMemory, MetricUnit.MEBI) + "MB"));
		sender.addChatMessage(new ChatComponentText("Allocated memory: " + (int)Math.floor(((double)allocatedMemory / maxMemory) * 100) + "% (" + MetricUnit.NONE.convertTo(allocatedMemory, MetricUnit.MEBI) + " MB)"));
	}

	@Override
	public boolean canCommandSenderUseCommand(ICommandSender sender) {
		return sender.canCommandSenderUseCommand(4, "stop");
	}

	@Override
	public List<String> addTabCompletionOptions(ICommandSender sender, String[] args) {
		return null;
	}

	@Override
	public boolean isUsernameIndex(String[] astring, int i) {
		return false;
	}
}

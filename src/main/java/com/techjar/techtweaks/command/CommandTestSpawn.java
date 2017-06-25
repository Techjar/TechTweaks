package com.techjar.techtweaks.command;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ChunkCoordinates;

public class CommandTestSpawn implements ICommand {
	private List<String> aliases;
	
	public CommandTestSpawn() {
		this.aliases = new ArrayList<String>();
		this.aliases.add("testspawn");
	}
	
	@Override
	public int compareTo(Object arg0) {
		return 0;
	}

	@Override
	public String getCommandName() {
		return "testspawn";
	}

	@Override
	public String getCommandUsage(ICommandSender sender) {
		return "testspawn";
	}

	@Override
	public List<String> getCommandAliases() {
		return this.aliases;
	}

	@Override
	public void processCommand(ICommandSender sender, String[] args) {
		if (sender instanceof EntityPlayerMP) {
			EntityPlayerMP player = (EntityPlayerMP)sender;
			ChunkCoordinates coord = player.worldObj.provider.getRandomizedSpawnPoint();
            player.mountEntity(null);
            player.setPositionAndUpdate(coord.posX + 0.5, coord.posY, coord.posZ + 0.5);
		}
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

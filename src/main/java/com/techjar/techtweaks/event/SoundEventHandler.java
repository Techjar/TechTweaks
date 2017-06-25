package com.techjar.techtweaks.event;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.client.event.sound.SoundSetupEvent;
import paulscode.sound.SoundSystemConfig;

public class SoundEventHandler {
	@SubscribeEvent
	public void onSoundSetup(SoundSetupEvent event) {
		SoundSystemConfig.setNumberNormalChannels(256);
	}
}

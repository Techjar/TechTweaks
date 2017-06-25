package com.techjar.techtweaks.core;

import java.util.Arrays;

import com.google.common.eventbus.EventBus;

import cpw.mods.fml.common.DummyModContainer;
import cpw.mods.fml.common.LoadController;
import cpw.mods.fml.common.ModMetadata;

public class TechTweaksCore extends DummyModContainer {
	public TechTweaksCore() {
		super(new ModMetadata());
		ModMetadata meta = getMetadata();
		meta.modId = "TechTweaksCore";
		meta.name = "TechTweaksCore";
		meta.version = "@VERSION@"; //String.format("%d.%d.%d.%d", majorVersion, minorVersion, revisionVersion, buildVersion);
		meta.credits = "blah";
		meta.authorList = Arrays.asList("Techjar");
		meta.description = "";
		meta.url = "";
		meta.updateUrl = "";
		meta.screenshots = new String[0];
		meta.logoFile = "";
	}
	
	@Override
    public boolean registerBus(EventBus bus, LoadController controller) {
		bus.register(this);
		return true;
    }
}

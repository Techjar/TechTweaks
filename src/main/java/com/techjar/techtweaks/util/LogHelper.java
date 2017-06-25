package com.techjar.techtweaks.util;

import org.apache.logging.log4j.Level;

import cpw.mods.fml.relauncher.FMLRelaunchLog;

public class LogHelper {
	public static void info(String message, Object... data) {
		FMLRelaunchLog.log("TechTweaks", Level.INFO, message, data);
	}

	public static void warning(String message, Object... data) {
		FMLRelaunchLog.log("TechTweaks", Level.WARN, message, data);
	}

	public static void severe(String message, Object... data) {
		FMLRelaunchLog.log("TechTweaks", Level.ERROR, message, data);
	}

	public static void debug(String message, Object... data) {
		FMLRelaunchLog.log("TechTweaks", Level.DEBUG, message, data);
	}
}
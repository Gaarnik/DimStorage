package com.gaarnik.dimstorage.util;

import org.apache.logging.log4j.Level;

import com.gaarnik.dimstorage.DimStorage;

import cpw.mods.fml.common.FMLLog;

public class LogUtils {
	// *******************************************************************

	// *******************************************************************
	private static String className, methodName;

	// *******************************************************************
	public static void in(String className, String methodName) {
		LogUtils.className = className;
		LogUtils.methodName = methodName;
		
		logMessage(">> in " + className + "->" + methodName);
	}

	public static void out() {
		logMessage("<< out " + className + ":" + methodName);
	}

	// *******************************************************************
	public static void log(String message) {
		logMessage(message);
	}

	// *******************************************************************
	private static void logMessage(String message) {
		FMLLog.log(DimStorage.MODID, Level.INFO, message, new Object[] {});
	}

}

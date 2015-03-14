package com.jofkos.utils;

import org.bukkit.Bukkit;

public class NMSUtils {
	
	private static String NMSVersion = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
	
	public static Class<?> getClass(String name) {
		try {
			name = name.replaceAll("^(obc|nms)(?=\\.)", "$1." + NMSVersion);
			name = name.replace("obc", "org.bukkit.craftbukkit");
			name = name.replace("nms", "net.minecraft.server");
			return Class.forName(name);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}

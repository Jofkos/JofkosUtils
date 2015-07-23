package com.jofkos.utils;

import net.minecraft.server.v1_8_R3.MathHelper;

public class ProtocolUtils {
	
	public static int toFixedPoint(double d) {
		return MathHelper.floor(d * 32.0D);
	}
	
	public static int toPackedByte(float f) {
		return MathHelper.d(f * 256.0F / 360.0F);
	}
	
}

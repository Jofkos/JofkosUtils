package com.jofkos.utils.entities;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Map;

import com.jofkos.utils.reflect.Reflect;

import net.minecraft.server.v1_8_R3.Entity;
import net.minecraft.server.v1_8_R3.EntityTypes;

@SuppressWarnings("unchecked")
public class EntityUtils {
	
	private static Map<Class<? extends Entity>, String> nameMap;
	private static Map<Class<? extends Entity>, Integer> idMap;
	
	static {
		try {
			for (Field field : EntityTypes.class.getDeclaredFields()) {				
				if (!Map.class.isAssignableFrom(field.getType())) continue;
				
				Type[] genericType = ((ParameterizedType) field.getGenericType()).getActualTypeArguments();
				
				if (!(genericType[0] instanceof ParameterizedType)) continue;
				
				field.setAccessible(true);
				
				if (String.class.isAssignableFrom((Class<?>) genericType[1])) {
					nameMap = Reflect.getField(field).getStatic();
				} else {
					idMap = Reflect.getField(field).getStatic();
				}
				
				if (nameMap != null && idMap != null) break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public static void registerEntity(Class<? extends Entity> customClass) {
		Class<? extends Entity> nmsClass = customClass;
		
		while (nmsClass.getPackage() != Entity.class.getPackage()) {
			nmsClass = (Class<? extends Entity>) nmsClass.getSuperclass();
		}
		
		registerEntity(nmsClass, customClass);
	}
	
	public static void registerEntity(Class<? extends Entity> nmsClass, Class<? extends Entity> customClass) {
		try {
			nameMap.put(customClass, nameMap.get(nmsClass));
			idMap.put(customClass, idMap.get(nmsClass));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
}

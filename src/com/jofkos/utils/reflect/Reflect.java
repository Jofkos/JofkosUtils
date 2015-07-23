package com.jofkos.utils.reflect;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

@SuppressWarnings("unchecked")
public class Reflect {
	
	private static final Field modifiers = getField(Field.class, "modifiers");
	
	public static Field getField(Class<?> clazz, String fieldName) {
		try {
			Field field;
			try {
				field = clazz.getDeclaredField(fieldName);
			} catch (NoSuchFieldException e) {
				field = clazz.getField(fieldName);
			}
			if (field == null) throw new NoSuchFieldException("No field '" + fieldName + "' found in '" + clazz.getCanonicalName() + "'");
			
			return setAccessible(field);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static Field setAccessible(Field field) {
		try {
			field.setAccessible(true);
			
			if (Modifier.isFinal(field.getModifiers())) {
				modifiers.set(field, field.getModifiers() & ~Modifier.FINAL);
			}
			
			return field;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static Method getMethod(Class<?> clazz, String methodName, Class<?>... args) {
		try {
			Method method;
			try {
				method = clazz.getDeclaredMethod(methodName, args);
			} catch (NoSuchMethodException e) {
				method = clazz.getMethod(methodName, args);
			}
			if (method == null) throw new NoSuchMethodException("No method '" + methodName + "' found in '" + clazz.getCanonicalName() + "'");
			method.setAccessible(true);
			
			return method;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static <T> T get(Object instance, String fieldName) {
		return get(instance, instance.getClass(), fieldName);
	}
	
	public static <T> T get(Object instance, Class<?> clazz, String fieldName) {
		return get(getField(clazz, fieldName), instance);
	}
	
	public static <T> T get(Field field, Object instance) {
		try {
			return (T) field.get(instance);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	public static void set(Object instance, String fieldName, Object value) {
		set(instance, instance.getClass(), fieldName, value);
	}
	
	public static void set(Object instance, Class<?> clazz, String fieldName, Object value) {
		set(getField(clazz, fieldName), instance, value);
	}
	
	public static void set(Field field, Object instance, Object value) {
		try {
			field.set(instance, value);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static <T> T invoke(Object instance, String methodName, Class<?>[] argTypes, Object[] args) {
		return invoke(instance, instance.getClass(), methodName, argTypes, args);
	}
	
	public static <T> T invoke(Object instance, Class<?> clazz, String methodName, Class<?>[] argTypes, Object[] args) {
		return invoke(getMethod(clazz, methodName, argTypes), instance, args);
	}
	
	public static <T> T invoke(Method method, Object instance, Object... args) {
		try {
			return (T) method.invoke(instance, args);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}

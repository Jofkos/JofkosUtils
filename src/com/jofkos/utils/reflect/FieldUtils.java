package com.jofkos.utils.reflect;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;

public class FieldUtils {
	
	public static <S, T> void transferFields(Class<S> source, Class<T> target) {
		Arrays.stream(target.getDeclaredFields()).map(Reflect::setAccessible).filter(FieldUtils::isField).forEach(field -> Reflect.set(field, (T) null, Reflect.getField(source, field.getName())));
	}
	
	private static boolean isField(Field field) {
		return Field.class.isAssignableFrom(field.getType()) && !Modifier.isTransient(field.getModifiers());
	}
	
}

package net.infernalrealms.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import net.minecraft.server.v1_9_R1.DamageSource;

public class ReflectionUtil {

	private ReflectionUtil() {}

	public static Method setIgnoreArmor;
	public static Field rewards;

	static {
		try {
			setIgnoreArmor = DamageSource.class.getDeclaredMethod("setIgnoreArmor");
			setIgnoreArmor.setAccessible(true);
		} catch (Exception e) {
			System.out.println("Error accessing setIgnoreArmor method.");
		}
	}

	public static Object getPrivateField(String fieldName, Class clazz, Object object) {
		Field field;
		Object o = null;

		try {
			field = clazz.getDeclaredField(fieldName);

			field.setAccessible(true);

			o = field.get(object);
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}

		return o;
	}

	public static void setPrivateField(String fieldName, Class clazz, Object object, Object newValue) {
		Field field;

		try {
			field = clazz.getDeclaredField(fieldName);

			field.setAccessible(true);

			field.set(object, newValue);
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}

}

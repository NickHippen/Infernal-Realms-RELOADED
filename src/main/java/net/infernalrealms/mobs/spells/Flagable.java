package net.infernalrealms.mobs.spells;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.util.Map;

import javax.annotation.Nonnull;

public interface Flagable {

	/**
	 * Takes the parsed flags and their corresponding values and applies them to their correct field
	 * @param flags the parsed flags that will be handled
	 */
	public default void handleFlags(Map<String, String> flags) {
		for (String flag : flags.keySet()) {
			try {
				handleFlag(flag, flags.get(flag));
			} catch (NumberFormatException | UnknownFlagException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Takes a field name and value and places them in the corresponding field
	 * @param flag the non-null name of the field (or alternative name) to be changed
	 * @param value the value to change the field to
	 * @return whether or not the flag was handled properly
	 * @throws UnknownFlagException if the flag was not found in the class or any super-classes
	 * @throws NumberFormatException if the value does not parse into the proper number format
	 */
	public default boolean handleFlag(@Nonnull String flag, String value) throws UnknownFlagException, NumberFormatException {
		for (Class<?> clazz = getClass(); !clazz.equals(Object.class); clazz = clazz.getSuperclass()) {
			for (Field field : clazz.getDeclaredFields()) {
				if (!field.isAnnotationPresent(AllowFlagValue.class) || !field.getName().equals(flag)
						&& !((AllowFlagValue) field.getAnnotation(AllowFlagValue.class)).value().equals(flag)) {
					continue;
				}
				field.setAccessible(true);
				Class<?> type = field.getType();
				try {
					if (type.equals(int.class)) {
						field.setInt(this, Integer.parseInt(value));
					} else if (type.equals(double.class)) {
						field.setDouble(this, Double.parseDouble(value));
					} else if (type.equals(short.class)) {
						field.setShort(this, Short.parseShort(value));
					} else if (type.equals(long.class)) {
						field.setLong(this, Long.parseLong(value));
					} else if (type.equals(float.class)) {
						field.setFloat(this, Float.parseFloat(value));
					} else if (type.equals(byte.class)) {
						field.setByte(this, Byte.parseByte(value));
					} else if (type.equals(boolean.class)) {
						field.setBoolean(this, Boolean.parseBoolean(value));
					} else if (type.equals(char.class)) {
						field.setChar(this, value.charAt(0));
					} else if (type.equals(String.class)) {
						field.set(this, value);
					} else {
						continue;
					}
					return true;
				} catch (IllegalAccessException e) {
					e.printStackTrace();
					return false;
				}
			}
		}
		throw new UnknownFlagException("Unknown flag: " + flag);
	}

	public static class UnknownFlagException extends RuntimeException {
		public UnknownFlagException(String message) {
			super(message);
		}
	}

	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.FIELD)
	public static @interface AllowFlagValue {
		public String value() default "\0";
	}

}

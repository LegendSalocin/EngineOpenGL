package de.salocin.engine.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ReflectionUtils {
	
	/**
	 * @return
	 */
	public static StackTraceElement getCaller() {
		return getCaller(3);
	}
	
	/**
	 * @return
	 */
	public static Class<?> getCallerClass() {
		try {
			return Class.forName(getCaller(4).getClassName());
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("This should never be thrown. Please send a bug report.", e);
		}
	}
	
	private static StackTraceElement getCaller(int stackTracePos) {
		StackTraceElement[] trace = Thread.currentThread().getStackTrace();
		
		if (trace.length >= stackTracePos) {
			return trace[stackTracePos];
		} else {
			return null;
		}
	}
	
	/**
	 * @param instance
	 * @param fieldName
	 * @param value
	 * @throws IllegalArgumentException
	 */
	public static void setFieldValue(Object instance, String fieldName, Object value) {
		setFieldValue(instance.getClass(), instance, fieldName, value);
	}
	
	/**
	 * @param theClass
	 * @param fieldName
	 * @param value
	 * @throws IllegalArgumentException
	 */
	public static void setStaticFieldValue(Class<?> theClass, String fieldName, Object value) {
		setFieldValue(theClass, null, fieldName, value);
	}
	
	/**
	 * @param theClass
	 * @param instance
	 * @param fieldName
	 * @param value
	 * @throws IllegalArgumentException
	 */
	public static void setFieldValue(Class<?> theClass, Object instance, String fieldName, Object value) {
		try {
			Field field = theClass.getDeclaredField(fieldName);
			field.setAccessible(true);
			field.set(instance, value);
		} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
			throw new IllegalArgumentException(e);
		}
	}
	
	/**
	 * @param instance
	 * @param methodName
	 * @param args
	 * @return
	 * @throws IllegalArgumentException
	 */
	public static Object invokeMethod(Object instance, String methodName, Object... args) {
		return invokeMethod(instance.getClass(), instance, methodName, args);
	}
	
	/**
	 * @param theClass
	 * @param methodName
	 * @param args
	 * @return
	 * @throws IllegalArgumentException
	 */
	public static Object invokeStaticMethod(Class<?> theClass, String methodName, Object... args) {
		return invokeMethod(theClass, null, methodName, args);
	}
	
	/**
	 * @param theClass
	 * @param instance
	 * @param methodName
	 * @param args
	 * @return
	 */
	public static Object invokeMethod(Class<?> theClass, Object instance, String methodName, Object... args) {
		Class<?>[] types = new Class<?>[args.length];
		for (int i = 0; i < types.length; i++) {
			types[i] = args[i].getClass();
		}
		
		try {
			Method method = theClass.getDeclaredMethod(methodName, types);
			method.setAccessible(true);
			return method.invoke(instance, args);
		} catch (IllegalArgumentException | NoSuchMethodException | SecurityException | IllegalAccessException | InvocationTargetException e) {
			throw new IllegalArgumentException(e);
		}
	}
	
	/**
	 * @param theClass
	 * @param args
	 * @return
	 * @throws IllegalArgumentException
	 */
	public static <T> T newInstance(Class<T> theClass, Object... args) {
		Class<?>[] types = new Class<?>[args.length];
		for (int i = 0; i < types.length; i++) {
			types[i] = args[i].getClass();
		}
		
		try {
			return theClass.getConstructor(types).newInstance(args);
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
			throw new IllegalArgumentException(e);
		}
	}
	
}

package com.techjar.techtweaks.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class ReflectionHelper {
	private ReflectionHelper() {
	}
	
	public static Method getMethod(Class<?> theClass, String name, Class<?>... parameterTypes) throws NoSuchMethodException, SecurityException {
		Method method = theClass.getMethod(name, parameterTypes);
		method.setAccessible(true);
		return method;
	}
	
	public static Method getMethod(String className, String name, Class<?>... parameterTypes) throws ClassNotFoundException, NoSuchMethodException, SecurityException {
		return getMethod(Class.forName(className), name, parameterTypes);
	}
	
	public static Method getDeclaredMethod(Class<?> theClass, String name, Class<?>... parameterTypes) throws NoSuchMethodException, SecurityException {
		Method method = theClass.getDeclaredMethod(name, parameterTypes);
		method.setAccessible(true);
		return method;
	}
	
	public static Method getDeclaredMethod(String className, String name, Class<?>... parameterTypes) throws ClassNotFoundException, NoSuchMethodException, SecurityException {
		return getDeclaredMethod(Class.forName(className), name, parameterTypes);
	}
	
	public static Field getField(Class<?> theClass, String name) throws NoSuchFieldException, SecurityException {
		Field field = theClass.getField(name);
		field.setAccessible(true);
		return field;
	}
	
	public static Field getField(String className, String name) throws ClassNotFoundException, NoSuchFieldException, SecurityException {
		return getField(Class.forName(className), name);
	}
	
	public static Field getDeclaredField(Class<?> theClass, String name) throws NoSuchFieldException, SecurityException {
		Field field = theClass.getDeclaredField(name);
		field.setAccessible(true);
		return field;
	}
	
	public static Field getDeclaredField(String className, String name) throws ClassNotFoundException, NoSuchFieldException, SecurityException {
		return getDeclaredField(Class.forName(className), name);
	}
	
	public static Constructor<?> getConstructor(Class<?> theClass, Class<?>... parameterTypes) throws NoSuchMethodException, SecurityException {
		Constructor<?> constructor = theClass.getConstructor(parameterTypes);
		constructor.setAccessible(true);
		return constructor;
	}
	
	public static Constructor<?> getConstructor(String className, Class<?>... parameterTypes) throws ClassNotFoundException, NoSuchMethodException, SecurityException {
		return getConstructor(Class.forName(className), parameterTypes);
	}
	
	public static Constructor<?> getDeclaredConstructor(Class<?> theClass, Class<?>... parameterTypes) throws NoSuchMethodException, SecurityException {
		Constructor<?> constructor = theClass.getDeclaredConstructor(parameterTypes);
		constructor.setAccessible(true);
		return constructor;
	}
	
	public static Constructor<?> getDeclaredConstructor(String className, Class<?>... parameterTypes) throws ClassNotFoundException, NoSuchMethodException, SecurityException {
		return getDeclaredConstructor(Class.forName(className), parameterTypes);
	}
}
 
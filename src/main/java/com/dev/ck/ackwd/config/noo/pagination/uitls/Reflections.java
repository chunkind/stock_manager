package com.dev.ck.ackwd.config.noo.pagination.uitls;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.apache.ibatis.logging.Log;
import org.apache.ibatis.logging.LogFactory;

@SuppressWarnings("PMD")
public class Reflections {

	private static final Log log = LogFactory.getLog(Reflections.class);

	public static final String CGLIB_CLASS_SEPARATOR = "$$";

	private Reflections() {
	}

	public static Object invokeGetter(Object obj, String propertyName) {
		String getterMethodName = "get" + StringUtils.capitalize(propertyName);
		return invokeMethod(obj, getterMethodName, new Class[]{}, new Object[]{});
	}

	public static void invokeSetter(Object obj, String propertyName, Object value) {
		invokeSetter(obj, propertyName, value, null);
	}

	public static void invokeSetter(Object obj, String propertyName, Object value, Class<?> propertyType) {
		Class<?> type = propertyType != null ? propertyType : value.getClass();
		String setterMethodName = "set" + StringUtils.capitalize(propertyName);
		invokeMethod(obj, setterMethodName, new Class[]{type}, new Object[]{value});
	}

	public static Object getFieldValue(final Object obj, final String fieldName) {
		Field field = getAccessibleField(obj, fieldName);

		if (field == null) {
			throw new IllegalArgumentException("Could not find field [" + fieldName + "] on target [" + obj + "]");
		}

		Object result = null;
		try {
			result = field.get(obj);
		} catch (IllegalAccessException e) {
			log.error("불가능 발생한 예외 !", e);
		}
		return result;
	}

	public static void setFieldValue(final Object obj, final String fieldName, final Object value) {
		Field field = getAccessibleField(obj, fieldName);

		if (field == null) {
			throw new IllegalArgumentException("Could not find field [" + fieldName + "] on target [" + obj + "]");
		}

		try {
			field.set(obj, value);
		} catch (IllegalAccessException e) {
			log.error("불가능 발생한 예외 !", e);
		}
	}

	public static Class<?> getUserClass(Class<?> clazz) {
		if (clazz != null && clazz.getName().contains(CGLIB_CLASS_SEPARATOR)) {
			Class<?> superClass = clazz.getSuperclass();
			if (superClass != null && !Object.class.equals(superClass)) {
				return superClass;
			}
		}
		return clazz;
	}

	public static Object isPage(Object obj, String fieldName) {

		if (obj instanceof java.util.Map) {
			java.util.Map map = (java.util.Map)obj;
			return map.get(fieldName);

		} else {
			for (Class<?> superClass = obj.getClass(); superClass != Object.class; superClass = superClass.getSuperclass()) {
				try {
					return superClass.getDeclaredField(fieldName);
				} catch (NoSuchFieldException e) {
				}
			}
			return null;
		}

	}

	public static Object invokeMethod(final Object obj, final String methodName, final Class<?>[] parameterTypes,
									  final Object[] args) {
		Method method = getAccessibleMethod(obj, methodName, parameterTypes);
		if (method == null) {
			throw new IllegalArgumentException("Could not find method [" + methodName + "] on target [" + obj + "]");
		}

		try {
			return method.invoke(obj, args);
		} catch (Exception e) {
			throw convertReflectionExceptionToUnchecked(e);
		}
	}

	public static Field getAccessibleField(final Object obj, final String fieldName) {
		Validate.notNull(obj, "object can't be null");
		Validate.notBlank(fieldName, "fieldName can't be blank");
		for (Class<?> superClass = obj.getClass(); superClass != Object.class; superClass = superClass.getSuperclass()) {
			try {
				Field field = superClass.getDeclaredField(fieldName);
				field.setAccessible(true);
				return field;
			} catch (NoSuchFieldException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	public static Method getAccessibleMethod(final Object obj, final String methodName, final Class<?>... parameterTypes) {
		Validate.notNull(obj, "object can't be null");

		for (Class<?> superClass = obj.getClass(); superClass != Object.class; superClass = superClass.getSuperclass()) {
			try {
				Method method = superClass.getDeclaredMethod(methodName, parameterTypes);

				method.setAccessible(true);

				return method;

			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	public static <T> Class<T> getSuperClassGenricType(final Class clazz) {
		return getSuperClassGenricType(clazz, 0);
	}

	public static Class getSuperClassGenricType(final Class clazz, final int index) {
		Type genType = clazz.getGenericSuperclass();

		if (!(genType.getClass().isAssignableFrom(ParameterizedType.class))) {
			log.warn(clazz.getSimpleName() + "'s superclass not ParameterizedType");
			return Object.class;
		}

		Type[] params = ((ParameterizedType) genType).getActualTypeArguments();

		if (index >= params.length || index < 0) {
			log.warn("Index: " + index + ", Size of " + clazz.getSimpleName() + "'s Parameterized Type: "
					+ params.length);
			return Object.class;
		}
		if (!(params[index].getClass().isAssignableFrom(Class.class))) {
			log.warn(clazz.getSimpleName() + " not set the actual class on superclass generic parameter");
			return Object.class;
		}

		return (Class) params[index];
	}

	public static Object instance(String className) {
		try {
			Class dialectCls = Class.forName(className);
			return dialectCls.newInstance();
		} catch (ClassNotFoundException e) {
			log.error("방언 클래스를 찾을 수 없습니다", e);
			return null;
		} catch (InstantiationException e) {
			log.error("방언 에러 의 예로", e);
			return null;
		} catch (IllegalAccessException e) {
			log.error("방언 에러 의 예로", e);
			return null;
		}
	}

	public static RuntimeException convertReflectionExceptionToUnchecked(Exception e) {
		if (e instanceof IllegalAccessException || e instanceof IllegalArgumentException
				|| e instanceof NoSuchMethodException) {
			return new IllegalArgumentException(e);
		} else if (e instanceof InvocationTargetException) {
			return new RuntimeException(((InvocationTargetException) e).getTargetException());
		} else if (e instanceof RuntimeException) {
			return (RuntimeException) e;
		}
		return new RuntimeException("Unexpected Checked Exception.", e);
	}
}

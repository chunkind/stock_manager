package com.dev.ck.ackwd.utils;

import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

public class ReflectionUtil {

	/**
	 * @Auth: K. J. S.
	 * @Date: 2023. 08. 07.
	 * 메서드리스트 가져오기
	 */
	public static Method[] getMethods(String path) {
		Class cls = null;
		Method[] methods = null;
		try {
			cls = Class.forName(path);
			methods = cls.getDeclaredMethods();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return methods;
	}
	
	/**
	 * @Auth: K. J. S.
	 * @Date: 2023. 08. 07.
	 * 생성자리스트 가져오기
	 */
	public static Constructor[] getConstructors(String path) {
		Class cls = null;
		Constructor[] ctorlist = null;
		try {
			cls = Class.forName(path);
			ctorlist = cls.getDeclaredConstructors();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return ctorlist;
	}
	
	/**
	 * @Auth: K. J. S.
	 * @Date: 2023. 08. 07.
	 * 변수 리스트 가져오기
	 */
	public static Field[] getFields(String path) {
		Class cls = null;
		Field[] fieldlist = null;
		try {
			cls = Class.forName(path);
			fieldlist = cls.getDeclaredFields();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return fieldlist;
	}
	
	/**
	 * @Auth: K. J. S.
	 * @Date: 2023. 08. 07.
	 * 변수 이름 가져오기.
	 */
	public static String[] getFieldNames(Class<?> type) {
		Field[] fieldArr = getFields(getPathByClass(type));
		String[] result = new String[fieldArr.length];
		for (int i=0; i<result.length; i++) {
			result[i] = fieldArr[i].getName(); 
		}
		return result;
	}
	
	/**
	 * @Auth: K. J. S.
	 * @Date: 2023. 08. 07.
	 * Object -> Map
	 */
	public static Map<String, Object> getFields(Object obj){
		Map<String, Object> result = new HashMap<String, Object>();
		Field[] declaredFields = obj.getClass().getDeclaredFields();
		for (Field field : declaredFields) {
			String fieldName = field.getName();
			Object value = null;
			// private Field일 경우 접근을 허용한다.
			field.setAccessible(true);
			try {
				value = field.get(obj);
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
			result.put(fieldName, value);
		}
		return result;
	}
	
	/**
	 * @Auth: K. J. S.
	 * @Date: 2023. 08. 07.
	 * 변수 타입 가져오기.
	 */
	public static String[] getFieldTypes(Class<?> type) {
		Field[] fieldArr = getFields(getPathByClass(type));
		String[] result = new String[fieldArr.length];
		for (int i=0; i<result.length; i++) {
			result[i] = fieldArr[i].getType().getSimpleName();
			System.out.println(result[i]);
		}
		return result;
	}
	
	public static String getPathByClass(Class<?> type) {
		String result = "";
		String path = type.toString();
		String[] sliceStrArr = path.split(" ");
		if(sliceStrArr.length > 1) {
			result = sliceStrArr[1].toString();
		}
		return result;
	}
	
	public static void printMethodsInfo(String path) {
		Method methlist[] = getMethods(path);
		for (int i = 0; i < methlist.length; i++) {
			Method m = methlist[i];
			System.out.println("name  = " + m.getName());
			System.out.println("decl class = " + m.getDeclaringClass());
			Class pvec[] = m.getParameterTypes();
			for (int j = 0; j < pvec.length; j++)
				System.out.println(" param #" + j + " " + pvec[j]);
			Class evec[] = m.getExceptionTypes();
			for (int j = 0; j < evec.length; j++)
				System.out.println("exc #" + j + " " + evec[j]);
			System.out.println("return type = " + m.getReturnType());
			System.out.println("-----");
		}
	}
	
	public static void prinntConstructorsInfo(String path) {
		Constructor ctorlist[] = getConstructors(path);
		for (int i = 0; i < ctorlist.length; i++) {
			Constructor ct = ctorlist[i];
			System.out.println("name = " + ct.getName());
			System.out.println("decl class = " + ct.getDeclaringClass());
			Class pvec[] = ct.getParameterTypes();
			for (int j = 0; j < pvec.length; j++)
				System.out.println("param #" + j + " " + pvec[j]);
			Class evec[] = ct.getExceptionTypes();
			for (int j = 0; j < evec.length; j++)
				System.out.println("exc #" + j + " " + evec[j]);
			System.out.println("-----");
		}
	}
	
	public static void printFieldsInfo(String path) {
		Field[] fieldlist = getFields(path);
		for (int i = 0; i < fieldlist.length; i++) {
			Field fld = fieldlist[i];
			System.out.println("name = " + fld.getName());
			System.out.println("decl class = " + fld.getDeclaringClass());
			System.out.println("type = " + fld.getType());
			int mod = fld.getModifiers();
			System.out.println("modifiers = " + Modifier.toString(mod));
			System.out.println("-----");
		}
	}
	
	/**
	 * @Auth: K. J. S.
	 * @Date: 2023. 08. 07.
	 * 메서드 실행
	 */
	public int add(int i, int j) {
		return i + j;
	}
	public static void excuteMethodSampleTest() {
		try {
			Class cls = Class.forName("com.dev.ck.cm.util.ReflectionUtil");
			Class partypes[] = new Class[2];
			partypes[0] = Integer.TYPE;
			partypes[1] = Integer.TYPE;
			Method meth = cls.getMethod("add", partypes);
			ReflectionUtil methobj = new ReflectionUtil();
			Object arglist[] = new Object[2];
			arglist[0] = new Integer(37);
			arglist[1] = new Integer(47);
			Object retobj = meth.invoke(methobj, arglist);
			Integer retval = (Integer) retobj;
			System.out.println(retval.intValue());
		} catch (Throwable e) {
			System.err.println(e);
		}
	}
	
	/**
	 * @Auth: K. J. S.
	 * @Date: 2023. 08. 07.
	 * 객체만들기
	 */
	public static void crateInstanceTest() {
		try {
			Class cls = Class.forName("com.dev.ck.cm.util.ReflectionUtil");
			Class partypes[] = new Class[2];
			partypes[0] = Integer.TYPE;
			partypes[1] = Integer.TYPE;
			Constructor ct = cls.getConstructor(partypes);
			Object arglist[] = new Object[2];
			arglist[0] = new Integer(37);
			arglist[1] = new Integer(47);
			Object retobj = ct.newInstance(arglist);
		} catch (Throwable e) {
			System.err.println(e);
		}
	}
	
	/**
	 * @Auth: K. J. S.
	 * @Date: 2023. 08. 07.
	 * 필드값 바꾸기
	 */
	public static void fieldValueChgTest() {
		try {
			Class cls = Class.forName("com.dev.ck.cm.util.ReflectionUtil");
			Field fld = cls.getField("d");
			ReflectionUtil f2obj = new ReflectionUtil();
//			System.out.println("d = " + f2obj.d);
			fld.setDouble(f2obj, 12.34);
//			System.out.println("d = " + f2obj.d);
		} catch (Throwable e) {
			System.err.println(e);
		}
	}
	
	/**
	 * @Auth: K. J. S.
	 * @Date: 2023. 08. 07.
	 * 배열 테스트
	 */
	public static void arrayTest() {
		try {
			Class cls = Class.forName("java.lang.String");
			Object arr = Array.newInstance(cls, 10);
			Array.set(arr, 5, "this is a test");
			String s = (String) Array.get(arr, 5);
			System.out.println(s);
		} catch (Throwable e) {
			System.err.println(e);
		}
	}
	
	/**
	 * @Auth: K. J. S.
	 * @Date: 2023. 08. 07.
	 * 배열 테스트2
	 */
	public static void arrayTest2() {
		int dims[] = new int[] { 5, 10, 15 };
		Object arr = Array.newInstance(Integer.TYPE, dims);
		Object arrobj = Array.get(arr, 3);
		Class cls = arrobj.getClass().getComponentType();
		System.out.println(cls);
		arrobj = Array.get(arrobj, 5);
		Array.setInt(arrobj, 10, 37);
		int arrcast[][][] = (int[][][]) arr;
		System.out.println(arrcast[3][5][10]);
	}
}

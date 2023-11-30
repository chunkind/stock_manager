package com.dev.ck.ackwd.config.noo.pagination.uitls;

public class ClassUtils {
	public static boolean equalClass(Class cls,Class target){
		return cls.isAssignableFrom(target);
	}

	public static boolean equalObject(Object obj,Class target){
		return equalClass(obj.getClass(),target);
	}
}
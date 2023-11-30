package com.dev.ck.ackwd.config.interceptors.programidcontext;

public class ProgramIdContextHolder {
	static ThreadLocal<String> localValue = new ThreadLocal<String>();

	public static void setProgramId(String value) {
		localValue.set(value != null ? value : "UNKNOWN");
	}

	public static String getProgramId() {
		return localValue.get() == null ? "UNKNOWN" : localValue.get();
	}

}
package com.dev.ck.ackwd.config.security.context;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

public class ProjectSecurityContextHolder {
	public static boolean isTest = false;
	static ThreadLocal<Map<String, String>> data = new ThreadLocal<>();
	public static Map<String, String> testData = new HashMap<>();
	public static Map<String, String> emptyData = Collections.unmodifiableMap(new HashMap<String, String>());

	static {
		testData.put("sessionId", ""); // sessionId
	}

	public static String getSessionId() {
		return getDataSet().get("sessionId");
	}

	public static String getLoginUserId() {
		return getDataSet().get("loginUserId");
	}

	public static String getLoginUserName() {
		return getDataSet().get("loginUserName");
	}

	public static Map<String, String> getDataSet() {
		if (isTest) {
			return testData;
		}
		return data.get() == null ? emptyData : data.get();
	}

	public static void setup(Map<String, String> dataSet) {
		data.set(dataSet);
	}

	public static void clean() {
		data.remove();
	}

	public static String getCurrentUserId() {
		return getLoginUserId();
	}

	public static String getCurrentUserName() {
		return getLoginUserName();
	}

	public static String getLoginType() {
		return getDataSet().get("loginType");
	}
}
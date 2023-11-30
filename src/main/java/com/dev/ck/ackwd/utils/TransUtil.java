package com.dev.ck.ackwd.utils;

import java.io.IOException;
import java.util.Map;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class TransUtil {
	/**
	 * @Auth : K. J. S.
	 * @Date : 2020. 5. 4.
	 * java Object를 String으로 변환.
	 */
	public static String objToStr(Object obj) {
		String result="";
		try {
			ObjectMapper mapper = new ObjectMapper();
			result = mapper.writeValueAsString(obj);
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * @Auth : K. J. S.
	 * @Date : 2020. 5. 4.
	 * 문자열을 JSONObject로 변환.
	 */
	public static JSONObject strToObj(String str) {
		JSONParser parser = new JSONParser();
		Object obj = null;

		// 따옴표 변환 필요
		str = str.replace("'", "\"");

		try {
			obj = parser.parse(str);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return (JSONObject) obj;
	}
	
	/**
	 * @Auth : K. J. S.
	 * @Date : 2023. 08. 08.
	 * 객체를 JSON객체로 변환, 빈값 포함.
	 */
	public static JSONObject objToJSONObject(Object obj) {
		return objToJSONObject(obj, false);
	}
	
	/**
	 * @Auth : K. J. S.
	 * @Date : 2023. 08. 08.
	 * 객체를 JSON객체로 변환, 빈값일때는 제거
	 */
	public static JSONObject objToNotNullJSONObject(Object obj) {
		return objToJSONObject(obj, true);
	}
	
	public static JSONObject objToJSONObject(Object obj, boolean notNullFlag) {
		JSONObject result = new JSONObject();
		Map<String, Object> fieldNameArr = ReflectionUtil.getFields(obj);
		
		String key = "";
		Object value = null;
		for (Map.Entry<String, Object> entry : fieldNameArr.entrySet()) {
			key = entry.getKey();
			value = entry.getValue();
			if(notNullFlag == true) {
				if(Util.isNotNull(value))
					result.put(key, value);
			}else {
				result.put(key, value);
			}
		}
		return result;
	}
	
}

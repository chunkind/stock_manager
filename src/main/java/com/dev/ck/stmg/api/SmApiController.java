package com.dev.ck.stmg.api;

import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.dev.ck.ackwd.utils.SendUtil;
import com.dev.ck.ackwd.utils.connector.Api_Client;

@RestController
public class SmApiController {

	@PostMapping("/api/bit.do")
	public JSONObject infoBalance(@RequestBody Map<String, String> map) throws ParseException {
		JSONObject result = null;
		String strResult = "";
		try {
			String url = (String) map.get("url");
			map.remove("url");
			
			Api_Client api = new Api_Client("d05283d80dbcbc4ce799eb58ce36f722", "03c43223f6b59ceca21d5d1114605ad1");
			
			strResult = api.callApi(url, (HashMap<String, String>) map);
			
			result = getJson(strResult);
			result.put("message", "Success");
		}catch(Exception e) {
			result = new JSONObject();
			result.put("errorMsg", e.getMessage());
			result.put("message", "Fail");
			e.printStackTrace();
		}
		
		return result;
	}
	
	@PostMapping("/api/trans.do")
	public JSONObject trans(@RequestBody JSONObject params) throws ParseException {
		JSONObject result = null;
		String strResult = "";
		try {
			String url = (String) params.get("url");
			String method = (String) params.get("method");
			Map<String, String> header = (Map<String, String>) params.get("header");
			Map<String, String> body = (Map<String, String>) params.get("body");
			params.remove("url");
			params.remove("method");
			if("GET".equals(method.toUpperCase())) {
				strResult = SendUtil.sendHttpsGet(url, header, body);
			}
			else if("POST".equals(method.toUpperCase())) {
				strResult = SendUtil.sendHttpsPost(url, header, body);
			}
			result = getJson(strResult);
			result.put("message", "Success");
		}catch(Exception e) {
			result = new JSONObject();
			result.put("errorMsg", e.getMessage());
			result.put("message", "Fail");
			e.printStackTrace();
		}
		return result;
	}
	
	public JSONObject getJson(String str) {
		JSONParser jsonParser = new JSONParser();
		Object obj = null;
		JSONObject jsonObj = null;
		try {
			obj = jsonParser.parse(str);
			jsonObj = (JSONObject) obj;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return jsonObj;
	}
}

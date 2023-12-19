package com.dev.ck.stmg.api;

import java.util.Map;

import org.json.simple.JSONObject;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

@RestController
public class SmApiController {

	@PostMapping("/info/balance")
	public JSONObject infoBalance(@RequestBody Map<String, Object> map) {
		JSONObject result = null;
		
		ObjectMapper mapper = new ObjectMapper();
		SmApiDto pvo = mapper.convertValue(map, SmApiDto.class);
		SmApiDto rvo = new SmApiDto();
		
		
		String jsonStr = new Gson().toJson(rvo);
		System.out.println(jsonStr);
		return result;
	}
}

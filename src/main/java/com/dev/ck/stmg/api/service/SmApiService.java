package com.dev.ck.stmg.api.service;

import java.util.Map;

import com.dev.ck.stmg.api.SmApiDto;

public interface SmApiService {
	Map<String, Object> httpsPost(String url, Map<String, Object> header, Map<String, Object> param);
//	Map<String, Object> getBitumbHeader
	SmApiDto getInfoBalance(SmApiDto pvo);
}

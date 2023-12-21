package com.dev.ck.api.service;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.dev.ck.TestApp;
import com.dev.ck.stmg.api.service.SmApiService;

public class SmApiServiceImplTest extends TestApp{
	@Autowired private SmApiService apiService;
	
	@Test
	public void 계좌조회() {
		Map<String, Object> header = new HashMap<>();
		Map<String, Object> params = new HashMap<>();
		String url = "https://api.bithumb.com";
		
		Map<String, Object> resultMap
			= apiService.httpsPost(url, header, params);
	}
}

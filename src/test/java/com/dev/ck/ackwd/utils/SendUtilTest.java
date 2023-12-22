package com.dev.ck.ackwd.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Value;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.dev.ck.TestApp;


public class SendUtilTest extends TestApp{
	@Value("${api.upbit.accessKey}") String accessKey;
	@Value("${api.upbit.secretKey}") String secretKey;
	@Value("${api.upbit.url}") String serverUrl;
	
	@Test
	public void 업비드계좌조회() {
		Algorithm algorithm = Algorithm.HMAC256(secretKey);
		String jwtToken = JWT.create()
			.withClaim("access_key", accessKey)
			.withClaim("nonce", UUID.randomUUID().toString())
			.sign(algorithm);
		
		String url = serverUrl + "/v1/accounts";
		String method = "GET";
		Map<String, String> header = new HashMap<String, String>();
		Map<String, String> body = new HashMap<String, String>();
		
		String authenticationToken = "Bearer " + jwtToken;
		header.put("Authorization", authenticationToken);
		
		String result = SendUtil.sendHttpsGet(url, header, body);
		System.out.println("result :: " + result);
	}
	
	@Test
	public void 그냥갯전송테스트() {
		String url = "http://dev-sel.skstoa.com/partner/code/commcd-list";
		Map<String, String> header = new HashMap<String, String>();
		Map<String, String> body = new HashMap<String, String>();
		body.put("linkCode", "HIMART");
		body.put("entpCode", "101291");
		body.put("entpId", "E101291");
		body.put("entpPass", "Skstoa1234");
		
		String result = SendUtil.sendHttpsGet(url, header, body);
		System.out.println("result :: " + result);
	}
}

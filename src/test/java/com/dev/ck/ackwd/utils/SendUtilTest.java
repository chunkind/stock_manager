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
		Map<String, String> param = new HashMap<String, String>();
		String authenticationToken = "Bearer " + jwtToken;
		System.out.println(authenticationToken);
		
		String result = SendUtil.sendHttpsGet(url, param, authenticationToken);
		System.out.println("result :: " + result);
	}
}

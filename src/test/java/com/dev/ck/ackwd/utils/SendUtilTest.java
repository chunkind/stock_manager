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
	public void getHttp전송() {
		String url = "https://www.googleapis.com/youtube/v3/videoCategories";
		url += "?regionCode=KR&part=snippet&key=AIzaSyDjQFtDtYzJ-pvcfOHKsVQ1HJR3AGhg-_E";
		Map<String, String> header = new HashMap<String, String>();
		Map<String, String> body = new HashMap<String, String>();
		String result = SendUtil.sendHttp(url, "GET", header, body);
		System.out.println(result);
	}
	
	@Test
	public void getHttps전송() {
		String url = "https://www.googleapis.com/youtube/v3/videoCategories";
		Map<String, String> body = new HashMap<String, String>();
		body.put("key", "AIzaSyDjQFtDtYzJ-pvcfOHKsVQ1HJR3AGhg-_E");
		body.put("part", "snippet");
		body.put("regionCode", "KR");
		
		String result = SendUtil.sendHttpsGet(url, body);
		System.out.println("result :: " + result);
		
//		SendUtil.checkCharSet(result);
	}
	
	@Test
	public void transUrl테스트() {
		String url = "https://www.googleapis.com/youtube/v3/videoCategories";
		url += "?regionCode=KR&part=snippet&key=AIzaSyDjQFtDtYzJ-pvcfOHKsVQ1HJR3AGhg-_E";
		SendUtil.transUrl(url);
	}
	
	@Test
	public void httpSendStrarg() {
		String url = "https://www.googleapis.com/youtube/v3/videoCategories";
		SendUtil.sendHttpsGet(
			url, 
			"regionCode", "KR", 
			"part","snippet", 
			"key", "AIzaSyDjQFtDtYzJ-pvcfOHKsVQ1HJR3AGhg-_E"
		);
	}
}

package com.dev.ck.ackwd.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.dev.ck.Config;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SendUtil {
	private static final int SOCKET_TIMEOUT = 10 * 1000;
	private static final int CONNECTION_TIMEOUT = 5 * 60 * 1000;
	
	// GET 요청
	public static String sendHttpGet(String sendURL, Map<String, String> body) {
		Map<String, String> header = new HashMap<String, String>();
		return sendHttpGet(sendURL, header, body);
	}
	public static String sendHttpGet(String sendURL, Map<String, String> header, Map<String, String> body) {
		return sendHttp(sendURL, "GET", header, body);
	}
	public static String sendHttpsGet(String sendURL, Map<String, String> body){
		Map<String, String> header = new HashMap<String, String>();
		return sendHttpsGet(sendURL, header, body);
	}
	public static String sendHttpsGet(String sendURL, Map<String, String> header, Map<String, String> body){
		return sendHttps(sendURL, "GET", header, body);
	}
	
	// POST 요청
	public static String sendHttpPost(String sendURL, Map<String, String> body) {
		Map<String, String> header = new HashMap<String, String>();
		return sendHttpPost(sendURL, header, body);
	}
	public static String sendHttpPost(String sendURL, Map<String, String> header, Map<String, String> body) {
		return sendHttp(sendURL, "POST", header, body);
	}
	public static String sendHttpsPost(String sendURL, Map<String, String> body) {
		Map<String, String> header = new HashMap<String, String>();
		return sendHttpsPost(sendURL, header, body);
	}
	public static String sendHttpsPost(String sendURL, Map<String, String> header, Map<String, String> body){
		return sendHttps(sendURL, "POST", header, body);
	}
	
	public static String sendHttp(String strUrl, String method, Map<String, String> header, Map<String, String> body) {
		HttpURLConnection conn = null;
		URL url = null;;
		StringBuilder sb = null;
		OutputStreamWriter wr = null;
		try {
			url = new URL(strUrl);
			sb = new StringBuilder();
			conn = (HttpURLConnection)url.openConnection();
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setConnectTimeout(CONNECTION_TIMEOUT);
			conn.setReadTimeout(SOCKET_TIMEOUT);
			conn.setRequestProperty("Content-Type", "application/json");
			conn.setRequestProperty("Accept", "application/json");
//			if(isTest) {
//				conn.setRequestProperty("Authorization", "test");
//			}
			conn.setRequestMethod(method);
			
			if("POST".equals(method)) {
				wr = new OutputStreamWriter(conn.getOutputStream());
				JSONParser parser = new JSONParser();
				JSONObject jobj = (JSONObject) parser.parse(JSONObject.toJSONString(body));
				wr.write(jobj.toString());
				wr.flush();
			}

			int responseCode = conn.getResponseCode();
			if (responseCode == 400 || responseCode == 401 || responseCode == 500 ) {
				System.out.println("error : " + responseCode);
				throw new RuntimeException("error:"+responseCode);
			} else {
				BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
				String line = "";
				while ((line = br.readLine()) != null) {
					sb.append(line);
				}
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return sb.toString();
	}
	
	public static String sendHttps(String sendURL, String method, Map<String, String> header, Map<String, String> body) {
		InputStream in = null;
		BufferedReader reader = null;
		HttpsURLConnection httpsConn = null;
		byte[] postDataBytes = null;
		String bodyStrDat = "";
		StringBuffer sb = new StringBuffer();
		try {
			bodyStrDat = mapToQueryStr(body);
			if("GET".equals(method.toUpperCase())) {
				sendURL += "?"+ bodyStrDat;
			}
			URL url = new URL(sendURL);
			//파라미터 셋팅
			SendUtil.disableSslVerification(); //인증서 에러 무시
			httpsConn = (HttpsURLConnection) url.openConnection();
//			httpsConn.setDoInput(true);
//			httpsConn.setUseCaches(false);
			httpsConn.setReadTimeout(SOCKET_TIMEOUT);
			httpsConn.setConnectTimeout(CONNECTION_TIMEOUT);
			httpsConn.setRequestMethod(method);
			httpsConn.setRequestProperty("Content-Type", "application/json");
//			httpsConn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			if("POST".equals(method.toUpperCase())) {
				postDataBytes = bodyStrDat.getBytes("UTF-8");
				httpsConn.setRequestProperty("Content-Length", String.valueOf(postDataBytes.length));
			}
			
			httpsConn.setRequestProperty("Accept", "*/*");
//			httpsConn.setRequestProperty("Accept-Encoding", "gzip, deflate, br");
//			httpsConn.setRequestProperty("charset", "utf-8");
//			httpsConn.setRequestProperty("Authorization", token);
//			if(body.containsKey("Authorization")) {
//				httpsConn.setRequestProperty("Authorization", body.get("Authorization"));
//			}
			
			//해더설정
			Iterator<String> headerKeys = header.keySet().iterator();
			while(headerKeys.hasNext()){
				String key = headerKeys.next();
				if(null != key && !"".equals(key)){
					String value = header.get(key).toString().trim();
					httpsConn.setRequestProperty(key, value);
				}
			}
			
			httpsConn.setDoOutput(true);
			
			if("POST".equals(method.toUpperCase())) {
				httpsConn.getOutputStream().write(postDataBytes);
			}

			int responseCode = httpsConn.getResponseCode();
			System.out.println("응답코드: " + responseCode);
			System.out.println("응답메세지: " + httpsConn.getResponseMessage());
			//SSL setting
//			SSLContext context = SSLContext.getInstance("TLSv1.2");
//			context.init(null, null, new SecureRandom());
//			httpsConn.setSSLSocketFactory(context.getSocketFactory());
//			httpsConn.setRequestProperty("charset", "utf-8");
			//Connect to host
			httpsConn.connect();
//			httpsConn.setInstanceFollowRedirects(true);
			//Print response from host
			if(responseCode == HttpsURLConnection.HTTP_OK){
				in = httpsConn.getInputStream();
			}else{
				in = httpsConn.getErrorStream();
			}
			reader = new BufferedReader(new InputStreamReader(in));
			String line = "";
			while((line = reader.readLine()) != null){
				sb.append(line);
			}
			System.out.println("=Send Start=================================================================");
			System.out.println("params:"+bodyStrDat.toString());
			System.out.println("returns:"+sb.toString());
			System.out.println("=Send End =================================================================");
			reader.close();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			if(reader != null){
				try{
					reader.close();
				} catch (IOException e){
					e.printStackTrace();
				}
			}
			if(httpsConn != null){
				httpsConn.disconnect();
			}
		}
		return sb.toString();
	}
	
	public static void transUrl(String strUrl){
		URL url = null;
		try {
			url = new URL(strUrl);
			BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"));
			StringBuffer sb = new StringBuffer();
			String tempStr = null;
			while (true) {
				tempStr = br.readLine();
				if (tempStr == null)
					break;
				sb.append(tempStr);
			}
			System.out.println(sb.toString());
			br.close();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	public static String get(String apiUrl, Map<String, String> requestHeaders){
		HttpURLConnection con = connect(apiUrl);
		try {
			con.setRequestMethod("GET");
			for(Map.Entry<String, String> header :requestHeaders.entrySet()) {
				con.setRequestProperty(header.getKey(), header.getValue());
			}
			int responseCode = con.getResponseCode();
			if (responseCode == HttpURLConnection.HTTP_OK) { // 정상 호출
				return readBody(con.getInputStream());
			} else { // 에러 발생
				return readBody(con.getErrorStream());
			}
		} catch (IOException e) {
			throw new RuntimeException("API 요청과 응답 실패", e);
		} finally {
			con.disconnect();
		}
	}

	private static HttpURLConnection connect(String apiUrl){
		try {
			URL url = new URL(apiUrl);
			return (HttpURLConnection)url.openConnection();
		} catch (MalformedURLException e) {
			throw new RuntimeException("API URL이 잘못되었습니다. : " + apiUrl, e);
		} catch (IOException e) {
			throw new RuntimeException("연결이 실패했습니다. : " + apiUrl, e);
		}
	}

	private static String readBody(InputStream body){
		InputStreamReader streamReader = new InputStreamReader(body);

		try (BufferedReader lineReader = new BufferedReader(streamReader)) {
			StringBuilder responseBody = new StringBuilder();

			String line;
			while ((line = lineReader.readLine()) != null) {
				responseBody.append(line);
			}

			return responseBody.toString();
		} catch (IOException e) {
			throw new RuntimeException("API 응답을 읽는데 실패했습니다.", e);
		}
	}
	
	/**
	 * @Auth: K. J. S.
	 * @Date: 2023. 12. 24.
	 * 캐릭터셋 확인
	 */
	public static void checkCharSet(String originalStr) {
		byte[] bytes;
		try {
			String[] charSet = {
				"utf-8", "euc-kr", "ksc5601", "iso-8859-1", "x-windows-949"
				,"US-ASCII", "ISO-8859-1", "UTF-8", "UTF-16BE", "UTF-16LE", "UTF-16", "EUC-KR", "MS949"
			};
			String result = "";
			for(int a=0; a<charSet.length; a++) {
				bytes = originalStr.getBytes(charSet[a]);
				result = new String(bytes);
				System.out.println(charSet[a] + "::" + result);
//				for(int i = 0; i<charSet.length; i++){
//					for(int j = 0; j<charSet.length; j++){
//						try{ 
//							System.out.println("[" + charSet[a] + "," + charSet[i] + "," + charSet[j] + "]" + new String(originalStr.getBytes(charSet[i]), charSet[j]));
//						} catch (UnsupportedEncodingException e){
//							e.printStackTrace();
//						}
//					}
//				}
			}
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
	}
	
	/**
	 * @Auth: K. J. S.
	 * @Date: 2023. 12. 24.
	 * Map을 쿼리스트링으로 변환.
	 */
	public static String mapToQueryStr(Map<String, String> map) {
		Iterator<String> iterKeys = map.keySet().iterator();
		StringBuilder postData = new StringBuilder();
		boolean isFirst = true;
		try {
			while(iterKeys.hasNext()){
				String key = iterKeys.next();
				if(null != map.get(key) && !"".equals(map.get(key))){
					String value = map.get(key).toString().trim();
					if(isFirst) {
						isFirst = false;
					}else {
						postData.append("&");
					}
					postData.append(URLEncoder.encode(key, "UTF-8"));
					postData.append('=');
					postData.append(URLEncoder.encode(value, "UTF-8"));
				}else{
					if(isFirst) {
						isFirst = false;
					}else {
						postData.append("&");
					}
					postData.append(key+"=");
				}
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return postData.toString();
	}
	
	/**
	 * @Date: 2022. 09. 19.
	 * @Auth: K. J. S.
	 * URL 파라미터 맵을 스트링으로 변환.
	 */
	public String transParamMapToStr(Map<String, Object> param) {
		String returnStr = "";
		try {
			Iterator<String> iterKeys = param.keySet().iterator();
			StringBuilder postData = new StringBuilder();
			while(iterKeys.hasNext()){
				String key = iterKeys.next();
				if(null != param.get(key) && !"".equals(param.get(key))){
					String value = param.get(key).toString().trim();
					postData.append("&");
					postData.append(URLEncoder.encode(key, "UTF-8"));
					postData.append('=');
					postData.append(URLEncoder.encode(value, "UTF-8"));
				}else{
					postData.append("&"+key+"=");
				}
			}
			returnStr = postData.toString();
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
		return returnStr;
	}
	
	/**
	 * @Auth: K. J. S.
	 * @Date: 2023. 11. 30.
	 * SSL인증서를 우회하는 코드
	 */
	public static void disableSslVerification() {
		try {
			// ============================================
			// trust manager 생성(인증서 체크 전부 안함)
			TrustManager[] trustAllCerts =new TrustManager[] {new X509TrustManager() {
				@Override
				public X509Certificate[] getAcceptedIssuers() {
					return null;
				}
				@Override
				public void checkClientTrusted(java.security.cert.X509Certificate[] arg0, String arg1)
						throws CertificateException {
				}
				@Override
				public void checkServerTrusted(java.security.cert.X509Certificate[] arg0, String arg1)
						throws CertificateException {
				}
			}};

			// trust manager 설치
			SSLContext sc = SSLContext.getInstance("SSL");
			sc.init(null, trustAllCerts, new java.security.SecureRandom());
			HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

			// ============================================
			// host name verifier 생성(호스트 네임 체크안함)
			HostnameVerifier allHostsValid = (hostname, session) -> true;

			// host name verifier 설치
			HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
		} catch (NoSuchAlgorithmException | KeyManagementException e) {
			e.printStackTrace();
		}
	}
}
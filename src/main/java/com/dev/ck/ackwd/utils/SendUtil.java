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
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.TimeZone;

import javax.net.ssl.HttpsURLConnection;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import com.dev.ck.Config;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component("sendUtil")
public class SendUtil {
	private static final String URL = Config.data.getProperty("api.you.url");
	private static final String KEY = Config.data.getProperty("api.you.key");
	private static final int SOCKET_TIMEOUT = 10 * 1000;
	private static final int CONNECTION_TIMEOUT = 5 * 60 * 1000;
	
	private static final String NF_TOPTEN_URL = Config.data.getProperty("api.netflix.toptenUrl");
	private static final String NF_TITLE_URL = Config.data.getProperty("api.netflix.titleUrl");
	
	public static String sendHttpsPost(String sendURL, Map<String, String> param){
		InputStream in = null;
		BufferedReader reader = null;
		HttpsURLConnection httpsConn = null;
		StringBuffer sb = new StringBuffer();
		try {
			URL url = new URL(sendURL);
			//파라미터 셋팅
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
			byte[] postDataBytes = postData.toString().getBytes("UTF-8");
			httpsConn = (HttpsURLConnection) url.openConnection();
//			httpsConn.setDoInput(true);
//			httpsConn.setUseCaches(false);
			httpsConn.setReadTimeout(SOCKET_TIMEOUT);
			httpsConn.setConnectTimeout(CONNECTION_TIMEOUT);
			httpsConn.setRequestMethod("POST");
//			httpsConn.setRequestProperty("Content-Type", "application/json");
			httpsConn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			httpsConn.setRequestProperty("Content-Length", String.valueOf(postDataBytes.length));
			httpsConn.setDoOutput(true);
			httpsConn.getOutputStream().write(postDataBytes);

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
			System.out.println("params:"+postData.toString());
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
	
	public static JSONObject sendHttpGet(String strUrl) {
		JSONObject json = new JSONObject();
		System.out.println("url : " + strUrl);
		try {
			JSONParser parser = new JSONParser();
			Object obj = parser.parse(SendUtil.sendHttp(strUrl, "GET", null, false));
			json = (JSONObject)obj;
			//org.json.JSONObject ver..
			//json = new JSONObject(sb.toString());
		} catch (Exception e) {
			json.put(Config.RESULT_CD, "F");
			json.put(Config.RESULT_MSG, e.getMessage());
			e.printStackTrace();
		}
		return json;
	}
	public static JSONObject sendHttpGetTest(String strUrl) {
		JSONObject json = new JSONObject();
		System.out.println("url : " + strUrl);
		try {
			JSONParser parser = new JSONParser();
			Object obj = parser.parse(SendUtil.sendHttp(strUrl, "GET", null, true));
			json = (JSONObject)obj;
		} catch (Exception e) {
			json.put(Config.RESULT_CD, "F");
			json.put(Config.RESULT_MSG, e.getMessage());
			e.printStackTrace();
		}
		return json;
	}
	public static JSONObject sendHttpPost(String strUrl, Map<String, String> params) {
		JSONObject json = new JSONObject();
		System.out.println("url : " + strUrl);
		try {
			JSONParser parser = new JSONParser();
			Object obj = parser.parse(SendUtil.sendHttp(strUrl, "POST", params, false));
			json = (JSONObject)obj;
			//org.json.JSONObject ver..
			//json = new JSONObject(sb.toString());
		} catch (Exception e) {
			json.put(Config.RESULT_CD, "F");
			json.put(Config.RESULT_MSG, e.getMessage());
			e.printStackTrace();
		}
		return json;
	}
	public static JSONObject sendHttpPostTest(String strUrl, Map<String, String> params) {
		JSONObject json = new JSONObject();
		System.out.println("url : " + strUrl);
		try {
			JSONParser parser = new JSONParser();
			Object obj = parser.parse(SendUtil.sendHttp(strUrl, "POST", params, true));
			json = (JSONObject)obj;
		} catch (Exception e) {
			json.put(Config.RESULT_CD, "F");
			json.put(Config.RESULT_MSG, e.getMessage());
			e.printStackTrace();
		}
		return json;
	}
	
	public static String sendHttp(String strUrl, String method, Map<String, String> params, boolean isTest) {
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
			if(isTest) {
				conn.setRequestProperty("Authorization", "test");
			}
			conn.setRequestMethod(method);
			
			if("POST".equals(method)) {
				wr = new OutputStreamWriter(conn.getOutputStream());
				JSONParser parser = new JSONParser();
				JSONObject jobj = (JSONObject) parser.parse(JSONObject.toJSONString(params));
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
	
	
	public static void testDate() {
		// Input
		Date date = new Date(System.currentTimeMillis());
		//Date date2 = new Date(System.currentTimeMillis()-(1000*60*60*24*30));

		// Conversion
		SimpleDateFormat sdf;
		
		sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
		sdf.setTimeZone(TimeZone.getTimeZone("Asia/Seoul"));
		System.out.println(sdf.format(date));
		
		Calendar cal = Calendar.getInstance();
		//cal.add(Calendar.DATE  , -7);
		//cal.add(Calendar.MONTH , -1);
		cal.add(Calendar.YEAR , -1);
		String beforeMonth = new java.text.SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").format(cal.getTime());
		System.out.println(beforeMonth);

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
	
	
	private static String getWeek(String dt) {
		System.out.println("week ----------------->> " + dt);
		String[] dtArr = dt.split(" ");
		String week = "";
		String dd = "";
		if(dtArr.length == 6) {
			week = dtArr[5];
			if("Jan".equals(dtArr[3])){
				week += "01";
			}else if("Feb".equals(dtArr[3])){
				week += "02";
			}else if("Mar".equals(dtArr[3])){
				week += "03";
			}else if("Apr".equals(dtArr[3])){
				week += "04";
			}else if("May".equals(dtArr[3])){
				week += "05";
			}else if("Jun".equals(dtArr[3])){
				week += "06";
			}else if("Jul".equals(dtArr[3])){
				week += "07";
			}else if("Aug".equals(dtArr[3])){
				week += "08";
			}else if("Sep".equals(dtArr[3])){
				week += "09";
			}else if("Oct".equals(dtArr[3])){
				week += "10";
			}else if("Nov".equals(dtArr[3])){
				week += "11";
			}else if("Dec".equals(dtArr[3])){
				week += "12";
			}
			dd = dtArr[4].replaceAll(",", "");
			week += dd.length() == 1 ? "0" + dd : dd; //숫자한자리일 경우
		}
		
		return week;
	}

	/**
	 * @Date: 2022. 09. 19.
	 * @Auth: K. J. S..
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

}
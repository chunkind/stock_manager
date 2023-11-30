package com.dev.ck.ackwd.utils;


import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.servlet.http.HttpServletRequest;

public class Util {
	public static String getClientIP(HttpServletRequest request) {
		String ip = request.getHeader("X-FORWARDED-FOR"); 
		if (ip == null || ip.length() == 0) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0) {
			ip = request.getHeader("WL-Proxy-Client-IP");  // 웹로직
		}
		if (ip == null || ip.length() == 0) {
			ip = request.getHeader("HTTP_CLIENT_IP");
		}
		if (ip == null || ip.length() == 0) {
			ip = request.getHeader("HTTP_X_FORWARDED_FOR");
		}
		if (ip == null || ip.length() == 0) {
			ip = request.getRemoteAddr() ;
		}
		return ip;
	}
	
	/**
	 * @Auth : K. J. S.
	 * @Date : 2023. 08. 07.
	 * Null 체크
	 */
	public static boolean isNull(Object obj) {
		boolean result =false;
		
		if (obj instanceof String) {
			if("".equals((String) obj))
				result = true;
		} else {
			if(null == obj)
				result = true;
		}
		return result;
	}
	
	/**
	 * @Auth : K. J. S.
	 * @Date : 2023. 08. 07.
	 * NotNull 체크
	 */
	public static boolean isNotNull(Object obj) {
		return !isNull(obj);
	}
	
	/**
	 * @Auth : K. J. S.
	 * @Date : 2023. 08. 09.
	 * 사용가능한 사업자 번호 추출
	 */
	public static void printPasBrno(int size) {
		int printCnt = 0;
		firstLoop: for(int i = 101; i<=999; i++) { //첫 3자리
			for(int j=1; j<=99; j++) { // 개인법인구분코드 2자리
				for(int k=1; k<=9999; k++) { //일련번호코드 4자리
					for(int l=0; l<=9; l++) { //검증코드 1자리
						String code = lpad(i, 3) + lpad(j, 2) + lpad(k, 4) + lpad(l, 1);
						if(checkBrno(code)) {
							printCnt++;
							if(printCnt >= size) break firstLoop;
						}
					}
				}
			}
		}
	}

	/**
	 * @Auth : K. J. S.
	 * @Date : 2023. 08. 09.
	 * LPad
	 */
	public static String lpad(int target, int size) {
		int length = String.valueOf(target).length();
		if(length > size) {
			System.out.println("변환하려는 자리수가 실제 데이터의 크기보다 작습니다.");
			return String.valueOf(target);
		}
		String result = "";
		for(int i=1; i<=size-length; i++) {
			result += "0";
		}
		result += target;
		return result;
	}
	
	/**
	 * @Auth : K. J. S.
	 * @Date : 2023. 08. 09.
	 * 사업자 번호 체크
	 */
	public static boolean checkBrno(String BRNO) {
		boolean result = false;
		int brnoNum = 0;
		brnoNum += Integer.parseInt(BRNO.substring(0,1));
		brnoNum += Integer.parseInt(BRNO.substring(1,2)) * 3 % 10;
		brnoNum += Integer.parseInt(BRNO.substring(2,3)) * 7 % 10;
		brnoNum += Integer.parseInt(BRNO.substring(3,4)) * 1 % 10;
		brnoNum += Integer.parseInt(BRNO.substring(4,5)) * 3 % 10;
		brnoNum += Integer.parseInt(BRNO.substring(5,6)) * 7 % 10;
		brnoNum += Integer.parseInt(BRNO.substring(6,7)) * 1 % 10;
		brnoNum += Integer.parseInt(BRNO.substring(7,8)) * 3 % 10;
		brnoNum += Math.floor(Integer.parseInt(BRNO.substring(8,9)) * 5 / 10);
		brnoNum += Integer.parseInt(BRNO.substring(8,9)) * 5 % 10;
		brnoNum += Integer.parseInt(BRNO.substring(9,10));
		
		if(brnoNum % 10 != 0){
//			System.err.println(BRNO + " 사용불가");
			result = false;
		}else {
			System.out.println(BRNO + " 사용가능");
			result = true;
		}
		return result;
	}
	
	public static String getUrl(HttpServletRequest req){
		return req.getRequestURL()+"?"+req.getQueryString();
	}

}
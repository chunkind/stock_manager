package com.dev.ck;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @Auth: K. J. S.
 * @Date: 2023. 11. 27.
 * 프로잭크 설정 관련
 */
public class Config {
	public static String RESULT_CD = "resultCd"; //성공:S, 실패:F
	public static String RESULT_MSG = "resultMsg"; //처리 메세지
	
	public static Properties data = null;
	static {
		System.out.println("Properties file loadding start.....");
		Config.data = new Properties();
		InputStream inputStream = Config.class.getClassLoader().getResourceAsStream("config.properties");
		try {
			data.load(inputStream);
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("Properties file loadding end.....");
	}
}
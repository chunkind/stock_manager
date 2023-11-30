package com.dev.ck.ackwd.config.core.config;

import java.util.Locale;

/**
 * EnableCommonMessage 에서 초기화를 과정을 설정하기 위한 Configurer
 */
public interface CommonMessageConfigurer {

	/**
	 * 메시지 를 몇초 동안 캐시 해둘 것인지. 지정된 시간이 지나면 메시지 소스 파일이 변경 되었는지 체크한다.
	 */
	int messageCacheSeconds();

	/**
	 * 메시지 소스 파일 위치.
	 * ex) classpath:/config/app/messages/messages classpath:/config/app/messages/validation
	 * @return
	 */
	String[] messageBasenames();

	/**
	 * 기본으로 사용할 Locale. 한국어라면 Locale.KOREAN 을 사용한다.
	 */
	Locale defaultLocale();

}
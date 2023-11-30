package com.dev.ck.ackwd.config.core.annotation.profiles;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.context.annotation.Profile;

/**
 * 스프링 설정중 개발 PC에서만 등록해야 하는 빈을 나타내기 위한 어노테이션 다음과 같이 클래스에 분여 사용하면 된다.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
// 로컬 PC에서 개발할때는 default 프로필로 동작하게 함. 이를 통해 별도의 spring.profiles.active 환경 변수를
// 지정하지 않아도 기본적으로 적용하게 함
@Profile({"default", Local.PROFILE_NAME})
public @interface Local {
	String PROFILE_NAME = "local";
}

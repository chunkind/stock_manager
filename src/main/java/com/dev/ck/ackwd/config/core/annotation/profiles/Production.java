package com.dev.ck.ackwd.config.core.annotation.profiles;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.context.annotation.Profile;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Profile(Local.PROFILE_NAME)
public @interface Production {
	String PROFILE_NAME = "production";
}
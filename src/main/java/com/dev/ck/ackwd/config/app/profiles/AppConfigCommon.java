package com.dev.ck.ackwd.config.app.profiles;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

import lombok.extern.slf4j.Slf4j;

//@Configuration
@Slf4j
public class AppConfigCommon {

	@Bean
	public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
		PropertySourcesPlaceholderConfigurer configurer = new PropertySourcesPlaceholderConfigurer();
		configurer.setFileEncoding("UTF-8");
		//configurer.setIgnoreUnresolvablePlaceholders(true);
		return configurer;
	}
}

package com.dev.ck.ackwd.config.app;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.stereotype.Controller;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.dev.ck.ackwd.config.app.profiles.AppConfigCommon;
import com.dev.ck.ackwd.config.app.profiles.AppConfigLocal;
import com.dev.ck.ackwd.config.app.profiles.AppConfigProduction;
import com.dev.ck.ackwd.config.core.config.CommonMessageConfigurer;
import com.dev.ck.ackwd.config.spring.WebApplicationContextHolder;
import com.google.gson.Gson;

import lombok.extern.slf4j.Slf4j;

//@Configuration
@Import({
	AppConfigCommon.class,
	AppConfigLocal.class, AppConfigProduction.class,
	MybatisConfig.class
})
@ComponentScan(
	basePackages = { 
		"com.dev.ck"
	}
	, excludeFilters = {
		@ComponentScan.Filter(value = Controller.class, type = FilterType.ANNOTATION),
	}
)
@EnableAspectJAutoProxy(proxyTargetClass = true)
@Slf4j
//@formatter:on
public class ApplicationConfig implements CommonMessageConfigurer{
	@Autowired ConfigurableEnvironment env;
	
	@Override
	public int messageCacheSeconds() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String[] messageBasenames() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Locale defaultLocale() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Bean
	public Gson gson() {
		return new Gson();
	}
	
	@Bean
	public WebApplicationContextHolder webApplicationContextHolder(WebApplicationContext context) {
		WebApplicationContextHolder holder = new WebApplicationContextHolder();
		holder.setApplicationContext(context);
		return holder;
	}

	@Bean
	public CommonsMultipartResolver multipartResolver() {
		CommonsMultipartResolver bean = new CommonsMultipartResolver();
		bean.setMaxUploadSize(5242880000L);
		bean.setMaxInMemorySize(100000000);
		bean.setDefaultEncoding("UTF-8");
		return bean;
	}
}

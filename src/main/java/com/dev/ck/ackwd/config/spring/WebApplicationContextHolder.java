package com.dev.ck.ackwd.config.spring;

import lombok.Getter;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class WebApplicationContextHolder implements ApplicationContextAware {
	@Getter
	static ApplicationContext context;

	@Override
	public void setApplicationContext(ApplicationContext context) throws BeansException {
		WebApplicationContextHolder.context = context;
	}
}
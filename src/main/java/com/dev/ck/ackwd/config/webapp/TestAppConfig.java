package com.dev.ck.ackwd.config.webapp;

import java.util.Locale;

import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

import com.dev.ck.ackwd.config.app.MybatisConfig;
import com.dev.ck.ackwd.config.app.profiles.AppConfigLocal;
import com.dev.ck.ackwd.config.core.config.CommonMessageConfigurer;
import com.google.gson.Gson;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Import({AppConfigLocal.class, MybatisConfig.class})
@ComponentScan(
	basePackages = { 
		"com.dev.ck"
	}/*,
	excludeFilters = {
		@ComponentScan.Filter(type=FilterType.ANNOTATION, value = Controller.class),
	}*/
)
@MapperScan(
	basePackages = {"com.dev.ck"},
	annotationClass = Mapper.class,
	sqlSessionFactoryRef = "commonMybatisSessionFactory",
	sqlSessionTemplateRef = "commonSqlSessionTemplate"
)
public class TestAppConfig implements CommonMessageConfigurer{
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
}

package com.dev.ck.ackwd.config.app.profiles;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

import com.dev.ck.ackwd.config.core.annotation.profiles.Local;
import com.dev.ck.ackwd.config.persistent.datasource.DataSourceAuthentication;
import com.dev.ck.ackwd.config.persistent.datasource.InMemoryDataSourceAuthentication;
import com.zaxxer.hikari.HikariDataSource;

import lombok.extern.slf4j.Slf4j;

//@Configuration
@PropertySources(value = {@PropertySource("classpath:/config.properties")})
@Local
@Slf4j
public class AppConfigLocal {
	@Value("${common.jdbc.classname}") String driver;
	
	public AppConfigLocal() {
		log.info("App local 프로필이 적용 되었습니다. driver:{}", driver);
	}
	
	@Bean
	public String currentStageProfile() {
		return "LOCAL";
	}
	
	@Bean
	public DataSourceAuthentication commonDataSourceAuthentication(
		@Value("${common.jdbc.username}") String user,
		@Value("${common.jdbc.password}") String password
	) {
		log.info("Use InMemoryDataSourceAuthentication user:{}", user);
		InMemoryDataSourceAuthentication auth = new InMemoryDataSourceAuthentication();
		auth.setUser(user);
		auth.setPassword(password);
		return auth;
	}
	
	@Bean(destroyMethod = "close")
	public DataSource commonDataSource(
		@Qualifier("commonDataSourceAuthentication") DataSourceAuthentication auth,
		@Value("${common.jdbc.url}") String url,
		@Value("${common.jdbc.maxPoolSize}") int maxPoolSize,
		@Value("${test.query}") String testQuery
	) {
		log.info("dataSource... url:{}", url);
		HikariDataSource ds = new HikariDataSource();
		ds.setDriverClassName(driver);
		ds.setJdbcUrl(url);
		ds.setUsername(auth.getUser());
		ds.setPassword(auth.getPassword());
		ds.setMaximumPoolSize(maxPoolSize);
		return ds;
	}
	
}

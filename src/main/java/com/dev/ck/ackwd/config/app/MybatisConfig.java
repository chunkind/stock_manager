package com.dev.ck.ackwd.config.app;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

import javax.sql.DataSource;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.type.TypeHandler;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import com.dev.ck.ackwd.common.CommonDto;
import com.dev.ck.ackwd.config.noo.pagination.interceptor.PaginationInterceptor;
import com.dev.ck.ackwd.config.noo.pagination.interceptor.PaginationNoGetCountInterceptor;
import com.dev.ck.ackwd.config.persistent.mybatis.QuiteSqlSessionTemplate;
import com.dev.ck.ackwd.config.persistent.mybatis.RefreshableSqlSessionFactoryBean;
import com.dev.ck.ackwd.config.persistent.mybatis.YesNoBooleanTypeHandler;
import com.dev.ck.ackwd.config.persistent.mybatis.audit.AuditableInterceptor;

import lombok.extern.slf4j.Slf4j;

//@Configuration
@Slf4j
@MapperScan(
	basePackages = {"com.dev.ck"},
	annotationClass = Mapper.class,
	sqlSessionFactoryRef = "commonMybatisSessionFactory",
	sqlSessionTemplateRef = "commonSqlSessionTemplate"
)
public class MybatisConfig {
	@Qualifier("commonDataSource")
	@Autowired(required = true)
	private DataSource commonDataSource;
	@Autowired
	private String currentStageProfile;
	@Value("${app.env.mybatis.pagination.dialect}")
	private String paginationDialect;

	@Bean
	public SqlSessionFactory commonMybatisSessionFactory() throws Exception{
		RefreshableSqlSessionFactoryBean factory = new RefreshableSqlSessionFactoryBean();
		factory.setDataSource(commonDataSource);
		List<Resource> buf = new ArrayList<Resource>();
		buf.addAll(Arrays.asList(new PathMatchingResourcePatternResolver().getResources("classpath:/sqlmap/*/**/*.xml")));
		Resource[] mapperLocations = buf.toArray(new Resource[buf.size()]);
		
		log.info("mybatisSessionFactory mapperLocations: {}", ArrayUtils.toString(mapperLocations));
		
		factory.setMapperLocations(mapperLocations);
		factory.setTypeAliases(new Class<?>[] {
			CommonDto.class
		});
		factory.setPlugins(interceptors());
		factory.setTypedHandlerMap(typeHandlers());
		
		//인자로 null이 왔을때 처리하는 기본 방식 지정
		final Properties sqlSessionFactoryProperties = new Properties();
		sqlSessionFactoryProperties.put("jdbcTypeForNull", "NULL");
		factory.setConfigurationProperties(sqlSessionFactoryProperties);
		factory.setInterval(5000);
		factory.afterPropertiesSet();
		
		SqlSessionFactory session = factory.getObject();
		
		return session;
	}
	
	@Bean
	public SqlSessionTemplate commonSqlSessionTemplate() throws Exception{
		log.info("웹 어플리케이션 실행을 위한 SqlSessionTemplate 을 사용합니다.");
		return new QuiteSqlSessionTemplate(commonMybatisSessionFactory());
	}
	
	Interceptor[] interceptors() {
		return new Interceptor[] {paginationInterceptor(), paginationInterceptorNoGetCount(), auditableInterceptor()};
	}

	/**
	 * String 기반의 ID 와 도메인 객체를 변경하기 위해서 등록하는 컨버터목록
	 * 
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	Map<Class, TypeHandler> typeHandlers() {

		Map<Class, TypeHandler> map = new ConcurrentHashMap<Class, TypeHandler>();

		// Y/N 을 BOOLEAN 으로 변환하는 핸들러
		map.put(Boolean.class, new YesNoBooleanTypeHandler());
		return map;
	}
	
	@Bean
	public AuditableInterceptor auditableInterceptor() {
		return new AuditableInterceptor();
	}

	@Bean
	public PaginationInterceptor paginationInterceptor() {
		PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
		Properties props = new Properties();
		log.info("Mybatis pagination dialect: {}", paginationDialect);
		props.setProperty("dialectClass", paginationDialect);
		props.setProperty("sqlPattern", ".*\\.findList.*");
		paginationInterceptor.setProperties(props);
		return paginationInterceptor;
	}

	// 전체카운트 없는 리스트
	@Bean
	public PaginationNoGetCountInterceptor paginationInterceptorNoGetCount() {
		PaginationNoGetCountInterceptor paginationInterceptor = new PaginationNoGetCountInterceptor();
		Properties props = new Properties();
		log.info("Mybatis pagination dialect: {}", paginationDialect);
		props.setProperty("dialectClass", paginationDialect);
		props.setProperty("sqlPattern", ".*\\.findNoGetCountList.*");
		paginationInterceptor.setProperties(props);
		return paginationInterceptor;
	}
}

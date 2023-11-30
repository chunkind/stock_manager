package com.dev.ck.ackwd.config.persistent.mybatis;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;
import org.apache.ibatis.type.TypeHandlerRegistry;
import org.mybatis.spring.SqlSessionFactoryBean;

import lombok.Getter;
import lombok.Setter;

/**
 * SqlSessionFactoryBean 내부에서 TypeHandler 의 제네릭 정보를 활용하지 못하는 문제를 해결하기 위해 추가 설정 과정 제공
 * 
 * @author narusas
 * 
 */
@SuppressWarnings("rawtypes")
@Setter
@Getter
public class TypedSqlSessionFactoryBean extends SqlSessionFactoryBean {
	Map<Class, TypeHandler> typedHandlerMap = new ConcurrentHashMap<Class, TypeHandler>();

	/**
	 * 값이 NULL 이 넘어 왔을떄, JDBC 드라이버에 기본으로 전달할 값
	 */
	private JdbcType jdbcTypeForNull = JdbcType.NULL;

	@SuppressWarnings("unchecked")
	protected SqlSessionFactory buildSqlSessionFactory() throws IOException {
		SqlSessionFactory factory = super.buildSqlSessionFactory();
		setupAdditional(factory);
		return factory;
	}

	protected void setupAdditional(SqlSessionFactory factory) {
		setupJdbcTypeForNull(factory);
		setupTypeHandlers(factory);
	}

	protected void setupTypeHandlers(SqlSessionFactory factory) {
		TypeHandlerRegistry registry = factory.getConfiguration().getTypeHandlerRegistry();
		for (Map.Entry<Class, TypeHandler> entry : typedHandlerMap.entrySet()) {
			registry.register(entry.getKey(), entry.getValue());
		}
	}

	protected void setupJdbcTypeForNull(SqlSessionFactory factory) {
		if (jdbcTypeForNull != null) {
			factory.getConfiguration().setJdbcTypeForNull(jdbcTypeForNull);
		}
	}
}

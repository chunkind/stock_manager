package com.dev.ck.ackwd.config.persistent.mybatis;

import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.dao.support.PersistenceExceptionTranslator;

/**
 * http://code.google.com/p/mybatis/issues/detail?id=778 에 따르면 close 호출시 발생하는 예외는 일부러 발생시키는 것으로 별 의미 없다고 함. 따라서 불필요한 예외가 기록되지 않게 close 를
 * quite 처리함
 */
public class QuiteSqlSessionTemplate extends SqlSessionTemplate {

	public QuiteSqlSessionTemplate(SqlSessionFactory sqlSessionFactory, ExecutorType executorType,
			PersistenceExceptionTranslator exceptionTranslator) {
		super(sqlSessionFactory, executorType, exceptionTranslator);
	}

	public QuiteSqlSessionTemplate(SqlSessionFactory sqlSessionFactory, ExecutorType executorType) {
		super(sqlSessionFactory, executorType);
	}

	public QuiteSqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {
		super(sqlSessionFactory);
	}

	@Override
	public void close() {
	}

}
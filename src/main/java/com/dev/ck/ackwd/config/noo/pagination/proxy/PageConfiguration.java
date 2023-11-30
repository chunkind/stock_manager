package com.dev.ck.ackwd.config.noo.pagination.proxy;

import org.apache.ibatis.binding.MapperRegistry;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;

/**
 * MyBatis로 지정 구성 확장 .
 */
public class PageConfiguration extends Configuration {
	protected MapperRegistry mapperRegistry = new PaginationMapperRegistry(this);

	@Override
	public <T> void addMapper(Class<T> type) {
		mapperRegistry.addMapper(type);
	}

	@Override
	public <T> T getMapper(Class<T> type, SqlSession sqlSession) {
		return mapperRegistry.getMapper(type, sqlSession);
	}

	@Override
	public boolean hasMapper(Class type) {
		return mapperRegistry.hasMapper(type);
	}
}

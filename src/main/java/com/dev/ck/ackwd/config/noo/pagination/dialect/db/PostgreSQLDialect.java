package com.dev.ck.ackwd.config.noo.pagination.dialect.db;

import com.dev.ck.ackwd.config.noo.pagination.dialect.Dialect;

public class PostgreSQLDialect implements Dialect {

	public boolean supportsLimit() {
		return true;
	}

	@Override
	public String getLimitString(String sql, int offset, int limit) {
		return getLimitString(sql, offset, Integer.toString(offset),
				Integer.toString(limit));
	}

	public String getLimitString(String sql, int offset,
								 String offsetPlaceholder, String limitPlaceholder) {
		StringBuilder pageSql = new StringBuilder().append(sql);
		pageSql = offset <= 0
				? pageSql.append(" limit ").append(limitPlaceholder) :
				pageSql.append(" limit ").append(limitPlaceholder).append(" offset ").append(offsetPlaceholder);
		return pageSql.toString();
	}
}

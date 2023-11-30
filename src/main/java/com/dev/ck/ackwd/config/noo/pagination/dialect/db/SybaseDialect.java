package com.dev.ck.ackwd.config.noo.pagination.dialect.db;

import com.dev.ck.ackwd.config.noo.pagination.dialect.Dialect;

public class SybaseDialect implements Dialect {

	public boolean supportsLimit() {
		return false;
	}


	@Override
	public String getLimitString(String sql, int offset, int limit) {
		return null;
	}

	public String getLimitString(String sql, int offset, String offsetPlaceholder, int limit, String limitPlaceholder) {
		throw new UnsupportedOperationException("paged queries not supported");
	}

}

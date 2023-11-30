package com.dev.ck.ackwd.config.noo.pagination.dialect.db;

import com.dev.ck.ackwd.config.noo.pagination.dialect.Dialect;

/**
 * 오라클 방언 실현
 */
public class OracleDialect implements Dialect {
	@Override
	public boolean supportsLimit() {
		return true;
	}

	@Override
	public String getLimitString(String sql, int offset, int limit) {
		return getLimitString(sql, offset, Integer.toString(offset), Integer.toString(limit));
	}

	/**
	 * ( 자리 ) 교체, SQL SQL 문 을 페이징 오프셋 제공 되고 자리 의 사용을 제한합니다.
	 * <pre>
	 * MySQL과 같은
	 * dialect.getLimitString("select * from user", 12, ":offset",0,":limit") 반환
	 * select * from user limit :offset,:limit
	 * </pre>
	 *
	 * @param sql			   실제 SQL 문
	 * @param offset			페이지의 수 를 기록 하기 시작
	 * @param offsetPlaceholder 페이지의 수 를 기록 하기 시작 - 자리
	 * @param limitPlaceholder  자리 의 페이징 레코드 번호
	 * @return 자리 페이징 SQL이
	 */
	public String getLimitString(String sql, int offset, String offsetPlaceholder, String limitPlaceholder) {
		sql = sql.trim();
		boolean isForUpdate = false;
		if (sql.toLowerCase().endsWith(" for update")) {
			sql = sql.substring(0, sql.length() - 11);
			isForUpdate = true;
		}
		StringBuilder pagingSelect = new StringBuilder(sql.length() + 100);
		if (offset > 0) {
			pagingSelect.append("select * from ( select row_.*, rownum rownum_ from ( ");
		} else {
			pagingSelect.append("select * from ( ");
		}
		pagingSelect.append(sql);
		if (offset > 0) {
			String endString = offsetPlaceholder + "+" + limitPlaceholder;
			pagingSelect.append(" ) row_ ) where rownum_ <= ")
					.append(endString).append(" and rownum_ > ").append(offsetPlaceholder);
		} else {
			pagingSelect.append(" ) where rownum <= ").append(limitPlaceholder);
		}

		if (isForUpdate) {
			pagingSelect.append(" for update");
		}

		return pagingSelect.toString();
	}

}

package com.dev.ck.ackwd.config.noo.pagination.dialect;

/**
 *  하이버네이트 방언과  유사 하지만, 일부분 만페이지를 간소화
 */
public interface Dialect {

	/**
	 * 현재 데이터베이스 자체는 페이징 페이징 쿼리 를 지원하는지 여부
	 * 데이터베이스 를 지원하지 않는 경우, 데이터베이스 페이지 없음
	 *
	 * @return true：현재 페이징 쿼리 를 지원합니다
	 */
	public boolean supportsLimit();

	/**
	 * 페이징 의 SQL 변환 SQL , 각각 페이징 SQL 호출
	 * 
	 * @param sql	SQL 문
	 * @param offset 시작 의 수
	 * @param limit  페이지 당 얼마나 많은 레코드 수
	 * @return  페이징 SQL 쿼리
	 */
	public String getLimitString(String sql, int offset, int limit);

}

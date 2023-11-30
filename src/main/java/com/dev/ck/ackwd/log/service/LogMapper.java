package com.dev.ck.ackwd.log.service;

import org.apache.ibatis.annotations.Mapper;

import com.dev.ck.ackwd.log.LogDto;

@Mapper
public interface LogMapper {
	/**
	 * @User : KJS
	 * @date : 2018. 5. 12.
	 * 로그 데이터 삽입.
	 */
	int insertLogOne(LogDto pvo);

	/**
	 * @User : K. J. S.
	 * @date : 2018. 5. 12.
	 * 로그 데이터 조회.
	 */
	LogDto selectLogOne(LogDto pvo);

	/**
	 * @User : K. J. S.
	 * @date : 2018. 5. 12.
	 * 로그 데이터 삭제.
	 */
	int deleteLogOne(LogDto pvo);

	/**
	 * @User : K. J. S.
	 * @date : 2022. 8. 21.
	 */
	int deleteLogOneByIp(LogDto pvo);

	/**
	 * @User : K. J. S.
	 * @date : 2018. 5. 12.
	 * 로그 데이터 수정.
	 */
	int updateLogOne(LogDto pvo);
}

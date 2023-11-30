package com.dev.ck.ackwd.log.service;

import com.dev.ck.ackwd.log.LogDto;

/**
 * @User: K. J. S.
 * @date: 2018. 5. 12.
 */
public interface LogService {
	/**
	 * @User : K. J. S.
	 * @date : 2018. 5. 12.
	 * 로그 데이터 삽입.
	 */
	public int insertLogOne(LogDto pvo);

	/**
	 * @User : K. J. S.
	 * @date : 2018. 5. 12.
	 * 로그 데이터 조회.
	 */
	public LogDto selectLogOne(LogDto pvo);

	/**
	 * @User : K. J. S.
	 * @date : 2018. 5. 12.
	 * 로그 데이터 삭제.
	 */
	public int deleteLogOne(LogDto pvo);

	/**
	 * @User : K. J. S.
	 * @date : 2022. 8. 21.
	 */
	public int deleteLogOneByIp(LogDto pvo);

	/**
	 * @User : K. J. S.
	 * @date : 2018. 5. 12.
	 * 로그 데이터 수정.
	 */
	public int updateLogOne(LogDto pvo);

}

package com.dev.ck.ackwd.log.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dev.ck.ackwd.log.LogDto;
import com.dev.ck.ackwd.log.service.LogMapper;
import com.dev.ck.ackwd.log.service.LogService;

@Service
public class LogServiceImpl implements LogService{
	@Autowired private LogMapper logMapper;
	
	/**
	 * @User : K. J. S.
	 * @date : 2018. 5. 12.
	 * 로그 데이터 삽입.
	 */
	@Override
	public int insertLogOne(LogDto pvo) {
		return logMapper.insertLogOne(pvo);
	}

	/**
	 * @User : K. J. S.
	 * @date : 2018. 5. 12.
	 * 로그 데이터 조회.
	 */
	@Override
	public LogDto selectLogOne(LogDto pvo) {
		return logMapper.selectLogOne(pvo);
	}

	/**
	 * @User : K. J. S.
	 * @date : 2018. 5. 12.
	 * 로그 데이터 삭제.
	 */
	@Override
	public int deleteLogOne(LogDto pvo) {
		return logMapper.deleteLogOne(pvo);
	}

	/**
	 * @User : K. J. S.
	 * @date : 2022. 8. 21.
	 */
	@Override
	public int deleteLogOneByIp(LogDto pvo) {
		return logMapper.deleteLogOneByIp(pvo);
	}

	/**
	 * @User : K. J. S.
	 * @date : 2018. 5. 12.
	 * 로그 데이터 수정.
	 */
	@Override
	public int updateLogOne(LogDto pvo) {
		return logMapper.updateLogOne(pvo);
	} 
}

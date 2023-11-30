package com.dev.ck.ackwd.common.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dev.ck.ackwd.common.service.CommonMapper;
import com.dev.ck.ackwd.common.service.CommonService;

/**
 * @Auth: K. J. S.
 * @Date: 2023. 11. 27.
 * 공통 서비스
 */
@Service
public class CommonServiceImpl implements CommonService{
	@Autowired private CommonMapper commonMapper;

}

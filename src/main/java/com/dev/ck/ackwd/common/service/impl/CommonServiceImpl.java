package com.dev.ck.ackwd.common.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dev.ck.ackwd.common.CommonDto;
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

	public int insertSmStdCdDtoOne(CommonDto param){
		return commonMapper.insertSmStdCdDtoOne(param);
	}

	public int updateSmStdCdDtoOne(CommonDto param){
		return commonMapper.updateSmStdCdDtoOne(param);
	}

	public int deleteSmStdCdDtoOne(CommonDto param){
		return commonMapper.deleteSmStdCdDtoOne(param);
	}

	public CommonDto selectSmStdCdDtoOne(CommonDto param){
		return commonMapper.selectSmStdCdDtoOne(param);
	}

	public List<CommonDto> selectSmStdCdDtoList(CommonDto param){
		return commonMapper.selectSmStdCdDtoList(param);
	}
}

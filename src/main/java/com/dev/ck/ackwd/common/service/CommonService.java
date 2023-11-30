package com.dev.ck.ackwd.common.service;

import java.util.List;

import com.dev.ck.ackwd.common.CommonDto;

public interface CommonService {
	int insertSmStdCdDtoOne(CommonDto param);
	int updateSmStdCdDtoOne(CommonDto param);
	int deleteSmStdCdDtoOne(CommonDto param);
	CommonDto selectSmStdCdDtoOne(CommonDto param);
	List<CommonDto> selectSmStdCdDtoList(CommonDto param);
}
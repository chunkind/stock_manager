package com.dev.ck.ackwd.common.service;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.dev.ck.ackwd.common.CommonDto;

@Mapper
public interface CommonMapper {
	int insertSmStdCdDtoOne(CommonDto param);
	int updateSmStdCdDtoOne(CommonDto param);
	int deleteSmStdCdDtoOne(CommonDto param);
	CommonDto selectSmStdCdDtoOne(CommonDto param);
	List<CommonDto> selectSmStdCdDtoList(CommonDto param);
}

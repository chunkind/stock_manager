package com.dev.ck.ackwd.common.service;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.dev.ck.ackwd.common.CommonDto;

@Mapper
public interface CommonMapper {
	List<CommonDto> selectCommonCodeList(CommonDto params);
}

package com.dev.ck.ackwd.common.service.impl;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.dev.ck.TestApp;
import com.dev.ck.ackwd.common.CommonDto;
import com.dev.ck.ackwd.common.service.CommonService;

public class CommonServiceImplTest extends TestApp{
	@Autowired private CommonService commonService;
	
	@Test
	public void 공통코드등록() {
		CommonDto param =
			CommonDto.builder()
			.grpCd("CD001")
			.grpNm("서비스상태")
			.cd("01")
			.cdNm("운영")
			.cdDesc("서비스상태표시")
			.sort("1")
			.dplyYn("Y")
			.useYn("Y")
			.sysRegrId("system")
			.sysModrId("system")
			.build();
		
		commonService.insertSmStdCdDtoOne(param);
	}
	
	@Test
	public void 공통코드리스트() {
		CommonDto param = new CommonDto();
		List<CommonDto> list =
			commonService.selectSmStdCdDtoList(param);
		System.out.println(list.toString());
	}
}

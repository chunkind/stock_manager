package com.dev.ck.ackwd.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.dev.ck.ackwd.common.service.CommonService;

/**
 * @Auth: K. J. S.
 * @Date: 2023. 11. 27.
 * 공통서비스 컨트롤러
 */
@Controller
public class CommonController {
	@Autowired private CommonService commonService;

}

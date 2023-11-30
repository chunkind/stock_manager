package com.dev.ck.ackwd.page;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Auth : K. J. S.
 * @Date : 2020. 3. 28.
 * 페이지 이동에 관한 것들.
 */
@Controller
public class PageController {
	@RequestMapping("/main/home.do")
	public String dashBoard(){
		return "stmg/main/home";
	}
}

package com.dev.ck.ackwd.page;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.dev.ck.Config;

/**
 * @Auth : K. J. S.
 * @Date : 2020. 3. 28.
 * 페이지 이동에 관한 것들.
 */
@Controller
public class PageController {
	/**
	 * @Auth: K. J. S.
	 * @Date: 2023. 12. 15.
	 * 홈화면
	 */
	@RequestMapping("/main/home.do")
	public String dashBoard(){
		return "stmg/main/home";
	}
	
	/**
	 * @Auth: K. J. S.
	 * @Date: 2023. 11. 26.
	 * @Memo - 로그인 페이지
	 */
	@RequestMapping("/user/login.do")
	public String login(HttpServletRequest req){
		req.setAttribute("kakaoUrl", Config.data.getProperty("api.kakao.redirectUrl"));
		return "ackwd/page/login.view";
	}
	
	/**
	 * @Auth: K. J. S.
	 * @Date: 2023. 12. 15.
	 * 회원가입
	 */
	@RequestMapping("/user/join.do")
	public String join(HttpServletRequest req){
		return "ackwd/page/join";
	}
	
	/**
	 * @Auth: K. J. S.
	 * @Date: 2023. 12. 15.
	 * 회원 정보 수정
	 */
	@RequestMapping(value="/user/editUserInfo.do")
	public String editUserInfo() {
		return "ackwd/page/join";
	}
	
	/**
	 * @User : K. J. S.
	 * @date : 2018. 1. 18.
	 * 로그아웃.
	 */
	@RequestMapping(value="/user/logout.do")
	public String logout(HttpSession session){
		session.invalidate();
		return "redirect:/main/home.do";
	}
	
	/**
	 * @Auth: K. J. S.
	 * @Date: 2023. 11. 26.
	 * @Memo - 카카오톡 로그인
	 */
	@RequestMapping(value="/page/kakaoLoginResult.do")
	public String pageKakaoLoginResult(@RequestParam("code") String code, HttpServletRequest req, HttpServletResponse res){
		req.setAttribute("code", code);
		return "ackwd/page/kakaoLoginResult.view";
	}
}

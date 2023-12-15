package com.dev.ck.ackwd.user;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.codehaus.jackson.map.ObjectMapper;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dev.ck.Config;
import com.dev.ck.ackwd.user.service.UserService;
import com.dev.ck.ackwd.utils.DateUtil;
import com.dev.ck.ackwd.utils.Util;

@RestController
public class UserController {
	@Autowired private UserService userService;
	
	/**
	 * @Auth: K. J. S.
	 * @Date: 2023. 12. 14.
	 * 카카오톡 로그인
	 */
	@RequestMapping(value = "/user/kakaoLogin.do")
	public JSONObject kakaoLogin(@RequestParam("code") String code, HttpSession session){
		JSONObject data = new JSONObject();
		try {
//			ObjectMapper mapper = new ObjectMapper();
//			UsrBaseDto pvo = mapper.convertValue(params, UsrBaseDto.class);

			//1번 인증코드 요청 전달
			UserDto rvo = userService.getAccessToken(code);
			//2번 인증코드로 토큰 전달
			UserDto userInfo = userService.getKakaoUserInfo(rvo);
			
			// 회원가입 안되어 있으면 회원가입 처리 (필수값 셋팅)
			UserDto usrDto = new UserDto();
			usrDto.setUserName(userInfo.getUserName() == null ? userInfo.getUserNic() : userInfo.getUserName());
			usrDto.setUserNic(userInfo.getUserNic());
			usrDto.setUserBirth(userInfo.getUserBirth());
			usrDto.setUserGender(userInfo.getUserGender());
			usrDto.setUserEmail(userInfo.getUserEmail());
			usrDto.setUserTell(userInfo.getUserTell());
			usrDto.setSnsConType("kakao");
			usrDto.setSnsConYn("Y");
			usrDto.setSnsSeqId(userInfo.getId());
			usrDto.setPoint(0);
			usrDto.setUserLv(1);

			boolean isAlreadyUser = userService.checkId(usrDto);
			String result = "";

			if(isAlreadyUser){
				UserDto mvo = userService.login(usrDto);
				session.setAttribute("mvo", mvo);
				data.put("returnPage", "/main/home.do");
			}
			else {
				result = userService.registerUserOne(usrDto);
				data.put("returnPage", "");
			}

			//토큰번호 변경
			usrDto.setCertType("kakao");
			usrDto.setCertKeyVal(userInfo.getAccessToken());
			usrDto.setCertKeyDtime(DateUtil.toDay());
			int updateResult = userService.updateToken(usrDto);

			data.put("accessToken", userInfo.getAccessToken());
			data.put("loginInfo", usrDto);
			data.put(Config.RESULT_CD, 'S');
			data.put("message", "SUCCESS");
		}catch(Exception e) {
			data.put(Config.RESULT_CD, 'F');
			data.put("message", "FAIL");
			e.printStackTrace();
		}
		return data;
	}
	
	/**
	 * @Auth: K. J. S.
	 * @Date: 2023. 12. 14.
	 * 로그인
	 */
	@RequestMapping(value = "/user/loginAction.do")
	public JSONObject login(@RequestBody Map<String, Object> map, HttpSession session, HttpServletRequest request){
		JSONObject data = new JSONObject();
		try {
			ObjectMapper mapper = new ObjectMapper();
			UserDto pvo = mapper.convertValue(map, UserDto.class);
			
			UserDto mvo = userService.login(pvo);
			String returnPage = "";
			
			if(null == mvo) {
				session.setAttribute("message", "로그인 실패!");
				returnPage = "/fo/user/showLogin.do";
			}
			
			returnPage = String.valueOf(null == session.getAttribute("strUrl")? "" : session.getAttribute("strUrl"));
			
			mvo.setUserIp(Util.getClientIP(request));
			
//			session.setAttribute("message", "환영합니다 ^^");
			session.setAttribute("mvo", mvo);
			
			if(null == returnPage || "".equals(returnPage)) {
				returnPage = "/fo/plan/dailyPlan.do";
			}else {
				returnPage = returnPage;
			}
			
			data.put(Config.RESULT_CD, 'S');
			data.put("returnPage", returnPage);
			data.put("message", "SUCCESS");
		}catch(Exception e) {
			data.put(Config.RESULT_CD, 'F');
			data.put("message", "FAIL");
			data.put("returnPage", "/fo/user/showLogin.do");
			e.printStackTrace();
		}
		return data;
	}
	
}
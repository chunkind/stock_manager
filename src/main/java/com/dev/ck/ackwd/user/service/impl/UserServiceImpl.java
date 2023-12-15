package com.dev.ck.ackwd.user.service.impl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.dev.ck.Config;
import com.dev.ck.ackwd.user.UserDto;
import com.dev.ck.ackwd.user.service.UserMapper;
import com.dev.ck.ackwd.user.service.UserService;
import com.dev.ck.ackwd.utils.SendUtil;
import com.dev.ck.ackwd.utils.StringUtil;
import com.dev.ck.ackwd.utils.aes.AES256;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * @Auth: K. J. S.
 * @Date: 2018. 1. 18.
 * 회원 관련, 블랙리스트 관련.
 */
@Service
public class UserServiceImpl implements UserService{
	@Autowired private UserMapper userMapper;
	@Value("${api.kakao.clientKey}") private String clientKey; 
	@Value("${api.kakao.redirectUrl}") private String redirectUrl; 
	@Value("${api.kakao.tokenUrl}") private String tokenUrl; 
	@Value("${api.kakao.userInfoUrl}") private String userInfoUrl; 
	
	/**
	 * @User : K. J. S.
	 * @date : 2018. 1. 18.
	 * 회원 가입.
	 */
	@Override
	public String registerUserOne(UserDto pvo) {
		//등록전에 이미 있는 아이디인지 체크
		int result = userMapper.checkUserId(pvo);
		
		//암호화
		if(StringUtil.isEmpty(pvo.getUserPw())) {
			pvo.setUserPw(AES256.encode(pvo.getUserPw()));
		}
		
		if(result == 0) {
			userMapper.insertUserOne(pvo);
			return "";
		}else {
			return "alredy used id";
		}
	}
	
	/**
	 * @User : K. J. S.
	 * @date : 2018. 1. 18.
	 * 회원 수정.
	 */
	@Override
	public void updateUser(UserDto pvo){
		userMapper.updateUserOne(pvo);
	}
	
	/**
	 * @auth : K.J.S
	 * @date : 2018. 8. 18.
	 * 회원 삭제.
	 */
	@Override
	public boolean deleteUserOne(UserDto pvo){
		
		int result = userMapper.deleteUserOne(pvo);
		
		if(result == 1) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * @User : K. J. S.
	 * @date : 2018. 1. 18.
	 * 로그인.
	 */
	@Override
	public UserDto login(UserDto pvo){
		//비밀번호 암호화
		if(!StringUtil.isEmpty(pvo.getUserPw()))
			pvo.setUserPw(AES256.encode(pvo.getUserPw()));
		
		//블랙리스트 확인.
		List<UserDto> blackList = userMapper.selectBlackList();
		
		//블랙리스트에 해당하는 아이디 접근 금지.
		for(UserDto bvo : blackList) {
			if(pvo.getUserId().equals(bvo.getUserId())) {
				return null;
			}
		}
		
		return userMapper.selectUserOne(pvo);
	}
	
	/**
	 * @auth : K. J. S.
	 * @date : 2018. 8. 18.
	 * 회원 정보 1개 가지고 오기.
	 */
	@Override
	public UserDto selectUserOne(UserDto pvo){
		return userMapper.selectUserOne(pvo);
	}
	
	/**
	 * @auth : K. J. S.
	 * @date : 2018. 8. 18.
	 * 모든 맴버 가지고 오기.
	 */
	@Override
	public List<UserDto> selectUserList(){
		return userMapper.selectUserList();
	}
	
	/**
	 * @Auth : K. J. S.
	 * @Date : 2023. 6. 30.
	 * 아이디 체크
	 */
	@Override
	public boolean checkId(UserDto pvo) {
		return userMapper.checkUserId(pvo) > 0 ? true : false;
	}
	
	/**
	 * @Auth: K. J. S.
	 * @Date: 2023. 12. 14.
	 */
	@Override
	public int updateToken(UserDto pvo) {
		return userMapper.updateToken(pvo);
	}
	
	/**
	 * @Auth : K. J. S.
	 * @Date : 2023. 6. 20.
	 */
	@Override
	public UserDto getAccessToken(String code) {
		UserDto rvo = null;
		Map<String, String> param = new HashMap<String, String>();
		param.put("grant_type", "authorization_code");
		param.put("client_id", clientKey);
		param.put("redirect_uri", redirectUrl);
		param.put("code", code);
		String result = SendUtil.sendHttpsPost(tokenUrl, param);

		System.out.println("response body :: " + result);
		
		JsonParser parser = new JsonParser();
		JsonElement element = parser.parse(result);

		rvo = UserDto.builder()
				.accessToken(element.getAsJsonObject().get("access_token").getAsString())
				.tokenType(element.getAsJsonObject().get("token_type").getAsString())
				.refreshToken(element.getAsJsonObject().get("refresh_token").getAsString())
//				.idToken(element.getAsJsonObject().get("id_token").getAsString())
				.expiresIn(element.getAsJsonObject().get("expires_in").getAsString())
				.scope(element.getAsJsonObject().get("scope").getAsString())
				.refreshTokenExpiresIn(element.getAsJsonObject().get("refresh_token_expires_in").getAsString())
				.build();

		return rvo;
	}
	
	/**
	 * @Auth : K. J. S.
	 * @Date : 2023. 6. 20.
	 */
	@Override
	public UserDto getKakaoUserInfo(UserDto pvo) {
		URL url = null;
		UserDto rvo = pvo;
//		try {
			/*url = new URL(userInfoUrl);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Authorization", "Bearer " + pvo.getAccessToken());
			int responseCode = conn.getResponseCode();
			System.out.println("responseCode ::" + responseCode);

			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

			String line = "";
			String result = "";

			while ((line = br.readLine()) != null){
				result += line;
			}*/
			
			Map<String, String> param = new HashMap<String, String>();
			param.put("Authorization", "Bearer " + pvo.getAccessToken());
			String result = SendUtil.sendHttpsPost(userInfoUrl, param);
			
			System.out.println("response body :: " + result);

			JsonParser parser = new JsonParser();
			JsonElement element = parser.parse(result);

			JsonObject properties = element.getAsJsonObject().get("properties").getAsJsonObject();
			JsonObject kakaoAccount = element.getAsJsonObject().get("kakao_account").getAsJsonObject();
			String id = element.getAsJsonObject().get("id").getAsString();

			String nickname = properties.getAsJsonObject().get("nickname").getAsString();
//			String email = kakaoAccount.getAsJsonObject().get("email").getAsString();
			String birthday = kakaoAccount.getAsJsonObject().get("birthday").getAsString();
			String gender = kakaoAccount.getAsJsonObject().get("gender").getAsString();

			rvo.setId(id);
//			rvo.setUserId(email);
			rvo.setUserNic(nickname);
//			rvo.setUserEmail(email);
			rvo.setUserGender(gender.equals("male")? "M":"W");

//		} catch (MalformedURLException e) {
//			throw new RuntimeException(e);
//		} catch (IOException e) {
//			throw new RuntimeException(e);
//		}
		return rvo;
	}
	
	/**
	 * @auth : K. J. S.
	 * @date : 2018. 8. 25.
	 * 블랙리스트 등록.
	 */
	@Override
	public boolean registerBlackListOne(UserDto pvo) {
		int result = userMapper.insertBlackListOne(pvo);
		
		if(result == 0) {
			return false;
		}else {
			return true;
		}
		
	}
	
	/**
	 * @auth : K. J. S.
	 * @date : 2018. 8. 25.
	 * 블랙리스트 삭제.
	 */
	@Override
	public boolean removeBlackListOne(UserDto pvo) {
		int result = userMapper.deleteBlackListOne(pvo);
		
		if(result == 0) {
			return false;
		}else {
			return true;
		}
		
	}
	
	/**
	 * @User : K. J. S.
	 * @date : 2018. 5. 19.
	 * 블랙 리스트 명단 가지고 오기.
	 */
	@Override
	public List<UserDto> selectBlackList(){
		return userMapper.selectBlackList();
	}
}
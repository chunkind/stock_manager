package com.dev.ck.ackwd.user.service;

import java.util.List;

import com.dev.ck.ackwd.user.UserDto;

public interface UserService {
	String registerUserOne(UserDto pvo); //회원 가입.
	void updateUser(UserDto pvo); //회원 수정.
	boolean deleteUserOne(UserDto pvo); //회원 삭제.
	UserDto login(UserDto pvo); //로그인.
	UserDto selectUserOne(UserDto pvo); //회원 정보 1개 가지고 오기.
	List<UserDto> selectUserList(); //모든 맴버 가지고 오기.
	
	boolean checkId(UserDto pvo); //아이디 체크
	int updateToken(UserDto pvo); //토큰 업데이트
	UserDto getAccessToken(String code);
	UserDto getKakaoUserInfo(UserDto pvo);
	
	boolean registerBlackListOne(UserDto pvo); //블랙리스트 등록.
	boolean removeBlackListOne(UserDto pvo); //블랙리스트 삭제.
	List<UserDto> selectBlackList(); //블랙 리스트 명단 가지고 오기.
}
package com.dev.ck.ackwd.user.service;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.dev.ck.ackwd.user.UserDto;

@Mapper
public interface UserMapper {
	int insertUserOne(UserDto pvo); //회원 가입.
	int updateUserOne(UserDto pvo); //회원 수정.
	int deleteUserOne(UserDto pvo); //회원 삭제 단건.
	int deleteUserList(); //회원 모두 삭제.
	UserDto selectUserOne(UserDto pvo); //회원 정보 가저오기 단건.
	List<UserDto> selectUserList(); //회원 리스트 가져오기.
	int checkUserId(UserDto pvo); //아이디가 있는지 없는지 체크
	int updateToken(UserDto pvo); //토큰 업데이트
	int insertBlackListOne(UserDto pvo); //블랙 리스트 등록.
	int deleteBlackListOne(UserDto pvo); //블랙 리스트 삭제.
	List<UserDto> selectBlackList(); //블랙 리스트 명단 가지고 오기.
}

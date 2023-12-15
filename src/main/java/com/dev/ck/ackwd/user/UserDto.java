package com.dev.ck.ackwd.user;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDto{
	private int usersSeq;
	private String userId;
	private String userName;
	private String userNic;
	private String userPw;
	private String userGender;
	private int userAge;
	private String userBirth;
	private String userEmail;
	private String userTell;
	private int userLv;
	private String landAddrBase;
	private String landAddrDtl;
	private String landPostNo;
	private String loadAddrBase;
	private String loadAddrDtl;
	private String loadPostNo;
	private int loginFailCnt;
	private String lockYn;
	private String tempPassYn;
	private String snsConType;
	private String snsConYn;
	private String snsSeqId;
	private String certKeyVal;
	private String certKeyDtime;
	private String rcertKeyVal;
	private String rcertKeyDtime;
	private String certType;
	private String expiresDtime;
	private String profileImage;
	private long point;
	private String userIp;
	private Date updDate;
	private Date regDate;
	private String accessToken;
	private String tokenType;
	private String refreshToken;
	private String idToken;
	private String expiresIn;
	private String scope;
	private String refreshTokenExpiresIn;
	private String id;
	private String useYn;
	private String regUserId;
	private String updUserId;
}
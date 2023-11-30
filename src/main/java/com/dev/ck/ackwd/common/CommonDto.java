package com.dev.ck.ackwd.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommonDto {
	private String grpCd; /*그룹코드*/
	private String grpNm; /*그룹코드*/
	private String cd; /*코드*/
	private String cdNm; /*코드명*/
	private String cdDesc; /*코드설명*/
	private String sort; /*정렬*/
	private String dplyYn; /*전시여부*/
	private String useYn; /*사용여부*/
	private String sysRegrId; /*시스템등록자아이디*/
	private String sysRegDtime; /*시스템등록일시*/
	private String sysModrId; /*시스템수정자아이디*/
	private String sysModDtime; /*시스템수정일시*/
}
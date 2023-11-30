package com.dev.ck.ackwd.config.persistent.mybatis.audit;

import java.util.Date;

import javax.validation.constraints.Null;

import lombok.Data;

/**
 * 프레임워크에서 Table Audit 에 관련되어 제공될수 있는 정보들을 담는다.
 * 
 * @author narusas
 *
 */
@Data
public class AuditInfo {
	/**
	 * 생성자의 ID
	 */
	@Null String	registId;

	/**
	 * 생성 된 시간
	 */
	@Null Date		registTime;

	/**
	 * 생성_프로그램_아이디
	 */
	@Null String	registProgramId;

	/**
	 * 생성_트랜잭션_아이디
	 */
	@Null String	registRequestId;

	/**
	 * 변경자의 ID
	 */
	@Null String	modifyId;

	/**
	 * 마지막 변경된 시간
	 */
	@Null Date		modifyTime;

	/**
	 * 수정_프로그램_아이디
	 */
	@Null String	modifyProgramId;

	/**
	 * 수정_트랜잭션_아이디
	 */
	@Null String	modifyRequestId;
}

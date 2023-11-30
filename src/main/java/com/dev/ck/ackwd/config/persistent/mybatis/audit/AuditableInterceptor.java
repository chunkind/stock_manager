package com.dev.ck.ackwd.config.persistent.mybatis.audit;

import java.util.Date;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;

import com.dev.ck.ackwd.config.core.environment.RequestTransactionContextHolder;
import com.dev.ck.ackwd.config.domain.entity.AuditableEntity;
import com.dev.ck.ackwd.config.interceptors.programidcontext.ProgramIdContextHolder;
import com.dev.ck.ackwd.config.security.context.ProjectSecurityContextHolder;

/**
 * Mybatis 의 update(insert 포함) 가 실행 될떄 현재 사용자, 시간, 프로그램등의 정보를 update 의 파라메터에 주입하는 인터셉터
 * 
 * @author narusas
 * 
 */
@Intercepts({ @Signature(type = Executor.class, method = "update", args = { MappedStatement.class, Object.class }) })
public class AuditableInterceptor implements Interceptor {

	@Override
	public Object intercept(Invocation invocation) throws Throwable {

		// final MappedStatement mappedStatement = (MappedStatement)
		// invocation.getArgs()[0];
		Object parameter = invocation.getArgs()[1];
		if (parameter == null || (parameter instanceof AuditableEntity) == false) {
			return invocation.proceed();
		}
		// SqlCommandType cmdType = mappedStatement.getSqlCommandType();
		String userId = ProjectSecurityContextHolder.getCurrentUserId();

		if (StringUtils.isEmpty(userId)) {
			userId = ProjectSecurityContextHolder.getSessionId();
		}
		if (StringUtils.isEmpty(userId)) {
			userId = "anonymous";
		}
		
		Date now = new Date();
		String programId = ProgramIdContextHolder.getProgramId();
		String txId = RequestTransactionContextHolder.getRequestTxId();

		AuditableEntity auditable = (AuditableEntity) parameter;
		AuditInfo auditInfo = new AuditInfo();

		// auditable entity 에 직접 값을 설정 하지 않는 이유: Open Close Principle
		auditInfo.setRegistId(userId);
		auditInfo.setRegistTime(now);
		auditInfo.setRegistProgramId(programId);
		auditInfo.setRegistRequestId(txId);

		// 변경 쿼리에서는 변경만 사용하기 때문에 모두 넣는다
		auditInfo.setModifyId(userId);
		auditInfo.setModifyTime(now);
		auditInfo.setModifyProgramId(programId);
		auditInfo.setModifyRequestId(txId);

		auditable.setupAudit(auditInfo);

		return invocation.proceed();
	}

	@Override
	public Object plugin(Object target) {

		return Plugin.wrap(target, this);
	}

	@Override
	public void setProperties(Properties properties) {

	}
}

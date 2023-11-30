package com.dev.ck.ackwd.config.noo.pagination.interceptor;

import java.sql.Connection;
import java.util.Properties;

import org.apache.ibatis.executor.statement.BaseStatementHandler;
import org.apache.ibatis.executor.statement.RoutingStatementHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;

import com.dev.ck.ackwd.config.noo.pagination.page.Page;
import com.dev.ck.ackwd.config.noo.pagination.uitls.Reflections;

@SuppressWarnings("UnusedDeclaration")
@Intercepts({
	@Signature(type = StatementHandler.class, method = "prepare", args = {Connection.class})
})
public class PreparePaginationInterceptor extends BaseInterceptor {
	private static final long serialVersionUID = -6075937069117597841L;

	public PreparePaginationInterceptor() {
		super();
	}

	@Override
	public Object intercept(Invocation ivk) throws Throwable {
		if (ivk.getTarget().getClass().isAssignableFrom(RoutingStatementHandler.class)) {
			final RoutingStatementHandler statementHandler = (RoutingStatementHandler) ivk.getTarget();
			final BaseStatementHandler delegate = (BaseStatementHandler) Reflections.getFieldValue(statementHandler, DELEGATE);
			final MappedStatement mappedStatement = (MappedStatement) Reflections.getFieldValue(delegate, MAPPED_STATEMENT);

			if (mappedStatement.getId().matches(_SQL_PATTERN)) {
				BoundSql boundSql = delegate.getBoundSql();
				Object parameterObject = boundSql.getParameterObject();
				if (parameterObject == null) {
					log.error("매개 변수 는 인스턴스화 되지");
					throw new NullPointerException("parameterObject 인스턴스화 되지！");
				} else {
					Connection connection = null;
					try {
						connection = (Connection) ivk.getArgs()[0];
						final String sql = boundSql.getSql();
						final int count = SQLHelp.getCount(sql, connection, mappedStatement, parameterObject, boundSql);
						Page page = null;
						page = convertParameter(parameterObject, page);
						page.init(count, page.getPageSize(), page.getCurrentPage());
						String pagingSql = SQLHelp.generatePageSql(sql, page, DIALECT);
						if (log.isDebugEnabled()) {
							log.debug("페이징 SQL:" + pagingSql);
						}
						Reflections.setFieldValue(boundSql, "sql", pagingSql);
					}
					finally {
						if (connection != null){
							connection.close();
						}
					}
				}
			}
		}
		return ivk.proceed();
	}

	@Override
	public Object plugin(Object o) {
		return Plugin.wrap(o, this);
	}

	@Override
	public void setProperties(Properties properties) {
		initProperties(properties);
	}
}

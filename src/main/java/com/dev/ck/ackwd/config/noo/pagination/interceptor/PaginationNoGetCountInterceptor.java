package com.dev.ck.ackwd.config.noo.pagination.interceptor;


import java.util.Properties;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import com.dev.ck.ackwd.config.noo.pagination.page.Page;
import com.dev.ck.ackwd.config.noo.pagination.page.PageContext;

/**
 * 만 차단 쿼리 플러그인 데이터베이스 매김.
 */
@Intercepts({@Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class,
		ResultHandler.class})})
public class PaginationNoGetCountInterceptor extends BaseInterceptor {
	private static final long serialVersionUID = 3576678797374122941L;

	@Override
	public Object intercept(Invocation invocation) throws Throwable {
		final MappedStatement mappedStatement = (MappedStatement) invocation.getArgs()[0];
		if (mappedStatement.getId().matches(_SQL_PATTERN)) { // 拦截需要分页的SQL
			Object parameter = invocation.getArgs()[1];
			BoundSql boundSql = mappedStatement.getBoundSql(parameter);
			String originalSql = boundSql.getSql().trim();

			Object parameterObject = boundSql.getParameterObject();
			if (boundSql.getSql() == null || "".equals(boundSql.getSql())) {
				return null;
			}

			// 페이징 매개 변수 - 컨텍스트 매개 변수 전달
			Page page = null;

			// 각각의 시간을 재설정지도의 첫 번째 해석을 currentPage 대중의 참여를 매핑 한 다음 컨텍스트를 결정
			if (parameterObject != null) {
				page = convertParameter(parameterObject, page);
			}

			PageContext context = PageContext.getPageContext();

			// 페이지에서 페이징 매개 변수 --context 매개 변수 대량 참여
			if (page == null) {
				page = context;
			}
			// 이후 동양의 문맥에서 사용
			if (page != null) {
				// 수정 데이터베이스를 달성하기 위해 수정 페이징 쿼리 현지화 관심 객체
				String pageSql = SQLHelp.generatePageSql(originalSql, page, DIALECT);
				if (log.isDebugEnabled()) {
					System.out.println("PaginationNoGetCountInterceptor 페이징 SQL:" + pageSql);
				}

				invocation.getArgs()[2] = new RowBounds(RowBounds.NO_ROW_OFFSET, RowBounds.NO_ROW_LIMIT);
				BoundSql newBoundSql =
						new BoundSql(mappedStatement.getConfiguration(), pageSql, boundSql.getParameterMappings(),
								boundSql.getParameterObject());

				MappedStatement newMs = copyFromMappedStatement(mappedStatement, new BoundSqlSqlSource(newBoundSql));

				invocation.getArgs()[0] = newMs;
			}
		}
		Object invocation_rtn = invocation.proceed();
		return invocation_rtn;
	}


	@Override
	public Object plugin(Object target) {
		return Plugin.wrap(target, this);
	}


	@Override
	public void setProperties(Properties properties) {
		super.initProperties(properties);
	}

	private MappedStatement copyFromMappedStatement(MappedStatement ms, SqlSource newSqlSource) {
		MappedStatement.Builder builder =
				new MappedStatement.Builder(ms.getConfiguration(), ms.getId(), newSqlSource, ms.getSqlCommandType());
		builder.resource(ms.getResource());
		builder.fetchSize(ms.getFetchSize());
		builder.statementType(ms.getStatementType());
		builder.keyGenerator(ms.getKeyGenerator());
		if (ms.getKeyProperties() != null) {
			for (String keyProperty : ms.getKeyProperties()) {
				builder.keyProperty(keyProperty);
			}
		}
		builder.timeout(ms.getTimeout());
		builder.parameterMap(ms.getParameterMap());
		builder.resultMaps(ms.getResultMaps());
		builder.cache(ms.getCache());
		return builder.build();
	}

	public static class BoundSqlSqlSource implements SqlSource {
		BoundSql boundSql;
		public BoundSqlSqlSource(BoundSql boundSql) {
			this.boundSql = boundSql;
		}

		@Override
		public BoundSql getBoundSql(Object parameterObject) {
			return boundSql;
		}
	}
}

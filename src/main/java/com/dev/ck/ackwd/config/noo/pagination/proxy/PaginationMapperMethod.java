package com.dev.ck.ackwd.config.noo.pagination.proxy;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.binding.BindingException;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;

/**
 * 프록시 클래스 의 구현은 , 매퍼 인터페이스 지원 을 할 수 의 MyBatis 방법 을 확장합니다.
 */
public class PaginationMapperMethod {
	private final SqlSession sqlSession;
	private final Configuration config;

	private SqlCommandType type;
	private String commandName;
	private String commandCountName;

	private final Class<?> declaringInterface;
	private final Method method;

	private Integer rowBoundsIndex;
	private Integer paginationIndex;

	private final List<String> paramNames;
	private final List<Integer> paramPositions;

	private boolean hasNamedParameters;

	public PaginationMapperMethod(Class<?> declaringInterface, Method method, SqlSession sqlSession) {
		paramNames = new ArrayList<String>();
		paramPositions = new ArrayList<Integer>();
		this.sqlSession = sqlSession;
		this.method = method;
		this.config = sqlSession.getConfiguration();
		this.declaringInterface = declaringInterface;
		this.hasNamedParameters = false;
		setupFields();
		setupMethodSignature();
		setupCommandType();
		validateStatement();
	}

	/**
	 * 집행 방법을 연기 
	 */
	@SuppressWarnings("unchecked")
	public Object execute(Object[] args) {
		final Object param = getParam(args);
		Pagination<Object> page;
		RowBounds rowBounds;
		if (paginationIndex != null) {
			page = (Pagination) args[paginationIndex];
			rowBounds =  new RowBounds(page.getOffset(), page.getLimit());
		} else if (rowBoundsIndex != null) {
			rowBounds = (RowBounds) args[rowBoundsIndex];
			page = new Pagination<Object>();
		} else {
			throw new BindingException("Invalid bound statement (not found rowBounds or pagination in paramenters)");
		}
		page.setTotal(executeForCount(param));
		page.setDatas(executeForList(param, rowBounds));
		return page;
	}

	/**
	 * 실행 에있어서 의총 수 , 총 통화 수 를메소드 실행 을 계산 전체적인 결과 를 달성
	 * @param param 매개 변수 정보
	 * @return 쿼리 의 레코드 의 총 수
	 */
	private long executeForCount(Object param) {
		Number result = (Number) sqlSession.selectOne(commandCountName, param);
		return result.longValue();
	}

	/**
	 * 달성 된 결과 의 페이지 매김 은 , 기록 정보를 반환
	 * @param param	 매개 변수
	 * @param rowBounds row
	 * @return 기록 목록
	 */
	private List executeForList(Object param, RowBounds rowBounds) {
		return sqlSession.selectList(commandName, param, rowBounds);
	}

	/**
	 * 재 실행 매개 변수에 대한 정보를 얻
	 * @param args 매개 변수
	 * @return 매개 변수 정보
	 */
	private Object getParam(Object[] args) {
		final int paramCount = paramPositions.size();
		if (args == null || paramCount == 0) {
			return null;
		} else if (!hasNamedParameters && paramCount == 1) {
			return args[paramPositions.get(0)];
		} else {
			Map<String, Object> param = new HashMap<String, Object>();
			for (int i = 0; i < paramCount; i++) {
				param.put(paramNames.get(i), args[paramPositions.get(i)]);
			}
			return param;
		}
	}

	private void setupMethodSignature() {
		final Class<?>[] argTypes = method.getParameterTypes();
		for (int i = 0; i < argTypes.length; i++) {
			if (Pagination.class.isAssignableFrom(argTypes[i])) {
				paginationIndex = i;
			} else if (RowBounds.class.isAssignableFrom(argTypes[i])) {
				rowBoundsIndex = i;
			} else {
				String paramName = String.valueOf(paramPositions.size());
				paramName = getParamNameFromAnnotation(i, paramName);
				paramNames.add(paramName);
				paramPositions.add(i);
			}
		}
	}

	private String getParamNameFromAnnotation(int i, String paramName) {
		Object[] annotations = method.getParameterAnnotations()[i];
		for (Object annotation : annotations) {
			if (annotation instanceof Param) {
				hasNamedParameters = true;
				paramName = ((Param) annotation).value();
			}
		}
		return paramName;
	}

	private void setupFields() {
		commandName = declaringInterface.getName() + "." + method.getName();
		commandCountName = commandName + "Count";
	}

	private void setupCommandType() {
		MappedStatement ms = config.getMappedStatement(commandName);
		type = ms.getSqlCommandType();
		if (type != SqlCommandType.SELECT) {
			throw new BindingException("Unsupport execution method for: " + commandName);
		}
	}

	private void validateStatement() {
		if (!config.hasStatement(commandName)) {
			throw new BindingException("Invalid bound statement (not found): " + commandName);
		}
		if (!config.hasStatement(commandCountName)) {
			throw new BindingException("Invalid bound statement (not found): " + commandCountName);
		}
	}
}

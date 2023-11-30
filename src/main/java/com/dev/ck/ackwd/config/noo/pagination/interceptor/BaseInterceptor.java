package com.dev.ck.ackwd.config.noo.pagination.interceptor;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Properties;

import javax.xml.bind.PropertyException;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.exceptions.PersistenceException;
import org.apache.ibatis.logging.Log;
import org.apache.ibatis.logging.LogFactory;
import org.apache.ibatis.plugin.Interceptor;

import com.dev.ck.ackwd.config.noo.pagination.annotation.Paging;
import com.dev.ck.ackwd.config.noo.pagination.dialect.Dialect;
import com.dev.ck.ackwd.config.noo.pagination.page.Page;
import com.dev.ck.ackwd.config.noo.pagination.page.Pagination;
import com.dev.ck.ackwd.config.noo.pagination.uitls.Reflections;

public abstract class BaseInterceptor implements Interceptor, Serializable {
	protected Log log = LogFactory.getLog(this.getClass());
	protected static final String DELEGATE = "delegate";
	protected static final String MAPPED_STATEMENT = "mappedStatement";
	protected Dialect DIALECT;

	/**
	 * ID의 차단은 ID의 매퍼, 일반을 일치시킬 수 있습니다
	 */
	protected String _SQL_PATTERN = "";
	private static final long serialVersionUID = 4596430444388728543L;

	/**
	 * 전환 및 매개 변수를 점검
	 *
	 * @param parameterObject 매개 변수 개체
	 * @param pageVO		  매개 변수 VO
	 * @return 매개 변수 VO
	 * @throws NoSuchFieldException 매개 변수를 찾을 수 없습니다
	 */
	protected static Page convertParameter(Object parameterObject, Page pageVO) throws NoSuchFieldException {
		if (parameterObject instanceof Page) {
			pageVO = (Pagination) parameterObject;
		} else {
			//페이지 속성을 소유하는 엔티티에 대한 매개 변수
			Paging paging = parameterObject.getClass().getAnnotation(Paging.class);
			String field = paging.field();
			Field pageField = Reflections.getAccessibleField(parameterObject, field);
			if (pageField != null) {
				pageVO = (Pagination) Reflections.getFieldValue(parameterObject, field);
				if (pageVO == null)
					throw new PersistenceException("페이징 매개 변수는 비워 둘 수 없습니다");
				//실제 개체에 반영하여 탭 개체를 초기 설정
				Reflections.setFieldValue(parameterObject, field, pageVO);
			} else {
				throw new NoSuchFieldException(parameterObject.getClass().getName() + "페이징 매개 변수 속성은 존재하지 않습니다!");
			}
		}
		return pageVO;
	}

	/**
	 * 사용자 정의 방언 클래스 의 속성 , 지원 을 설정 및 개발 방법 데이터베이스
	 * <p>
	 * <code>dialectClass</code>,사용자 정의 방언 클래스 . 이 구성 할 수 없습니다
	 * <ode>dbms</ode> 데이터베이스 유형 , 플러그인 데이터베이스 에 대한 지원
	 * <code>sqlPattern</code> SQL ID 를 가로 채기 위해 필요
	 * </p>
	 * 당신이 < 코드 > dialectClass </ 코드 >와 < 코드 > DBMS </ 코드 > , 장소 < 코드 > DBMS 를 구성하는 경우 </ 코드 > 메인
	 *
	 * @param p 재산
	 */
	protected void initProperties(Properties p) {
		String dialectClass = p.getProperty("dialectClass");
		if (StringUtils.isEmpty(dialectClass)) {
			try {
				throw new PropertyException("데이터베이스 페이징 방언 을 찾을 수 없습니다 !");
			} catch (PropertyException e) {
				e.printStackTrace();
			}
		} else {
			Dialect dialect1 = (Dialect) Reflections.instance(dialectClass);
			if (dialect1 == null) {
				throw new NullPointerException("방언 인스턴스 오류");
			}
			DIALECT = dialect1;
		}

		_SQL_PATTERN = p.getProperty("sqlPattern");
		if (StringUtils.isEmpty(_SQL_PATTERN)) {
			try {
				throw new PropertyException("sqlPattern property is not found!");
			} catch (PropertyException e) {
				e.printStackTrace();
			}
		}
	}
}

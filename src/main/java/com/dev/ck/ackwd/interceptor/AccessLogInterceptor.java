package com.dev.ck.ackwd.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.dev.ck.ackwd.log.service.LogService;

/**
 * @User : K. J. S.
 * @date : 2018. 5. 20.
 * 인입기록
 */
public class AccessLogInterceptor extends HandlerInterceptorAdapter{
	@Autowired private LogService logService;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		boolean result = true;
		return result;
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		super.afterCompletion(request, response, handler, ex);
	}
}

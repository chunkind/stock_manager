package com.dev.ck;

import javax.servlet.ServletContext;

import org.springframework.scheduling.annotation.EnableScheduling;

import com.dev.ck.ackwd.config.CommonWebApplicationInitializer;
import com.dev.ck.ackwd.config.app.ApplicationConfig;
import com.dev.ck.ackwd.config.webapp.CkWebAppConfig;

import lombok.extern.slf4j.Slf4j;
//import nframework.web.authcontextfilter.AuthenticationContextFilter;
//import nframework.web.initializer.CommonWebApplicationInitializer;

@Slf4j
public class CkWebApplicationInitializer extends CommonWebApplicationInitializer {

	@Override
	protected String[] getServletMappings() {
		return new String[] {"*.do", "/app/*"};
	}

	@Override
	protected Class<?>[] getRootContextConfig() {
		return new Class[] {ApplicationConfig.class};
	}

	@Override
	protected Class<?>[] getServletContextConfig() {
		return new Class[] {CkWebAppConfig.class};
	}

	@Override
	protected String getResourceLoadingMapping() {
		return "/contents/*";
	}

	@Override
	protected boolean enableWebSecurity() {
		return false;
	}

	@Override
	protected void initCustomListener(ServletContext container) {
		// container.addListener(new ServletRequestListener());
	}

	@Override
	protected void initCustomeFilters(ServletContext container) {
		log.info("init BO CustomFilters");
//		log.info("Init cookieSessionFilter");
//		container
//		.addFilter("cookieSessionFilter", new DelegatingFilterProxy("cookieSessionFilter"))
//			.addMappingForUrlPatterns(null, true, new String[]{"*.do","/app/*"})
//			;
//		log.info("Init projectSecurityContextHolderFilter");
//		container
//			.addFilter("projectSecurityContextHolderFilter", new DelegatingFilterProxy("projectSecurityContextHolderFilter"))
//				.addMappingForUrlPatterns(null, true, new String[]{"*.do","/app/*"})
//				;
//		log.info("Init sessionWrappingFilter");
//		container
//			.addFilter("sessionWrappingFilter", new DelegatingFilterProxy("sessionWrappingFilter"))
//				.addMappingForUrlPatterns(null, true,new String[]{"*.do","/app/*"});
//		log.info("Init sitemesh");
//		container
//			.addFilter("sitemesh", "com.opensymphony.sitemesh.webapp.SiteMeshFilter")
//				.addMappingForUrlPatterns(null, true, "/*");
	}
	
	@Override
	protected boolean useWebXmlFilters(){
		return false;
	}
}
